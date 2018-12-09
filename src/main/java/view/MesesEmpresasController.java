/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import static config.Config.i18n;
import static config.Config.nf;
import static config.DAO.empresaRepository;
import static config.DAO.instituicaoRepository;
import static config.DAO.mesEmpresaRepository;
import static config.DAO.mesesRepository;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import static javafx.scene.input.MouseEvent.MOUSE_CLICKED;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Empresa;
import model.Instituicao;
import model.MesEmpresa;
import model.Meses;
import model.Nota;
import utility.Dados;
import utility.XPopOver;

public class MesesEmpresasController implements Initializable {

    public Empresa empresaTemp;
    private Meses mesAux;

    private List<MesEmpresa> lstPrinc = new ArrayList<MesEmpresa>();

    @FXML
    private ComboBox cmbMeses;

    @FXML
    private TableView tblVlwMesEmpresa;

    @FXML
    private TableView teste;

    @FXML
    private Label lblTotalNotas;

    @FXML
    private Label lblTotalValNotas;

    @FXML
    private Label lblTotalCredito;
    @FXML
    public StackPane stackPane;
    @FXML
    public Label lblProgress;
    @FXML
    public ProgressBar progressBar;
    @FXML
    public Button btnAbrir;

    @FXML
    private void tblVlwEmpresaClick(Event event) {
        MouseEvent me = null;
        if (event.getEventType() == MOUSE_CLICKED) {
            me = (MouseEvent) event;
            if (me.getClickCount() == 2) {   //Conta os clicks do mouse, 2 no presente caso.
                showInformacoes();

            }
        }
    }

    @FXML
    private void showInformacoes() {
        MesEmpresa mesEmpresa = (MesEmpresa) tblVlwMesEmpresa.getSelectionModel().getSelectedItem();
        empresaTemp = mesEmpresa.getEmpresa();
        if (mesEmpresa != null) {
            String cena = "/fxml/InformacoesEmpresa.fxml";
            XPopOver popOver = null;
            popOver = new XPopOver(cena, "Empresa", null);

            InformacoesEmpresa controllerFilho = popOver.getLoader().getController();
            controllerFilho.setCadastroController(this);
        }

    }

    @FXML
    private void acLimpar() {
        tblVlwMesEmpresa.setItems(FXCollections.observableList(mesEmpresaRepository.findAll()));
        cmbMeses.getSelectionModel().clearSelection();
         lblTotalCredito.setText("");
            lblTotalNotas.setText("");
            lblTotalValNotas.setText("");
    }

    @FXML
    private void acExcluiMes() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                i18n.getString("lbl.confirmaExcluiMes.txt"),
                ButtonType.YES, ButtonType.NO);
        alert.setTitle(i18n.getString("lbl.mes.txt"));
        alert.showAndWait();
        if (alert.getResult() == ButtonType.NO) {
            alert.close();
            return;
        }

        mesAux = (Meses) cmbMeses.getSelectionModel().getSelectedItem();
        mesesRepository.delete(mesAux);
        mesEmpresaRepository.deleteByAnoAndMes(mesAux.getAno(), mesAux.getMes());
        tblVlwMesEmpresa.setItems(FXCollections.observableList(mesEmpresaRepository.findByMes(mesAux.getMes())));
        cmbMeses.setItems(FXCollections.observableList(mesesRepository.findAll()));
        inicializaComboMeses();

    }

    @FXML
    private void btnAbrirClick() {

        Dados dados;
        final Stage stage = null;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Escolha o seu arquivo:");
        fileChooser.setInitialDirectory(new File("C:\\"));
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "Text Files", "*.txt", "*.csv"
        );
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.getExtensionFilters()
                .add(extFilter);

        dados = new Dados(String.valueOf(fileChooser.showOpenDialog(stage)));
        // btnAbrir.disableProperty();
        dados.importar(MesesEmpresasController.this);

    }
    
    public void filtraFilial(){
        int i =0;
        Empresa emp;
        String[] partes;
        String auxBusca = "";
        MesEmpresa mesEmpresa = (MesEmpresa) tblVlwMesEmpresa.getSelectionModel().getSelectedItem();
        emp = mesEmpresa.getEmpresa();
        partes = emp.getCnpj().split("");
        for(String st: partes){
            if(i<8)
            auxBusca = auxBusca.concat(partes[i]);
            System.out.println(partes[i]);
            i++;
        }
        System.out.println(auxBusca);
       List<Empresa> lstempresa = new ArrayList<Empresa>();
       lstempresa = empresaRepository.findByCnpjLikeIgnoreCase(auxBusca);
       tblVlwMesEmpresa.refresh();
          tblVlwMesEmpresa.setItems(FXCollections.observableList(lstempresa));
           tblVlwMesEmpresa.refresh();
       for(Empresa st: lstempresa){
            System.out.println(st.getRazaoSocial());
        }
    

    }

    public void inicializaComboMeses() {
        List<Meses> lista = new ArrayList<>();
        lista = mesesRepository.findAll();
        if (!lista.isEmpty()) {
            Collections.reverse(lista);

            cmbMeses.setItems(FXCollections.observableList(lista));
            cmbMeses.getSelectionModel().selectFirst();
            mesAux = (Meses) cmbMeses.getSelectionModel().getSelectedItem();

            tblVlwMesEmpresa.setItems(FXCollections.observableList(mesEmpresaRepository.findByMes(mesAux.getMes())));
            // System.out.println(mesAux.getTotalCredito());
            //     teste.setItems(FXCollections.observableList(mesesRepository.findByMes(mesAux.getMes())));
            lblTotalCredito.setText("R$ " + String.format("%.2f", mesAux.getTotalCredito()));
            lblTotalNotas.setText(String.valueOf(mesAux.getTotalNotas()));
            lblTotalValNotas.setText("R$ " + String.format("%.2f", mesAux.getTotalValor()));
        } else {
            lblTotalCredito.setText("");
            lblTotalNotas.setText("");
            lblTotalValNotas.setText("");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        inicializaComboMeses();
        cmbMeses.valueProperty().addListener(
                new ChangeListener<Meses>() {
            @Override
            public void changed(ObservableValue<? extends Meses> observable, Meses oldValue, Meses newValue) {
                if (newValue != null) {
                    tblVlwMesEmpresa.setItems(FXCollections.observableList(mesEmpresaRepository.findByMes(newValue.getMes())));
//                    teste.setItems(FXCollections.observableList(mesesRepository.findByMes(newValue.getMes())));
                    lblTotalCredito.setText("R$ " +String.format("%.2f", newValue.getTotalCredito()));
                    lblTotalNotas.setText(Integer.toString(newValue.getTotalNotas()));
                    lblTotalValNotas.setText("R$ " +String.format("%.2f", newValue.getTotalValor()));

                }
            }

        });

    }
//    }
}
