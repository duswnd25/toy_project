package catvote.beans;

public class RewardItem {
    private String title, description;
    private int    point, id;

    public RewardItem(int id, String title, String description, int point) {
        this.title       = title;
        this.description = description;
        this.point       = point;
        this.id          = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
