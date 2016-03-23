package com.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.model.*;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Created by Mateus on 19/03/2016.
 */
public class MainTest01 {

	public static void main(String[] args) {

		List<Livro> livros = new ArrayList<Livro>();
		String url = "http://babao.dr.ufu.br:8080/search/query?term_1=haskell&theme=mobile";
		Document document = null;

		try {
			document = Jsoup.connect(url).get();
		} catch (IOException e){
			e.printStackTrace();
		}

		for (Element elemTitle : document.getElementsByClass("title")){
			Livro livro = new Livro();
			
			String titulo = elemTitle.childNode(0).toString();
			int end = titulo.indexOf(" /");
			if (end != -1) {
				titulo = elemTitle.text().substring(0, end);
			}
			livro.setTitulo(titulo);
			Element parent = elemTitle.parent();
			Elements authors = parent.getElementsByClass("author");
			for (Element author : authors) {

				String sAuthor = author.text();

				end = author.text().indexOf('1');
				if (end != -1) {
					sAuthor = sAuthor.substring(0, end);
				}
				livro.setAutor(new Autor(sAuthor));

			}
			Elements tags = parent.getElementsByClass("itemFields").get(0).getElementsByTag("span");
			for (Element tag : tags) {
				String atributo = tag.parent().parent().parent().getElementsByClass("label").get(0).text();
				String valor = tag.childNode(0).toString();

				if(atributo.equals("Número de chamada")){
					livro.setNumeroChamada(valor);
				}
				else if(atributo.equals("Editora")){
					livro.setEditor(new Editor(valor));
				}
				else if(atributo.equals("Ano")){
					livro.setAno(Integer.parseInt(valor));
				}
				else if(atributo.equals("Edição")){
					livro.setEdicao(valor);
				}
				else if(atributo.equals("Descrição física")){
					livro.setDescricaoFisica(valor);
				}
				
			}

			try {
				Elements disps = parent.parent().getElementsByClass("search-availability");
				Element parentDisp = disps.get(0).getElementsByClass("availabilityTotal").get(0);
				String disponibilidade = parentDisp.getElementsByTag("span").get(0).childNode(0).toString()
						.replace(" em", "");
				int disponivel = 0;
				if (disponibilidade.toLowerCase().contains("nenhuma")) {
					disponivel = 0;
				} else {
					int fimDis = (disponibilidade.indexOf("cópia"));
					if (fimDis == -1) {
						fimDis = (disponibilidade.indexOf("copia"));
					}
					fimDis--;
					disponivel = Integer.parseInt(disponibilidade.substring(0, fimDis));
				}
				
				Elements locaisDisp = parentDisp.parent().getElementsByTag("li");
				for (Element locais : locaisDisp) {
					String local = locais.getElementsByClass("availabilityLocation").get(0).childNode(0).toString();
					String qtde = locais.getElementsByClass("availabilityCount").get(0).childNode(0).toString();

					int fimLocal = local.indexOf('-');
					if (fimLocal != -1) {
						local = local.substring(0, fimLocal - 1);
					}
					int fimQtde = qtde.indexOf(" ");
					if (fimQtde != -1) {
						qtde = qtde.substring(1, fimQtde);
					}
					
					Disponibilidade disp = livro.getDisponibilidade();
					disp.add(local, qtde);
					livro.setDisponibilidade(disp);
					
				}
				
				livros.add(livro);
				
			} catch (NullPointerException ex) {
				ex.printStackTrace();				
			}
		}
		
		Gson g = new GsonBuilder().create();
		String json = g.toJson(livros);
		System.out.println(json);
	}
}
