package dyehard.Player;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

import Engine.BaseCode;
import Engine.Primitive;
import Engine.Vector2;
import Engine.World.BoundCollidedStatus;
import dyehard.Actor;
import dyehard.DyeHardGame;
import dyehard.Collectibles.DyePack;
import dyehard.Collectibles.Invincibility;
import dyehard.Collectibles.PowerUp;
import dyehard.Collision.CollidableGameObject;
import dyehard.Player.HeroInterfaces.HeroCollision;
import dyehard.Player.HeroInterfaces.HeroDamage;
import dyehard.Resources.ConfigurationFileParser;
import dyehard.Util.Colors;
import dyehard.Util.DyeHardSound;
import dyehard.Util.ImageTint;
import dyehard.Util.Timer;
import dyehard.Weapons.OverHeatWeapon;
import dyehard.Weapons.Weapon;
import dyehard.World.GameState;
import dyehard.World.WormHole;
import dyehard.World.WormHole.DeathGate;
import dyehard.World.WormHole.GateDoor;
import dyehard.World.WormHole.StargatePath;
import dyehard.World.WormHole.StargatePathFront;


// TODO: Auto-generated Javadoc
/**
 * The Class Hero.
 */
public class Hero extends Actor implements HeroCollision, HeroDamage {
	
    /** The charger idle textures. */
    public static HashMap<Color, BufferedImage> chargerIdleTextures = new HashMap<Color, BufferedImage>();    
    
    /** The charger attack textures. */
    public static HashMap<Color, BufferedImage> chargerAttackTextures = new HashMap<Color, BufferedImage>();    
    
    /** The regular left textures. */
    public static HashMap<Color, BufferedImage> regularLeftTextures = new HashMap<Color, BufferedImage>();    
    
    /** The regular right textures. */
    public static HashMap<Color, BufferedImage> regularRightTextures = new HashMap<Color, BufferedImage>();    
    
    /** The portal enemy textures. */
    public static HashMap<Color, BufferedImage> portalEnemyTextures = new HashMap<Color, BufferedImage>();    
    
    /** The dye textures. */
    private static HashMap<Direction, BufferedImage> dyeTextures = new HashMap<Direction, BufferedImage>();    
    
    /** The dye fire textures. */
    private static HashMap<Direction, BufferedImage> dyeFireTextures = new HashMap<Direction, BufferedImage>();
    
    /** The timer. */
    private final Timer timer = new Timer(7000);    
    
    /** The no more teleport. */
    public boolean noMoreTeleport = false;
    
    /** The collision on. */
    public boolean collisionOn = true;
    
    /** The damage on. */
    public boolean damageOn = true;    
    
    /** The current weapon. */
    public Weapon currentWeapon;    
    
    /** The current jet speed. */
    public float currentJetSpeed;    
    
    /** The current gravity. */
    public Vector2 currentGravity;
    
    /** The powerups. */
    public Set<PowerUp> powerups;
    
    /** The cur power up. */
    public CurPowerUp curPowerUp;  
    
    /** The debug invincibility. */
    public boolean debugInvincibility;    
    
    /** The is invin. */
    public boolean isInvin;    
    
    /** The is repel. */
    public boolean isRepel;    
    
    /** The is firing. */
    public boolean isFiring;    
    
    /** The flashing. */
    private boolean flashing;

    /** The default weapon. */
    public final Weapon defaultWeapon = new OverHeatWeapon(this);    
    
    /** The default jet speed. */
    public final float defaultJetSpeed = ConfigurationFileParser.getInstance().getHeroData().getHeroJetSpeed(); //ConfigurationFileParser.heroJetSpeed;    
    
    /** The default gravity. */
    public final Vector2 defaultGravity = new Vector2(0f, 0f);
    
    /** The total thrust. */
    public Vector2 totalThrust = new Vector2();    
    
    /** The bullet textures. */
    public HashMap<Color, BufferedImage> bulletTextures = new HashMap<Color, BufferedImage>();    
    
    /** The muzzle textures. */
    public HashMap<Color, BufferedImage> muzzleTextures = new HashMap<Color, BufferedImage>();
    
    /** The speed limit x. */
    private final float speedLimitX = ConfigurationFileParser.getInstance().getHeroData().getHeroSpeedLimit();    
    
    /** The drag. */
    private static float drag = ConfigurationFileParser.getInstance().getHeroData().getHeroDrag();
    
