package com.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.model.Autor;
import com.model.Disponibilidade;
import com.model.Editor;
import com.model.Livro;
import com.model.Pesquisa;

public class PesquisaController extends BaseController{
	
	Pesquisa pesquisa = new Pesquisa();
	String url = "";
		
	public Pesquisa getPesquisa() {
		if(pesquisa == null)
			pesquisa = new Pesquisa();
		return pesquisa;
	}
	public void setPesquisa(Pesquisa pesquisa) {
		this.pesquisa = pesquisa;
	}

	public String constroiUrl(Map<String, String> parametros){
		
		pesquisa.setParametros(parametros);
		
		String base= "http://babao.dr.ufu.br:8080/";
		String termo = "";
		String autor = "";
		String ordem = "";
		String localizacao = "";
		String idioma = "";
		
		if(parametros.isEmpty()){
			System.out.println("Sem parâmetros - Retornando pesquisa completa");
			url = base;
		}
		else{
			
			url = base;
			url += "search/query?";

			if(parametros.containsKey("TERMO")){
				termo = parametros.get("TERMO");
				url += "term_1="+termo;
			}
			if(parametros.containsKey("AUTOR")){
				autor = parametros.get("AUTOR");
				url += "&facet_author="+autor;
			}
			if(parametros.containsKey("ORDEM")){
				ordem = parametros.get("ORDEM");
				url += "&sort="+ordem;
			}
			if(parametros.containsKey("LOCALIZACAO")){
				localizacao = parametros.get("LOCALIZACAO");
				url += "&facet_loc="+localizacao;
			}
			if(parametros.containsKey("IDIOMA")){
				idioma = parametros.get("IDIOMA");
				url += "&facet_lang="+idioma;
			}
			if(url.equals(base+"search/query?")){
				url = base;
			}
		}
		
		return url;
		
		
	}
	public void efetuaPesquisa() throws IOException{
		Document document = null;
		document = Jsoup.connect(url).get();
		
		if(document == null){
			throw new IOException("Pesquisa não retornou nenhum resultado!");
		}
		pesquisa.setResultado(document);
	}
	public String retornaGson(){
		
		Document document = pesquisa.getResultado();
		List<Livro> livros = new ArrayList<Livro>();
		
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
				livro.setQuantidadeDisponivel(disponivel);
				
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
		return json;
	}

}
