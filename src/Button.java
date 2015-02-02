import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JSlider;

public class Button{
	
	Simulator sim;
	JButton submit;
    
 // buttons
    JButton hundredButton;
    JButton oneButton;
    JButton resetButton;
    JButton stopButton;
    JButton userInput;
    JButton special;
    
    //JTextFields van penguin
    JSlider mAgeField3;
    JSlider aNakField3;
    JSlider vLefField3;
    
    //JTextFields van rabbit
    JSlider mAgeField2;
    JSlider aNakField2;
    JSlider vLefField2;
    
    //JTextFields van fox
    JSlider mAgeField;
    JSlider aNakField;
    JSlider vLefField;
    
    public void rabbitProperties(JPanel panel){
    	JPanel cRabbit = new JPanel();
        cRabbit.setLayout(new BorderLayout());
        JPanel gridLayoutPane2 = new JPanel(); 
        GridLayout gridLayout2 = new GridLayout(3,2);
        gridLayoutPane2.setLayout(gridLayout2);
        
        JLabel mAge2 = new JLabel("Max Leeftijd");
        JLabel aNak2 = new JLabel("Max Aantal nakomelingen");
        JLabel vLef2 = new JLabel("Voortplantingsleeftijd");
        JLabel animalLabel2 = new JLabel("Rabbit Eigenschappen:");
        
        mAgeField2 = new JSlider(JSlider.HORIZONTAL, 0, 200, 100);
        mAgeField2.setMinorTickSpacing(2);
        mAgeField2.setMajorTickSpacing(10);
        mAgeField2.setLabelTable(mAgeField.createStandardLabels(40));
        mAgeField2.setPaintLabels(true);
        aNakField2 = new JSlider(JSlider.HORIZONTAL, 0, 40, 20);
        aNakField2.setMinorTickSpacing(2);
        aNakField2.setMajorTickSpacing(10);
        aNakField2.setLabelTable(mAgeField.createStandardLabels(5));
        aNakField2.setPaintLabels(true);
        vLefField2 = new JSlider(JSlider.HORIZONTAL, 0, 20, 10);
        vLefField2.setMinorTickSpacing(2);
        vLefField2.setMajorTickSpacing(10);
        vLefField2.setLabelTable(mAgeField.createStandardLabels(4));
        vLefField2.setPaintLabels(true);
        
        gridLayoutPane2.add(mAge2);
        gridLayoutPane2.add(mAgeField2);
        gridLayoutPane2.add(vLef2);
        gridLayoutPane2.add(vLefField2);
        gridLayoutPane2.add(aNak2);
        gridLayoutPane2.add(aNakField2);
        cRabbit.add(gridLayoutPane2, BorderLayout.CENTER);
        cRabbit.add(animalLabel2, BorderLayout.NORTH);
        panel.add(cRabbit);
        panel.add(Box.createRigidArea(new Dimension(0,10)));
    }
    
    
    public void penguinProperties(JPanel panel){
    	JPanel cPenguin = new JPanel();
        cPenguin.setLayout(new BorderLayout());
        JPanel gridLayoutPane3 = new JPanel(); 
        GridLayout gridLayout3 = new GridLayout(3,2);
        gridLayoutPane3.setLayout(gridLayout3);
        
        JLabel mAge3 = new JLabel("Max Leeftijd");
        JLabel aNak3 = new JLabel("Max Aantal nakomelingen");
        JLabel vLef3 = new JLabel("Voortplantingsleeftijd");
        JLabel animalLabel3 = new JLabel("Penguin Eigenschappen:");
        
        mAgeField3 = new JSlider(JSlider.HORIZONTAL, 0, 200, 100);
        mAgeField3.setMinorTickSpacing(2);
        mAgeField3.setMajorTickSpacing(10);
        mAgeField3.setLabelTable(mAgeField.createStandardLabels(40));
        mAgeField3.setPaintLabels(true);
        aNakField3 = new JSlider(JSlider.HORIZONTAL, 0, 40, 20);
        aNakField3.setMinorTickSpacing(2);
        aNakField3.setMajorTickSpacing(10);
        aNakField3.setLabelTable(mAgeField.createStandardLabels(5));
        aNakField3.setPaintLabels(true);
        vLefField3 = new JSlider(JSlider.HORIZONTAL, 0, 20, 10);
        vLefField3.setMinorTickSpacing(2);
        vLefField3.setMajorTickSpacing(10);
        vLefField3.setLabelTable(mAgeField.createStandardLabels(4));
        vLefField3.setPaintLabels(true);
        
        gridLayoutPane3.add(mAge3);
        gridLayoutPane3.add(mAgeField3);
        gridLayoutPane3.add(vLef3);
        gridLayoutPane3.add(vLefField3);
        gridLayoutPane3.add(aNak3);
        gridLayoutPane3.add(aNakField3);
        cPenguin.add(gridLayoutPane3, BorderLayout.CENTER);
        cPenguin.add(animalLabel3, BorderLayout.NORTH);
        panel.add(cPenguin);
        panel.add(Box.createRigidArea(new Dimension(0,10)));
    }
    