    /** The collected dyepacks. */
    private int collectedDyepacks;    
    
    /** The collected powerups. */
    private int collectedPowerups;    
    
    /** The size scale. */
    private final float sizeScale;
    
    /** The max hero speed. */
    private final float maxHeroSpeed = ConfigurationFileParser.getInstance().getHeroData().getHeroSpeedLimit();    
    
    /** The hero speed ratio. */
    private final float heroSpeedRatio = ConfigurationFileParser.getInstance().getHeroData().getHeroJetSpeed();

    /** The direction state. */
    protected Direction directionState;
    
    /** The dynamic dyepack. */
    protected DynamicDyePack dynamicDyepack;
    
    /** The hero effect. */
    protected HeroEffect heroEffect;
    
    /** The previous velocity. */
    protected Vector2 previousVelocity;
    
    /** The current velocity. */
    protected Vector2 currentVelocity;
    
    /** The Constant startingLocation. */
    protected final static Vector2 startingLocation = new Vector2(20f, 20f);

    /** The weapon rack. */
    private final ArrayList<Weapon> weaponRack;    
    
    /** The weapon hotkeys. */
    private final HashMap<Integer, Integer> weaponHotkeys;

    /**
     * The Enum Direction.
     */
    public enum Direction {
        
        /** The up. */
        UP, 
        
        /** The down. */
        DOWN, 
 		
		 /** The back. */
		 BACK, 
 		
		 /** The forward. */
		 FORWARD, 
 		
		 /** The upforward. */
		 UPFORWARD, 
 		
		 /** The upback. */
		 UPBACK, 
 		
		 /** The downforward. */
		 DOWNFORWARD, 
 		
		 /** The downback. */
		 DOWNBACK, 
 		
		 /** The neutral. */
		 NEUTRAL
    }

    /**
     * The Enum CurPowerUp.
     */
    public enum CurPowerUp {
        
        /** The ghost. */
        GHOST, 
        
        /** The invin. */
        INVIN, 
 		
		 /** The magnet. */
		 MAGNET, 
 		
		 /** The overload. */
		 OVERLOAD, 
 		
		 /** The slow. */
		 SLOW, 
 		
		 /** The speed. */
		 SPEED, 
 		
		 /** The unarmed. */
		 UNARMED, 
 		
		 /** The gravity. */
		 GRAVITY, 
 		
		 /** The repel. */
		 REPEL, 
 		
		 /** The none. */
		 NONE
    }

    static {
        BufferedImage idle = BaseCode.resources
                .loadImage("Textures/Enemies/Charger_AnimSheet_Idle.png");
        BufferedImage attack = BaseCode.resources
                .loadImage("Textures/Enemies/Charger_AnimSheet_Attack.png");
        BufferedImage regularLeft = BaseCode.resources
                .loadImage("Textures/Enemies/Regular_AnimSheet_Left.png");
        BufferedImage regularRight = BaseCode.resources
                .loadImage("Textures/Enemies/Regular_AnimSheet_Right.png");
        BufferedImage portalEnemy = BaseCode.resources
                .loadImage("Textures/Enemies/PortalMinion_AnimSheet_Left.png");

        // Fill the hashmap with tinted images for later use
        for (int i = 0; i < 6; i++) {
            Color temp = Colors.colorPicker(i);
            float alpha = 0.45f;
            if (temp == Colors.Blue) {
                alpha = 0.75f;
            }
            chargerIdleTextures.put(temp,
                    ImageTint.tintedImage(idle, temp, alpha));
            chargerAttackTextures.put(temp,
                    ImageTint.tintedImage(attack, temp, alpha));
            regularLeftTextures.put(temp,
                    ImageTint.tintedImage(regularLeft, temp, alpha));
            regularRightTextures.put(temp,
                    ImageTint.tintedImage(regularRight, temp, alpha));
            portalEnemyTextures.put(temp,
                    ImageTint.tintedImage(portalEnemy, temp, alpha));
        }
        chargerIdleTextures.put(Color.gray, idle);
        chargerAttackTextures.put(Color.gray, attack);
        regularLeftTextures.put(Color.gray, regularLeft);
        regularRightTextures.put(Color.gray, regularRight);
        portalEnemyTextures.put(Color.gray, portalEnemy);

        for (Direction dir : Direction.values()) {
            dyeTextures.put(
                    dir,
                    BaseCode.resources.loadImage("Textures/Hero/Dye_"
                            + dir.toString() + ".png"));
            dyeFireTextures.put(
                    dir,
                    BaseCode.resources.loadImage("Textures/Hero/Dye_"
                            + dir.toString() + "_Fire.png"));
        }
    }

