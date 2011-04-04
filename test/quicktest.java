import java.util.List;

import entitysystem.EntityManager;
import entitysystem.components.Position;

/**
 * Very brief test to check that the ES implementation is compiling and running OK
 * 
 * Run from the command line - no arguments - and see if you get Exceptions / Errors.
 */
public class quicktest {

    public static void main(final String[] args) {
        final EntityManager em = new EntityManager();

        final int entity1 = em.createEntity();

        /* TODO : Refactor to JUnit. This works for now. */
        /* TODO : Unit test entire API */
        em.addComponent(entity1, new Position());
        em.getComponent(entity1, Position.class).x = 5;
        em.getComponent(entity1, Position.class).y = 10;

        final List<Position> posList = em.getAllComponentsOfType(Position.class);
        assert posList.size() == 1;
        assert posList.get(0).x == 5;
        assert posList.get(0).y == 10;
        assert em.getComponent(entity1, Position.class).x == 5;
        assert em.getComponent(entity1, Position.class).y == 10;

        System.out.println("Value of Position component is: " + em.getComponent(entity1, Position.class));
    }
}
