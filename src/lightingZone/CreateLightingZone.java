package lightingZone;

import deviceConfigurator.DeviceConfigurator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;

import java.io.IOException;

public class CreateLightingZone {

    @FXML
    DeviceConfigurator config;
    @FXML
    TreeView<String> tree;

    Zone zone;

    public void initData(DeviceConfigurator config){
        this.config = config;
        tree.setRoot(config.getTree().getRoot());
        tree.setShowRoot(false);
        tree.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tree.setCellFactory(CheckBoxTreeCell.forTreeView());
    }

    public Zone getZone(){
        return zone;
    }

    @FXML
    private void confirm(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("zone.fxml"));
        Parent panel = null;
        try {
            panel = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(tree.getSelectionModel().getSelectedItems());
        ((Zone) loader.getController()).initData(tree, panel);
        zone = loader.getController();
        tree.getScene().getWindow().hide();
    }

    @FXML
    private void cancel() {
        tree.getScene().getWindow().hide();
    }

}