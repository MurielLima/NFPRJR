/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import static config.DAO.empresaRepository;
import static config.DAO.instituicaoRepository;
import static config.DAO.mesEmpresaRepository;
import static config.DAO.mesesRepository;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Empresa;
import model.Instituicao;
import model.MesEmpresa;
import model.Meses;
import utility.Dados;


public class MesesEmpresasController implements Initializable {
    

    private Meses mesAux;

private List<MesEmpresa> lstPrinc = new ArrayList<MesEmpresa>();


@FXML
private ComboBox cmbMeses;

@FXML
private TableView tblVlwMesEmpresa;

@FXML
private Label lblTotalNotas;

@FXML
private Label lblTotalValNotas;

@FXML
private Label lblTotalCredito;

@FXML
private void acLimpar(){
     tblVlwMesEmpresa.setItems(FXCollections.observableList(mesEmpresaRepository.findAll()));
     cmbMeses.getSelectionModel().clearSelection();
}

@FXML
    private void btnAbrirClick() {
        
        Dados dados;
        final Stage stage = null;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Escolha o seu arquivo:");
        fileChooser.setInitialDirectory(new File("C:\\"));
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "Text Files", "*.txt","*.csv"
        );
        fileChooser.getExtensionFilters().add(extFilter);

        
        
        String[] mesAno;
        Meses m = new Meses();
        String linha;
        BufferedReader br = null;

        fileChooser.getExtensionFilters()
                .add(extFilter);

        try {
            br = new BufferedReader(new FileReader(String.valueOf(fileChooser.showOpenDialog(stage))));
            if((linha = br.readLine()) != null){
                String[] partes = linha.split(" ");   // Dividir a string em partes.  
                
                //Mes e Ano para a Classe Meses
                mesAno=partes[9].split(";");
                mesAno=mesAno[0].split("/");

                //FUNCAO DE TESTE 
                for (String parte : mesAno) {
                    System.out.println(parte);
                }
                if((instituicaoRepository.countByCnpj(partes[6]))==1){
                        if(mesesRepository.countByMesOrAno(mesAno[0],mesAno[1])==0){
                            m.setMes(mesAno[0]);
                            m.setAno(mesAno[1]);
                            //Inicia loop para calcular as notas
                            while ((linha = br.readLine()) != null) {
                                MesEmpresa me=new MesEmpresa();
                                partes = linha.split(";");
                                if (partes[6].equals("CALCULADO")) {
                            //        Formatar CNPJ para apenas numeros
                                    partes[0] = partes[0].replaceAll("[\\\\.\\\\/\\\\-]", "");
                                    // Setar a Empresa para a classe MesEmpresa
                                    if (empresaRepository.findByCnpj(partes[0]) != null) {
                                        me.setEmpresa(empresaRepository.findByCnpj(partes[0])) ;
                                    }else {
                                        Empresa empTemp = new Empresa(partes[0], partes[1], partes[1]);
                                        empresaRepository.insert(empTemp);
                                        me.setEmpresa(empresaRepository.findByCnpj(partes[0]));
                                    }
                                    //Mes e Ano para a classe MesEmpresa
                                    mesAno = partes[3].split("/");
                                    me.setMes(mesAno[0]);
                                    me.setAno(mesAno[1]);
                                    //Valor total da nota
                                    partes[4] = partes[4].replaceAll("R\\$ ", "");
                                    partes[4] = partes[4].replaceAll("\\,", ".");
                                    me.setTotalValor(Double.valueOf(partes[4]));
                                    m.addTotalValor(me.getTotalValor());
                                    //Valor total credito
                                    partes[5] = partes[5].replaceAll("R\\$ ", "");
                                    partes[5] = partes[5].replaceAll("\\,", ".");
                                    me.setTotalCredito(Double.valueOf(partes[5]));
                                    m.addTotalCredito(me.getTotalCredito());
                                    mesEmpresaRepository.insert(me);
                                }

                            }
                            mesesRepository.insert(m);
                        }else{
                            //Alert Mes já cadastrado
                        }
                }else{
                    //Alert Instituição errada
                }
                }

            

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
            }
        }

//        tblView.refresh();
//
//        tblView.setItems(
//                FXCollections.observableList(professorRepository.findAll(new Sort(new Sort.Order("nome")))));

        
        // Cria o objeto Dados na memória passando por parâmetro o nome.
        
//        lstPrinc = dados.ler();
        
        // Ler e interpretar o arquivo e devolver uma lista.

        // Mostra os times no TableView.
        

    }




    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        List<Meses>  lista = new ArrayList<>();
        lista=mesesRepository.findAll();
        Collections.reverse(lista);
       cmbMeses.setItems(FXCollections.observableList(lista));
       cmbMeses.getSelectionModel().selectFirst();
      mesAux =  (Meses) cmbMeses.getSelectionModel().getSelectedItem();
     
       tblVlwMesEmpresa.setItems(FXCollections.observableList(mesEmpresaRepository.findByMes(mesAux.getMes())));
        lblTotalCredito.setText(Double.toString(mesAux.getTotalCredito()));
                lblTotalNotas.setText(Integer.toString(mesAux.getTotalNotas()));
              lblTotalValNotas.setText(Double.toString(mesAux.getTotalValor()));
       
       cmbMeses.valueProperty().addListener(
                new ChangeListener<Meses>() {
            public void changed(ObservableValue<? extends Meses> observable, Meses oldValue, Meses newValue) {
                if(newValue!=null){
                tblVlwMesEmpresa.setItems(FXCollections.observableList(mesEmpresaRepository.findByMes(newValue.getMes())));
                lblTotalCredito.setText(Double.toString(newValue.getTotalCredito()));
                lblTotalNotas.setText(Integer.toString(newValue.getTotalNotas()));
                lblTotalValNotas.setText(Double.toString(newValue.getTotalValor()));
                
            }}

           
        });
       
    }    
    
}
