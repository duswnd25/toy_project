package catvote.api;

import java.io.IOException;

import java.text.SimpleDateFormat;

import java.util.Calendar;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import catvote.Const;

import catvote.beans.CandidateItem;
import catvote.beans.NoticeItem;
import catvote.beans.PostItem;
import catvote.beans.VoteItem;

import catvote.database.DatabaseManager;

public class AdminApiServlet extends HttpServlet {
    private static final long   serialVersionUID = 1L;
    private HttpServletResponse response;
    private HttpServletRequest  request;

    // create or update candidate information
    private void candidate() throws IOException {
        CandidateItem candidate = new CandidateItem();

        candidate.setStudentNumber(request.getParameter(Const.PARAMETER.STUDENT_NUMBER));
        candidate.setName(request.getParameter(Const.PARAMETER.NAME));
        candidate.setMajor(request.getParameter(Const.PARAMETER.MAJOR));
        candidate.setMemo(request.getParameter(Const.PARAMETER.MEMO));
        candidate.setDescription(request.getParameter(Const.PARAMETER.DESCRIPTION));
        new DatabaseManager.VOTE().updateCandidateInfo(candidate);
        response.sendRedirect(Const.PATH.ROOT_PATH + "/admin?menu=edit_profile&student_number="
                              + candidate.getStudentNumber());
    }

    protected void doGet(HttpServletRequest localRequest, HttpServletResponse localResponse) throws IOException {
        response = localResponse;
        request  = localRequest;
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        // Return All of Parameter

        /*
         * Enumeration<String> parameterNames = request.getParameterNames();
         *
         * while (parameterNames.hasMoreElements()) {
         *
         * String paramName = parameterNames.nextElement();
         *
         * System.out.println(paramName);
         *
         * String[] paramValues = request.getParameterValues(paramName);
         *
         * for (int i = 0; i < paramValues.length; i++) { String paramValue =
         * paramValues[i]; System.out.println(paramValue); } }
         */
        boolean isAdmin = false;

        try {
            HttpSession session = request.getSession();

            isAdmin = (boolean) session.getAttribute(Const.SESSION.IS_ADMIN);

            if (isAdmin) {
                String type = ((String) request.getParameter(Const.PARAMETER.VOTE_API_TYPE));

                if (type.contains(CONST.VOTE)) {
                    vote();
                } else if (type.contains(CONST.CANDIDATE)) {
                    candidate();
                } else if (type.contains(CONST.POST)) {
                    post();
                } else if (type.contains(CONST.NOTICE)) {
                    notice();
                } else {
                    escape();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            escape();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

    // escape else pattern
    private void escape() throws IOException {
        response.sendRedirect("/catvote/login");
    }

    // create or update notice
    private void notice() throws Exception {
        SimpleDateFormat sdf    = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        NoticeItem       notice = new NoticeItem();

        notice.setIndex(Integer.parseInt(request.getParameter(Const.PARAMETER.NOTICE_ID)));
        notice.setTitle(request.getParameter(Const.PARAMETER.TITLE));
        notice.setContent(request.getParameter(Const.PARAMETER.CONTENT));
        notice.setStart(sdf.parse(request.getParameter(Const.PARAMETER.NOTICE_DATE_START)));
        notice.setEnd(sdf.parse(request.getParameter(Const.PARAMETER.NOTICE_DATE_END)));
        notice.setPrimary(Integer.parseInt(request.getParameter(Const.PARAMETER.NOTICE_PRIMARY)) == 1);
        new DatabaseManager.NOTICE().uploadNotice(notice);
        response.sendRedirect(Const.PATH.ROOT_PATH + "/admin?menu=notification&notice_id=" + notice.getIndex());
    }

    // create or update admin post
    private void post() throws IOException {
        PostItem post = new PostItem();

        post.setIndex(Integer.parseInt(request.getParameter(Const.PARAMETER.POST_ID)));
        post.setTitle(request.getParameter(Const.PARAMETER.TITLE));
        post.setContent(request.getParameter(Const.PARAMETER.CONTENT));
        post.setDate(Calendar.getInstance().getTime());
        new DatabaseManager.POST().uploadPost(post);
        response.sendRedirect(Const.PATH.ROOT_PATH + "/admin?menu=edit_post&post_id=" + post.getIndex());
    }

    // create or update vote
    private void vote() throws Exception {
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        VoteItem         vote = new VoteItem();

        vote.setId(Integer.parseInt(request.getParameter(Const.PARAMETER.VOTE_ID)));
        vote.setTitle(request.getParameter(Const.PARAMETER.TITLE));
        vote.setStart(sdf.parse(request.getParameter(Const.PARAMETER.NOTICE_DATE_START)));
        vote.setEnd(sdf.parse(request.getParameter(Const.PARAMETER.NOTICE_DATE_END)));
        vote.setTarget(request.getParameter(Const.PARAMETER.VOTE_TARGET));
        vote.setCandidate(request.getParameter(Const.PARAMETER.VOTE_CANDIDATE));
        new DatabaseManager.VOTE().createVote(vote);
        response.sendRedirect("/catvote/admin");
    }

    static class CONST {
        public static final String VOTE      = "create_vote";
        public static final String CANDIDATE = "update_candidate";
        public static final String POST      = "update_post";
        public static final String NOTICE    = "update_notice";
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