    /**
     * Instantiates a new hero.
     */
    public Hero() {
        super(startingLocation.clone(), ConfigurationFileParser.getInstance().getHeroData().getHeroWidth(),
        		ConfigurationFileParser.getInstance().getHeroData().getHeroHeight()); // TODO remove magic numbers

        sizeScale = size.getY() / 9f;

        curPowerUp = CurPowerUp.NONE;
        color = Colors.randomColor();
        if (!bulletTextures.containsKey(color)) {
            bulletTextures.put(color,
                    ImageTint.tintedImage(BaseCode.resources
                            .loadImage("Textures/Dye_attack_projectile.png"),
                            color, 1f));
            muzzleTextures
                    .put(color,
                            ImageTint.tintedImage(
                                    BaseCode.resources
                                            .loadImage("Textures/Dye_attack_muzzle_flash_AnimSheet.png"),
                                    color, 1f));
        }
        directionState = Direction.NEUTRAL;
        dynamicDyepack = new DynamicDyePack(this);
        heroEffect = new HeroEffect(this);
        texture = BaseCode.resources.loadImage("Textures/Hero/Dye_NEUTRAL.png");

        collectedDyepacks = 0;
        collectedPowerups = 0;

        powerups = new TreeSet<PowerUp>();

        currentJetSpeed = defaultJetSpeed;
        currentGravity = defaultGravity;

        weaponRack = new ArrayList<Weapon>();
        weaponRack.add(defaultWeapon); // add default weapon
        currentWeapon = defaultWeapon;

        // Maps number keys to weaponRack index
        weaponHotkeys = new HashMap<Integer, Integer>();
        weaponHotkeys.put(KeyEvent.VK_1, 0);
        weaponHotkeys.put(KeyEvent.VK_2, 1);
        weaponHotkeys.put(KeyEvent.VK_3, 2);
        weaponHotkeys.put(KeyEvent.VK_4, 3);

        previousVelocity = new Vector2(0f, 0f);
        currentVelocity = new Vector2(0f, 0f);

        isInvin = false;
        isRepel = false;
        isFiring = false;
    }

    /**
     * Update movement.
     */
    public void updateMovement() {
        // Clamp the horizontal speed to speedLimit
        float velX = velocity.getX();
        velX = Math.min(speedLimitX, velX);
        velX = Math.max(-speedLimitX, velX);
        velocity.setX(velX);

        velocity.add(currentGravity);
        velocity.mult(drag);

        // Scale the velocity to the frame rate
        Vector2 frameVelocity = velocity.clone();
        // frameVelocity.mult(DyeHard.DELTA_TIME);
        center.add(frameVelocity);
    }

    /* (non-Javadoc)
     * @see dyehard.Collision.CollidableGameObject#update()
     */
    @Override
    public void update() {
        super.update();
        if (noMoreTeleport) {
            if (timer.isDone()) {
                noMoreTeleport = false;
            }
        }
        applyPowerups();

        // handleInput();
        // updateDirectionState();
        // updateMovement();
        // selectWeapon();
        clampToWorldBounds();
        dynamicDyepack.update();
        heroEffect.update();
    }

    /**
     * Apply powerups.
     */
    private void applyPowerups() {
        Set<PowerUp> destroyed = new TreeSet<PowerUp>();
        for (PowerUp p : powerups) {
            if (p.isDone()) {
                p.unapply(this);
                destroyed.add(p);
            }
        }

        powerups.removeAll(destroyed);

        for (PowerUp p : powerups) {
            p.apply(this);
        }
    }

    /**
     * Clamp to world bounds.
     */
    private void clampToWorldBounds() {
        // restrict the hero's movement to the boundary
        BoundCollidedStatus collisionStatus = collideWorldBound();
        if (collisionStatus != BoundCollidedStatus.INSIDEBOUND) {
            if (collisionStatus == BoundCollidedStatus.LEFT
                    || collisionStatus == BoundCollidedStatus.RIGHT) {
                velocity.setX(0);
                acceleration.setX(0);
            } else if (collisionStatus == BoundCollidedStatus.TOP
                    || collisionStatus == BoundCollidedStatus.BOTTOM) {
                velocity.setY(0);
                acceleration.setY(0);
            }
        }

        BaseCode.world.clampAtWorldBound(this);
    }

