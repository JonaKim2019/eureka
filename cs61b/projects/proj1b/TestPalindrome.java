import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        //Checking empty string case
        assertTrue(palindrome.isPalindrome(" "));
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("  "));

        //Checking length 1 string case
        assertTrue(palindrome.isPalindrome("!"));
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome("A"));
        assertTrue(palindrome.isPalindrome("Z"));

        //Checking true statements
        assertTrue(palindrome.isPalindrome("mom"));
        assertTrue(palindrome.isPalindrome("mooom"));
        assertTrue(palindrome.isPalindrome("MoM"));
        assertTrue(palindrome.isPalindrome("mOOm"));
        assertTrue(palindrome.isPalindrome("NOON"));
        assertTrue(palindrome.isPalindrome("NooN"));
        assertTrue(palindrome.isPalindrome("nOOn"));
        assertTrue(palindrome.isPalindrome("repaper"));
        assertTrue(palindrome.isPalindrome("deified"));
        assertTrue(palindrome.isPalindrome("cattac"));
        assertTrue(palindrome.isPalindrome("ZzZzZzZ"));
        assertTrue(palindrome.isPalindrome("zzZZzzZZzz"));
        assertTrue(palindrome.isPalindrome("pep"));
        assertTrue(palindrome.isPalindrome("PeppeP"));
        assertTrue(palindrome.isPalindrome("LLLLOoOLLLL"));
        assertTrue(palindrome.isPalindrome("zazaZaZaZaZazaz"));
        assertTrue(palindrome.isPalindrome("&*&"));
        assertTrue(palindrome.isPalindrome("&&"));
        assertTrue(palindrome.isPalindrome("$$"));
        assertTrue(palindrome.isPalindrome("racecar"));

        //Checking false statements
        assertFalse(palindrome.isPalindrome("catted"));
        assertFalse(palindrome.isPalindrome("awesomeness"));
        assertFalse(palindrome.isPalindrome("clock"));
        assertFalse(palindrome.isPalindrome("none"));
        assertFalse(palindrome.isPalindrome("NONE"));
        assertFalse(palindrome.isPalindrome("NoNE"));
        assertFalse(palindrome.isPalindrome("NoNe"));
        assertFalse(palindrome.isPalindrome("%^&"));
        assertFalse(palindrome.isPalindrome("Aa"));
        assertFalse(palindrome.isPalindrome("cata"));
        assertFalse(palindrome.isPalindrome("ab"));
        assertFalse(palindrome.isPalindrome("NoON"));
        assertFalse(palindrome.isPalindrome("HelloWorld!"));
    }

    @Test
    public void testIsPalindromeOffByOne() {
        //Checking empty string case
        OffByOne test = new OffByOne();

        assertTrue(palindrome.isPalindrome("", test));


        //Checking length 1 string case
        assertTrue(palindrome.isPalindrome("a", test));
        assertTrue(palindrome.isPalindrome("Z", test));

        //Checking true statements
        assertTrue(palindrome.isPalindrome("flake", test));
        assertTrue(palindrome.isPalindrome("AB", test));
        assertTrue(palindrome.isPalindrome("122", test));
        assertTrue(palindrome.isPalindrome("&!%", test));
        assertTrue(palindrome.isPalindrome("4CD5", test));

        //Checking false statements
        assertFalse(palindrome.isPalindrome("awesome", test));
        assertFalse(palindrome.isPalindrome("$A", test));
        assertFalse(palindrome.isPalindrome("@#", test));
        assertFalse(palindrome.isPalindrome("mom", test));
        assertFalse(palindrome.isPalindrome("racecar", test));

    }

    @Test
    public void testPalindromeByN() {
        OffByN offByN = new OffByN(6);
        assertTrue(palindrome.isPalindrome("gmdjgm", offByN));
        assertFalse(palindrome.isPalindrome("aaabbbaa", offByN));
        assertTrue(palindrome.isPalindrome("", offByN));
        assertTrue(palindrome.isPalindrome("&", offByN));
    }
}
