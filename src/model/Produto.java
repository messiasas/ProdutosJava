package model;

import java.math.BigDecimal;

public class Produto {
    private int id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private int quantidade;
    private Categoria categoria;

    public Produto(){

    }
    public Produto(int id, String nome, String descricao, BigDecimal preco, int quantidade, Categoria categoria){
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidade = quantidade;
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public Categoria getCategoria() {
        return categoria;
    }

}
