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
import TutorialFour.DebrisGenerator;
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
import dyehard.Obstacles.Debris;
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
    
    private String className = "";
    
    /** The hero. */
    
    private ClassReflector cf;
    
    private Class<?> hero;
    
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
    	
    	// Request the class name, and store under className
    	className = JOptionPane.showInputDialog("Please type in your class name");
    	
    	// Based on the provided int, initialize the corresponding lab number
    	switch(labNum)
    	{
    	
    	// LAB 0: Class Hierarchy, Basic Fields
    	case 0:
    		initializeLab0();
    		break;
    		
    	// LAB 1: New Hero Texture, Methods
    	case 1:
    		initializeLab1();
    		break;
    		
    	// LAB 2: New Constructors
    	case 2:
    		initializeLab2();
    		break;
    	
    	// LAB 3: Creating a new Debris Subclass
    	case 3:
    		initializeLab3();
    		break;
    	
    	// LAB 4: All together, with a new Weapon
    	case 4:
    		initializeLab4();
    		break;
    		
    	// LAB 5: Making a level. 
    	case 5:
    		initializeLab5();
    	// Nothing. Break and exit program
    	default:
    		JOptionPane.showMessageDialog(window, "INVALID NUMBER");
    		System.exit(labNum);
    		break;
    			
    	}
    }
    
    private void initializeLab0() {
		// TODO Auto-generated method stub
		
	}

    /**
     * Update the game state based on the initialized lab.
     * @see	dyehard.DyehardGame#update()
     */
    @Override
    protected void update() {
    	switch (labNum){
    	case 0:
    		break;
    	case 1:
    		lab1Update();
    		break;
    	case 2:
    		//lab2Update();
    		break;
    	case 3:
    		break;
    	case 4:
    		lab4Update();
    	}
    }

	/**
     * Lab 1 - Simple Inheritance, simple field values
     * 
     * Basic Lab that validates if a student has made a class that extends Hero,
     * and contains float values for variables "width" and "height".
     * 
     */
    private void initializeLab1() {
    	// Get the class from user input and see if it extends Hero.
    	// If not, the student class does not extend Hero.
    	if(!Hero.class.isAssignableFrom(hero = StudentObjectManager.getClassFromString(className))){
    		System.out.println("Provided class does not extend Hero");
    		System.exit(0);
    	}
    	
    	// Create a new instance of class. Reflection requires an instance of an obj
    	heroInst = StudentObjectManager.createObj(hero);

    	try {
    		// Must always create and reflect before anything!
    		cf = new ClassReflector(className);
    		cf.reflect();
    		
    		// Get the new width and height.
    		float newWidth, newHeight = 0;
    		newWidth = (float) cf.getFieldValue("width", heroInst);
    		newHeight = (float) cf.getFieldValue("height", heroInst);
    		
    		// Create a new hero with the new width and height
    		privateHero = new Hero();
	    	privateHero.size.set(newWidth, newHeight);
	    	
		} catch (SecurityException | IllegalArgumentException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Lab 1 - Simple Inheritance, simple field values
     * 
     * Update Hero position and fire
     * 
     */
    private void lab1Update(){
    	// Update all applicable updates
    	UpdateManager.getInstance().update();
        CollisionManager.getInstance().update();
        StudentObjectManager.update();

        // Update Hero position
        privateHero.center.setX(mouse.getWorldX());
        privateHero.center.setY(mouse.getWorldY());

        // Hero shoot
        if ((keyboard.isButtonDown(KeyEvent.VK_F))
                || (mouse.isButtonDown(1))) {
        	privateHero.currentWeapon.fire();
        }
    }

    /**
     * Lab 2 - Hero Texture and Method Validation
     */
    private void initializeLab2() {
    	// Used to track the amount of correct method signatures
    	boolean hasMethod = false;
    	
    	// Get the class from user input, assign to hero
    	hero = StudentObjectManager.getClassFromString(className);
    	
    	// Create a new instance of class. Reflection requires an instance of an obj
    	heroInst = StudentObjectManager.createObj(hero);
    	
    	// Create a new Hero instance.
    	privateHero = new Hero();
    	cf = new ClassReflector(className);
    	cf.getArrayOfMethods().toString();
    	
    	// Try to set the texture of Hero to the class' getTexture() texture
    	try {
    		// Search for the method by its name, then see if it takes no args
    		if (cf.getMethodByName("getTexture") != null &&
    			cf.getMethodByName("getTexture").getGenericParameterTypes().length == 0){
    			hasMethod = true;
    		} else {
    			System.out.println("Class contains getTexture()");
    		}
    		
    		if (cf.getMethodByName("getColorWrong") != null){
    			System.out.println("Class does not contain getColorWrong()");
    		}
    		
    		if (cf.getMethodByName("getWidth") != null){
    			System.out.println("Class does contain getWidth");
    		}
    		
    	} catch (SecurityException
				| IllegalArgumentException e) {
			e.printStackTrace();
		}
    	
    	// Method found -- invoke on heroInst and apply to privateHero
    	if (hasMethod){
    		Method methodToCall = null;
    		
			try {
				methodToCall = hero.getDeclaredMethod("getTexture");
				BufferedImage img = (BufferedImage) methodToCall.invoke(heroInst);
				privateHero.texture = img;
			} 
			
			catch (NoSuchMethodException | SecurityException 
					| IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			} 
    	}
	}

    private void lab2Update() {
    	UpdateManager.getInstance().update();
        CollisionManager.getInstance().update();
        StudentObjectManager.update();
        
        try {
        	// Get "setCenter"
        	hero.getDeclaredMethod("setCenter", float.class, float.class).invoke(heroInst, mouse.getWorldX(), mouse.getWorldY());
        	privateHero.center = (Vector2) hero.getDeclaredMethod("getCenter").invoke(heroInst);
        	
            // Hero shoot
            if ((keyboard.isButtonDown(KeyEvent.VK_F))
                    || (mouse.isButtonDown(1))) {
            	privateHero.currentWeapon.fire();
            }
			
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException 
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
    }

    
    /**
     * Lab 1
     * 
     * Lab that validates the class has the listed methods
     * 
     */
    private void oldinitializeLab3(){
    	// Request the class name
    	className = JOptionPane.showInputDialog("Please type in your class name");
    	
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
    
    private void initializeLab3(){
    	// Input Lab3Incorrect, and verify against Lab3Correct
		cf = new ClassReflector(className);		// should be Lab3Incorrect	
		ClassReflector cf2 = new ClassReflector("Lab3Correct");

		// Validate the class
		cf.verifyFields(cf2.getArrayOfFields());
		cf.verifyMethods(cf2.getArrayOfMethods());
		cf.verifyConstructors(cf2.getArrayOfConstructors());
    }

    private void initializeLab4() {
    	cf = new ClassReflector(className);		// should be Lab4Incorrect
		ClassReflector cf2 = new ClassReflector("Lab4Correct");
		
		// Check to see if the reflected class extends Debris
		if (!Debris.class.isAssignableFrom(cf.getReflectedClass())){
			System.out.println(className + " does not extend Debris");
		} else {
			// Validate the class
			cf.verifyFields(cf2.getArrayOfFields());
			cf.verifyMethods(cf2.getArrayOfMethods());
			cf.verifyConstructors(cf2.getArrayOfConstructors());
		}
	}
    
	private void lab4Update() {
		Lab4DebrisGenerator.getInstance().update();
		UpdateManager.getInstance().update();
        CollisionManager.getInstance().update();
	}

    private void initializeLab5() {
		// TODO Auto-generated method stub
    	cf = new ClassReflector(className);
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