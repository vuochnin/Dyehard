
package Engine;

import java.util.Vector;

// TODO: Auto-generated Javadoc
/**
 * The Class ButtonsInput.
 */
public class ButtonsInput
{
  
  /**
   * The Enum ButtonState.
   */
  public enum ButtonState
  {
    
    /** The up. */
    UP, 
 /** The tapped. */
 TAPPED, 
 /** The pressed. */
 PRESSED, 
 /** The released. */
 RELEASED
  };

  /**
   * The Class ButtonData.
   */
  protected class ButtonData
  {
    
    /**
     * Instantiates a new button data.
     *
     * @param theKey the the key
     */
    public ButtonData(int theKey)
    {
      key = theKey;
    }

    /** The key. */
    public int key = 0;
    
    /** The state. */
    public ButtonState state = ButtonState.TAPPED;
  }

  /** The button states. */
  protected Vector<ButtonData> buttonStates = new Vector<ButtonData>();

  /**
   * Gets the button data.
   *
   * @param key the key
   * @return the button data
   */
  protected ButtonData getButtonData(int key)
  {
    for(int i = 0; i < buttonStates.size(); i++)
    {
      if(buttonStates.get(i).key == key)
      {
        return buttonStates.get(i);
      }
    }

    return null;
  }

  /**
   * Checks if is button tapped.
   *
   * @param button the button
   * @return true, if is button tapped
   */
  public boolean isButtonTapped(int button)
  {
    ButtonData data = getButtonData(button);

    return (data != null && data.state == ButtonState.TAPPED);
  }

  /**
   * Checks if is button down.
   *
   * @param button the button
   * @return true, if is button down
   */
  public boolean isButtonDown(int button)
  {
    ButtonData data = getButtonData(button);

    return (data != null && (data.state == ButtonState.TAPPED || data.state == ButtonState.PRESSED));
  }

  /**
   * Press button.
   *
   * @param button the button
   */
  public void pressButton(int button)
  {
    ButtonData data = getButtonData(button);

    if(data == null)
    {
      buttonStates.add(new ButtonData(button));
    }
    else if(data.state == ButtonState.UP)
    {
      data.state = ButtonState.TAPPED;
    }
  }

  /**
   * Release button.
   *
   * @param button the button
   */
  public void releaseButton(int button)
  {
    ButtonData data = getButtonData(button);

    if(data != null &&
        (data.state == ButtonState.TAPPED || data.state == ButtonState.PRESSED))
    {
      data.state = ButtonState.RELEASED;
    }
  }

  /**
   * Update.
   */
  public void update()
  {
    ButtonData current;

    // Update key states
    for(int i = 0; i < buttonStates.size(); i++)
    {
      current = buttonStates.get(i);

      // Tapped to pressed
      if(current.state == ButtonState.TAPPED)
      {
        current.state = ButtonState.PRESSED;
      }
      // Released to up
      else if(current.state == ButtonState.RELEASED)
      {
        //current.state = ButtonState.UP;

        buttonStates.remove(i);
        i -= 1;
      }
    }
  }
}
