package com.entrega2;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TelaEdicaoClienteController {

    @FXML
    private TextField nomeField;

    @FXML
    private TextField idadeField;

    @FXML
    private TextField sexoField;

    @FXML
    private TextField profissaoField;

    @FXML
    private Button salvarButton;

    private Cliente clienteSelecionado;
    private Stage stage;

    public void initData(Cliente clienteSelecionado) {
        this.clienteSelecionado = clienteSelecionado;
        nomeField.setText(clienteSelecionado.nome);
        idadeField.setText(clienteSelecionado.idade);
        sexoField.setText(clienteSelecionado.sexo);
        profissaoField.setText(clienteSelecionado.profissao);
    }

    @FXML
    void salvarEdicao() {
        String nome = nomeField.getText();
        String idade = idadeField.getText();
        String sexo = sexoField.getText();
        String profissao = profissaoField.getText();

        if (nome.isEmpty() || idade.isEmpty() || sexo.isEmpty() || profissao.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Campos vazios");
            alert.setContentText("Por favor, preencha todos os campos.");
            alert.showAndWait();
            return;
        }

        // Verificar se a idade é um número válido e positivo
        int idadeInt;
        try {
            idadeInt = Integer.parseInt(idade);
            if (idadeInt <= 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Idade inválida");
                alert.setContentText("A idade deve ser um número positivo.");
                alert.showAndWait();
                return;
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Idade inválida");
            alert.setContentText("A idade deve ser um número válido.");
            alert.showAndWait();
            return;
        }

        clienteSelecionado.nome = nome;
        clienteSelecionado.idade = idade;
        clienteSelecionado.sexo = sexo;
        clienteSelecionado.profissao = profissao;

        ConexaoBanco.EditarCliente(clienteSelecionado, clienteSelecionado.nome);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText(null);
        alert.setContentText("Cliente atualizado com sucesso.");
        alert.showAndWait();

        stage.close();
    }

    public void setStage(@SuppressWarnings("exports") Stage stage) {
        this.stage = stage;
    }
}
