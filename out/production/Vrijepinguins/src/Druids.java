import java.util.Iterator;
import java.util.List;

/**
 * 
 * Public class Druids for Vossen & Konijnen
 * This druid is a human, therefore he should not extend the superclass Animal.
 *
 * Druids can't breed, nor can they die. Their only purpose is to kill all that
 * stands in their way, except other hunters.
 * 
 * @author Micha�l van der Veen
 */
public class Druids implements Actor
{
	private Field field;
	private Location location;
	// hunters can't die, therefore the variable can be regarded as a constant
	private static final boolean ALIVE = true;
	private int manaPool;
	private int spells;

	/**
	 * Create a druid
	 * @param field
	 * @param location
	 */
    public Druids(Field field, Location location)
    {
        this.field = field;
        setLocation(location);
        manaPool = 0;
    }	
	
    /**
     * Way of behavior for a druid.
     * @param druids
     */
    public void act(List<Actor> druids)
    {
    	Area area = field.getSameLocation(location);
    	// Move towards a location where a target could be
        Location newLocation = findTargets();
        if(newLocation == null) { 
            if(area.getType()==3){
	        	area.special();
	        	manaPool++;
            }else if(area.getType()==2&&area.getGroundLevel()<1){
            	area.special();
            	area.special();
            }
            // No target found - try to move to a free location.
            newLocation = getField().freeAdjacentLocation(getLocation());
            
        }
        // See if it was possible to move.
        if(newLocation != null){//)&&(hunger>0||!(area.getType()==1))) {
            setLocation(newLocation);
            
            walk();
        }
    }

    /**
     * This method lets a druid move to another area.
     * This decreases the area level.
     */
	public void walk()
    {
    	Area area = getField().getSameLocation(getLocation());
    	area.walkedOn();
    }
    
    /**
     * Find targets
     * @return Location
     */
    private Location findTargets()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object actor = field.getObjectAt(where);
            if(actor instanceof Cockroach) {
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
     *
     * @return if a druid is alive.
     */
    public boolean isAlive()
    {
    	return ALIVE;
    }
    
    /**
     * Return the druid's field.
     * @return The druid's field.
     */
    public Field getField()
    {
        return field;
    }
    
    /**
     * Return the druid's location.
     * @return The druid's location.
     */
    public Location getLocation()
    {
        return location;
    }
    
    /**
     * Place the druid at the new location in the given field.
     * @param newLocation The druid's new location.
     */
    public void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }

    /**
     * A druid can cast spells. A spell can spawn an animal.
     * @param actors
     */
    public void castSpells(List<Actor>actors)
    {
    	if(manaPool>100){
			if(spells>=3){
			spells = 0;
	    	}
	    	switch(spells){
	    	case 0: castBunny(actors);
	    	break;
	    	case 1: castFox(actors);
	    	break;
	    	case 2: castPenguin(actors);
	    	break;
	    	}
	    	spells++;
	    	manaPool = 0;
    	}
    }

    /**
     * Spawn a rabbit
     * @param newBunny
     */
    private void castBunny(List<Actor>newBunny){
    	List<Location> free = field.getFreeAdjacentLocations(getLocation());
    	Location loc = free.remove(0);
    	Rabbit young = new Rabbit(false,field,loc);
    	newBunny.add(young);
    	System.out.println("ABBRA CABUNNA!");
    }

    /**
     * Spawn a fox
     * @param newFox
     */
    private void castFox(List<Actor>newFox){
    	List<Location> free = field.getFreeAdjacentLocations(getLocation());
    	Location loc = free.remove(0);
    	Fox young = new Fox(false,field,loc);
    	newFox.add(young);
    	System.out.println("WHA DU DA FOX SAIWE!");
    }

    /**
     * Spawn a pinguin
     * @param newPenguin
     */
    private void castPenguin(List<Actor>newPenguin){
    	List<Location> free = field.getFreeAdjacentLocations(getLocation());
    	Location loc = free.remove(0);
    	Penguin young = new Penguin(false,field,loc);
    	newPenguin.add(young);
    	System.out.println("BLAQUE UNA WHIATJE U BACKE A PEGUAIN!");
    }
	
}
