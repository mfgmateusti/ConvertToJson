package com.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mateus on 19/03/2016.
 */
public class Disponibilidade {

    private Map<Local, Integer> locais;

    public Disponibilidade(Local local, int qtdeLivros){
        this.locais = new HashMap<Local, Integer>();
        this.locais.put(local, qtdeLivros);
    }

    public Map<Local, Integer> getLocais() {
        return locais;
    }

    public void setLocais(Map<Local, Integer> locais) {
        this.locais = locais;
    }
}
