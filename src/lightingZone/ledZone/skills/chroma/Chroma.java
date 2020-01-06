package lightingZone.ledZone.skills.chroma;

import devices.ControllableItem;
import devices.opc.Animation;
import devices.opc.PixelStrip;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import lightingZone.ledZone.LedZone;
import lightingZone.ledZone.skills.Skill;

import java.io.IOException;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.HashSet;

public class Chroma extends Animation implements Skill {

    private HashSet<ControllableItem> devices;

    private Parent panel;

    private double incrementFactor = 1.0;
    private double hue = 0.0;
    private Color[] pixels;

    //Options
    private boolean solidColor = false;
    private boolean reverse = true;
    private int frameDelay = 20;
    private int spread = 3;
    private int brightness = 100;
    private int saturation = 100;

    public Chroma(LedZone zone){
        this.devices = zone.getDevices();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("chroma.fxml"));
        loader.setController(this);
        try {
            this.panel = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initData(LedZone zone) {

    }

    @Override
    public Parent getPanel() {
        return panel;
    }

    @Override
    public void reset(PixelStrip strip) {
        System.out.println("Chroma Started on Device: + " + strip.toString());
        this.pixels = new Color[strip.getPixelCount()];
    }

    @Override
    public boolean draw(PixelStrip strip) {
        Color c = Color.hsb(hue,saturation/100,brightness/100);
        if(solidColor) {
            for(int i = 0; i<strip.getPixelCount(); i++){
                pixels[i] = c;
            }
        } else {
            if(reverse){
                pixels[strip.getPixelCount()-1] = c;
                for(int i=0 ; i<strip.getPixelCount()-1; i++){ //Move color down the line;
                    pixels[i] = pixels[i+1];
                }
            } else {
                pixels[0] = c;
                for(int i = strip.getPixelCount()-1; i>0; i--){ //Move color down the line;
                    System.out.println(i);
                    pixels[i] = pixels[i-1];
                }
            }
        }
        for (int i = 0; i < strip.getPixelCount(); i++) {
            strip.setPixelColor(i, makeColorFromHSB(pixels[i]));
        }
        strip.show();
        if(hue >= 360){
            incrementFactor = -spread;
        } else if(hue <= 0){
            incrementFactor = spread;
        }
        hue += incrementFactor;
        try {
            Thread.sleep(frameDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
}
