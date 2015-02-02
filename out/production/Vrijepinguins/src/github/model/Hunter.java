package github.model;

import java.util.Iterator;
import java.util.List;

/**
 * 
 * Public class github.model.Hunter for Vossen & Konijnen
 * This hunter is a human, therefore he should not extend the superclass github.model.Animal.
 *
 * Hunters can't breed, nor can they die. Their only purpose is to kill all that
 * stands in their way, except other hunters.
 * 
 * @author leonwetzel
 */
public class Hunter implements Actor
{
	private Field field;
	private Location location;
	// hunters can't die, therefore the variable can be regarded as a constant
	private static final boolean ALIVE = true;
	private int hunger;

	/**
	 * Create a hunter
	 * @param field
	 * @param location
	 */
    public Hunter(Field field, Location location)
    {
        this.field = field;
        setLocation(location);
        hunger = 10;
    }	
	
    /**
     * Way of behavior for a hunter.
     * @param newHunters
     */
    public void act(List<Actor> hunter)
    {
    	if(hunger>0){
    		hunger--;
    	}else{
    		hunger = 0;
    	}
    	Area area = field.getSameLocation(location);
    	// Move towards a location where a target could be
        Location newLocation = findTargets();
        if(newLocation == null) { 
            if(hunger==0&&(area.getType()==2)){
	        	area.special(-10);
            }else if(hunger==0&&area.getType()==1){
            	area.special();
            	area.special();
            }
            // No target found - try to move to a free location.
            newLocation = getField().freeAdjacentLocation(getLocation());
            
        }else{
        	hunger=20;
        }
        // See if it was possible to move.
        if(newLocation != null){//)&&(hunger>0||!(area.getType()==1))) {
            setLocation(newLocation);
            
            walk();
        }
    }
    
    public void walk()
    {
    	Area area = getField().getSameLocation(getLocation());
    	area.walkedOn();
    }
    
    /**
     * Find targets
     * @return github.model.Location
     */
    private Location findTargets()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object actor = field.getObjectAt(where);
            if(actor instanceof Animal) {
                Animal animal = (Animal) actor;
                if(animal.isAlive()) { 
                	animal.setDead();
                    return where;
                }
            }
            // else: do nothing when a hunter meets another hunter
        }
        // No targets found? Carry on...
        return null;
    }
    
    /**
     * Since hunters cannot die, the boolean should be always true, right?
     * @return true
     */
    public boolean isAlive()
    {
    	return ALIVE;
    }
    
    /**
     * Return the hunter's field.
     * @return The hunter's field.
     */
    public Field getField()
    {
        return field;
    }
    
    /**
     * Return the hunter's location.
     * @return The hunter's location.
     */
    public Location getLocation()
    {
        return location;
    }
    
    /**
     * Place the hunter at the new location in the given field.
     * @param newLocation The hunter's new location.
     */
    public void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
	
}
