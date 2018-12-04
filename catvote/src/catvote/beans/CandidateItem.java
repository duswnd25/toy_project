package catvote.beans;

public class CandidateItem implements Comparable<CandidateItem> {
    private String name, major, description, memo, studentNumber, question;

    public CandidateItem() {}

    @Override
    public int compareTo(CandidateItem o) {
        return o.getStudentNumber().compareTo(this.getStudentNumber());
    }

    public String getDescription() {
        return description.replaceAll("\n", "<br>");
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
