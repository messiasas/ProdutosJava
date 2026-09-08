package model;

public class Categoria {
    private int id;
    private String nome;

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
