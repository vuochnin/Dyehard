package dyehard.Ui;

import Engine.BaseCode;
import Engine.Vector2;
import dyehard.DyehardRectangle;

// TODO: Auto-generated Javadoc
/**
 * The Class ClickableMenuLayer.
 */
public class ClickableMenuLayer extends DyehardRectangle{

    /**
     * Instantiates a new clickable menu layer.
     *
     * @param pos the pos
     */
    public ClickableMenuLayer(Vector2 pos) {
        size = new Vector2(2.8f, 2.8f);
        center = new Vector2(pos);
        texture = BaseCode.resources
                .loadImage("Textures/UI/UI_Menu_Select.png");
        alwaysOnTop = true;
        visible = false;
    }

    /**
     * Active.
     *
     * @param active the active
     */
    public void active(boolean active) {
        if (active) {
            BaseCode.resources.moveToFrontOfDrawSet(this);
        }
        visible = active;
    }

    /**
     * Move.
     *
     * @param pos the pos
     */
    public void move(Vector2 pos) {
        if (pos != center) {
            center = pos;
            active(true);
        }
    }
}