/** 
 * An interface for the Deque ADT.
 * 
 * @param <T> The type of data that the deque stores.
 * 
 * @author Anna Rafferty
 */
public interface Deque<T> {
    /** Adds an item to the front of this deque
     * @param item The item to add.
     */
    public void addFront(T item);
    
    /** Removes the item from the front of this deque, and returns it.
     * Throws a NoSuchElementException if the deque is empty
     * @return the item at the top of the deque
     */
    public T removeFront();
    
    /** Adds an item to the back of this deque
     * @param item The item to add.
     */
    public void addBack(T item);
    
    /** Removes the item from the back of this deque, and returns it.
     * Throws a NoSuchElementException if the deque is empty
     * @return the item at the top of the deque
     */
    public T removeBack();
    
    /** Returns the item at the front of the deque, without removing it.
     * Throws a NoSuchElementException if the deque is empty
     * @return the item at the top of the deque
     */
    public T peekFront();
    
    /** Returns the item at the front of the deque, without removing it.
     * Throws a NoSuchElementException if the deque is empty
     * @return the item at the top of the deque
     */
    public T peekBack();
    
    /** Returns true if the deque is empty. */
    public boolean isEmpty();
    
    /** Removes all items from the deque. */
    public void clear();
}
