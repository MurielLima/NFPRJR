/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import static config.Config.ALTERAR;
import static config.Config.EXCLUIR;
import static config.Config.INCLUIR;
import static config.Config.nf;
import static config.DAO.empresaRepository;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import model.Empresa;
import org.controlsfx.control.PopOver;
import org.springframework.data.domain.Sort;
import utility.XPopOver;

/**
 * FXML Controller class
 *
 * @author Muriel
 */
public class EmpresasController implements Initializable {
    /**
     * Initializes the controller class.
     */
    public Empresa empresa;
    public char acao;
    @FXML
    public TableView<Empresa> tblView;
    @FXML
    private MaterialDesignIconView btnIncluir;
    @FXML
    private MaterialDesignIconView btnAlterar;
    @FXML
    private MaterialDesignIconView btnExcluir;
    @FXML
    private TextField txtFldPesquisar;
    @FXML
    private MaterialDesignIconView btnPesquisar;

    @FXML
    private MenuItem mnAlterar;
    @FXML
    private Label lblRandom;
       Timeline timeline;
        Random rand = new Random();


    @FXML
    private void acAlterar() {
        acao = ALTERAR;
        empresa = tblView.getSelectionModel().getSelectedItem();
        showCRUD();
    }

    @FXML
    private void acPesquisar() {
        tblView.setItems(FXCollections.observableList(
                empresaRepository.findByNomeFantasiaLikeIgnoreCaseOrCnpjOrRazaoSocialLikeIgnoreCase(txtFldPesquisar.getText(),txtFldPesquisar.getText(),txtFldPesquisar.getText())));
    }


    @FXML
    private void acLimpar() {
        txtFldPesquisar.setText("");
        tblView.setItems(
                FXCollections.observableList(empresaRepository.findAll(new Sort(new Sort.Order("cnpj")))));
    }

    private void showCRUD() {
        String cena = "/fxml/CRUDEmpresa.fxml";
        XPopOver popOver = null;

        switch (acao) {

            case ALTERAR:
                popOver = new XPopOver(cena, "Alteração de Empresa", null);
                break;

        }
        CRUDEmpresaController controllerFilho = popOver.getLoader().getController();
        controllerFilho.setCadastroController(this);
    }
    
    @FXML
    private void btnProgressClick(){
//        painelInfo.setVisible(true);
//        
//        prog += j;
//        
//        progressoImportacao.setProgress(prog);
   final Task threadImportacao = new Task<Integer>() {
            @Override
            protected Integer call() throws InterruptedException {
                try {
                    int prog = 0;
                    for (int i = 0; i < 100000000; i++) {
                        updateProgress(prog++, 100000000);
                    //    updateMessage(/*nf.format*/(progressoImportacao.getProgress() * 100) + "%");

                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return 0;
                } finally {

                    return 0;
                }
            }

        };
        Thread t = new Thread(threadImportacao);
        t.setDaemon(true);
        t.start();
       

        threadImportacao.stateProperty().addListener(new ChangeListener<Worker.State>() {
            @Override
            public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) {
                if (newValue == Worker.State.SUCCEEDED) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Sucesso");
                    alert.setHeaderText("Finalizado titulo");
                    alert.setContentText("concluido");
                    alert.showAndWait();
                }
            }

        });
    }

      private void randomizaNumero() {
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(1000), new EventHandler() {
                    @Override
                    
                    public void handle(Event event) {
                        
                        Integer num = rand.nextInt(500);  
                        lblRandom.setText(String.valueOf(num));
                      
                        }
                    }
                ));
        timeline.playFromStart();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        tblView.setItems(
                FXCollections.observableList(empresaRepository.findAll(new Sort(new Sort.Order("cnpj")))));
        btnAlterar.visibleProperty().bind(
                Bindings.isEmpty((tblView.getSelectionModel().getSelectedItems())).not());
        mnAlterar.visibleProperty().bind(btnAlterar.visibleProperty());      
        btnPesquisar.disableProperty().bind(txtFldPesquisar.textProperty().isEmpty());
        randomizaNumero();

    }
}
