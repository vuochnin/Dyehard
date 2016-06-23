package dyehard;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import Engine.LibraryCode;
import dyehard.GameScreens.BackgroundScreen;

// TODO: Auto-generated Javadoc
/**
 * The Class DyeHardGame.
 */
//TODO: think about this: should this be a singleton class - use getInstance() to interact with it
public abstract class DyeHardGame extends LibraryCode {
	 
 	/** The state. */
	 // Game state
    private static State state;
		
	/**
	 * The Enum State.
	 */
	public enum State {
		
		/** Begin Game */
		BEGIN,
		
		/** Game Paused. */
		PAUSED, 
		
		/** Game is being played. */
		PLAYING, 
		
		/** Game Over! */
		GAMEOVER, 
		
		/** Quit the game */
		QUIT, 
		
		/** Menu */
		MENU, 
		
		/** Restart the game. */
		RESTART
	}

    /* (non-Javadoc)
     * @see Engine.BaseCode#initializeWorld()
     */
    @Override
    public void initializeWorld() {
    	
        super.initializeWorld();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("resources/DyeHard_Cursor.png");
        
        Dimension cursorSize = Toolkit.getDefaultToolkit().getBestCursorSize(
                image.getHeight(window), image.getHeight(window));
        
        Point point = new Point((int) (cursorSize.getWidth() / 2.0),
                (int) (cursorSize.getHeight() / 2.0)); // hot point to middle
        
        Cursor cursor = toolkit.createCustomCursor(image, point, "cursor");
        window.setCursor(cursor);
        window.addMouseListener(mouse);

        resources.setClassInJar(this);

        state = State.PLAYING;

        // preload the gate path images
        dyehard.World.WormHole.setGatePathImages();

        new BackgroundScreen();

        initialize(); // call user code Initialize()
    }
    
    /* (non-Javadoc)
     * @see Engine.BaseCode#updateWorld()
     */
    @Override
    public void updateWorld() {
        update(); // call user code update()
    }
    
    /**
     * Sets the state.
     *
     * @param newState the new state
     */
    public static void setState(State newState) {
    	state = newState;
    }
    
    /**
     * Gets the state.
     *
     * @return the state
     */
    public static State getState() {
        return state;
    }

    /**
     * Update.
     */
    protected abstract void update();
    
    /**
     * Initialize.
     */
    protected abstract void initialize();

}
