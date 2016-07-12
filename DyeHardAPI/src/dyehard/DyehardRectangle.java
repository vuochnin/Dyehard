package dyehard;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Engine.BaseCode;
import Engine.Primitive;
import Engine.Vector2;

// TODO: Auto-generated Javadoc
// Sprite sheet code ported from the C# engine, which
// is credited to Samuel Cook and Ron Cook for adding support for that.
/**
 * The Class DyehardRectangle.
 */
public class DyehardRectangle extends Primitive {
    
    /** The sprite cycle done. */
    public boolean spriteCycleDone = false;
    
    /** The reverse. */
    public boolean reverse = false;
    
    /** The over ride. */
    public boolean overRide = false;
    
    /** The stop at end. */
    public boolean stopAtEnd = false;
    
    /** The draw image. */
    private boolean drawImage = true;
    
    /** The draw filled rect. */
    private boolean drawFilledRect = true;    
    
    /** The flash. */
    private boolean flash = false;   
    
    /** The flash count. */
    private int flashCount = 0;
    
    /** The flash rate. */
    private final int flashRate = 10;

    /**
     * The Enum SpriteSheetAnimationMode.
     */
    public enum SpriteSheetAnimationMode {
    	
	    /** The animate forward. */
	    ANIMATE_FORWARD, 
    	
	    /** The animate backward. */
	    ANIMATE_BACKWARD, 
    	
	    /** The animate swing. */
	    ANIMATE_SWING
    };

    /** The using sprite sheet. */
    private boolean usingSpriteSheet = false;
    
    /** The panning. */
    private boolean panning = false;
    
    /** The vertical. */
    private boolean vertical = false;
    /**
     * Sprite tolerance is for letting spritesheets with odd widths/heights to
     * include outermost sprites. This is because the spritemapping algorithm
     * does not include any part of a spritesheet that is bigger than the sum of
     * the sprites dimension (width or height).
     */

    /**
     * frameCoords holds the lower left and upper right coordinates of each
     * frame of the sprite sheet. frameCoords[][0] = lower left x
     * frameCoords[][1] = lower left y frameCoords[][2] = upper right x
     * frameCoords[][3] = upper right y
     * 
     */
    protected int[][] frameCoords;
    
    /** The current frame. */
    protected int currentFrame;
    
    /** The total frames. */
    protected int totalFrames;
    
    /** The frame height. */
    protected int frameWidth, frameHeight;
    
    /** The extra. */
    private int extra;

    /** The ticks per frame. */
    protected int ticksPerFrame;
    
    /** The current tick. */
    protected int currentTick;

    /**
     * Instantiates a new dyehard rectangle.
     */
    public DyehardRectangle() {
        super();
    }

    /**
     * Instantiates a new dyehard rectangle.
     *
     * @param other the other
     */
    public DyehardRectangle(DyehardRectangle other) {
        super(other);

        drawImage = other.drawImage;
        drawFilledRect = other.drawFilledRect;
    }

    /**
     * Using sprite sheet.
     *
     * @return true, if successful
     */
    public boolean usingSpriteSheet() {
        return usingSpriteSheet;
    }

    /**
     * Sets the using sprite sheet.
     *
     * @param usingSpriteSheet the new using sprite sheet
     */
    public void setUsingSpriteSheet(boolean usingSpriteSheet) {
        this.usingSpriteSheet = usingSpriteSheet;
    }

    /**
     * Panning.
     *
     * @return true, if successful
     */
    public boolean panning() {
        return panning;
    }

    /**
     * Sets the panning.
     *
     * @param panning the new panning
     */
    public void setPanning(boolean panning) {
        this.panning = panning;
    }

