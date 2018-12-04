package catvote.beans;

import java.util.Date;

public class VoteLogItem {
    private int    voteId;
    private String userId, userSelect;
    private Date   timestamp;

    public VoteLogItem() {}

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserSelect() {
        return userSelect;
    }

    public void setUserSelect(String userSelect) {
        this.userSelect = userSelect;
    }

    public int getVoteId() {
        return voteId;
    }

    public void setVoteId(int voteId) {
        this.voteId = voteId;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
