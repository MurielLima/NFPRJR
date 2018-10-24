package view;

import static config.DAO.cidadeRepository;
import static config.DAO.empresaRepository;
import static config.DAO.mesEmpresaRepository;
import static config.DAO.mesesRepository;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.Cidade;
import model.Empresa;
import model.MesEmpresa;
import model.Meses;
import repository.CidadeRepository;

public class PrincipalController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println(cidadeRepository.findAll());
        System.out.println(empresaRepository.findAll());
        System.out.println(mesesRepository.findAll());
        System.out.println(mesEmpresaRepository.findAll());
   
        label.setText("Hello World!");
    }
    
    Empresa empresa;
    Cidade cidade;
    Meses meses;
    MesEmpresa mesEmpresa;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
//        cidade = new Cidade("Piraí do Sul", "PS");
//        cidadeRepository.save(cidade);
//        empresa = new Empresa("83276137000109", "Mercado Fanelli", "Mercade Fanelli",cidade);
//        empresaRepository.save(empresa);
//        meses = new Meses("05","2018", 28, 527.25, 7.89, empresa);
//        mesesRepository.save(meses);
//        mesEmpresa = new MesEmpresa("2018", "05", meses);
//        mesEmpresaRepository.save(mesEmpresa);
    }    
}
