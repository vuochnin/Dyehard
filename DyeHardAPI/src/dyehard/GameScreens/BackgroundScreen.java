package dyehard.GameScreens;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Random;

import Engine.BaseCode;
import Engine.Vector2;
import dyehard.DyeHardGameObject;
import dyehard.UpdateableObject;
import dyehard.Enums.ManagerStateEnum;

// TODO: Auto-generated Javadoc
/**
 * The Class BackgroundScreen.
 */
public class BackgroundScreen extends UpdateableObject {
    
	private ManagerStateEnum updateState = ManagerStateEnum.ACTIVE;
	
    /** The ship. */
    private final Deque<Tile> ship;    
    
    /** The ship textures. */
    private final List<BufferedImage> shipTextures;
    
    /** The ship texture paths. */
    private final String[] shipTexturePaths = {
            "Textures/Background/Dyehard_ship_tile_01.png",
            "Textures/Background/Dyehard_ship_tile_02.png",
            "Textures/Background/Dyehard_ship_tile_03.png",
            "Textures/Background/Dyehard_ship_tile_04.png", };

    /** The background. */
    private final Deque<Tile> background;    
    
    /** The background textures. */
    private final List<BufferedImage> backgroundTextures;    
    
    /** The background texture paths. */
    private final String[] backgroundTexturePaths = {
            "Textures/Background/Dyehard_starfield_01.png",
            "Textures/Background/Dyehard_starfield_02.png", };
    
    /** The foreground. */
    private final Deque<Tile> foreground;    
    
    /** The foreground textures. */
    private final List<BufferedImage> foregroundTextures;
    
    /** The foreground texture paths. */
    private final String[] foregroundTexturePaths = {
            "Textures/Background/Dyehard_starfield_stars.png",
            "Textures/Background/Dyehard_starfield_stars.png", };

    /** The random. */
    private static Random RANDOM = new Random();

    /**
     * Instantiates a new background screen.
     */
    public BackgroundScreen() {
        backgroundTextures = loadTextures(backgroundTexturePaths);
        background = createTiles(backgroundTextures, -0.00390625f); // 1/256

        foregroundTextures = loadTextures(foregroundTexturePaths);
        foreground = createTiles(foregroundTextures, -0.0078125f); // 1/128

        shipTextures = loadTextures(shipTexturePaths);
        ship = createTiles(shipTextures, -0.03125f); // 1/32
    }

    /* (non-Javadoc)
     * @see dyehard.Updateable#updateState()
     */
    @Override
    public ManagerStateEnum updateState() {
        return updateState;
    }
    
    public void destroy()
    {
    	updateState = ManagerStateEnum.DESTROYED;
    }
    
    /* (non-Javadoc)
     * @see dyehard.UpdateableObject#update()
     */
    @Override
    public void update() {
        updateTileQueue(ship);
        updateTileQueue(background);
        updateTileQueue(foreground);
    }

    /**
     * Update tile queue.
     *
     * @param tiles the tiles to update
     */
    private void updateTileQueue(Deque<Tile> tiles) {
        if (tiles.peek().isOffScreen()) {
            Tile first = tiles.poll();
            Tile last = tiles.peekLast();
            first.setLeftEdgeAt(last.rightEdge() - 5f);

            tiles.add(first);
        }
    }

    /**
     * Load textures.
     *
     * @param paths is the paths to the images to load
     * @return the list
     */
    private List<BufferedImage> loadTextures(String[] paths) {
        List<BufferedImage> textures = new ArrayList<BufferedImage>();
        for (String path : paths) {
            BufferedImage texture = BaseCode.resources.loadImage(path);
            textures.add(texture);
        }

        return textures;
    }

    /**
     * Creates the tiles.
     *
     * @param textures the textures
     * @param speed the speed of the tile
     * @return the deque of tiles
     */
    private Deque<Tile> createTiles(List<BufferedImage> textures, float speed) {
        // Collections.shuffle(textures);

        float randomStart = RANDOM.nextFloat() * Tile.width;
        Deque<Tile> tiles = new ArrayDeque<Tile>();
        for (int i = 0; i < textures.size(); ++i) {
            Tile tile = new Tile(i * Tile.width - randomStart, speed);
            tile.texture = textures.get(i);
            tiles.add(tile);
        }
        return tiles;
    }

    /**
     * The Class Tile.
     */
    private class Tile extends DyeHardGameObject {
        
        /** The Constant width. */
        public static final float width = 106;        
        
        /** The Constant height. */
        public static final float height = 60f;

        /**
         * Instantiates a new tile.
         *
         * @param leftEdge is the left edge
         * @param speed the speed of the velocity
         */
        public Tile(float leftEdge, float speed) {
            float Xpos = leftEdge + (width * 0.5f);
            float Ypos = height / 2;
            Vector2 position = new Vector2(Xpos, Ypos);

            velocity = new Vector2(speed, 0f);
            center = position;

            // slightly stretch the graphic to cover the gap where the tiles
            // overlap
            size = new Vector2(width + 0.5f, height);
            color = Color.PINK;
            shouldTravel = true;
        }

        /**
         * Sets the left edge at.
         *
         * @param leftEdge the new left edge at
         */
        public void setLeftEdgeAt(float leftEdge) {
            center.setX(leftEdge + size.getX() / 2f);
        }

        /**
         * Right edge.
         *
         * @return the right edge
         */
        public float rightEdge() {
            return center.getX() + size.getX() / 2f;
        }

        /**
         * Checks if is off screen.
         *
         * @return true, if is off screen
         */
        public boolean isOffScreen() {
            return rightEdge() < BaseCode.world.getPositionX();
        }
    }

    /* (non-Javadoc)
     * @see dyehard.Updateable#setSpeed(float)
     */
    @Override
    public void setSpeed(float v) {
        // TODO Auto-generated method stub
    }
}