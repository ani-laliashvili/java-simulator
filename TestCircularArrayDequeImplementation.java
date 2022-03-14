import java.util.NoSuchElementException;

/**
 * Testing code for a circular array deque implementation.
 * If all tests pass, nothing is printed. If a test fails, a
 * message is printed to narrow down the cause of the errors.
 * You are welcome (and encouraged!) to modify this code to
 * suit your needs.
 * @author Anna Rafferty
 */
public class TestCircularArrayDequeImplementation {
    
    /**
     * main takes no command line arguments and goes through a series of tests
     * for addFront, addBack, peekFront, peekBack, clear, and isEmpty. How well it  
     * tests is partially dependent on your initial array size.
     * 
     * I encourage you to read through the code to understand what it test, and 
     * to modify maxValue to make sure you're hitting relevant possible edge cases in 
     * your implementation. Draw out the deque on paper to make sure you understand
     * what's happening. You can add a toString implement to your 
     * CircularArrayDequeImplementation if you want it to print nicely. This
     * may help with debugging.
     */
    public static void main(String[] args) {
        Deque<Integer> deque = new CircularArrayDequeImplementation<Integer>();
        
        //First, try adding some items to the front of the deque
        int maxValue = 20;
        for(int i = 0; i < maxValue; i++) {
            deque.addFront(i);
            if(deque.peekFront() != i) {
                System.out.println("Just added " + i + " to front, but peek is " + deque.peekFront());
            }
            if(deque.peekBack() != 0) {
                System.out.println("0 should be back of queue, but it's " + deque.peekBack());
            }
        }
        
        //Now, clear it and make sure it's empty
        deque.clear();
        if(!deque.isEmpty()) {
            System.out.println("isEmpty() returns false directly after deque is cleared.");
        }
        //We'll try clearing one more time since we should be able to clear an empty deque
        deque.clear();
        if(!deque.isEmpty()) {
            System.out.println("isEmpty() returns false directly after deque is cleared.");
        }
        
        //We'll also check that we throw the right exceptions for an empty deque.
        try {
            deque.peekFront();
            System.out.println("Called peekFront on an empty stack, but no exception was thrown");
        } catch(NoSuchElementException e) {
            ;//Nothing to do - we want an exception when peeking an empty stack
        }
        
        try {
            deque.peekBack();
            System.out.println("Called peekBack on an empty stack, but no exception was thrown");
        } catch(NoSuchElementException e) {
            ;//Nothing to do - we want an exception when peeking an empty stack
        }
        
        try {
            deque.removeFront();
            System.out.println("Called removeFront on an empty stack, but no exception was thrown");
        } catch(NoSuchElementException e) {
            ;//Nothing to do - we want an exception when peeking an empty stack
        }
        
        try {
            deque.removeBack();
            System.out.println("Called removeBack on an empty stack, but no exception was thrown");
        } catch(NoSuchElementException e) {
            ;//Nothing to do - we want an exception when peeking an empty stack
        }
        
        //Now, we'll start again, this time adding to the back
        deque = new CircularArrayDequeImplementation<Integer>();
        for(int i = 0; i < maxValue; i++) {
            deque.addBack(i);
            if(deque.peekBack() != i) {
                System.out.println("Just added " + i + " to front, but peek is " + deque.peekBack());
            }
            if(deque.peekFront() != 0) {
                System.out.println("0 should be front of queue, but it's " + deque.peekFront());
            }
        }


        //Now we'll remove everything from the front, checking that peekFront and peekBack return the correct values.
        for(int i = 0; i < maxValue; i++) {
            int removed = deque.removeFront();
            if(removed != i) {
                System.out.println("Just removed " + i + " from front, but actual removed value is " + removed);
            }
            if(i != maxValue - 1) {
                if(deque.peekFront() != i+1) {
                    System.out.println((i+1) + " should be front of queue, but it's " + deque.peekFront());
                }
                if(deque.peekBack() != maxValue - 1) {
                    System.out.println(maxValue-1 + " should be back of queue, but it's " + deque.peekBack());
                }
            }
        }

        //Deque should be empty
        if(!deque.isEmpty()) {
            System.out.println("Everything removed but deque not empty - peek front is " + deque.peekFront());
        }

        //Now intersperse some adding and removing from front and back
        //We'll add 0, -1, ... , -5 to back, then 1, ..., 5 to front, then -6 and -7 to back.
        int valuesInQueue = 0;//Keeping a count of the values in the queue so we know how many we should remove
        for(int i = 0; i > -6; i--) {
            deque.addBack(i);
            valuesInQueue++;
            if(deque.peekBack() != i) {
                System.out.println("Just added " + i + " to back, but peek is " + deque.peekBack());
            }
            if(deque.peekFront() != 0) {
                System.out.println("0 should be front of queue, but it's " + deque.peekFront());
            }
        }
        
        for(int i = 1; i < 6; i++) {
            deque.addFront(i);
            valuesInQueue++;
            if(deque.peekBack() != -5) {
                System.out.println("Back should remain at -5 but peek is " + deque.peekBack());
            }
            if(deque.peekFront() != i) {
                System.out.println("Just added " + i + " to back, but peek is " +deque.peekFront());
            }
        }
        
        for(int i = -6; i >= -7; i--) {
            deque.addBack(i);
            valuesInQueue++;
            if(deque.peekBack() != i) {
                System.out.println("Just added " + i + " to back, but peek is " + deque.peekBack());
            }
            if(deque.peekFront() != 5) {
                System.out.println("5 should be front of queue, but it's " + deque.peekFront());
            }
        }
        
        //Now we'll try removing, alternating between front and back
        int backValue = -7;
        int frontValue = 5;
        while(valuesInQueue >= 2) {//We remove two values every round
            int removed = deque.removeBack();
            valuesInQueue--;
            if(removed != backValue) {
                System.out.println("Should have removed " + backValue + " but actual removed value is " + removed);
            }
            backValue++;
            if(deque.peekBack() != backValue) {
                System.out.println("Back should be " + backValue + " but is " + deque.peekBack());
            }
            valuesInQueue--;
            removed = deque.removeFront();
            if(removed != frontValue) {
                System.out.println("Should have removed " + frontValue + " but actual removed value is " + removed);
            }
            frontValue--;
            if(deque.peekBack() != backValue) {
                System.out.println("Back should be " + backValue + " but is " + deque.peekBack());
            }
            if(deque.peekFront() != frontValue) {
                System.out.println("Front should be " + frontValue + " but is " + deque.peekFront());
            }
        }
    }

}
