package com.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mateus on 19/03/2016.
 */
public class Disponibilidade {

    private ArrayList<HashMap<String, Integer>> locais;

    public Disponibilidade(){
        this.locais = new ArrayList<HashMap<String, Integer>>();
    }

    public ArrayList<HashMap<String, Integer>> getLocais() {
        return locais;
    }

    public void setLocais(ArrayList<HashMap<String, Integer>> locais) {
        this.locais = locais;
    }
    
    public void add(String local, String qt){
    	HashMap hash = new HashMap<String, Integer>();
    	int qtde = Integer.parseInt(qt);
    	hash.put(local, qtde);
    	this.locais.add(hash);
    }
  
}
