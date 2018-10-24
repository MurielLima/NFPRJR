/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Muriel
 */
@Document
public class Empresa {
    @Id
    private String idEmpresa;
    @Indexed(unique=true)
    private String cnpj;
    private String nomeFantasia;
    private String razaoSocial;
    @DBRef
    private Cidade cidade;

    public Empresa() {
    }

    public Empresa(String cnpj, String nomeFantasia, String razaoSocial, Cidade cidade) {
        this.cnpj = cnpj;
        this.nomeFantasia = nomeFantasia;
        this.razaoSocial = razaoSocial;
        this.cidade = cidade;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.idEmpresa != null ? this.idEmpresa.hashCode() : 0);
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
        final Empresa other = (Empresa) obj;
        if ((this.idEmpresa == null) ? (other.idEmpresa != null) : !this.idEmpresa.equals(other.idEmpresa)) {
            return false;
        }
        return true;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }
    
}
