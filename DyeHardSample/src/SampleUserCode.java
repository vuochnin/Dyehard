import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

import Engine.BaseCode;
import Engine.Text;
import Engine.Vector2;
import dyehard.DyeHardGame;
import dyehard.UpdateManager;
import dyehard.Collectibles.DyePack;
import dyehard.Collectibles.Ghost;
import dyehard.Collectibles.Invincibility;
import dyehard.Collectibles.Magnetism;
import dyehard.Collectibles.Overload;
import dyehard.Collectibles.PowerUp;
import dyehard.Collectibles.Repel;
import dyehard.Collectibles.SlowDown;
import dyehard.Collectibles.SpeedUp;
import dyehard.Collectibles.Unarmed;
import dyehard.Collision.CollisionManager;
import dyehard.Player.Hero;
import dyehard.Reflection.StudentObjectManager;
import dyehard.Util.Colors;

// TODO: Auto-generated Javadoc
/**
 * The Class SampleUserCode.
 */
public class SampleUserCode extends DyeHardGame {
    
    /** The h. */
    private Hero h;
    
    /** The hero. */
    // private ClassReflector cf;
    private StudentObj hero;

    /** The powerup text. */
    private List<Text> powerupText;
    
    /** The power up types. */
    private List<PowerUp> powerUpTypes;

    /** The enemies. */
    private HashSet<StudentObj> enemies;
    
    /** The random. */
    private Random RANDOM = new Random();

    /* (non-Javadoc)
     * @see dyehard.DyeHardGame#initialize()
     */
    @Override
    protected void initialize() {
    	//resources.setClassInJar(new JarResource());
        sample1Ini();
        sample2Ini();
        sample3Ini();
        //sample4Ini();
    }

    /**
     * Sample1 ini.
     */
    private void sample1Ini() {
        StudentObjectManager.validate();
        hero = new StudentObj();
        h = StudentObjectManager.registerHero(hero);
    }

    /**
     * Sample2 ini.
     */
    private void sample2Ini() {
        powerupText = new ArrayList<Text>();

        powerUpTypes = new ArrayList<PowerUp>();

        powerUpTypes.add(new Ghost());
        powerUpTypes.add(new Invincibility());
        powerUpTypes.add(new Magnetism());
        powerUpTypes.add(new Overload());
        powerUpTypes.add(new SlowDown());
        powerUpTypes.add(new SpeedUp());
        powerUpTypes.add(new Unarmed());
        powerUpTypes.add(new Repel());
    }

    /**
     * Sample3 ini.
     */
    private void sample3Ini() {
        enemies = new HashSet<StudentObj>();
        RANDOM = new Random();
    }

    /**
     * Sample4 ini.
     */
    private void sample4Ini() {
        //new WormHole(hero, Colors.randomColor(), 30f, 15f, 100f, 20f);
    }

    /* (non-Javadoc)
     * @see dyehard.DyeHardGame#update()
     */
    @Override
    protected void update() {

    	sample1Update();
        sample2Update();
        sample3Update();
        // sample4Update();
        
    }

    /**
     * Sample1 update.
     */
    private void sample1Update() {
        UpdateManager.getInstance().update();
        CollisionManager.getInstance().update();
        StudentObjectManager.update();
        // cf.invokeMethod(hero, "moveTo", mouse.getWorldX(),
        // mouse.getWorldY());
        // hero.moveTo(mouse.getWorldX(), mouse.getWorldY());

        hero.setCenter(mouse.getWorldX(), mouse.getWorldY());

        if ((keyboard.isButtonDown(KeyEvent.VK_F)) || (mouse.isButtonDown(1))) {
            // cf.invokeMethod(hero, "fire");
            h.currentWeapon.fire();
        }
    }

    /**
     * Sample2 update.
     */
    private void sample2Update() {
        updatePowerupText();

        if (keyboard.isButtonTapped(KeyEvent.VK_P)) {
            float randomY = RANDOM
                    .nextInt((int) BaseCode.world.getHeight() - 8) + 5;
            Vector2 position = new Vector2(BaseCode.world.getWidth() - 5,
                    randomY);
            PowerUp randomPowerUp = powerUpTypes.get(RANDOM
                    .nextInt(powerUpTypes.size()));
            randomPowerUp.initialize(position);
        }

        if (keyboard.isButtonTapped(KeyEvent.VK_D)) {
            float randomY = RANDOM
                    .nextInt((int) BaseCode.world.getHeight() - 8) + 5;
            Vector2 position = new Vector2(BaseCode.world.getWidth() - 5,
                    randomY);
            Color randomColor = Colors.randomColor();
            DyePack dye = new DyePack(randomColor);
            dye.initialize(position);
        }
    }

    /**
     * Sample3 update.
     */
    private void sample3Update() {
        for (StudentObj temp : enemies) {
            temp.setWidth((RANDOM.nextFloat() + 0.1f) * 7f);
            temp.setHeight((RANDOM.nextFloat() + 0.1f) * 7f);
        }
        if (keyboard.isButtonTapped(KeyEvent.VK_C)) {
            for (StudentObj temp : enemies) {
                temp.setColor(Colors.randomColor());
            }
        }
        if (keyboard.isButtonTapped(KeyEvent.VK_E)) {
            float randomY = RANDOM
                    .nextInt((int) BaseCode.world.getHeight() - 8) + 5;
            Vector2 position = new Vector2(BaseCode.world.getWidth() - 20,
                    randomY);
            StudentObj e = new StudentObj(position, 5f, 5f);
            enemies.add(e);
            StudentObjectManager.registerEnemy(e);
        }
    }

    /**
     * Sample4 update.
     */
    private void sample4Update() {
        float randomY = RANDOM.nextInt((int) BaseCode.world.getHeight() - 8) + 5;
        Vector2 position = new Vector2(BaseCode.world.getWidth() - 5, randomY);
        if (keyboard.isButtonTapped(KeyEvent.VK_G)) {
             //new WormHole(hero, Colors.randomColor(), 60f, 15f,
            // position.getX(),
            // position.getY());
        }
    }

    /**
     * Update powerup text.
     */
    private void updatePowerupText() {
        TreeSet<PowerUp> sortedPowerups = new TreeSet<PowerUp>(
                new Comparator<PowerUp>() {
                    @Override
                    public int compare(PowerUp o1, PowerUp o2) {
                        return (int) (o1.getRemainingTime() - o2
                                .getRemainingTime());
                    }
                });
        // sortedPowerups.addAll(hero.powerups);

        if (sortedPowerups.size() > powerupText.size()) {
            for (int i = powerupText.size(); i < sortedPowerups.size(); ++i) {
                powerupText.add(createTextAt(3f, BaseCode.world.getHeight() - 3
                        - i * 2));
            }
        }

        int i = 0;
        for (PowerUp p : sortedPowerups) {
            powerupText.get(i).setText(p.toString());
            i++;
        }

        for (; i < powerupText.size(); ++i) {
            powerupText.get(i).setText("");
        }
    }

    /**
     * Creates the text at.
     *
     * @param x the x-coordinate of the text center
     * @param y the y-coordinate of the text center
     * @return the text as Text
     */
    private Text createTextAt(float x, float y) {
        Text text = new Text("", x, y);
        text.setFrontColor(Color.white);
        text.setBackColor(Color.black);
        text.setFontSize(18);
        text.setFontName("Arial");
        return text;
    }

}