package lightingZone.ledZone.skills;

import devices.fadecandy.opc.Animation;
import javafx.scene.Parent;
import lightingZone.ledZone.LedZone;

public interface Skill {
    public Parent getPanel();
    public void initData(LedZone zone);
}
