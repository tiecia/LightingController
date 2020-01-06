package lightingZone.ledZone.skills;

import javafx.scene.Parent;
import lightingZone.ledZone.LedZone;

public interface Skill {
    public Parent getPanel();
    public void initData(LedZone zone);
}
