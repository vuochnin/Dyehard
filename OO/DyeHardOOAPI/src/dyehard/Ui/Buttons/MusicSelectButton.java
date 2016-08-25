/** File:		MusicSelectButton
 * Description:	Subclass of button used to toggle the music
 * 				on and off.  Has an extra ClickableMenuLayer
 * 				that brings up the music on and off buttons
 * Author:		Paul Kessler
 * Date:		2/14/2016
 */

package dyehard.Ui.Buttons;

import Engine.Vector2;
import dyehard.Ui.ClickableMenuLayer;
import dyehard.Util.DyeHardSound;

// TODO: Auto-generated Javadoc
/**
 * The Class MusicSelectButton.
 */
public class MusicSelectButton extends Button {
	
	/** The music tog. */
	private ClickableMenuLayer musicTog;
	
	/** The music on. */
	private Vector2 musicOn;
	
	/** The music off. */
	private Vector2 musicOff;
	
	/** The music. */
	private boolean music;

	/**
	 * @param x1 is the new topLeftX
	 * @param x2 is the new bottomRightX
	 * @param y1 is the new topLeftY
	 * @param y2 is the new bottomRightY
	 * @param menuSelect move the menu to the selected button that is passed in as the selector
	 * @param musicTog move the menu to the music toggle menu
	 * @param musicSelect selects the music on or off
	 */
	public MusicSelectButton(float x1, float x2, float y1, float y2, ClickableMenuLayer menuSelect, 
			ClickableMenuLayer musicTog ,Vector2 musicSelect) {
		
		super(x1, x2, y1, y2, menuSelect, musicSelect);
		setMusicTog(musicTog);
		setMusicOn(new Vector2(46.328f, 27.345f));
		setMusicOff(new Vector2(56.614f, 27.345f));
		setMusic(true);
	}

	/**
	 * - Moves the menu to the musicOn or MusicOff button
	 */
	@Override
	public void doClickAction() {
		setMusic(!isMusic());
		DyeHardSound.setMusic(isMusic());
		if(isMusic()) {
			DyeHardSound.playBgMusic();
			getMusicTog().move(getMusicOn());
		} else {
			DyeHardSound.stopBgMusic();
			getMusicTog().move(getMusicOff());
		}

	}

	/**
	 * @return
	 */
	public ClickableMenuLayer getMusicTog() {
		return musicTog;
	}

	/**
	 * @param musicTog
	 */
	public void setMusicTog(ClickableMenuLayer musicTog) {
		this.musicTog = musicTog;
	}

	/**
	 * @return
	 */
	public Vector2 getMusicOn() {
		return musicOn;
	}

	/**
	 * @param musicOn
	 */
	public void setMusicOn(Vector2 musicOn) {
		this.musicOn = musicOn;
	}

	/**
	 * @return
	 */
	public Vector2 getMusicOff() {
		return musicOff;
	}

	/**
	 * @param musicOff
	 */
	public void setMusicOff(Vector2 musicOff) {
		this.musicOff = musicOff;
	}

	/**
	 * @return
	 */
	public boolean isMusic() {
		return music;
	}

	/**
	 * @param music
	 */
	public void setMusic(boolean music) {
		this.music = music;
	}
}
