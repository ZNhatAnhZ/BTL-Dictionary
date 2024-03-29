package sample;

import animatefx.animation.FadeIn;
import com.jfoenix.controls.JFXAutoCompletePopup;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;

import static javafx.scene.text.FontWeight.BOLD;


public class firstSceneController {
    private DictionaryManagement Dict = DictionaryManagement.getInstance();

    @FXML
    JFXSnackbar errorWarning = new JFXSnackbar();
    @FXML
    JFXButton firstSearchButton = new JFXButton();
    @FXML
    AnchorPane anchorSearchPane = new AnchorPane();
    @FXML
    JFXTextField firstSearchBar = new JFXTextField();
    @FXML
    JFXButton firstSettingButton = new JFXButton();
    @FXML
    JFXButton closeButton = new JFXButton();
    int count =  0;

    public void initialize() {
        if (count == 0) {
            Dict.insertFromMySQL();
            count++;
        }


        JFXAutoCompletePopup<String> autoCompletePopup = new JFXAutoCompletePopup<>();
        autoCompletePopup.getSuggestions().addAll(Dict.getAllWord_Target());

        autoCompletePopup.setSelectionHandler(event -> {
            firstSearchBar.setText(event.getObject());

            // you can do other actions here when text completed
        });

        // filtering options
        firstSearchBar.textProperty().addListener(observable -> {
            autoCompletePopup.filter(string -> string.toLowerCase().startsWith(firstSearchBar.getText().toLowerCase()));
            if (autoCompletePopup.getFilteredSuggestions().isEmpty() || firstSearchBar.getText().isEmpty()) {
                autoCompletePopup.hide();
                // if you remove textField.getText.isEmpty() when text field is empty it suggests all options
                // so you can choose
            } else {
                autoCompletePopup.show(firstSearchBar);
            }
        });
    }
    public void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
    public void firstSearchButtonAction(ActionEvent event) throws IOException {
        if (!firstSearchBar.getText().equals("")) {
            String temp[] = new String[100];
            String s = Dict.dictionaryLookup(firstSearchBar.getText());
            if (s != null) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("secondScene.fxml"));
                Parent root = loader.load();
                Dict.addHisWord(firstSearchBar.getText());
                temp = s.split("<br />");
                secondSceneController secondcontroller = loader.getController();
                secondcontroller.setSecondDefinition(Dict.getPronun(firstSearchBar.getText()));
                secondcontroller.setSecondDefinition(temp);

                Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
                window.setScene(new Scene(root, 1000, 600));
                window.show();
                new FadeIn(root).play();
            } else {
                Label tempLabel = new Label("Không tìm thấy từ đó");
                tempLabel.setTextFill(Paint.valueOf("Red"));
                tempLabel.setOpacity(0.8);
                tempLabel.setFont(Font.font("System", 14));
                final JFXSnackbar.SnackbarEvent snackbarEvent = new JFXSnackbar.SnackbarEvent(tempLabel, Duration.seconds(3.33));
                errorWarning.enqueue(snackbarEvent);
            }
        }

    }
    public void firstSettingButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("settingScene.fxml"));
        Parent root = loader.load();
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 1000, 600));
        window.show();
        new FadeIn(root).play();
    }
    public void firstApiButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("apiScene.fxml"));
        Parent root = loader.load();
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 1000, 600));
        window.show();
        new FadeIn(root).play();
    }
    public void firstFavButtonAction (ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("secondScene.fxml"));
        Parent root = loader.load();
        secondSceneController secondcontroller = loader.getController();
        secondcontroller.secondTabPane.getSelectionModel().select(2);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 1000, 600));
        window.show();
        new FadeIn(root).play();
    }
    public void firstHisButtonAction (ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("secondScene.fxml"));
        Parent root = loader.load();
        secondSceneController secondcontroller = loader.getController();
        secondcontroller.secondTabPane.getSelectionModel().select(1);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 1000, 600));
        window.show();
        new FadeIn(root).play();
    }
}
