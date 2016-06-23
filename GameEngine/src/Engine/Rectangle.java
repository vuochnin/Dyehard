
package Engine;

import java.awt.image.BufferedImage;

// TODO: Auto-generated Javadoc
// Sprite sheet code ported from the C# engine, which
/**
 * The Class Rectangle.
 */
// is credited to Samuel Cook and Ron Cook for adding support for that.
public class Rectangle extends Primitive
{

  /**
   * The Enum SpriteSheetAnimationMode.
   */
  public enum SpriteSheetAnimationMode
  {
    
    /** The animate forward. */
    ANIMATE_FORWARD,         // Animates from first frame to last frame.
    
    /** The animate backward. */
         ANIMATE_BACKWARD,        // Animates from last frame to first.
    
    /** The animate swing. */
        ANIMATE_SWING,           // Animates from first to last then to first
                             //   again and so forth.
    
    /** The animate forward stop. */
           ANIMATE_FORWARD_STOP,    // Animates from first to last frame then
                             //   stops on final frame.
    
    /** The animate backwords stop. */
    ANIMATE_BACKWORDS_STOP   // Animates from last to first frame then
                             //   stops on first frame.
  };
  
  /**
   * frameCoords holds the lower left and upper right coordinates of each
   * frame of the sprite sheet.
   * frameCoords[][0] = lower left x
   * frameCoords[][1] = lower left y
   * frameCoords[][2] = upper right x
   * frameCoords[][3] = upper right y
   */
  private int[][] mFrameCoords;
  
  /** The m current frame. */
  private int mCurrentFrame;
  
  /** The m total frames. */
  private int mTotalFrames;
  
  /** The m first frame. */
  private int mFirstFrame;
  
  /** The m last frame. */
  private int mLastFrame;
  
  /** The m ticks per frame. */
  private int mTicksPerFrame;
  
  /** The m current tick. */
  private int mCurrentTick;
  
  /** The m swing direction left. */
  private boolean mSwingDirectionLeft = false; // For swing animation, if false, go from
                                               // first to last frame, if true go from
                                               // last to first frame.
  
  /** The m draw image. */
                                               private boolean mDrawImage = true;
  
  /** The m draw filled rect. */
  private boolean mDrawFilledRect = true;
  
  /** The m animation mode. */
  private SpriteSheetAnimationMode mAnimationMode = SpriteSheetAnimationMode.ANIMATE_FORWARD;
  
  /** The m using sprite sheet. */
  private boolean mUsingSpriteSheet = false;
  
  /** The m horizontal flip. */
  private boolean mHorizontalFlip = false;     // Flips on horizontal axis. (Up and Down swapped)
  
  /** The m vertical flip. */
  private boolean mVerticalFlip = false;       // Flips on vertical axis (Left and Right swapped)
  
  

  /**
   * Instantiates a new rectangle.
   */
  public Rectangle()
  {
    super();
  }

  /**
   * Instantiates a new rectangle.
   *
   * @param other the other
   */
  public Rectangle(Rectangle other)
  {
    super(other);

    mDrawImage = other.mDrawImage;
    mDrawFilledRect = other.mDrawFilledRect;
  }
  
  /**
   * Using sprite sheet.
   *
   * @return true, if successful
   */
  public boolean usingSpriteSheet()
  {
	  return mUsingSpriteSheet;
  }
  
  /**
   * Sets the using sprite sheet.
   *
   * @param usingSpriteSheet the new using sprite sheet
   */
  public void setUsingSpriteSheet(boolean usingSpriteSheet)
  {
	  this.mUsingSpriteSheet = usingSpriteSheet;
  }
  
  /**
   * Gets the horizontal flip.
   *
   * @return the horizontal flip
   */
  public boolean getHorizontalFlip()
  {
	  return mHorizontalFlip;
  }
  
  /**
   * Sets the horizontal flip.
   *
   * @param horizontalFlip the new horizontal flip
   */
  public void setHorizontalFlip(boolean horizontalFlip)
  {
	  mHorizontalFlip = horizontalFlip;
  }
  
  /**
   * Gets the vertical flip.
   *
   * @return the vertical flip
   */
  public boolean getVerticalFlip()
  {
	  return mVerticalFlip;
  }
  
  /**
   * Sets the vertical flip.
   *
   * @param verticalFlip the new vertical flip
   */
  public void setVerticalFlip(boolean verticalFlip)
  {
	  mVerticalFlip = verticalFlip;
  }
  
