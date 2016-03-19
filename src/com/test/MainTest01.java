package com.test;

import com.google.gson.Gson;
import com.model.*;

/**
 * Created by Mateus on 19/03/2016.
 */
public class MainTest01 {

    public static void main(String [] args){

        Local local = new Local("MON", "Santa Mônica");
        Autor autor = new Autor("Mussen, Paul Henry");
        Editor editor = new Editor("Zahar");
        Disponibilidade disponibilidade = new Disponibilidade(local, 10);
        Livro livro = new Livro();
        livro.setTitulo("O desenvolvimento psicologico da criança");
        livro.setAutor(autor);
        livro.setNumeroChamada("159.922 M989pP 8.ed");
        livro.setEditor(editor);
        livro.setAno(1978);
        livro.setEdicao("8. ed. rev. e aum");
        livro.setDescricaoFisica("148p");
        livro.setDisponibilidade(disponibilidade);

        Gson g = new Gson();
        String json = g.toJson(livro);
        System.out.println(json);

    }
}
