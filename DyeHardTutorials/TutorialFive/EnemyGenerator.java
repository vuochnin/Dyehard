package TutorialFive;

import java.util.Random;

import Engine.BaseCode;
import Engine.Vector2;
import dyehard.Enemies.ChargerEnemy;
import dyehard.Enemies.CollectorEnemy;
import dyehard.Enemies.EnemyManager;
import dyehard.Enemies.RegularEnemy;
import dyehard.Enemies.ShootingEnemy;
import dyehard.Player.Hero;
import dyehard.Resources.ConfigurationFileParser;
import dyehard.Util.Timer;

public class EnemyGenerator {
    private final float enemyFrequency = ConfigurationFileParser.getInstance().getWorldData().getWorldEnemyFrequency() * 1000f;
    private static Random RANDOM = new Random();
    private static Hero hero;
    private static Timer t;

    public EnemyGenerator(Hero hero) {
        t = new Timer(enemyFrequency);
        EnemyGenerator.hero = hero;
    }

    public static void generateEnemy() {
        // TODO: Replace magic numbers
        float randomY = RANDOM.nextInt((int) BaseCode.world.getHeight() - 8) + 5;
        Vector2 position = new Vector2(BaseCode.world.getWidth() + 10, randomY);
        switch (RANDOM.nextInt(4)) {
        case 1:
            EnemyManager.getInstance().registerEnemy(new CollectorEnemy(position, hero));
            break;
        case 2:
        	EnemyManager.getInstance().registerEnemy(new ChargerEnemy(position, hero));
            break;
        case 3:
        	EnemyManager.getInstance().registerEnemy(new ShootingEnemy(position, hero));
        default:
        	EnemyManager.getInstance().registerEnemy(new RegularEnemy(position, hero));
            break;
        }
        t.reset();
    }


    public void clearEnemy() {
    	EnemyManager.getInstance().clear();
    }

    public void update() {
        if (t.isDone()) {
            generateEnemy();
        }
    }
}

