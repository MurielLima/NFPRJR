/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import static config.DAO.mesEmpresaRepository;
import static config.DAO.mesesRepository;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import model.Meses;


public class MesesEmpresasController implements Initializable {

@FXML
private ComboBox cmbMeses;

@FXML
private TableView tblVlwMesEmpresa;

@FXML
private void acLimpar(){
     tblVlwMesEmpresa.setItems(FXCollections.observableList(mesEmpresaRepository.findAll()));
     cmbMeses.getSelectionModel().clearSelection();
}


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       cmbMeses.setItems(FXCollections.observableList(mesesRepository.findAll()));
       tblVlwMesEmpresa.setItems(FXCollections.observableList(mesEmpresaRepository.findAll()));
       
       cmbMeses.valueProperty().addListener(
                new ChangeListener<Meses>() {
            public void changed(ObservableValue<? extends Meses> observable, Meses oldValue, Meses newValue) {
                if(newValue!=null)
                tblVlwMesEmpresa.setItems(FXCollections.observableList(mesEmpresaRepository.findByMes(newValue.getMes())));
            }

           
        });
       
    }    
    
}
