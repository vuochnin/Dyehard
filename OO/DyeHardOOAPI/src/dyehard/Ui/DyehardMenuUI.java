package dyehard.Ui;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Engine.BaseCode;
import Engine.Vector2;
import dyehard.Ui.Buttons.Button;
import dyehard.Ui.Buttons.CreditButton;
import dyehard.Ui.Buttons.MusicOffButton;
import dyehard.Ui.Buttons.MusicOnButton;
import dyehard.Ui.Buttons.MusicSelectButton;
import dyehard.Ui.Buttons.QuitButton;
import dyehard.Ui.Buttons.RestartButton;
import dyehard.Ui.Buttons.SoundOffButton;
import dyehard.Ui.Buttons.SoundOnButton;
import dyehard.Ui.Buttons.SoundSelectButton;
 
// TODO: Auto-generated Javadoc
/**
 * The Class DyehardMenuUI.
 */
public class DyehardMenuUI {
	
    /** The menu select. */
    private final ClickableMenuLayer menuSelect;
    
    /** The sound tog. */
    private final ClickableMenuLayer soundTog;
    
    /** The music tog. */
    private final ClickableMenuLayer musicTog;
    
    /** The menu hud. */
    private final MenuHud menuHud;
    
    /** The size. */
    //values used to create the MenuHud object
    private Vector2 size = new Vector2(40f, 40f);
    
    /** The center. */
    private Vector2 center = new Vector2(BaseCode.world.getWidth() / 2,
    								BaseCode.world.getHeight() / 2);
    
    /** The texture. */
    private BufferedImage texture = BaseCode.resources
    								.loadImage("Textures/UI/UI_Menu.png");
    
    /** The always on top. */
    private boolean alwaysOnTop = true;
    
    /** The visible. */
    private boolean visible = false;
   
    /** The sound on. */
    // positions for selection texture
    private final Vector2 soundOn = new Vector2(46.328f, 36.111f);
    
    /** The music on. */
    private final Vector2 musicOn = new Vector2(46.328f, 27.345f);
    
    /** The restart select. */
    private final Vector2 restartSelect = new Vector2(38.383f, 42.506f);
    
    /** The quit select. */
    private final Vector2 quitSelect = new Vector2(38.383f, 12.722f);
    
    /** The sound select. */
    private final Vector2 soundSelect = new Vector2(38.383f, 36.111f);
    
    /** The music select. */
    private final Vector2 musicSelect = new Vector2(38.383f, 27.345f);
    
    /** The buttons. */
    private ArrayList<Button> buttons = new ArrayList<Button>();
    
    /** The my restart button. */
    private RestartButton myRestartButton;
    
    /** The my quit button. */
    private QuitButton myQuitButton;
    
    /** The my sound select button. */
    private SoundSelectButton mySoundSelectButton;
    
    /** The my music select button. */
    private MusicSelectButton myMusicSelectButton;
    
    /** The my credit button. */
    private CreditButton myCreditButton;
    
    /** The my sound on button. */
    private SoundOnButton mySoundOnButton;
    
    /** The my sound off button. */
    private SoundOffButton mySoundOffButton;
    
    /** The my music on button. */
    private MusicOnButton myMusicOnButton;
    
    /** The my music off button. */
    private MusicOffButton myMusicOffButton;
    
    
    /**
     * Instantiates a new dyehard menu ui.
     */
    public DyehardMenuUI() {
    	
    	menuSelect = new ClickableMenuLayer(restartSelect);
        soundTog = new ClickableMenuLayer(soundOn);
        musicTog = new ClickableMenuLayer(musicOn);
    	
    	myRestartButton = new RestartButton(35.45f, 59.68f, 42.75f, 47.125f, menuSelect, restartSelect);
    	myQuitButton = new QuitButton(35.45f, 59.68f, 13.125f, 17.5f, menuSelect, quitSelect);
    	mySoundSelectButton = new SoundSelectButton(35.45f, 59.68f, 36.125f, 40.75f, menuSelect, soundTog, soundSelect);
    	myMusicSelectButton = new MusicSelectButton(35.45f, 59.68f, 27.625f , 32.125f, menuSelect, musicTog, musicSelect);
    	myCreditButton = new CreditButton(35.45f, 59.68f, 19.25f, 23.75f, menuSelect);
    	mySoundOnButton = new SoundOnButton(49.25f, 54.25f, 33.375f, 35.625f, menuSelect, soundTog, soundSelect);
    	mySoundOffButton = new SoundOffButton(59.5f, 65.625f, 33.375f, 35.625f, menuSelect, soundTog, soundSelect);
    	myMusicOnButton = new MusicOnButton(49.25f, 54.25f, 24.75f, 26.875f, menuSelect, musicTog, musicSelect);
    	myMusicOffButton = new MusicOffButton(59.5f, 65.625f, 24.75f, 26.875f, menuSelect,musicTog, musicSelect);

    	menuHud = new MenuHud(size, center, texture, alwaysOnTop, visible);
       
        //prep buttons
        buttons.add(myRestartButton);
        buttons.add(mySoundSelectButton);
        buttons.add(myMusicSelectButton);
        buttons.add(myCreditButton);
        buttons.add(myQuitButton);
        buttons.add(mySoundOnButton);
        buttons.add(mySoundOffButton);
        buttons.add(myMusicOnButton);
        buttons.add(myMusicOffButton);        
    }

    /**
     * Active.
     *
     * @param active the active
     */
    public void active(boolean active) {
        if (active) {
            BaseCode.resources.moveToFrontOfDrawSet(menuHud);
        }
        menuHud.visible = active;
        soundTog.active(active);
        musicTog.active(active);
        menuSelect.active(active);
    }

    /**
     * Select.
     *
     * @param mouseX the mouse x pos
     * @param mouseY the mouse y pos
     * @param click the click
     */
    public void select(float mouseX, float mouseY, boolean click) {
    	
    	for(int i = 0; i < buttons.size(); i++) {
    		Button target = buttons.get(i);
    		
    		if(target.wasClicked(mouseX, mouseY)) {
    			target.menuSelect(); // Toggles to the selected button
    			if(click) {
    				target.doClickAction();
    			}
    		}
    	}  	
    }

    /**
     * Credits off.
     */
    public void creditsOff() {
        if (myCreditButton.isShown()) {
            myCreditButton.creditsOff();
        }
    }

    /**
     * Checks if is credits.
     *
     * @return true, if is credits
     */
    public boolean isCredits() {
        return myCreditButton.isShown();
    }
}