    /**
     * Move up.
     */
    public void moveUp() {
        // Upward speed needs to counter the effects of gravity
        totalThrust
                .add(new Vector2(0f, defaultJetSpeed - currentGravity.getY()));
    }

    /**
     * Move down.
     */
    public void moveDown() {
        totalThrust.add(new Vector2(0f, -defaultJetSpeed));
    }

    /**
     * Move left.
     */
    public void moveLeft() {
        totalThrust.add(new Vector2(-defaultJetSpeed, 0f));
    }

    /**
     * Move right.
     */
    public void moveRight() {
        totalThrust.add(new Vector2(defaultJetSpeed, 0));
    }

    /**
     * Move to.
     *
     * @param x the x
     * @param y the y
     */
    public void moveTo(float x, float y) {
        if ((DyeHardGame.getState() == DyeHardGame.State.PLAYING) && !flashing) {
            float xOffset = x - center.getX();
            float yOffset = y - center.getY();

            float theta = (float) (180.0 / Math.PI * Math.atan2(xOffset,
                    yOffset));

            if (Math.abs(xOffset) + Math.abs(yOffset) < 0.2f) {
                directionState = Direction.NEUTRAL;
            } else if (theta > 0) {
                if (theta < 22.5) {
                    directionState = Direction.UP;
                } else if (theta < 67.5) {
                    directionState = Direction.UPFORWARD;
                } else if (theta < 112.5) {
                    directionState = Direction.FORWARD;
                } else if (theta < 157.5) {
                    directionState = Direction.DOWNFORWARD;
                } else {
                    directionState = Direction.DOWN;
                }
            } else {
                if (theta > -22.5) {
                    directionState = Direction.UP;
                } else if (theta > -67.5) {
                    directionState = Direction.UPBACK;
                } else if (theta > -112.5) {
                    directionState = Direction.BACK;
                } else if (theta > -157.5) {
                    directionState = Direction.DOWNBACK;
                } else {
                    directionState = Direction.DOWN;
                }
            }

            setTexture();

            if (xOffset > 0) {
                center.setX(center.getX()
                        + Math.min((xOffset * heroSpeedRatio), maxHeroSpeed));
            } else if (xOffset < 0) {
                center.setX(center.getX()
                        + Math.max((xOffset * heroSpeedRatio), -maxHeroSpeed));
            }

            if (yOffset > 0) {
                center.setY(center.getY()
                        + Math.min((yOffset * heroSpeedRatio), maxHeroSpeed));
            } else if (yOffset < 0) {
                center.setY(center.getY()
                        + Math.max((yOffset * heroSpeedRatio), -maxHeroSpeed));
            }
        }
    }

    /**
     * Sets the texture.
     */
    private void setTexture() {
        if (isFiring) {
            switch (directionState) {
            case NEUTRAL:
                size.set(new Vector2(3.35f * sizeScale, 8.4f * sizeScale));
                break;
            case UP:
                size.set(new Vector2(5.2f * sizeScale, 6.9f * sizeScale));
                break;
            case DOWN:
                size.set(new Vector2(3.4f * sizeScale, 5.8f * sizeScale));
                break;
            case BACK:
                size.set(new Vector2(3.35f * sizeScale, 6.2f * sizeScale));
                break;
            case FORWARD:
                size.set(new Vector2(6.95f * sizeScale, 5.2f * sizeScale));
                break;
            case UPFORWARD:
                size.set(new Vector2(5.55f * sizeScale, 6.35f * sizeScale));
                break;
            case UPBACK:
                size.set(new Vector2(3.9f * sizeScale, 6.4f * sizeScale));
                break;
            case DOWNFORWARD:
                size.set(new Vector2(5.25f * sizeScale, 5.95f * sizeScale));
                break;
            case DOWNBACK:
                size.set(new Vector2(4.1f * sizeScale, 5.4f * sizeScale));
                break;
            }
            texture = dyeFireTextures.get(directionState);
        } else {
            switch (directionState) {
            case NEUTRAL:
                size.set(new Vector2(ConfigurationFileParser.getInstance().getHeroData().getHeroWidth(),
                		ConfigurationFileParser.getInstance().getHeroData().getHeroHeight()));
                break;
            case UP:
                size.set(new Vector2(4f * sizeScale, 7.25f * sizeScale));
                break;
            case DOWN:
                size.set(new Vector2(3.55f * sizeScale, 6.05f * sizeScale));
                break;
            case BACK:
                size.set(new Vector2(3.9f * sizeScale, 5.2f * sizeScale));
                break;
            case FORWARD:
                size.set(new Vector2(6.25f * sizeScale, 5.75f * sizeScale));
                break;
            case UPFORWARD:
                size.set(new Vector2(6.15f * sizeScale, 6.7f * sizeScale));
                break;
            case UPBACK:
                size.set(new Vector2(5.5f * sizeScale, 6.85f * sizeScale));
                break;
            case DOWNFORWARD:
                size.set(new Vector2(6f * sizeScale, 4.5f * sizeScale));
                break;
            case DOWNBACK:
                size.set(new Vector2(4.9f * sizeScale, 5.6f * sizeScale));
                break;
            }
            texture = dyeTextures.get(directionState);
        }
    }

