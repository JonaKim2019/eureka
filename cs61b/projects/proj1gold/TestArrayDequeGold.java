import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {

    @Test
    public void randomTestForError() {
        StudentArrayDeque<Integer> testArrayNumber1 = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> testArrayNumber2 = new ArrayDequeSolution<>();

        String message = "\n";

        for (int i = 0; i < 10; i++) {
            Integer randomNumber = StdRandom.uniform(100);
            testArrayNumber2.addFirst(randomNumber);
            testArrayNumber1.addFirst(randomNumber);
            message = message + "addFirst(" + randomNumber + ")\n";
        }

        for (int i = 0; i < 10; i++) {
            message = message + "removeFirst()\n";
            assertEquals(message, testArrayNumber2.removeFirst(), testArrayNumber1.removeFirst());
            message = message + "removeLast()\n";
            assertEquals(message, testArrayNumber2.removeLast(), testArrayNumber1.removeLast());
        }
    }
}