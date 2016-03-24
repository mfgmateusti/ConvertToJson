package com.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.controller.PesquisaController;

public class PesquisaTeste {

	public static void main(String[] args) {
		
		PesquisaController c = new PesquisaController();
		
		Map<String, String> parametros = new HashMap<String, String>();
		parametros.put("TERMO", "haskell");
		parametros.put("IDIOMA", "eng");
		
		System.out.println(c.constroiUrl(parametros));
		try {
			c.efetuaPesquisa();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(c.retornaGson());

	}

}