    /**
     * Fire.
     */
    public void fire() {
        if (!flashing) {
            currentWeapon.fire();
        }
    }

    /**
     * Handle input.
     */
    private void handleInput() {
        velocity.add(totalThrust);
        totalThrust.set(0f, 0f);
    }

    // public void updateDirectionState() {
    // previousVelocity = currentVelocity.clone();
    // currentVelocity = velocity.clone();
    // Vector2 tempVelocity = currentVelocity.clone().sub(
    // previousVelocity.clone());
    //
    // if (tempVelocity.getY() > 1f && tempVelocity.getX() < -1f) {
    // directionState = Direction.TOPLEFT;
    // } else if (tempVelocity.getY() > 1f && tempVelocity.getX() > 1f) {
    // directionState = Direction.TOPRIGHT;
    // } else if (tempVelocity.getY() < -1f && tempVelocity.getX() < -1f) {
    // directionState = Direction.BOTTOMLEFT;
    // } else if (tempVelocity.getY() < -1f && tempVelocity.getX() > 1f) {
    // directionState = Direction.BOTTOMRIGHT;
    // } else if (tempVelocity.getY() > 1f) {
    // directionState = Direction.UP;
    // } else if (tempVelocity.getY() < -1f) {
    // directionState = Direction.DOWN;
    // } else if (tempVelocity.getX() < -1f) {
    // directionState = Direction.LEFT;
    // } else if (tempVelocity.getX() > 1f) {
    // directionState = Direction.RIGHT;
    // } else {
    // directionState = Direction.NEUTRAL;
    // }
    // }

    // Select a weapon in the weapon rack based on the input
    // private void selectWeapon() {
    // for (int hotkey : weaponHotkeys.keySet()) {
    // if (DyehardKeyboard.isKeyDown(hotkey)) {
    // int weaponIndex = weaponHotkeys.get(hotkey);
    // if (weaponIndex < weaponRack.size() && weaponIndex >= 0) {
    // currentWeapon = weaponRack.get(weaponIndex);
    // }
    // }
    // }
    // }

    /**
     * Always on top.
     */
    public void alwaysOnTop() {
        alwaysOnTop = true;
        dynamicDyepack.alwaysOnTop = true;
    }

    /**
     * Draw on top.
     */
    public void drawOnTop() {
        removeFromAutoDrawSet();
        addToAutoDrawSet();
        dynamicDyepack.removeFromAutoDrawSet();
        dynamicDyepack.addToAutoDrawSet();
        heroEffect.drawOnTop();
    }

    /**
     * Collect.
     *
     * @param dye the dye
     */
    public void collect(DyePack dye) {
        dye.activate(this);
        collectedDyepacks += 1;
    }

    /**
     * Collect.
     *
     * @param powerup the powerup
     */
    public void collect(PowerUp powerup) {
        // Only one powerup can be active at a time
        for (PowerUp p : powerups) {
            if (!(p instanceof Invincibility)) {
                p.unapply(this);
            }
        }
        powerups.clear();

        powerups.add(powerup);
        powerup.activate(this);
        collectedPowerups += 1;
    }

    /**
     * Register weapon.
     *
     * @param weapon the weapon
     */
    public void registerWeapon(Weapon weapon) {
        weaponRack.add(weapon);
    }

