package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import java.awt.*;
import java.io.IOException;

public class secondController {
    private DictionaryManagement Dict = new DictionaryManagement();
    @FXML
    private Button BacktoFirst;
    @FXML
    private Button secondSearchButton;
    @FXML
    private TextField secondSearchBar;
    @FXML
    private Text secondDefinition;

    @FXML
    public void initialize() {
        Dict.insertFromFile();
        TextFields.bindAutoCompletion(secondSearchBar, Dict.Searcher(secondSearchBar.getText()));
    }

    @FXML
    public void backwardScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("firstScene.fxml"));
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 800, 500));
        window.show();
    }
    public void setSecondDefinition(String s) {
        secondDefinition.setText(s);
    }

    public void secondSearchButtonAction(ActionEvent event) throws IOException{
        String s = Dict.dictionaryLookup(secondSearchBar.getText());
        if (s.length() > 0) {
            setSecondDefinition(s);
        } else {
            setSecondDefinition("Không tìm thấy từ đó");
        }
    }
}
