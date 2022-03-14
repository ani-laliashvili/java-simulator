import java.util.NoSuchElementException;

/**
* This class creates a deque using a circular array.  
*
* @author Ani Laliashvili @author Sarah Gregory
*/

public class CircularArrayDequeImplementation<T> implements Deque<T> {
    public T[] items; // circular array
    public int frontIndex;
    public int backIndex;
    
    public CircularArrayDequeImplementation() {
        @SuppressWarnings("unchecked")
        T[] tmp = (T[]) new Object[10];
        items = tmp;
        frontIndex = 0;
        backIndex = 9;
    } // end constructor
    
    /** 
     * Adds an item to the front of this deque
     * @param item The item to add.
     */
    public void addFront(T item){
        ensureCapacity();
        frontIndex = (frontIndex + items.length - 1) % items.length;
        items[frontIndex] = item;
    }
    
    /** 
     * Removes the item from the front of this deque, and returns it.
     * Throws a NoSuchElementException if the deque is empty
     * @return the item at the top of the deque
     */
    public T removeFront(){
        T item;
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else { 
            item = items[frontIndex];
            frontIndex = (frontIndex + 1) % items.length;
        }
        return item;
    }
    
    /** 
     * Adds an item to the back of this deque
     * @param item The item to add.
     */
    public void addBack(T item){
        ensureCapacity();
        backIndex = (backIndex + 1) % items.length;
        items[backIndex] = item;
    }
    
    /** 
     * Removes the item from the back of this deque, and returns it.
     * Throws a NoSuchElementException if the deque is empty
     * @return the item at the top of the deque
     */
    public T removeBack(){
        T item;
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else { 
            item = items[backIndex];
            backIndex = (backIndex + items.length -1) % items.length;
        } 
        return item;
    }
    
    /** 
     * Returns the item at the front of the deque, without removing it.
     * Throws a NoSuchElementException if the deque is empty
     * @return the item at the top of the deque
     */
    public T peekFront(){
        if (isEmpty()) {
            throw new NoSuchElementException();
        } 
        return items[frontIndex];
    }
    
    /** 
     * Returns the item at the front of the deque, without removing it.
     * Throws a NoSuchElementException if the deque is empty
     * @return the item at the top of the deque
     */
    public T peekBack(){
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return items[backIndex];
    }
    
    /** 
    * Returns true if the deque is empty. 
    */
    public boolean isEmpty(){
        return frontIndex == ((backIndex + 1) % items.length);
    }

    /** 
    * Returns true if the deque is full 
    */
    public boolean isFull(){
        return frontIndex == ((backIndex + 2) % items.length);
    }
    
    /** 
    * Checks if the deque is full and doubles it in size if it is.
    */
    public void ensureCapacity(){
      if (isFull()) {
        int oldSize = items.length;
        int newSize = 2*oldSize;
        @SuppressWarnings("unchecked")
        T[] tmp = (T[]) new Object[newSize];

        for (int i = 0; i < oldSize - 1; i++){
          tmp[i] = items[frontIndex];
          frontIndex = (frontIndex + 1) % items.length;
        }
        backIndex = oldSize - 2;
        frontIndex = 0;
          items = tmp;
      }
    }
    
    /** 
    * Removes all items from the deque. 
    */
    public void clear(){
        frontIndex = 0;
        backIndex = items.length - 1;
        for (int i = 0; i < items.length; i++){
            items[i] = null;
        }
    }

    /** 
    * Returns deque as a String.
    * @return arrayString String version of the deque
    */
    public String toString(){
        String arrayString;
        if (isEmpty()){
            arrayString = "frontIndex is: " + frontIndex + ", backIndex is: "+ backIndex + ", The array is Empty.";
        } else {
            arrayString = "frontIndex is: " + frontIndex + ", backIndex is: " + backIndex + ", Deque elements: ";
            for (int front = frontIndex; front != (backIndex+1)%items.length; front = (front + 1) % items.length) {
                arrayString = arrayString + items[front];
                if (front != (backIndex+1)%items.length - 1){
                    arrayString = arrayString + ", ";
                }
            }
        }
        System.out.println(arrayString);
        return arrayString;
    }
}
