package view;

import model.Categoria;

import javax.swing.*;
import java.awt.*;

public class TelaProduto extends JFrame {

    private JTextField nome_campo;
    private JTextField descr_campo;
    private JTextField preco_campo;
    private JTextField qtd_campo;

    private JComboBox<Categoria> comboCategoria;

    public TelaProduto(){
        setTitle("Cadastro de produtos");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painelFormulario = new JPanel(new GridLayout(5,2,5,5));

        painelFormulario.add(new JLabel("Nome: "));
        nome_campo = new JTextField();
        painelFormulario.add(nome_campo);

        painelFormulario.add(new JLabel("Descrição: "));
        descr_campo = new JTextField();
        painelFormulario.add(descr_campo);

        painelFormulario.add(new JLabel("Preço: "));
        preco_campo = new JTextField();
        painelFormulario.add(preco_campo);

        painelFormulario.add(new JLabel("Quantidade: "));
        qtd_campo = new JTextField();
        painelFormulario.add(qtd_campo);

        painelFormulario.add(new JLabel("Categoria: "));
        comboCategoria = new JComboBox<>(); // o combo vai guardar os objetos Categoria inteiros, nao somente o texto do nome
                                            // Por isso que fizemos o toString(), quando o combo for popular de verdade, ele vai exibir
                                            // categoria.toString (o nome), mas por baixo dos panos continua guardando o objeto completo, com o id junto
        painelFormulario.add(comboCategoria);

        add(painelFormulario, BorderLayout.NORTH);

    }
}
