package dyehard.Enemies;

import java.awt.Color;

import Engine.BaseCode;
import Engine.Vector2;
import dyehard.Enums.EnemyType;
import dyehard.Player.Hero;
import dyehard.Resources.ConfigurationFileParser;
import dyehard.Util.DyeHardSound;
import dyehard.Util.Timer;

// TODO: Auto-generated Javadoc
/**
 * The Class ChargerEnemy.
 */
public class ChargerEnemy extends Enemy {
    
    /** The chasing. */
    private boolean chasing;    
    
    /** The set image. */
    private boolean setImage;    
    
    /** The charge. */
    private boolean charge;    
    
    /** The x offset. */
    private final float xOffset;    
    
    /** The y offset. */
    private final float yOffset;    
    
    /** The timer. */
    private final Timer timer;    ; 

    /**
     * Instantiates a new charger enemy.
     *
     * @param center, where the charger will be spawned
     * @param currentHero, the hero
     */
    public ChargerEnemy(Vector2 center, Hero currentHero) {
        super(center, 0, 0, currentHero,
                "Textures/Enemies/Charger_AnimSheet_Idle.png");

        width = ConfigurationFileParser.getInstance().getEnemyData(EnemyType.CHARGER_ENEMY).getWidth();
        height = ConfigurationFileParser.getInstance().getEnemyData(EnemyType.CHARGER_ENEMY).getHeight();
        sleepTimer = ConfigurationFileParser.getInstance().getEnemyData(EnemyType.CHARGER_ENEMY).getSleepTimer() * 1000f;
        speed = ConfigurationFileParser.getInstance().getEnemyData(EnemyType.CHARGER_ENEMY).getSpeed();

        chasing = false;
        setImage = false;
        charge = false;
        xOffset = 45f;
        yOffset = 7f;

        setUsingSpriteSheet(true);
        setSpriteSheet(texture, 340, 140, 13, 2);

        timer = new Timer(2000);
    }

    /* (non-Javadoc)
     * @see dyehard.Enemies.Enemy#update()
     */
    @Override
    public void update() {
        float deltaT = timer.deltaTime();

        if (harmlessTimer.isDone()) {
            harmlessTimer.setActive(false);
            color = null;
            if (chasing) {
                setSpriteSheet(Hero.chargerAttackTextures.get(Color.gray), 340,
                        140, 11, 2);
            } else {
                setSpriteSheet(Hero.chargerIdleTextures.get(Color.gray), 340,
                        140, 11, 2);
            }
        }

        if (chasing) {
            DyeHardSound.playLoop(DyeHardSound.enemySpaceship1);
            soundOn = true;
            if (!setImage) {
                if (color == null) {
                    setSpriteSheet(Hero.chargerAttackTextures.get(Color.gray),
                            340, 140, 11, 2);
                } else {
                    setSpriteSheet(Hero.chargerAttackTextures.get(color), 340,
                            140, 11, 2);
                }
                setImage = true;
                timer.reset();
            }
            if (timer.isDone() && (!charge)) {
                charge = true;
            }
            if (charge) {
                center = center.clone().add(
                        new Vector2(-speed * (deltaT * 500), 0f));
            }
        } else {
            center = center.clone().add(
                    new Vector2(-speed * (deltaT * 100), 0f));
            float xDif = Math.abs(hero.center.getX() - center.getX());
            float yDif = center.getY() - hero.center.getY();
            if ((xDif < xOffset) && (yDif < yOffset)
                    && (center.getX() < (BaseCode.world.getWidth()) - 5)) {
                chasing = true;
            }
        }

        if (center.getX() < BaseCode.world.getPositionX()) {
            destroy();
        }
    }

    /* (non-Javadoc)
     * @see dyehard.Enemies.Enemy#setColor(java.awt.Color)
     */
    @Override
    public void setColor(Color color) {
        if (this.color != color) {
            this.color = color;
            int temp = getCurFrame();
            if (chasing) {
                setSpriteSheet(Hero.chargerAttackTextures.get(color), 340, 140,
                        11, 8);
            } else {
                setSpriteSheet(Hero.chargerIdleTextures.get(color), 340, 140,
                        11, 8);
            }
            setCurFrame(temp);
        }
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Charger";
    }

    /* (non-Javadoc)
     * @see dyehard.Actor#destroy()
     */
    @Override
    public void destroy() {
        if (soundOn) {
            DyeHardSound.stopSound(DyeHardSound.enemySpaceship1);
        }
        super.destroy();
    }
}