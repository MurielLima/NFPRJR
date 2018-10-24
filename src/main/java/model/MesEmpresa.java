/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Mateus
 */
@Document
public class MesEmpresa {
    @Id
    private String idMesEmpresa;
    private String ano;
    private String mes;
    @DBRef
    private Meses meses;

    public MesEmpresa(){
        
    }
    
    public MesEmpresa(String ano, String mes, Meses meses) {
        this.ano = ano;
        this.mes = mes;
        this.meses = meses;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public Meses getMeses() {
        return meses;
    }

    public void setMeses(Meses meses) {
        this.meses = meses;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + (this.idMesEmpresa != null ? this.idMesEmpresa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MesEmpresa other = (MesEmpresa) obj;
        if ((this.idMesEmpresa == null) ? (other.idMesEmpresa != null) : !this.idMesEmpresa.equals(other.idMesEmpresa)) {
            return false;
        }
        return true;
    }
    
    
    
}
