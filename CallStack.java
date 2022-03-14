/**
* This class simulates memory.  
*
* @author Ani Laliashvili
*/

public class CallStack extends CircularArrayDequeImplementation<Method> {
    public SimulationComponent simComp;
    
    public CallStack(SimulationComponent simulationComponent){
        simComp = simulationComponent;
    } // end constructor
    
    /**
    * Adds method to front and adds it to the simulation component.
    */
    @Override
    public void addFront(Method m){
        super.addFront(m);
        simComp.addMethodToGraphicalStack(m);
    }
    
    /**
    * Removes method from the front and removes it from the simulation component.
    * @return m removed method
    */
    @Override
    public Method removeFront(){
        Method m = super.removeFront();
        simComp.removeMethodFromGraphicalStack(m);
        return m;
    }
    
    
    /** 
     * Throws an UnsupportedOperationException.
     */
    @Override
    public Method peekBack(){
        throw new UnsupportedOperationException();
    }
    
    /** 
     * Throws an UnsupportedOperationException.
     */
    @Override
    public void addBack(Method m){
        throw new UnsupportedOperationException();
    }
    
    /** 
     * Throws an UnsupportedOperationException.
     */
    @Override
    public Method removeBack(){
        throw new UnsupportedOperationException();
    }  
}
