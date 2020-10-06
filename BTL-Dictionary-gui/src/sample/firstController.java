package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;
import javafx.event.ActionEvent;
import java.io.IOException;



public class firstController {
    protected DictionaryManagement Dict = new DictionaryManagement();

    @FXML
    private Text errorWarningText;
    @FXML
    private TextField firstSearchBar;
    @FXML
    private Button firstSearchButton;
    @FXML
    public void initialize() {
        Dict.insertFromMySQL();
    }

    public void autocomplete() {
        TextFields.bindAutoCompletion(firstSearchBar, Dict.Searcher(firstSearchBar.getText()));
    }

    public void firstSearchButtonAction(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("secondScene.fxml"));
        Parent root = loader.load();
        String temp[] = new String[100];
        String s = Dict.dictionaryLookup(firstSearchBar.getText());
        if (s != null) {
            temp = s.split("<br />");
            secondController secondcontroller = loader.getController();
            secondcontroller.setSecondDefinition(temp);

            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(new Scene(root, 800, 500));
            window.show();
        } else {
            errorWarningText.setText("Không tìm thấy từ đó");
        }
    }
}