    /**
     * Sets up the spritesheet animation.
     * 
     * Will do nothing if width, height, or totalFrame are <= 0.
     * 
     * @param textureFilename
     *            Filename of the spritesheet image
     * @param width
     *            Width of individual sprite.
     * @param height
     *            Height of individual sprite.
     * @param totalFrames
     *            Total sprites in spritesheet
     * @param ticksPerFrame
     *            How long each sprite remains before the next is drawn.
     */
    public void setSpriteSheet(String textureFilename, int width, int height,
            int totalFrames, int ticksPerFrame) {
        if (totalFrames <= 0 || width <= 0 || height <= 0) {
            return;
        }
        setImage(textureFilename);

        frameCoords = new int[totalFrames][4];
        this.totalFrames = totalFrames;
        currentFrame = 0;

        currentTick = 0;
        this.ticksPerFrame = ticksPerFrame;

        int currentFrameX = 0;
        int currentFrameY = 0;

        for (int frame = 0; frame < totalFrames; frame++) {
            if (currentFrameX + width > texture.getWidth()) {
                currentFrameX = 0;
                currentFrameY += height;
            }
            if (currentFrameY + height > texture.getHeight()) {
                break;
            }
            frameCoords[frame][0] = currentFrameX + width;
            frameCoords[frame][1] = currentFrameY + height;
            frameCoords[frame][2] = currentFrameX;
            frameCoords[frame][3] = currentFrameY;

            currentFrameX += width;
        }

    }

    /**
     * Sets the sprite sheet.
     *
     * @param texture the texture
     * @param width the width
     * @param height the height
     * @param totalFrames the total frames
     * @param ticksPerFrame the ticks per frame
     */
    public void setSpriteSheet(BufferedImage texture, int width, int height,
            int totalFrames, int ticksPerFrame) {
        if (totalFrames <= 0 || width <= 0 || height <= 0) {
            return;
        }
        this.texture = texture;

        frameWidth = width;
        frameHeight = height;
        frameCoords = new int[totalFrames][4];
        this.totalFrames = totalFrames;
        currentFrame = 0;

        currentTick = 0;
        this.ticksPerFrame = ticksPerFrame;

        int currentFrameX = 0;
        int currentFrameY = 0;

        for (int frame = 0; frame < totalFrames; frame++) {
            if (currentFrameX + width > texture.getWidth()) {
                currentFrameX = 0;
                currentFrameY += height;
            }
            if (currentFrameY + height > texture.getHeight()) {
                break;
            }
            frameCoords[frame][0] = currentFrameX + width;
            frameCoords[frame][1] = currentFrameY + height;
            frameCoords[frame][2] = currentFrameX;
            frameCoords[frame][3] = currentFrameY;

            currentFrameX += width;
        }
    }

    /**
     * Updates the currently drawn sprite of the spritesheet.
     */
    private void updateSpriteSheetAnimation() {
        if ((DyeHardGame.getState() == DyeHardGame.State.PLAYING) || (overRide)) {
            if (currentTick < ticksPerFrame) {
                currentTick++;
                return;
            }

            currentTick = 0;
            if (reverse) {
                --currentFrame;
                if (currentFrame < 0) {
                    currentFrame = totalFrames - 1;
                    return;
                }
                else {
                    spriteCycleDone = true;
                }
            } else {
                if (currentFrame >= totalFrames - 1) {
                    spriteCycleDone = true;
                    if (stopAtEnd) {
                        return;
                    } else {
                        currentFrame = 0;
                        return;
                    }
                } 
                ++currentFrame;
            }
        }
    }

    /**
     * Gets the num frames.
     *
     * @return the num frames
     */
    public int getNumFrames() {
        return totalFrames;
    }

    /**
     * Gets the cur frame.
     *
     * @return the cur frame
     */
    public int getCurFrame() {
        return currentFrame;
    }

    /**
     * Sets the cur frame.
     *
     * @param frame the new cur frame
     */
    public void setCurFrame(int frame) {
        currentFrame = frame;
    }

    /**
     * Sets the frame number.
     *
     * @param frameNumber the new frame number
     */
    public void setFrameNumber(int frameNumber) {
        currentFrame = frameNumber;
        // currentFrame = Math.clamp(currentFrame, 0, totalFrames - 1)
        currentFrame = Math.min(currentFrame, totalFrames - 1);
        currentFrame = Math.max(currentFrame, 0);
    }

    /**
     * Gets the sprite upper x.
     *
     * @return the sprite upper x
     */
    // Helper getters for drawing a portion of an image(the spritesheet)
    private int getSpriteUpperX() {
        if (currentFrame >= frameCoords.length) {
            currentFrame = frameCoords.length - 1;
        }
        return frameCoords[currentFrame][0];
    }

