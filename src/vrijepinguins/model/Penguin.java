package vrijepinguins.model;

import java.util.List;
import java.util.Iterator;
import java.util.Random;

import vrijepinguins.controller.Randomizer;

/**
 * A simple model of a penguin.
 * Penguins age, move, eat rabbits, and die.
 * 
 * @author Michaël van der Veen
 * @version 2015.2.2
 */
public class Penguin extends Animal
{
    // Characteristics shared by all penguins (class variables).
    
    // The age at which a penguin can start to breed.
    private static final int BREEDING_AGE = 5;
    private int breeding_age = BREEDING_AGE;
    // The age to which a penguin can live.
    private static final int MAX_AGE = 26;
    private int max_age = MAX_AGE;
    // The likelihood of a penguin breeding.
    private static final double BREEDING_PROBABILITY = 0.06;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 2;
    private int max_litter_size = MAX_LITTER_SIZE;
    // The food value of a single rabbit. In effect, this is the
    // number of steps a penguin can go before it has to eat again.
    private static final int RABBIT_FOOD_VALUE = 7;
    // The food value of a single penguin.  In effect, this is the
    // number of steps a penguin can go before it has to eat again.
    private static final int FOX_FOOD_VALUE = 1;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    // Individual characteristics (instance fields).
    // The penguin's age.
    private int age;
    // The penguin's food level, which is increased by eating rabbits.
    private int foodLevel;

    /**
     * Create a penguin. A penguin can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the fox will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Penguin(boolean randomAge, Field field, Location location, Integer mAge, Integer aNak, Integer vLef)
    {
        super(field, location);
        if(mAge !=0){
        	max_age = mAge;
        }
        if(aNak != 0){
        	max_litter_size = aNak;
        }
        if(vLef != 0){
        	breeding_age = vLef;
        }
        	      
        if(randomAge) {
            age = rand.nextInt(max_age);
            foodLevel = rand.nextInt(RABBIT_FOOD_VALUE)+rand.nextInt(FOX_FOOD_VALUE);
        }
        else {
            age = 0;
            foodLevel = RABBIT_FOOD_VALUE+FOX_FOOD_VALUE;
        }
    }
    
    /**
     * This is what the penguin does most of the time: it hunts for
     * rabbits and foxes. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param newPinguins A list to return newly born foxes.
     */
    public void act(List<Actor> newPinguins)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newPinguins);            
            // Move towards a source of food if found.
            Location newLocation = findFood();
            if(newLocation == null) { 
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
            if(newLocation != null) {
                setLocation(newLocation);
                walk();
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }

    /**
     * Increase the age. This could result in the vrijepinguins.model.Penguin's death.
     */
    private void incrementAge()
    {
        age++;
        if(age > max_age) {
            setDead();
        }
    }
    
    /**
     * Make this penguin more hungry. This could result in the penguin's death.
     */
    private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }
    
    /**
     * Look for rabbits and foxes adjacent to the current location.
     * Only the first live rabbit or fox is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if(rabbit.isAlive()) { 
                    rabbit.setDead();
                    if(foodLevel>FOX_FOOD_VALUE){
                    	foodLevel +=RABBIT_FOOD_VALUE;
                    }else if(foodLevel<FOX_FOOD_VALUE){
                    	foodLevel = FOX_FOOD_VALUE+RABBIT_FOOD_VALUE;
                    }
                    return where;
                }
            }
            else if(animal instanceof Fox){
            	Fox fox = (Fox) animal;
            	if(fox.isAlive()){
            		fox.setDead();
                    if(foodLevel>RABBIT_FOOD_VALUE){
                    	foodLevel +=FOX_FOOD_VALUE;
                    }else if(foodLevel<RABBIT_FOOD_VALUE){
                    	foodLevel = FOX_FOOD_VALUE+RABBIT_FOOD_VALUE;
                    }
            		return where;
            	}
            }
            
        }
        return null;
    }
    
    /**
     * Check whether or not this vrijepinguins.model.Penguin is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newPenguin A list to return newly born foxes.
     */
    private void giveBirth(List<Actor> newPenguin)
    {
        // New vrijepinguins.model.Penguin are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Penguin young = new Penguin(false, field, loc, max_age, max_litter_size, breeding_age);
            newPenguin.add(young);
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
            births = rand.nextInt(max_litter_size) + 1;
        }
        return births;
    }

    /**
     * A penguin can breed if it has reached the breeding age.
     */
    private boolean canBreed()
    {
        return age >= breeding_age;
    }
    
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
