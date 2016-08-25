/*
 * 
 */
package dyehard.Util;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

// TODO: Auto-generated Javadoc
/**
 * The Class Colors.
 */
public class Colors {

    /** The color count. */
    public static int colorCount = 6;
    
    /** The Green. */
    public static Color Green = new Color(38, 153, 70);
    
    /** The Red. */
    public static Color Red = new Color(193, 24, 30);
    
    /** The Yellow. */
    public static Color Yellow = new Color(228, 225, 21);
    
    /** The Teal. */
    public static Color Teal = new Color(90, 184, 186);
    
    /** The Pink. */
    public static Color Pink = new Color(215, 59, 148);
    
    /** The Blue. */
    public static Color Blue = new Color(50, 75, 150);

    /**
     * Color picker.
     *
     * @param choice is the new color
     * @return the new color
     */
    public static Color colorPicker(int choice) {
        switch (choice) {
        case 0:
            return Green;
        case 1:
            return Red;
        case 2:
            return Yellow;
        case 3:
            return Teal;
        case 4:
            return Pink;
        case 5:
            return Blue;
        }
        return Color.black;
    }

    /**
     * Random color.
     *
     * @return the color
     */
    public static Color randomColor() {
        Random rand = new Random();
        return colorPicker(rand.nextInt(6));
    }

    /**
     * Random color set.
     *
     * @param count is the subset of random colors
     * @return the array list of random colors
     */
    public static ArrayList<Color> randomColorSet(int count) {
        // get a random and unique subset of the available colors
        ArrayList<Integer> range = new ArrayList<Integer>();
        for (int i = 0; i < Colors.colorCount; i++) {
            range.add(i);
        }
        Collections.shuffle(range);
        // get the colors from the indexes in the sample list
        ArrayList<Color> colors = new ArrayList<Color>();
        for (int i = 0; i < count; i++) {
            colors.add(colorPicker(range.get(i)));
        }
        return colors;
    }

    /**
     * Random unique color set.
     *
     * @param count is the subset of random unique colors
     * @param unavailableColors are unavailable colors
     * @return the array list of unique random colors
     */
    public static ArrayList<Color> randomUniqueColorSet(int count,
            ArrayList<Color> unavailableColors) {
        // get a random and unique subset of the available colors

        ArrayList<Integer> range = new ArrayList<Integer>();
        for (int i = 0; i < Colors.colorCount; i++) {
            range.add(i);
        }
        Collections.shuffle(range);

        // get the colors from the indexes in the sample list
        ArrayList<Color> colors = new ArrayList<Color>();
        colors.addAll(unavailableColors);

        int addCount = 0;
        for (int i = 0; i < range.size() - 1; i++) {
            if (addCount >= count) {
                break;
            }

            if (!colors.contains(colorPicker(range.get(i)))) {
                colors.add(colorPicker(range.get(i)));
                addCount++;
            }
        }

        return colors;
    }
}
