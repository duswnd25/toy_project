package catvote.main.user;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import catvote.Const;
import catvote.beans.CandidateItem;
import catvote.beans.QuestionItem;
import catvote.beans.UserItem;
import catvote.database.DatabaseManager;
import catvote.utils.ServletInit;

public class VoteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public VoteServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletInit servletInit = new ServletInit();

		request = servletInit.initDefaultRequest(request);
		response = servletInit.initDefaultResponse(response);

		HttpSession session = request.getSession();

		if (session.getAttribute(Const.SESSION.IS_LOGIN) == null) {
			redirect(response);
		} else {
			try {
				String voteId = request.getParameter(Const.PARAMETER.VOTE_ID);
				String[] candidateListInVote = new DatabaseManager.VOTE().getVote(voteId).getCandidate().split(",");
				String recentSelect = new DatabaseManager.VOTE().getRecentSelect(voteId,
						((UserItem) session.getAttribute(Const.SESSION.USER_INFO)).getId());
				LinkedList<CandidateItem> candidateList = new LinkedList<>();

				for (String s : candidateListInVote) {
					CandidateItem temp = new DatabaseManager.VOTE().getCandidate(s);

					LinkedList<QuestionItem> questionList = new DatabaseManager.VOTE()
							.getQuestionList(temp.getStudentNumber());
					StringBuilder stb = new StringBuilder();
					for (QuestionItem q : questionList) {
						stb.append(q.getQuestion());
						stb.append(" / ");
						stb.append(q.getUserId().substring(0, 2));
						stb.append("***");
						stb.append("<br>");
					}
					temp.setQuestion(stb.toString());
					candidateList.add(temp);
				}

				if (recentSelect == null || recentSelect.equals("")) {
					recentSelect = "없음";
				}

				// 투표율
				DatabaseManager.VOTE db = new DatabaseManager.VOTE();
				float targetTotalSize = db.getVoteTargetNum(Integer.parseInt(voteId));
				float voteTotalSize = db.getVoteUserNum(Integer.parseInt(voteId));

				// CANDIDATE LIST
				request.setAttribute(Const.ATTRIBUTE.VOTE_RATE, (int) ((voteTotalSize == 0 || targetTotalSize == 0 ? 0
						: ((voteTotalSize / targetTotalSize) * 100))) + "%");
				request.setAttribute(Const.ATTRIBUTE.RECENT_SELECT, recentSelect);
				request.setAttribute(Const.ATTRIBUTE.CANDIDATE_LIST, candidateList);
				request.setAttribute(Const.ATTRIBUTE.VOTE_ID, request.getParameter(Const.PARAMETER.VOTE_ID));
				request.setAttribute(Const.PATH.PAGE, "vote");
				getServletContext().getRequestDispatcher(Const.PATH.TEMPLATE_PATH_USER).forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
				redirect(response);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void redirect(HttpServletResponse response) throws IOException {
		response.sendRedirect(Const.PATH.ROOT_PATH);
	}
}

// ~ Formatted by Jindent --- http://www.jindent.com
