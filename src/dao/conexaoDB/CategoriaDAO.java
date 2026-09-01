package dao.conexaoDB;

import model.Categoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    public List<Categoria> listarTodas() throws SQLException {
        String sql = "SELECT * FROM categoria ORDER BY nome";

        List<Categoria> categorias = new ArrayList<>();

        try(Connection connection = ConexaoDB.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet res = stmt.executeQuery()){

            while(res.next()){
                int id = res.getInt("id");
                String nome = res.getString("nome");

                Categoria categoria = new Categoria(id,nome);
                categorias.add(categoria);
            }
        }
        return categorias;
    }

}
