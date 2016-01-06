import javax.swing.*;

public class objCardWindow extends JFrame {
    private static objCardWindow myInstance;

    // the constructor
    private objCardWindow() {
        this.setSize(400, 100);

        this.setTitle("Singleton Frame. Timestamp:" +
            System.currentTimeMillis());

        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    public static objCardWindow getInstance() {
        if (myInstance == null)
            myInstance = new objCardWindow();

        return myInstance;
    }
    
    
    

}