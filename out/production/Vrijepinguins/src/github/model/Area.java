package github.model;

import java.util.List;
import java.awt.Color;

/**
* Public interface github.model.Area.
 *Contains method signatures for github.model.Ground and github.model.Earth
*
*/

public interface Area {
    /**
     * general method to get groundlevel
     * @return groundlevel
     */
	public int getGroundLevel();

    /**
     * general method passTime in the simulator
     * @param newArea
     */
	public void passTime(List<Area>newArea);

    /**
     * general method for walking over area.
     */
	public void walkedOn();

    /**
     * general method for being eaten.
     */
	public void beingEaten();

    /**
     * general method for special moves.
     */
	public void special();

    /**
     * general method for special moves.
     * @param level
     */
	public void special(int level);

    /**
     * get a location
     * @return arealocation
     */
	public AreaLocation getAreaLocation();

    /**
     * Get a field
     * @return field
     */
	public Field getField();

    /**
     * get a color
     * @return color
     */
	public Color getColor();

    /**
     * Returns true if the ground type is deadly
     * @return true or false
     */
	public boolean isDeadly();

    /**
     * Returns the ground type
     * @return ground type
     */
	public int getType();

    /**
     * Setter for ground level
     * @param Level
     */
	public void setLevel(int Level);
}