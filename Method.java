import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/***********************
 * Method - A class to represent a Java method within 
 * VM simulator
 * 
 * @author Andy
 * 
 */
public class Method {

  private Deque<Method> callStack;
  private String name;             // the name of this Method
  private int gSize;              // size of this method (in bytes)
  private int ticks;              // counter to check how many times this method has had step() called.
  private List<String> lineList;        // list of text lines in this method
  private Program gParent;          // program that this method belongs to

  /***************
   * Constructor 
   * @param mName This method's name
   * @param mProg The program this method belongs to
   */
  public Method(String mName, Program mProg) {
    gParent = mProg;
    name = mName;
    gSize = 16;
    lineList = new LinkedList<String>();
    ticks = 0;
  }
  
  /*************
   * copy returns a copy of this method, usually used to run separate 
   * instances of methods
   * @return the copy
   */
  public Method copy() {
    Method ret = new Method(this.name, this.gParent);
    ret.gSize = gSize;
    ret.ticks = 0;
    ret.lineList.addAll(this.lineList);
    return ret;
  }
  
  /*************
   * Gets the size of this method, in virtual bytes.
   * Size is related to number of lines in the method.
   * @return the size
   */
  public int getSize() { return gSize; }
  
  /*************
   * Gets the name of this method
   * @return the name
   */
  public String getName() { return name; }
  
  /*************
   * Adds the given line to this method
   * usually used when constructing methods
   * @param mLine the line to add
   */
  public void addLine(String mLine) { lineList.add(mLine); gSize += 4; }
  
  /*************
   * Returns whether this method is finished executing or not
   * @return true if finished
   */
  public boolean isFinished() { return ticks >= lineList.size(); }
  
  /*************
   * Sets the call stack that this method uses
   * @param the call stack to use
   */
  public void setStack(Deque<Method> mStack) { callStack = mStack; }
  
  /*************
   * Executes one step of this method, using the given SimulationComponent to 
   * display things
   * @param mComp the SimulationComponent in which to display
   */
  public void step(SimulationComponent mComp) {
    String tLine = lineList.get(ticks);
    tick();
    Pattern tPat1 = Pattern.compile("\\s*print\\s*\"(.*)\".*");
    Matcher tM1 = tPat1.matcher(tLine);
    if(tM1.matches()) {
      mComp.addStatusMessage(name + " output : " + tM1.group(1));
    } else {
      mComp.addStatusMessage(name + " : " + tLine.trim());
    }
    Pattern tPat2 = Pattern.compile("\\s*(\\S*)\\s*\\(.*?\\).*");
    Matcher tM2 = tPat2.matcher(tLine);
    if(tM2.matches()) {
      Method m = gParent.getMethod(tM2.group(1));
      if(m != null && callStack != null) {
        callStack.addFront(m);
      }
    }
    if(ticks > lineList.size()) {
      System.err.println(name + " should have exited by now...");
    }
  }
  
  private void tick() { ticks++; }
}
