import java.util.List;
import java.util.ArrayList;
import java.awt.*;

/**
 * Public class Ground.
 * This class implements from abstract class Ground.
 */
public class Earth extends Ground
{
    private int groundLevel;
    private static final int GROUND_MAX_LEVEL = 9,GROUND_MIN_LEVEL=0;
    
    private ArrayList<Color> grassColors;
    private ArrayList<Color> radiationColors;
    private ArrayList<Color> holyLandColors;
   // private Color color;
    private int type; // 1=Grass, 2=Radiation, 3= HolyLand;


    /**
     * Constructor for an Earth object
     * @param field
     * @param areaLocation
     */
    public Earth(Field field, AreaLocation areaLocation)
    {
    	super(field, areaLocation);
    	groundLevel = 7;
    	type = 1;
    	setColors();
    	//setColor(groundLevel);
    }

    /**
     * Constructor for an Earth object
     * @param field
     * @param areaLocation
     * @param level
     */
    public Earth(Field field, AreaLocation areaLocation,int level)
    {
    	super(field, areaLocation);
    	groundLevel = level;
    	type=2;
    	setColors();
    	//setColor(groundLevel);
    	
    }

    /**
     * Setter for colors
     */
    public void setColors(){
    	grassColors 		= new ArrayList<Color>();
    	radiationColors 	= new ArrayList<Color>();
    	holyLandColors		= new ArrayList<Color>();
    	
    	grassColors.add(new Color(229,181,149));//127.51.0 	0
    	grassColors.add(new Color(229,181,149));//127.51.0 	1 
    	grassColors.add(new Color(229,181,149));//127.51.0 	2 
    	grassColors.add(new Color(229,181,149));//127.51.0 	3 
    	grassColors.add(new Color(229,217,149));//127.109.0 	4
    	grassColors.add(new Color(229,217,149));//127.109.0 	5
    	grassColors.add(new Color(209,229,149));//127.170.0	6
    	grassColors.add(new Color(209,229,149));//127.170.0	7
    	grassColors.add(new Color(198,229,149));//127.209.0	8
    	grassColors.add(new Color(181,229,149));//97.244.0	9
    	
    	radiationColors.add(new Color(229,181,150));//		0
    	radiationColors.add(new Color(229,181,110));//		1
    	radiationColors.add(new Color(229,181,70));//		2
    	radiationColors.add(new Color(229,200,60));//		3
    	radiationColors.add(new Color(240,230,50));//		4
    	radiationColors.add(new Color(255,255,40));//		5
    	radiationColors.add(Color.YELLOW);			//		6
    	radiationColors.add(Color.YELLOW);			//		7
    	radiationColors.add(Color.YELLOW);			//		8
    	radiationColors.add(Color.YELLOW);			//		9
    	
    	for(int i=0;i<10;i++){
    		holyLandColors.add(Color.GREEN);
    	}
    	
    }

    /**
     * Getter for a color
     * @return a Color
     */
    public Color getColor() {
		return setColor(groundLevel);
	}

    /**
     * Another setter for colors, which uses the value of the ground.
     * @param value
     * @return
     */
	public Color setColor(int value) {
			
			if(value>=GROUND_MAX_LEVEL){
				value=GROUND_MAX_LEVEL;
			}else if(value<=GROUND_MIN_LEVEL){
				value=GROUND_MIN_LEVEL;
			}
			if(type==1){
				return grassColors.get(value);
			}else if(type==2){
				return radiationColors.get(value);
			}else if(type==3){
				return holyLandColors.get(value);
			}else{
				return Color.GRAY;
			}
			
		}

    /**
     * Getter for the ground level
     * @return
     */
    public int getGroundLevel()
    {
    	return groundLevel;
    }

    /**
     * Decrease the ground level.
     * @param amount
     */
	public void lowerLevel(int amount)
	{
    	if(groundLevel>GROUND_MIN_LEVEL+amount)
		{
    		groundLevel-=amount;
    	}else{
    		groundLevel=0;
    	}
	}

    /**
     * Increase the groud level.
     * @param amount
     */
	public void higherLevel(int amount)
	{
    	if(groundLevel<GROUND_MAX_LEVEL-amount)
    	{
    		groundLevel+=amount;
    	}else{
    		groundLevel=GROUND_MAX_LEVEL;
    	} 		
	}
    
	/**
	 * general method passTime for all actors in the simulator
	 */
	public void passTime(List<Area> newGrass)
	{
 		if(type==1||type==3){
 			higherLevel(1);
 		}else if(type==2){
 			lowerLevel(1);
 		}
		
 		setColor(getGroundLevel());
 		
 		
	}

    /**
     * Decrease the ground level when an actor walks on it.
     */
	public void walkedOn()
	{
		lowerLevel(3);
	}

    /**
     * Decrease the ground level when the ground is being eaten (when it's grass).
     */
	public void beingEaten()
	{
		lowerLevel(6);
	}

    /**
     * Converts the ground, according to the ground level.
     * For example, grass can be converted to radiation.
     */
	public void special()
	{
		if(type==1){
			type=2;
		}else if(type==2){
			type=3;
		}else{
			type=1;
		}
	}

    /**
     * Converts the ground, according to the ground level.
     * For example, grass can be converted to radiation.
     *
     * This method uses the ground level..
     * @param level
     */
	public void special(int level)
	{
		if(type==1){
			type=2;
			setLevel(level);
		}else if(type==2){
			type=3;
			setLevel(level);
		}else{
			type=1;
			setLevel(level);
		}
	}
	
	/**
	 * Return the arealocation
     * @return The animal's location.
     */
	public AreaLocation getAreaLocation()
    {
        return areaLocation;
    }
    
    /**
     * Place the animal at the new location in the given field.
     * @param newAreaLocation The animal's new location.
     */
    public void setAreaLocation(AreaLocation newAreaLocation)
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

    /**
     * Returns true if the earth object is deadly.
     * @return true or false
     */
	public boolean isDeadly()
	{
		if(type==2&&groundLevel>0){
			return true;
		}
		return false;
	}

    /**
     * Setter for the ground level
     * @param level
     */
	public void setLevel(int level){
		groundLevel = level;
	}

    /**
     * Getter for ground type
     * @return
     */
	public int getType() {
		return type;
	}
	
}
