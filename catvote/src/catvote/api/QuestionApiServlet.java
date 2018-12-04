package catvote.api;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import catvote.Const;
import catvote.beans.QuestionItem;
import catvote.beans.UserItem;
import catvote.database.DatabaseManager;

public class QuestionApiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");

		String userId, candidate, voteId = "", input = "";

		try {
			HttpSession session = request.getSession();

			userId = ((UserItem) session.getAttribute(Const.ATTRIBUTE.USER_INFO)).getId();
			candidate = request.getParameter("candidate");
			input = request.getParameter("input");
			voteId = request.getParameter("vote_id");

			QuestionItem question = new QuestionItem();
			question.setId(candidate + "-" + userId);
			question.setCandidateNumber(candidate);
			question.setQuestion(input);
			question.setTimestamp(Calendar.getInstance().getTime());
			question.setUserId(userId);

			new DatabaseManager.VOTE().updateQuestion(question);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			String path = Const.PATH.ROOT_PATH + "/vote?vote_id=" + voteId;
			System.out.println(path);
			response.sendRedirect(path);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doGet(request, response);
	}
}

// ~ Formatted by Jindent --- http://www.jindent.com
