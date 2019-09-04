/**  Implementing a deque by using a circular doubly linked list data structure.
 *  Some invariants:
 *  The first node in the list is always sentinel.next.
 *  The last node in the list is always sentinel.prev.
 *  If a linked list deque is empty, then the prev and
 *  next fields of the sentinel point at the sentinel itself.
 */
public class LinkedListDeque<T> {
    private class IntNode {
        private T item;
        private IntNode prev;
        private IntNode next;
        public IntNode(IntNode p, T i, IntNode n) {
            prev = p;
            item = i;
            next = n;
        }
        public IntNode(T i) {
            prev = this;
            item = i;
            next = this;
        }
    }
    private int size = 0;
    private IntNode sentinel;

    /** Creates an empty deque. */
    public LinkedListDeque() {
        // Creates the circular IntNode by pointing the prev and next fields at the sentinel itself.
        sentinel = new IntNode(null);
    }

    /** Creates a deep copy of other. */
    public LinkedListDeque(LinkedListDeque other) {
        sentinel = new IntNode(null);
        IntNode pointer = other.sentinel;

        while (pointer.next != other.sentinel) {
            this.addLast(pointer.next.item);
            pointer = pointer.next;
        }
    }

    /** Adds an item to the front of the deque. */
    public void addFirst(T item) {
        IntNode moveToFront = new IntNode(sentinel, item, sentinel.next);
        // Special case for adding onto an empty list.
        if (sentinel.prev.equals(sentinel)) {
            sentinel.prev = moveToFront;
        } else {
            // Set the former first node's prev field to point at the added node.
            sentinel.next.prev = moveToFront;
        }
        sentinel.next = moveToFront;
        size += 1;
    }

    /** Adds an item to the back of the deque. */
    public void addLast(T item) {
        IntNode moveToLast = new IntNode(sentinel.prev, item, sentinel);
        // Special case for adding onto an empty list.
        if (sentinel.next.equals(sentinel)) {
            sentinel.next = moveToLast;
        }
        // Set the former last node's next field to point at the added node.
        sentinel.prev.next = moveToLast;
        // Set the prev field of the sentinel to point at the added node.
        sentinel.prev = moveToLast;
        size += 1;
    }

    /** Returns true if deque is empty (size is 0), false otherwise. */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Returns the number of items in the deque. */
    public int size() {
        return size;
    }

    /**
        Removes and returns the item at the front of the Deque.
        If no such item exists, returns null.
    */
    public T removeFirst() {
        if (sentinel.next.equals(sentinel)) {
            return null;
        } else {
            IntNode itemToRemove = sentinel.next;
            // next field of sentinel points at the node after the removed item.
            IntNode moveToFront = itemToRemove.next;
            sentinel.next = moveToFront;
            moveToFront.prev = sentinel; // prev field of the new front item points at the sentinel
            itemToRemove.prev = null; // remove the pointer to the sentinel
            itemToRemove.next = null; // remove the pointer to the next node
            size -= 1;
            return itemToRemove.item;
        }
    }

    /**
        Removes and returns the item at the back of the deque.
        If no such item exists, returns null.
    */
    public T removeLast() {
        if (sentinel.prev.equals(sentinel)) {
            return null;
        } else {
            IntNode itemToRemove = sentinel.prev;
            IntNode moveToLast = itemToRemove.prev;
            sentinel.prev = moveToLast; // Set the second-to-last node as the last node.
            moveToLast.next = sentinel;
            itemToRemove.prev = null;
            itemToRemove.next = null;
            size -= 1;
            return itemToRemove.item;
        }
    }

    /** Prints the items in the deque from the front to the rear, separated by spaces. */
    public void printDeque() {
        IntNode pointer = sentinel.next;
        while (!pointer.equals(sentinel)) {
            System.out.print(pointer.item + " ");
            pointer = pointer.next;
        }
        System.out.print("\n");
    }

    /** Gets the item at the given index, where 0 is the front,
     * 1 is the next item, and thereafter. */
    public T get(int index) {
        IntNode pointer = sentinel.next;
        if (index >= size) {
            return null;
        }
        while (index > 0) {
            pointer = pointer.next;
            index -= 1;
        }
        return pointer.item;
    }

    private T getRecursiveHelper(IntNode lst, int index) {
        if (index == 0) {
            return lst.item;
        } else if (index >= size) {
            return null;
        } else {
            return getRecursiveHelper(lst.next, index - 1);
        }
    }
    
    public T getRecursive(int index) {
        return getRecursiveHelper(sentinel.next, index);
    }
}
