
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class ComplexDialogPanel extends JPanel {

	private static final long serialVersionUID = 1L;
public static final String[] LABEL_TEXTS = { "Player 1",  "Player 2",
		 "Player 3",  "Player 4" };
   public static final int COLS = 4;
   private Map<String, JTextField> labelFieldMap = new HashMap<>();

   public ComplexDialogPanel() {
      setLayout(new GridBagLayout());
      for (int i = 0; i < LABEL_TEXTS.length; i++) {
         String labelTxt = LABEL_TEXTS[i];
         add(new JLabel(labelTxt), createGbc(0, i));

         JTextField textField = new JTextField(COLS);
         labelFieldMap.put(labelTxt, textField);
         setBackground(new Color(255, 214, 158));
         add(textField, createGbc(1, i));
      }

      setBorder(BorderFactory.createTitledBorder("Wpisz nazwê graczy"));
   }

   public String getText(String labelText) {
      JTextField textField = labelFieldMap.get(labelText);
      if (textField != null) {
         return textField.getText();
      } else {
         throw new IllegalArgumentException(labelText);
      }
   }

   public static GridBagConstraints createGbc(int x, int y) {
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.gridx = x;
      gbc.gridy = y;
      gbc.weightx = 1.0;
      gbc.weighty = gbc.weightx;
      if (x == 0) {
         gbc.anchor = GridBagConstraints.LINE_START;
         gbc.fill = GridBagConstraints.BOTH;
         gbc.insets = new Insets(3, 3, 3, 8);
      } else {
         gbc.anchor = GridBagConstraints.LINE_END;
         gbc.fill = GridBagConstraints.HORIZONTAL;
         gbc.insets = new Insets(3, 3, 3, 3);
      }
      return gbc;
   }

   public static  ArrayList<String> createAndShowGui() {
	   ArrayList<String> listaGraczy=new ArrayList<String>();
      ComplexDialogPanel mainPanel = new ComplexDialogPanel();
     mainPanel.setBackground(new Color(255, 214, 158));
      int optionType = JOptionPane.DEFAULT_OPTION;
      int messageType = JOptionPane.PLAIN_MESSAGE;
      Icon icon = null;
      String[] options = { "Submit", "Cancel" };
      Object initialValue = options[0];
      int reply = JOptionPane.showOptionDialog(null, mainPanel,
            "Nazwy graczy", optionType, messageType, icon, options,
            initialValue);
      if (reply == 0) {
         //System.out.println("Selections:");
        
         for (String labelText : LABEL_TEXTS) {
          //  System.out.printf("%12s: %s%n", labelText,
            //      mainPanel.getText(labelText));
            listaGraczy.add(mainPanel.getText(labelText));
         }

      }
      return listaGraczy;
   }

   
}
