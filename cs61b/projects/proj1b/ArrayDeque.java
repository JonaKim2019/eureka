/** Implementing a deque by using a circular array list data structure. */
public class ArrayDeque<T> implements Deque<T> {
    private T[] items;
    // first and last always have to contain some value after the first insertion
    private int first, last;
    private int size = 0;
    /** Creates an empty deque. */
    public ArrayDeque() {
        items = (T[]) new Object[8];
        first = items.length / 2;
        last = items.length / 2;
    }

    /** Create a deep copy of other */
    public ArrayDeque(ArrayDeque other) {
        this(); // calls defaults constructor
        other = (ArrayDeque<T>) other;
        int sizeCount = other.size();
        for (int i = 0; i < sizeCount; i++) {
            this.addLast((T) other.get(i));
        }
    }

    /** decrease and increase helper functions. */
    private static int decrease(int n, int length) {
        if (n == 0) {
            return n = length - 1;
        } else {
            return n = (n - 1) % length;
        }
    }

    private static int increase(int n, int length) {
        return n = (n + 1) % length;
    }

    /** Resizes the entire array. */
    private void resize(int capacity) {
        T[] resizedArray = (T[]) new Object[capacity];
        int j = 0;
        for (int i = this.first; i != this.last; i = ArrayDeque.increase(i, this.items.length)) {
            resizedArray[j] = this.items[i];
            j = ArrayDeque.increase(j, resizedArray.length);
        }
        resizedArray[j] = this.items[this.last]; // last
        this.first = 0;
        this.last = j;
        this.items = resizedArray;
    }

    /** Adds an item to the first of the deque. */
    public void addFirst(T i) {
        if (this.isEmpty()) {
            items[this.first] = i; // first and last should still be the same
            this.size += 1;
        } else {
            this.first = ArrayDeque.decrease(this.first, this.items.length);
            this.items[this.first] = i;
            this.size += 1;
            if (this.size == this.items.length) {
                this.resize(this.items.length * 2);
            }
        }
    }

    /** Adds an item to the last of the deque. */
    public void addLast(T i) {
        if (this.isEmpty()) {
            items[this.last] = i; // first and last should still be the same
            this.size += 1;
        } else {
            this.last = ArrayDeque.increase(this.last, this.items.length);
            this.items[this.last] = i;
            this.size += 1;
            if (this.size == this.items.length) {
                this.resize(this.items.length * 2);
            }
        }
    }

    /** Returns true if deque is empty (size = 0), false otherwise. */
    /**
     public boolean isEmpty() {
     return this.size == 0;
     }
     */

    /** Returns the number of items in the deque. */
    public int size() {
        return this.size;
    }

    /** Removes and returns the item at the first of the deque. */
    public T removeFirst() {
        if (this.isEmpty()) {
            return null;
        } else if (this.size == 1) {
            T toRemove = this.items[this.first];
            this.items[this.first] = null;
            this.size -= 1;
            return toRemove;
        } else {
            T toRemove = this.items[this.first];
            this.items[this.first] = null;
            this.first = ArrayDeque.increase(this.first, this.items.length);
            this.size -= 1;
            // Check the usage ratio (r) and shrink the array
            // by half if r < 0.25 and array length >= 16.
            double r = this.size / (double) this.items.length;
            if (r < 0.25 && this.items.length >= 16) {
                this.resize(this.items.length / 2);
            }
            return toRemove;
        }
    }

    /** Removes and returns the item at the last of the deque. */
    public T removeLast() {
        if (this.isEmpty()) {
            return null;
        } else if (this.size == 1) {
            T toRemove = this.items[this.last];
            this.items[this.last] = null;
            this.size -= 1;
            return toRemove;
        } else {
            T toRemove = this.items[this.last];
            this.items[this.last] = null;
            this.last = ArrayDeque.decrease(this.last, this.items.length);
            this.size -= 1;
            // Check the usage ratio (r) and shrink the array by
            // half if r < 0.25 and array length >= 16.
            double r = this.size / (double) this.items.length;
            if (r < 0.25 && this.items.length >= 16) {
                this.resize(this.items.length / 2);
            }
            return toRemove;
        }
    }

    /** Prints the items in the deque from the first to the last, separated by spaces. */
    public void printDeque() {
        if (this.isEmpty()) {
            System.out.println("You have no items in your deque.");
        } else {
            for (int i = this.first; i != this.last;
                 i = ArrayDeque.increase(i, this.items.length)) {
                System.out.print(this.items[i] + " ");
            }
            System.out.print(this.items[this.last]); // Last item
        }
        System.out.print("\n");
    }

    /** Gets the item at the given index, where 0 is the first, 1 is the next item, and thereafter*/
    public T get(int index) {
        if (index >= this.size) {
            return null;
        }
        int actualIValue = (index + this.first) % this.items.length;
        return this.items[actualIValue];
    }
}
