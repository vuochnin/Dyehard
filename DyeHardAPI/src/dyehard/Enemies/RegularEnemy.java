package dyehard.Enemies;

import java.awt.Color;

import Engine.BaseCode;
import Engine.Vector2;
import dyehard.Enums.EnemyType;
import dyehard.Player.Hero;
import dyehard.Resources.ConfigurationFileParser;

// TODO: Auto-generated Javadoc
/**
 * The Class RegularEnemy.
 */
public class RegularEnemy extends Enemy {
    
    /** The left. */
    private boolean left;

    /**
     * Instantiates a new regular enemy.
     *
     * @param center the center
     * @param currentHero the current hero
     */
    public RegularEnemy(Vector2 center, Hero currentHero) {
        super(center, 0, 0, currentHero,
                "Textures/Enemies/Regular_AnimSheet_Left.png");
        setUsingSpriteSheet(true);
        setSpriteSheet(texture, 212, 170, 5, 5);
        left = false;

        width = ConfigurationFileParser.getInstance().getEnemyData(EnemyType.SHOOTING_ENEMY).getWidth();
        height = ConfigurationFileParser.getInstance().getEnemyData(EnemyType.SHOOTING_ENEMY).getHeight();
        sleepTimer = ConfigurationFileParser.getInstance().getEnemyData(EnemyType.SHOOTING_ENEMY).getSleepTimer() * 1000f;
        speed = ConfigurationFileParser.getInstance().getEnemyData(EnemyType.SHOOTING_ENEMY).getSpeed();
    }

    /* (non-Javadoc)
     * @see dyehard.Enemies.Enemy#update()
     */
    @Override
    public void update() {
        super.update();
        if (enemyState == EnemyState.CHASEHERO) {
            soundOn = true;
        }

        if ((velocity.getX() > 0) && left) {
            if (color == null) {
                texture = BaseCode.resources
                        .loadImage("Textures/Enemies/Regular_AnimSheet_Right.png");
                setSpriteSheet(texture, 212, 170, 5, 5);
            } else {
                setSpriteSheet(Hero.regularRightTextures.get(color), 212, 170,
                        5, 5);
            }
            left = false;
        } else if ((velocity.getX() < 0) && !left) {
            if (color == null) {
                texture = BaseCode.resources
                        .loadImage("Textures/Enemies/Regular_AnimSheet_Left.png");
                setSpriteSheet(texture, 212, 170, 5, 5);
            } else {
                setSpriteSheet(Hero.regularLeftTextures.get(color), 212, 170,
                        5, 5);
            }
            left = true;
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
            if (left) {
                setSpriteSheet(Hero.regularLeftTextures.get(color), 212, 170,
                        5, 5);
            } else {
                setSpriteSheet(Hero.regularRightTextures.get(color), 212, 170,
                        5, 5);
            }
            setCurFrame(temp);
        }
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Shooting";
    }
}