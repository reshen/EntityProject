import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entitysystem.EntityManager;
import entitysystem.components.Position;

public class EntityManagerTest {

    EntityManager em = null;

    @Before
    public void setUp() throws Exception {
        em = new EntityManager();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testAddComponent() {
        final UUID entity1 = em.createEntity();

        final Position p = new Position();
        p.x = 5;
        p.y = 10;

        em.addComponent(entity1, p);

        assert em.getComponent(entity1, Position.class).x == 5;
        assert em.getComponent(entity1, Position.class).y == 10;
    }

    @Test
    public void testCreateEntity() {
        final UUID entity1 = em.createEntity();
        assert entity1 != null;
    }

    @Test
    public void testGetAllComponentsOfType() {
        final List<Position> emptyList = em.getAllComponentsOfType(Position.class);
        assert emptyList.size() == 0;

        final Position p = new Position();
        p.x = 5;
        p.y = 10;

        final UUID entity1 = em.createEntity();
        em.addComponent(entity1, p);

        final List<Position> posList = em.getAllComponentsOfType(Position.class);
        assert posList.size() == 1;
        assert posList.get(0).x == 5;
        assert posList.get(0).y == 10;
        assert em.getComponent(entity1, Position.class).x == 5;
        assert em.getComponent(entity1, Position.class).y == 10;
    }

    @Test
    public void testGetAllEntitiesPossessingComponent() {
        {
            final Set<UUID> s = em.getAllEntitiesPossessingComponent(Position.class);
            assert s.size() == 0;
        }

        final UUID entity1 = em.createEntity();
        {
            final Position p = new Position();
            p.x = 5;
            p.y = 10;

            em.addComponent(entity1, p);
        }

        final UUID entity2 = em.createEntity();
        {
            final Position p2 = new Position();
            p2.x = 15;
            p2.y = 110;

            em.addComponent(entity2, p2);
        }

        final Set<UUID> s = em.getAllEntitiesPossessingComponent(Position.class);
        assert s.size() == 2;
        assert s.contains(entity1);
        assert s.contains(entity2);
    }

    @Test(expected = AssertionError.class)
    public void testInvalidKillEntity() {
        em.killEntity(null);
    }

    @Test
    public void testKillEntity() {

        final UUID entity1 = em.createEntity();
        {
            final Position p = new Position();
            p.x = 5;
            p.y = 10;

            em.addComponent(entity1, p);
        }

        final UUID entity2 = em.createEntity();
        {
            final Position p2 = new Position();
            p2.x = 15;
            p2.y = 110;

            em.addComponent(entity2, p2);
        }

        {
            final Set<UUID> s = em.getAllEntitiesPossessingComponent(Position.class);
            assert s.size() == 2;
            assert s.contains(entity1);
            assert s.contains(entity2);
        }

        em.killEntity(entity1);

        {
            final Set<UUID> s = em.getAllEntitiesPossessingComponent(Position.class);
            assert s.size() == 1;
            assert s.contains(entity2);
        }
    }

}
