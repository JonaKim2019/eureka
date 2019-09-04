/**
 * @ClassName Palindrome
 * @Author Jonathan Kim
 */
public class Palindrome {
    /**
     * build a Deque where the characters in the deque appear in the same order as in the word.
     * */
    public boolean isPalindrome(String word, CharacterComparator cc) {
        boolean checker = true;
        for (int i = 0; i < word.length(); i++) {
            if (i == (word.length() - 1 - i)) {
                break;
            }
            checker &= cc.equalChars(word.charAt(i), word.charAt(word.length() - 1 - i));
        }
        return checker;
    }

    public Deque<Character> wordToDeque(String word) {
        Deque<Character> array = new ArrayDeque<Character>();

        for (int i = 0; i < word.length(); i++) {
            array.addLast(word.charAt(i));
        }
        return array;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> array = wordToDeque(word);
        char front;
        char back;

        while (!array.isEmpty()) {
            back = array.removeFirst();

            /** jump out if the number of character is odd */
            if (array.isEmpty()) {
                break;
            }
            front = array.removeLast();

            if (back != front) {
                return false;
            }
        }
        return true;
    }
}
