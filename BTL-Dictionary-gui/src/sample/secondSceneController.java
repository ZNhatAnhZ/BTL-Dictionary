package sample;

import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class secondSceneController {
    DictionaryManagement Dict = new DictionaryManagement();
    @FXML
    TextArea secondDefinition = new TextArea();
    @FXML
    JFXTextField secondSearchBar = new JFXTextField();
    @FXML
    JFXListView<String> firstListView = new JFXListView();
    @FXML
    JFXRadioButton secondRadioButton = new JFXRadioButton();

    public void backwardScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("firstScene.fxml"));
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 800, 500));
        window.show();
    }

    public void initialize() {
        Dict.insertFromMySQL();

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
                            setStyle("-fx-control-inner-background: " +  "#fdf5e6" + ";");
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
    }

}
