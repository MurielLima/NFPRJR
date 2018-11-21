package utility;

import static config.Config.nf;
import static config.DAO.empresaRepository;
import static config.DAO.instituicaoRepository;
import static config.DAO.mesEmpresaRepository;
import static config.DAO.mesesRepository;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.scene.control.Alert;
import model.Empresa;
import model.MesEmpresa;
import model.Meses;
import model.Nota;
import view.MesesEmpresasController;

public class Dados {

    MesesEmpresasController controllerPai;
    private BufferedReader br = null;
    private String nomeArq;
    private String linha;
    private String[] mesAno;
    String[] partes;
    private List<Nota> lstNota;
    private Meses m;
    public double j = 0.000000001;
    public double prog = 0;

    public Dados(String nomeArq) {
        m = new Meses(0, 0, 0);
        lstNota = new ArrayList<>();
        this.nomeArq = nomeArq;
    }

    private void primeiraLinha() {

        partes = linha.split(" ");   // Dividir a string em partes.
        partes[6] = partes[6].replaceAll("[\\\\.\\\\/\\\\-]", "");
        //Mes e Ano para a Classe Meses
        mesAno = partes[9].split(";");
        mesAno = mesAno[0].split("/");

    }

    public void importar(MesesEmpresasController mes) {
        controllerPai = mes;

        Task threadImportacao = new Task<Integer>() {
            @Override
            protected Integer call() throws InterruptedException {
                try {
                    try {
                        long linhas;
                        br = new BufferedReader(new FileReader(nomeArq));
                        linhas = br.lines().count();
                        br.close();
                        br = new BufferedReader(new FileReader(nomeArq));
                        if ((linha = br.readLine()) != null) {
                            primeiraLinha();
                        }
                        if ((instituicaoRepository.countByCnpj(partes[6])) > 0) {//Juntar findByCnpjMesAno
                            if ((mesesRepository.countByMesAndAno(mesAno[0], mesAno[1])) == 0 ) {
                                System.out.println("Mes ugal");
                                System.out.println(mesAno[0]);
                                System.out.println(mesAno[1]);
                                m.setMes(mesAno[0]);
                                m.setAno(mesAno[1]);
                                while ((linha = br.readLine()) != null) {
                                  //  System.out.println("WHILE OK");
                                    Nota no = particionaLinhas(linha);
                                    if (no != null) {
                                     //   System.out.println("IF NO != NULL");
                                        lstNota.add(no);
                                        cadastraNota(no);
                                    }
                                    updateProgress(prog++, linhas);
                                    updateMessage(nf.format(controllerPai.progressBar.getProgress() * 100) + "%");
                                }
                                mesesRepository.insert(m);

                            } else {
                                System.out.println("Mes já cadastrado");
                                System.out.println("Mes ugal");
                                System.out.println(mesAno[0]);
                                System.out.println(mesAno[1]);
                                System.out.println(mesesRepository.countByMesAndAno(mesAno[0], mesAno[1]));
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Erro");
                                alert.setHeaderText("Cadastro de Mês");
                                alert.setContentText("Mês Já cadastrado");
                                alert.showAndWait();
                            }
                        } else {
                            System.out.println("Instituição não confere");
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Erro");
                            alert.setHeaderText("Arquivo de Instituição");
                            alert.setContentText("Nota não tem o mesmo CNPJ da Instituição");
                            alert.showAndWait();
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(MesesEmpresasController.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            if (br != null) {
                                br.close();
                            }
                        } catch (IOException ex) {
                        }
                    }
                } catch (Exception e) {
                }
                return 0;
            }
        };
        Thread t = new Thread(threadImportacao);

        t.setDaemon(
                true);
        t.start();
        controllerPai.stackPane.visibleProperty().bind(threadImportacao.runningProperty());
        controllerPai.lblProgress.textProperty().bind(threadImportacao.messageProperty());
        controllerPai.progressBar.progressProperty().bind(threadImportacao.progressProperty());
        threadImportacao.stateProperty().addListener(new ChangeListener<Worker.State>() {
            @Override
            public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue
            ) {
                if (newValue == Worker.State.SUCCEEDED) {
                    controllerPai.inicializaComboMeses();
                }
            }

        }
        );

    }

    private Nota particionaLinhas(String linha1) {
        Nota no = new Nota();
        String[] partes = linha1.split(";");

        if (partes[6].equals("CALCULADO")) {
          //  System.out.println("Mais uma");
            //Formatar CNPJ para apenas numeros
            partes[0] = partes[0].replaceAll("[\\\\.\\\\/\\\\-]", "");
            no.setCnpj(partes[0]);

            //Setar razao social
            no.setRazaoSocial(partes[1]);

            //Mes e Ano para a classe MesEmpresa
            no.setMes(mesAno[0]);
            no.setAno(mesAno[1]);

            //Valor total da nota
            partes[4] = partes[4].replaceAll("R\\$", "");
            partes[4] = partes[4].replaceAll("\\,", ".");
            no.setValor(partes[4]);

            //Valor total credito
            partes[5] = partes[5].replaceAll("R\\$", "");
            partes[5] = partes[5].replaceAll("\\,", ".");
            no.setCredito(partes[5]);
            return no;
        }
        return null;
    }

