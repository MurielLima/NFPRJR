/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import static config.Config.ALTERAR;
import static config.Config.EXCLUIR;
import static config.Config.INCLUIR;
import static config.DAO.empresaRepository;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    private void acAlterar() {
        acao = ALTERAR;
        empresa = tblView.getSelectionModel().getSelectedItem();
        showCRUD();
    }

    @FXML
    private void acPesquisar() {
        tblView.setItems(FXCollections.observableList(
                empresaRepository.findByNomeFantasiaLikeIgnoreCaseOrCnpjLikeIgnoreCaseOrRazaoSocialLikeIgnoreCase(txtFldPesquisar.getText(),txtFldPesquisar.getText(),txtFldPesquisar.getText())));
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tblView.setItems(
                FXCollections.observableList(empresaRepository.findAll(new Sort(new Sort.Order("cnpj")))));
        btnAlterar.visibleProperty().bind(
                Bindings.isEmpty((tblView.getSelectionModel().getSelectedItems())).not());
        mnAlterar.visibleProperty().bind(btnAlterar.visibleProperty());      
        btnPesquisar.disableProperty().bind(txtFldPesquisar.textProperty().isEmpty());
        

    }
}
