package devices;

import javafx.scene.control.Tab;

public interface ControllableItem {
    public static Tab getControlTab() {
        return null;
    }
    public void resetThread();
    public void closeThread();
}
