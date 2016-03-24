package com.model;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Document;

public class Pesquisa {

	public Map<String, String> parametros;
	public Document resultado;
	public Map<String, String> getParametros() {
		return parametros;
	}
	public void setParametros(Map<String, String> parametros) {
		if(parametros == null)
			parametros = new HashMap<String, String>();
		this.parametros = parametros;
	}
	public Document getResultado() {
		return resultado;
	}
	public void setResultado(Document resultado) {
		this.resultado = resultado;
	}
}
