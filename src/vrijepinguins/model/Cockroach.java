package vrijepinguins.model;

import vrijepinguins.controller.Randomizer;
import java.util.List;
import java.util.Random;


/**
 * A simple model of a cockroach.
 * Cockroaches age, move, breed, and die.
 * 
 * @author Michaël van der Veen
 * @version 2015.2.2
 */
public class Cockroach extends Animal
{
    // Characteristics shared by all cockroaches (class variables).

    // The age at which a cockroach can start to breed.
    private static final int BREEDING_AGE = 7;
    // The age to which a cockroach can live.
    private static final int MAX_AGE = 28;
    // The likelihood of a cockroach breeding.
    private static final double BREEDING_PROBABILITY = 0.04;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 2;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    // The value of a grass object
    private static final int GRASS_FOOD_VALUE = 6;
    // An area
    private Area newArea;
    // Food level for a cockroach
    private int foodLevel;
    
    // Individual characteristics (instance fields).
    
    // The cockroach's age.
    private int age;

    /**
     * Create a new cockroach. A cockroach may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the cockroach will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Cockroach(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        
        if(randomAge) {
            age = MAX_AGE-1;
            foodLevel = rand.nextInt(GRASS_FOOD_VALUE);
        }
        else{
        	age = 0;
        	foodLevel = GRASS_FOOD_VALUE;
        }
    }
    
    /**
     * This is what the rabbit does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param newCockroach A list to return newly born rabbits.
     */
    public void act(List<Actor> newCockroach)
    {
    	newArea = null;
        incrementAge();
        //incrementHunger();
        if(isAlive()) {
            giveBirth(newCockroach);            
            // Try to move into a free location.
            Location newLocation = getField().freeAdjacentLocation(getLocation());
            if(newLocation != null) {
                setLocation(newLocation);
                findFood();
                crawl();
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
        
    }

    /**
     * Increase the age.
     * This could result in the cockroach's death.
     */
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }
    
    
    /**
     * Check whether or not this cockroach is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newCockroach A list to return newly born rabbits.
     */
    private void giveBirth(List<Actor> newCockroach)
    {
        // New cockroachs are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Cockroach young = new Cockroach(false, field, loc);
            newCockroach.add(young);
        }
    }

    /**
     * Method to let a cockroach find food.
     * Cockroaches ruin the area by decreasing the area level.
     */
    public void findFood()
    {
    	Field field = getField();
    	Area area = field.getSameLocation(getLocation());
    	if(area.getType()==2){
    		area.setLevel(50);
    		foodLevel=1;
    	}else{
    		area.special(50);
    		foodLevel=0;
    	}
	}
    
    
    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    private int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }

    /**
     * A cockroach can breed if it has reached the breeding age.
     * @return true if the cockroach can breed, false otherwise.
     */
    private boolean canBreed()
    {
        return age >= BREEDING_AGE&&(foodLevel==1);
    }

    /**
     * Generate a new area
     * @return an vrijepinguins.model.Area
     */
    public Area newArea()
    {
    	if(newArea!=null)
    	{
    		return newArea;
    	}
    	return null;
    }
    
    
}
