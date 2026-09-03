package dao.conexaoDB;

import model.Categoria;
import model.Produto;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    public void inserir(Produto produto)throws SQLException {
        String sql = "INSERT INTO produto (nome, descricao, preco, quantidade, categoria_id) VALUES (?,?,?,?,?)";

        try(Connection con = ConexaoDB.getConnection();
            PreparedStatement stm = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){

            stm.setString(1, produto.getNome());
            stm.setString(2,produto.getDescricao());
            stm.setBigDecimal(3, produto.getPreco());
            stm.setInt(4, produto.getQuantidade());
            stm.setInt(5, produto.getCategoria().getId());

            stm.executeUpdate();

            try(ResultSet generatedKeys = stm.getGeneratedKeys()){
                if(generatedKeys.next()){
                    produto.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public List<Produto> listarTodos() throws SQLException{

        String sql = "SELECT p.id, p.nome, p.descricao, p.preco, p.quantidade, " +
                "c.id AS categoria_id, c.nome AS categoria_nome " +
                "FROM produto p " +
                "JOIN categoria c ON p.categoria_id = c.id " +
                "ORDER BY p.nome";

        List<Produto> produtos = new ArrayList<>();

        try(Connection con = ConexaoDB.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet res = st.executeQuery()){

            while(res.next()){
                Categoria categoria = new Categoria(
                        res.getInt("categoria_id"),
                        res.getString("categoria_nome")
                );

                Produto produto = new Produto(
                        res.getInt("id"),
                        res.getString("nome"),
                        res.getString("descricao"),
                        res.getBigDecimal("preco"),
                        res.getInt("quantidade"),
                        categoria
                );

                produtos.add(produto);
            }
        }
        return produtos;
    }

    public void atualizar(Produto produto) throws SQLException{
        String sql = "UPDATE produto SET nome = ?, descricao = ?, preco = ?, quantidade = ?, categoria_id = ? WHERE id = ?";

        try(Connection con = ConexaoDB.getConnection();
        PreparedStatement st = con.prepareStatement(sql)){

            st.setString(1, produto.getNome());
            st.setString(2, produto.getDescricao());
            st.setBigDecimal(3, produto.getPreco());
            st.setInt(4, produto.getQuantidade());
            st.setInt(5,produto.getCategoria().getId());
            st.setInt(6, produto.getId());

            st.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException{
        String sql = "DELETE FROM produto WHERE id = ?";

        try(Connection con = ConexaoDB.getConnection();
        PreparedStatement stm = con.prepareStatement(sql)){

            stm.setInt(1, id);
            stm.executeUpdate();

        }
    }
}
