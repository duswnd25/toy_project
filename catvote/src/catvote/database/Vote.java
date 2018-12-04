package catvote.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;

import catvote.Const;
import catvote.beans.CandidateItem;
import catvote.beans.QuestionItem;
import catvote.beans.VoteItem;
import catvote.beans.VoteLogItem;

class Vote {

	// create new vote
	boolean createVote(VoteItem item) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String query = String.format(
				"insert into %s (`%s`, `%s`, `%s`, `%s`, `%s`, `%s`) VALUES (%s, '%s', '%s', '%s', '%s', '%s') ON DUPLICATE KEY UPDATE %s='%s', %s='%s', %s='%s', %s='%s', %s='%s';",
				Const.DB_CONTENT.VOTE.TABLE_NAME, Const.DB_CONTENT.VOTE.VOTE_ID, Const.DB_CONTENT.VOTE.TITLE,
				Const.DB_CONTENT.VOTE.START_DATE, Const.DB_CONTENT.VOTE.END_DATE, Const.DB_CONTENT.VOTE.TARGET,
				Const.DB_CONTENT.VOTE.CANDIDATE, item.getId(), item.getTitle(), sdf.format(item.getStart()),
				sdf.format(item.getEnd()), item.getTarget(), item.getCandidate(), Const.DB_CONTENT.VOTE.TITLE,
				item.getTitle(), Const.DB_CONTENT.VOTE.START_DATE, sdf.format(item.getStart()),
				Const.DB_CONTENT.VOTE.END_DATE, sdf.format(item.getEnd()), Const.DB_CONTENT.VOTE.TARGET,
				item.getTarget(), Const.DB_CONTENT.VOTE.CANDIDATE, item.getCandidate());

		int resultCount = 0;

		try {
			Class.forName(Const.DB_INFO.DB_CONNECTOR_NAME);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection(Const.DB_INFO.DB_URL, Const.DB_INFO.DB_ID,
				Const.DB_INFO.DB_PW); Statement statement = conn.createStatement()) {
			resultCount = statement.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultCount != 0;
	}

