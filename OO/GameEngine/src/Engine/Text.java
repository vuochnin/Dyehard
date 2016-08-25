
package Engine;

import java.awt.Color;
import java.awt.Font;

// TODO: Auto-generated Javadoc
/**
 * The Class Text.
 */
public class Text extends Primitive
{
  
  /** The text. */
  private String text = "UNSET_TEXT";
  
  /** The draw in world coords. */
  private boolean drawInWorldCoords = true;
  
  /** The font name. */
  private String fontName = "Times New Roman";
  
  /** The font size. */
  private int fontSize = 24;

  /** The color back. */
  private Color colorBack = Color.WHITE;
  
  /** The color front. */
  private Color colorFront = Color.BLACK;

  /** The secondary font access. */
  private Font secondaryFontAccess;
  
  /**
   * Instantiates a new text.
   */
  public Text()
  {
    alwaysOnTop = true;
  }

  /**
   * Instantiates a new text.
   *
   * @param value the value
   */
  public Text(String value)
  {
    alwaysOnTop = true;

    setText(value);
  }

  /**
   * Instantiates a new text.
   *
   * @param value the value
   * @param x the x
   * @param y the y
   */
  public Text(String value, float x, float y)
  {
    center.set(x, y);

    alwaysOnTop = true;

    setText(value);
  }

  /**
   * Sets the back color.
   *
   * @param value the new back color
   */
  public void setBackColor(Color value)
  {
    colorBack = value;
  }

  /**
   * Sets the front color.
   *
   * @param value the new front color
   */
  public void setFrontColor(Color value)
  {
    colorFront = value;
  }

  /**
   * Sets the text.
   *
   * @param value the new text
   */
  public void setText(String value)
  {
    text = value;
  }

  /**
   * Gets the text.
   *
   * @return the text
   */
  public String getText()
  {
    return text;
  }

  /**
   * Sets the font name.
   *
   * @param value the new font name
   */
  public void setFontName(String value)
  {
    fontName = value;
  }
  
  /**
   * Sets the font.
   *
   * @param font the new font
   */
  public void setFont(Font font)
  {
	secondaryFontAccess = font;
  }

  /**
   * Sets the font size.
   *
   * @param value the new font size
   */
  public void setFontSize(int value)
  {
    fontSize = value;
  }

  /**
   * Set whether the text should be positioned in world coordinates or pixel
   * coordinates.
   * 
   * @param value
   *          - True to draw in world coordinates, false to draw in pixel
   *          coordinates.
   */
  public void setDrawInWorldCoords(boolean value)
  {
    drawInWorldCoords = value;
  }

  /**
   * Get the width of the text in world coordinates.
   * 
   * @return - Width of the text in world coordinates.
   */
  public float getWidth()
  {
    return BaseCode.resources.getTextWidth(text);
  }

  /**
   * Get the width of the text in pixel coordinates.
   * 
   * @return - Width of the text in pixel coordinates.
   */
  public float getWidthABS()
  {
    return BaseCode.resources.getTextWidth(text);
  }

  /* (non-Javadoc)
   * @see Engine.Primitive#draw()
   */
  public void draw()
  {
	if(secondaryFontAccess != null)
		BaseCode.resources.setFont(secondaryFontAccess, fontSize);
	else
		BaseCode.resources.setFont(fontName, fontSize);

    BaseCode.resources.setTextBackColor(colorBack);
    BaseCode.resources.setTextFrontColor(colorFront);

    if(drawInWorldCoords)
    {
      BaseCode.resources.drawText(text, center.getX(), center.getY(), rotate);
    }
    else
    {
      BaseCode.resources
          .drawTextABS(text, center.getX(), center.getY(), rotate);
    }
  }

  /* (non-Javadoc)
   * @see Engine.Primitive#update()
   */
  public void update()
  {
  }
}
