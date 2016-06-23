package dyehard.GameScreens;

import Engine.BaseCode;
import dyehard.DyehardRectangle;

// TODO: Auto-generated Javadoc
/**
 * The Class StartScreen.
 */
public class StartScreen {
    
    /** The screen. */
    private final DyehardRectangle screen;

    /**
     * Instantiates a new start screen.
     */
    public StartScreen() {
        screen = new DyehardRectangle();
        screen.size.set(BaseCode.world.getWidth(), BaseCode.world.getHeight());
        screen.center.set(BaseCode.world.getWidth() / 2,
                BaseCode.world.getHeight() / 2);
        screen.texture = BaseCode.resources
                .getImage("Textures/UI/DyeHard_StartScreen.png");
        screen.alwaysOnTop = true;
        screen.visible = true;
    }

    /**
     * Show screen.
     *
     * @param show the showscreen
     */
    public void showScreen(boolean show) {
        if (show) {
            BaseCode.resources.moveToFrontOfDrawSet(screen);
        } else {
            screen.texture = null;
        }
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