    /**
     * Gets the sprite upper y.
     *
     * @return the sprite upper y
     */
    private int getSpriteUpperY() {
        if (currentFrame >= frameCoords.length) {
            currentFrame = frameCoords.length - 1;
        }
        return frameCoords[currentFrame][1];
    }

    /**
     * Gets the sprite lower x.
     *
     * @return the sprite lower x
     */
    private int getSpriteLowerX() {
        if (currentFrame >= frameCoords.length) {
            currentFrame = frameCoords.length - 1;
        }
        return frameCoords[currentFrame][2];
    }

    /**
     * Gets the sprite lower y.
     *
     * @return the sprite lower y
     */
    private int getSpriteLowerY() {
        if (currentFrame >= frameCoords.length) {
            currentFrame = frameCoords.length - 1;
        }
        return frameCoords[currentFrame][3];
    }

    /**
     * Sets the panning sheet.
     *
     * @param t the t
     * @param totalFrames the total frames
     * @param ticksPerFrame the ticks per frame
     * @param vertical the vertical
     */
    public void setPanningSheet(BufferedImage t, int totalFrames,
            int ticksPerFrame, boolean vertical) {
        if (totalFrames <= 0) {
            return;
        }
        this.vertical = vertical;
        this.totalFrames = totalFrames;
        currentFrame = 0;

        currentTick = 0;
        this.ticksPerFrame = ticksPerFrame;

        if (texture == null) {
            texture = t;
        }

        int factor;
        if (vertical) {
            float ratio = t.getWidth() / size.getX();
            factor = ((int) ((size.getY() * ratio) / t.getHeight())) * 2;
            if (factor < 1) {
                factor = 1;
            }
            extra = (factor * t.getHeight()) - ((int) (ratio * size.getY()));
        } else {
            float ratio = t.getHeight() / size.getY();
            factor = ((int) ((size.getX() * ratio) / t.getWidth())) * 2;
            if (factor < 1) {
                factor = 1;
            }
            extra = (factor * t.getWidth()) - ((int) (ratio * size.getX()));
        }

        if (factor > 1) {
            texture = setTiling(t, factor, vertical);
        } else {
            texture = t;
        }
    }

    /* (non-Javadoc)
     * @see Engine.Primitive#draw()
     */
    @Override
    public void draw() {
        if (flash) {
            if (flashCount == flashRate) {
                drawImage = !drawImage;
                flashCount = 0;
            }
            flashCount++;
        }
        if (drawImage && texture != null) {
            if (usingSpriteSheet) {
                BaseCode.resources.drawImage(texture,
                        center.getX() - (size.getX() * 0.5f), center.getY()
                                - (size.getY() * 0.5f),
                        center.getX() + (size.getX() * 0.5f), center.getY()
                                + (size.getY() * 0.5f), getSpriteLowerX(),
                        getSpriteLowerY(), getSpriteUpperX(),
                        getSpriteUpperY(), rotate);
                updateSpriteSheetAnimation();
            } else if (panning) {
                if (vertical) {
                    BaseCode.resources.drawImage(texture,
                            center.getX() - (size.getX() * 0.5f), center.getY()
                                    - (size.getY() * 0.5f), center.getX()
                                    + (size.getX() * 0.5f), center.getY()
                                    + (size.getY() * 0.5f), 0, extra
                                    / totalFrames * currentFrame,
                            texture.getWidth(), extra / totalFrames
                                    * currentFrame
                                    + (texture.getHeight() - extra), rotate);
                } else {
                    BaseCode.resources.drawImage(texture,
                            center.getX() - (size.getX() * 0.5f), center.getY()
                                    - (size.getY() * 0.5f), center.getX()
                                    + (size.getX() * 0.5f), center.getY()
                                    + (size.getY() * 0.5f), extra / totalFrames
                                    * currentFrame, 0, extra / totalFrames
                                    * currentFrame
                                    + (texture.getWidth() - extra),
                            texture.getHeight(), rotate);
                }
                updateSpriteSheetAnimation();
            } else {
                BaseCode.resources.drawImage(texture,
                        center.getX() - (size.getX() * 0.5f), center.getY()
                                - (size.getY() * 0.5f),
                        center.getX() + (size.getX() * 0.5f), center.getY()
                                + (size.getY() * 0.5f), rotate);
            }
        } else if (texture == null) {
            BaseCode.resources.setDrawingColor(color);

            if (drawFilledRect) {
                BaseCode.resources.drawFilledRectangle(center.getX(),
                        center.getY(), size.getX() * 0.5f, size.getY() * 0.5f,
                        rotate);
            } else {
                BaseCode.resources.drawOutlinedRectangle(center.getX(),
                        center.getY(), size.getX() * 0.5f, size.getY() * 0.5f,
                        rotate);
            }
        }
    }

