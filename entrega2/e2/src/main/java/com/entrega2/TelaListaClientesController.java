package com.entrega2;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class TelaListaClientesController {

    @FXML
    private ListView<Cliente> listView;

    public void initListaCliente(ObservableList<Cliente> clientes) {
        if (listView == null) {
            throw new IllegalStateException("ListView is not injected: check your FXML file 'TelaListaClientes.fxml'.");
        }

        listView.setItems(clientes);
        listView.setCellFactory(param -> new ListCell<Cliente>() {
            @Override
            protected void updateItem(Cliente cliente, boolean empty) {
                super.updateItem(cliente, empty);
                if (empty || cliente == null) {
                    setGraphic(null);
                } else {
                    TextFlow textFlow = new TextFlow();
                    
                    Text nome = new Text("Nome: ");
                    nome.setStyle("-fx-font-weight: bold");

                    Text textoNome = new Text(cliente.nome + " - ");

                    Text idade = new Text("Idade: ");
                    idade.setStyle("-fx-font-weight: bold");

                    Text textoIdade = new Text(cliente.idade + "\n");

                    Text sexo = new Text("Sexo: ");
                    sexo.setStyle("-fx-font-weight: bold");

                    Text textoSexo = new Text(cliente.sexo + " - ");

                    Text profissao = new Text("Profissão: ");
                    profissao.setStyle("-fx-font-weight: bold");

                    Text textoProfissao = new Text(cliente.profissao);

                    textFlow.getChildren().addAll(nome, textoNome, idade, textoIdade, sexo, textoSexo, profissao, textoProfissao);
                    textFlow.setTextAlignment(TextAlignment.CENTER);
                    setGraphic(textFlow);
                }
            }
        });
    }

    @FXML
    void excluirCliente() {
        Cliente clienteSelecionado = listView.getSelectionModel().getSelectedItem();
        if (clienteSelecionado != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação");
            alert.setHeaderText("Tem certeza que deseja excluir este cliente?");
            alert.setContentText("Esta ação não pode ser desfeita.");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    ConexaoBanco.deletarCliente(clienteSelecionado);
                    listView.getItems().remove(clienteSelecionado);
                }
            });
        }
    }

    @FXML
void editarCliente() {
    Cliente clienteSelecionado = listView.getSelectionModel().getSelectedItem();
    if (clienteSelecionado != null) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TelaEditarCliente.fxml"));
            Parent root = loader.load();

            TelaEdicaoClienteController controller = loader.getController();
            controller.initData(clienteSelecionado);

            Stage stage = new Stage();
            controller.setStage(stage);
            stage.setTitle("Editar cliente");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Recarregar a lista de clientes após a edição
            List<Cliente> clientesAtualizados = ConexaoBanco.PegarCliente();
            ObservableList<Cliente> observableClientesAtualizados = javafx.collections.FXCollections.observableArrayList(clientesAtualizados);
            initListaCliente(observableClientesAtualizados);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

}
