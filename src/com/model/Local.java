package com.model;

/**
 * Created by Mateus on 19/03/2016.
 */
public class Local {

    private String sigla;
    private String nome;

    public Local(String sigla, String nome){
        this.sigla = sigla;
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
