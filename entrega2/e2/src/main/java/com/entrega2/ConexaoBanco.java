package com.entrega2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.ObservableList;

public class ConexaoBanco {
    private static final String URL = "jdbc:postgresql://localhost:5432/clientesdb";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    public static void CadastrarCliente(Cliente cliente) {
        String sql = "INSERT INTO cliente (nome, idade, sexo, profissao) VALUES (?,?,?,?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cliente.nome);
            pstmt.setString(2, cliente.idade);
            pstmt.setString(3, cliente.sexo);
            pstmt.setString(4, cliente.profissao);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            
        }
    }

    public static ObservableList<Cliente> PegarCliente() {
    String SQL = "SELECT * FROM cliente";
    ObservableList<Cliente> clientes = javafx.collections.FXCollections.observableArrayList();
    try (Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(SQL);
         ResultSet rs = pstmt.executeQuery()) {
        while (rs.next()) {
            Cliente cliente = new Cliente(
                rs.getString("nome"),
                rs.getString("idade"),
                rs.getString("sexo"),
                rs.getString("profissao")
            );
            clientes.add(cliente);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return clientes;
}


    public static void deletarCliente(Cliente cliente) {
        String SQL = "DELETE FROM cliente WHERE nome = ?";
        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, cliente.nome);
            pstmt.executeUpdate();
            System.out.println("Cliente deletado com sucesso.");
        } catch (SQLException ex) {
            System.out.println("Erro ao deletar cliente.");
            System.out.println(ex.getMessage());
        }
    }

    public static void EditarCliente(Cliente cliente, String nomeOriginal) {
        String SQL = "UPDATE cliente SET nome = ?, idade = ?, sexo = ?, profissao = ? WHERE nome = ?";
        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, cliente.nome);
            pstmt.setString(2, cliente.idade);
            pstmt.setString(3, cliente.sexo);
            pstmt.setString(4, cliente.profissao);
            pstmt.setString(5, nomeOriginal);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

    