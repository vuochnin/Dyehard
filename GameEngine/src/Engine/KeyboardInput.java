
package Engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// TODO: Auto-generated Javadoc
/**
 * The Class KeyboardInput.
 */
public class KeyboardInput extends ButtonsInput implements KeyListener
{
  
  /** The last key. */
  private String lastKey = "";

  /**
   * Get the most recent key that was pressed.
   * 
   * @return - The name of the key that was pressed last.
   */
  public String getLastKey()
  {
    return lastKey;
  }

  //
  // Listener methods
  //

  /* (non-Javadoc)
   * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
   */
  public void keyPressed(KeyEvent e)
  {
    pressButton(e.getKeyCode());
    lastKey = KeyEvent.getKeyText(e.getKeyCode());
  }

  /* (non-Javadoc)
   * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
   */
  public void keyReleased(KeyEvent e)
  {
    releaseButton(e.getKeyCode());
  }

  /* (non-Javadoc)
   * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
   */
  public void keyTyped(KeyEvent e)
  {
  }
}
