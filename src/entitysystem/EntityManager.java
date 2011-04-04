package entitysystem;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

/**
 * Standard design: c.f. http://entity-systems.wikidot.com/rdbms-with-code-in-systems
 * 
 * Modified in java to use Generics: instead of having a "ComponentType" enum, we use the class type
 * of each subclass instead. This is safer.
 */

public class EntityManager {

    /* Set of all entities which are alive */
    HashSet<UUID> allEntities = new HashSet<UUID>();

    /*
     * Maps a type of component -> (Entity Identifier, Component of type requested).
     * For example, Position.class -> ( Foo, Foo's Position )
     */
    HashMap<Class<? extends Component>, HashMap<UUID, ? extends Component>> componentStores = new HashMap<Class<? extends Component>, HashMap<UUID, ? extends Component>>();

    public EntityManager() {
    }

    @SuppressWarnings("unchecked")
    public <T extends Component> void addComponent(final UUID entity, final T component) {
        HashMap<UUID, ? extends Component> store = componentStores.get(component.getClass());

        if (store == null) {
            store = new HashMap<UUID, T>();
            componentStores.put(component.getClass(), store);
        }

        ((HashMap<UUID, T>) store).put(entity, component);
    }

    public UUID createEntity() {
        final UUID uuid = UUID.randomUUID();
        allEntities.add(uuid);
        return uuid;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <T extends Component> List<T> getAllComponentsOfType(final Class<T> componentType) {
        final HashMap<UUID, ? extends Component> store = componentStores.get(componentType);

        if (store == null) {
            return new LinkedList<T>();
        }

        return new LinkedList(store.values());
    }

    public <T extends Component> Set<UUID> getAllEntitiesPossessingComponent(final Class<T> componentType) {
        final HashMap<UUID, ? extends Component> store = componentStores.get(componentType);

        if (store == null) {
            return new HashSet<UUID>();
        }

        return store.keySet();
    }

    @SuppressWarnings("unchecked")
    public <T extends Component> T getComponent(final UUID entity, final Class<T> componentType) {
        final HashMap<UUID, ? extends Component> store = componentStores.get(componentType);

        if (store == null) {
            throw new IllegalArgumentException("GET FAIL: there are no entities with a Component of class: " + componentType);
        }

        final T result = (T) store.get(entity);
        if (result == null) {
            throw new IllegalArgumentException("GET FAIL: " + entity + " does not possess Component of class\n   missing: " + componentType);
        }

        return result;
    }

    public void killEntity(final UUID entity) {
        /* Invalid kills are likely indicative of a coding error elsewhere. */
        assert allEntities.contains(entity);

        allEntities.remove(entity);
        removeAllComponentsForEntity(entity);
    }

    /*
     * Beware, this a very expensive operation - O(n) for component types, and O(n) for
     * alive components.
     */
    public void removeAllComponentsForEntity(final UUID entity) {

        final HashSet<Entry<UUID, ? extends Component>> forRemoval = new HashSet<Entry<UUID, ? extends Component>>();

        for (final Entry<Class<? extends Component>, HashMap<UUID, ? extends Component>> componentTypeEntry : componentStores.entrySet()) {
            for (final Entry<UUID, ? extends Component> component : componentTypeEntry.getValue().entrySet()) {
                if (component.getKey() == entity) {
                    forRemoval.add(component);
                }
            }
            componentTypeEntry.getValue().entrySet().removeAll(forRemoval);
            forRemoval.clear();
        }

    }
}