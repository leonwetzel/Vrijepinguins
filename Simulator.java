import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;
import java.awt.event.*;

/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing rabbits and foxes.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 * 
 * This class is part of the Vossen & Konijnen Project by Jesse Stal, Paul Koning,
 * Micha�l van der Veen and Leon Wetzel. Don't steal this work. 
 * 
 * Questions regarding code or development process? Please send an e-mail to l.f.a.wetzel@st.hanze.nl.
 */
public class Simulator implements Runnable {
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;
    // The probability that a fox will be created in any given grid position.
    private static final double FOX_CREATION_PROBABILITY = 0.02;
    // The probability that a rabbit will be created in any given grid position.
    private static final double RABBIT_CREATION_PROBABILITY = 0.08; 
    // The probability that a penguin will be created in any given grid position.
    private static final double PENGUIN_CREATION_PROBABILITY = 0.01;
    // The probability that a hunter will be created in any given grid position.
    private static final double HUNTER_CREATION_PROBABILITY = 0.01;
    // the delay between each frame
    private static final int THREAD_DELAY = 100;

    // List of animals in the field.
    private List<Actor> actors;
    // List of Areas in the field.
    private List<Area> areas;
    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    private int step;
    
    private int specialstep;
    // A graphical view of the simulation.
    private SimulatorView view;
    // how many steps the thread has to do
    private int threadStep;
    // is the Thread running
    private boolean running;  
    
