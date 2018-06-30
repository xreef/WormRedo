import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class WormFinestraPrincipale extends JFrame implements WindowListener
{
  private static int DEFAULT_FPS = 10;

  // private WormPanel wp;        // where the worm is drawn
  private WormPannelloGioco wp;
  private JTextField jtfBox;   // displays no.of boxes used
  private JTextField jtfTime;  // displays time spent in game
  private JTextField jtfFps;

  private JTextField jtfLevel;
  
  public WormFinestraPrincipale(long period)
  { super("Worm");
    makeGUI(period);

    addWindowListener( this );

    
    pack();
    setResizable(false);
    setVisible(true);
  }  // end of WormChase() constructor


  private void makeGUI(long period)
  {
    Container c = getContentPane();    // default BorderLayout used

    wp = new WormPannelloGioco(this, period);
    c.add(wp, BorderLayout.CENTER);

    JPanel ctrls = new JPanel();   // Pannello per le informazioni
    ctrls.setLayout( new BoxLayout(ctrls, BoxLayout.X_AXIS));

    jtfLevel = new JTextField("L: 1 S:    0 Vite: 5");
    jtfLevel.setEditable(false);
    ctrls.add(jtfLevel);
    
    jtfBox = new JTextField("Fruit: 0");
    jtfBox.setEditable(false);
    ctrls.add(jtfBox);

    jtfTime = new JTextField("Time: 0 secs");
    jtfTime.setEditable(false);
    ctrls.add(jtfTime);

    jtfFps = new JTextField("Average FPS/UPS: 00.00, 00.00");
    jtfFps.setEditable(false);
    ctrls.add(jtfFps);
    
    c.add(ctrls, BorderLayout.SOUTH);
  }  // end of makeGUI()

  public void setLevel(int l, int globalScore, int vite){
	  jtfLevel.setText("L: "+l+" S: "+globalScore+" Vite: "+vite);
  }
  
  public void setFpsUps(String string) {
		jtfFps.setText(string);
  }
  
  public void setBoxNumber(int no)
  {  jtfBox.setText("Fruit: " + no);  }

  public void setTimeSpent(long t)
  {  jtfTime.setText("Time: " + t + " secs"); }
  

  // ----------------- window listener methods -------------

  public void windowActivated(WindowEvent e) 
  { wp.resumeGame();  }

  public void windowDeactivated(WindowEvent e) 
  {  wp.pauseGame();  }


  public void windowDeiconified(WindowEvent e) 
  {  wp.resumeGame();  }

  public void windowIconified(WindowEvent e) 
  {  wp.pauseGame(); }


  public void windowClosing(WindowEvent e)
  {  wp.stopGame();  }


  public void windowClosed(WindowEvent e) {}
  public void windowOpened(WindowEvent e) {}

  // ----------------------------------------------------

  public static void main(String args[])
  { 
    int fps = DEFAULT_FPS;
    if (args.length != 0)
      fps = Integer.parseInt(args[0]);

    long period = (long) 1000.0/fps;
    System.out.println("fps: " + fps + "; period: " + period + " ms");

    new WormFinestraPrincipale(period*1000000L);    // ms --> nanosecs 
  }




} // end of WormChase class