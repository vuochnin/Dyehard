/*
 * 
 */
package dyehard.Util;

import java.util.ArrayList;
import java.util.List;

import Engine.Primitive;

// TODO: Auto-generated Javadoc
/**
 * The Class PrimitiveSet.
 */
public class PrimitiveSet {
    
    /** The primitives. */
    protected List<Primitive> primitives;

    /**
     * Instantiates a new primitive set.
     */
    public PrimitiveSet() {
        primitives = new ArrayList<Primitive>();
    }

    /**
     * Adds the primitive.
     *
     * @param the primitive to add to the set
     */
    public void addPrimitive(Primitive primitive) {
        if (primitive != null) {
            primitives.add(primitive);
        }
    }

    /**
     * Destroy all primitives in the set.
     */
    public void destroyAll() {
        for (Primitive p : primitives) {
            p.destroy();
        }
    }
}
