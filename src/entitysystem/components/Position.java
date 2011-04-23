package entitysystem.components;

import entitysystem.Component;

/**
 * Example of a single component - a 2D position component with an x, y, and z coordinates
 * 
 * Note: all fields should be public, so you can direct-access them!
 * 
 * c.f. http://entity-systems.wikidot.com/rdbms-with-code-in-systems
 */
public class Position implements Component {
    public float x, y, z;

    public Position() {
        this(0.0f, 0.0f, 0.0f);
    }

    public Position(final float x, final float y) {
        this(x, y, 0.0f);
    }

    public Position(final float x, final float y, final float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + "," + z + ")";
    }
}