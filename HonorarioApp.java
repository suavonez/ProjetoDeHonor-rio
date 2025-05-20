
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;

public class HonorarioApp extends Application {

    private TextField clienteField;
    private TextField valorField;
    private TextField vencimentoField;
    private ComboBox<String> statusBox;
    private TextArea resultadoArea;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Controle de Honorários - Adcont");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label clienteLabel = new Label("Cliente:");
        GridPane.setConstraints(clienteLabel, 0, 0);

        clienteField = new TextField();
        GridPane.setConstraints(clienteField, 1, 0);

        Label valorLabel = new Label("Valor:");
        GridPane.setConstraints(valorLabel, 0, 1);

        valorField = new TextField();
        GridPane.setConstraints(valorField, 1, 1);

        Label vencimentoLabel = new Label("Vencimento:");
        GridPane.setConstraints(vencimentoLabel, 0, 2);

        vencimentoField = new TextField();
        GridPane.setConstraints(vencimentoField, 1, 2);

        Label statusLabel = new Label("Status:");
        GridPane.setConstraints(statusLabel, 0, 3);

        statusBox = new ComboBox<>();
        statusBox.getItems().addAll("Pendente", "Pago", "Vencido");
        statusBox.setValue("Pendente");
        GridPane.setConstraints(statusBox, 1, 3);

        Button salvarButton = new Button("Salvar Honorário");
        GridPane.setConstraints(salvarButton, 1, 4);
        salvarButton.setOnAction(e -> salvarHonorario());

        resultadoArea = new TextArea();
        resultadoArea.setEditable(false);
        GridPane.setConstraints(resultadoArea, 0, 5, 2, 1);

        grid.getChildren().addAll(clienteLabel, clienteField, valorLabel, valorField, vencimentoLabel, vencimentoField,
                statusLabel, statusBox, salvarButton, resultadoArea);

        Scene scene = new Scene(grid, 400, 350);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void salvarHonorario() {
        String cliente = clienteField.getText();
        String valor = valorField.getText();
        String vencimento = vencimentoField.getText();
        String status = statusBox.getValue();

        String linha = String.format("Cliente: %s | Valor: R$ %s | Vencimento: %s | Status: %s\n",
                cliente, valor, vencimento, status);

        try (FileWriter fw = new FileWriter("honorarios.txt", true)) {
            fw.write(linha);
            resultadoArea.appendText("Salvo com sucesso: " + linha);
            limparCampos();
        } catch (IOException e) {
            resultadoArea.appendText("Erro ao salvar: " + e.getMessage() + "\n");
        }
    }

    private void limparCampos() {
        clienteField.clear();
        valorField.clear();
        vencimentoField.clear();
        statusBox.setValue("Pendente");
    }
}
