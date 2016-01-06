/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 



import javax.swing.*;
import javax.swing.border.*;
import javax.accessibility.*;

import java.awt.*;
import java.awt.event.*;

/* 
 * LayeredPaneDemo.java requires
 * images/dukeWaveRed.gif. 
 */
public class objLayeredPanel extends JPanel
  {
    private String[] layerStrings = { "Yellow (0)", "Magenta (1)",
                                      "Cyan (2)",   "Red (3)",
                                      "Green (4)" };
    private Color[] layerColors = { Color.yellow, Color.magenta,
                                    Color.cyan,   Color.red,
                                    Color.green };

    private JLayeredPane layeredPane;
    private JLabel dukeLabel;
    private JCheckBox onTop;
    private JComboBox layerList;

    //Action commands
    private static String ON_TOP_COMMAND = "ontop";
    private static String LAYER_COMMAND = "layer";

    //Adjustments to put Duke's toe at the cursor's tip.
    private static final int XFUDGE = 40;
    private static final int YFUDGE = 57;

    public objLayeredPanel()    {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        //Create and load the duke icon.

        //Create and set up the layered pane.
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(300, 310));
        layeredPane.setBorder(BorderFactory.createTitledBorder(
                                    "Move the Mouse to Move Duke"));
        

       
        add(layeredPane);
    }


 


}
