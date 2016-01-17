import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.List;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sun.java.swing.plaf.windows.resources.windows;

public class objEquipmentSlotWindow extends JFrame  implements MouseListener {
private static objEquipmentSlotWindow myInstance;
private ArrayList<JPanel> panelKart =new ArrayList<JPanel>();
private ArrayList<JLabel>  karty = new ArrayList<JLabel>();
private objCharacterWindow oknoGracza;
private ArrayList<Integer> list = new ArrayList<Integer>();
private String cardType;
private objPlayer  Gracz;

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
		remove(panelKart.get(0));
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
	 //   panelKart.get(0).addMouseListener(this);
	    list=new ArrayList<Integer>();
	}
	public static objEquipmentSlotWindow getInstance(objCharacterWindow window) {
	    if (myInstance == null)
	        myInstance = new objEquipmentSlotWindow(window);
	        
	        return myInstance;
	}    
	
	
	public void ShowEquipment(String name ,int Player)
	{   czyszczenie();
	  Gracz=	oknoGracza.getMainWindow().getLogic().getPlayer(Player);
	//objPlayer  Gracz=	oknoGracza.getMainWindow().getLogic().getPlayer(Player);
	switch(name)
	{
	case"Armor":
		setTitle("Armor");
		//setLabels(5,Player);
		
		if(Gracz.findArmor()!=null&&Gracz.findArmor().size()!=0)
		{
	    this.setSize(250*Gracz.findArmor().size(),200);
		for(int x =0; x< Gracz.findArmor().size()  ;x++)
		{
			karty.add(x, new JLabel());
			karty.get(x).setIcon(getEqupmentIcon("src/images/karta ("+(Gracz.getCardsInPlay().getCard(Gracz.findArmor().get(x)).getIdNr())+").jpg",100,155));
			list.add(Gracz.getCardsInPlay().getCard(Gracz.findArmor().get(x)).getIdNr());
			cardType="Armor";
			panelKart.get(0).add(karty.get(x));
			   add(panelKart.get(0));
		}
		}else
		{
			JLabel tmp = new JLabel();
			karty.add(0, tmp);
			
			karty.get(0).setIcon(getEqupmentIcon("src/images/Ekwipunek/ekw(5).jpg",100,155));
			//karty.get(1).setIcon(oknoGracza.getEqupmentIcon(5,105,110));
	        panelKart.get(0).add(karty.get(0));
	       // panelKart.get(0).add(karty.get(1));
	        add(panelKart.get(0));
	      
		}
		break;
	case"Weapon":
		setTitle("Weapon");
		if(Gracz.findWeapon()!=null&&Gracz.findWeapon().size()!=0)
		{
	    this.setSize(150*Gracz.findWeapon().size(),200);
		for(int x =0; x<  Gracz.findWeapon().size() ;x++)
		{
			karty.add(x, new JLabel());
			karty.get(x).setIcon(getEqupmentIcon("src/images/karta ("+Gracz.getCardsInPlay().getCard(Gracz.findWeapon().get(x)).getIdNr()+").jpg",100,155));
			  list.add(Gracz.getCardsInPlay().getCard(Gracz.findWeapon().get(x)).getIdNr());
			  cardType="Weapon";
			
			panelKart.get(0).add(karty.get(x));
			   add(panelKart.get(0));
		}
		}else
		{
			JLabel tmp = new JLabel();
			karty.add(0, tmp);
			
			karty.get(0).setIcon(getEqupmentIcon("src/images/Ekwipunek/ekw(4).jpg",100,155));
			//karty.get(1).setIcon(oknoGracza.getEqupmentIcon(5,105,110));
	        panelKart.get(0).add(karty.get(0));
	       // panelKart.get(0).add(karty.get(1));
	        add(panelKart.get(0));
	      
		}
		break;
	case"Headgear":
		setTitle("Headgear");
		
		if(Gracz.findHat()!=null&&Gracz.findHat().size()!=0)
		{
	    this.setSize(150*Gracz.findHat().size(),200);
		for(int x =0; x< Gracz.findHat().size()  ;x++)
		{
			karty.add(x, new JLabel());
			karty.get(x).setIcon(getEqupmentIcon("src/images/karta ("+Gracz.getCardsInPlay().getCard(Gracz.findHat().get(x)).getIdNr()+").jpg",100,155));
			list.add(Gracz.getCardsInPlay().getCard(Gracz.findHat().get(x)).getIdNr());
			cardType="Headgear";
			panelKart.get(0).add(karty.get(x));
			   add(panelKart.get(0));
		}
		}else
		{
			JLabel tmp = new JLabel();
			karty.add(0, tmp);
			
			karty.get(0).setIcon(getEqupmentIcon("src/images/Ekwipunek/ekw(2).jpg",100,155));
			//karty.get(1).setIcon(oknoGracza.getEqupmentIcon(5,105,110));
	        panelKart.get(0).add(karty.get(0));
	       // panelKart.get(0).add(karty.get(1));
	        add(panelKart.get(0));
	      
		}
		break;
	case"Bonus":
		setTitle("Bonus Cards");
		
		if(Gracz.findOtherItem()!=null&&Gracz.findOtherItem().size()!=0)
		{
	    this.setSize(150*Gracz.findOtherItem().size(),200);
		for(int x =0; x< Gracz.findOtherItem().size()  ;x++)
		{
			karty.add(x, new JLabel());
			karty.get(x).setIcon(getEqupmentIcon("src/images/karta ("+Gracz.getCardsInPlay().getCard(Gracz.findOtherItem().get(x)).getIdNr()+").jpg",100,155));
			list  .add(Gracz.getCardsInPlay().getCard(Gracz.findOtherItem().get(x)).getIdNr());
			cardType="Bonus";
			
			panelKart.get(0).add(karty.get(x));
			   add(panelKart.get(0));
		}
		}else
		{
			JLabel tmp = new JLabel();
			karty.add(0, tmp);
			
			karty.get(0).setIcon(getEqupmentIcon("src/images/Ekwipunek/ekw(9).jpg",100,155));
			//karty.get(1).setIcon(oknoGracza.getEqupmentIcon(5,105,110));
	        panelKart.get(0).add(karty.get(0));
	       // panelKart.get(0).add(karty.get(1));
	        add(panelKart.get(0));
	      
		}
		break;
	case"Footgear":
		setTitle("Footgear");
		if(Gracz.findBoots()!=null&&Gracz.findBoots().size()!=0)
		{
	    this.setSize(150*Gracz.findBoots().size(),200);
		for(int x =0;x<Gracz.findBoots().size();x++)
		{
			karty.add(x, new JLabel());
			//System.out.println(Gracz.getCardsInPlay().getCard(Gracz.findBoots().get(x)).getIdNr());
			karty.get(x).setIcon(getEqupmentIcon("src/images/karta ("+Gracz.getCardsInPlay().getCard(Gracz.findBoots().get(x)).getIdNr()+").jpg",100,155));
			list.add(Gracz.getCardsInPlay().getCard(Gracz.findBoots().get(x)).getIdNr());
			cardType="Footgear";
			panelKart.get(0).add(karty.get(x));
			   add(panelKart.get(0));
		}
		}else
		{
			JLabel tmp = new JLabel();
			karty.add(0, tmp);
			
			karty.get(0).setIcon(getEqupmentIcon("src/images/Ekwipunek/ekw(8).jpg",100,155));
			//karty.get(1).setIcon(oknoGracza.getEqupmentIcon(5,105,110));
	        panelKart.get(0).add(karty.get(0));
	       // panelKart.get(0).add(karty.get(1));
	        add(panelKart.get(0));
	      
		}
		//setLabels(8,Player);
		break;
	case"Class":
		setTitle("Class");
		if(Gracz.findClass()!=null&&Gracz.findClass().size()!=0)
		{
	    this.setSize(150*Gracz.findClass().size(),200);
		for(int x =0; x< Gracz.findClass().size()  ;x++)
		{
			karty.add(x, new JLabel());
			karty.get(x).setIcon(getEqupmentIcon("src/images/karta ("+Gracz.getCardsInPlay().getCard(Gracz.findClass().get(x)).getIdNr()+").jpg",100,155));
			list  .add(Gracz.getCardsInPlay().getCard(Gracz.findClass().get(x)).getIdNr());
			cardType="Calss";
			
			panelKart.get(0).add(karty.get(x));
			   add(panelKart.get(0));
		}
		}else
		{
			JLabel tmp = new JLabel();
			karty.add(0, tmp);
			
			karty.get(0).setIcon(getEqupmentIcon("src/images/Ekwipunek/ekw(3).jpg",100,155));
			//karty.get(1).setIcon(oknoGracza.getEqupmentIcon(5,105,110));
	        panelKart.get(0).add(karty.get(0));
	       // panelKart.get(0).add(karty.get(1));
	        add(panelKart.get(0));
	      
		}
		//setLabels(3,Player);
		break;
	case"Backpack":
		setTitle("Backpack");
		if(Gracz.getCarriedCards()!=null&&Gracz.getCarriedCards().size()!=0)
		{
	    this.setSize(150*Gracz.getCarriedCards().size(),200);
		for(int x =0;x< Gracz.getCarriedCards().size();x++)
		{
			karty.add(x, new JLabel());
			karty.get(x).setIcon(getEqupmentIcon("src/images/karta ("+Gracz.getCarriedCards().getCard(x).getIdNr()+").jpg",100,155));
			list.add(Gracz.getCarriedCards().getCard(x).getIdNr());
			cardType="Backpack";
			panelKart.get(0).add(karty.get(x));
			   add(panelKart.get(0));
		}
		}else
		{
			JLabel tmp = new JLabel();
			karty.add(0, tmp);
			
			karty.get(0).setIcon(getEqupmentIcon("src/images/Ekwipunek/ekw(9).jpg",100,155));
			//karty.get(1).setIcon(oknoGracza.getEqupmentIcon(5,105,110));
	        panelKart.get(0).add(karty.get(0));
	       // panelKart.get(0).add(karty.get(1));
	        add(panelKart.get(0));
	      
		}
		//setLabels(9,Player);
		break;
	default:
		break;
	}
for(JLabel i : karty)
{
	i. addMouseListener(this);
}
	}
	public BufferedImage scaleImage(int WIDTH, int HEIGHT, String filename) {
        BufferedImage bi = null;
        ImageIcon ii ;
        ii=null;
        try {
        	System.out.println(filename);
            ii = new ImageIcon(ImageIO.read(new File(filename)));//path to image
            bi = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = (Graphics2D) bi.createGraphics();
            g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
            g2d.drawImage(ii.getImage(), 0, 0, WIDTH, HEIGHT, null);
        } catch (Exception e) {
        	System.out.println("blad");
            e.printStackTrace();
            return null;
        }
        return bi;
    }
	
	public ImageIcon getEqupmentIcon(String filename,int x,int y)
	{
		BufferedImage img;
		
	    img  =scaleImage(x, y,filename);
	    ImageIcon icon=new ImageIcon((Image) img);
		return icon;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		if (e.getButton() == MouseEvent.BUTTON1)
		{

			int x = e.getX();
			int y = e.getY();
			
       for(int i=0;i<list.size();i++ )
       {
    	System.out.println("list size"+list.size()+" i "+i+" Panel size"+	panelKart.size());
    
    	   if (e.getSource() == karty.get(i))
    	   {
    		   switch(cardType)
    		   {
    		   case "Armor":
    			   
    			   oknoGracza.getMainWindow().useCardWindow(Gracz.getCardsInPlay().getCard(Gracz.findArmor().get(i)),Gracz);
    			   break;
    		   case "Weapon":
    			   oknoGracza.getMainWindow().useCardWindow(Gracz.getCardsInPlay().getCard(Gracz.findWeapon().get(i)),Gracz);
    			   break;
    		   case "Footgear":
    			   oknoGracza.getMainWindow().useCardWindow(Gracz.getCardsInPlay().getCard(Gracz.findBoots().get(i)),Gracz);
    			   break;
    		   case "Headgear":
    			   oknoGracza.getMainWindow().useCardWindow(Gracz.getCardsInPlay().getCard(Gracz.findHat().get(i)),Gracz);
    			   break;
    		   case "Class":
    			   oknoGracza.getMainWindow().useCardWindow(Gracz.getCardsInPlay().getCard(Gracz.findClass().get(i)),Gracz);
    			   break;
    		   case "Bonus":
    			   oknoGracza.getMainWindow().useCardWindow(Gracz.getCardsInPlay().getCard(Gracz.findOtherItem().get(i)),Gracz);
    			   break;
    		   case "Backpack":
    			   oknoGracza.getMainWindow().useCardWindow(Gracz.getCarriedCards().getCard(i),Gracz);
    			   break;
    		   }
    		   
    	   }
	    }
	}
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
