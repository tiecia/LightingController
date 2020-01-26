package deviceConfigurator;

import devices.fadecandy.opc.Animation;
import devices.fadecandy.opc.PixelStrip;

public class PixelEditAnimation extends Animation {
    @Override
    public void reset(PixelStrip strip) {

    }

    @Override
    public boolean draw(PixelStrip strip) {
        for(int i = 0; i<strip.getPixelCount(); i++){
            strip.setPixelColor(i, makeColor(255,255,255));
        }
        strip.show();
        System.out.println("Editing");
        return false;
    }
}
