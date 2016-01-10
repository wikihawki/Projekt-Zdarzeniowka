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
	    this.setSize(250, 150);
	    panelKart.add(new JPanel());
	    panelKart.get(0).setBackground( new Color(231, 218, 167) );
	    panelKart.get(0).setLayout(new GridLayout(1, 3));
	    this.add(panelKart.get(0));
	    this.setResizable(false);
	}
	
	private void czyszczenie()
	{
		
	}
	public static objEquipmentSlotWindow getInstance(objCharacterWindow window) {
	    if (myInstance == null)
	        myInstance = new objEquipmentSlotWindow(window);
	        
	        return myInstance;
	}    
	
	
	public void ShowEquipment(String name ,int Player)
	{   remove(panelKart.get(0));
        new JFrame();
		repaint();
		this.getContentPane().setBackground( new Color(231, 218, 167) );
		this.setLayout(new BorderLayout());
	    this.setSize(250, 200);
	    this.setResizable(false);
		panelKart.set(0, new JPanel());
	    panelKart.get(0).removeAll();
	    panelKart.get(0).updateUI();
	    panelKart.get(0).setBackground( new Color(231, 218, 167) );
	    panelKart.get(0).setLayout(new GridLayout(1, 3));
	//objPlayer  Gracz=	oknoGracza.getMainWindow().getLogic().getPlayer(Player);
	switch(name)
	{
	case"Armor":
		setTitle("Armor");
		setLabels(5,Player);
		
		break;
	case"Weapon":
		setTitle("Weapon");
		setLabels(4,Player);
		break;
	case"Headgear":
		setTitle("Headgear");
		setLabels(2,Player);
		break;
	case"Footgear":
		setTitle("Footgear");
		setLabels(8,Player);
		break;
	case"Class":
		setTitle("Class");
		setLabels(3,Player);
		break;
	case"Backpack":
		setTitle("Backpack");
		setLabels(9,Player);
		break;
	default:
		break;
	}
	
		
	}
public void setLabels(int segment,int Player)
{
	objPlayer  Gracz=	oknoGracza.getMainWindow().getLogic().getPlayer(Player);
	if(Gracz.findArmor()!=null&&Gracz.findArmor().size()!=0)
	{
    this.setSize(250+250%Gracz.findArmor().size(),150*(1+Gracz.findArmor().size()/3));
	for(int x :Gracz.findArmor())
	{
		karty.add(x, new JLabel());
		
	}
	}else
	{
		JLabel tmp = new JLabel();
		karty.add(0, tmp);
		karty.add(1, tmp);
		karty.get(0).setIcon(oknoGracza.getEqupmentIcon(segment,100,155));
		//karty.get(1).setIcon(oknoGracza.getEqupmentIcon(5,105,110));
        panelKart.get(0).add(karty.get(0));
       // panelKart.get(0).add(karty.get(1));
        add(panelKart.get(0));
      
	}
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
