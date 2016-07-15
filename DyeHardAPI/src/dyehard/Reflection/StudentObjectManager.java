package dyehard.Reflection;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import Engine.Vector2;
import dyehard.Enemies.Enemy;
import dyehard.Enemies.EnemyManager;
import dyehard.Player.Hero;

/**
 * The Class StudentObjectManager.
 * 
 * @purpose	To perform the actual validation of a student's work. If this class
 * 			is the information validator, then ClassReflector is the information
 * 			extractor.
 */
public class StudentObjectManager {
    
    /** The student hero object. */
    public static Object studentHero;
    
    /** The hero. */
    public static Hero hero;
    
    /** The enemies. */
    public static HashMap<Object, Enemy> enemies;

    /** The student obj ref. */
    public static ClassReflector studentObjRef;
    
    /** The use student obj. */
    private static boolean useStudentObj = false;

    // This is a Static Initializer Block to initialize static members.
    // This is used since the initialized members are complex (object, HashMap)
    static {
        studentHero = null;
        enemies = new HashMap<Object, Enemy>();
    }

    /**
     * Validate
     * 
     * @purpose	Create a ClassReflector from StudentObj
     */
    public static void validate() {
    	
        studentObjRef = new ClassReflector("StudentObj");
        
        studentObjRef.reflect();
        
        String[] arrayOfConstructors = { "public StudentObj()",
                "public StudentObj(Engine.Vector2,float,float)" };
        
        String[] arrayOfMethods = {
                "public float StudentObj.getWidth()",
                "public float StudentObj.getHeight()",
                "public void StudentObj.setWidth(float)",
                "public void StudentObj.setHeight(float)",
                "public void StudentObj.setCenter(float,float)",
                "public Engine.Vector2 StudentObj.getCenter()",
                "public void StudentObj.setTexture(java.awt.image.BufferedImage)",
                "public java.awt.image.BufferedImage StudentObj.getTexture()" };
        
        useStudentObj = studentObjRef.validate(arrayOfConstructors, arrayOfMethods);
    }

    /**
     * Update.
     */
    public static void update() {
        if (studentHero != null) {
            studentHeroUpdate();
        }
        studentEnemyUpdate();
    }

    /**
     * Register hero.
     *
     * @param heroObj is the student Hero
     * @return the hero
     */
    public static Hero registerHero(Object heroObj) {
    	
    	// Check to see if the passed object or useStudentObj is null
        if ((heroObj == null) || (!useStudentObj)) {
            return null;
        } else {
            studentHero = heroObj;	// Set the studentHero as the passed obj.
        }

        // If there currently is no set hero, create one.
        if (hero == null) {
            hero = new Hero();
        }
        
        // Set the hero's center, size, and texture based on studentObjRef's
        // methods
        hero.center = (Vector2) studentObjRef.invokeMethod(heroObj, "getCenter");
        hero.size.set((float) studentObjRef.invokeMethod(heroObj, "getWidth"),
                (float) studentObjRef.invokeMethod(heroObj, "getHeight"));
        hero.texture = (BufferedImage) studentObjRef.invokeMethod(heroObj,
                "getTexture");

        return hero;
    }

    /**
     * Student hero update.
     */
    private static void studentHeroUpdate() {
        if (useStudentObj) {
            hero.center = (Vector2) studentObjRef.invokeMethod(studentHero,
                    "getCenter");
            hero.size
                    .set((float) studentObjRef.invokeMethod(studentHero,
                            "getWidth"), (float) studentObjRef.invokeMethod(
                            studentHero, "getHeight"));
            hero.texture = (BufferedImage) studentObjRef.invokeMethod(
                    studentHero, "getTexture");
        }
    }

    /**
     * Register enemy.
     *
     * @param e the e
     * @return the enemy
     */
    public static Enemy registerEnemy(Object e) {
        if ((e == null) || (!useStudentObj)) {
            return null;
        }

        Vector2 c = (Vector2) studentObjRef.invokeMethod(e, "getCenter");
        float x = (float) studentObjRef.invokeMethod(e, "getWidth");
        float y = (float) studentObjRef.invokeMethod(e, "getHeight");
        BufferedImage t = (BufferedImage) studentObjRef.invokeMethod(e,
                "getTexture");

        Enemy enemy = new Enemy(c, x, y, hero, t);
        enemy.speed = 0.15f;
        EnemyManager.getInstance().registerEnemy(enemy);
        enemies.put(e, enemy);

        return enemy;
    }

    /**
     * Student enemy update.
     */
    private static void studentEnemyUpdate() {
        for (Object e : enemies.keySet()) {
            float x = (float) studentObjRef.invokeMethod(e, "getWidth");
            float y = (float) studentObjRef.invokeMethod(e, "getHeight");
            studentObjRef.invokeMethod(e, "setWidth", x);
            studentObjRef.invokeMethod(e, "setHeight", y);
            x = (float) studentObjRef.invokeMethod(e, "getWidth");
            y = (float) studentObjRef.invokeMethod(e, "getHeight");
            Vector2 c = (Vector2) studentObjRef.invokeMethod(e, "getCenter");
            studentObjRef.invokeMethod(e, "setCenter", c.getX(), c.getY());
            Color color = (Color) studentObjRef.invokeMethod(e, "getColor");
            enemies.get(e).setSize(x, y);
            enemies.get(e).setCenter(c);
            enemies.get(e).setColor(color);
        }
    }

    /**
     * Clear all enemies.
     */
    public static void clear() {
        for (Enemy e : enemies.values()) {
            e.destroy();
        }
        enemies.clear();
    }

    /**
     * Gets the enemies.
     *
     * @return the enemies
     */
    public static HashMap<Object, Enemy> getEnemies() {
        return enemies;
    }

    /**
     * Use student obj.
     *
     * @return true, if successful
     */
    public static boolean useStudentObj() {
        return useStudentObj;
    }
    
    /**
     * Check to see if the object being passed in is a certain class/interface
     * 
     * @param obj, the object being evaluated
     * @param classType, the type of class/interface being checked
     * @return true if object is a classType, false otherwise
     */
    public boolean checkClass(Object obj, Class<?> classType) {
    	return classType.isInstance(obj);
    }
}
