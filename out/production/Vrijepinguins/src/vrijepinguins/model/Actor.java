package vrijepinguins.model;

import java.util.List;

/**
 * Public interface vrijepinguins.model.Actor for Vossen & Konijnen
 * Contains a set of methods for all actors in the simulator
 * 
 * @author leonwetzel
 *
 */
public interface Actor {
    /**
     * general method act for all actors in the simulator
     */
	public void act(List<Actor> newActors);


    /**
     * return if the actor is alive
     * @return true or false
     */
	public boolean isAlive();

    /**
     * get a location
     * @return location
     */
	public Location getLocation();

    /**
     * get a field
     * @return field
     */
	public Field getField();
}
