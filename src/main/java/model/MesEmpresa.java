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
    private Empresa empresa;
    private int totalNotas;
    private double totalValor;
    private double totalCredito;
 

    public MesEmpresa(){
        
    }

    public MesEmpresa(String ano, String mes, Empresa empresa, int totalNotas, double totalValor, double totalCredito) {
        this.ano = ano;
        this.mes = mes;
        this.empresa = empresa;
        this.totalNotas = totalNotas;
        this.totalValor = totalValor;
        this.totalCredito = totalCredito;
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


    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public int getTotalNotas() {
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

   public double getPercentual(){
       return getTotalCredito()/getTotalValor();
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