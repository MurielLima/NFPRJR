/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import static config.Config.ALTERAR;
import static config.Config.EXCLUIR;
import static config.Config.INCLUIR;
import static config.DAO.cidadeRepository;
import static config.DAO.empresaRepository;
import java.net.URL;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import model.Cidade;
import org.springframework.data.domain.Sort;

/**
 * FXML Controller class
 *
 * @author Muriel
 */
public class CRUDEmpresaController implements Initializable {

 /**
     * Initializes the controller class.
     */
    private EmpresasController controllerPai;
    private final char separadorDecimal
            = new DecimalFormatSymbols(Locale.getDefault(Locale.Category.FORMAT)).getDecimalSeparator();
    @FXML
    private TextField txtFldCnpj;
    @FXML
    private TextField txtFldNomeFantasia;
    @FXML
    private TextField txtFldRazaoSocial;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button btnConfirma;
    @FXML
    private ComboBox cmbCidade;
 

    

    @FXML
    private void btnCancelaClick() {
        anchorPane.getScene().getWindow().hide();
        controllerPai.tblView.requestFocus();
    }

    @FXML
    private void btnConfirmaClick() {
        controllerPai.empresa.setCnpj(txtFldCnpj.getText());
        controllerPai.empresa.setNomeFantasia(txtFldNomeFantasia.getText());
        controllerPai.empresa.setRazaoSocial(txtFldRazaoSocial.getText());        
        controllerPai.empresa.setCidade((Cidade) cmbCidade.getSelectionModel().getSelectedItem());
        
        
        try {
            switch (controllerPai.acao) {
                case ALTERAR:
                    empresaRepository.save(controllerPai.empresa);
                    break;
            }
            controllerPai.tblView.setItems(
                    FXCollections.observableList(empresaRepository.findAll(
                            new Sort(new Sort.Order("cnpj")))));
            controllerPai.tblView.refresh();
            controllerPai.tblView.getSelectionModel().clearSelection();
            controllerPai.tblView.getSelectionModel().select(controllerPai.empresa);
            anchorPane.getScene().getWindow().hide();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Cadastro de Empresa");
            if (e.getMessage().contains("duplicate key")) {
                alert.setContentText("Código já cadastrado");
            } else {
                alert.setContentText(e.getMessage());
            }
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnConfirma.disableProperty().bind(txtFldCnpj.textProperty().isEmpty().
                or(txtFldNomeFantasia.textProperty().isEmpty()));
    }

    public void setCadastroController(EmpresasController controllerPai) {
        this.controllerPai = controllerPai;
        txtFldCnpj.setText(controllerPai.empresa.getCnpj());
        txtFldNomeFantasia.setText(controllerPai.empresa.getNomeFantasia());
        txtFldRazaoSocial.setText(controllerPai.empresa.getRazaoSocial());
        
        cmbCidade.setItems(FXCollections.observableList(
                cidadeRepository.findAll(new Sort(new Sort.Order("nome")))));
        cmbCidade.getSelectionModel().select(controllerPai.empresa.getCidade());

    }

    private final ChangeListener<? super String> listenerCnpj
            = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            if (!newValue.matches("\\d*?")
                    && !newValue.isEmpty()) {
                txtFldCnpj.setText(oldValue);
            } else {
                txtFldCnpj.setText(newValue);
            }
        }
    };
}
