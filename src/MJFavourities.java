// Mirek's Java Cellebration
// http://www.mirwoj.opus.chelm.pl
//
// Favourite patterns list

import java.awt.*;
import java.awt.List;
import java.awt.event.*;
import java.util.*;
//import java.io.*;

public class MJFavourities extends Dialog implements ActionListener
{
  private Button   btnLoad, btnCcl;
  private Label    lblPrompt;
  private List     LstFiles = new List(10, false);
  private MJCellUI mjUI;

  // ----------------------------------------------------------------
  // Constructor
  MJFavourities(Frame frame, MJCellUI mjui)
  {
    super(frame, "Favourite patterns", false);
    enableEvents(Event.WINDOW_DESTROY);
    mjUI = mjui;
    setLayout(new BorderLayout());
    lblPrompt = new Label("Select the pattern:");
    add("North", lblPrompt);
    add("Center", LstFiles);

    Panel pnlButtons = new Panel();
    pnlButtons.setLayout(new FlowLayout());
    pnlButtons.add(btnLoad = new Button(" Load "));
    btnLoad.addActionListener(this); 
    pnlButtons.add(btnCcl = new Button("Cancel"));
    btnCcl.addActionListener(this);
    add("South", pnlButtons);

    Dimension d = getToolkit().getScreenSize();
    setLocation(d.width/4, d.height/3);
    setSize(d.width/6, d.height/3);
    setVisible(false);
    InitList();
    pack();
  }
  // ----------------------------------------------------------------
  // Fill the list with patterns from the current rule
  public void InitList()
  {
    MJTools  mjT;
    Vector<String>   vLines;
    int      i;

    LstFiles.removeAll(); // no items

    vLines = new Vector<String>();
    mjT = new MJTools();
    if (mjT.LoadResTextFile("fav.txt", vLines))
    {
      for (i = 0; i < vLines.size(); i++)
      {
        if (!((String)vLines.elementAt(i)).startsWith("//"))
          LstFiles.add((String)vLines.elementAt(i));
      }
    }
  }
  // ----------------------------------------------------------------
  // Load the currently selected pattern
  private void LoadCurrentPattern()
  {
    String sGameName = "";
    String sRuleName = "";
    String sPattName = "";

    if (LstFiles.getSelectedIndex() >= 0)
    {
      String sItem = LstFiles.getSelectedItem();
      int whereSlash = sItem.lastIndexOf('/');
      if (whereSlash > 0)
      {
        sPattName = sItem.substring(whereSlash + 1); // part after '/'

        sItem = sItem.substring(0, whereSlash); // part before '/'
        int whereSep = sItem.lastIndexOf('|');
        if (whereSep > 0)
        {
          sGameName = sItem.substring(0, whereSep); // part before '|'
          sRuleName = sItem.substring(whereSep + 1); // part after '|'

          sGameName = mjUI.mjr.GetGameName(mjUI.mjr.GetGameIndex(sGameName));
          mjUI.ActivateGame(sGameName);
          mjUI.ActivateRule(sRuleName);
        }
      }

      if ((sGameName.length() > 0) && (sRuleName.length() > 0))
      {
        lblPrompt.setText("Please wait...");
        try
        {
          mjUI.mjo.OpenFile(sGameName + "/" + sRuleName + "/" + sPattName);
          lblPrompt.setText("Select the pattern:");
        }
        catch(Exception exc)
        {
          lblPrompt.setText("Error loading pattern!");
        }
      }
    }
  }
  // ----------------------------------------------------------------
  public void actionPerformed(ActionEvent ae)
  {
    if (ae.getSource() == btnLoad)
    {
      LoadCurrentPattern();
    }
    else if (ae.getSource() == btnCcl)
    {
      setVisible(false);
    }
  }
  // ----------------------------------------------------------------
  public boolean action (Event evt, Object arg)
  {
    if (evt.target.equals(LstFiles))
    {
      LoadCurrentPattern();
    }
    else 
      return super.action(evt, arg);
    return true;
  }
  // ----------------------------------------------------------------
  public void processEvent(AWTEvent e)
  {
	if ( e.getID() == Event.WINDOW_DESTROY )
      setVisible(false);
    else 
      super.processEvent(e);
  }
  // ----------------------------------------------------------------

}
