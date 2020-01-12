package lightingZone.ledZone.skills.solidColor;

import devices.fadecandy.opc.Animation;
import devices.fadecandy.opc.PixelStrip;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import lightingZone.ledZone.LedZone;
import lightingZone.ledZone.skills.Skill;
import lightingZone.ledZone.skills.solidColor.luxColorPalette.java.LuxColorPalette;
import lightingZone.ledZone.skills.solidColor.luxColorPalette.java.LuxColorPicker;

public class SolidColorSkill extends Animation implements Skill {

    private LedZone parentController;

    private LuxColorPicker picker;
    private LuxColorPalette palette;

    public void initData(LedZone zone){
        parentController = zone;
        picker = new LuxColorPicker(parentController.getDevices(), this);
        palette = picker.getPalette();
        System.out.println("New Palette: " + palette);
    }

    public Parent getPanel(){
        return palette;
    }

    @Override
    public String toString() {
        return "Solid Color";
    }

    @Override
    public void reset(PixelStrip strip) {

    }

    @Override
    public boolean draw(PixelStrip strip) {
        Color c = picker.getColor();
        int r = (int)(c.getRed()*255);
        int g = (int)(c.getGreen()*255);
        int b = (int)(c.getBlue()*255);
//        System.out.println("JavaFX: " + c.getRed() + ", " + c.getGreen() + ", " + c.getBlue());
        System.out.println("sRGB: " + r + ", " + g + ", " + b);
        System.out.println("Integer: " + makeColor(r,g,b));
        for(int i = 0; i<strip.getPixelCount(); i++){
            strip.setPixelColor(i, makeColor(r,g,b));
        }
        strip.show();
        return true;
    }
}
