
package Engine;

import javax.swing.JOptionPane;

// TODO: Auto-generated Javadoc
/**
 * The Class MessageOnce.
 */
public class MessageOnce
{
  
  /** The num alerts. */
  private static int numAlerts = 1;

  /**
   * Show alert.
   *
   * @param message the message
   */
  public static void showAlert(String message)
  {
    if(numAlerts > 0)
    {
      JOptionPane.showMessageDialog(null, message);

      numAlerts -= 1;
    }
  }
}
