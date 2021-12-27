package forum.helpers;

public class VoteHelper {
    public static Object calculateVote() {
        return new Object() {
            int vote = 0;

            public void modify(boolean b) {
                vote += b ? 1 : -1;
            }
        };
    }
}
