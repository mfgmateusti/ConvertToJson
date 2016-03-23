package com.model;

/**
 * Created by Mateus on 19/03/2016.
 */
public class Livro {

    private String titulo;
    private Autor autor;
    private String numeroChamada;
    private Editor editor;
    private int ano;
    private String edicao;
    private String descricaoFisica;
    private int quantidadeDisponivel;
    private Disponibilidade disponibilidade;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor == null ? new Autor("") : autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getNumeroChamada() {
        return numeroChamada;
    }

    public void setNumeroChamada(String numeroChamada) {
        this.numeroChamada = numeroChamada;
    }

    public Editor getEditor() {
    	return editor == null ? new Editor("") : editor;
    }

    public void setEditor(Editor editor) {
        this.editor = editor;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getEdicao() {
        return edicao;
    }

    public void setEdicao(String edicao) {
        this.edicao = edicao;
    }

    public String getDescricaoFisica() {
        return descricaoFisica;
    }

    public void setDescricaoFisica(String descricaoFisica) {
        this.descricaoFisica = descricaoFisica;
    }
    

    public int getQuantidadeDisponivel() {
		return quantidadeDisponivel;
	}

	public void setQuantidadeDisponivel(int quantidadeDisponivel) {
		this.quantidadeDisponivel = quantidadeDisponivel;
	}

	public Disponibilidade getDisponibilidade() {
    	return disponibilidade == null ? new Disponibilidade() : disponibilidade;
    }

    public void setDisponibilidade(Disponibilidade disponibilidade) {
        this.disponibilidade = disponibilidade;
    }
}
