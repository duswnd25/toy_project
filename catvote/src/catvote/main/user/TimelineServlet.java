package catvote.main.user;

import java.io.IOException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import catvote.Const;

import catvote.beans.CandidateItem;
import catvote.beans.TimelineItem;
import catvote.beans.VoteItem;
import catvote.beans.VoteLogItem;
import catvote.beans.VoteResultItem;

import catvote.database.DatabaseManager;

import catvote.utils.ServletInit;

public class TimelineServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public TimelineServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletInit servletInit = new ServletInit();

		request = servletInit.initDefaultRequest(request);
		response = servletInit.initDefaultResponse(response);

		HttpSession session = request.getSession();

		if (session.getAttribute(Const.SESSION.IS_LOGIN) == null) {
			response.sendRedirect(Const.PATH.ROOT_PATH);
		} else {
			request = initData(request);
			request.setAttribute("PAGE", "timeline");
			getServletContext().getRequestDispatcher(Const.PATH.TEMPLATE_PATH_USER).forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private HttpServletRequest initData(HttpServletRequest request) throws IOException {
		DatabaseManager.VOTE db = new DatabaseManager.VOTE();
		LinkedList<TimelineItem> timelineList = new LinkedList<>();

		// 투표 결과 계산
		for (VoteItem item : db.getVoteList()) {
			item.setTargetSize(db.getVoteTargetNum(item.getId()));
			item.setVoteSize(db.getVoteUserNum(item.getId()));

			LinkedList<VoteLogItem> voteLogList = db.getAllVoteLog(item.getId());
			HashMap<String, Integer> map = new HashMap<>();
			String mostVotedCandidate = "";
			int maxVoteCount = 0;

			for (VoteLogItem log : voteLogList) {
				String select = log.getUserSelect().split("-")[0];
				int count = map.containsKey(select) ? map.get(select) + 1 : 1;

				map.put(select, count);
				mostVotedCandidate = (maxVoteCount > count) ? mostVotedCandidate : log.getUserSelect().split("-")[1];
				maxVoteCount = Math.max(maxVoteCount, count);
			}

			LinkedList<VoteResultItem> tempResultList = new LinkedList<>();
			StringBuilder stb = new StringBuilder();

			for (Entry<String, Integer> entry : map.entrySet()) {
				tempResultList.add(new VoteResultItem(db.getCandidateName(entry.getKey()), entry.getValue()));
			}

			// 최종 결과
			for (int index = 0; index < tempResultList.size(); index++) {
				stb.append(tempResultList.get(index).getResult());

				if (index + 1 != tempResultList.size()) {
					stb.append(" / ");
				}
			}

			stb.append("<br>최고 득표 : ");
			stb.append(mostVotedCandidate);
			stb.append(" ");
			stb.append(maxVoteCount);
			item.setStatus(stb.toString());

			// 후보자 명단
			LinkedList<CandidateItem> tempCandidate = new LinkedList<>();

			for (String s : item.getCandidate().split(",")) {
				tempCandidate.add(db.getCandidate(s));
			}

			float targetTotalSize = db.getVoteTargetNum(item.getId());
			float voteTotalSize = db.getVoteUserNum(item.getId());

			// 타임라인 추가
			TimelineItem timelineItem = new TimelineItem();

			timelineItem.setTitle(item.getTitle());
			timelineItem.setResult(item.getStatus());
			timelineItem.setCandidateList(tempCandidate);
			timelineItem.setRate(
					(int) ((voteTotalSize == 0 || targetTotalSize == 0 ? 0 : ((voteTotalSize / targetTotalSize) * 100))));
			timelineList.add(timelineItem);
		}

		request.setAttribute(Const.ATTRIBUTE.TIMELINE_LIST, timelineList);

		return request;
	}
}

// ~ Formatted by Jindent --- http://www.jindent.com
