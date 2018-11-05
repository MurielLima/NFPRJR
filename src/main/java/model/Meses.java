/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Mateus
 */
@Document
@CompoundIndexes({
    @CompoundIndex(
            name = "anoMes_idx",
            def = "{'ano': 1,'mes': 1}", unique = true)

})
public class Meses {
    @Id
    private String idMes;
    private String mes;
    private String ano;
    private int totalNotas;
    private double totalValor;
    private double totalCredito;
 

     public Meses(){
         
     }
    
    
    public Meses(String mes, String ano, int totalNotas, double totalValor, double totalCredito) {
        this.mes = mes;
        this.ano = ano;
        this.totalNotas = totalNotas;
        this.totalValor = totalValor;
        this.totalCredito = totalCredito;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public double getTotalNotas() {
        return totalNotas;
    }

    public void setTotalNotas(int totalNotas) {
        this.totalNotas = totalNotas;
    }

    public double getTotalValor() {
        return totalValor;
    }

    public void setTotalValor(double totalValor) {
        this.totalValor = totalValor;
    }

    public double getTotalCredito() {
        return totalCredito;
    }

    public void setTotalCredito(double totalCredito) {
        this.totalCredito = totalCredito;
    }

    @Override
    public String toString() {
        return mes;
    }
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.idMes != null ? this.idMes.hashCode() : 0);
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
        final Meses other = (Meses) obj;
        if ((this.idMes == null) ? (other.idMes != null) : !this.idMes.equals(other.idMes)) {
            return false;
        }
        return true;
    }
    
    
    
}