  /**
   * Sets the draw image.
   *
   * @param value the new draw image
   */
  public void setDrawImage(boolean value)
  {
    mDrawImage = value;
  }

  /**
   * Sets the draw filled rect.
   *
   * @param value the new draw filled rect
   */
  public void setDrawFilledRect(boolean value)
  {
    mDrawFilledRect = value;
  }
  /**
   * Sets up the spritesheet animation.
   * 
   * Will do nothing if width, height, or totalFrame are <= 0.
   * 
   * @param textureFilename Filename of the spritesheet image
   * @param width Width of individual sprite.
   * @param height Height of individual sprite.
   * @param totalFrames Total sprites in spritesheet
   * @param ticksPerFrame How long each sprite remains before the next is drawn.
   */
  public void setSpriteSheet(String textureFilename, int width, int height, int totalFrames, int ticksPerFrame)
  {
	  if(totalFrames <= 0 || width <= 0 || height <= 0)
	  {
		  return;
	  }
	  setImage(textureFilename);
	  initializeSpriteSheet(width,height,totalFrames,ticksPerFrame);
  }
  
  /**
   * Sets up the spritesheet animation.
   * 
   * Will do nothing if width, height, or totalFrame are <= 0.
   *
   * @param texture the texture
   * @param width Width of individual sprite.
   * @param height Height of individual sprite.
   * @param totalFrames Total sprites in spritesheet
   * @param ticksPerFrame How long each sprite remains before the next is drawn.
   */
  public void setSpriteSheet(BufferedImage texture, int width, int height, int totalFrames, int ticksPerFrame)
  {
	  if(totalFrames <= 0 || width <= 0 || height <= 0)
	  {
		  return;
	  }
	  this.texture = texture;
	  initializeSpriteSheet(width,height,totalFrames,ticksPerFrame);
  }
  
  /**
   * Initialize sprite sheet.
   *
   * @param width the width
   * @param height the height
   * @param totalFrames the total frames
   * @param ticksPerFrame the ticks per frame
   */
  private void initializeSpriteSheet(int width, int height, int totalFrames, int ticksPerFrame)
  {
	  mFrameCoords = new int[totalFrames][4];
	  this.mTotalFrames = totalFrames;
	  mCurrentFrame = 0;
	  
	  mCurrentTick = 0;
	  this.mTicksPerFrame = ticksPerFrame;
	  
	  int currentFrameX = 0;
	  int currentFrameY = 0;
	  
	  for(int frame = 0; frame < totalFrames; frame++)
	  {
		  if(currentFrameX + width > texture.getWidth())
		  {
			  currentFrameX = 0;
			  currentFrameY += height;
		  }
		  if(currentFrameY + height > texture.getHeight())
		  {
			  break;
		  }
		  mFrameCoords[frame][0] = currentFrameX + width;
		  mFrameCoords[frame][1] = currentFrameY + height;
		  mFrameCoords[frame][2] = currentFrameX;
		  mFrameCoords[frame][3] = currentFrameY;
		  
		  currentFrameX += width;
	  }
	  mFirstFrame = 0;
	  mLastFrame = mTotalFrames - 1;
  }
  /**
   * Sets the mode of animation for the spritesheet animation.
   * Will do nothing if firstFrame or lastFrame is less than 0.
   * 
   * During the animation, if LastFrame exceeds mTotalFrames,
   *  an error will occur.
   * 
   * @param firstFrame First frame in the spritesheet to animate.
   * @param lastFrame  Last frame in the spritesheet to animate.
   * @param animationMode The type of animation that will be followed.
   */
  public void setAnimationMode(int firstFrame, int lastFrame,
		  SpriteSheetAnimationMode animationMode)
  {
	  if(firstFrame < 0 || lastFrame < 0)
		  return;
	  
	  mFirstFrame = firstFrame;
	  mLastFrame = lastFrame;
	  mAnimationMode = animationMode;
	  
	  if(animationMode == SpriteSheetAnimationMode.ANIMATE_BACKWARD ||
			  animationMode == SpriteSheetAnimationMode.ANIMATE_BACKWORDS_STOP)
	  {
		  mCurrentFrame = mLastFrame;
	  }
  }
  
 

  /**
   * Gets the sprite upper x.
   *
   * @return the sprite upper x
   */
  // Helper getters for drawing a portion of an image(the spritesheet)
  private int getSpriteUpperX()
  {
	  return mFrameCoords[mCurrentFrame][0];
  }
  
