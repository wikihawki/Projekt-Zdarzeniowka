import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.*;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

public class objCharacterWindow extends JFrame implements MouseListener{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private MunchkinWindow MainWindow;
private static objCharacterWindow myInstance;
private objPlayer Player;
private  JPanel pnlUpper = new JPanel(new GridLayout(1, 3));
private  JPanel pnlMiddle = new JPanel(new GridLayout(1, 3));
private  JPanel pnlBottom = new JPanel(new GridLayout(1, 3));
private ArrayList<JLabel> PaneleEkwipunku=new ArrayList<JLabel>();
private objEquipmentSlotWindow okienko ;
private String PlayerName="";
private int PlayerLVL=1;



public objCharacterWindow(MunchkinWindow Window,int index)
{
	this.okienko=objEquipmentSlotWindow.getInstance(this);
	okienko.setVisible(false);
	this.getContentPane().setBackground( new Color(231, 218, 167) );
	this.MainWindow=Window;
	this.setLayout(new GridLayout(3,3));
    this.setSize(450, 750);
    this.setResizable(false);
    this.setTitle("Singleton Frame. Timestamp:" +
     System.currentTimeMillis());
    this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    for(int i=0;i<9;i++)
    {
    	JLabel tmp = new JLabel();
    	PaneleEkwipunku.add(tmp);
    	PaneleEkwipunku.get(i).addMouseListener(this);
    	setLabelImages(i);
    	add(PaneleEkwipunku.get(i));
    }
    
    
    
}



public ImageIcon getEqupmentIcon(int i,int x,int y)
{
	BufferedImage img;
    img  =scaleImage(x, y,"src/images/Ekwipunek/ekw("+i+").jpg");
    ImageIcon icon=new ImageIcon((Image) img);
	return icon;
}
public void setLabelImages(int i)
{

    
switch(i)
{
case 0:
	
	PaneleEkwipunku.get(i).setLayout(new GridLayout(8, 1));
	PaneleEkwipunku.get(i).setFont(new Font("", Font.ITALIC, 20));
	PaneleEkwipunku.get(i).setText(PlayerName+"\n LVL "+PlayerLVL);
	

	break;
case 1:
	PaneleEkwipunku.get(i).setIcon( getEqupmentIcon(i+1,150,250));
	break;
case 2:
	PaneleEkwipunku.get(i).setIcon(getEqupmentIcon(i+1,150,250));
	break;
case 3:
	PaneleEkwipunku.get(i).setIcon(getEqupmentIcon(i+1,150,250));
	break;
case 4:
	PaneleEkwipunku.get(i).setIcon(getEqupmentIcon(i+1,150,250));
	break;
case 5:
	break;
case 6:
	break;
case 7:
	PaneleEkwipunku.get(i).setIcon(getEqupmentIcon(i+1,150,250));
	break;
case 8:
	PaneleEkwipunku.get(i).setIcon(getEqupmentIcon(i+1,150,250));
	break;
default:
	break;
	

}
}
public static objCharacterWindow getInstance(MunchkinWindow Window,int index) {
    if (myInstance == null)
        myInstance = new objCharacterWindow(Window,index);
        
    return myInstance;
}    
	public BufferedImage scaleImage(int WIDTH, int HEIGHT, String filename) {
        BufferedImage bi = null;
        ImageIcon ii ;
        ii=null;
        try {
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
	@Override
	public void mouseClicked(MouseEvent e) {
		
		if (e.getButton() == MouseEvent.BUTTON1)
		{

			int x = e.getX();
			int y = e.getY();
			System.out.println("X "+x+" Y "+y);
			for(int i =0;i<9;i++)
{
			if (e.getSource() == PaneleEkwipunku.get(i)) //Check if the menu or menu items were clicked
			{
				switch(i)
				{
				case 0:
					System.out.println(i);
					break;
				case 1:
					System.out.println(i);
					setVisible(false);
					okienko.ShowEquipment("Headgear", Player.getPlayerId());
					okienko.setVisible(true);
					break;
				case 2:
					System.out.println(i);
					setVisible(false);
					okienko.ShowEquipment("Class", Player.getPlayerId());
					okienko.setVisible(true);
					break;
				case 3:
					System.out.println(i);
					setVisible(false);
					okienko.ShowEquipment("Weapon", Player.getPlayerId());
					okienko.setVisible(true);
					break;
				case 4:
					System.out.println(i);
					setVisible(false);
					okienko.ShowEquipment("Armor", Player.getPlayerId());
					okienko.setVisible(true);
					break;
				case 5:
					System.out.println(i);
					setVisible(false);
					okienko.setVisible(true);
					
					break;
				case 6:
					System.out.println(i);
					setVisible(false);
					okienko.setVisible(true);
					break;
				case 7:
					System.out.println(i);
					setVisible(false);
					okienko.ShowEquipment("Footgear", Player.getPlayerId());
					okienko.setVisible(true);
					break;
				case 8:
					System.out.println(i);
					setVisible(false);
					okienko.ShowEquipment("Backpack", Player.getPlayerId());
					okienko.setVisible(true);
					break;
				default:
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
	public MunchkinWindow getMainWindow()
	{
		return MainWindow;
	}
    public void setPlyer(int PlayerId)
    {
    System.out.println(PlayerId);
	Player=MainWindow.getLogic().getPlayer(PlayerId-1);
	PlayerName=Player.getName();
	PlayerLVL=Player.getLevel();
	PaneleEkwipunku.get(0).setLayout(new GridLayout(8, 1));
	PaneleEkwipunku.get(0).setFont(new Font("", Font.ITALIC, 20));
	PaneleEkwipunku.get(0).setText("Player "+PlayerName+"\n LVL "+PlayerLVL);
	
    };
}
