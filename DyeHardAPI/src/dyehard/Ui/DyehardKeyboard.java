package dyehard.Ui;

import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import Engine.KeyboardInput;

// TODO: Auto-generated Javadoc
/**
 * The Class DyehardKeyboard.
 */
public class DyehardKeyboard extends KeyboardInput {

    /** The instance. */
    static DyehardKeyboard instance = null;

    /**
     * Checks if is key tapped.
     *
     * @param key the key
     * @return true, if is key tapped
     */
    public static boolean isKeyTapped(int key) {
        return instance != null && instance.isButtonTapped(key);
    }

    /**
     * Checks if is key down.
     *
     * @param key the key
     * @return true, if is key down
     */
    public static boolean isKeyDown(int key) {
        return instance != null && instance.isButtonDown(key);
    }

    /** The key presses. */
    Set<KeyEvent> keyPresses = new HashSet<KeyEvent>();
    
    /** The key releases. */
    Set<KeyEvent> keyReleases = new HashSet<KeyEvent>();
    
    /** The last key press. */
    protected String lastKeyPress;

    /**
     * Instantiates a new dyehard keyboard.
     */
    public DyehardKeyboard() {
        assert (instance == null);

        instance = this;
    }

    /* (non-Javadoc)
     * @see Engine.KeyboardInput#getLastKey()
     */
    @Override
    public String getLastKey() {
        return lastKeyPress;
    }

    /* (non-Javadoc)
     * @see Engine.KeyboardInput#keyPressed(java.awt.event.KeyEvent)
     */
    @Override
    public void keyPressed(KeyEvent key) {
        keyPresses.add(key);
        lastKeyPress = KeyEvent.getKeyText(key.getKeyCode());
    }

    /* (non-Javadoc)
     * @see Engine.KeyboardInput#keyReleased(java.awt.event.KeyEvent)
     */
    @Override
    public void keyReleased(KeyEvent key) {
        keyReleases.add(key);
    }

    /* (non-Javadoc)
     * @see Engine.KeyboardInput#keyTyped(java.awt.event.KeyEvent)
     */
    @Override
    public void keyTyped(KeyEvent arg0) {
        // do nothing
    }

    /* (non-Javadoc)
     * @see Engine.ButtonsInput#update()
     */
    @Override
    public void update() {
        super.update();

        for (KeyEvent key : keyPresses) {
            pressButton(key.getKeyCode());
        }
        keyPresses.clear();

        for (KeyEvent key : keyReleases) {
            releaseButton(key.getKeyCode());
        }
        keyReleases.clear();
    }

}
