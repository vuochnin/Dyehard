package dyehard.Resources;

import java.util.HashMap;
import java.util.Map;

import dyehard.Enums.HeroID;

// TODO: Auto-generated Javadoc
/**
 * The Class HeroParser.
 */
public class HeroParser implements CsvParser {
	
    /** The hero. */
    private Map<HeroID, Float> hero = new HashMap<HeroID, Float>();
    
    /* (non-Javadoc)
     * @see dyehard.Resources.CsvParser#parseData(java.lang.String)
     */
    @Override
    public void parseData(String line) {
        String[] data = line.split(",");

        HeroID id = HeroID.valueOf(data[0]);
        float value = Float.valueOf(data[1]);

        hero.put(id, value);
    }
    
    /**
     * Gets the hero data.
     *
     * @param id the id
     * @return the hero data
     */
    public float getHeroData(HeroID id) {
    	return hero.get(id);
    }
}
