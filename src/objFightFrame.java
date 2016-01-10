

 import java.awt.*;
 import java.awt.event.*;

 public class objFightFrame extends Frame {

	private static objFightFrame myInstance;
	private static final long serialVersionUID = 1L;
	private final Color clrBackground = new Color(231, 218, 167);

   private objFightFrame(MunchkinWindow Window,int index) {
    //Title our frame.
     super("Java 2D Example01");
     setBackground(clrBackground);
    setSize(970,730);
    setResizable(false);
    setVisible(true);  
     addWindowListener(new WindowAdapter()
       {public void windowClosing(WindowEvent e)
        {dispose(); System.exit(0);}
      }
     );
  }


   public void paint(Graphics g) {
	  

    g.setColor(clrBackground );
   g.drawRect(50,50,200,200);


     Graphics2D g2d = (Graphics2D)g;
    g2d.setColor(Color.blue);
    g2d.drawRect(75,75,300,200);
   }
   
   
   public static objFightFrame getInstance(MunchkinWindow Window,int index) {
	    if (myInstance == null)
	        myInstance = new objFightFrame(Window,index);
	        
	    return myInstance;
	}    
 }

	
	
	
	
	

	