    public void foxProperties(JPanel panel){
    	 JPanel cFox = new JPanel();
         cFox.setLayout(new BorderLayout());
         JPanel gridLayoutPane = new JPanel(); 
         GridLayout gridLayout = new GridLayout(3,2);
         gridLayoutPane.setLayout(gridLayout);
         
         JLabel mAge = new JLabel("Max Leeftijd");
         JLabel aNak = new JLabel("Max Aantal nakomelingen");
         JLabel vLef = new JLabel("Voortplantingsleeftijd");
         JLabel animalLabel = new JLabel("Fox Eigenschappen:");
         
         mAgeField = new JSlider(JSlider.HORIZONTAL, 0, 200, 100);
         mAgeField.setMinorTickSpacing(2);
         mAgeField.setMajorTickSpacing(10);
         mAgeField.setLabelTable(mAgeField.createStandardLabels(40));
         mAgeField.setPaintLabels(true);
         aNakField = new JSlider(JSlider.HORIZONTAL, 0, 40, 20);
         aNakField.setMinorTickSpacing(2);
         aNakField.setMajorTickSpacing(10);
         aNakField.setLabelTable(mAgeField.createStandardLabels(5));
         aNakField.setPaintLabels(true);
         vLefField = new JSlider(JSlider.HORIZONTAL, 0, 20, 10);
         vLefField.setMinorTickSpacing(2);
         vLefField.setMajorTickSpacing(10);
         vLefField.setLabelTable(mAgeField.createStandardLabels(4));
         vLefField.setPaintLabels(true);
         
         gridLayoutPane.add(mAge);
         gridLayoutPane.add(mAgeField);
         gridLayoutPane.add(vLef);
         gridLayoutPane.add(vLefField);
         gridLayoutPane.add(aNak);
         gridLayoutPane.add(aNakField);
         cFox.add(gridLayoutPane, BorderLayout.CENTER);
         cFox.add(animalLabel, BorderLayout.NORTH);
         panel.add(cFox);
         panel.add(Box.createRigidArea(new Dimension(0,10)));
    }
    
    /**
     * Method to create the left sidebar
     * @param frame
     */
    public void makeleftSidebarButtons(JFrame frame, JToolBar buttons)
    {  	
        oneButton = new JButton("Step 1");
        
        hundredButton = new JButton("Step 4000");
        
        resetButton = new JButton("Reset");
        
        stopButton = new JButton("Pause");     
        
        special = new JButton("Special");
        
        submit = new JButton("Submit");
        
        buttons.add(oneButton);
        buttons.add(hundredButton);  
        buttons.add(resetButton);
        buttons.add(stopButton);
        buttons.add(special);
        
        frame.add(buttons);

    }
    
}
