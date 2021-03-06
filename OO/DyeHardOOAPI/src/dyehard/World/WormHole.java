/*
 * 
 */
package dyehard.World;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import Engine.BaseCode;
import Engine.Vector2;
import dyehard.Actor;
import dyehard.DyeHardGameObject;
import dyehard.Collision.CollidableGameObject;
import dyehard.Enemies.Enemy;
import dyehard.Player.Hero;
import dyehard.Resources.ConfigurationFileParser;
import dyehard.Util.Colors;
import dyehard.Util.DyeHardSound;
import dyehard.Util.ImageTint;
import dyehard.Util.TextureTile;

// TODO: Auto-generated Javadoc
/**
 * The Class WormHole.
 */
public class WormHole {
    
    /** The hero. */
    private final Hero hero;
    
    /** The path. */
    private final StargatePath path;
    
    /** The path front. */
    private final StargatePathFront pathFront;
    
    /** The death gate. */
    private final DeathGate deathGate;
    
    /** The preview. */
    private final GatePreview preview;
    
    /** The entrance front. */
    private final GateDoor entranceFront;
    
    /** The entrance back. */
    private final GateDoor entranceBack;
    
    /** The exit front. */
    private final GateDoor exitFront;
    
    /** The exit back. */
    private final GateDoor exitBack;
    
    /** The plat height. */
    private final float platHeight = 1.25f;

    // private static BufferedImage gDoorBack;
    // private static BufferedImage gDoorFront;

    /** The d gates. */
    private static HashMap<Color, BufferedImage> dGates = new HashMap<Color, BufferedImage>();
    
    /** The g path back. */
    private static HashMap<Color, BufferedImage> gPathBack = new HashMap<Color, BufferedImage>();
    
    /** The g path front. */
    private static HashMap<Color, BufferedImage> gPathFront = new HashMap<Color, BufferedImage>();

    /**
     * Sets the gate path images.
     */
    public static void setGatePathImages() {
        // gDoorFront = BaseCode.resources
        // .loadImage("Textures/Background/Entrance_front.png");
        // gDoorBack = BaseCode.resources
        // .loadImage("Textures/BackGround/Entrance_back.png");

        BufferedImage dGate = BaseCode.resources
                .loadImage("Textures/Background/Warp_start_Anim.png");
        TextureTile tile = new TextureTile();
        // Fill the hashmaps with tinted images for later use
        for (int i = 0; i < 6; i++) {
            Color temp = Colors.colorPicker(i);
            dGates.put(temp, ImageTint.tintedImage(dGate, temp, 0.25f));

            String colorString = "";
            switch (i) {
            case 0:
                colorString = "green";
                break;
            case 1:
                colorString = "red";
                break;
            case 2:
                colorString = "yellow";
                break;
            case 3:
                colorString = "teal";
                break;
            case 4:
                colorString = "pink";
                break;
            case 5:
                colorString = "blue";
                break;
            }

            // cache the tiled portal images
            gPathBack.put(temp, tile.setTiling(
                    BaseCode.resources.loadImage("Textures/Background/Warp_"
                            + colorString + "_back.png"), 22, false));
            gPathFront.put(temp, tile.setTiling(
                    BaseCode.resources.loadImage("Textures/Background/Warp_"
                            + colorString + "_front.png"), 22, false));
        }
    }

