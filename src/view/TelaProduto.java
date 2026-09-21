package view;

import dao.conexaoDB.CategoriaDAO;
import dao.conexaoDB.ProdutoDAO;
import model.Categoria;
import model.Produto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
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

    private int idSelecionado;

    ProdutoDAO produtoDAO = new ProdutoDAO();

    private void configurarSelecaoTabela() {
        tabelaProduto.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabelaProduto.getSelectedRow() != -1) {

                int linha = tabelaProduto.getSelectedRow();

                idSelecionado = (int) modeloTabela.getValueAt(linha, 0);
                String nome = (String) modeloTabela.getValueAt(linha, 1);
                String descricao = (String) modeloTabela.getValueAt(linha, 2);

                BigDecimal preco = (BigDecimal) modeloTabela.getValueAt(linha, 3);
                int quantidade = (int) modeloTabela.getValueAt(linha, 4);
                Categoria categoriaDaLinha = (Categoria) modeloTabela.getValueAt(linha, 5);

                nome_campo.setText(nome);
                descr_campo.setText(descricao);
                preco_campo.setText(preco.toString());
                qtd_campo.setText(String.valueOf(quantidade));

                for (int i = 0; i < comboCategoria.getItemCount(); i++) {
                    Categoria c = comboCategoria.getItemAt(i);
                    if (c.getId() == categoriaDaLinha.getId()) {
                        comboCategoria.setSelectedIndex(i);
                        break;
                    }
                }
            }
        });
    }

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

    private void atualizarTabela(){
        DefaultTableModel model = (DefaultTableModel) tabelaProduto.getModel();
        model.setRowCount(0); // remove todas as linhas antes de popular

        try{
            List<Produto> produtos = produtoDAO.listarTodos();
            for(Produto p : produtos) {
                model.addRow(new Object[]{
                    p.getId(),
                    p.getNome(),
                    p.getDescricao(),
                    p.getPreco(),
                    p.getQuantidade(),
                    p.getCategoria()
                });
            }
        }catch (SQLException e){
            JOptionPane.showMessageDialog(this,"Erro ao atualizar tabela: "+e.getMessage());
        }
    }

    private void limparDados(){
        nome_campo.setText("");
        descr_campo.setText("");
        preco_campo.setText("");
        qtd_campo.setText("");
        comboCategoria.setSelectedIndex(0);
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
        add_btn.addActionListener(e ->{
            try{
                String nome = nome_campo.getText().toString().trim();
                String descricao = descr_campo.getText().toString().trim();
                BigDecimal preco = new BigDecimal(preco_campo.getText().trim());
                int qtd = Integer.parseInt(qtd_campo.getText().toString().trim());
                Categoria categoria = (Categoria ) comboCategoria.getSelectedItem();

                Produto produto = new Produto(nome, descricao, preco, qtd, categoria);

                produtoDAO.inserir(produto);

                carregarProdutos();
                limparDados();

            }catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(this,"Falha ao inserir os dados: ","Erro: ", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this,"Falha ao inserir: "+ ex.getMessage());
            }
        });

        atua_btn = new JButton("Atualizar");

        atua_btn.addActionListener(e -> {
            if (tabelaProduto.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um produto para atualizar.");
                return;
            }

            try {
                String nome = nome_campo.getText().trim();
                String descricao = descr_campo.getText().trim();
                BigDecimal preco = new BigDecimal(preco_campo.getText().trim());
                int qtd = Integer.parseInt(qtd_campo.getText().trim());
                Categoria categoria = (Categoria) comboCategoria.getSelectedItem();

                Produto produto = new Produto(idSelecionado, nome, descricao, preco, qtd, categoria);

                produtoDAO.atualizar(produto);

                JOptionPane.showMessageDialog(this, "Atualizado");

                carregarProdutos();
                limparDados();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Preço ou quantidade inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Falha ao atualizar: " + ex.getMessage());
            }
        });

        excluir_btn = new JButton("Excluir");

        excluir_btn.addActionListener(e -> {
            if (tabelaProduto.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um produto para excluir.");
                return;
            }

            int confirmacao = JOptionPane.showConfirmDialog(
                    this,
                    "Deseja realmente excluir este produto?",
                    "Confirmar exclusão",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmacao == JOptionPane.YES_OPTION) {
                try {
                    produtoDAO.excluir(idSelecionado);
                    carregarProdutos();
                    limparDados();

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Falha ao excluir: " + ex.getMessage());
                }
            }
        });

        limpar_btn = new JButton("Limpar");

        btnPainel.add(add_btn);
        btnPainel.add(atua_btn);
        btnPainel.add(excluir_btn);
        btnPainel.add(limpar_btn);

        add(painelFormulario, BorderLayout.NORTH);
        add(scrollTabela, BorderLayout.CENTER);
        add(btnPainel, BorderLayout.SOUTH);

        configurarSelecaoTabela();
        carregarCategoria();
        carregarProdutos();

    }
}
