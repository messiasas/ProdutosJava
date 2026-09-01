package model;

public class Categoria {
    private int id;
    private String nome;

    // Criamos construtores vazios para não ter a necessidade de sempre ter os dados
    // nas mãos para criar um objeto. Pense por exemplo quando o usuario vai preencher os campos aos poucos:

    /*
    *
    * Produto prod = new Produto();
    * prod.setName(campoNome.getString());
    * prod.setDesricao(campoDescricao.getString());
    * prod.setPreco(campoPreco.getString());
    * */

    // Além disso existem bibliotecas que exigem construtores vazios, pois usam o objeto em branco
    // para depois usam os setters para preencher campo por campo.


    public Categoria(){

    }
    public Categoria(int id, String nome){
        this.id = id;
        this.nome = nome;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String nome(){
        return nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }

    // Mais para frente vamos usar para quando o Swing chamar na interface
    // não apareça o hash do objeto, como Categoria@1d73s821, mas sim "Eletrônicos"
    @Override
    public String toString(){
        return nome;
    }
}
