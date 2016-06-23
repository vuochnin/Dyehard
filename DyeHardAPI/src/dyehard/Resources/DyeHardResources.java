package dyehard.Resources;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import Engine.BaseCode;
import Engine.MessageOnce;
import Engine.Rectangle;
import Engine.Vector2;
import dyehard.DyehardRectangle;
import dyehard.Enums.HeroID;
import dyehard.Enums.PowerupID;
import dyehard.Resources.ImageDataParser.ImageID;

// TODO: Auto-generated Javadoc
/**
 * The Class DyeHardResources.
 */
public class DyeHardResources {
	
	/** The single instance. */
	//Singleton
	private static DyeHardResources singleInstance = null;
	// creates instance of DyeHardResources before anything else is spun up
	static {
			singleInstance = new DyeHardResources();
	}

	/** The powerups. */
	//actual instance variables
	private PowerupParser powerups = new PowerupParser();	
	
	/** The hero. */
	private HeroParser hero = new HeroParser();	
	
	/** The image data. */
	private ImageDataParser imageData = new ImageDataParser();
	
	/**
	 * Instantiates a new dye hard resources.
	 */
	private DyeHardResources() {
		loadFromFile("Textures/ImageData.csv", imageData);
        loadFromFile("PowerupData.csv", powerups);
        loadFromFile("HeroData.csv", hero);
	}
	
	/**
	 * Gets the single instance of DyeHardResources.
	 *
	 * @return single instance of DyeHardResources
	 */
	// later consider monitors to synchronize if multi-threaded(avoid double lock issue
	public static DyeHardResources getInstance() {
		return singleInstance;
	}
		    

    /**
     * Load external file.
     *
     * @param path of the file to load
     * @return the input stream
     */
    private InputStream loadExternalFile(String path) {
        String basePath = BaseCode.resources.basePath;
        URL url;

        try {
            url = new URL(basePath + path);
            URLConnection in = url.openConnection();
            if (in.getContentLengthLong() > 0) {
                return url.openStream();
            }
        } catch (MalformedURLException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Load from file.
     *
     * @param csvPath the path to load
     * @param parser the Csvparser interface
     */
    public void loadFromFile(String csvPath, CsvParser parser) {
        InputStream input = null;

        String[] filePaths = { "resources/" + csvPath,
                "bin/resources/" + csvPath, };

        for (String path : filePaths) {
            if (input == null) {
                input = loadExternalFile(path);
            }
        }

        for (String path : filePaths) {
            if (input == null) {
                input = DyeHardResources.class.getClassLoader().getResourceAsStream(path);
            }
        }

        if (input == null) {
            MessageOnce.showAlert("Error reading configuration files.");
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(input));
        String line;

        try {

            br = new BufferedReader(new InputStreamReader(input));
            line = br.readLine(); // discard table headers
            while ((line = br.readLine()) != null) {
                parser.parseData(line);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Gets the powerup data.
     *
     * @param id the id
     * @return the powerup data
     */
    public PowerupData getPowerupData(PowerupID id) {
        return powerups.getPowerupData(id);
    }

    /**
     * Gets the hero data.
     *
     * @param id the id
     * @return the hero data
     */
    public float getHeroData(HeroID id) {
        return hero.getHeroData(id);
    }

    /**
     * Gets the texture.
     *
     * @param image the image
     * @return the texture
     */
    public BufferedImage getTexture(ImageID image) {
        ImageData data = imageData.getImageData(image);
        if (data == null) {
            return null;
        }

        if (data.getTexture() == null) {
            data.setTexture(BaseCode.resources.loadImage(data.getTexturePath()));
        }

        return data.getTexture();
    }

    /**
     * Gets the scaled rectangle.
     *
     * @param image the image
     * @return the scaled rectangle
     */
    public Rectangle getScaledRectangle(ImageID image) {
        ImageData data = imageData.getImageData(image);
        if (data == null) {
            return null;
        }

        if (data.getTexture() == null) {
            data.setTexture(BaseCode.resources.loadImage(data.getTexturePath()));
        }

        Rectangle rect = new Rectangle();
        rect.texture = data.getTexture();
        rect.size = scaleToGameWorld(data.getTargetedPixelSize(),
                data.getActualPixelSize());
        return rect;
    }

    /**
     * Gets the scaled rectangle.
     *
     * @param screenSize the screen size
     * @param rectSize the rect size
     * @param texturePath the texture path
     * @return the scaled rectangle
     */
    public Rectangle getScaledRectangle(Vector2 screenSize,
            Vector2 rectSize, String texturePath) {
        Rectangle rect = new Rectangle();
        rect.texture = BaseCode.resources.loadImage(texturePath);
        rect.size = scaleToGameWorld(screenSize, rectSize);
        return rect;
    }

    // this is an old comment to be removed - Paul 2/7/2016
    /**
     * Gets the scaled animation.
     *
     * @param screenSize the screen size
     * @param frameSize the frame size
     * @param numFrames the num frames
     * @param tpf the tpf
     * @param texturePath the texture path
     * @return the scaled animation
     */
    // TODO fix this huge function
    public DyehardRectangle getScaledAnimation(Vector2 screenSize,
            Vector2 frameSize, int numFrames, int tpf, String texturePath) {
        DyehardRectangle rect = new DyehardRectangle();
        rect.texture = BaseCode.resources.loadImage(texturePath);
        rect.size = scaleToGameWorld(screenSize, frameSize);
        rect.setUsingSpriteSheet(true);

        int width = (int) frameSize.getX();
        int height = (int) frameSize.getY();
        rect.setSpriteSheet(rect.texture, width, height, numFrames, tpf);
        return rect;
    }

    /**
     * Converts an images pixel size to game world size Eg. An image with a
     * width of 1920 on a screen size of 1920 will have a width equal to 100% of
     * the game world.
     *
     * @param screen            The expected size of the window in pixels
     * @param size            The size of the graphic in pixels
     * @return The size of the graphic in game world units
     */
    public Vector2 scaleToGameWorld(Vector2 screen, Vector2 size) {
        float widthRatio = screen.getX() / BaseCode.world.getWidth();
        float heightRatio = screen.getY() / BaseCode.world.getHeight();

        Vector2 scaledSize = new Vector2();
        scaledSize.setX(size.getX() / widthRatio);
        scaledSize.setY(size.getY() / heightRatio);

        return scaledSize;
    }
}