  /**
   * Gets the sprite upper y.
   *
   * @return the sprite upper y
   */
  private int getSpriteUpperY()
  {
	  return mFrameCoords[mCurrentFrame][1];
  }
  
  /**
   * Gets the sprite lower x.
   *
   * @return the sprite lower x
   */
  private int getSpriteLowerX()
  {
	  return mFrameCoords[mCurrentFrame][2];
  }
  
  /**
   * Gets the sprite lower y.
   *
   * @return the sprite lower y
   */
  private int getSpriteLowerY()
  {
	  return mFrameCoords[mCurrentFrame][3];
  }
  
  // Helper getters for drawing onto the screen.
  /**
   * Gets the on screen lower x.
   *
   * @return the on screen lower x
   */
  // Swapping these values allows vertical and horizontal flips.
  private float getOnScreenLowerX()
  {
	  if(mVerticalFlip)
		  return center.getX() + (size.getX() * 0.5f);
	  return center.getX() - (size.getX() * 0.5f);
  }
  
  /**
   * Gets the on screen lower y.
   *
   * @return the on screen lower y
   */
  private float getOnScreenLowerY()
  {
	  if(mHorizontalFlip)
		  return center.getY() + (size.getY() * 0.5f);
	  return center.getY() - (size.getY() * 0.5f);
  }
  
  /**
   * Gets the on screen upper x.
   *
   * @return the on screen upper x
   */
  private float getOnScreenUpperX()
  {
	  if(mVerticalFlip)
		  return center.getX() - (size.getX() * 0.5f);
	  return center.getX() + (size.getX() * 0.5f);
  }
  
  /**
   * Gets the on screen upper y.
   *
   * @return the on screen upper y
   */
  private float getOnScreenUpperY()
  {
	  if(mHorizontalFlip)
		  return center.getY() - (size.getY() * 0.5f);
	  return center.getY() + (size.getY() * 0.5f);
  }

  
  /* (non-Javadoc)
   * @see Engine.Primitive#draw()
   */
  public void draw()
  {
    if(mDrawImage && texture != null)
    {
      if(mUsingSpriteSheet)
      {
    	  BaseCode.resources.drawImage(texture,
    			  	getOnScreenLowerX(),
          			getOnScreenLowerY(),
          			getOnScreenUpperX(),
          			getOnScreenUpperY(),
    	    	    getSpriteLowerX(),
    			    getSpriteLowerY(),
    			    getSpriteUpperX(),
    			    getSpriteUpperY(),
    			    rotate);
    	  updateSpriteSheetAnimation();
    	  
      }
      else
      {
        BaseCode.resources.drawImage(texture,
        		getOnScreenLowerX(),
        		getOnScreenLowerY(),
        		getOnScreenUpperX(),
        		getOnScreenUpperY(),
                rotate);
      }
    }
    else
    {
      BaseCode.resources.setDrawingColor(color);

      if(mDrawFilledRect)
      {
        BaseCode.resources.drawFilledRectangle(center.getX(), center.getY(),
            size.getX() * 0.5f, size.getY() * 0.5f, rotate);
      }
      else
      {
        BaseCode.resources.drawOutlinedRectangle(center.getX(), center.getY(),
            size.getX() * 0.5f, size.getY() * 0.5f, rotate);
      }
    }
  }
  
  //***************************************************************************
  // Animation Modes
  //***************************************************************************
  
  /**
   * Updates the currently drawn sprite of the spritesheet.
   * The type of update depends on the current mAnimationMode
   * and the first and last frames.
   * 
   * This is called after the actual draw, as are all related
   * methods.
   */
  private void updateSpriteSheetAnimation()
  {
	  switch(mAnimationMode)
	  {
	  case ANIMATE_FORWARD:
		  updateForwardAnimation();
		  break;
	  case ANIMATE_BACKWARD:
		  updateBackwardAnimation();
		  break;
	  case ANIMATE_BACKWORDS_STOP:
		  updateBackwardStopAnimation();
		  break;
	  case ANIMATE_FORWARD_STOP:
		  updateForwardStopAnimation();
		  break;
	  case ANIMATE_SWING:
		  updateSwingAnimation();
		  break;
	  default:
		  break;
	  }
  }
  
  /**
   * Update forward animation.
   */
  private void updateForwardAnimation()
  {
	  // Waits for as many ticks per draw opportunity.
	  if(mCurrentTick < mTicksPerFrame)
	  {
		  mCurrentTick++;
		  return;
	  }
	  
	  mCurrentTick = 0;
	  
	  // Updates frames, loops back if at final frame.
	  if(mCurrentFrame >= mLastFrame)
	  {
		  mCurrentFrame = mFirstFrame;
		  return;
	  }
	  ++mCurrentFrame;
  }
  
