package devices.fadecandy.fcBoard;

import devices.fadecandy.fcStrip.FCStrip;
import devices.fadecandy.fcServer.FCServer;
import devices.DeviceManager;
import devices.fadecandy.opc.OpcDevice;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FCBoard extends CheckBoxTreeItem<String> implements Initializable, DeviceManager {

    @FXML
    BorderPane borderPane;

    String name;


    //This manager's board setting page
    private Parent settingPage;

    //Link back to this boards server manager
    private FCServer serverManager;

    //This manager's board
    private OpcDevice board;

    @FXML
    private TextField nameField;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.setValue(toString());
    }

    public void initData(Parent settingPage, FCServer serverManager){
        this.settingPage = settingPage;
        this.serverManager = serverManager;
        this.board = serverManager.getOPC().addDevice();
    }

    public OpcDevice getOPC(){
        return board;
    }

    public Parent getSettingPage() {
        return settingPage;
    }

    public FCServer getServerManager(){
        return serverManager;
    }

    @FXML
    public void addLedStrip(){
        FXMLLoader newStripLoader = new FXMLLoader(getClass().getResource("../fcStrip/fcStripSettings.fxml"));
        Parent newStripSettingPage = null;
        try {
            newStripSettingPage = newStripLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FCStrip newFCStripManager = newStripLoader.getController();
        newFCStripManager.initData(newStripSettingPage, this);
        super.getChildren().add(newFCStripManager);
        newFCStripManager.getParent().setExpanded(true);
    }

    public void removeStrip(FCStrip strip){
        board.removeStrip(strip.getOPC());
        getChildren().remove(strip);
    }

    public void removeThisBoard(){
        serverManager.removeDevice(this);
    }

    public ContextMenu getContextMenu() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("boardContextMenu.fxml"));
        ContextMenu menu = null;
        try {
            menu = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ((BoardContextMenu)loader.getController()).initData(this);
        return menu;
    }

    public String toString() {
        if (name == null || name.equals("")) {
            return "Fadecandy Board";
        }
        return name;
    }

    @FXML
    public void nameChanged(){
        this.name = nameField.getText();
        super.setValue(toString());
    }
}
