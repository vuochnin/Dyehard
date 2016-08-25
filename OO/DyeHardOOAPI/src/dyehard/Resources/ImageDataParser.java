package dyehard.Resources;

import java.util.HashMap;
import java.util.Map;

import Engine.Vector2;

// TODO: Auto-generated Javadoc
/**
 * The Class ImageDataParser.
 */
public class ImageDataParser implements CsvParser {
	
	/** The data. */
	//private ImageData data = new ImageData();
    
    /** The image data. */
    private Map<ImageID, ImageData> imageData = new HashMap<ImageID, ImageData>();
    
    /**
     * The Enum ImageID.
     */
    public enum ImageID {
        
        /** The ui hud. */
        UI_HUD, 
        
        /** The ui path. */
        UI_PATH, 
        
        /** The ui path marker. */
        UI_PATH_MARKER, 
        
        /** The ui dye path marker. */
        UI_DYE_PATH_MARKER, 
        
        /** The ui path marker full. */
        UI_PATH_MARKER_FULL, 
        
        /** The ui heart. */
        UI_HEART
    }
    
	/* (non-Javadoc)
	 * @see dyehard.Resources.CsvParser#parseData(java.lang.String)
	 */
	@Override
    public void parseData(String line) {
        String[] data = line.split(",");

        ImageID image = ImageID.valueOf(data[0]);
        int screenWidth = Integer.valueOf(data[1]);
        int screenHeight = Integer.valueOf(data[2]);
        int actualWidth = Integer.valueOf(data[3]);
        int actualHeight = Integer.valueOf(data[4]);
        String path = data[5];

        addData(image, path, new Vector2(actualWidth, actualHeight),
                new Vector2(screenWidth, screenHeight));
    }
	
    /**
     * Adds the data.
     *
     * @param image the image
     * @param path the path
     * @param actualSize the actual size
     * @param targetSize the target size
     */
    public void addData(ImageID image, String path, Vector2 actualSize,
            Vector2 targetSize) {
        ImageData data = new ImageData();

        data.setTexturePath(path);
        data.setActualPixelSize(actualSize);
        data.setTargetedPixelSize(targetSize);
        imageData.put(image, data);
    }
    
    /**
     * Gets the image data.
     *
     * @param image the image
     * @return the image data
     */
    public ImageData getImageData(ImageID image) {
    	return imageData.get(image);
    }
}
