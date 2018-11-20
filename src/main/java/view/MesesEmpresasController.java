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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Empresa;
import model.Instituicao;
import model.MesEmpresa;
import model.Meses;
import model.Nota;
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
    String[] mesAno;
    Meses m = new Meses();
    String linha1;
    BufferedReader br = null;

    @FXML
    private void acLimpar() {
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
                "Text Files", "*.txt", "*.csv"
        );
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.getExtensionFilters()
                .add(extFilter);

        int i = 0;
        dados = new Dados(String.valueOf(fileChooser.showOpenDialog(stage)));

        final Task threadImportacao = new Task<Integer>() {
            @Override
            protected Integer call() throws InterruptedException {
                try {

                    dados.ler();
                } catch (Exception e) {
                }
                return 0;
            }
        };
        Thread t = new Thread(threadImportacao);

        t.setDaemon(
                true);
        t.start();

        threadImportacao.stateProperty()
                .addListener(new ChangeListener<Worker.State>() {
                    @Override
                    public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue
                    ) {
                        if (newValue == Worker.State.SUCCEEDED) {
                            inicializaComboMeses();
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Sucesso");
                            alert.setHeaderText("Finalizado titulo");
                            alert.setContentText("concluido");
                            alert.showAndWait();
                        }
                    }

                }
                );

    }

    private void inicializaComboMeses() {
        List<Meses> lista = new ArrayList<>();
        lista = mesesRepository.findAll();
        if (lista.size() != 0) {
            Collections.reverse(lista);

            cmbMeses.setItems(FXCollections.observableList(lista));
            cmbMeses.getSelectionModel().selectFirst();
            mesAux = (Meses) cmbMeses.getSelectionModel().getSelectedItem();

            tblVlwMesEmpresa.setItems(FXCollections.observableList(mesEmpresaRepository.findByMes(mesAux.getMes())));
            System.out.println(mesAux.getTotalCredito());
            lblTotalCredito.setText(String.valueOf(mesAux.getTotalCredito()));
            lblTotalNotas.setText(String.valueOf(mesAux.getTotalNotas()));
            lblTotalValNotas.setText(String.valueOf(mesAux.getTotalValor()));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        inicializaComboMeses();
        cmbMeses.valueProperty().addListener(
                new ChangeListener<Meses>() {
            public void changed(ObservableValue<? extends Meses> observable, Meses oldValue, Meses newValue) {
                if (newValue != null) {
                    tblVlwMesEmpresa.setItems(FXCollections.observableList(mesEmpresaRepository.findByMes(newValue.getMes())));
                    lblTotalCredito.setText(Double.toString(newValue.getTotalCredito()));
                    lblTotalNotas.setText(Integer.toString(newValue.getTotalNotas()));
                    lblTotalValNotas.setText(Double.toString(newValue.getTotalValor()));

                }
            }

        });

    }
//    }
}
