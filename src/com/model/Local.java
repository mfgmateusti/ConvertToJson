package com.model;

import java.util.Objects;

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
    public int hashCode()
    {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.nome);
        hash = 37 * hash + Objects.hashCode(this.sigla);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Local other = (Local) obj;
        if (!Objects.equals(this.nome, other.sigla))
        {
            return false;
        }
        if (!Objects.equals(this.nome, other.sigla))
        {
            return false;
        }
        return true;
    }
}
