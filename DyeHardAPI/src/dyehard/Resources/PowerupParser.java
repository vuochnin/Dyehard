package dyehard.Resources;

import java.util.HashMap;
import java.util.Map;

import dyehard.Enums.PowerupID;

// TODO: Auto-generated Javadoc
/**
 * The Class PowerupParser.
 */
public class PowerupParser implements CsvParser {
	
	/** The powerup data. */
	//private PowerupData powerupData = new PowerupData();	
    
    /** The powerups. */
    private Map<PowerupID, PowerupData> powerups = new HashMap<PowerupID, PowerupData>();
        
	 /* (non-Javadoc)
 	 * @see dyehard.Resources.CsvParser#parseData(java.lang.String)
 	 */
 	@Override
     public void parseData(String line) {
         String[] data = line.split(",");

         PowerupID powerupId = PowerupID.valueOf(data[0]);
         float magnitude = Float.valueOf(data[1]);
         float duration = Float.valueOf(data[2]);

         PowerupData powerupData = new PowerupData();
         powerupData.setMagnitude(magnitude);
         powerupData.setDuration(duration);
         powerups.put(powerupId, powerupData);
     }
	 
	 /**
 	 * Adds the powerup data.
 	 
 	public void addPowerupData() {
		 
	 }
	 */
 	
	 /**
 	 * Gets the powerup data.
 	 *
 	 * @param id the id
 	 * @return the powerup data
 	 */
 	public PowerupData getPowerupData(PowerupID id) {
		 return powerups.get(id);
	 }
}
