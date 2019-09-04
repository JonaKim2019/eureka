/** Performs some basic linked list tests. */
public class LinkedListDequeTest {
	
	/* Utility method for printing out empty checks. */
	public static boolean checkEmpty(boolean expected, boolean actual) {
		if (expected != actual) {
			System.out.println("isEmpty() returned " + actual + ", but expected: " + expected);
			return false;
		}
		return true;
	}

	/* Utility method for printing out empty checks. */
	public static boolean checkSize(int expected, int actual) {
		if (expected != actual) {
			System.out.println("size() returned " + actual + ", but expected: " + expected);
			return false;
		}
		return true;
	}

	/* Prints a nice message based on whether a test passed. 
	 * The \n means newline. */
	public static void printTestStatus(boolean passed) {
		if (passed) {
			System.out.println("Test passed!\n");
		} else {
			System.out.println("Test failed!\n");
		}
	}

	/** Adds a few things to the list, checking isEmpty() and size() are correct, 
	  * finally printing the results. 
	  *
	  * && is the "and" operation. */
	public static void addIsEmptySizeTest() {
		System.out.println("Running add/isEmpty/Size test.");
		System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

		LinkedListDeque<String> lld1 = new LinkedListDeque<String>();

		boolean passed = checkEmpty(true, lld1.isEmpty());

		lld1.addFirst("front");
		
		// The && operator is the same as "and" in Python.
		// It's a binary operator that returns true if both arguments true, and false otherwise.
		passed = checkSize(1, lld1.size()) && passed;
		passed = checkEmpty(false, lld1.isEmpty()) && passed;

		lld1.addLast("middle");
		passed = checkSize(2, lld1.size()) && passed;

		lld1.addLast("back");
		passed = checkSize(3, lld1.size()) && passed;

		System.out.println("Printing out deque: ");
		lld1.printDeque();

		printTestStatus(passed);

	}

	/** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
	public static void addRemoveTest() {

		System.out.println("Running add/remove test.");

		System.out.println("Make sure to uncomment the lines below (and delete this print statement).");
		
		LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
		// should be empty 
		boolean passed = checkEmpty(true, lld1.isEmpty());

		lld1.addFirst(10);
		// should not be empty 
		passed = checkEmpty(false, lld1.isEmpty()) && passed;

		lld1.removeFirst();
		// should be empty 
		passed = checkEmpty(true, lld1.isEmpty()) && passed;

		printTestStatus(passed);
		
	}

	/** Testing out the ArrayDeque class! */
	public static void arrayDequeTest() {

		ArrayDeque<Integer> list = new ArrayDeque<>();
        list.addFirst(3);
        list.addFirst(2);
        list.addFirst(1);
        list.addFirst(0);
        list.addLast(4);
        list.addLast(5);
        list.addLast(6);
        list.addLast(7);
        list.addLast(8);
        list.addLast(9);
        list.addFirst(-1);
        list.addFirst(-2);
        list.addFirst(-3);
        list.addFirst(-4);
        list.addFirst(-5);
        list.addFirst(-5);
        list.printDeque();
        System.out.println("Size: " + list.size());
        list.removeFirst();
        list.removeFirst();
        list.removeFirst();
        list.removeFirst();
        list.removeFirst();
        list.removeFirst();
        list.removeFirst();
        list.removeFirst();
        list.removeFirst();
        list.printDeque();
        list.printDeque();
		list.addFirst(3);
		list.addFirst(2);
		ArrayDeque<Integer> k =  new ArrayDeque(list);
		list.addFirst(2);
        System.out.println("Size " + list.size());
        System.out.println(list.get(3));
	}

	/** Testing out the LinkedListDeque class! */
	public static void linkedListDequeTest() {
		LinkedListDeque<Integer> list = new LinkedListDeque<>();
		list.addFirst(3);
		list.addFirst(2);
		list.addFirst(1);
		list.addFirst(0);
		list.addLast(4);
		list.addLast(5);
		list.addLast(6);
		list.addLast(7);
		list.addLast(8);
		list.addLast(9);
		list.addFirst(-1);
		list.addFirst(-2);
		list.addFirst(-3);
		list.addFirst(-4);
		list.addFirst(-5);
		list.addFirst(-5);
		list.printDeque();
		System.out.println("Size: " + list.size());
		list.removeFirst();
		list.removeFirst();
		list.removeFirst();
		list.removeFirst();
		list.removeFirst();
		list.removeFirst();
		list.removeFirst();
		list.removeFirst();
		list.removeFirst();
		list.printDeque();
		list.printDeque();
		list.addFirst(3);
		list.addFirst(2);
		LinkedListDeque<Integer> k =  new LinkedListDeque(list);
		list.addFirst(2);
		System.out.println("Size " + list.size());
		System.out.println(list.get(3));
        System.out.println("Size " + list.size());
        System.out.println(list.get(3));
        System.out.println(list.removeLast());
        list.printDeque();
        System.out.println(list.getRecursive(0));
	}

	public static void main(String[] args) {
		System.out.println("Running tests.\n");
		addIsEmptySizeTest();
		addRemoveTest();
		arrayDequeTest();
		linkedListDequeTest();
	}
} 
