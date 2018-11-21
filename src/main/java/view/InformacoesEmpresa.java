/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import static config.Config.ALTERAR;
import static config.Config.EXCLUIR;
import static config.Config.INCLUIR;
import static config.DAO.mesEmpresaRepository;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import model.MesEmpresa;
import org.springframework.data.domain.Sort;
import utility.XPopOver;


public class InformacoesEmpresa implements Initializable {

    MesesEmpresasController controllerPai;
    
    @FXML
    protected TableView tblVlwMesEmpresa;
    
    @FXML
    private Label lblNomeEmpresa;
    
    @FXML
    private Label lblTotalNotas;

    @FXML
    private Label lblTotalValNotas;

    @FXML
    private Label lblTotalCredito;
    
    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private void btnFecharClick(){
        anchorPane.getScene().getWindow().hide();
    }
    
    private void setValores(){
        int notas = 0;
        double credito = 0,valor = 0;
        for(MesEmpresa M : mesEmpresaRepository.findByEmpresa(controllerPai.empresaTemp)){
            notas += M.getTotalNotas();
            credito += M.getTotalCredito();
            valor += M.getTotalValor();
    }
        lblTotalNotas.setText(String.valueOf(notas));
        lblTotalValNotas.setText("R$ "+String.format("%.2f",valor));
        lblTotalCredito.setText("R$ "+String.format("%.2f",credito));
        
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    public void setCadastroController(MesesEmpresasController controllerPai) {
        this.controllerPai=controllerPai;
        lblNomeEmpresa.setText(controllerPai.empresaTemp.getRazaoSocial());
        tblVlwMesEmpresa.setItems(FXCollections.observableArrayList(mesEmpresaRepository.findByEmpresa(controllerPai.empresaTemp)));
        setValores();
    }
}
