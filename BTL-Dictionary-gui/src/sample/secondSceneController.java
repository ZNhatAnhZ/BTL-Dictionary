package sample;

import animatefx.animation.FadeIn;
import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.Parent;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;

public class secondSceneController {
    DictionaryManagement Dict = DictionaryManagement.getInstance();
    TextToSpeech textToSpeech = new TextToSpeech();

    @FXML
    TextArea secondDefinition = new TextArea();
    @FXML
    JFXTextField secondSearchBar = new JFXTextField();
    @FXML
    JFXListView<String> firstListView = new JFXListView();
    @FXML
    JFXRadioButton secondRadioButton = new JFXRadioButton();
    @FXML
    JFXButton closeButton = new JFXButton();
    @FXML
    JFXButton secondVolumeButton = new JFXButton();
    @FXML
    JFXListView<String> firstListView1 = new JFXListView<>();
    @FXML
    JFXListView<String> firstListView2 = new JFXListView<>();
    @FXML
    JFXTabPane secondTabPane = new JFXTabPane();

    public void backwardScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("firstScene.fxml"));
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 1000, 600));
        window.show();
        new FadeIn(root).play();
    }
    public void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
    public void initialize() {
        ObservableList<String> list = FXCollections.observableArrayList(Dict.getAllWord_Target());
        firstListView.setItems(list);
        firstListView.setStyle("-fx-background-insets: 0 ;");
        firstListView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                            setStyle("-fx-control-inner-background: " +  "#f4f4f4" + ";");
                            setFont(Font.font(null, 13));
                        } else {
                            setText(item);
                        }
                    }
                };
            }
        });

        ObservableList<String> list1 = FXCollections.observableArrayList(Dict.getHisWord());
        firstListView1.setItems(list1);
        firstListView1.setStyle("-fx-background-insets: 0 ;");
        firstListView1.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                            setStyle("-fx-control-inner-background: " +  "#f4f4f4" + ";");
                            setFont(Font.font(null, 13));
                        } else {
                            setText(item);
                        }
                    }
                };
            }
        });

        ObservableList<String> list2 = FXCollections.observableArrayList(Dict.getFavWord());
        firstListView2.setItems(list2);
        firstListView2.setStyle("-fx-background-insets: 0 ;");
        firstListView2.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                            setStyle("-fx-control-inner-background: " +  "#f4f4f4" + ";");
                            setFont(Font.font(null, 13));
                        } else {
                            setText(item);
                        }
                    }
                };
            }
        });
    }
    public void setSecondDefinition(String[] s) {
        for(int i=0; i< s.length; i++) {
            Text temp = new Text(s[i] + "\n");
            temp.setFont(Font.font(null, 13));
            secondDefinition.appendText(temp.getText());
        }
    }
    public void setSecondDefinition(String s) {
        Text temp = new Text(s + "\n");
        temp.setFont(Font.font(null, 13));
        if (!(s.equals(""))) secondDefinition.appendText(temp.getText());
    }
    public void secondSearchButtonAction(ActionEvent event) throws IOException {
        Platform.runLater(()-> {
            if (!secondSearchBar.getText().equals("")) {
                secondDefinition.clear();
                String s = Dict.dictionaryLookup(secondSearchBar.getText());
                if (s != null) {
                    String temp[] = new String[100];
                    temp = s.split("<br />");
                    setSecondDefinition(Dict.getPronun(secondSearchBar.getText()));
                    setSecondDefinition(temp);
                    Dict.addHisWord(secondSearchBar.getText());
                    updateListView1();
                    if (Dict.isFavWord(secondSearchBar.getText())) {
                        secondRadioButton.setSelected(true);
                    } else {
                        secondRadioButton.setSelected(false);
                    }
                } else {
                    String[] temp = {"Không tìm thấy từ đó"};
                    setSecondDefinition(temp);
                }
            }
        });
    }
    public void selectedListViewAction(MouseEvent event) throws IOException{
        Platform.runLater(()-> {
            secondDefinition.clear();
            String s = Dict.dictionaryLookup(firstListView.getSelectionModel().getSelectedItem());
            String s1 = Dict.getPronun(firstListView.getSelectionModel().getSelectedItem());
            if (s != null) {
                String temp[] = new String[100];
                temp = s.split("<br />");
                setSecondDefinition(s1);
                setSecondDefinition(temp);
                Dict.addHisWord(firstListView.getSelectionModel().getSelectedItem());
                updateListView1();
                if (Dict.isFavWord(firstListView.getSelectionModel().getSelectedItem())) {
                    secondRadioButton.setSelected(true);
                } else {
                    secondRadioButton.setSelected(false);
                }
            } else {
                String[] temp = {"Không tìm thấy từ đó"};
                setSecondDefinition(temp);
            }
        });
    }
    public void selectedListViewAction1(MouseEvent event) throws IOException{
        Platform.runLater(()-> {
            secondDefinition.clear();
            String s = Dict.dictionaryLookup(firstListView1.getSelectionModel().getSelectedItem());
            String s1 = Dict.getPronun(firstListView1.getSelectionModel().getSelectedItem());
            if (s != null) {
                String temp[] = new String[100];
                temp = s.split("<br />");
                setSecondDefinition(s1);
                setSecondDefinition(temp);
                if (Dict.isFavWord(firstListView1.getSelectionModel().getSelectedItem())) {
                    secondRadioButton.setSelected(true);
                } else {
                    secondRadioButton.setSelected(false);
                }
            } else {
                String[] temp = {"Không tìm thấy từ đó"};
                setSecondDefinition(temp);
            }
        });
    }
    public void selectedListViewAction2(MouseEvent event) throws IOException{
        Platform.runLater(()-> {
            secondDefinition.clear();
            String s = Dict.dictionaryLookup(firstListView2.getSelectionModel().getSelectedItem());
            String s1 = Dict.getPronun(firstListView2.getSelectionModel().getSelectedItem());
            if (s != null) {
                String temp[] = new String[100];
                temp = s.split("<br />");
                setSecondDefinition(s1);
                setSecondDefinition(temp);
                if (Dict.isFavWord(firstListView2.getSelectionModel().getSelectedItem())) {
                    secondRadioButton.setSelected(true);
                } else {
                    secondRadioButton.setSelected(false);
                }
            } else {
                String[] temp = {"Không tìm thấy từ đó"};
                setSecondDefinition(temp);
            }
        });
    }
    public void autoCompletedTextView(InputEvent event) throws IOException{
        firstListView.getItems().clear();
        ObservableList<String> list = FXCollections.observableArrayList(Dict.Searcher(secondSearchBar.getText()));
        firstListView.setItems(list);
        firstListView1.getItems().clear();
        ObservableList<String> list1 = FXCollections.observableArrayList(Dict.searcherCustom(secondSearchBar.getText(),Dict.getHisWord()));
        firstListView1.setItems(list1);
        firstListView2.getItems().clear();
        ObservableList<String> list2 = FXCollections.observableArrayList(Dict.searcherCustom(secondSearchBar.getText(),Dict.getFavWord()));
        firstListView2.setItems(list2);
    }
    public void updateListView1() {
        firstListView1.getItems().clear();
        ObservableList<String> list1 = FXCollections.observableArrayList(Dict.getHisWord());
        firstListView1.setItems(list1);
    }
    public void updateListView2() {
        firstListView2.getItems().clear();
        ObservableList<String> list2 = FXCollections.observableArrayList(Dict.getFavWord());
        firstListView2.setItems(list2);
    }
    public void secondVolumeButtonAction(ActionEvent event) throws IOException {
        if (secondTabPane.getSelectionModel().isSelected(0)) {
            Platform.runLater(()-> textToSpeech.speak(firstListView.getSelectionModel().getSelectedItem(), 1.0f, false, true));
        } else if (secondTabPane.getSelectionModel().isSelected(1)) {
            Platform.runLater(()-> textToSpeech.speak(firstListView1.getSelectionModel().getSelectedItem(), 1.0f, false, true));
        } else if (secondTabPane.getSelectionModel().isSelected(2)) {
            Platform.runLater(()-> textToSpeech.speak(firstListView2.getSelectionModel().getSelectedItem(), 1.0f, false, true));
        }
    }
    public void secondBookMarkButtonAction(ActionEvent event) throws IOException {
        if (secondRadioButton.isSelected()) {
            Dict.addFavWord(firstListView.getSelectionModel().getSelectedItem());
        } else {
            Dict.delFavWord(firstListView.getSelectionModel().getSelectedItem());
        }
        updateListView2();
    }
}
