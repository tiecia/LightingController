package deviceConfigurator.fcServerSettings;

import deviceConfigurator.DeviceConfigurator;
import javafx.fxml.FXML;

import java.io.IOException;

public class ServerContextMenu {

    private FCServer server;

    public void initData(FCServer server){
        this.server = server;
    }

    @FXML
    private void addDevice(){
        try {
            server.getConfigurator().addDevice();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void removeDevice(){
        server.removeThisServer();
    }

    @FXML
    private void addBoard(){
        try {
            server.addBoard();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
