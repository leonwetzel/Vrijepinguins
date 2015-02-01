import java.awt.Color;
import java.util.List;

public class Radiation extends Ground
{
    private int groundLevel;
    private static final int GROUND_MAX_LEVEL = 10,GROUND_MIN_LEVEL=0;

    public Radiation(Field field, AreaLocation areaLocation)
    {
    	super(field, areaLocation);
    	groundLevel = 10;
    	
    }
    public Radiation(Field field, AreaLocation areaLocation,int groundLevel)
    {
    	super(field, areaLocation);
    	this.groundLevel = groundLevel;
    }
	public Color getColor()
	{
		Color color = Color.YELLOW;
		int lvl = groundLevel/10;
		switch(lvl){
		case 0:// case 1: case 2:
			color =  new Color(229,181,150);
			break;
		case 1:
			color =  new Color(229,181,110);
			break;
		case 2: 
			color =  new Color(229,181,70);
			break;
		case 3:
			color =  new Color(229,200,60);
			break;
		case 4: 
			color =  new Color(240,230,50);
			break;
		case 5: 
			color =  new Color(255,255,40);
			break;	
			default: break;
		}
		return color;
	}
    
    public int getGroundLevel()
    {
    	return groundLevel;
    }
    
	public void lowerLevel(int amount)
	{
    	if(groundLevel>(GROUND_MIN_LEVEL+amount))
		{
    		groundLevel-=amount;
    	}else{
    		groundLevel=0;
    		
    	}
    	
	}
    
	public void higherLevel(int ammount)
	{
    	if(groundLevel<GROUND_MAX_LEVEL-ammount)
    	{
    		groundLevel=groundLevel+ammount;
    	}else{
    		groundLevel=GROUND_MAX_LEVEL;
    	} 		
	}
    
	/**
	 * general method passTime for all actors in the simulator
	 */
	public void passTime(List<Area> newRadiation)
	{
 		
 		
 		
 		
    	this.lowerLevel(1);
 			
    	
	}
 	
	public void walkedOn()
	{
		
		Object object= field.getSameAreaLocation(areaLocation);
		if(object instanceof Cockroach){
			groundLevel = 80;
		}
	}
 	
	public void beingEaten()
	{
		higherLevel(GROUND_MAX_LEVEL);
	}
 	 
	public void special()
	{
		AreaLocation a = areaLocation;
		field.clear(areaLocation);
		Radiation radiation = new Radiation(field,a);
		field.placeArea(radiation,a);
	}
	public void special(int level){
		field.clear(areaLocation);
		Radiation radiation = new Radiation(field,areaLocation,level);
		field.placeArea(radiation,areaLocation);
	}
	/**
	 * Return the animal's location.
     * @return The animal's location.
     */
	public AreaLocation getAreaLocation()
    {
        return areaLocation;
    }
    
    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    public void setLocation(AreaLocation newAreaLocation)
    {
        if(areaLocation != null) {
            field.clear(areaLocation);
        }
        areaLocation = newAreaLocation;
        field.placeArea(this, newAreaLocation);
    }
    
    /**
     * Return the animal's field.
     * @return The animal's field.
     */
    public Field getField()
    {
        return field;
    }
    
    public void setLevel(int level)
    {
    	groundLevel = level;
    }
}
