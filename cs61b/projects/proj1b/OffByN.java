/**
 * @ClassName OffByN
 * @Author Jonathan Kim
 */
public class OffByN implements CharacterComparator {

    private int N;

    public OffByN(int N) {
        this.N = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        int diff = x - y;
        return Math.abs(diff) == N ? true : false;
    }

}
