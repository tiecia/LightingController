package lightingZone;

import devices.ControllableItem;

import java.util.HashSet;

public interface ControlTab {
    public void initData(HashSet<ControllableItem> devices);
    public void close();
    public void setAnimation();
}
