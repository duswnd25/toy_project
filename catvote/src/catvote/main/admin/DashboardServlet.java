package catvote.main.admin;

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
import catvote.beans.NoticeItem;
import catvote.beans.PostItem;
import catvote.beans.VoteItem;
import catvote.beans.VoteLogItem;
import catvote.beans.VoteResultItem;

import catvote.database.DatabaseManager;

import catvote.utils.ServletInit;

public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public DashboardServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletInit servletInit = new ServletInit();

        request  = servletInit.initDefaultRequest(request);
        response = servletInit.initDefaultResponse(response);

        HttpSession session = request.getSession();
        boolean     isAdmin = false;
        String      menu    = "dashboard";

        try {
            isAdmin = (boolean) session.getAttribute(Const.SESSION.IS_ADMIN);
            menu    = request.getParameter(Const.PARAMETER.MENU);

            if (menu == null) {
                menu = "dashboard";
            }
        } catch (Exception e) {
            isAdmin = false;
        } finally {
            if (isAdmin) {
                request = initData(request, response, menu);
                getServletContext().getRequestDispatcher(Const.PATH.TEMPLATE_PATH_ADMIN).forward(request, response);
            } else {
                response.sendRedirect(Const.PATH.ROOT_PATH + "/index");
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private HttpServletRequest initCandidateEdit(HttpServletRequest request) {
        DatabaseManager.VOTE      db            = new DatabaseManager.VOTE();
        LinkedList<CandidateItem> candidateList = db.getAllCandidate();
        String                    select        = request.getParameter(Const.PARAMETER.STUDENT_NUMBER);

        if (select == null) {
            select = "0";
        }

        request.setAttribute(Const.ATTRIBUTE.CANDIDATE_INDEX, select);
        request.setAttribute(Const.ATTRIBUTE.CANDIDATE_LIST, candidateList);

        return request;
    }

    private HttpServletRequest initData(HttpServletRequest request, HttpServletResponse response, String menu) {
        int index = 0;

        if (menu.equals("dashboard")) {
            index   = 0;
            request = initVoteListData(request);
        } else if (menu.equals("edit_vote")) {
            index   = 0;
            request = initEditVote(request);
        } else if (menu.equals("notification")) {
            index   = 1;
            request = initNotice(request);
        } else if (menu.equals("edit_profile")) {
            index   = 2;
            request = initCandidateEdit(request);
        } else if (menu.equals("edit_post")) {
            index   = 3;
            request = initPost(request);
        }

        request.setAttribute(Const.ATTRIBUTE.INDEX, index);
        request.setAttribute(Const.PATH.PAGE, menu);

        return request;
    }

    private HttpServletRequest initEditVote(HttpServletRequest request) {
        DatabaseManager.VOTE db     = new DatabaseManager.VOTE();
        String               select = (String) request.getParameter(Const.PARAMETER.VOTE_ID);

        if (select == null) {
            select = "0";
        }

        VoteItem vote = db.getVote(select);

        request.setAttribute(Const.ATTRIBUTE.VOTE_ID, select);
        request.setAttribute(Const.ATTRIBUTE.VOTE_SIZE, db.getVoteList().size() + 1);
        request.setAttribute(Const.ATTRIBUTE.VOTE_LIST, vote);

        return request;
    }

    private HttpServletRequest initNotice(HttpServletRequest request) {
        DatabaseManager.NOTICE db         = new DatabaseManager.NOTICE();
        LinkedList<NoticeItem> noticeList = db.getNoticeList();
        String                 select     = request.getParameter(Const.PARAMETER.NOTICE_ID);

        if (select == null) {
            select = "0";
        }

        request.setAttribute(Const.ATTRIBUTE.NOTICE_INDEX, select);
        request.setAttribute(Const.ATTRIBUTE.NOTICE_LIST, noticeList);

        return request;
    }

    private HttpServletRequest initPost(HttpServletRequest request) {
        DatabaseManager.POST db       = new DatabaseManager.POST();
        LinkedList<PostItem> postList = db.getPostList();
        String               select   = request.getParameter(Const.PARAMETER.POST_ID);

        if (select == null) {
            select = "0";
        }

        request.setAttribute(Const.ATTRIBUTE.POST_INDEX, select);
        request.setAttribute(Const.ATTRIBUTE.POST_LIST, postList);

        return request;
    }

    private HttpServletRequest initVoteListData(HttpServletRequest request) {
        DatabaseManager.VOTE db       = new DatabaseManager.VOTE();
        LinkedList<VoteItem> voteList = db.getVoteList();

        for (VoteItem item : voteList) {
            item.setTargetSize(db.getVoteTargetNum(item.getId()));
            item.setVoteSize(db.getVoteUserNum(item.getId()));

            LinkedList<VoteLogItem>  voteLogList = db.getAllVoteLog(item.getId());
            HashMap<String, Integer> map         = new HashMap<>();

            for (VoteLogItem log : voteLogList) {
                String select = log.getUserSelect().split("-")[0];
                map.put(select, map.containsKey(select) ? map.get(select) + 1 : 1);
            }

            LinkedList<VoteResultItem> tempResultList = new LinkedList<>();
            StringBuilder              stb            = new StringBuilder();

            for (Entry<String, Integer> entry : map.entrySet()) {
                tempResultList.add(new VoteResultItem(db.getCandidateName(entry.getKey()), entry.getValue()));
            }

            for (int index = 0; index < tempResultList.size(); index++) {
                stb.append(tempResultList.get(index).getResult());

                if (index + 1 != tempResultList.size()) {
                    stb.append(" / ");
                }
            }
            item.setStatus(stb.toString());
        }

        request.setAttribute(Const.ATTRIBUTE.VOTE_LIST, voteList);

        return request;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
