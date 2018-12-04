package catvote.beans;

public class UserItem {
    private String  id;
    private String  pw;
    private int     point;
    private boolean isAdmin;
    private String  group;

    public UserItem() {}

    public UserItem(String id, String pw, int point, boolean isAdmin) {
        this.id      = id;
        this.pw      = pw;
        this.point   = point;
        this.isAdmin = isAdmin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
