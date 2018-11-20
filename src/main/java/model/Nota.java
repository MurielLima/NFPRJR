/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Muriel
 */
public class Nota {

    private String cnpj;
    private String ano;
    private String mes;
    private String valor;
    private String credito;
    private String razaoSocial;

    public Nota(String cnpj, String ano, String mes, String valor, String credito, String razaoSocial) {
        this.cnpj = cnpj;
        this.ano = ano;
        this.mes = mes;
        this.valor = valor;
        this.credito = credito;
        this.razaoSocial = razaoSocial;
    }

    public Nota() {
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    

    public String getCnpj() {
        return cnpj;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
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

    public double getValor() {
        return Double.valueOf(valor);
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public double getCredito() {
        return Double.valueOf(credito);
    }

    public void setCredito(String credito) {
        this.credito = credito;
    }
    
}