    /**
     * Dyepacks collected.
     *
     * @return the int
     */
    public int dyepacksCollected() {
        return collectedDyepacks;
    }

    /**
     * Powerups collected.
     *
     * @return the int
     */
    // Powerups Functions
    public int powerupsCollected() {
        return collectedPowerups;
    }

    /**
     * Gets the direction.
     *
     * @return the direction
     */
    public Direction getDirection() {
        return directionState;
    }

    /* (non-Javadoc)
     * @see dyehard.Actor#kill(Engine.Primitive)
     */
    @Override
    public void kill(Primitive who) {
        if ((damageOn)
                && (who.color != color)
                || ((who instanceof DeathGate) && curPowerUp == CurPowerUp.GHOST)) {

            damageHero(this, who);
        }
    }

    /* (non-Javadoc)
     * @see dyehard.Player.HeroInterfaces.HeroDamage#damageHero(dyehard.Player.Hero, Engine.Primitive)
     */
    @Override
    public void damageHero(Hero hero, Primitive who) {
        // Only one powerup can be active at a time
        for (PowerUp p : powerups) {
            p.unapply(this);
        }
        powerups.clear();

        if (!debugInvincibility) {
            GameState.RemainingLives--;
        }

        if (GameState.RemainingLives <= 0) {
            alive = false;
            DyeHardSound.play(DyeHardSound.loseSound);
        } else {
            powerups.add(new Invincibility());
            applyPowerups();
            DyeHardSound.play(DyeHardSound.lifeLostSound);
            hero.center.set(startingLocation.clone());
        }
    }

    /* (non-Javadoc)
     * @see dyehard.Actor#handleCollision(dyehard.Collision.CollidableGameObject)
     */
    @Override
    public void handleCollision(CollidableGameObject other) {
    	//System.out.println(other.getClass().toString());
    	// Sammy code
    	// If Dye touches the entrances of a Wormhole (GateDoor, StargatePath, 
    	// etc.) then allow the Wormhole 
        if (collisionOn && (other instanceof GateDoor) == false && 
        		(other instanceof StargatePath) == false && 
        		(other instanceof StargatePathFront) == false) {
            super.handleCollision(other);
        }
        else if (other instanceof StargatePath){
        	StargatePath oStargate = (StargatePath) other;
        	if (oStargate.dyeColor != this.color)
        		super.handleCollision(other);
        }
        	
    }

    /* (non-Javadoc)
     * @see dyehard.Player.HeroInterfaces.HeroCollision#collideWithHero(dyehard.Player.Hero, dyehard.Collision.CollidableGameObject)
     */
    @Override
    public void collideWithHero(Hero hero, CollidableGameObject other) {
        super.handleCollision(other);
    }

    /* (non-Javadoc)
     * @see dyehard.Actor#setColor(java.awt.Color)
     */
    @Override
    public void setColor(Color color) {
        this.color = color;
        if (!bulletTextures.containsKey(color)) {
            bulletTextures.put(color,
                    ImageTint.tintedImage(BaseCode.resources
                            .loadImage("Textures/Dye_attack_projectile.png"),
                            color, 1f));
            muzzleTextures
                    .put(color,
                            ImageTint.tintedImage(
                                    BaseCode.resources
                                            .loadImage("Textures/Dye_attack_muzzle_flash_AnimSheet.png"),
                                    color, 1f));
        }
    }

    /* (non-Javadoc)
     * @see dyehard.Actor#destroy()
     */
    @Override
    public void destroy() {
        return;
    }

    /**
     * Gets the start.
     *
     * @return the start
     */
    public Vector2 getStart() {
        return startingLocation.clone();
    }

    /* (non-Javadoc)
     * @see dyehard.DyehardRectangle#startFlashing()
     */
    @Override
    public void startFlashing() {
        super.startFlashing();
        heroEffect.startFlashing();
        dynamicDyepack.startFlashing();
        flashing = true;
        damageOn = false;
        noMoreTeleport = true;
        timer.reset();
    }

    /* (non-Javadoc)
     * @see dyehard.DyehardRectangle#stopFlashing()
     */
    @Override
    public void stopFlashing() {
        super.stopFlashing();
        heroEffect.stopFlashing();
        dynamicDyepack.stopFlashing();
        flashing = false;
        damageOn = true;
    }
}
