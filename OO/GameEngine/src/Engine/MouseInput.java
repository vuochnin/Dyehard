
package Engine;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

// TODO: Auto-generated Javadoc
/**
 * The Class MouseInput.
 */
public class MouseInput extends ButtonsInput implements MouseListener,
    MouseMotionListener
{
  
  /** The mouse pos. */
  private Vector2 mousePos = new Vector2();
  
  /** The mouse on screen. */
  private boolean mouseOnScreen = false;
  
  /** The world. */
  private World world = null;

  //
  //
  //

  /**
   * Set the world that will be used to convert between pixel space and world
   * space.
   * 
   * @param aWorld
   *          - The world to use.
   */
  public void setWorld(World aWorld)
  {
    world = aWorld;
  }

  /**
   * Get the world x coordinate of the mouse.
   * 
   * @return The x world coordinate of the mouse.
   */
  public float getWorldX()
  {
    if(world != null)
    {
      return world.screenToWorldX(mousePos.getX());
    }

    return 0.0f;
  }

  /**
   * Get the world y coordinate of the mouse.
   * 
   * @return The y world coordinate of the mouse.
   */
  public float getWorldY()
  {
    if(world != null)
    {
      return world.screenToWorldY(mousePos.getY());
    }

    return 0.0f;
  }

  /**
   * Get the pixel x coordinate of the mouse.
   * 
   * @return The x pixel coordinate of the mouse.
   */
  public float getPixelX()
  {
    return mousePos.getX();
  }

  /**
   * Get the pixel y coordinate of the mouse.
   * 
   * @return The y pixel coordinate of the mouse.
   */
  public float getPixelY()
  {
    return mousePos.getY();
  }
  
  /**
   * Mouse on screen.
   *
   * @return true, if successful
   */
  public boolean MouseOnScreen(){
	  return mouseOnScreen;
  }

  //
  // Listener methods
  //

  /* (non-Javadoc)
   * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
   */
  public void mouseClicked(MouseEvent e)
 {
  }

  /* (non-Javadoc)
   * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
   */
  public void mouseEntered(MouseEvent e){
	  mouseOnScreen = true;
  }

  /* (non-Javadoc)
   * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
   */
  public void mouseExited(MouseEvent e){
	  mouseOnScreen = false;
  }

  /* (non-Javadoc)
   * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
   */
  public void mousePressed(MouseEvent e)
  {
    int button = e.getButton();

    pressButton(button);
  }

  /* (non-Javadoc)
   * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
   */
  public void mouseReleased(MouseEvent e)
  {
    int button = e.getButton();

    releaseButton(button);
  }

  /* (non-Javadoc)
   * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
   */
  public void mouseDragged(MouseEvent e)
  {
    mousePos.setX(e.getX());
    mousePos.setY(e.getY());
  }

  /* (non-Javadoc)
   * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
   */
  public void mouseMoved(MouseEvent e)
  {
    mousePos.setX(e.getX());
    mousePos.setY(e.getY());
  }
}