    /**
     * Instantiates a new worm hole.
     *
     * @param 	hero, the hero to get reference to
     * @param 	color, the color of the wormhole. Must use Dyehard.Util.Colors, 
     * 			not java awt color package.
     * @param 	width, the width of the wormhole
     * @param 	height, the height of the wormhole
     * @param 	x, the center location in x world space
     * @param 	y, the center location in y world space
     */
    public WormHole(Hero hero, Color color, float width, float height, float x,
            float y) {
        this.hero = hero;
        // set up pipe
        int pathTF = 64;
        int pathTick = 3;

        float leftEdge = x - (width / 2f);

        path = new StargatePath();
        path.center = new Vector2(x, y);
        path.size.set(width, height);
        path.setPanning(true);
        path.setPanningSheet(gPathBack.get(color), pathTF, pathTick, false);
        path.dyeColor = color;
        path.velocity = new Vector2(-ConfigurationFileParser.getInstance().getWorldData().getWorldGameSpeed(), 0f);
        path.shouldTravel = true;

        float gateHeight = path.size.getY() + 2f;

        // entrance back
        entranceBack = new GateDoor(new Vector2(leftEdge - 3.8f,
                path.center.getY()), new Vector2(0.852564f * gateHeight,
                gateHeight), false, false, color);
        // exit back
        exitBack = new GateDoor(new Vector2(leftEdge + width - 3.8f,
                path.center.getY()), new Vector2(0.852564f * gateHeight,
                gateHeight), false, false, color);

        deathGate = new DeathGate();
        deathGate.center = new Vector2(leftEdge - 8f, path.center.getY());
        deathGate.size.set(0.9091f * (gateHeight + 2f), gateHeight + 2f);
        // Texture premade in static function
        deathGate.setUsingSpriteSheet(true);
        deathGate.setSpriteSheet(dGates.get(color), 200, 220, 24, 2);
        deathGate.dyeColor = color;
        deathGate.visible = true;
        deathGate.velocity = new Vector2(-ConfigurationFileParser.getInstance().getWorldData().getWorldGameSpeed(), 0f);
        deathGate.shouldTravel = true;
       
        // entrance front
        entranceFront = new GateDoor(new Vector2(leftEdge - 3.8f,
                path.center.getY()), new Vector2(0.852564f * gateHeight,
                gateHeight), true, true, color);
        // exit front
        exitFront = new GateDoor(new Vector2(leftEdge + width - 3.8f,
                path.center.getY()), new Vector2(0.852564f * gateHeight,
                gateHeight), true, false, color);

        hero.drawOnTop();

        pathFront = new StargatePathFront();
        pathFront.center = new Vector2(x, y);
        pathFront.size.set(width, height);
        pathFront.setPanning(true);
        pathFront.setPanningSheet(gPathFront.get(color), pathTF, pathTick,
                false);
        pathFront.velocity = new Vector2(-ConfigurationFileParser.getInstance().getWorldData().getWorldGameSpeed(), 0f);
        pathFront.shouldTravel = true;
        pathFront.reverse = true;

        preview = new GatePreview();
        preview.center = new Vector2(BaseCode.world.getWidth(), y);
        preview.size.set(4f, 0f);
        preview.color = path.dyeColor;
        preview.visible = true;
    }

    public GatePreview getPreview()
    {
    	return preview;
    }
    
    /**
     * The Class GatePreview.
     */
    public class GatePreview extends DyeHardGameObject {
        
    	public boolean overrideInvisible;
    	
        /* (non-Javadoc)
         * @see Engine.Primitive#update()
         */
        @Override
        public void update() {
            super.update();

            if(overrideInvisible)
            {
            	visible = false;
            	return;
            }
            
            visible = (path.center.getX() - (path.size.getX() / 2)) > ((preview.center
                    .getX() - (preview.size.getX() / 2)) + preview.size.getX())
            // Was path.LowerLeft.X TODO code it to work with different width
                    && (BaseCode.world.getWidth() + (BaseCode.world.getWidth() * 3f * 0.7f)) > (path.center
                            .getX() - (path.size.getX() / 2));
            if (preview.visible) {
                preview.size
                        // Was path.LowerLeft.X and preview.LowerLeft.X
                        .setY(((path.size.getY() + (platHeight * 2)) * (1 - (((path.center
                                .getX() - (path.size.getX() / 2)) - ((preview.center
                                .getX() - (preview.size.getX() / 2)) + preview.size
                                .getX())) / (BaseCode.world.getWidth() * 3f)))));
            }
        }
    }

    /**
     * The Class DeathGate.
     */
    public class DeathGate extends DyeHardGameObject {
        
        /** The dye color. */
        public Color dyeColor;

        /* (non-Javadoc)
         * @see Engine.Primitive#update()
         */
        @Override
        public void update() {
            super.update();
            if (center.getX() < BaseCode.world.getPositionX() - 10f) {
                destroy();
            }
        }
    }

    /**
     * The Class GateDoor.
     */
    public class GateDoor extends CollidableGameObject {
        
        /** The death. */
        private final boolean death;
        
        /** The dye color. */
        public Color dyeColor;

