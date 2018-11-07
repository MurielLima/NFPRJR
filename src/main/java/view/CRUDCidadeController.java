/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import static config.Config.ALTERAR;
import static config.Config.INCLUIR;
import static config.DAO.cidadeRepository;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.springframework.data.domain.Sort;

/**
 * FXML Controller class
 *
 * @author Muriel
 */
public class CRUDCidadeController implements Initializable {

    /**
     * Initializes the controller class.
     */
    public char acao;
    private CRUDEmpresaController controllerPai;
    @FXML
    private MaterialDesignIconView btnIncluir;
    @FXML
    private MaterialDesignIconView btnAlterar;
    @FXML
    private TextField txtFldNome;
    @FXML
    private TextField txtFldSigla;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button btnConfirma;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnConfirma.disableProperty().bind(txtFldNome.textProperty().isEmpty().or(txtFldSigla.textProperty().isEmpty()));
    }

    public void setCadastroController(CRUDEmpresaController controllerPai) {
        this.controllerPai = controllerPai;
        txtFldNome.setText(controllerPai.cidade.getNome());
        txtFldSigla.setText(controllerPai.cidade.getSigla());
    }

    @FXML
    private void btnCancelaClick() {
        anchorPane.getScene().getWindow().hide();
    }

    @FXML
    private void btnConfirmaClick() {
        controllerPai.cidade.setNome(txtFldNome.getText().toUpperCase());
        controllerPai.cidade.setSigla(txtFldSigla.getText().toUpperCase());
        if ((cidadeRepository.findByNomeLikeIgnoreCaseOrSiglaLikeIgnoreCase(controllerPai.cidade.getNome(),controllerPai.cidade.getSigla())).equals(controllerPai.cidade)) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Cadastro de Cidade");
            alert.setContentText("Já existe uma cidade com esse nome.");
            alert.showAndWait();
        } else {
            try {

                switch (controllerPai.acao) {
                    case INCLUIR:
                        cidadeRepository.insert(controllerPai.cidade);
                        break;
                    case ALTERAR:
                        cidadeRepository.save(controllerPai.cidade);
                        break;
                }
                controllerPai.cmbCidade.setItems(FXCollections.observableList(
                        cidadeRepository.findAll(new Sort(new Sort.Order("nome")))));
                controllerPai.cmbCidade.getSelectionModel().clearSelection();
                controllerPai.cmbCidade.getSelectionModel().select(controllerPai.cidade);

                anchorPane.getScene().getWindow().hide();

            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Cadastro de Cidade");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }
}
