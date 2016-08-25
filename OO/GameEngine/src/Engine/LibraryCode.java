
package Engine;

// TODO: Auto-generated Javadoc
/**
 * The Class LibraryCode.
 */
public abstract class LibraryCode extends BaseCode
{
  
  /** The background. */
  private Rectangle background = null;

  /** The Constant INIT_WIDTH. */
  private static final int INIT_WIDTH = 800;
  
  /** The Constant INIT_HEIGHT. */
  private static final int INIT_HEIGHT = 480;
  
  /* (non-Javadoc)
   * @see Engine.BaseCode#initConfig(Engine.GameWindow)
   */
  public void initConfig(GameWindow theWindow)
  {
    super.initConfig(theWindow);

    //window.setSize(800, 600);
    //window.setSize(1200, 675);
    window.setSize(getInitWidth(), getInitHeight());

    //world.SetWorldCoordinate(134.0f);
    world.SetWorldCoordinate(100.0f);
    world.setPosition(0.0f, 0.0f);
  }
  
  /**
   * Gets the inits the width.
   *
   * @return the inits the width
   */
  public int getInitWidth()
  {
	return INIT_WIDTH;  
  }
  
  /**
   * Gets the inits the height.
   *
   * @return the inits the height
   */
  public int getInitHeight()
  {
	return INIT_HEIGHT;
  }

  /**
   * Gets the background image.
   *
   * @return the background image
   */
  protected Rectangle getBackgroundImage()
  {
    return background;
  }

  /**
   * Sets the background image.
   *
   * @param image the new background image
   */
  protected void setBackgroundImage(String image)
  {
    if(background == null)
    {
      background = new Rectangle();
      background.center.set(world.getWidth() * 0.5f, world.getHeight() * 0.5f);
      background.size.set(world.getWidth(), world.getHeight());
      resources.moveToBackOfDrawSet(background);
    }

    background.setImage(image);

    if(background.texture == null)
    {
      background.visible = false;
    }
    else
    {
      background.visible = true;
    }
  }
}
