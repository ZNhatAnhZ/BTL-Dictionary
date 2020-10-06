package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class secondController {
    private DictionaryManagement Dict = new DictionaryManagement();
    @FXML
    private Button BacktoFirst;
    @FXML
    private Button secondSearchButton;
    @FXML
    private TextField secondSearchBar;
    @FXML
    private TextFlow secondDefinition;

    @FXML
    public void initialize() {
        Dict.insertFromMySQL();
    }
    public void autocomplete() {
        TextFields.bindAutoCompletion(secondSearchBar, Dict.Searcher(secondSearchBar.getText()));
    }
    public void backwardScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("firstScene.fxml"));
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 800, 500));
        window.show();
    }
    public void setSecondDefinition(String[] s) {
        for(int i=0; i< s.length; i++) {
                Text temp = new Text(s[i] + "\n");
                secondDefinition.getChildren().add(temp);
        }
    }
    public void secondSearchButtonAction(ActionEvent event) throws IOException{
        String s = Dict.dictionaryLookup(secondSearchBar.getText());
        secondDefinition.getChildren().clear();
        if (s != null) {
            String temp[] = new String[100];
            temp = s.split("<br />");
            setSecondDefinition(temp);
        } else {
            String[] temp = {"Không tìm thấy từ đó"};
            setSecondDefinition(temp);
        }
    }
}