    private void cadastraNota(Nota me) {
        int i = 0;
        try {

            MesEmpresa mesEmpresa = new MesEmpresa(0, 0, 0);
            if ((mesEmpresaRepository.findByAnoAndMesAndEmpresa(me.getAno(), me.getMes(), empresaRepository.findByCnpj(me.getCnpj()))) != null) {
                mesEmpresa = (mesEmpresaRepository.findByAnoAndMesAndEmpresa(me.getAno(), me.getMes(), empresaRepository.findByCnpj(me.getCnpj())));
                mesEmpresa.setTotalCredito(me.getCredito() + mesEmpresa.getTotalCredito());
                mesEmpresa.setTotalValor(me.getValor() + mesEmpresa.getTotalValor());
                mesEmpresa.setTotalNotas(mesEmpresa.getTotalNotas() + 1);
              //  System.out.println("save");
                mesEmpresaRepository.save(mesEmpresa);
            } else {
                mesEmpresa.setAno(me.getAno());
                mesEmpresa.setMes(me.getMes());
                mesEmpresa.setTotalCredito(me.getCredito());
                mesEmpresa.setTotalValor(me.getValor());
                mesEmpresa.setTotalNotas(1);
                // Setar a Empresa para a classe MesEmpresa
                if ((empresaRepository.countByCnpj(me.getCnpj())) == 0) {
                    Empresa empTemp = new Empresa(me.getCnpj(), me.getRazaoSocial(), me.getRazaoSocial());

                    empresaRepository.insert(empTemp);

                }
                //System.out.println("insert");
                mesEmpresa.setEmpresa(empresaRepository.findByCnpj(me.getCnpj()));
                mesEmpresaRepository.insert(mesEmpresa);
            }

            m.addTotalNotas(1);
            m.addTotalValor(me.getValor());
            m.addTotalCredito(me.getCredito());
          //  System.out.println(m.getTotalNotas());
         //   System.out.println("CADASTRA NOTA OK");

        } catch (Exception e) {
            System.out.println("Erro no cadastro de notas" + e.getMessage());
        }
    }

    private void cadastraNotas() {
        int i = 0;
        try {
            for (Nota me : lstNota) {
                MesEmpresa mesEmpresa = new MesEmpresa();
                if ((mesEmpresaRepository.findByAnoAndMesAndEmpresa(me.getAno(), me.getMes(), empresaRepository.findByCnpj(me.getCnpj()))) != null) {
                    mesEmpresa = (mesEmpresaRepository.findByAnoAndMesAndEmpresa(me.getAno(), me.getMes(), empresaRepository.findByCnpj(me.getCnpj())));
                    mesEmpresa.setTotalCredito(me.getCredito() + mesEmpresa.getTotalCredito());
                    mesEmpresa.setTotalValor(me.getValor() + mesEmpresa.getTotalValor());
                    mesEmpresa.setTotalNotas(mesEmpresa.getTotalNotas() + 1);
                    System.out.println("save" + i);
                    mesEmpresaRepository.save(mesEmpresa);
                } else {
                    mesEmpresa.setAno(me.getAno());
                    mesEmpresa.setMes(me.getMes());
                    mesEmpresa.setTotalCredito(me.getCredito());
                    mesEmpresa.setTotalValor(me.getValor());
                    mesEmpresa.setTotalNotas(1);
                    // Setar a Empresa para a classe MesEmpresa
                    if ((empresaRepository.countByCnpj(me.getCnpj())) == 0) {
                        Empresa empTemp = new Empresa(me.getCnpj(), me.getRazaoSocial(), me.getRazaoSocial());
                        System.out.println("insert" + i);
                        empresaRepository.insert(empTemp);

                    }
                    mesEmpresa.setEmpresa(empresaRepository.findByCnpj(me.getCnpj()));
                    mesEmpresaRepository.insert(mesEmpresa);
                }

                m.addTotalNotas(m.getTotalNotas() + 1);
                m.addTotalValor(me.getValor());
                m.addTotalCredito(me.getCredito());
                System.out.println(i++ + " - " + me);

            }
            mesesRepository.insert(m);
        } catch (Exception e) {
            System.out.println("Erro no cadastro de notas" + e.getMessage());
        }
    }

}
