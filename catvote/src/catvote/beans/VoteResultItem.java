package catvote.beans;

public class VoteResultItem {
    private String name;
    private int    voteCount;

    public VoteResultItem(String name, int voteCount) {
        this.name      = name;
        this.voteCount = voteCount;
    }

    public String getResult() {
        return name + " " + voteCount + " 표";
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
