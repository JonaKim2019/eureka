import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * @ClassName TestOffByN
 * @Author Jonathan Kim
 */
public class TestOffByN {
    static CharacterComparator offByN;

    @Test
    public void testEqualChars() {
        offByN = new OffByN(3);
        assertTrue(offByN.equalChars('a', 'd'));
        assertTrue(offByN.equalChars('g', 'd'));
        assertFalse(offByN.equalChars('a', 'a'));
    }
}
