package com.soulcode.soullib.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idLivro;

    @Column(nullable = false, length = 120)
    private String titulo;

    @Column(nullable = false, length = 180)
    private String descricao;

    @Column(nullable = false, length = 80)
    private String autor;

    @Column(nullable = false, length = 40)
    private String editora;

    @Column(nullable = false)
    private Integer paginas;

    public Livro() {

    }

    public Livro(Integer idLivro, String titulo, String descricao, String autor, String editora, Integer paginas) {
        this.idLivro = idLivro;
        this.titulo = titulo;
        this.descricao = descricao;
        this.autor = autor;
        this.editora = editora;
        this.paginas = paginas;
    }

    public Integer getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(Integer idLivro) {
        this.idLivro = idLivro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public Integer getPaginas() {
        return paginas;
    }

    public void setPaginas(Integer paginas) {
        this.paginas = paginas;
    }

}