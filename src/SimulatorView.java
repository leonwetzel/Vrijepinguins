import java.awt.*;
import javax.imageio.*;
import java.awt.image.*;
import java.applet.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

import java.util.LinkedHashMap;
import java.util.Map;
/**
 * A graphical view of the simulation grid.
 * The view displays a colored rectangle for each location 
 * representing its contents. It uses a default background color.
 * Colors for each type of species can be defined using the
 * setColor method.
 * 
 * @author David J. Barnes and Michael KÃ¶lling
 * @version 2011.07.31
 */
public class SimulatorView extends JFrame
{
    // Colors used for empty locations.
    private static final Color EMPTY_COLOR = Color.WHITE; //new Color(127,51,0);

    // Color used for objects that have no defined color.
    private static final Color UNKNOWN_COLOR = Color.gray;
    
    private final String STEP_PREFIX = "Step: ";
    private final String POPULATION_PREFIX = "Population: ";
    private JLabel stepLabel, population;
    private FieldView fieldView;
    // A map for storing colors for participants in the simulation
    private Map<Class, Color> colors;
    // A statistics object computing and storing simulation information
    private FieldStats stats;
    
    // MenuItems
    JMenuItem about;
    JMenuItem userInput;
    JMenuItem switchView;
    
    //JFrames
    JFrame aInput;
    JFrame aboutFrame;
    
    //JPanels
    JPanel container;
    
    //Buttons object
    Button button;
    
    /**
     * Create a view of the given width and height.
     * @param height The simulation's height.
     * @param width  The simulation's width.
     */
    public SimulatorView(int height, int width)
    {  
    	// maak het frame en dergelijke
    	//setLocation(410, 0);
    	button = new Button();
    	setTitle("Vossen & Konijnen, uitgevoerd door Vrijepinguins");
        stats = new FieldStats();
        colors = new LinkedHashMap<Class, Color>();
        stepLabel = new JLabel(STEP_PREFIX, JLabel.CENTER);
        population = new JLabel(POPULATION_PREFIX, JLabel.CENTER);
        
        Container contentPane = getContentPane();  //mich
        contentPane.setLayout(new BorderLayout());
        
        // maak de menubar
        makeMenuBar(this);

        // maak de buttons
        JToolBar lbuttons = new JToolBar(JToolBar.VERTICAL);
        button.makeleftSidebarButtons(this, lbuttons);
        
        //maak invoervelden voor de verschillende dieren
        
        
        
        fieldView = new FieldView(height, width);
        JPanel mcontent = new JPanel();
        mcontent.setLayout(new BorderLayout());
        mcontent.setPreferredSize(new Dimension(750, 550));
        mcontent.add(stepLabel, BorderLayout.NORTH);
        mcontent.add(fieldView, BorderLayout.CENTER);
        mcontent.add(population, BorderLayout.SOUTH);
        
        
        contentPane.add(lbuttons, BorderLayout.WEST);
        contentPane.add(mcontent,BorderLayout.CENTER);
        //JSplitPane container = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, lbuttons, mcontent);
        //container.setEnabled(false);
        //getContentPane().add(container);
        //setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
    
    public void inputFrame(){
    	JFrame frame = new JFrame("Diereigenschappen");
    	container = new JPanel();
    	container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //new grid for fox property inputs
        button.foxProperties();
        //new grid for rabbit property inputs
        button.rabbitProperties();
        //new grid for penguin property inputs
       button.penguinProperties();
        frame.add(container);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }
    /**
     * Define a color to be used for a given class of animal.
     * @param animalClass The animal's Class object.
     * @param color The color to be used for the given class.
     */
    public void setColor(Class animalClass, Color color)
    {
        colors.put(animalClass, color);
    }
        
    /**
     * Method to create the left sidebar
     * @param frame
     */
    
    /**
     * Method to create the menubar
     * @param frame
     */
    public void makeMenuBar(JFrame frame)
    {	
        JMenuBar mbar = new JMenuBar();
        JMenu menu1 = new JMenu("File");
        	switchView = new JMenuItem("Switch View");
        	menu1.add(switchView);
        JMenu menu2 = new JMenu("Edit");
        	userInput= new JMenuItem("Animal Input");
        	menu2.add(userInput);
        JMenu menu3 = new JMenu("Help");
        	about = new JMenuItem("About");
        	menu3.add(about);
        mbar.add(menu1);
        mbar.add(menu2);
        mbar.add(menu3);
        setJMenuBar(mbar);    	
    }
     
    /**
     * @return The color to be used for a given class of animal.
     */
    private Color getColor(Class animalClass)
    {
        Color col = colors.get(animalClass);
        if(col == null) {
            // no color defined for this class
            return UNKNOWN_COLOR;
        }
        else {
            return col;
        }
    }

    /**
     * Show the current status of the field.
     * @param step Which iteration step it is.
     * @param field The field whose status is to be displayed.
     */
    public void showStatus(int step, Field field)
    {
        if(!isVisible()) {
            setVisible(true);
        }
            
        stepLabel.setText(STEP_PREFIX + step);
        stats.reset();
        
        fieldView.preparePaint();

        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                Object animal = field.getObjectAt(row, col);
                if(animal != null) {
                    stats.incrementCount(animal.getClass());
                    fieldView.drawMark(col, row, getColor(animal.getClass()));
                }
                else {
                	Area area = field.getAreaAt(row, col);
                	fieldView.drawMark(col, row, area.getColor());
                }
            }
        }
        stats.countFinished();

