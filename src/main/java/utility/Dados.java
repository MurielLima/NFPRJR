package utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.Empresa;
import model.MesEmpresa;
import org.json.JSONObject;
import org.json.JSONArray;

public class Dados {
    
    private BufferedReader br = null;
    private String nomeArq;
    private MesEmpresa mesLinha;
    List<MesEmpresa> lstMes = new ArrayList<>();
    
    public Dados(String nomeArq) {
        this.nomeArq = nomeArq;
    }

    
    public List<MesEmpresa> ler() {
        String linha;
        
        try {
            br = new BufferedReader(new FileReader(nomeArq));
            while ((linha = br.readLine()) != null) {
                mesLinha = new MesEmpresa(linha);
             //   analisa(mesLinha);
            }
            
        
        } catch (Exception e){
        } finally {
            try {
                if(br != null){
                   br.close();
            }
        } catch (IOException ex) {
        }    
    }
        
        return lstMes;
    }    
    
    private MesEmpresa achaEmpresa(String nomeBusca){
        
        for (MesEmpresa t : lstMes) {
            if (t.getEmpresa().equals(nomeBusca)){
                return t;
            }
        }
        // Criando novo time caso o mesmo não exista.
        MesEmpresa empresaNova = new MesEmpresa(nomeBusca);
        lstMes.add(empresaNova);
        return(empresaNova);
    }
    
//    
//    private void analisa(MesEmpresa mes) {
//        MesEmpresa mesA = mes;
//           
//        mes.setTotalCredito(0);
//    }
    
    
    
    
    
}
