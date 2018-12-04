package catvote.api;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import catvote.Const;

import catvote.beans.UserItem;

import catvote.database.DatabaseManager;

public class VoteApiServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");

        String  userId, select,
                voteId    = "";
        boolean isSuccess = false;

        try {
            HttpSession session = request.getSession();

            userId    = ((UserItem) session.getAttribute(Const.ATTRIBUTE.USER_INFO)).getId();
            select    = request.getParameter(Const.PARAMETER.VOTE_SELECT);
            voteId    = request.getParameter(Const.PARAMETER.VOTE_ID);
            isSuccess = new DatabaseManager.VOTE().updateUserVoteStatus(voteId, userId, select);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            response.sendRedirect(Const.PATH.ROOT_PATH + (isSuccess
                                                          ? "/vote?vote_id=" + voteId
                                                          : ""));
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
