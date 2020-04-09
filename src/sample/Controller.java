package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

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
    private Label labelD;

    @FXML
    private Label labelTimePQ;

    @FXML
    private Label labelEncryptedM;

    @FXML
    private Label labelDecryptedC;

    @FXML
    private Text textP;

    @FXML
    private Text textQ;

    @FXML
    private Text textE;

    @FXML
    private Text textD;

    @FXML
    private Text textError;

    @FXML
    private Text textTime;

    @FXML
    private Text textEncryptedM;

    @FXML
    private TextField textFieldN;

    @FXML
    private TextField textFieldM;

    @FXML
    private TextField textFieldNDecryption;

    @FXML
    private TextField textFieldEDecryption;

    @FXML
    private TextArea textAreaCDecryption;

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

        BigInteger q = n;

        // Loop while n mod i != 0
        while (q.compareTo(index) > 0) {
            while (q.mod(index).equals(BigInteger.ZERO)) {
                // Add p to the list
                listPQ.add(index);
                //  calculate q, q = n / p
                q = q.divide((index));
            }
            index = index.nextProbablePrime();
        }
        // Add q to the list if value of q is bigger than 2
        if (q.compareTo(TWO) > 0) {
            listPQ.add(q);
        }
    }

    /**
     * Encryption
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

    /**
     * Encryption
     * Step 2 - Calculating e
     */
    @FXML
    private void calculateE() {
        if (p.equals(null) || q.equals(null)) {
            return;
        }

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
     * Encryption
     * Step 3 - Encrypting the message
     */
    @FXML
    private void encryptM() {
        if (textFieldM.getText().trim().isEmpty() || textFieldM.getText() == null) {
            return;
        }

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

    /**
     * Calculate phi(n)
     * phi(n) = (p-1)(q-1)
     */
    private BigInteger getPhi() {
        return (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
    }

    /**
     * Decryption
     * Step 1 - Calculating d
     */
    @FXML
    private void calculateD() {
        textD.setVisible(false);
        labelD.setVisible(false);
        // Set text to invisible for Step 1 repetition
        if (textFieldNDecryption.getText().trim().isEmpty() || textFieldNDecryption.getText() == null || textFieldEDecryption.getText().trim().isEmpty() || textFieldEDecryption.getText() == null) {
            labelDecryptedC.setVisible(true);
            labelDecryptedC.setText("No value given for N or E!");
            return;
        }
        labelDecryptedC.setVisible(false);

        n = new BigInteger(textFieldNDecryption.getText());
        e = new BigInteger(textFieldEDecryption.getText());

        List<BigInteger> listPQ = new ArrayList<>();

        calculatePQ(listPQ);

        p = listPQ.get(0);
        q = listPQ.get(1);

        phiN = getPhi();
        // Catch error when there is an invalid value for E
        try {
            d = e.modInverse(phiN);
        } catch (ArithmeticException e) {
            textError.setVisible(true);
            textError.setText("Pick another value for E!");
            return;
        }
        textError.setVisible(false);
        textD.setVisible(true);
        labelD.setVisible(true);

        labelD.setText(d.toString());
    }

    /**
     * Decryption
     * Step 2 - Decrypting the message
     */
    @FXML
    private void decryptMessage() {
        labelDecryptedC.setVisible(true);

        if (textFieldNDecryption.getText().trim().isEmpty() || textFieldNDecryption.getText() == null || textFieldEDecryption.getText().trim().isEmpty() || textFieldEDecryption.getText() == null) {
            labelDecryptedC.setVisible(true);
            labelDecryptedC.setText("No value given for N or E!");
            return;
        }

        if (textAreaCDecryption.getText().trim().isEmpty() || textAreaCDecryption.getText() == null) {
            labelDecryptedC.setText("No cipher text given!");
            return;
        }

        StringBuilder sb = new StringBuilder();
        String c = textAreaCDecryption.getText();
        String[] stringValuesArray = c.split(",");
        byte[] byteValuesArray = new byte[stringValuesArray.length];
        BigInteger bigIntegerValue;

        for (int i = 0; i < stringValuesArray.length; i++) {
            bigIntegerValue = new BigInteger(stringValuesArray[i]);
            bigIntegerValue = bigIntegerValue.modPow(d, n);
            byteValuesArray[i] = bigIntegerValue.byteValueExact();
        }

        sb.append("Message after decryption is: ").append(new String(byteValuesArray));
        labelDecryptedC.setText(sb.toString());
    }
}
