package deviceConfigurator.addDevice.choices;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class FCOptionController {

    @FXML
    TextField ipField;
    @FXML
    TextField portField;

    public String getIP(){
        return ipField.getText();
    }

    public int getPort() {
        return Integer.parseInt(portField.getText());
    }
}
