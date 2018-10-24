/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Muriel
 */
@Document
public class Cidade {
    @Id
    private String idCidade;
    @Indexed(unique=true)
    private String nome;
    @Indexed(unique=true)
    private String sigla;

    public Cidade() {
    }

    public Cidade(String nome, String sigla) {
        this.nome = nome;
        this.sigla = sigla;
    }
    

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (this.idCidade != null ? this.idCidade.hashCode() : 0);
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
        final Cidade other = (Cidade) obj;
        if ((this.idCidade == null) ? (other.idCidade != null) : !this.idCidade.equals(other.idCidade)) {
            return false;
        }
        return true;
    }
    
}
