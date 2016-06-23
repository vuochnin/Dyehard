
package Engine;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

// TODO: Auto-generated Javadoc
/**
 * The Class BaseCode.
 */
public abstract class BaseCode// extends GameWindow
{
  
  /** The window. */
  protected GameWindow window = null;
  
  /** The resources. */
  public static ResourceHandler resources = null;
  
  /** The world. */
  public static World world = null;
  
  /** The random. */
  public static Random random = null;

  /** The keyboard. */
  protected KeyboardInput keyboard = null;
  
  /** The mouse. */
  protected MouseInput mouse = null;

  /** The frames per second. */
  // FPS
  private int framesPerSecond = 0;
  
  /** The frames per second prev. */
  private int framesPerSecondPrev = 0;
  
  /** The prev fps time. */
  private long prevFPSTime = (System.nanoTime() / 1000000);
  
  /** The cur fps time. */
  private long curFPSTime = prevFPSTime;

  /** The updates per second. */
  // UPS
  private int updatesPerSecond = 0;
  
  /** The updates per second prev. */
  private int updatesPerSecondPrev = 0;
  
  /** The prev ups time. */
  private long prevUPSTime = (System.nanoTime() / 1000000);
  
  /** The cur ups time. */
  private long curUPSTime = prevUPSTime;

  /** The text fps. */
  private Text textFPS = null;
  
  /** The text ups. */
  private Text textUPS = null;
  
  /** The text last key. */
  private Text textLastKey = null;
  
  /** The text client size. */
  private Text textClientSize = null;

  /** The show debug info. */
  private boolean showDebugInfo = false;

  /** The text status top. */
  private Text textStatusTop = null;
  
  /** The text status bottom. */
  private Text textStatusBottom = null;

  /**
   * Toggle whether debug information should be shown on screen.
   */
  public void toggleShowDebugInfo()
  {
    setShowDebugInfo(!showDebugInfo);
  }

  /**
   * Set whether debug information should be shown on screen.
   * 
   * @param value
   *          - True if debug information should be shown, false otherwise.
   */
  public void setShowDebugInfo(boolean value)
  {
    showDebugInfo = value;

    if(showDebugInfo)
    {
      textFPS.addToAutoDrawSet();
      textUPS.addToAutoDrawSet();
      textLastKey.addToAutoDrawSet();

      if(textClientSize != null)
      {
        textClientSize.addToAutoDrawSet();
      }
    }
    else
    {
      textFPS.removeFromAutoDrawSet();
      textUPS.removeFromAutoDrawSet();
      textLastKey.removeFromAutoDrawSet();

      if(textClientSize != null)
      {
        textClientSize.removeFromAutoDrawSet();
      }
    }
  }

  /**
   * Inits the config.
   *
   * @param theWindow the the window
   */
  public void initConfig(GameWindow theWindow)
  {
    window = theWindow;

    world = new World(window);

    resources = new ResourceHandler();
    resources.basePath = window.getBasePath();
    resources.setWorld(world);

    // Start listening for keyboard input
    keyboard = new KeyboardInput();
    window.addKeyListener(keyboard);

    random = new Random();

    // Start listening for mouse input
    mouse = new MouseInput();
    mouse.setWorld(world);
    window.addMouseListener(mouse);
    window.addMouseMotionListener(mouse);
  }

  /**
   * Echo to top status.
   *
   * @param text the text
   */
  protected void echoToTopStatus(String text)
  {
    textStatusTop.setText("Status: " + text);
    textStatusTop.visible = true;
  }

  /**
   * Echo to bottom status.
   *
   * @param text the text
   */
  protected void echoToBottomStatus(String text)
  {
    textStatusBottom.setText("Status: " + text);
    textStatusBottom.visible = true;
  }

  /**
   * Initialize world.
   */
  public void initializeWorld()
  {
    textFPS = new Text();
    textFPS.center.set(window.getWidth() - 120, 20);
    textFPS.setDrawInWorldCoords(false);
    textFPS.setText("FPS: " + framesPerSecondPrev);

    textUPS = new Text();
    textUPS.center.set(window.getWidth() - 120, 40);
    textUPS.setDrawInWorldCoords(false);
    textUPS.setText("UPS: " + updatesPerSecondPrev);

    textLastKey = new Text();
    textLastKey.center.set(window.getWidth() - 160, 60);
    textLastKey.setDrawInWorldCoords(false);
    textLastKey.setText("Last key: " + keyboard.getLastKey());

    /*
    textClientSize = new Text();
    textClientSize.center.set(window.getWidth() - 180, 80);
    textClientSize.setDrawInWorldCoords(false);
    textClientSize.setText("Client: " + window.getWidth() + ", " +
        window.getHeight());
    */

    textStatusTop = new Text();
    textStatusTop.center.set(10.0f, 30.0f);
    textStatusTop.setDrawInWorldCoords(false);
    textStatusTop.alwaysOnTop = true;
    textStatusTop.visible = false;
    textStatusTop.setBackColor(Color.BLACK);
    textStatusTop.setFrontColor(Color.RED);
    textStatusTop.setFontSize(28);
    textStatusTop.setText("Status:");

    textStatusBottom = new Text();
    textStatusBottom.center.set(10.0f, window.getHeight() - 20.0f);
    textStatusBottom.setDrawInWorldCoords(false);
    textStatusBottom.alwaysOnTop = true;
    textStatusBottom.visible = false;
    textStatusBottom.setBackColor(Color.BLACK);
    textStatusBottom.setFrontColor(Color.RED);
    textStatusBottom.setFontSize(28);
    textStatusBottom.setText("Status:");

    setShowDebugInfo(showDebugInfo);
  }

  /**
   * Update world.
   */
  public void updateWorld()
  {
    textStatusTop.visible = false;
    textStatusBottom.visible = false;

    // UPS
    {
      curUPSTime = (System.nanoTime() / 1000000);

      // Calculate FPS
      if(curUPSTime - prevUPSTime >= 1000)
      {
        prevUPSTime = curUPSTime;

        updatesPerSecondPrev = updatesPerSecond;
        updatesPerSecond = 0;

        if(textFPS != null)
        {
          textUPS.setText("UPS: " + updatesPerSecondPrev);
        }
      }

      updatesPerSecond += 1;
    }
  }

  /**
   * Update input.
   */
  public void updateInput()
  {
    keyboard.update();
    mouse.update();

    if(textLastKey != null)
    {
      textLastKey.setText("Last key: " + keyboard.getLastKey());
    }

    if(textClientSize != null)
    {
      textClientSize.setText("Client: " + window.getWidth() + ", " +
          window.getHeight());
    }
  }

  /**
   * Draw.
   *
   * @param gfx the gfx
   */
  public void draw(Graphics gfx)
  {
    resources.setGraphics(gfx);

    resources.drawDrawSet();

    // FPS
    {
      curFPSTime = (System.nanoTime() / 1000000);

      // Calculate FPS
      if(curFPSTime - prevFPSTime >= 1000)
      {
        prevFPSTime = curFPSTime;

        framesPerSecondPrev = framesPerSecond;
        framesPerSecond = 0;

        if(textFPS != null)
        {
          textFPS.setText("FPS: " + framesPerSecondPrev);
        }
      }

      framesPerSecond += 1;
    }
  }

  /**
   * Clean.
   */
  public void clean()
  {
    resources.clean();
  }
}