        population.setText(POPULATION_PREFIX + stats.getPopulationDetails(field));
        fieldView.repaint();
    }

    /**
     * Determine whether the simulation should continue to run.
     * @return true If there is more than one species alive.
     */
    public boolean isViable(Field field)
    {
        return stats.isViable(field);
    }
    
    /**
     * Provide a graphical view of a rectangular field. This is 
     * a nested class (a class defined inside a class) which
     * defines a custom component for the user interface. This
     * component displays the field.
     * This is rather advanced GUI stuff - you can ignore this 
     * for your project if you like.
     */
    private class FieldView extends JPanel
    {
        private final int GRID_VIEW_SCALING_FACTOR = 6;

        private int gridWidth, gridHeight;
        private int xScale, yScale;
        Dimension size;
        private Graphics g;
        private Image fieldImage;

        /**
         * Create a new FieldView component.
         */
        public FieldView(int height, int width)
        {
            gridHeight = height;
            gridWidth = width;
            size = new Dimension(0, 0);
        }

        /**
         * Tell the GUI manager how big we would like to be.
         */
        public Dimension getPreferredSize()
        {
            return new Dimension(gridWidth * GRID_VIEW_SCALING_FACTOR,
                                 gridHeight * GRID_VIEW_SCALING_FACTOR);
        }

        /**
         * Prepare for a new round of painting. Since the component
         * may be resized, compute the scaling factor again.
         */
        public void preparePaint()
        {
            if(! size.equals(getSize())) {  // if the size has changed...
                size = getSize();
                fieldImage = fieldView.createImage(size.width, size.height);
                g = fieldImage.getGraphics();

                xScale = size.width / gridWidth;
                if(xScale < 1) {
                    xScale = GRID_VIEW_SCALING_FACTOR;
                }
                yScale = size.height / gridHeight;
                if(yScale < 1) {
                    yScale = GRID_VIEW_SCALING_FACTOR;
                }
            }
        }
        
        /**
         * Paint on grid location on this field in a given color.
         */
        public void drawMark(int x, int y, Color color)
        {
            g.setColor(color);
            g.fillRect(x * xScale, y * yScale, xScale-1, yScale-1);
        }
        
        
        /**
         * The field view component needs to be redisplayed. Copy the
         * internal image to screen.
         */
        public void paintComponent(Graphics g)
        {
            if(fieldImage != null) {
                Dimension currentSize = getSize();
                if(size.equals(currentSize)) {
                    g.drawImage(fieldImage, 0, 0, null);
                }
                else {
                    // Rescale the previous image.
                    g.drawImage(fieldImage, 0, 0, currentSize.width, currentSize.height, null);
                }
            }
        }
    }

    private void setImage(String filePath,JPanel container)
    {
    	File file = new File(filePath);
    	BufferedImage images = null;
    	try{
    		images = ImageIO.read(file);
    	}catch(Exception e){
    		System.out.println("Geen image, voer een image in bij de map extraFiles/Images genaamd background.jpg");
    	}
    	if(images!=null){
    		JLabel label = new JLabel(new ImageIcon(images));
    		container.add(label);
    	}
    }
    
	public void about() {
		JFrame frameAbout = new JFrame("About");
    	JPanel container = new JPanel();
    	container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
    	String filePath = "extraFiles//images//background.jpg";
    	setImage(filePath,container);
    	/*
    	File file = new File(filePath);
    	BufferedImage images = null;
    	try{
    		images = ImageIO.read(file);
    	}catch(Exception e){
    		System.out.println("Geen image, voer een image in bij de map extraFiles/Images genaamd background.jpg");
    	}
    	if(images!=null){
    		JLabel label = new JLabel(new ImageIcon(images));
    		container.add(label);
    	}*/
		
    	JPanel infoPanel = new JPanel();
    	infoPanel.setBackground(Color.WHITE);
    	JTextArea infoText = new JTextArea(
    			"Aan dit programma zijn vele uren aan typen, lezen en nadenken besteed. \n"
    			+"Dit werk is met uiterste zorg en moeite in elkaar gezet.\n"
				+"Steel dit werk dus niet. Dat maakt ons heel verdrietig\n"
    			+"\nDit programma is gepubliceerd door Vrijepinguins in opdracht van Dierenpark Groningen\n"
    			+"\nAuteurs: Paul Koning, Jesse Stal, Michaël van der Veen en Leon Wetzel\n"
    			+"\nCopyright Vrijepinguins © 2015 "
    					);
    	infoPanel.add(infoText);
		frameAbout.add(infoPanel,BorderLayout.NORTH);
        frameAbout.add(container,BorderLayout.SOUTH);
        //frameAbout.setSize(width 600);
        frameAbout.pack();
        frameAbout.setResizable(false);
        frameAbout.setLocation(400,0);
        frameAbout.setVisible(true);
	}
}
