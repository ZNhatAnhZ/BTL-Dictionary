package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextArea;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class apiSceneController {
    TranslatorApi translatorApi = new TranslatorApi();
    TextToSpeech textToSpeech = new TextToSpeech();

    @FXML
    JFXTextArea apiLeftAutoTextArea = new JFXTextArea();
    @FXML
    JFXTextArea apiLeftEnTextArea = new JFXTextArea();
    @FXML
    JFXTextArea apiLeftViTextArea = new JFXTextArea();
    @FXML
    JFXTextArea apiRightEnTextArea = new JFXTextArea();
    @FXML
    JFXTextArea apiRightViTextArea = new JFXTextArea();
    @FXML
    JFXTabPane apiLeftTabPane = new JFXTabPane();
    @FXML
    JFXTabPane apiRightTabPane = new JFXTabPane();
    @FXML
    Tab firstLeftTab = new Tab();
    @FXML
    Tab secondLeftTab = new Tab();
    @FXML
    Tab thirdLeftTab = new Tab();
    @FXML
    Tab firstRightTab = new Tab();
    @FXML
    Tab secondRightTab = new Tab();
    @FXML
    JFXSnackbar apiWarning = new JFXSnackbar();
    @FXML
    public JFXButton closeButton = new JFXButton();

    public void initialize() {
    }
    public void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
    public void backwardScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("firstScene.fxml"));
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 1000, 600));
        window.show();
    }
    public void translateAction(ActionEvent event) throws IOException {
        int leftIndex = apiLeftTabPane.getSelectionModel().getSelectedIndex();
        int rightIndex = apiRightTabPane.getSelectionModel().getSelectedIndex();
        String leftLanguage = new String();
        String rightLanguage;
        String leftTextArea = new String();
        switch(leftIndex) {
            case 0:
                leftLanguage = "";
                leftTextArea = apiLeftAutoTextArea.getText();
                break;
            case 1:
                leftLanguage = "en";
                leftTextArea = apiLeftEnTextArea.getText();
                break;
            case 2:
                leftLanguage = "vi";
                leftTextArea = apiLeftViTextArea.getText();
                break;
        }
        switch(rightIndex) {
            case 0:
                rightLanguage = "vi";
                apiRightViTextArea.setText(translatorApi.translate(leftLanguage, rightLanguage, leftTextArea));
                break;
            case 1:
                rightLanguage = "en";
                apiRightEnTextArea.setText(translatorApi.translate(leftLanguage, rightLanguage, leftTextArea));
                break;
        }


    }
    public void autoLeftSelectedTab() throws IOException {
        Platform.runLater(()-> {
            resetTextArea();
            int leftIndex = apiLeftTabPane.getSelectionModel().getSelectedIndex();
            int rightIndex = apiRightTabPane.getSelectionModel().getSelectedIndex();

            if (leftIndex == 1 && rightIndex == 1) {
                apiLeftTabPane.getSelectionModel().clearAndSelect(1);
                apiRightTabPane.getSelectionModel().clearAndSelect(0);
            } else if (leftIndex == 2 && rightIndex == 0) {
                apiLeftTabPane.getSelectionModel().clearAndSelect(2);
                apiRightTabPane.getSelectionModel().clearAndSelect(1);
            }
        });
    }
    public void autoRightSelectedTab() throws IOException {
        Platform.runLater(()-> {
            resetTextArea();
            int leftIndex = apiLeftTabPane.getSelectionModel().getSelectedIndex();
            int rightIndex = apiRightTabPane.getSelectionModel().getSelectedIndex();

            if (rightIndex == 0 && leftIndex == 2) {
                apiRightTabPane.getSelectionModel().clearAndSelect(0);
                apiLeftTabPane.getSelectionModel().clearAndSelect(1);
            } else if (rightIndex == 1 && leftIndex == 1) {
                apiRightTabPane.getSelectionModel().clearAndSelect(1);
                apiLeftTabPane.getSelectionModel().clearAndSelect(2);
            }
        });
    }
    public void resetTextArea() {
        Platform.runLater(()-> {
            if (!apiLeftAutoTextArea.getParagraphs().isEmpty()) {
                apiLeftAutoTextArea.setText("");
            }
            if (!apiLeftEnTextArea.getParagraphs().isEmpty()) {
                apiLeftEnTextArea.setText("");
            }
            if (!apiLeftViTextArea.getParagraphs().isEmpty()) {
                apiLeftViTextArea.setText("");
            }
            if (!apiRightEnTextArea.getParagraphs().isEmpty()) {
                apiRightEnTextArea.setText("");
            }
            if (!apiRightViTextArea.getParagraphs().isEmpty()) {
                apiRightViTextArea.setText("");
            }
        });
    }
    public void leftVoiceButtonAction() {
        Platform.runLater(()-> {
            if (apiLeftTabPane.getSelectionModel().getSelectedIndex() == 1) {
                textToSpeech.speak(apiLeftEnTextArea.getText(), 1.0f, false, true);
            } else {
                Label tempLabel = new Label("Chức năng nghe chưa khả dụng cho ngôn ngữ này");
                tempLabel.setTextFill(Paint.valueOf("Red"));
                tempLabel.setOpacity(0.8);
                tempLabel.setFont(Font.font("System", 14));
                final JFXSnackbar.SnackbarEvent snackbarEvent = new JFXSnackbar.SnackbarEvent(tempLabel, Duration.seconds(2));
                apiWarning.enqueue(snackbarEvent);
            }
        });
    }
    public void rightVoiceButtonAction() {
        Platform.runLater(()-> {
            if (apiRightTabPane.getSelectionModel().getSelectedIndex() == 1) {
                textToSpeech.speak(apiRightEnTextArea.getText(), 1.0f, false, true);
            } else {
                Label tempLabel = new Label("Chức năng nghe chưa khả dụng cho ngôn ngữ này");
                tempLabel.setTextFill(Paint.valueOf("Red"));
                tempLabel.setOpacity(0.8);
                tempLabel.setFont(Font.font("System", 14));
                final JFXSnackbar.SnackbarEvent snackbarEvent = new JFXSnackbar.SnackbarEvent(tempLabel, Duration.seconds(2));
                apiWarning.enqueue(snackbarEvent);
            }
        });
    }
}
