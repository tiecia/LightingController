package deviceConfigurator.fcServerSettings;

import deviceConfigurator.DeviceConfigurator;
import deviceConfigurator.fcServerSettings.fcBoard.FCBoard;
import deviceConfigurator.fcServerSettings.addFCBoard.AddFCBoardController;
import devices.DeviceManager;
import devices.opc.OpcClient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FCServer extends CheckBoxTreeItem<String> implements Initializable, devices.DeviceManager {

    @FXML
    BorderPane borderPane;

    @FXML
    private Label titleLabel;

    @FXML
    private TextField nameField;
    @FXML
    private TextField ipField;
    @FXML
    private TextField portField;

    private String name;
    private String ip;
    private int port;

    private DeviceConfigurator parent;

    //Ths managers's server setting page
    private Parent settingsPage;

    //This manager's server;
    private OpcClient server;



    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleLabel.setText(toString());
        super.setValue(toString());
    }

    public void initData(String ip, int port, Parent settingsPage, DeviceConfigurator parent){
        this.settingsPage = settingsPage;
        this.ip = ip;
        this.port = port;
        this.parent = parent;
        ipField.setText(ip);
        portField.setText(port + "");
        connect();
    }

    public OpcClient getOPC(){
        return server;
    }

    public ContextMenu getContextMenu() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("serverContextMenu.fxml"));
        ContextMenu menu = null;
        try {
            menu = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ((ServerContextMenu) loader.getController()).initData( this);
        return menu;
    }

    @Override
    public Parent getSettingPage() {
        return settingsPage;
    }

    public DeviceConfigurator getConfigurator(){
        return parent;
    }

    public String toString() {
        if (name == null || name.equals("")) {
            return "Fadecandy Server";
        }
        return name + " (FC Server)";
    }

    @FXML
    public void addBoard() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addFCBoard/addFCBoard.fxml"));
        Parent parent = loader.load();
        Scene parentScene = new Scene(parent);
        Stage dialogStage = new Stage();
        dialogStage.setScene(parentScene);
        dialogStage.showAndWait();

        //Wait for user

        AddFCBoardController controller = loader.getController();
        if(controller.getSelection().equals("Fadecandy Board")){
            addFadecandyBoard();
        } else if(controller.getSelection().equals("DMX Device")){
            addDMXDevice();
        }
    }

    public void removeBoard(FCBoard board){
        server.removeDevice(board.getOPC());
        getChildren().remove(board);
    }

    public void removeThisServer(){
        parent.removeServer(this);
    }

    @FXML
    private void nameChanged(){
        this.name = nameField.getText();
        super.setValue(toString());
    }

    @FXML
    private void dataChanged(){
        try {
            this.ip = ipField.getText();
            this.port = Integer.parseInt(portField.getText());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return;
        }
        server.changeData(ip, port);
    }

    private void addFadecandyBoard(){
        FXMLLoader newFCBoardLoader = new FXMLLoader(getClass().getResource("fcBoard/FCBoardSettings.fxml"));
        Parent newFCBoardSettingsPage = null;
        try {
            newFCBoardSettingsPage = newFCBoardLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FCBoard newFCBoardManager = newFCBoardLoader.getController();
        newFCBoardManager.initData(newFCBoardSettingsPage, this);
        getChildren().add(newFCBoardManager);
        newFCBoardManager.getParent().setExpanded(true);
    }

    private void addDMXDevice(){
        //@TODO Add DMX Device
    }

    private void connect(){
        server = new OpcClient(this.ip, this.port);
        server.show();
    }
}
