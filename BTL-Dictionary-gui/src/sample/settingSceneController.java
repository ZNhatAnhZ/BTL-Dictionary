package sample;

import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class settingSceneController {
    DictionaryManagement Dict = new DictionaryManagement();
    @FXML
    JFXListView<String> settingListView = new JFXListView<>();
    @FXML
    JFXTextArea settingTextArea = new JFXTextArea();
    @FXML
    JFXTextField settingSearchBar = new JFXTextField();
    @FXML
    JFXButton settingSearchButton = new JFXButton();
    @FXML
    JFXButton settingDeleteButton = new JFXButton();
    @FXML
    JFXButton settingChangeButton = new JFXButton();
    @FXML
    JFXButton settingAddButton = new JFXButton();
    @FXML
    JFXSnackbar Notification = new JFXSnackbar();

    public void initialize() {
        Dict.insertFromMySQL();

        ObservableList<String> list = FXCollections.observableArrayList(Dict.getAllWord_Target());
        settingListView.setItems(list);
        settingListView.setStyle("-fx-background-insets: 0 ;");
        settingListView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
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
    public void backwardScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("firstScene.fxml"));
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 800, 500));
        window.show();
    }
    public void settingSearchButtonAction(ActionEvent event) throws IOException {
        Platform.runLater(()-> {
            if (!settingSearchBar.getText().equals("")) {
                settingTextArea.clear();
                String s = Dict.dictionaryLookup(settingSearchBar.getText());
                if (s != null) {
                    String temp[] = new String[100];
                    temp = s.split("<br />");
                    setSettingTextArea(Dict.getPronun(settingSearchBar.getText()));
                    setSettingTextArea(temp);
                } else {
                    String[] temp = {"Không tìm thấy từ đó"};
                    setSettingTextArea(temp);
                }
            }
        });
    }
    public void setSettingTextArea(String[] s) {
        for(int i=0; i< s.length; i++) {
            Text temp = new Text(s[i] + System.lineSeparator());
            temp.setFont(Font.font(null, 13));
            settingTextArea.appendText(temp.getText());
        }
    }
    public void setSettingTextArea(String s) {
        Text temp = new Text(s + System.lineSeparator());
        temp.setFont(Font.font(null, 13));
        if (!(s.equals(""))) settingTextArea.appendText(temp.getText());
    }
    public void autoCompletedTextView(InputEvent event) throws IOException {
        settingListView.getItems().clear();
        ObservableList<String> list = FXCollections.observableArrayList(Dict.Searcher(settingSearchBar.getText()));
        settingListView.setItems(list);
    }
    public void selectedListViewAction(MouseEvent event) throws IOException{
        Platform.runLater(()-> {
            settingTextArea.clear();
            String s = Dict.dictionaryLookup(settingListView.getSelectionModel().getSelectedItem());
            String s1 = Dict.getPronun(settingListView.getSelectionModel().getSelectedItem());
            if (s != null) {
                String temp[] = new String[100];
                temp = s.split("<br />");
                setSettingTextArea(s1);
                setSettingTextArea(temp);
            } else {
                String[] temp = {"Không tìm thấy từ đó"};
                setSettingTextArea(temp);
            }
        });
    }
    public void settingChangeButtonAction(ActionEvent event) throws IOException {
        if (!settingTextArea.getParagraphs().isEmpty() && !settingListView.getSelectionModel().isEmpty()) {
            ArrayList<String> templist = new ArrayList<>();
            for (String temp : settingTextArea.getText().split("\\n")) templist.add(temp);
            String s = new String();
            String pronoun = new String();
            String word_explain = new String();
            for (int i = 1; i < templist.size(); i++) {
                s = s + templist.get(i);
                if (i + 1 < templist.size()) s = s + "<br />";
            }

            if (templist.size() > 0) {
                if (templist.get(0).indexOf('/') > -1 && templist.get(0).lastIndexOf('/') > -1 && templist.get(0).lastIndexOf('/') != templist.get(0).indexOf('/')) {
                    pronoun = templist.get(0);
                    word_explain = s;
                } else {
                    pronoun = "";
                    word_explain = templist.get(0) + "<br />" + s;
                }
            }
            Dict.changeWordAndPronoun(new Word(settingListView.getSelectionModel().getSelectedItem(), word_explain), pronoun, Dict.getWordId(settingListView.getSelectionModel().getSelectedItem()));

            Connection con = null;
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?characterEncoding=UTF-8", "root", "1892001");
                String query = "UPDATE dictionary_data SET detail=? WHERE word=? ";
                String query1 = "UPDATE pronunciation SET pronun=? WHERE word=? ";
                PreparedStatement ps = con.prepareStatement(query);
                PreparedStatement ps1 = con.prepareStatement(query1);
                ps.setString(1, word_explain);
                ps.setString(2, settingListView.getSelectionModel().getSelectedItem());
                ps1.setString(1, pronoun);
                ps1.setString(2, settingListView.getSelectionModel().getSelectedItem());
                ps.executeUpdate();
                ps1.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            Label tempLabel = new Label("Đã sửa từ đó");
            tempLabel.setTextFill(Paint.valueOf("Red"));
            tempLabel.setOpacity(0.8);
            tempLabel.setFont(Font.font("System", 14));
            final JFXSnackbar.SnackbarEvent snackbarEvent = new JFXSnackbar.SnackbarEvent(tempLabel, Duration.seconds(2));
            Notification.enqueue(snackbarEvent);
        }
    }
    public void settingAddButtonAction(ActionEvent event) throws IOException {
        if (settingListView.getSelectionModel().isEmpty() && settingSearchBar.getLength() > 0) {
            ArrayList<String> templist = new ArrayList<>();
            for (String temp : settingTextArea.getText().split("\\n")) templist.add(temp);
            String s = new String();
            String pronoun = new String();
            String word_explain = new String();
            for (int i = 1; i < templist.size(); i++) {
                s = s + templist.get(i);
                if (i + 1 < templist.size()) s = s + "<br />";
            }

            if (templist.size() > 0) {
                if (templist.get(0).indexOf('/') > -1 && templist.get(0).lastIndexOf('/') > -1 && templist.get(0).lastIndexOf('/') != templist.get(0).indexOf('/')) {
                    pronoun = templist.get(0);
                    word_explain = s;
                } else {
                    pronoun = "";
                    word_explain = templist.get(0) + "<br />" + s;
                }
            }

            Connection con = null;
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?characterEncoding=UTF-8", "root", "1892001");
                String query = "INSERT INTO dictionary_data (word, detail) VALUES (?, ?) ";
                String query1 = "INSERT INTO pronunciation (word, pronun) VALUES (?, ?) ";
                PreparedStatement ps = con.prepareStatement(query);
                PreparedStatement ps1 = con.prepareStatement(query1);
                ps.setString(1, settingSearchBar.getText());
                ps.setString(2, word_explain);
                ps1.setString(1, settingSearchBar.getText());
                ps1.setString(2, pronoun);
                ps.executeUpdate();
                ps1.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            Dict.addWord(settingSearchBar.getText(), word_explain);
            Dict.addPronun(pronoun);
            ObservableList<String> list = FXCollections.observableArrayList(Dict.Searcher(settingSearchBar.getText()));
            settingListView.setItems(list);

            Label tempLabel = new Label("Đã thêm từ đó");
            tempLabel.setTextFill(Paint.valueOf("Red"));
            tempLabel.setOpacity(0.8);
            tempLabel.setFont(Font.font("System", 14));
            final JFXSnackbar.SnackbarEvent snackbarEvent = new JFXSnackbar.SnackbarEvent(tempLabel, Duration.seconds(2));
            Notification.enqueue(snackbarEvent);
        }
    }
    public void settingRemoveButtonAction(ActionEvent event) throws IOException {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?characterEncoding=UTF-8", "root", "1892001");
            String query = "DELETE FROM dictionary_data WHERE word=? ";
            String query1 = "DELETE FROM pronunciation WHERE id=? ";
            PreparedStatement ps = con.prepareStatement(query);
            PreparedStatement ps1 = con.prepareStatement(query1);
            ps.setString(1, settingListView.getSelectionModel().getSelectedItem());
            ps1.setInt(1, Dict.getWordId(settingListView.getSelectionModel().getSelectedItem()) + 2);
            ps.executeUpdate();
            ps1.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        Dict.deleteWordAndPronoun(settingListView.getSelectionModel().getSelectedItem());
        ObservableList<String> list = FXCollections.observableArrayList(Dict.Searcher(settingSearchBar.getText()));
        settingListView.setItems(list);

        Label tempLabel = new Label("Đã xóa từ đó");
        tempLabel.setTextFill(Paint.valueOf("Red"));
        tempLabel.setOpacity(0.8);
        tempLabel.setFont(Font.font("System", 14));
        final JFXSnackbar.SnackbarEvent snackbarEvent = new JFXSnackbar.SnackbarEvent(tempLabel, Duration.seconds(2));
        Notification.enqueue(snackbarEvent);
    }
}
