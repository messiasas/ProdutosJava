package view;

import dao.conexaoDB.CategoriaDAO;
import dao.conexaoDB.ProdutoDAO;
import model.Categoria;
import model.Produto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class TelaProduto extends JFrame {

    private JTextField nome_campo;
    private JTextField descr_campo;
    private JTextField preco_campo;
    private JTextField qtd_campo;
    private JComboBox<Categoria> comboCategoria;

    private JButton add_btn;
    private JButton atua_btn;
    private JButton excluir_btn;
    private JButton limpar_btn;

    private JTable tabelaProduto;
    private DefaultTableModel modeloTabela;

    private void carregarCategoria(){
        try{
            CategoriaDAO categoriaDAO = new CategoriaDAO();
            List<Categoria> categorias = categoriaDAO.listarTodas();

            for(Categoria categoria : categorias){
                comboCategoria.addItem(categoria);
            }
        }catch(SQLException exc){
            JOptionPane.showMessageDialog(this,"Erro ao carregar categoria: "+exc.getMessage());
        }
    }

    private void carregarProdutos(){
        try{
            ProdutoDAO produtoDAO = new ProdutoDAO();
            List<Produto> produtos = produtoDAO.listarTodos();

            modeloTabela.setRowCount(0);

            for(Produto produto : produtos){ // Object é um array que guarda variados tipos de objeto
                Object[] linha = {
                    produto.getId(),
                    produto.getNome(),
                    produto.getDescricao(),
                    produto.getPreco(),
                    produto.getQuantidade(),
                    produto.getCategoria()
                };
                modeloTabela.addRow(linha);
            }

        }catch (SQLException iu){
            JOptionPane.showMessageDialog(this,"Erro ao carregar produtos: "+iu.getMessage());
        }

    }

    public TelaProduto(){
        setTitle("Cadastro de produtos");
        setSize(630, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painelFormulario = new JPanel(new GridLayout(5,2,5,5));
        JPanel btnPainel = new JPanel();

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

        String[] colunas = {"ID","Nome","Descrição","Preço","Quantidade","Categoria"};
        modeloTabela = new DefaultTableModel(colunas,0); // 0 de zero linhas
        tabelaProduto = new JTable(modeloTabela);

        JScrollPane scrollTabela = new JScrollPane(tabelaProduto);

        add_btn = new JButton("Adicionar");
        atua_btn = new JButton("Atualizar");
        excluir_btn = new JButton("Excluir");
        limpar_btn = new JButton("Limpar");

        btnPainel.add(add_btn);
        btnPainel.add(atua_btn);
        btnPainel.add(excluir_btn);
        btnPainel.add(limpar_btn);

        add(painelFormulario, BorderLayout.NORTH);
        add(scrollTabela, BorderLayout.CENTER);
        add(btnPainel, BorderLayout.SOUTH);

        carregarCategoria();
    }
}