    /**
     * Construct a simulation field with default size.
     */    
    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }
    
    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width)
    {
    	// create the main display
    	makeSimPanel(depth, width);
    	// create the histogram view
    	//makeHistogram();
    	// create the chart view
    	//makeChart();
    }
    
    /**
     * Create the main simulator view where all the magic happens
     * 
     */
    public void makeSimPanel(int depth, int width) 
    {
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        
        actors = new ArrayList<Actor>();
        areas = new ArrayList<Area>();
        field = new Field(depth, width);

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width);
        view.setColor(Rabbit.class, Color.WHITE);
        view.setColor(Fox.class, Color.BLUE);
        view.setColor(Penguin.class, Color.CYAN);
        view.setColor(Hunter.class, Color.RED);
        view.setColor(Cockroach.class,new Color(127,51,0));
       
        
        // Add the actionlisteners
        addListeners();
        
        // Setup a valid starting point.
        reset();
        
        // Play the background music
        MusicPlayer player = new MusicPlayer();
        player.playMusic();
    }
    
    /**
     * Run the simulation from its current state for a reasonably long period,
     * (4000 steps).
     */
    public void runLongSimulation()
    {
        simulate(100);
    }
    
    /**
     * Make the buttons interactive!
     */
    public void addListeners()
    {
    	view.oneButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { start(1); }
        });
    	view.hundredButton.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) { start(4000); }
    	});
    	view.resetButton.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) { reset(); }
    	});
    	view.stopButton.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) { stop(); }
    	});
    	view.userInput.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) { view.inputFrame(); }
    	});
    	view.special.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) { specials(); }
    	});
    	view.about.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) { view.about(); }
    	});
    }
    
    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps)
    {
        for(int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
        }
    }
    
    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * fox and rabbit.
     */
    public void simulateOneStep()
    {
        step++;

        // Provide space for newborn animals.
        List<Actor> newActors = new ArrayList<Actor>(); 
        List<Area> newAreas = new ArrayList<Area>();
        // Let all rabbits act.
        for(Iterator<Actor> it = actors.iterator(); it.hasNext(); ) {
        	Actor actor = it.next();
        	actor.act(newActors);
            if(!actor.isAlive()) {
                it.remove();
            }
           if(actor instanceof Druids){
        	   Druids druid = (Druids) actor;
        	   druid.castSpells(newActors);
           }
        }
        
        for(Iterator<Area>it = areas.iterator();it.hasNext();){
        	Area area = it.next();
        	/*if(area.getGroundLevel()<0)
        	{
        		if(area instanceof Grass){
        			
        			newAreas.add(new Radiation(field,area.getAreaLocation(),50));
        		}
        	}*/
        	area.passTime(newAreas);
        	//if(stepspecial!=null&&specialstep<20){
        	//	Radiation a= (Radiation) area.expand(newAreas);
        	//}
        }
               
        // Add new actors to the main lists.
        actors.addAll(newActors);
        areas.addAll(newAreas);

        view.showStatus(step, field);
    }
        
    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;
        actors.clear();
        areas.clear();
        populate();
        
        // Show the starting state in the view.
        view.showStatus(step, field);
        
        // stop the thread (to prevent the simulation from auto-restarting)
        stop();
    }
    
    /**
     * Randomly populate the field with actors.
     */
    private void populate()
    {
        Random rand = Randomizer.getRandom();
        field.clear();
        areas.clear();
        //AreaLocation areaLocation = new AreaLocation(0,0);
    	//Radiation radiation = new Radiation(field,areaLocation);
    	
    	//areas.add(radiation);
    	
    	
    	
    	
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
            	Location location = new Location(row,col);
            	if(rand.nextDouble() <= FOX_CREATION_PROBABILITY) {
                    //Location location = new Location(row, col);
                    Fox fox = new Fox(true, field, location);
                    actors.add(fox);
                } else if(rand.nextDouble() <= RABBIT_CREATION_PROBABILITY) {
                    //Location location = new Location(row, col);
                    Rabbit rabbit = new Rabbit(true, field, location);
                    actors.add(rabbit);
                } else if(rand.nextDouble()<= PENGUIN_CREATION_PROBABILITY){
                	//Location location = new Location(row, col);
                	Penguin penguin = new Penguin(true,field,location);
                	actors.add(penguin);
                } else if(rand.nextDouble() <= HUNTER_CREATION_PROBABILITY) {
                	//Location location = new Location(row, col);
                	if(rand.nextDouble()<=0.60){
                		Hunter hunter = new Hunter(field, location);
                		actors.add(hunter);
                	}else{
                		Druids druids = new Druids(field,location);
                		actors.add(druids);
                	}
                	
                }
            	
               /* else if(rand.nextDouble() <= HUNTER_CREATION_PROBABILITY) {
                	//Location location = new Location(row, col);
                	Cockroach cockroach = new Cockroach(false, field, location);
                	actors.add(cockroach);
                }*/
                // else leave the location empty.
            	
            	AreaLocation areaLocation = new AreaLocation(row,col);
            	Earth earth = new Earth(field,areaLocation);
            	areas.add(earth);
            	//field.placeArea(grass, 0, 0);
                
            }
        }
    }

    /**
     * Start the thread
     * @param steps -1 for infinite; 
     */
    public void start(int steps){
    	threadStep = steps;
    	running = true;
    	new Thread(this).start();
    }
    
    /**
     * Stop the thread
     */
    public void stop(){
    	threadStep = 0;
    	running = false;
    }
    
    /**
     * Decrement one step
     */
    public void decrementSteps(){
    	if (threadStep > 0){
    		threadStep--;
    	}
    }
    
    /**
     * Make simulator steps equal to the steps the thread makes in this class
     * In other words: let the simulator run steps ;)
     */
	@Override
	public void run() {
		while (running){
			if (threadStep != 0){
				simulateOneStep();
				try {
					Thread.sleep(THREAD_DELAY);
				} catch(InterruptedException e){
					e.printStackTrace();
				}
				decrementSteps();
			} else {
				stop();
			}
		}
	}
	
	public void specials(){
		Random rand = Randomizer.getRandom();
		for(int row = 0;row<DEFAULT_DEPTH;row++){
			for(int col = 0; col<DEFAULT_WIDTH;col++){
				if(rand.nextDouble()<=0.10){
					Location location = new Location(row, col);
                    Cockroach cockroach = new Cockroach(true, field, location);
                    actors.add(cockroach);
				}
				AreaLocation areaLocation = new AreaLocation(row,col);
            	Earth earth = new Earth(field,areaLocation,50);
            	areas.add(earth);
            	
            	
			}
		}
	}
	
	
}
