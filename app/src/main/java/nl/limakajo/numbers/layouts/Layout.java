package nl.limakajo.numbers.layouts;

import java.util.Map;

import nl.limakajo.numbers.gameObjects.ScreenArea;
import nl.limakajo.numbers.gameObjects.TextBox;

public class Layout {

    Map<String, ScreenArea> screenAreas;
    Map<String, TextBox> textBoxes;

    public Map<String, ScreenArea> getScreenAreas() {
        return screenAreas;
    }

    public Map<String, TextBox> getTextBoxes() {
        return textBoxes;
    }

    public ScreenArea getScreenArea(String key) {
        return screenAreas.get(key);
    }

    public TextBox getTextBox(String key) {
        return textBoxes.get(key);
    }
}
