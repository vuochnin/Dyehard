import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

import javax.swing.JOptionPane;

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
import dyehard.Reflection.ClassReflector;
import dyehard.Reflection.StudentObjectManager;
import dyehard.Util.Colors;

// TODO: Auto-generated Javadoc
/**
 * The Class SampleUserCode.
 */
public class SampleUserCode extends DyeHardGame {
    
    /** The hero. */
    private Hero privateHero;
    
    /** The Lab currently being used */
    private int labNum = -1;
    
    /** The hero. */
    
    private ClassReflector cf;
    
    private Class<?> hero;
    
    private Object genericHero;
    
    private Object heroInst = null;

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
    	
    	// Request the lab number, and parse it as an int
    	String labString = JOptionPane.showInputDialog("Please type in lab number");
    	labNum = Integer.parseInt(labString);
    	
    	// Based on the provided int, initialize the corresponding lab number
    	switch(labNum)
    	{
    	
    	// LAB 0: Class Hierarchy Lab
    	case 0:
    		initializeLab0();
    		break;
    		
    	// LAB 1: Creating a new hero
    	case 1:
    		initializeLab1();
    		break;
    		
    	// LAB 2: Verify Fields
    	case 2:
    		initializeLab2();
    		break;
    		
    	// Nothing. Break and exit program
    	default:
    		JOptionPane.showMessageDialog(window, "INVALID NUMBER");
    		System.exit(labNum);
    		break;
    			
    	}
    }
    
    /**
     * Lab 0 - Class Hierarchy.
     * 
     * Basic Lab that validates that the validates the class hierachy. The
     * student should have made a class with a default constructor which
     * extends the Hero class.
     * 
     */
    private void initializeLab0() {
    	
    	// Get the class from user input, assign to hero
    	hero = StudentObjectManager.getClassFromInput();
    	
    	// Create a new instance of class and Hero to modify/evaluate
    	heroInst = StudentObjectManager.createObj(hero);
    	privateHero = new Hero();
    	
    	// Try to set the texture of Hero to the class' getTexture() texture
    	try {
    		Method methodToCall = hero.getDeclaredMethod("getTexture");
			BufferedImage img = (BufferedImage) methodToCall.invoke(heroInst);
			privateHero.texture = img;
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException 
				| IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
		
    	// Create an instance of the class, assign to heroInst
    	//heroInst = StudentObjectManager.createObj(hero);
		//privateHero = StudentObjectManager.registerHero(hero);
    	

    	
    	// if StudentObj is not a Hero class, it failed the lab
		/*if(hero.getSuperclass() != Hero.class){
    		JOptionPane.showMessageDialog(window, "Invalid Class. Current class"
    			+ " is of " + hero.getName());
    		System.exit(labNum);
    	} else
    		System.out.println("Success! Student class is: " + hero.getName());
        */
    }
    
    /**
     * Lab 1
     * 
     * Lab that validates the class has the listed methods
     * 
     */
    private void initializeLab1(){
    	// Request the class name
    	String className = JOptionPane.showInputDialog("Please type in your class name");
    	
    	String[] arrayOfConstructors = { 
    			"public StudentObj()",
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
    	
    	try {
    		// Instantiate the class based on the provided string
    		hero = Class.forName(className);
    		
    		// Validate the class
    		boolean isValid =
    		StudentObjectManager.validate(className, arrayOfConstructors, arrayOfMethods);
    		
    		if (isValid == false){
    			JOptionPane.showMessageDialog(window, "Class is not valid");
    			System.exit(labNum);
    	    }
    	    
    		privateHero = new Hero();
    		heroInst = StudentObjectManager.createObj(hero);
    		
    		try {
				privateHero.center = (Vector2) hero.getDeclaredMethod("getCenter").invoke(heroInst);
				privateHero.size.set((float) hero.getDeclaredMethod("getWidth").invoke(heroInst), 
						(float) hero.getDeclaredMethod("getHeight").invoke(heroInst));
				privateHero.texture = (BufferedImage) hero.getDeclaredMethod("getTexture").invoke(heroInst);
	
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    		
 
    	} catch (ClassNotFoundException e){
    		e.printStackTrace();
    	}
    }
    
    private void initializeLab2(){
    	// Request the class name
    	String className = JOptionPane.showInputDialog("Please type in your class name");
    	
    	// TempClass to use for testing Fields
    	class tempClass {
    		private float height = 2;	// Test correctness
    		public float width = 2;		// Test privacy/access
    		private boolean color = false;	// Test type
    	};
    	
    	tempClass test = new tempClass();
    	
    	// Validate the class based on Test's fields
		cf = new ClassReflector(className);
		cf.reflect();
		cf.verifyFields(test.getClass().getDeclaredFields());
    }


    /**
     * Sample1 ini.
     * 
     * This uses the set of arrays inside StudentObjectManager to reflect and
     * validate a StudentObj based on its constructors and methods.
     */
    private void sample1Ini() {
        StudentObjectManager.validate();
        //hero = new StudentObj();
        privateHero = StudentObjectManager.registerHero(hero);
    }

    /**
     * Sample2 ini.
     * 
     * This simply adds the different types of powerups that can affect the
     * hero. 
     * 
     * TODO: Maybe add tutorial so users can create their own powerup?
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
     * 
     * Create a new Hashset of enemies defined by StudentObj.
     * 
     * TODO: Maybe add tutorial so students can create their own enemy, then
     * test it here?
     */
    private void sample3Ini() {
        enemies = new HashSet<StudentObj>();
        RANDOM = new Random();
    }

    /**
     * Sample4 ini.
     * 
     * Wormhole tutorial. 
     * 
     * TODO: Remove since it is too basic. Replace with players creating their
     * own weapon? Would need to make a template for that.
     */
    private void sample4Ini() {
        // new WormHole(hero, Colors.randomColor(), 30f, 15f, 100f, 20f);
    }

    /* (non-Javadoc)
     * @see dyehard.DyeHardGame#update()
     */
    @Override
    protected void update() {

    	switch (labNum){
    	case 0:
    		lab0Update();
    		break;
    	
    	case 1:
    		lab1Update();
    		break;
    		
    	case 2:
    		// lab2Update();
    		break;
    	}
    	
    	//lab1Update();
    	
    	// sample1Update();
        // sample2Update();
        // sample3Update();
        // sample4Update();
        
    }
    
    private void lab0Update(){
    	UpdateManager.getInstance().update();
        CollisionManager.getInstance().update();
        StudentObjectManager.update();
    }
    
    private void lab1Update() {
    	UpdateManager.getInstance().update();
        CollisionManager.getInstance().update();
        StudentObjectManager.update();
        
        try {
        	hero.getDeclaredMethod("setCenter", float.class, float.class).invoke(heroInst, mouse.getWorldX(), mouse.getWorldY());
        	privateHero.center = (Vector2) hero.getDeclaredMethod("getCenter").invoke(heroInst);
			
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException 
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
        
        //privateHero.moveTo(mouse.getWorldX(), mouse.getWorldY());
    }

    /**
     * Sample1 update.
     */
    private void sample1Update() {
        UpdateManager.getInstance().update();
        CollisionManager.getInstance().update();
        StudentObjectManager.update();
        // cf.invokeMethod(hero, "moveTo", mouse.getWorldX(),
        // mouse.getWorldY();
        // hero.moveTo(mouse.getWorldX(), mouse.getWorldY());

        // TODO: This
        //Class<?> genericObj = Class.forName("StudentObj");
        //genericObj.getConstructor().newInstance();
       
        
        //if (hero == null)
        	//Method m = genericObj.class.getDeclaredMethod("setCenter", float.class, float.class);
        	//Method m = null;
        	//genericHero.getMethod("setCenter", float.class, float.class);
        
        //hero.setCenter(mouse.getWorldX(), mouse.getWorldY());
        

        
        
        if ((keyboard.isButtonDown(KeyEvent.VK_F)) || (mouse.isButtonDown(1))) {
            // cf.invokeMethod(hero, "fire");
            privateHero.currentWeapon.fire();
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