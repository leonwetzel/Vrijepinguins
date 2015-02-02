import java.util.Iterator;
import java.util.List;
import java.util.Random;


/**
 * A simple model of a rabbit.
 * Rabbits age, move, breed, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
public class Rabbit extends Animal
{
    // Characteristics shared by all rabbits (class variables).

    // The age at which a rabbit can start to breed.
    private static final int BREEDING_AGE = 5;
    private int breeding_age = BREEDING_AGE;
    // The age to which a rabbit can live.
    private static final int MAX_AGE = 40;
    private int max_age = MAX_AGE;
    // The likelihood of a rabbit breeding.
    private static final double BREEDING_PROBABILITY = 0.12;
    // The real likelihood of a rabbit breeding.
    private double breeding_probability;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;
    private int max_litter_size = MAX_LITTER_SIZE;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    // 
    private static final int GRASS_FOOD_VALUE = 6;
    //
    private int foodLevel;
    
    // Individual characteristics (instance fields).
    
    // The rabbit's age.
    private int age;

    /**
     * Create a new rabbit. A rabbit may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the rabbit will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Rabbit(boolean randomAge, Field field, Location location, Integer mAge, Integer aNak, Integer vLef)
    {
        super(field, location);
        if(mAge == 0){
        }else{
        	max_age = mAge;
        }
        if(aNak == 0){
        }else{
        	max_litter_size = aNak;
        }
        if(vLef == 0){
        }else{
        	breeding_age = vLef;
        }
        if(randomAge) {
            age = rand.nextInt(max_age);
            foodLevel = rand.nextInt(GRASS_FOOD_VALUE);
        }
        else{
        	age = 0;
        	foodLevel = GRASS_FOOD_VALUE;
        }
        breeding_probability = BREEDING_PROBABILITY;
    }
    
    /**
     * This is what the rabbit does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param newRabbits A list to return newly born rabbits.
     */
    public void act(List<Actor> newRabbits)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newRabbits);            
            // Try to move into a free location.
            Location newLocation = getField().freeAdjacentLocation(getLocation());
            if(newLocation != null) {
                setLocation(newLocation);
                findFood();
                walk();
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
        
    }

    /**
     * Increase the age.
     * This could result in the rabbit's death.
     */
    private void incrementAge()
    {
        age++;
        if(age > max_age) {
            setDead();
        }
    }
    
    private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 2) {
           setBreedingChance(0.00);
        }else if(foodLevel >= GRASS_FOOD_VALUE-2){
        	setBreedingChance(0.16);
        }else{
        	setBreedingChance(BREEDING_PROBABILITY);
        }
        	
    }
    
    private void setBreedingChance(double chance)
    {
    	breeding_probability = chance;
    }
    
    /**
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newRabbits A list to return newly born rabbits.
     */
    private void giveBirth(List<Actor> newRabbits)
    {
        // New rabbits are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Rabbit young = new Rabbit(false, field, loc, max_age, max_litter_size, breeding_age);
            newRabbits.add(young);
        }
    }
        
    private void findFood()
    {
    	Field field = getField();
    	Area area = field.getSameLocation(getLocation());
    	if(area instanceof Earth){
	    	Earth earth = (Earth) area;
    		int groundLevel = earth.getGroundLevel();
	    	if(groundLevel>GRASS_FOOD_VALUE&&(earth.getType()!=2)){
	    		foodLevel = GRASS_FOOD_VALUE;
	    		earth.beingEaten();
	    	}
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
        if(canBreed() && rand.nextDouble() <= breeding_probability) {
            births = rand.nextInt(max_litter_size) + 1;
        }
        return births;
    }

    /**
     * A rabbit can breed if it has reached the breeding age.
     * @return true if the rabbit can breed, false otherwise.
     */
    private boolean canBreed()
    {
        return age >= breeding_age;
    }
    /**
     * Returns the max litter size of rabbit
     * @return
     */
    public Integer getMaxLitterSize(){
    	return max_litter_size;
    }
    public Integer getMaxAge(){
    	return max_age;
    }
    public Integer getBreedingAge(){
    	return breeding_age;
    }
}