  /**
   * Update forward stop animation.
   */
  private void updateForwardStopAnimation()
  {
	  // Waits for as many ticks per draw opportunity.
	  if(mCurrentTick < mTicksPerFrame)
	  {
		  mCurrentTick++;
		  return;
	  }
	  
	  mCurrentTick = 0;
	  
	  // Updates frames, stop animation if at final frame.
	  if(mCurrentFrame >= mLastFrame)
	  {
		  return;
	  }
	  ++mCurrentFrame;
  }
  
  /**
   * Update backward animation.
   */
  private void updateBackwardAnimation()
  {
	  // Waits for as many ticks per draw opportunity.
	  if(mCurrentTick < mTicksPerFrame)
	  {
		  mCurrentTick++;
		  return;
	  }
	  
	  mCurrentTick = 0;
	  
	  // Updates frames, loops back if at final frame.
	  if(mCurrentFrame <= mFirstFrame)
	  {
		  mCurrentFrame = mLastFrame;
		  return;
	  }
	  --mCurrentFrame;
  }
  
  /**
   * Update backward stop animation.
   */
  private void updateBackwardStopAnimation()
  {
	  // Waits for as many ticks per draw opportunity.
	  if(mCurrentTick < mTicksPerFrame)
	  {
		  mCurrentTick++;
		  return;
	  }
	  
	  mCurrentTick = 0;
	  
	  // Updates frames, stops on firstFrame
	  if(mCurrentFrame <= mFirstFrame)
	  {
		  return;
	  }
	  --mCurrentFrame;
  }
  
  /**
   * Update swing animation.
   */
  private void updateSwingAnimation()
  {
	  // Waits for as many ticks per draw opportunity.
	  if(mCurrentTick < mTicksPerFrame)
	  {
		  mCurrentTick++;
		  return;
	  }
	  
	  mCurrentTick = 0;
	  
	  if(mSwingDirectionLeft)
	  {
		  // Updates frames, loops back if at final frame.
		  if(mCurrentFrame <= mFirstFrame)
		  {
			  mSwingDirectionLeft = false;
			  return;
		  }
		  --mCurrentFrame;
	  }
	  else
	  {
		  // Updates frames, loops back if at final frame.
		  if(mCurrentFrame >= mLastFrame)
		  {
			  mSwingDirectionLeft = true;
			  return;
		  }
		  ++mCurrentFrame;
	  }
  }
  //***************************************************************************
  // Collision and related methods
  //***************************************************************************
  
  /**
   * Contains point.
   *
   * @param point the point
   * @return true, if successful
   */
  public boolean containsPoint(Vector2 point)
  {
    if(rotate != 0.0f)
    {
      // Rotate the point to match this object's rotation
      point = point.clone().sub(center).rotate(-rotate).add(center);
    }

    return (point.getX() >= (center.getX() - (size.getX() * 0.5f))) &&
        (point.getX() < (center.getX() + (size.getX() * 0.5f))) &&
        (point.getY() >= (center.getY() - (size.getY() * 0.5f))) &&
        (point.getY() < (center.getY() + (size.getY() * 0.5f)));
  }

  /**
   * Pixel touches.
   *
   * @param otherPrim the other prim
   * @return true, if successful
   */
  public boolean pixelTouches(Rectangle otherPrim)
  {
    return pixelTouches(otherPrim, null);
  }

