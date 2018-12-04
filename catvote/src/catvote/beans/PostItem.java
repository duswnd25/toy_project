package catvote.beans;

import java.util.Date;

public class PostItem implements Comparable<PostItem> {
    private Date   date;
    private String content;
    private String title;
    private int    index;

    public PostItem() {}

    public PostItem(String content, Date date) {
        this.date    = date;
        this.content = content;
    }

    @Override
    public int compareTo(PostItem o) {
        return o.getDate().compareTo(this.getDate());
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
