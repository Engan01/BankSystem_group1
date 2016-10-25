package banksystem;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author asanilssonenglund
 */
public class FXMLDocumentController implements Initializable {

    private BankLogic b;

    private ObservableList<String> oList;
    
    @FXML
    private TextField ssnField;
    
    @FXML
    private Label statusLabel;
    
    @FXML
    private ListView customersList;
    
    @FXML
    private Button addCustomerButton;

    @FXML
    private Button deleteCustomerButton;
    
    @FXML
    public void viewProfile(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("scene2.fxml"));
        Scene s1 = new Scene(root);
        Stage stg = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stg.setScene(s1);
        stg.show();

    }

    // search method
    @FXML
    public void search(ActionEvent event) throws IOException {
        String str = ssnField.getText();
        Boolean ok = b.searchCustomer(Long.parseLong(str));

        if (ok) {
            b.getCustomer(Long.parseLong(str));
        } else {
            statusLabel.setText("This customer doesn't exist in the system!");
        }
    }
    @FXML
    private void addCustomer(ActionEvent event) throws IOException {
        
        Stage stage;
        Parent root;

        stage = new Stage();
        root = FXMLLoader.load(getClass().getResource("FXMLpopUp1.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(addCustomerButton.getScene().getWindow());
        stage.showAndWait();
    }
    @FXML
    private void deleteCustomer(ActionEvent event) throws IOException {

        Stage stage;
        Parent root;

        stage = new Stage();
        root = FXMLLoader.load(getClass().getResource("FXMLpopUp2.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(deleteCustomerButton.getScene().getWindow());
        stage.showAndWait();
    }
    @FXML
    private void confirmPop2(ActionEvent event) {

        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stg.close();

    }
    @FXML
    private void cancelPop2(ActionEvent event) {

        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stg.close();

    }
    @FXML
    private void exportToFile(ActionEvent event) throws IOException {

        BufferedWriter writer = null;
        try {
            ArrayList<Customer> lista1 = b.getCustomerList();
            String userHomeFolder = System.getProperty("user.home");
            File textFile = new File(userHomeFolder, "customerpage.txt"); // lägger filen i hem mappen istället för i projektmappen
            writer = new BufferedWriter(new FileWriter(textFile));
            for (Customer c : lista1) {
                writer.write(c.toString() + "\n");

            }

        } catch (IOException e) {
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        b = BankLogic.getInstance();

        // test - ta bort när vi är färdiga
        long nr = 198905643943L;
        b.addCustomer("Kalle karlsson", nr);
        nr = 198905643843L;
        b.addCustomer("Peter haraldsson", nr);
        nr = 198967643943L;
        b.addCustomer("Hans haraldsson", nr);
        nr = 198905643978L;
        b.addCustomer("Harry haraldsson", nr);
        // ta bort

        setListView(); // fyller lista med kunder

    }

    public void setListView() {  // metod för att lägga samtliga kunders namn i listView
        oList = FXCollections.observableArrayList();

        ArrayList<Customer> tC = b.getCustomerList();
        for (Customer c : tC) {
            String s = c.getName() + " " + c.getPnr();
            oList.add(s);
        }

        customersList.setItems(oList);
    }

}
