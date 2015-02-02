package vrijepinguins;

import vrijepinguins.controller.Simulator;

/**
 * 
 * This class is part of the Vossen & Konijnen Project by Jesse Stal, Paul Koning,
 * Micha�l van der Veen and Leon Wetzel. Don't steal this work. 
 * 
 * Questions regarding code or development process? Please send an e-mail to l.f.a.wetzel@st.hanze.nl.
 */
public class Main {
	
	private Simulator simulator;
	
	/**
	 * vrijepinguins.Main methode wordt als eerste geroepen en roept simulator aan.
	 * @param args
	 */
	public static void main(String[] args) {
		Main main = new Main();
		main.setSimulator(new Simulator());
	}
	
	/**
	 * setter for simulator
	 * @param simulator 
	 */
	private void setSimulator(Simulator simulator){
		this.simulator = simulator;
	}
	
	/**
	 * Getter for simulator
	 * @return vrijepinguins.controller.Simulator
	 */
	private Simulator getSimulator(){
		return simulator;
	}

}
