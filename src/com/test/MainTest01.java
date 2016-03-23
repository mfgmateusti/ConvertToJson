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

		/*List<Livro> livros = new ArrayList<Livro>();

		Local local = new Local("MON", "Santa MÃ´nica");
		Autor autor = new Autor("Mussen, Paul Henry");
		Editor editor = new Editor("Zahar");
		Disponibilidade disponibilidade = new Disponibilidade("MON", 10);
		Livro livro = new Livro();
		livro.setTitulo("O desenvolvimento psicologico da crianÃ§a");
		livro.setAutor(autor);
		livro.setNumeroChamada("159.922 M989pP 8.ed");
		livro.setEditor(editor);
		livro.setAno(1978);
		livro.setEdicao("8. ed. rev. e aum");
		livro.setDescricaoFisica("148p");
		livro.setDisponibilidade(disponibilidade);
		
		livros.add(livro);

		Gson g = new GsonBuilder().create();
		String json = g.toJson(livros);
		System.out.println(json);*/

		List<Livro> livros = new ArrayList<Livro>();
		String url = "http://babao.dr.ufu.br:8080/search/query?term_1=haskell&theme=mobile";
		Document document = null;

		try {
			document = Jsoup.connect(url).get();
		} catch (

		IOException e)

		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
		for (Element elemTitle : document.getElementsByClass("title"))

		{
			Livro livro = new Livro();
			
			String titulo = elemTitle.childNode(0).toString();
			int end = titulo.indexOf(" /");
			if (end != -1) {
				titulo = elemTitle.text().substring(0, end);
			}
			livro.setTitulo(titulo);
			//build.append("\"titulo\":\"" + titulo + "\"");
			Element parent = elemTitle.parent();
			Elements authors = parent.getElementsByClass("author");
			for (Element author : authors) {

				String sAuthor = author.text();

				end = author.text().indexOf('1');
				if (end != -1) {
					sAuthor = sAuthor.substring(0, end);
				}
				//build.append(", \"author\":\"" + sAuthor + "\"");
				livro.setAutor(new Autor(sAuthor));
				// System.out.println(sAuthor);

			}
			Elements tags = parent.getElementsByClass("itemFields").get(0).getElementsByTag("span");
			for (Element tag : tags) {
				String atributo = tag.parent().parent().parent().getElementsByClass("label").get(0).text();
				String valor = tag.childNode(0).toString();
				// System.out.println(atributo+": "+valor);
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
				//build.append(", \"" + atributo + "\":\"" + valor + "\"");
				
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
				// System.out.println(disponibilidade);
				//build.append(", \"disponibilidade\":\"" + disponivel + "\"");
				//build.append(", \"locais\":[");
				Elements locaisDisp = parentDisp.parent().getElementsByTag("li");
				for (Element locais : locaisDisp) {
					String local = locais.getElementsByClass("availabilityLocation").get(0).childNode(0).toString();
					String qtde = locais.getElementsByClass("availabilityCount").get(0).childNode(0).toString();

					//build.append("{");
					int fimLocal = local.indexOf('-');
					if (fimLocal != -1) {
						local = local.substring(0, fimLocal - 1);
					}
					int fimQtde = qtde.indexOf(" ");
					if (fimQtde != -1) {
						qtde = qtde.substring(1, fimQtde);
					}
					// System.out.println(local);
					Disponibilidade disp = livro.getDisponibilidade();
					disp.add(local, qtde);
					livro.setDisponibilidade(disp);
					
					//build.append("\"local\":\"" + local + "\"");
					//build.append(",\"qtde\":\"" + qtde + "\"");
					// System.out.println(qtde);

					//build.append("}");
					
					
				}
				
				livros.add(livro);
				//build.append("]");
			} catch (NullPointerException ex) {
				// System.out.println("Disponibilidade Indefinida");
				//build.append(", \"disponibilidade\":\"-1\"");
				
				
			}
			//build.append("},");
			// System.out.println("---------------------------------------");
			
			
		}

		/*int ultCom = build.lastIndexOf(",");
		build.replace(ultCom, ultCom + 1, "");

		build.append("]");
		build.append("}");

		System.out.println(build);*/
		
		
		Gson g = new GsonBuilder().create();
		String json = g.toJson(livros);
		System.out.println(json);
	}
}
