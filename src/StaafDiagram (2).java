// Voorbeeld 0805  Staafdiagram
import javax.swing.*;
import java.awt.*;   
   
public class Vb0805 extends JFrame {
  public static void main( String args[] ) {
    JFrame frame = new Vb0805();
    frame.setSize( 350, 300 );
    frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    frame.setTitle( "Voorbeeld 0805  Staafdiagram" );
    frame.setContentPane( new Tekenpaneel() );
    frame.setVisible( true );
  }
}


class Tekenpaneel extends JPanel {
  private Staafdiagram staafdiagram;
  
  public Tekenpaneel() {
    int[] lengtes = { 144, 98, 117, 130, 172 };
    setBackground( Color.WHITE );
    staafdiagram = new Staafdiagram( lengtes, 30 );
  }
  
  public void paintComponent( Graphics g ) {
    super.paintComponent( g );
    staafdiagram.teken( g, 70, 250 );
  }  
}


class Staafdiagram {
  private int[] lengtes;
  private int witruimte;

  public Staafdiagram( int[] lengtes, int witruimte ) {
    this.lengtes = lengtes;
    this.witruimte = witruimte;
  }

  public void teken( Graphics g, int x, int y ) {
    int startX = x;
    
    // Teken de staven
    for( int lengte : lengtes ) {
      g.fillRect( x, y - 20 - lengte, 20, lengte );
      x += witruimte;
    }

    // Zet de getallen onder de staven
    x = startX;
    for( int lengte : lengtes ) {
      g.drawString( String.format( "%d", lengte ), x, y );
      x += witruimte;
    }
  }
}