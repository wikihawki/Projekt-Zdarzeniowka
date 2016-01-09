import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sun.java.swing.plaf.windows.resources.windows;

public class objEquipmentSlotWindow extends JFrame  implements MouseListener {
private static objEquipmentSlotWindow myInstance;
private ArrayList<JPanel> panelKart =new ArrayList<JPanel>();
private ArrayList<JLabel>  karty = new ArrayList<JLabel>();
private objCharacterWindow oknoGracza;
	public objEquipmentSlotWindow(objCharacterWindow window)
	{
		 this.oknoGracza=window;
		this.getContentPane().setBackground( new Color(231, 218, 167) );
		this.setLayout(new BorderLayout());
	    this.setSize(750, 150);
	    this.setResizable(false);
	}
	
	
	public static objEquipmentSlotWindow getInstance(objCharacterWindow window) {
	    if (myInstance == null)
	        myInstance = new objEquipmentSlotWindow(window);
	        
	    return myInstance;
	}    
	
	
	public void ShowEquipment(String name ,int Player)
	{
		oknoGracza.getMainWindow().getLogic().getPlayer(Player);
		
	}
	
	
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
