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
//        meses = new Meses("05","2018", 28, 527.25, 7.89);
//        mesesRepository.save(meses);
//        mesEmpresa = new MesEmpresa("2018", "05", meses,empresa);
//        mesEmpresaRepository.save(mesEmpresa);
//         
//        cidade = new Cidade("Ponta Grossa", "PG");
//        cidadeRepository.save(cidade);
//        empresa = new Empresa("83276687000109", "SuperMercado Condor", "SuperMercado Condor",cidade);
//        empresaRepository.save(empresa);
//        meses = new Meses("05","2018", 58, 1277.25, 28.38);
//        mesesRepository.save(meses);
//        mesEmpresa = new MesEmpresa("2018", "05", meses,empresa);
//        mesEmpresaRepository.save(mesEmpresa);
//         cidade = new Cidade("Castro", "CST");
//        cidadeRepository.save(cidade);
//        empresa = new Empresa("83276131000109", "Posto do Mel", "Posto do Mel",cidade);
//        empresaRepository.save(empresa);
//        meses = new Meses("06","2018", 22, 685.25, 9.89);
//        mesesRepository.save(meses);
//        mesEmpresa = new MesEmpresa("2018", "05", meses,empresa);
//        mesEmpresaRepository.save(mesEmpresa);
//         
//        cidade = new Cidade("Carambeí", "CRB");
//        cidadeRepository.save(cidade);
//        empresa = new Empresa("83276557000109", "SuperMercado Rickli", "SuperMercado Rickli",cidade);
//        empresaRepository.save(empresa);
//        meses = new Meses("05","2018", 78, 1357.25, 32.38);
//        mesesRepository.save(meses);
//        mesEmpresa = new MesEmpresa("2018", "05", meses,empresa);
//        mesEmpresaRepository.save(mesEmpresa);
    }    
}
