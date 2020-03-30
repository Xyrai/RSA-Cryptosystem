package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Controller {

    private BigInteger p;
    private BigInteger q;
    private BigInteger n;
    private BigInteger e;
    private BigInteger d;
    private BigInteger TWO = new BigInteger(("2"));

    @FXML
    private Label labelP;

    @FXML
    private Label labelQ;

    @FXML
    private Label labelE;

    @FXML
    private Label labelTimePQ;

    @FXML
    private Text textP;

    @FXML
    private Text textQ;

    @FXML
    private Text textE;

    @FXML
    private Text textTime;

    @FXML
    private TextField textFieldN;

    @FXML
    private TextField textFieldM;


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
    private void getPQ() {
        // Check if value N field is empty
        if (textFieldN.getText().trim().isEmpty() || textFieldN.getText() == null) {
            return;
        }

        n = new BigInteger(textFieldN.getText());

        // List to store p and q values
        List<BigInteger> listPQ = new ArrayList<>();

        long startTime = System.currentTimeMillis();
        calculatePQ(listPQ);
        long endTime = System.currentTimeMillis();

        // Total time spend calculating p and q
        long totalTime = endTime - startTime;

        // Retrieve p and q for output
        p = listPQ.get(0);
        q = listPQ.get(1);

        initStep1Text();

        labelP.setText(p.toString());
        labelQ.setText(q.toString());
        labelTimePQ.setText(totalTime + "ms");
    }

    // Set text of Step 1 output to visible
    private void initStep1Text() {
        textP.setVisible(true);
        textQ.setVisible(true);
        textTime.setVisible(true);
    }

    private void calculatePQ(List<BigInteger> listPQ) {
        // Start the index on 2
        BigInteger index = new BigInteger(("2"));

        // Loop while n mod i != 0
        while (n.compareTo(index) > 0) {
            while (n.mod(index).equals(BigInteger.ZERO)) {
                // Add p to the list
                listPQ.add(index);
                n = n.divide((index));
            }
            index = index.add(BigInteger.ONE);
        }
        // Add q to the list
        if (n.compareTo(TWO) > 0) {
            listPQ.add(n);
        }
    }
}