	// create or update candidate information
	boolean updateCandidateInfo(CandidateItem item) {
		String query = String.format(
				"insert into %s (`%s`, `%s`, `%s`, `%s`, `%s`) VALUES ('%s', '%s', '%s', '%s', '%s') ON DUPLICATE KEY UPDATE %s='%s', %s='%s', %s='%s', %s='%s';",
				Const.DB_CONTENT.CANDIDATE.TABLE_NAME, Const.DB_CONTENT.CANDIDATE.STUDENT_NUMBER,
				Const.DB_CONTENT.CANDIDATE.NAME, Const.DB_CONTENT.CANDIDATE.MAJOR,
				Const.DB_CONTENT.CANDIDATE.DESCRIPTION, Const.DB_CONTENT.CANDIDATE.MEMO, item.getStudentNumber(),
				item.getName(), item.getMajor(), item.getDescription(), item.getMemo(), Const.DB_CONTENT.CANDIDATE.NAME,
				item.getName(), Const.DB_CONTENT.CANDIDATE.MAJOR, item.getMajor(),
				Const.DB_CONTENT.CANDIDATE.DESCRIPTION, item.getDescription(), Const.DB_CONTENT.CANDIDATE.MEMO,
				item.getMemo());
		int resultCount = 0;

		try {
			Class.forName(Const.DB_INFO.DB_CONNECTOR_NAME);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection(Const.DB_INFO.DB_URL, Const.DB_INFO.DB_ID,
				Const.DB_INFO.DB_PW); Statement statement = conn.createStatement()) {
			resultCount = statement.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultCount != 0;
	}

	// update user vote log value
	boolean updateUserVoteStatus(String voteId, String userId, String select) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int result = 0;

		try {
			Class.forName(Const.DB_INFO.DB_CONNECTOR_NAME);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection(Const.DB_INFO.DB_URL, Const.DB_INFO.DB_ID,
				Const.DB_INFO.DB_PW); Statement statement = conn.createStatement()) {
			String query = String.format(
					"insert into %s (`%s`, `%s`, `%s`, `%s`, `%s`) VALUES ('%s', %s, '%s', '%s', '%s') ON DUPLICATE KEY UPDATE %s='%s', %s='%s';",
					Const.DB_CONTENT.VOTE_LOG.TABLE_NAME, Const.DB_CONTENT.VOTE_LOG.ID,
					Const.DB_CONTENT.VOTE_LOG.VOTE_ID, Const.DB_CONTENT.VOTE_LOG.USER_ID,
					Const.DB_CONTENT.VOTE_LOG.USERSELECT, Const.DB_CONTENT.VOTE_LOG.TIMESTAMP, voteId + "-" + userId,
					voteId, userId, select + "-" + getCandidateName(select),
					sdf.format(Calendar.getInstance().getTime()), Const.DB_CONTENT.VOTE_LOG.USERSELECT,
					select + "-" + getCandidateName(select), Const.DB_CONTENT.VOTE_LOG.TIMESTAMP,
					sdf.format(Calendar.getInstance().getTime()));

			result = statement.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result != 0;
	}

	// return all candidate information list
	LinkedList<CandidateItem> getAllCandidate() {
		LinkedList<CandidateItem> candidateList = new LinkedList<>();

		try {
			Class.forName(Const.DB_INFO.DB_CONNECTOR_NAME);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection(Const.DB_INFO.DB_URL, Const.DB_INFO.DB_ID,
				Const.DB_INFO.DB_PW); Statement statement = conn.createStatement()) {
			String query = "select * from " + Const.DB_CONTENT.CANDIDATE.TABLE_NAME + ";";
			ResultSet rs = statement.executeQuery(query);

			while (rs.next()) {
				CandidateItem temp = new CandidateItem();

				temp.setName(rs.getString(Const.DB_CONTENT.CANDIDATE.NAME));
				temp.setMajor(rs.getString(Const.DB_CONTENT.CANDIDATE.MAJOR));
				temp.setDescription(rs.getString(Const.DB_CONTENT.CANDIDATE.DESCRIPTION));
				temp.setMemo(rs.getString(Const.DB_CONTENT.CANDIDATE.MEMO));
				temp.setStudentNumber(rs.getString(Const.DB_CONTENT.CANDIDATE.STUDENT_NUMBER));
				candidateList.add(temp);
			}
		} catch (Exception ignored) {
		}

		Collections.sort(candidateList);

		return candidateList;
	}

	LinkedList<VoteLogItem> getAllVoteLog(int voteId) {
		LinkedList<VoteLogItem> result = new LinkedList<>();

		try {
			Class.forName(Const.DB_INFO.DB_CONNECTOR_NAME);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection(Const.DB_INFO.DB_URL, Const.DB_INFO.DB_ID,
				Const.DB_INFO.DB_PW); Statement statement = conn.createStatement()) {
			String query = String.format("select * from %s where %s = '%s';", Const.DB_CONTENT.VOTE_LOG.TABLE_NAME,
					Const.DB_CONTENT.VOTE_LOG.VOTE_ID, voteId);
			ResultSet rs = statement.executeQuery(query);

			while (rs.next()) {
				VoteLogItem item = new VoteLogItem();

				item.setVoteId(voteId);
				item.setUserSelect(rs.getString(Const.DB_CONTENT.VOTE_LOG.USERSELECT));
				item.setUserId(rs.getString(Const.DB_CONTENT.VOTE_LOG.USER_ID));
				item.setTimestamp(rs.getTimestamp(Const.DB_CONTENT.VOTE_LOG.TIMESTAMP));
				result.add(item);
			}
		} catch (Exception ignored) {
		}

		return result;
	}

	// return available vote list for this user
	LinkedList<VoteItem> getAvailableVoteList(String userGroup) {
		LinkedList<VoteItem> voteList = new LinkedList<>();

		try (Connection conn = DriverManager.getConnection(Const.DB_INFO.DB_URL, Const.DB_INFO.DB_ID,
				Const.DB_INFO.DB_PW); Statement statement = conn.createStatement()) {
			String query = "select * from vote;";
			ResultSet rs = statement.executeQuery(query);

			while (rs.next()) {
				for (String s : rs.getString(Const.DB_CONTENT.VOTE.TARGET).split(",")) {
					if (userGroup.contains(s)) {
						VoteItem temp = new VoteItem();

						temp.setId(rs.getInt(Const.DB_CONTENT.VOTE.VOTE_ID));
						temp.setTitle(rs.getString(Const.DB_CONTENT.VOTE.TITLE));
						temp.setStart(rs.getTimestamp(Const.DB_CONTENT.VOTE.START_DATE));
						temp.setEnd(rs.getTimestamp(Const.DB_CONTENT.VOTE.END_DATE));
						temp.setTarget(rs.getString(Const.DB_CONTENT.VOTE.TARGET));
						temp.setCandidate(rs.getString(Const.DB_CONTENT.VOTE.CANDIDATE));
						voteList.add(temp);
					}
				}
			}

			if (voteList.size() == 0) {
				VoteItem temp = new VoteItem();

				temp.setId(0);
				temp.setTitle("NO AVAILABLE VOTE");
				temp.setStart(Calendar.getInstance().getTime());
				temp.setEnd(Calendar.getInstance().getTime());
				temp.setTarget(rs.getString(Const.DB_CONTENT.VOTE.TARGET));
				temp.setCandidate(rs.getString(Const.DB_CONTENT.VOTE.CANDIDATE));
				voteList.add(temp);
			}
		} catch (Exception ignored) {
		}

		return voteList;
	}

	// return candidate information
	CandidateItem getCandidate(String studentNumber) {
		CandidateItem candidate = new CandidateItem();

		try {
			Class.forName(Const.DB_INFO.DB_CONNECTOR_NAME);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection(Const.DB_INFO.DB_URL, Const.DB_INFO.DB_ID,
				Const.DB_INFO.DB_PW); Statement statement = conn.createStatement()) {
			String query = String.format("select * from %s where %s=%s", Const.DB_CONTENT.CANDIDATE.TABLE_NAME,
					Const.DB_CONTENT.CANDIDATE.STUDENT_NUMBER, studentNumber);
			ResultSet rs = statement.executeQuery(query);

			while (rs.next()) {
				candidate.setName(rs.getString(Const.DB_CONTENT.CANDIDATE.NAME));
				candidate.setMajor(rs.getString(Const.DB_CONTENT.CANDIDATE.MAJOR));
				candidate.setDescription(rs.getString(Const.DB_CONTENT.CANDIDATE.DESCRIPTION));
				candidate.setMemo(rs.getString(Const.DB_CONTENT.CANDIDATE.MEMO));
				candidate.setStudentNumber(rs.getString(Const.DB_CONTENT.CANDIDATE.STUDENT_NUMBER));
			}
		} catch (Exception ignored) {
		}

		return candidate;
	}

	// return candidate name
	String getCandidateName(String candidateId) {
		String query = String.format("select * from %s where %s='%s';", Const.DB_CONTENT.CANDIDATE.TABLE_NAME,
				Const.DB_CONTENT.CANDIDATE.STUDENT_NUMBER, candidateId);
		String result = "";

		try {
			Class.forName(Const.DB_INFO.DB_CONNECTOR_NAME);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection(Const.DB_INFO.DB_URL, Const.DB_INFO.DB_ID,
				Const.DB_INFO.DB_PW); Statement statement = conn.createStatement()) {
			ResultSet rs = statement.executeQuery(query);

			while (rs.next()) {
				result = rs.getString(Const.DB_CONTENT.CANDIDATE.NAME);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	// return next available vote
	VoteItem getNextVote() {
		VoteItem temp = new VoteItem();

		temp.setTitle("NO AVAILABLE VOTE");
		temp.setId(0);
		temp.setStart(Calendar.getInstance().getTime());
		temp.setEnd(Calendar.getInstance().getTime());

		try (Connection conn = DriverManager.getConnection(Const.DB_INFO.DB_URL, Const.DB_INFO.DB_ID,
				Const.DB_INFO.DB_PW); Statement statement = conn.createStatement()) {
			String query = "select * from vote;";
			ResultSet rs = statement.executeQuery(query);

			while (rs.next()) {
				Date now = Calendar.getInstance().getTime();
				Date startDate = rs.getTimestamp(Const.DB_CONTENT.VOTE.START_DATE);

				if (now.before(startDate)) {
					temp.setTitle(rs.getString(Const.DB_CONTENT.VOTE.TITLE));
					temp.setId(rs.getInt(Const.DB_CONTENT.VOTE.VOTE_ID));
					temp.setStart(rs.getTimestamp(Const.DB_CONTENT.VOTE.START_DATE));
					temp.setEnd(rs.getTimestamp(Const.DB_CONTENT.VOTE.END_DATE));
					temp.setTarget(rs.getString(Const.DB_CONTENT.VOTE.TARGET));
					temp.setCandidate(rs.getString(Const.DB_CONTENT.VOTE.CANDIDATE));

					break;
				}
			}
		} catch (Exception ignored) {
		}

		return temp;
	}

	String getRecentSelect(String voteId, String userId) {
		String result = "";

		try {
			Class.forName(Const.DB_INFO.DB_CONNECTOR_NAME);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection(Const.DB_INFO.DB_URL, Const.DB_INFO.DB_ID,
				Const.DB_INFO.DB_PW); Statement statement = conn.createStatement()) {
			String query = String.format("select * from %s where %s='%s';", Const.DB_CONTENT.VOTE_LOG.TABLE_NAME,
					Const.DB_CONTENT.VOTE_LOG.ID, voteId + "-" + userId);
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				result = rs.getString(Const.DB_CONTENT.VOTE_LOG.USERSELECT).split("-")[1];
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	// return is user already vote or not
	boolean isUserAlreadyVote(int voteId, String userId) {
		int rowCount = 0;

		try {
			Class.forName(Const.DB_INFO.DB_CONNECTOR_NAME);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection(Const.DB_INFO.DB_URL, Const.DB_INFO.DB_ID,
				Const.DB_INFO.DB_PW); Statement statement = conn.createStatement()) {
			String query = String.format("select * from %s where %s='%s' and %s=%s;",
					Const.DB_CONTENT.VOTE_LOG.TABLE_NAME, Const.DB_CONTENT.VOTE_LOG.USER_ID, userId,
					Const.DB_CONTENT.VOTE_LOG.VOTE_ID, voteId);

			ResultSet rs = statement.executeQuery(query);

			rs.last();
			rowCount = rs.getRow();
		} catch (Exception ignored) {
		}

		return !(rowCount == 0);
	}

	// return all vote list
	VoteItem getVote(String voteId) {
		VoteItem result = new VoteItem();

		try {
			Class.forName(Const.DB_INFO.DB_CONNECTOR_NAME);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection(Const.DB_INFO.DB_URL, Const.DB_INFO.DB_ID,
				Const.DB_INFO.DB_PW); Statement statement = conn.createStatement()) {
			String query = String.format("select * from %s where %s=%s;", Const.DB_CONTENT.VOTE.TABLE_NAME,
					Const.DB_CONTENT.VOTE.VOTE_ID, voteId);
			ResultSet rs = statement.executeQuery(query);

			while (rs.next()) {
				result.setId(rs.getInt(Const.DB_CONTENT.VOTE.VOTE_ID));
				result.setTitle(rs.getString(Const.DB_CONTENT.VOTE.TITLE));
				result.setStart(rs.getTimestamp(Const.DB_CONTENT.VOTE.START_DATE));
				result.setEnd(rs.getTimestamp(Const.DB_CONTENT.VOTE.END_DATE));
				result.setTarget(rs.getString(Const.DB_CONTENT.VOTE.TARGET));
				result.setCandidate(rs.getString(Const.DB_CONTENT.VOTE.CANDIDATE));
			}
		} catch (Exception ignored) {
		}

		return result;
	}

	// return all vote list
	LinkedList<VoteItem> getVoteList() {
		LinkedList<VoteItem> voteList = new LinkedList<>();

		try {
			Class.forName(Const.DB_INFO.DB_CONNECTOR_NAME);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection(Const.DB_INFO.DB_URL, Const.DB_INFO.DB_ID,
				Const.DB_INFO.DB_PW); Statement statement = conn.createStatement()) {
			String query = "select * from " + Const.DB_CONTENT.VOTE.TABLE_NAME + ";";
			ResultSet rs = statement.executeQuery(query);

			while (rs.next()) {
				VoteItem temp = new VoteItem();

				temp.setId(rs.getInt(Const.DB_CONTENT.VOTE.VOTE_ID));
				temp.setTitle(rs.getString(Const.DB_CONTENT.VOTE.TITLE));
				temp.setId(rs.getInt(Const.DB_CONTENT.VOTE.VOTE_ID));
				temp.setStart(rs.getTimestamp(Const.DB_CONTENT.VOTE.START_DATE));
				temp.setEnd(rs.getTimestamp(Const.DB_CONTENT.VOTE.END_DATE));
				temp.setTarget(rs.getString(Const.DB_CONTENT.VOTE.TARGET));
				temp.setCandidate(rs.getString(Const.DB_CONTENT.VOTE.CANDIDATE));
				voteList.add(temp);
			}
		} catch (Exception ignored) {
		}

		return voteList;
	}

	// return available user size for this vote
	int getVoteTargetNum(int voteId) {
		int userCount = 0;

		try (Connection conn = DriverManager.getConnection(Const.DB_INFO.DB_URL, Const.DB_INFO.DB_ID,
				Const.DB_INFO.DB_PW); Statement statement = conn.createStatement()) {
			String voteGroup[] = null;
			String query = String.format("select * from %s where %s='%s';", Const.DB_CONTENT.VOTE.TABLE_NAME,
					Const.DB_CONTENT.VOTE_LOG.VOTE_ID, voteId);
			ResultSet rs = statement.executeQuery(query);

			if (rs.next()) {
				voteGroup = rs.getString(Const.DB_CONTENT.VOTE.TARGET).split(",");

				for (int index = 0; index < voteGroup.length; index++) {
					voteGroup[index] = voteGroup[index].trim();
				}
			}

			query = String.format("select * from %s;", Const.DB_CONTENT.USER_INFO.TABLE_NAME);
			rs = statement.executeQuery(query);

			while (rs.next()) {
				String joinGroup = rs.getString(Const.DB_CONTENT.USER_INFO.GROUP);

				for (String temp : voteGroup) {
					if (joinGroup.contains(temp)) {
						userCount++;
					}
				}
			}
		} catch (Exception ignored) {
		}

		return userCount;
	}

	// return user size of already vote to this vote
	int getVoteUserNum(int voteId) {
		int result = 0;

		try {
			Class.forName(Const.DB_INFO.DB_CONNECTOR_NAME);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection(Const.DB_INFO.DB_URL, Const.DB_INFO.DB_ID,
				Const.DB_INFO.DB_PW); Statement statement = conn.createStatement()) {
			String query = String.format("select * from %s where %s = '%s';", Const.DB_CONTENT.VOTE_LOG.TABLE_NAME,
					Const.DB_CONTENT.VOTE_LOG.VOTE_ID, voteId);
			ResultSet rs = statement.executeQuery(query);

			rs.last();
			result = rs.getRow();
		} catch (Exception ignored) {
		}

		return result;
	}

	LinkedList<QuestionItem> getQuestionList(String candidate) {
		LinkedList<QuestionItem> result = new LinkedList<>();
		try {
			Class.forName(Const.DB_INFO.DB_CONNECTOR_NAME);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection(Const.DB_INFO.DB_URL, Const.DB_INFO.DB_ID,
				Const.DB_INFO.DB_PW); Statement statement = conn.createStatement()) {
			String query = String.format("select * from %s where %s = '%s';", Const.DB_CONTENT.QUESTION.TABLE_NAME,
					Const.DB_CONTENT.QUESTION.CANDIDATE_NUMBER, candidate);
			
			ResultSet rs = statement.executeQuery(query);

			while(rs.next()) {
				QuestionItem temp = new QuestionItem();
				temp.setId(rs.getString(Const.DB_CONTENT.QUESTION.ID));
				temp.setCandidateNumber(rs.getString(Const.DB_CONTENT.QUESTION.CANDIDATE_NUMBER));
				temp.setQuestion(rs.getString(Const.DB_CONTENT.QUESTION.QUESTION));
				temp.setUserId(rs.getString(Const.DB_CONTENT.QUESTION.USER_ID));
				result.add(temp);
			}
		} catch (Exception ignored) {
		}

		return result;
	}
	
	boolean updateQuestion(QuestionItem item) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int resultCount = 0;
		String query = String.format(
				"insert into %s (`%s`, `%s`, `%s`, `%s`, `%s`) VALUES ('%s', '%s', '%s', '%s', '%s') ON DUPLICATE KEY UPDATE %s='%s', %s='%s';",
				Const.DB_CONTENT.QUESTION.TABLE_NAME, Const.DB_CONTENT.QUESTION.ID,
				Const.DB_CONTENT.QUESTION.QUESTION, Const.DB_CONTENT.QUESTION.USER_ID,
				Const.DB_CONTENT.QUESTION.CANDIDATE_NUMBER, Const.DB_CONTENT.QUESTION.TIMESTAMP, item.getId(),
				item.getQuestion(), item.getUserId(), item.getCandidateNumber(), sdf.format(item.getTimestamp()), Const.DB_CONTENT.QUESTION.QUESTION,
				item.getQuestion(), Const.DB_CONTENT.QUESTION.TIMESTAMP, sdf.format(item.getTimestamp()));

		try {
			Class.forName(Const.DB_INFO.DB_CONNECTOR_NAME);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection(Const.DB_INFO.DB_URL, Const.DB_INFO.DB_ID,
				Const.DB_INFO.DB_PW); Statement statement = conn.createStatement()) {
			resultCount = statement.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultCount != 0;
	}
}