    /**
     * Sets the draw image.
     *
     * @param value the new draw image
     */
    public void setDrawImage(boolean value) {
        drawImage = value;
    }

    /**
     * Sets the draw filled rect.
     *
     * @param value the new draw filled rect
     */
    public void setDrawFilledRect(boolean value) {
        drawFilledRect = value;
    }

    /**
     * Contains point.
     *
     * @param point the point
     * @return true, if successful
     */
    public boolean containsPoint(Vector2 point) {
        if (rotate != 0.0f) {
            // Rotate the point to match this object's rotation
            point = point.clone().sub(center).rotate(-rotate).add(center);
        }

        return (point.getX() >= (center.getX() - (size.getX() * 0.5f)))
                && (point.getX() < (center.getX() + (size.getX() * 0.5f)))
                && (point.getY() >= (center.getY() - (size.getY() * 0.5f)))
                && (point.getY() < (center.getY() + (size.getY() * 0.5f)));
    }

    /**
     * Pixel touches.
     *
     * @param otherPrim the other prim
     * @return true, if successful
     */
    public boolean pixelTouches(DyehardRectangle otherPrim) {
        return pixelTouches(otherPrim, null);
    }

    /**
     * Pixel touches.
     *
     * @param otherPrim the other prim to check if the primitives touch
     * @param collidePoint the collide point of the primitive colliding
     * @return true, if successful
     */
    // From the C# code "TexturedPrimitivePixelCollide.cs"
    public boolean pixelTouches(DyehardRectangle otherPrim, Vector2 collidePoint) {
        if (texture == null || otherPrim.texture == null) {
            return false;
        }

        if (!primitivesTouches(otherPrim)) {
            return false;
        }

        Vector2 myXDir = Vector2.rotateVectorByAngle(Vector2.unitX,
                (float) Math.toRadians(rotate));
        Vector2 myYDir = Vector2.rotateVectorByAngle(Vector2.unitY,
                (float) Math.toRadians(rotate));

        Vector2 otherXDir = Vector2.rotateVectorByAngle(Vector2.unitX,
                (float) Math.toRadians(otherPrim.rotate));
        Vector2 otherYDir = Vector2.rotateVectorByAngle(Vector2.unitY,
                (float) Math.toRadians(otherPrim.rotate));

        if (collidePoint == null) {
            collidePoint = new Vector2();
        }

        int minX = usingSpriteSheet ? getSpriteLowerX() : 0;
        int maxX = usingSpriteSheet ? getSpriteUpperX() : texture.getWidth();

        int minY = usingSpriteSheet ? getSpriteLowerY() : 0;
        int maxY = usingSpriteSheet ? getSpriteUpperY() : texture.getHeight();

        for (int x = minX; x < maxX; ++x) {
            for (int y = minY; y < maxY; ++y) {
                int myColor = ((texture.getRGB(x, y) >> 24) & 0xff);

                // Skip transparent pixels
                if (myColor <= 0) {
                    continue;
                }

                collidePoint.set(indexToCameraPosition(x - minX, y - minY,
                        myXDir, myYDir));
                Vector2 otherIndex = otherPrim.cameraPositionToIndex(
                        collidePoint, otherXDir, otherYDir);
                int xMin = (int) otherIndex.getX();
                int yMin = (int) otherIndex.getY();

                // TODO add check for otherPrim spriteSheet width/height
                if (xMin < 0 || xMin >= otherPrim.texture.getWidth()
                        || yMin < 0 || yMin >= otherPrim.texture.getHeight()) {
                    continue;
                }

                // overlap found!
                if (((otherPrim.texture.getRGB(xMin, yMin) >> 24) & 0xff) > 0) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Index to camera position. TODO: Need to work on this
     *
     * @param i the i
     * @param j the j
     * @param xDir the x dir
     * @param yDir the y dir
     * @return the vector2
     */
    private Vector2 indexToCameraPosition(int i, int j, Vector2 xDir,
            Vector2 yDir) {
        // Translate from percent across image to percent across size
        float x = i * size.getX() / (texture.getWidth() - 1);
        float y = j * size.getY() / (texture.getHeight() - 1);

        Vector2 r = center.clone()
                .add(xDir.clone().mult(x - (size.getX() * 0.5f)))
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
    private Vector2 cameraPositionToIndex(Vector2 p, Vector2 xDir, Vector2 yDir) {
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
     * @return true if they touch. Else return false.
     */
    private boolean primitivesTouches(DyehardRectangle otherPrim) {
        final float epsilon = 0.0001f;

        if (Math.abs(Math.toRadians(rotate)) < epsilon
                && Math.abs(Math.toRadians(otherPrim.rotate)) < epsilon) {
            return ((center.getX() - (size.getX() * 0.5f)) < (otherPrim.center
                    .getX() + (otherPrim.size.getX() * 0.5f)))
                    && ((center.getX() + (size.getX() * 0.5f)) > (otherPrim.center
                            .getX() - (otherPrim.size.getX() * 0.5f)))
                    && ((center.getY() - (size.getY() * 0.5f)) < (otherPrim.center
                            .getY() + (otherPrim.size.getY() * 0.5f)))
                    && ((center.getY() + (size.getY() * 0.5f)) > (otherPrim.center
                            .getY() - (otherPrim.size.getY() * 0.5f)));
        } else {
            // From the C# version:
            // one of both are rotated ... use radius ... be conservative
            // Use the larger of the Width/Height and approx radius
            // Sqrt(1/2)*x Approx = 0.71f * x;
            float r1 = 0.71f * Math.max(size.getX(), size.getY());
            float r2 = 0.71f * Math.max(otherPrim.size.getX(),
                    otherPrim.size.getY());
            return (otherPrim.center.clone().sub(center).length() < (r1 + r2));
        }
    }

    /**
     * Collided.
     *
     * @param otherPrim the other prim to see if they collided
     * @return true, if they collided. 
     */
    public boolean collided(DyehardRectangle otherPrim) {
        return (visible && otherPrim.visible && primitivesTouches(otherPrim));
    }

    /**
     * Push the other object out of the current object
     *
     * @param other the other DyehardRectangle
     * @return the vector2
     */
    // Pushes the other object out of the current object
    public Vector2 pushOutCircle(DyehardRectangle other) {
        Vector2 resolved = null;

        if (other != null) {
            float topD = 0f, bottomD = 0f, leftD = 0f, rightD = 0f;

            // Flying upwards
            if (other.velocity.getY() > 0.0f) {
                // Check for bottom penetration
                topD = (other.center.getY() + (other.size.getY() * 0.5f))
                        - (center.getY() - (size.getY() * 0.5f));
            }
            // Flying downwards
            else {
                // Check for top penetration
                bottomD = (center.getY() + (size.getY() * 0.5f))
                        - (other.center.getY() - (other.size.getY() * 0.5f));
            }

            // Flying towards right
            if (other.velocity.getX() > 0) {
                // Check for left penetration
                leftD = (other.center.getX() + (other.size.getX() * 0.5f))
                        - (center.getX() - (size.getX() * 0.5f));
            }
            // Flying towards left
            else {
                // Check for right penetration
                rightD = (center.getX() + (size.getX() * 0.5f))
                        - (other.center.getX() - (other.size.getX() * 0.5f));
            }

            if (topD > 0) {
                if (leftD > 0) {
                    if (topD < leftD) {
                        // Push up from top
                        resolved = new Vector2();
                        resolved.setY((center.getY()
                                - (other.size.getY() * 0.5f) - (size.getY() * 0.5f))
                                - other.center.getY());
                        other.center
                                .setY(other.center.getY() + resolved.getY());
                    } else {
                        // Push towards left
                        resolved = new Vector2();
                        resolved.setX((center.getX()
                                - (other.size.getX() * 0.5f) - (size.getX() * 0.5f))
                                - other.center.getX());
                        other.center
                                .setX(other.center.getX() + resolved.getX());
                    }
                } else if (rightD > 0) {
                    if (topD < rightD) {
                        // Push up from top
                        resolved = new Vector2();
                        resolved.setY((center.getY()
                                - (other.size.getY() * 0.5f) - (size.getY() * 0.5f))
                                - other.center.getY());
                        other.center
                                .setY(other.center.getY() + resolved.getY());
                    } else {
                        // Push towards right
                        resolved = new Vector2();
                        resolved.setX((center.getX()
                                + (other.size.getX() * 0.5f) + (size.getX() * 0.5f))
                                - other.center.getX());
                        other.center
                                .setX(other.center.getX() + resolved.getX());
                    }
                }
            } else if (bottomD > 0) {
                if (leftD > 0) {
                    if (bottomD < leftD) {
                        // Push up from bottom
                        resolved = new Vector2();
                        resolved.setY((center.getY()
                                + (other.size.getY() * 0.5f) + (size.getY() * 0.5f))
                                - other.center.getY());
                        other.center
                                .setY(other.center.getY() + resolved.getY());
                    } else {
                        // Push towards left
                        resolved = new Vector2();
                        resolved.setX((center.getX()
                                - (other.size.getX() * 0.5f) - (size.getX() * 0.5f))
                                - other.center.getX());
                        other.center
                                .setX(other.center.getX() + resolved.getX());
                    }
                } else if (rightD > 0) {
                    if (bottomD < rightD) {
                        // Push up from bottom
                        resolved = new Vector2();
                        resolved.setY((center.getY()
                                + (other.size.getY() * 0.5f) + (size.getY() * 0.5f))
                                - other.center.getY());
                        other.center
                                .setY(other.center.getY() + resolved.getY());
                    } else {
                        // Push towards right
                        resolved = new Vector2();
                        resolved.setX((center.getX()
                                + (other.size.getX() * 0.5f) + (size.getX() * 0.5f))
                                - other.center.getX());
                        other.center
                                .setX(other.center.getX() + resolved.getX());
                    }
                }
            }

            /*
             * Vector2 otherCenter = new Vector2(other.center.x,
             * other.center.y); float left = center.x - size.x; float right =
             * center.x + size.x; float top = center.y + size.y; float bottom =
             * center.y - size.y; otherCenter.x = clamp(otherCenter.x, left,
             * right); otherCenter.y = clamp(otherCenter.y, top, bottom);
             * Vector2 direction = other.center.sub(otherCenter); float dist =
             * other.size.x - direction.length(); direction.normalize();
             * other.center.set(other.center.add(direction.mult(dist)));
             */
        }

        return resolved;
    }

    /**
     * Start flashing.
     */
    public void startFlashing() {
        flashCount = 0;
        drawImage = true;
        flash = true;
    }

    /**
     * Stop flashing.
     */
    public void stopFlashing() {
        drawImage = true;
        flash = false;
    }

    /**
     * Sets the tiling.
     *
     * @param img the img
     * @param tileNum the tile num
     * @param vertical the vertical
     * @return the buffered image
     */
    private BufferedImage setTiling(BufferedImage img, int tileNum,
            boolean vertical) {
        int currentEnd = 0;
        int tileWidth = img.getWidth();
        int tileHeight = img.getHeight();

        if (vertical) {
            int width = tileWidth;
            int height = tileHeight * tileNum;

            BufferedImage newImage = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = newImage.createGraphics();

            while (currentEnd < height) {
                g2.drawImage(img, null, 0, currentEnd);
                currentEnd += tileHeight;
            }
            g2.dispose();
            return newImage;
        } else {
            int width = tileWidth * tileNum;
            int height = tileHeight;

            BufferedImage newImage = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = newImage.createGraphics();

            while (currentEnd < width) {
                g2.drawImage(img, null, currentEnd, 0);
                currentEnd += tileWidth;
            }
            g2.dispose();
            return newImage;
        }
    }

    /* (non-Javadoc)
     * @see Engine.Primitive#destroy()
     */
    @Override
    public void destroy() {
    	flash = false;
        texture = null;
        super.destroy();
    }

    /*
     * private float clamp(float val, float min, float max) { return (val < min
     * ? val = min : (val > max ? val = max : val)); }
     */
}