  /**
   * Pixel touches.
   *
   * @param otherPrim the other prim
   * @param collidePoint the collide point
   * @return true, if successful
   */
  // From the C# code "TexturedPrimitivePixelCollide.cs"
  public boolean pixelTouches(Rectangle otherPrim, Vector2 collidePoint)
  {
    if(texture == null || otherPrim.texture == null)
    {
      return false;
    }

    boolean touches = primitivesTouches(otherPrim);
    //collidePoint.set(center);

    if(touches)
    {
      boolean pixelTouch = false;

      Vector2 myXDir =
          Vector2.rotateVectorByAngle(Vector2.unitX,
              (float)Math.toRadians(rotate));
      Vector2 myYDir =
          Vector2.rotateVectorByAngle(Vector2.unitY,
              (float)Math.toRadians(rotate));

      Vector2 otherXDir =
          Vector2.rotateVectorByAngle(Vector2.unitX,
              (float)Math.toRadians(otherPrim.rotate));
      Vector2 otherYDir =
          Vector2.rotateVectorByAngle(Vector2.unitY,
              (float)Math.toRadians(otherPrim.rotate));

      if(collidePoint == null)
      {
        collidePoint = new Vector2();
      }

      int i = 0;
      while((!pixelTouch) && (i < texture.getWidth()))
      {
        int j = 0;

        while((!pixelTouch) && (j < texture.getHeight()))
        {
          collidePoint.set(indexToCameraPosition(i, j, myXDir, myYDir));
          int myColor = ((texture.getRGB(i, j) >> 24) & 0xff);

          if(myColor > 0)
          {
            Vector2 otherIndex =
                otherPrim.cameraPositionToIndex(collidePoint, otherXDir,
                    otherYDir);
            int xMin = (int)otherIndex.getX();
            int yMin = (int)otherIndex.getY();

            if((xMin >= 0) && (xMin < otherPrim.texture.getWidth()) &&
                (yMin >= 0) && (yMin < otherPrim.texture.getHeight()))
            {
              pixelTouch =
                  (((otherPrim.texture.getRGB(xMin, yMin) >> 24) & 0xff) > 0);
            }
          }

          j++;
        }

        i++;
      }

      touches = pixelTouch;
    }

    return touches;
  }

  /**
   * Index to camera position.
   *
   * @param i the i
   * @param j the j
   * @param xDir the x dir
   * @param yDir the y dir
   * @return the vector2
   */
  private Vector2
      indexToCameraPosition(int i, int j, Vector2 xDir, Vector2 yDir)
  {
    // Translate from percent across image to percent across size
    float x = i * size.getX() / (float)(texture.getWidth() - 1);
    float y = j * size.getY() / (float)(texture.getHeight() - 1);

    Vector2 r =
        center.clone().add(xDir.clone().mult(x - (size.getX() * 0.5f)))
            .sub(yDir.clone().mult(y - (size.getY() * 0.5f)));

    return r;
  }

  /**
   * Camera position to index.
   *
   * @param p the p
   * @param xDir the x dir
   * @param yDir the y dir
   * @return the vector2
   */
  private Vector2 cameraPositionToIndex(Vector2 p, Vector2 xDir, Vector2 yDir)
  {
    Vector2 delta = p.clone().sub(center);

    float xOffset = Vector2.dot(delta, xDir);
    float yOffset = Vector2.dot(delta, yDir) - 1;

    float i = texture.getWidth() * (xOffset / size.getX());
    float j = texture.getHeight() * (yOffset / size.getY());
    i += texture.getWidth() / 2;
    j = (texture.getHeight() / 2) - j;

    return new Vector2(i, j);
  }

  /**
   * Primitives touches.
   *
   * @param otherPrim the other prim
   * @return true, if successful
   */
  private boolean primitivesTouches(Rectangle otherPrim)
  {
    final float epsilon = 0.0001f;

    if(Math.abs(Math.toRadians(rotate)) < epsilon &&
        Math.abs(Math.toRadians(otherPrim.rotate)) < epsilon)
    {
      return ((center.getX() - (size.getX() * 0.5f)) < (otherPrim.center.getX() + (otherPrim.size
          .getX() * 0.5f))) &&
          ((center.getX() + (size.getX() * 0.5f)) > (otherPrim.center.getX() - (otherPrim.size
              .getX() * 0.5f))) &&
          ((center.getY() - (size.getY() * 0.5f)) < (otherPrim.center.getY() + (otherPrim.size
              .getY() * 0.5f))) &&
          ((center.getY() + (size.getY() * 0.5f)) > (otherPrim.center.getY() - (otherPrim.size
              .getY() * 0.5f)));
    }
    else
    {
      // From the C# version:
      // one of both are rotated ... use radius ... be conservative
      // Use the larger of the Width/Height and approx radius
      //   Sqrt(1/2)*x Approx = 0.71f * x;
      float r1 = 0.71f * Math.max(size.getX(), size.getY());
      float r2 = 0.71f * Math.max(otherPrim.size.getX(), otherPrim.size.getY());
      return (otherPrim.center.clone().sub(center).length() < (r1 + r2));
    }
  }

  /**
   * Collided.
   *
   * @param otherPrim the other prim
   * @return true, if successful
   */
  public boolean collided(Rectangle otherPrim)
  {
    return (visible && otherPrim.visible && primitivesTouches(otherPrim));
  }

