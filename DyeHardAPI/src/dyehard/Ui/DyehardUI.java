package dyehard.Ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import Engine.BaseCode;
import Engine.Rectangle;
import Engine.Text;
import Engine.Vector2;
import dyehard.Enums.ManagerStateEnum;
import dyehard.DyehardDistanceMeter;
import dyehard.UpdateableObject;
import dyehard.Player.DyeMeter;
import dyehard.Player.Hero;
import dyehard.Resources.DyeHardResources;
import dyehard.Resources.ImageDataParser.ImageID;
import dyehard.World.GameState;

// TODO: Auto-generated Javadoc
/**
 * The Class DyehardUI.
 * 
 * @author Modified by: Nin
 */
public class DyehardUI extends UpdateableObject {
	// ProAPI: To check if the custom number of lives has set to display and update based on that
	private boolean livesIsSet = false;
	// ProAPI: To check if the user asked to display the score, then update based on that
	private boolean displayScore = false;
	// ProAPI: To check if the user asked to display the distance meter UI, then update based on that
	private boolean displayDistanceMeter = false;
	// ProAPI: To check if the default setting is being used
	private boolean uiDefaultSetting = false;
    
    /** The hero. */
    protected Hero hero;
    
    /** The hud. */
    Rectangle hud;
    
    /** The distance meter. */
    DyehardDistanceMeter distanceMeter;
    
    /** The score text. */
    Text scoreText;
    
    /** The hearts. */
    List<Rectangle> hearts = new ArrayList<Rectangle>();

    /**
     * Instantiates a new dyehard ui.
     *
     * @param hero the hero
     */
    public DyehardUI(Hero hero) {
        this.hero = hero;
        new DyeMeter(hero);

        // TODO magic numbers
        scoreText = new Text("", 4f, BaseCode.world.getHeight() - 3.25f);
        scoreText.setFrontColor(Color.white);
        scoreText.setBackColor(Color.black);
        scoreText.setFontSize(18);
        scoreText.setFontName("Arial");
    }
    
    /**
     * Sets DyehardUI to default setting, default UI layout
     * Note: This method is added by Nin, for the purpose of making the Score, 
     * Heart bar, and Distance meter UI to be customizable or accessible separately 
     * in the Procedural API
     */
    public void useUIdefaultSetting(){
      uiDefaultSetting = true;
	  hud = DyeHardResources.getInstance().getScaledRectangle(ImageID.UI_HUD);
	  hud.center.setX(BaseCode.world.getWidth() / 2);
	  hud.center.setY(fromTop(hud, 0f));
	  hud.alwaysOnTop = true;
	  
	  Rectangle baseHeart = DyeHardResources.getInstance().getScaledRectangle(ImageID.UI_HEART);
	  baseHeart.alwaysOnTop = true;
	  hearts = new ArrayList<Rectangle>();
	  for (int i = 0; i < 4; ++i) {
	      Rectangle heart = new Rectangle(baseHeart);
	      float width = heart.size.getX();
	
	      // TODO magic numbers
	      heart.center = new Vector2(BaseCode.world.getWidth() - i * 1.62f
	              * width - 4f, BaseCode.world.getHeight() - width / 2 - 1.4f);
	      hearts.add(heart);
	  }
	
	  baseHeart.visible = false;
	  distanceMeter = new DyehardDistanceMeter(GameState.TargetDistance);
    }
    
    /**
     * Displays the score on the game window (For Procedural API)
     * @param display true/false
     */
    public void displayScore(boolean display){
    	displayScore = display;
    }
    
    /**
     * Displays the distance meter on the game window (For Procedural API)
     * @param tartgetDistance the length of the total distance that 
     * 	the user set for the game
     */
    public void displayDistanceMeter(int tartgetDistance){
    	displayDistanceMeter = true;
    	distanceMeter = new DyehardDistanceMeter(tartgetDistance, 55f);
    }
    
    /**
     * Sets the number of lives the game initially have (For Procedural API)
     * @param lives the number of lives
     */
    public void setRemainingLives(int lives){
    	livesIsSet = true;
        Rectangle baseHeart = DyeHardResources.getInstance().getScaledRectangle(ImageID.UI_HEART);
        baseHeart.alwaysOnTop = true;
        hearts = new ArrayList<Rectangle>();
        for (int i = 0; i < lives; ++i) {
            Rectangle heart = new Rectangle(baseHeart);
            float width = heart.size.getX();

            // TODO magic numbers
            heart.center = new Vector2(BaseCode.world.getWidth() - i * 1.62f
                    * width - 4f, BaseCode.world.getHeight() - width / 2 - 1.4f);
            hearts.add(heart);
        }

        baseHeart.visible = false;
        GameState.RemainingLives = lives;
    }

    /**
     * From top.
     *
     * @param image the image
     * @param padding the padding
     * @return the float
     */
    protected float fromTop(Rectangle image, float padding) {
        return BaseCode.world.getHeight() - image.size.getY() / 2f - padding;
    }

    /* (non-Javadoc)
     * @see dyehard.UpdateableObject#updateState()
     */
    @Override
    public ManagerStateEnum updateState() {
        return ManagerStateEnum.ACTIVE;
    }

    /* (non-Javadoc)
     * @see dyehard.UpdateableObject#update()
     */
    @Override
    public void update() {					// Modified for Procedural API (also work as default)
        for (Rectangle r : hearts) {
            r.visible = false;
        }
        if(livesIsSet || uiDefaultSetting){
	        int numHearts = hearts.size() - 1;
	        for (int i = 0; i < GameState.RemainingLives; ++i) {
	        	hearts.get(numHearts - i).visible = true;
	        }
        }
        if(displayScore || uiDefaultSetting){
        	scoreText.setText(Integer.toString(GameState.Score));
        }
        
        if(displayDistanceMeter || uiDefaultSetting){
	        distanceMeter.setValue(GameState.DistanceTravelled);
	
	        // controls texture of the progress marker in UI Bar
	        switch (hero.curPowerUp) {
	        case GHOST:
	            distanceMeter.setProgTexture("UI/UI_Ghost");
	            break;
	        case INVIN:
	            distanceMeter.setProgTexture("UI/UI_Invincibility");
	            break;
	        case MAGNET:
	            distanceMeter.setProgTexture("UI/UI_Magnetism");
	            break;
	        case OVERLOAD:
	            distanceMeter.setProgTexture("UI/UI_Overload");
	            break;
	        case SLOW:
	            distanceMeter.setProgTexture("UI/UI_SlowDown");
	            break;
	        case SPEED:
	            distanceMeter.setProgTexture("UI/UI_SpeedUp");
	            break;
	        case UNARMED:
	            distanceMeter.setProgTexture("UI/UI_Unarmed");
	            break;
	        case REPEL:
	            distanceMeter.setProgTexture("UI/UI_Repel");
	            break;
	        case GRAVITY:
	            distanceMeter.setProgTexture("UI/Dyehard_UI_Progress_marker_empty");
	            break;
	        default:
	            distanceMeter.setProgTexture("UI/Dyehard_UI_Progress_marker_empty");
	            break;
	        }
        }
    }

    /**
     * Draw front.
     */
    public void drawFront() {
        BaseCode.resources.moveToFrontOfDrawSet(scoreText);
        distanceMeter.drawFront();
        for (Rectangle h : hearts) {
            BaseCode.resources.moveToFrontOfDrawSet(h);
        }
        if(uiDefaultSetting){
        	BaseCode.resources.moveToFrontOfDrawSet(hud);
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
