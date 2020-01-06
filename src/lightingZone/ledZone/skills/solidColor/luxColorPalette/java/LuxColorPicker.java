/* Copyright 2018 Jesper Öqvist
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package lightingZone.ledZone.skills.solidColor.luxColorPalette.java;

import deviceConfigurator.fcServerSettings.fcBoard.FCStripSettings.FCStrip;
import devices.ControllableItem;
import devices.opc.Animation;
import devices.opc.OpcClient;
import devices.opc.PixelStrip;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import javafx.stage.Window;

import java.util.HashSet;

/**
 * A simple HSV color picker control for JavaFX.
 *
 * <p>The control consists of a button which can be clicked to bring up
 * a color palette. The button has an icon displaying the currently selected
 * color. The color palette uses a Hue gradient selector and a HSV 2D gradient.
 * The color palette also has color swatches with neighbour colors and
 * previously selected colors are.
 */
public class LuxColorPicker extends Button {

  private Color originalColor = Color.CRIMSON;
  private ObjectProperty<Color> color = new SimpleObjectProperty<>(Color.CRIMSON);
  private final Popup popup;
  private final LuxColorPalette palette;

  private HashSet<ControllableItem> controllingDevices;
  private Animation controllingAnimation;

  public LuxColorPicker(HashSet<ControllableItem> controllingDevices, Animation controllingAnimation) {
    this.controllingDevices = controllingDevices;
    this.controllingAnimation = controllingAnimation;
    setText("Pick Color");
    palette = new LuxColorPalette(this);
    popup = new Popup();
    popup.getContent().add(palette);
    Rectangle colorSample = new Rectangle(12, 12);
    colorSample.setStroke(Color.DARKGRAY);
    colorSample.setStrokeWidth(1);
    colorSample.fillProperty().bind(color);
    setGraphic(colorSample);
    setOnAction(event -> {
      originalColor = getColor();
      palette.setColor(originalColor);
      Scene scene = getScene();
      Window window = scene.getWindow();
      popup.show(window);
      popup.setAutoHide(true);
      popup.setOnAutoHide(event2 -> updateHistory());
      Bounds buttonBounds = getBoundsInLocal();
      Point2D point = localToScreen(buttonBounds.getMinX(), buttonBounds.getMaxY());
      popup.setX(point.getX() - 9);
      popup.setY(point.getY() - 9);
    });
  }

  /**
   * Store the current color in the history palette.
   */
  protected void updateHistory() {
    palette.addToHistory(color.get());
  }

  public ObjectProperty<Color> colorProperty() {
    return color;
  }

  public LuxColorPalette getPalette() {
    return palette;
  }

  public void setColor(Color value) {
    color.set(value);
    palette.updateRandomColorSamples();
  }

  public void colorChanged(){
    for(ControllableItem d : controllingDevices){
      OpcClient server = ((FCStrip)d).getParentFCManager().getServerManager().getOPC();
      PixelStrip strip = ((FCStrip)d).getOPC();
      strip.setAnimation(controllingAnimation);
    }
  }

  public Color getColor() {
    return color.get();
  }

  /**
   * Hide the color picker popup.
   */
  public void hidePopup() {
    popup.hide();
  }

  public void revertToOriginalColor() {
    setColor(originalColor);
  }
}