  /**
   * Push out circle.
   *
   * @param other the other
   * @return the vector2
   */
  // Pushes the other object out of the current object
  public Vector2 pushOutCircle(Rectangle other)
  {
    Vector2 resolved = null;

    if(other != null)
    {
      float topD = 0f, bottomD = 0f, leftD = 0f, rightD = 0f;

      // Flying upwards
      if(other.velocity.getY() > 0.0f)
      {
        // Check for bottom penetration
        topD =
            (other.center.getY() + (other.size.getY() * 0.5f)) -
                (center.getY() - (size.getY() * 0.5f));
      }
      // Flying downwards
      else
      {
        // Check for top penetration
        bottomD =
            (center.getY() + (size.getY() * 0.5f)) -
                (other.center.getY() - (other.size.getY() * 0.5f));
      }

      // Flying towards right
      if(other.velocity.getX() > 0)
      {
        // Check for left penetration
        leftD =
            (other.center.getX() + (other.size.getX() * 0.5f)) -
                (center.getX() - (size.getX() * 0.5f));
      }
      // Flying towards left
      else
      {
        // Check for right penetration
        rightD =
            (center.getX() + (size.getX() * 0.5f)) -
                (other.center.getX() - (other.size.getX() * 0.5f));
      }

      if(topD > 0)
      {
        if(leftD > 0)
        {
          if(topD < leftD)
          {
            // Push up from top
            resolved = new Vector2();
            resolved.setY((center.getY() - (other.size.getY() * 0.5f) - (size
                .getY() * 0.5f)) - other.center.getY());
            other.center.setY(other.center.getY() + resolved.getY());
          }
          else
          {
            // Push towards left
            resolved = new Vector2();
            resolved.setX((center.getX() - (other.size.getX() * 0.5f) - (size
                .getX() * 0.5f)) - other.center.getX());
            other.center.setX(other.center.getX() + resolved.getX());
          }
        }
        else if(rightD > 0)
        {
          if(topD < rightD)
          {
            // Push up from top
            resolved = new Vector2();
            resolved.setY((center.getY() - (other.size.getY() * 0.5f) - (size
                .getY() * 0.5f)) - other.center.getY());
            other.center.setY(other.center.getY() + resolved.getY());
          }
          else
          {
            // Push towards right
            resolved = new Vector2();
            resolved.setX((center.getX() + (other.size.getX() * 0.5f) + (size
                .getX() * 0.5f)) - other.center.getX());
            other.center.setX(other.center.getX() + resolved.getX());
          }
        }
      }
      else if(bottomD > 0)
      {
        if(leftD > 0)
        {
          if(bottomD < leftD)
          {
            // Push up from bottom
            resolved = new Vector2();
            resolved.setY((center.getY() + (other.size.getY() * 0.5f) + (size
                .getY() * 0.5f)) - other.center.getY());
            other.center.setY(other.center.getY() + resolved.getY());
          }
          else
          {
            // Push towards left
            resolved = new Vector2();
            resolved.setX((center.getX() - (other.size.getX() * 0.5f) - (size
                .getX() * 0.5f)) - other.center.getX());
            other.center.setX(other.center.getX() + resolved.getX());
          }
        }
        else if(rightD > 0)
        {
          if(bottomD < rightD)
          {
            // Push up from bottom
            resolved = new Vector2();
            resolved.setY((center.getY() + (other.size.getY() * 0.5f) + (size
                .getY() * 0.5f)) - other.center.getY());
            other.center.setY(other.center.getY() + resolved.getY());
          }
          else
          {
            // Push towards right
            resolved = new Vector2();
            resolved.setX((center.getX() + (other.size.getX() * 0.5f) + (size
                .getX() * 0.5f)) - other.center.getX());
            other.center.setX(other.center.getX() + resolved.getX());
          }
        }
      }

      /*
      Vector2 otherCenter = new Vector2(other.center.x, other.center.y);
      float left = center.x - size.x;
      float right = center.x + size.x;
      float top = center.y + size.y;
      float bottom = center.y - size.y;
      otherCenter.x = clamp(otherCenter.x, left, right);
      otherCenter.y = clamp(otherCenter.y, top, bottom);
      Vector2 direction = other.center.sub(otherCenter);
      float dist = other.size.x - direction.length();
      direction.normalize();
      other.center.set(other.center.add(direction.mult(dist)));
      */
    }

    return resolved;
  }

  /*
  private float clamp(float val, float min, float max)
  {
    return (val < min ? val = min : (val > max ? val = max : val));
  }
  */
}
