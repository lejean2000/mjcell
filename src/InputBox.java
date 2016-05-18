// Mirek's Java Cellebration
// http://www.mirwoj.opus.chelm.pl
//
// General-purpose single line input box

import java.awt.*;
import java.awt.event.*;

public class InputBox extends Dialog implements ActionListener
{
  boolean isAccepted = false;
  Button btnOk, btnCcl;
  TextField txtFld;

  InputBox(Frame frame, String sDeafult, String sTitle, String sPrompt)
  {
    super(frame, sTitle, true);
    enableEvents(Event.WINDOW_DESTROY);
    setLayout(new BorderLayout());
    txtFld = new TextField(sDeafult);
    btnOk = new Button("  Ok  ");
    
    txtFld.addKeyListener(new KeyListener() {
		
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_ENTER){
            	isAccepted = true;
            	dispose();
            }   
		}
	});
    
    
    add("West", new Panel()); // some margins
    add("East", new Panel());
    add("North", new Label(sPrompt));
    add("Center", txtFld);

    Panel pnlButtons = new Panel();
    pnlButtons.setLayout(new FlowLayout());
    pnlButtons.add(btnOk);
    btnOk.addActionListener(this); 
    pnlButtons.add(btnCcl = new Button("Cancel"));
    btnCcl.addActionListener(this);
    add("South", pnlButtons);

    Dimension d = getToolkit().getScreenSize();
    setLocation(d.width/4, d.height/3);
    pack();
    setVisible(true);
  }

  public void actionPerformed(ActionEvent ae)
  {
    if (ae.getSource() == btnOk)
    {
      isAccepted = true;
      setVisible(false);
    }
    else if (ae.getSource() == btnCcl)
    {
      setVisible(false);
    }
  }

  public void processEvent(AWTEvent e)
  {
	if ( e.getID() == Event.WINDOW_DESTROY )
      dispose();
       
    super.processEvent(e);
  }
}
