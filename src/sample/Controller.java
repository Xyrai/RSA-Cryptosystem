package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    @FXML
    private Label labelP;

    @FXML
    private Label labelQ;

    @FXML
    private Label labelE;

    @FXML
    private Label labelTimePQ;

    @FXML
    private void startEncryption(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        Stage dialogStage = (Stage) source.getScene().getWindow();
        dialogStage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("encryption.fxml")));
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    @FXML
    private void startDecryption(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        Stage dialogStage = (Stage) source.getScene().getWindow();
        dialogStage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("decryption.fxml")));
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    @FXML
    private void returnHome(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        Stage dialogStage = (Stage) source.getScene().getWindow();
        dialogStage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("main.fxml")));
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    @FXML
    private void calculatePQ(ActionEvent event) {
        long startTime = System.currentTimeMillis();
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        // examples
        labelP.setText("21412421421");
        labelQ.setText("5235235233");
        labelE.setText("23523121221");
        labelTimePQ.setText(totalTime + "ms");
    }
}
