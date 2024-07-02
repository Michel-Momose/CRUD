package com.entrega2;


import java.io.IOException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TelaPrincipalController {


    private ObservableList<Cliente> listaCliente = FXCollections.observableArrayList();

    @FXML
    private TextField nomeCliente;
    
    @FXML
    private TextField idadeCliente;
    
    @FXML
    private TextField sexoCliente;

    @FXML
    private TextField profissaoCliente;

    @FXML
    private ListView<Cliente> listView;

    public void initialize() {}

    public static void exibirAlerta(String mensagem, @SuppressWarnings("exports") AlertType tipo) {
      Alert alerta = new Alert(tipo);
      alerta.setTitle("Aviso");
      alerta.setHeaderText(null);
      alerta.setContentText(mensagem);
      alerta.showAndWait();
    }

    @FXML
    void CadastrarCliente(ActionEvent event) {

        // Recuperar os valores dos campos de texto
        String nome = nomeCliente.getText();
        String idade = idadeCliente.getText();
        String sexo = sexoCliente.getText();
        String profissao = profissaoCliente.getText();

        // Verificar se todos os campos estão preenchidos
        if (nome.isEmpty() || idade.isEmpty() || sexo.isEmpty() || profissao.isEmpty()) {
            exibirAlerta("Todos os campos devem ser preenchidos!", AlertType.WARNING);
            return;
        }
       
         // Verificar se a idade é um número válido e positivo  
        int idadeInt;
        try {
            idadeInt = Integer.parseInt(idade);
            if (idadeInt <= 0) {
                exibirAlerta("A idade deve ser um número positivo!", AlertType.WARNING);
                return;
            }
        } catch (NumberFormatException e) {
            exibirAlerta("A idade deve ser um número válido!", AlertType.WARNING);
            return;
        }

        // Tentar cadastrar o cliente   
        try { 
            Cliente cliente = new Cliente(nomeCliente.getText(), idadeCliente.getText(), sexoCliente.getText(), profissaoCliente.getText());
            ConexaoBanco.CadastrarCliente(cliente);
            listaCliente.add(cliente);

            // Limpar os campos de texto apos o cadastro
            nomeCliente.setText("");
            idadeCliente.setText("");
            sexoCliente.setText("");
            profissaoCliente.setText("");             

            exibirAlerta("Cliente cadastrado com sucesso!", AlertType.INFORMATION);
        }
        catch (Exception e) {
            exibirAlerta("Erro ao cadastrar cliente!", AlertType.ERROR);
        }  
    }

    @FXML
    void verListaClientes(ActionEvent event) {
        List<Cliente> clientes = ConexaoBanco.PegarCliente();  // Corrigir para pegar a lista de clientes
        listaCliente.setAll(clientes);  // Atualizar a lista de clientes

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TelaListaClientes.fxml"));  // Corrigir o nome do arquivo FXML
            Parent root = loader.load();
            TelaListaClientesController controller = loader.getController();
            controller.initListaCliente(listaCliente);  // Passar a lista de clientes para o controlador
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Lista de Clientes Cadastrados");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

   