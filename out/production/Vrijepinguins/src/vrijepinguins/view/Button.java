package vrijepinguins.view;

import java.awt.*;

import javax.swing.*;

public class Button{
	
	SimulatorView sim;
	JButton submit;

    // buttons
    JButton hundredButton;
    JButton oneButton;
    JButton resetButton;
    JButton stopButton;
    JButton userInput;
    JButton special;
    
    //JTextFields van penguin
    JTextField mAgeField3;
    JTextField aNakField3;
    JTextField vLefField3;
    
    //JTextFields van rabbit
    JTextField mAgeField2;
    JTextField aNakField2;
    JTextField vLefField2;
    
    //JTextFields van fox
    JTextField mAgeField;
    JTextField aNakField;
    JTextField vLefField;
    
    public void rabbitProperties(){
    	JPanel cRabbit = new JPanel();
        cRabbit.setLayout(new BorderLayout());
        JPanel gridLayoutPane2 = new JPanel(); 
        GridLayout gridLayout2 = new GridLayout(3,2);
        gridLayoutPane2.setLayout(gridLayout2);
        
        JLabel mAge2 = new JLabel("Max Leeftijd");
        JLabel aNak2 = new JLabel("Aantal nakomelingen");
        JLabel vLef2 = new JLabel("Voortplantingsleeftijd");
        JLabel animalLabel2 = new JLabel("vrijepinguins.model.Rabbit Eigenschappen:");
        
        mAgeField2 = new JTextField();
        aNakField2 = new JTextField();
        vLefField2 = new JTextField();
        
        gridLayoutPane2.add(mAge2);
        gridLayoutPane2.add(mAgeField2);
        gridLayoutPane2.add(vLef2);
        gridLayoutPane2.add(vLefField2);
        gridLayoutPane2.add(aNak2);
        gridLayoutPane2.add(aNakField2);
        cRabbit.add(gridLayoutPane2, BorderLayout.CENTER);
        cRabbit.add(animalLabel2, BorderLayout.NORTH);
        sim.container.add(cRabbit);
        sim.container.add(Box.createRigidArea(new Dimension(0,10)));
    }
    
    
    public void penguinProperties(){
    	JPanel cPenguin = new JPanel();
        cPenguin.setLayout(new BorderLayout());
        JPanel gridLayoutPane3 = new JPanel(); 
        GridLayout gridLayout3 = new GridLayout(3,2);
        gridLayoutPane3.setLayout(gridLayout3);
        
        JLabel mAge3 = new JLabel("Max Leeftijd");
        JLabel aNak3 = new JLabel("Aantal nakomelingen");
        JLabel vLef3 = new JLabel("Voortplantingsleeftijd");
        JLabel animalLabel3 = new JLabel("vrijepinguins.model.Penguin Eigenschappen:");
        
        mAgeField3 = new JTextField();
        aNakField3 = new JTextField();
        vLefField3 = new JTextField();
        
        gridLayoutPane3.add(mAge3);
        gridLayoutPane3.add(mAgeField3);
        gridLayoutPane3.add(vLef3);
        gridLayoutPane3.add(vLefField3);
        gridLayoutPane3.add(aNak3);
        gridLayoutPane3.add(aNakField3);
        cPenguin.add(gridLayoutPane3, BorderLayout.CENTER);
        cPenguin.add(animalLabel3, BorderLayout.NORTH);
        sim.container.add(cPenguin);
        sim.container.add(Box.createRigidArea(new Dimension(0,10)));
    }
    
    public void foxProperties(){
    	 JPanel cFox = new JPanel();
         cFox.setLayout(new BorderLayout());
         JPanel gridLayoutPane = new JPanel(); 
         GridLayout gridLayout = new GridLayout(3,2);
         gridLayoutPane.setLayout(gridLayout);
         
         JLabel mAge = new JLabel("Max Leeftijd");
         JLabel aNak = new JLabel("Aantal nakomelingen");
         JLabel vLef = new JLabel("Voortplantingsleeftijd");
         JLabel animalLabel = new JLabel("vrijepinguins.model.Fox Eigenschappen:");
         
         mAgeField = new JTextField();
         aNakField = new JTextField();
         vLefField = new JTextField();
         
         gridLayoutPane.add(mAge);
         gridLayoutPane.add(mAgeField);
         gridLayoutPane.add(vLef);
         gridLayoutPane.add(vLefField);
         gridLayoutPane.add(aNak);
         gridLayoutPane.add(aNakField);
         submit = new JButton("Submit");
         gridLayoutPane.add(submit);
         cFox.add(gridLayoutPane, BorderLayout.CENTER);
         cFox.add(animalLabel, BorderLayout.NORTH);
         sim.container.add(cFox);
         sim.container.add(Box.createRigidArea(new Dimension(0,10)));
    }
    
    /**
     * Method to create the left sidebar
     * @param frame
     */
    public void makeleftSidebarButtons(JFrame frame, JPanel buttons)
    {
        final int SHORTCUT_MASK = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

        oneButton = new JButton("One Step");
        oneButton.getInputMap(javax.swing.JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).
                put(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_SPACE,0), "Space_pressed");
        buttons.add(oneButton);

        hundredButton = new JButton("Play");
        hundredButton.getInputMap(javax.swing.JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).
                put(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H,0), "H_pressed");
        buttons.add(hundredButton);

        resetButton = new JButton("Reset");
        resetButton.getInputMap(javax.swing.JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).
                put(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, 0), "R_pressed");
        buttons.add(resetButton);

        stopButton = new JButton("Pause");
        stopButton.getInputMap(javax.swing.JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).
                put(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P,0), "P_pressed");
        buttons.add(stopButton);
        
        special = new JButton("Special");
        special.getInputMap(javax.swing.JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).
                put(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ENTER,0), "Enter_pressed");
        buttons.add(special);
        
        //buttons.add(oneButton);
        //buttons.add(hundredButton);
        //buttons.add(resetButton);
        //buttons.add(stopButton);
        //buttons.add(special);
        
        frame.add(buttons);

    }

    public SimulatorView getSim() {
        return sim;
    }

    public JButton getSubmit() {
        return submit;
    }

    public JButton getHundredButton() {
        return hundredButton;
    }

    public JButton getOneButton() {
        return oneButton;
    }

    public JButton getResetButton() {
        return resetButton;
    }

    public JButton getStopButton() {
        return stopButton;
    }

    public JButton getUserInput() {
        return userInput;
    }

    public JButton getSpecial() {
        return special;
    }

    public JTextField getmAgeField3() {
        return mAgeField3;
    }

    public JTextField getaNakField3() {
        return aNakField3;
    }

    public JTextField getvLefField3() {
        return vLefField3;
    }

    public JTextField getmAgeField2() {
        return mAgeField2;
    }

    public JTextField getaNakField2() {
        return aNakField2;
    }

    public JTextField getmAgeField() {
        return mAgeField;
    }

    public JTextField getvLefField2() {
        return vLefField2;
    }

    public JTextField getaNakField() {
        return aNakField;
    }

    public JTextField getvLefField() {
        return vLefField;
    }
    
}