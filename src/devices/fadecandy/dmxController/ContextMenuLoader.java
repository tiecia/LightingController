package devices.fadecandy.dmxController;

import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URL;

public abstract class ContextMenuLoader {
    public ContextMenuLoader(URL location){
        FXMLLoader loader = new FXMLLoader(location);
        System.out.println(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
