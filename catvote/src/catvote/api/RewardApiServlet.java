package catvote.api;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import catvote.Const;

import catvote.beans.UserItem;

import catvote.database.DatabaseManager;

public class RewardApiServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    String                    userId           = "";
    int                       point            = 0;
    boolean                   isSuccess        = false;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        try {
            HttpSession session = request.getSession();
            UserItem    user    = (UserItem) session.getAttribute(Const.SESSION.USER_INFO);

            userId = user.getId();
            point  = Integer.parseInt(request.getParameter(Const.PARAMETER.REWARD_POINT));

            System.out.println(point);
            System.out.println(user.getPoint());
            
            if (user.getPoint() >= point) {
            	System.out.println(point);
                isSuccess = new DatabaseManager.REWARD().purchaseItem(userId, user.getPoint() - point);
                session.setAttribute(Const.SESSION.USER_INFO,
                                     new DatabaseManager.AUTH().checkLoginInfo(user.getId(), user.getPw()));
            }else {
            	isSuccess = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            redirect(response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

    private void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect(Const.PATH.ROOT_PATH + "/reward?" + Const.PARAMETER.REWARD_RESULT + "=" + isSuccess);
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
