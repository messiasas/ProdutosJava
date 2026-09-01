package dao.conexaoDB;

import model.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProdutoDAO {

    public void inserir(Produto produto)throws SQLException {
        String sql = "INSERT INTO produto (nome, descricao, preco, quantidade, categoria_id) VALUES (?,?,?,?,?)";

        try(Connection con = ConexaoDB.getConnection();
            PreparedStatement stm = con.prepareStatement(sql)){

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

}
