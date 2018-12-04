package catvote.beans;

import java.util.LinkedList;

public class TimelineItem {
    private LinkedList<CandidateItem> candidateList = new LinkedList<>();
    private String                    title;
    private int                       id;
    private String                    result;
    private int                       rate;

    public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public LinkedList<CandidateItem> getCandidateList() {
        return candidateList;
    }

    public void setCandidateList(LinkedList<CandidateItem> candidateList) {
        this.candidateList = candidateList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}