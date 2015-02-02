package vrijepinguins.model;

import java.awt.Color;
import java.util.List;

/**
 * Public abstract class vrijepinguins.model.Ground.
 * This abstract class implements from interface vrijepinguins.model.Area.
 */
public abstract class Ground implements Area
{
	//Whether the ground is edible.
	protected boolean edible;
	//The ground's field.
	protected Field field;
	//The ground's vrijepinguins.model.Location.
	protected AreaLocation areaLocation;

    /**
     * Constructor for a vrijepinguins.model.Ground object
     * @param field
     * @param areaLocation
     */
	public Ground(Field field, AreaLocation areaLocation)
	{
		this.field = field;
		setLocation(areaLocation);
		edible = true;
	}

    /**
     * Abstract method for letting the time pass on an object.
     * @param newArea
     */
	public abstract void passTime(List<Area> newArea);

    /**
     * Getter for an arealocation
     * @return
     */
	public AreaLocation getAreaLocation()
	{
		return areaLocation;
	}

    /**
     * Setter for a location
     * @param newAreaLocation
     */
	public void setLocation(AreaLocation newAreaLocation)
	{
		if(areaLocation !=null){
			field.clear(areaLocation);
		}
		areaLocation = newAreaLocation;
		field.placeArea(this, newAreaLocation);
	}

    /**
     * Returns true if the ground is edible.
     * @return true or false
     */
	public boolean edible()
	{
		return edible;
	}

    /**
     * Getter for color
     * @return a color
     */
	public Color getColor()
	{
		return Color.GRAY;
	}
	
	
}