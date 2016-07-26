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
 */
public class DyehardUI extends UpdateableObject {
	
	private boolean livesIsSet = false;
	private boolean displayScore = false;
	private boolean displayDistanceMeter = false;
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

//        hud = DyeHardResources.getInstance().getScaledRectangle(ImageID.UI_HUD);
//        hud.center.setX(BaseCode.world.getWidth() / 2);
//        hud.center.setY(fromTop(hud, 0f));
//        hud.alwaysOnTop = true;
        
        
//        Rectangle baseHeart = DyeHardResources.getInstance().getScaledRectangle(ImageID.UI_HEART);
//        baseHeart.alwaysOnTop = true;
//        hearts = new ArrayList<Rectangle>();
//        for (int i = 0; i < 10; ++i) {
//            Rectangle heart = new Rectangle(baseHeart);
//            float width = heart.size.getX();
//
//            // TODO magic numbers
//            heart.center = new Vector2(BaseCode.world.getWidth() - i * 1.62f
//                    * width - 4f, BaseCode.world.getHeight() - width / 2 - 1.4f);
//            hearts.add(heart);
//        }
//
//        baseHeart.visible = false;
        //distanceMeter = new DyehardDistanceMeter(GameState.TargetDistance);

        // TODO magic numbers
        scoreText = new Text("", 4f, BaseCode.world.getHeight() - 3.25f);
        scoreText.setFrontColor(Color.white);
        scoreText.setBackColor(Color.black);
        scoreText.setFontSize(18);
        scoreText.setFontName("Arial");
    }
    
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
    
    
    public void displayScore(boolean display){
    	displayScore = display;
    }
    
    public void displayDistanceMeter(boolean display, int tartgetDistance){
    	displayDistanceMeter = display;
    	if(display)
    		distanceMeter = new DyehardDistanceMeter(tartgetDistance, 55f);
    }
    
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
    public void update() {
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
