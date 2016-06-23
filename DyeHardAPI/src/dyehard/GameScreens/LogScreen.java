package dyehard.GameScreens;

import Engine.BaseCode;
import dyehard.DyehardRectangle;

// TODO: Auto-generated Javadoc
/**
 * The Class LogScreen.
 */
public class LogScreen {
    
    /** The screen. */
    private final DyehardRectangle screen;

    /**
     * Instantiates a new log screen.
     */
    public LogScreen() {
        screen = new DyehardRectangle();
        screen.size.set(40f, 11f);
        screen.center.set(BaseCode.world.getWidth() / 2,
                BaseCode.world.getHeight() / 2);
        screen.texture = BaseCode.resources
                .loadImage("Textures/UI/UI_Start.png");
        screen.alwaysOnTop = true;
        screen.visible = false;
    }

    /**
     * Show screen.
     *
     * @param show the showscreen
     */
    public void showScreen(boolean show) {
        screen.visible = show;
    }

    /**
     * Checks if is shown.
     *
     * @return true, if is shown
     */
    public boolean isShown() {
        return screen.visible;
    }
}
