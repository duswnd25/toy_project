package catvote.beans;

import java.util.Date;

public class VoteItem {
	private String title;
	private int id, targetSize, voteSize;
	private Date start, end;
	private String status, target, candidate;
	private boolean userAlreadyVote;

	public VoteItem() {
	}

	public VoteItem(int id, String title, Date start, Date end) {
		super();
		this.id = id;
		this.title = title;
		this.start = start;
		this.end = end;
	}

	public String getCandidate() {
		return candidate;
	}

	public void setCandidate(String candidate) {
		this.candidate = candidate;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public int getTargetSize() {
		return targetSize;
	}

	public void setTargetSize(int targetSize) {
		this.targetSize = targetSize;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isUserAlreadyVote() {
		return userAlreadyVote;
	}

	public void setUserAlreadyVote(boolean userAlreadyVote) {
		this.userAlreadyVote = userAlreadyVote;
	}

	public int getVoteRate() {
		int rate = ((targetSize != 0) && (voteSize != 0))
				? Math.round((float) ((float) voteSize / (float) targetSize) * 100)
				: 0;
		return rate;
	}

	public int getVoteSize() {
		return voteSize;
	}

	public void setVoteSize(int voteSize) {
		this.voteSize = voteSize;
	}
}

// ~ Formatted by Jindent --- http://www.jindent.com
