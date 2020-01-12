package lightingZone.ledZone.skills.chroma;

import devices.ControllableItem;
import devices.fadecandy.opc.Animation;
import devices.fadecandy.opc.PixelStrip;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import lightingZone.ledZone.LedZone;
import lightingZone.ledZone.skills.Skill;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;

public class Chroma extends Animation implements Skill, Initializable {

    private HashSet<ControllableItem> devices;

    private Parent panel;

    private double incrementFactor = 1.0;
    private double hue = 0.0;
    private Color[] pixels;

    private @FXML Slider delaySlider;
    private @FXML Slider spreadSlider;
    private @FXML Slider brightnessSlider;
    private @FXML Slider saturationSlider;
    private @FXML TextField delayText;
    private @FXML TextField spreadText;
    private @FXML TextField brightnessText;
    private @FXML TextField saturationText;
    private @FXML CheckBox reverseCheckbox;
    private @FXML CheckBox solidCheckbox;

    //Default Options
    private final boolean defSolidColor = false;
    private final boolean defReverse = false;
    private final int defFrameDelay = 0;
    private final int defSpread = 3;
    private final int defBrightness = 100;
    private final int defSaturation = 100;

    //Options
    private boolean solidColor = defSolidColor;
    private boolean reverse = defReverse;
    private int frameDelay = defFrameDelay;
    private int spread = defSpread;
    private int brightness = defBrightness;
    private int saturation = defSaturation;

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
    public void initialize(URL url, ResourceBundle resourceBundle) {
        delaySlider.valueProperty().addListener(e->{
            valueChanged();
            updateText();
        });
        spreadSlider.valueProperty().addListener(e->{
            valueChanged();
            updateText();
        });
        brightnessSlider.valueProperty().addListener(e->{
            valueChanged();
            updateText();
        });
        saturationSlider.valueProperty().addListener(e->{
            valueChanged();
            updateText();
        });
        delaySlider.setValue(defFrameDelay);
        spreadSlider.setValue(defSpread);
        brightnessSlider.setValue(defBrightness);
        saturationSlider.setValue(defSaturation);
    }

    private @FXML void valueChanged(){
        frameDelay = (int)delaySlider.getValue();
        spread = (int)spreadSlider.getValue();
        brightness = (int)brightnessSlider.getValue();
        saturation = (int) saturationSlider.getValue();
        solidColor = solidCheckbox.selectedProperty().get();
        reverse = reverseCheckbox.selectedProperty().get();
    }

    private void updateText(){
        delayText.setText(""+(int) delaySlider.getValue());
        spreadText.setText(""+(int)spreadSlider.getValue());
        brightnessText.setText(""+(int)brightnessSlider.getValue());
        saturationText.setText(""+(int)saturationSlider.getValue());
    }

    private @FXML void textChanged(){
        delaySlider.setValue(Integer.parseInt(delayText.getText()));
        spreadSlider.setValue(Integer.parseInt(spreadText.getText()));
        brightnessSlider.setValue(Integer.parseInt(brightnessText.getText()));
        saturationSlider.setValue(Integer.parseInt(saturationText.getText()));
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
        Color c = Color.hsb(hue,((double) saturation)/100.0, ((double) brightness)/100.0);
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
                    pixels[i] = pixels[i-1];
                }
            }
        }
        System.out.println("Increment Factor: " + incrementFactor);
        System.out.println("Hue" + pixels[0].getHue());
        System.out.println("Integer" + makeColorFromHSB(pixels[0]));
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
