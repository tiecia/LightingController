package deviceConfigurator;

import deviceConfigurator.addDevice.AddDeviceController;
import deviceConfigurator.fcServerSettings.FCServer;
import devices.DeviceManager;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DeviceConfigurator implements Initializable {

    //@TODO Auto expand nodes when new node is added

    Stage window;

    @FXML
    private BorderPane rootLayout;
    @FXML
    //Displays and stores all connected devices
    private TreeView<String> tree;
    private CheckBoxTreeItem<String> root;

    private ContextMenu contextMenu;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("treeContextMenu.fxml"));
        this.contextMenu = null;
        try {
            contextMenu = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ((TreeContextMenu)loader.getController()).initData(this);
        tree.setContextMenu(contextMenu);
        tree.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            try {
                Parent selectedPage = ((DeviceManager) tree.getSelectionModel().getSelectedItem()).getSettingPage();
                rootLayout.setCenter(selectedPage);
            } catch (ClassCastException e){
                System.out.println("Root Selected");
                return;
            }
            setContextMenu();
        });
        tree.setShowRoot(false);
        this.root = new CheckBoxTreeItem<>("Root");
        tree.setRoot(root);
    }

    @FXML
    public void addDevice() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addDevice/addDeviceDialog.fxml"));
        Parent parent = loader.load();
        Scene parentScene = new Scene(parent);
        Stage dialogStage = new Stage();
        dialogStage.setScene(parentScene);
        dialogStage.showAndWait();

        //Wait for User

        AddDeviceController chooserDialog = loader.getController();
        if(chooserDialog.getSelection() != null && chooserDialog.getSelection().equals("Fadecandy Server")){
            newFadecandyServer(chooserDialog.getServerIP(), chooserDialog.getServerPort());
        } else if (chooserDialog.getSelection() != null && chooserDialog.getSelection().equals("Arduino")){
            // TODO: 12/21/2019 Add Arduino
        }
    }

    public TreeView<String> getTree(){
        return tree;
    }

    @FXML
    private void closeWindow(){
        Stage stage = (Stage) tree.getScene().getWindow();
        stage.close();
    }

    public void removeServer(FCServer server){
        root.getChildren().remove(server);
        tree.getSelectionModel().select(root);
        setContextMenu();
    }

    public void removeSelectedNode(){
        DeviceManager selectedManager = (DeviceManager) tree.getSelectionModel().getSelectedItem();
        selectedManager = null;
        TreeItem selectedNode = tree.getSelectionModel().getSelectedItem();
        selectedNode = null;
    }

    private void newFadecandyServer(String ip, int port){
        FXMLLoader newServerLoader = new FXMLLoader(getClass().getResource("fcServerSettings/fcServerSettings.fxml"));
        Parent newFCServerSettingPage = null;
        try {
            newFCServerSettingPage = newServerLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FCServer newFCServerManager = newServerLoader.getController();
        newFCServerManager.initData(ip, port, newFCServerSettingPage, this);;
        root.getChildren().add(newFCServerManager);
        tree.getSelectionModel().selectLast();
    }

    private void setContextMenu(){
        try{
            DeviceManager selectedItem = (DeviceManager) tree.getSelectionModel().getSelectedItem();
            System.out.println(selectedItem);
            tree.setContextMenu(selectedItem.getContextMenu());
        } catch (ClassCastException e){
            tree.setContextMenu(contextMenu);
        }
    }
}
