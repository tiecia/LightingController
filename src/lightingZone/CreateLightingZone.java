package lightingZone;

import com.sun.source.tree.Tree;
import deviceConfigurator.DeviceConfigurator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateLightingZone implements Initializable {
    private @FXML DeviceConfigurator config;

    private @FXML TreeView<String> tree;
    private @FXML TextField nameField;

    private Stage window;
    private Zone zone;


    public CreateLightingZone(Stage parentWindow, DeviceConfigurator config){
        this.config = config;

        //Load FXML File
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../lightingZone/createLightingZone.fxml"));
        loader.setController(this);
        Parent content = null;
        try {
            content = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Set Up Tree
        this.config = config;
        tree.setRoot(config.getTree().getRoot());
        tree.setShowRoot(false);
        tree.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tree.setCellFactory(CheckBoxTreeCell.forTreeView());

        //Set Up and Show Window
        this.window = new Stage();
        Scene s = new Scene(content);
        window.setScene(s);
        window.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameField.setText("New Zone");
    }

    public Zone getZone(){
        return zone;
    }

    public String getName(){
        return nameField.getText();
    }

    public TreeView<String> getTree() {
        return tree;
    }

    public void showAndWait(){
        window.showAndWait();
    }

    @FXML
    private void confirm(){
        zone = new Zone(this, nameField.getText());
        tree.getScene().getWindow().hide();
    }

    @FXML
    private void cancel() {
        tree.getScene().getWindow().hide();
    }
}