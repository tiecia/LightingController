package devices.fadecandy.dmxController;

import devices.ControllableItem;
import devices.DeviceManager;
import devices.fadecandy.fcServer.FCServer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.ContextMenu;

import java.io.IOException;

public class DMXController extends CheckBoxTreeItem<String> implements DeviceManager, ControllableItem {

    //TODO Finish DMX Implementation

    Parent panel;

    ContextMenu contextMenu;

    FCServer server;

    public DMXController(FCServer server){
        this.server = server;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dmxController.fxml"));
        loader.setController(this);
        try {
            this.panel = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setValue(toString());

        contextMenu = DMXControllerContextMenu.newDMXControllerContextMenu(this);
    }

    public Parent getSettingPage() {
        return panel;
    }

    @Override
    public ContextMenu getContextMenu() {
        return contextMenu;
    }

    public void addDevice(){
        //TODO
    }

    public void removeThisDevice(){
        //TODO
        server.removeDevice(this);
    }

    @Override
    public String toString() {
        return "DMXController{}";
    }

    @Override
    public void close() {
        //TODO
    }
}
