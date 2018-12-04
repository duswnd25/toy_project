package catvote.utils;

import java.io.UnsupportedEncodingException;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import catvote.Const;

import catvote.beans.NoticeItem;
import catvote.beans.UserItem;
import catvote.beans.VoteItem;

import catvote.database.DatabaseManager;

public class ServletInit {
    public ServletInit() {}

    public HttpServletRequest initAdminRequest(HttpServletRequest request) {
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return request;
    }

    public HttpServletRequest initDefaultRequest(HttpServletRequest request) {
        try {
            request.setCharacterEncoding("UTF-8");

            // =================== DEFAULT INFORMATION ===================
            UserItem userInfo = (UserItem) request.getSession().getAttribute(Const.SESSION.USER_INFO);

            // USER INFORMATINO
            request.setAttribute(Const.ATTRIBUTE.USER_INFO, request.getSession().getAttribute(Const.SESSION.USER_INFO));

            DatabaseManager.VOTE voteDb            = new DatabaseManager.VOTE();
            LinkedList<VoteItem> availableVoteList = voteDb.getAvailableVoteList(userInfo.getGroup());

            for (int index = 0; index < availableVoteList.size(); index++) {
                availableVoteList.get(index)
                                 .setUserAlreadyVote(voteDb.isUserAlreadyVote(availableVoteList.get(index).getId(),
                                                                              userInfo.getId()));
            }

            // AVAILABLE VOTE LIST
            request.setAttribute(Const.ATTRIBUTE.AVAILABLE_VOTE, availableVoteList);

            // NEXT VOTE
            request.setAttribute(Const.ATTRIBUTE.NEXT_VOTE, new DatabaseManager.VOTE().getNextVote());

            // TIME
            request.setAttribute(Const.ATTRIBUTE.TODAY,
                                 new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime()));

            // NOTICE LIST
            LinkedList<NoticeItem> noticeList = new DatabaseManager.NOTICE().getNoticeList();

            request.setAttribute(Const.ATTRIBUTE.NOTICE, noticeList);

            // PRIMARY NOTICE LIST
            request.setAttribute(Const.ATTRIBUTE.PRIMARY_NOTICE, noticeList);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return request;
    }

    public HttpServletResponse initDefaultResponse(HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");

        return response;
    }

    public HttpServletResponse initDefaultResponse(HttpServletResponse response, int interval) {
        response.setCharacterEncoding("UTF-8");
        response.setIntHeader("Refresh", interval);

        return response;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
