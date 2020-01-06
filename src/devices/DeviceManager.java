package devices;

import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.ContextMenu;

public interface DeviceManager {
    public String toString();
    public Parent getSettingPage();
    public ContextMenu getContextMenu();
}
