package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Controller {

    private BigInteger p;
    private BigInteger q;
    private BigInteger n;
    private BigInteger e;
    private BigInteger d;
    private BigInteger phiN;
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
    private Label labelEncryptedM;

    @FXML
    private Text textP;

    @FXML
    private Text textQ;

    @FXML
    private Text textE;

    @FXML
    private Text textTime;

    @FXML
    private Text textEncryptedM;

    @FXML
    private TextField textFieldN;

    @FXML
    private TextField textFieldM;

    // TODO: Add error prevention checks

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

    /**
     * Step 1 - Calculating p and q
     */
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

    // Set required text of Step 1 output to visible
    private void initStep1Text() {
        textP.setVisible(true);
        textQ.setVisible(true);
        textTime.setVisible(true);
        labelTimePQ.setVisible(true);

        textE.setVisible(false);
        textEncryptedM.setVisible(false);

        labelEncryptedM.setVisible(false);
        labelE.setVisible(false);
    }

    // Set required text of Step 2 output to visible
    private void initStep2Text() {
        textE.setVisible(true);
        labelE.setVisible(true);

        textTime.setVisible(false);
        textEncryptedM.setVisible(false);
        labelTimePQ.setVisible(false);
        labelEncryptedM.setVisible(false);
    }

    // Set required text of Step 3 output to visible
    private void initStep3Text() {
        textE.setVisible(true);
        textEncryptedM.setVisible(true);
        labelEncryptedM.setVisible(true);
        labelE.setVisible(true);

        textTime.setVisible(false);
        labelTimePQ.setVisible(false);
    }

    private void calculatePQ(List<BigInteger> listPQ) {
        // Start the index on 2
        BigInteger index = new BigInteger(("2"));

        // backup of N because this value needs to be used for the encrypted message
        // otherwise N is a totally different value than p * q
        BigInteger backupN = n;

        // Loop while n mod i != 0
        while (backupN.compareTo(index) > 0) {
            while (backupN.mod(index).equals(BigInteger.ZERO)) {
                // Add p to the list
                listPQ.add(index);
                backupN = backupN.divide((index));
            }
            index = index.add(BigInteger.ONE);
        }
        // Add q to the list
        if (backupN.compareTo(TWO) > 0) {
            listPQ.add(backupN);
        }
    }

    /**
     * Step 2 - Calculating e
     */
    @FXML
    private void calculateE() {
        phiN = getPhi();
        // e must be: 1 < e < phiN
        // Choose e, such that e should be co-prime. Co-prime means it should not multiply by factors of \phi and also not divide by \phi
        // Factors of \phi are, 20 = 5 \times 4 = 5 \times 2 \times 2 so e should not multiply by 5 and 2 and should not divide by 20.
        e = TWO;
        while (e.compareTo(phiN) < 0) {
            if (e.gcd(phiN).equals(BigInteger.ONE)) {
                break;
            } else {
                e = e.add(BigInteger.ONE);
            }
        }

        initStep2Text();
        labelE.setText(e.toString());
    }

    /**
     * Step 3 - Encrypting the message
     */
    @FXML
    private void encryptM() {
        String message = textFieldM.getText();
        StringBuilder sb = new StringBuilder();
        // encoded message
        byte[] byteValues = message.getBytes();

        for (byte byteValue : byteValues) {
            BigInteger bigIntegerValue = new BigInteger(Byte.toString(byteValue));
            bigIntegerValue = bigIntegerValue.modPow(e, n);
            sb.append(bigIntegerValue.intValue()).append(",");
        }

        initStep3Text();
        labelEncryptedM.setText(sb.toString().replaceFirst(".$", ""));
    }

    private BigInteger getPhi() {
        // phi(n) = (p-1)(q-1)
        return (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
    }
}