        /**
         * Instantiates a new gate door.
         *
         * @param c the center pos
         * @param s the size
         * @param front the front bool
         * @param death the death bool
         * @param color the color
         */
        public GateDoor(Vector2 c, Vector2 s, boolean front, boolean death,
                Color color) {
            dyeColor = color;
            center = c;
            size.set(s);
            visible = true;
            if (front) {
                texture = BaseCode.resources
                        .loadImage("Textures/Background/Entrance_front.png");
                ;
                alwaysOnTop = true;
            } else {
                texture = BaseCode.resources
                        .loadImage("Textures/Background/Entrance_back.png");
                ;
            }
            this.death = death;
            velocity = new Vector2(-ConfigurationFileParser.getInstance().getWorldData().getWorldGameSpeed(), 0f);
            shouldTravel = true;
        }

        /* (non-Javadoc)
         * @see dyehard.Collision.CollidableGameObject#handleCollision(dyehard.Collision.CollidableGameObject)
         */
        @Override
        public void handleCollision(CollidableGameObject other) {
            if (death) {
                if (other instanceof Actor) {
                    Actor target = (Actor) other;
                    if (target.center.getX() < center.getX()) {
                        if (target.getColor() != dyeColor) {
                            if (target instanceof Enemy) {
                                if (((Enemy) target).beenHit) {
                                    target.kill(this);
                                }
                            } else if (target instanceof Hero) {
                                target.kill(this);
                            }
                        }
                        if (target instanceof Hero) {
                            DyeHardSound.play(DyeHardSound.portalEnter);
                        }
                    }
                }
            }
        }
    }

    /**
     * The Class StargatePath.
     */
    public class StargatePath extends CollidableGameObject {
        
        /** The dye color. */
        public Color dyeColor;
        
        /** The exit played. */
        private boolean exitPlayed = false;

        /* (non-Javadoc)
         * @see dyehard.Collision.CollidableGameObject#handleCollision(dyehard.Collision.CollidableGameObject)
         */
        @Override
        public void handleCollision(CollidableGameObject other) {
            if (other instanceof Actor) {
                Actor target = (Actor) other;
                // Sammy code. If target.setColor(dyeColor) is enabled, then
                // Dye can get past wormholes.
                //target.setColor(dyeColor);
                if (other.center.getX() >= center.getX() + (size.getX() / 2)
                        - 3f) {
                    if (other instanceof Enemy) {
                        ((Enemy) other).setHarmless();
                    }
                }
            }
        }

        /* (non-Javadoc)
         * @see dyehard.Collision.CollidableGameObject#update()
         */
        @Override
        public void update() {
            // update() in collideable prematurely destroys gate, seperate
            // udpate function made.
            updateGate();
            // portal loop music
            if (((center.getX() - size.getX() / 2f) < BaseCode.world.getWidth())
                    && (!BaseCode.resources
                            .isSoundPlaying(DyeHardSound.portalLoop))
                    && (DyeHardSound.getSound())) {
                BaseCode.resources.playSoundLooping(DyeHardSound.portalLoop);
            } else if (!DyeHardSound.getSound()
                    && BaseCode.resources
                            .isSoundPlaying(DyeHardSound.portalLoop)) {
                BaseCode.resources.stopSound(DyeHardSound.portalLoop);
            }
            // end portal loop music
            if (center.getX() + size.getX() / 2 < BaseCode.world.getPositionX() - 5f) {
                BaseCode.resources.stopSound(DyeHardSound.portalLoop);
                destroy();
            }

            // portal exit sound
            if ((hero.center.getX() > center.getX() + size.getX() / 2f)
                    && (!exitPlayed)) {
                DyeHardSound.play(DyeHardSound.portalExit);
                exitPlayed = true;
            }
        }
    }

    /**
     * The Class StargatePathFront.
     */
    public class StargatePathFront extends CollidableGameObject {

        /* (non-Javadoc)
         * @see dyehard.Collision.CollidableGameObject#handleCollision(dyehard.Collision.CollidableGameObject)
         */
        @Override
        public void handleCollision(CollidableGameObject other) {
            // don't do anything
        }

        /* (non-Javadoc)
         * @see dyehard.Collision.CollidableGameObject#update()
         */
        @Override
        public void update() {
            // update() in collideable prematurely destroys gate, seperate
            // udpate function made.
            updateGate();
            removeFromAutoDrawSet();
            addToAutoDrawSet();

        }
    }

    /**
     * Destroy.
     */
    public void destroy() {
        path.destroy();
        pathFront.destroy();
        deathGate.destroy();
        preview.destroy();
        entranceFront.destroy();
        entranceBack.destroy();
        exitFront.destroy();
        exitBack.destroy();
        BaseCode.resources.stopSound(DyeHardSound.portalLoop);
    }
}
