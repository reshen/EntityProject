package entitysystem;

/**
 * Standard design: c.f. http://entity-systems.wikidot.com/rdbms-with-code-in-systems
 */

public interface SubSystem {
    public void processOneGameTick();
}