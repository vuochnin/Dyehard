package dyehard.Ui;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Engine.BaseCode;
import Engine.Vector2;
import dyehard.Ui.Buttons.Button;
import dyehard.Ui.Buttons.QuitButton;
import dyehard.Ui.Buttons.RestartButton;

// TODO: Auto-generated Javadoc
/**
 * The Class DyehardEndMenu.
 */
public class DyehardEndMenu {
	
	/** The menu select. */
	private final ClickableMenuLayer menuSelect;
	
	/** The menu hud. */
	private final MenuHud menuHud;

	/** The restart select. */
	// positions for selection texture
	private final Vector2 restartSelect = new Vector2(40.85f, 29.4f);
	
	/** The quit select. */
	private final Vector2 quitSelect = new Vector2(40.85f, 20.755f);
	
	/** The buttons. */
	private ArrayList<Button> buttons = new ArrayList<Button>();
	
	/** The my restart button. */
	private Button myRestartButton;
	
	/** The my quit button. */
	private Button myQuitButton;
	
	/** The size. */
	// Values to create the MenuHud Object
	private Vector2 size = new Vector2(34f, 26.9f);
	
	/** The center. */
	private Vector2 center = new Vector2(BaseCode.world.getWidth() / 2,
			BaseCode.world.getHeight() / 2);
	
	/** The texture. */
	private BufferedImage texture = BaseCode.resources
			.loadImage("Textures/UI/Win_Menu.png");
	
	/** The always on top. */
	private boolean alwaysOnTop = true;
	
	/** The visible. */
	private boolean visible = false;
	

	/**
	 * Instantiates a new dyehard end menu.
	 */
	public DyehardEndMenu() {
		
		menuSelect = new ClickableMenuLayer(quitSelect);
		
		myRestartButton = new RestartButton(38f, 61.875f, 29.75f, 34.125f, menuSelect, restartSelect);
		myQuitButton = new QuitButton(38f, 61.875f, 21.125f, 25.5f, menuSelect, quitSelect);
		
		buttons.add(myRestartButton);
		buttons.add(myQuitButton);
		
		menuHud = new MenuHud(size, center, texture, alwaysOnTop, visible);
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
		menuSelect.active(active);
	}

	/**
	 * Select.
	 *
	 * @param mouseX the mouse x
	 * @param mouseY the mouse y
	 * @param click the click
	 */
	public void select(float mouseX, float mouseY, boolean click) {
		
		for(int i = 0; i < buttons.size(); i++) {
			Button target = buttons.get(i);
			
			if(target.wasClicked(mouseX, mouseY)) {
				target.menuSelect();
				if(click) {
					buttons.get(i).doClickAction();	
				}
			}
		}		
	}

	/**
	 * Sets the menu.
	 *
	 * @param win the new menu
	 */
	public void setMenu(boolean win) {
		if (win) {
			menuHud.texture = BaseCode.resources
					.loadImage("Textures/UI/Win_Menu.png");
		} else {
			menuHud.texture = BaseCode.resources
					.loadImage("Textures/UI/Lose_Menu.png");
		}
	}
}