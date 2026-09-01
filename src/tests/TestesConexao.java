package tests;

import dao.conexaoDB.ConexaoDB;

import java.sql.Connection;
import java.sql.SQLException;

public class TestesConexao{

    public static void main(String[] args){
        try{
            Connection con = ConexaoDB.getConnection();
            System.out.println("Sucesso na conexao.");
        }catch(SQLException e){
            System.out.println("Falha na conexao: "+ e.getMessage());
        }
    }
}