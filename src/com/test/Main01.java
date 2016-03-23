package com.test;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main01 {

	public static void main(String[] args) {
		
		String url = "http://babao.dr.ufu.br:8080/search/query?term_1=haskell&theme=mobile";
        Document document = null;
		try {
			document = Jsoup.connect(url).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringBuilder build = new StringBuilder();
		build.append("{");
		build.append("\"books\":[");
		
		
		for(Element elemTitle : document.getElementsByClass("title")){
			build.append("{");
			String titulo = "";
			titulo = elemTitle.childNode(0).toString();
			int end = titulo.indexOf(" /");
			if(end != -1){
				titulo = elemTitle.text().substring(0, end);
			}
			build.append("\"titulo\":\""+titulo+"\"");
			Element parent = elemTitle.parent();
			Elements authors = parent.getElementsByClass("author");
			for(Element author : authors){
				
				String sAuthor = author.text();
				
				end = author.text().indexOf('1');
				if(end != -1){
					sAuthor = sAuthor.substring(0, end);
				}
				build.append(", \"author\":\""+sAuthor+"\"");
				//System.out.println(sAuthor);
				
			}
			Elements tags = parent.getElementsByClass("itemFields").get(0).getElementsByTag("span");
			for(Element tag : tags){
				String atributo = tag.parent().parent().parent().getElementsByClass("label").get(0).text();
				String valor = tag.childNode(0).toString();
				//System.out.println(atributo+": "+valor);
				build.append(", \""+atributo+"\":\""+valor+"\"");
			}
			
			try{
				Elements disps = parent.parent().nextElementSibling().getElementsByClass("search-availability");
				Element parentDisp = disps.get(0).getElementsByClass("availabilityTotal").get(0);
				String disponibilidade = parentDisp.getElementsByTag("span").get(0).childNode(0).toString().replace(" em", "");
				int disponivel = 0;
				if(disponibilidade.toLowerCase().contains("nenhuma")){
					disponivel = 0;
				}else{
					int fimDis = (disponibilidade.indexOf("cópia"));
					if (fimDis == -1){
						fimDis = (disponibilidade.indexOf("copia"));
					}
					fimDis--;
					disponivel = Integer.parseInt(disponibilidade.substring(0, fimDis));
				}
				//System.out.println(disponibilidade);
				build.append(", \"disponibilidade\":\""+disponivel+"\"");
				build.append(", \"locais\":[");
				Elements locaisDisp = parentDisp.parent().getElementsByTag("li");
				for(Element locais : locaisDisp){
					String local = locais.getElementsByClass("availabilityLocation").get(0).childNode(0).toString();
					String qtde = locais.getElementsByClass("availabilityCount").get(0).childNode(0).toString();
					
					
					build.append("{");
					int fimLocal = local.indexOf('-');
					if(fimLocal != -1){
						local = local.substring(0, fimLocal-1);
					}
					int fimQtde = qtde.indexOf(" ");
					if(fimQtde != -1){
						qtde = qtde.substring(1, fimQtde);
					}
					//System.out.println(local);
					build.append("\"local\":\""+local+"\"");
					build.append(",\"qtde\":\""+qtde+"\"");
					//System.out.println(qtde);
					
					build.append("}");
				}
				build.append("]");
			}
			catch(NullPointerException ex){
				//System.out.println("Disponibilidade Indefinida");
				build.append(", \"disponibilidade\":\"-1\"");
			}
			build.append("},");
			//System.out.println("---------------------------------------");
		}
		int ultCom = build.lastIndexOf(",");
		build.replace(ultCom, ultCom+1, "");
		
		build.append("]");
		build.append("}");
		
		System.out.println(build);
	}
	
	/*while(endTitulo != -1){
	author = author.substring(0, endTitulo);
	endTitulo = author.lastIndexOf('1');
}
System.out.println(titulo+";"+author);*/

}
