import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.util.List;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

/**********************
 * Class SimulationComponent is a component that handles the graphics
 * of a Java Virtual Machine Simulator
 * @author Andy
 * @author Anna (modified Winter 2020)
 */
public class SimulationComponent extends JComponent {

  private static final long serialVersionUID = 1L;
  private Vector<String> gProgNames;
  private Program gCurrentProgram;
  private File gCurrentProgramFile;
  private boolean gProgramRunning;
  private GraphicsComponent gGraphicsComp;
  private JList<String> gProgMethodList;
  private JLabel gPrognameLabel;
  private JTextArea gStatusArea;

  /***********
   * Constructor - initializes private attributes and sets up all graphics stuff
   */
  public SimulationComponent() {
    
    // add graphics component
    this.setLayout(new BorderLayout());

    this.setMinimumSize(new Dimension(600, 400));
    this.setPreferredSize(new Dimension(600,400));
    
    // simple panel to hold memory stuff
    JPanel tMemPanel = new JPanel();
    tMemPanel.setLayout(new BorderLayout());
    tMemPanel.setMinimumSize(new Dimension(150, 400));
    tMemPanel.setPreferredSize(new Dimension(150, 400));
  
    JLabel tStackLabel = new JLabel("VM Memory", SwingConstants.CENTER);
    tStackLabel.setMinimumSize(new Dimension(100, 20));
    tStackLabel.setPreferredSize(new Dimension(100, 20));
    tMemPanel.add(tStackLabel, BorderLayout.NORTH);

    gGraphicsComp = new GraphicsComponent();
    tMemPanel.add(gGraphicsComp, BorderLayout.SOUTH);

    this.add(tMemPanel, BorderLayout.WEST);
  
    // simple panel to hold program list.
    JPanel tOtherPanel = new JPanel();
    tOtherPanel.setMinimumSize(new Dimension(450, 400));
    tOtherPanel.setPreferredSize(new Dimension(450, 400));
    tOtherPanel.setLayout(new BorderLayout());
  
    
    // simple panel to hold program list.
    JPanel tListPanel = new JPanel();
    tListPanel.setMinimumSize(new Dimension(450, 150));
    tListPanel.setPreferredSize(new Dimension(450, 150));
    tListPanel.setLayout(new BorderLayout());

    JPanel tLabelPanel = new JPanel();
    tLabelPanel.setMinimumSize(new Dimension(450, 50));
    tLabelPanel.setPreferredSize(new Dimension(450, 50));
    tLabelPanel.setLayout(new BorderLayout());
    tListPanel.add(tLabelPanel, BorderLayout.NORTH);

    gPrognameLabel = new JLabel("Current Program");
    gPrognameLabel.setMinimumSize(new Dimension(450, 20));
    gPrognameLabel.setPreferredSize(new Dimension(450, 20));
    tLabelPanel.add(gPrognameLabel, BorderLayout.NORTH);

    JLabel tMethodLabel = new JLabel("Methods");
    tMethodLabel.setMinimumSize(new Dimension(450, 20));
    tMethodLabel.setPreferredSize(new Dimension(450, 20));
    tLabelPanel.add(tMethodLabel, BorderLayout.SOUTH);

    // add list component
    gProgMethodList = new JList<String>();
    gProgMethodList.setMinimumSize(new Dimension(450, 100));
    gProgMethodList.setPreferredSize(new Dimension(450, 100));
    gProgMethodList.setEnabled(false);    
    tListPanel.add(gProgMethodList, BorderLayout.SOUTH);

    tOtherPanel.add(tListPanel, BorderLayout.NORTH);
    
    // simple panel to hold status info
    JPanel tStatusPanel = new JPanel();
    tStatusPanel.setMinimumSize(new Dimension(450,200));
    tStatusPanel.setPreferredSize(new Dimension(450,200));
    tStatusPanel.setLayout(new BorderLayout());
    
    JLabel tStatusLabel = new JLabel("Status Information");
    tStatusLabel.setMinimumSize(new Dimension(450, 20));
    tStatusLabel.setPreferredSize(new Dimension(450, 20));
    tStatusPanel.add(tStatusLabel, BorderLayout.NORTH);
  
    gStatusArea = new JTextArea(7, 40);
    gStatusArea.setEditable(false);

    JScrollPane tTextPane = new JScrollPane(gStatusArea);
    tTextPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    tTextPane.setMinimumSize(new Dimension(450, 180));
    tTextPane.setPreferredSize(new Dimension(450, 180));
    
    tStatusPanel.add(tTextPane, BorderLayout.SOUTH);
    
    tOtherPanel.add(tStatusPanel, BorderLayout.SOUTH);
    
    this.add(tOtherPanel, BorderLayout.EAST);
    
    gProgNames = new Vector<String>();
    
  }
  
  /*******************
   * step method - called every time MainWindow's timer ticks
   */
  public void step() {
    gProgramRunning = true;
    if(gCurrentProgram == null) {
      return;
    } else if(gCurrentProgram.isFinished()) {
      gProgramRunning = false;
    } else {
      gProgramRunning = true;
      gCurrentProgram.step(this);
    }
    gGraphicsComp.repaint();
  }
  
  /***************
   * reset method: clears the status of the current program
   */
  public void reset() {
      addProgram(gCurrentProgramFile);
  }

  
  /***************
   * Add a program to the SimulationComponent from the given file.
   * If the file doesn't load properly, it isn't added
   * @param tFile the file to read
   */
  public void addProgram(File tFile) { 
    Program tProg = null;
    try {
      // attempt to instantiate the program from the file
      tProg = new Program(tFile, this);
      gCurrentProgram = tProg;
      gPrognameLabel.setText(tProg.getName());
      gGraphicsComp.clearMethods();
      List<Method> methods = tProg.getMethodList();
      String[] arr = new String[methods.size()];
      for(int i = 0; i < arr.length; i++) {
        arr[i] = methods.get(i).getName();
      }
      gProgMethodList.setListData(arr);
      gCurrentProgramFile = tFile;
    } catch (SyntaxErrorException e) {
      gStatusArea.append("Syntax error in " + tFile.getName() + ":\n");
      gStatusArea.append(e.getMessage() + "\n");
      gStatusArea.append("Program not loaded.\n");
    }
    
    gGraphicsComp.repaint();
  }
  
  /*************
   * Adds the given method to the graphical stack representation
   * @param mMethod the method to add
   */
  public void addMethodToGraphicalStack(Method mMethod) {
    gGraphicsComp.addMethod(mMethod);
  }
  
  /**************
   * Attempts to remove the given method from the graphical stack representation
   * @param mMethod the method to remove
   */
  public void removeMethodFromGraphicalStack(Method mMethod) {
    gGraphicsComp.removeMethod(mMethod);
  }
  
  /****************
   * Appends the given string to the status text area. This method also adds a 
   * newline ('\n') to the String given
   * @param str the string to append
   */
  public void addStatusMessage(String str) {
    gStatusArea.append(str + "\n");
  }
  
  /****************
   * Returns true if a program is currently being run by the simulator
   * @return true if a program is currently being run by the simulator
   */
  public boolean isProgramRunning() { return gProgramRunning; }

  /****************
   * Orders the simulation to halt
   */
  public void halt() { gProgramRunning = false; }
}
