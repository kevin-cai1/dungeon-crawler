package ass2;

/**
 * base class for obstacles which are entities that cannot be passed through
 * e.g. closed doors, boulders, walls
 * @author gordon
 *
 */
public abstract class Obstacle extends Entity{
    Obstacle(int id){
        super(id);
    }

}
