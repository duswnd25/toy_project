package catvote.main.common;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import catvote.Const;

import catvote.beans.UserItem;

import catvote.database.DatabaseManager;

public class AuthServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AuthServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        String      menu    = request.getParameter(Const.PARAMETER.AUTH_TYPE);

        if (menu.equals("logout")) {
            try {
                session.removeAttribute(Const.SESSION.IS_LOGIN);
                session.removeAttribute(Const.SESSION.IS_ADMIN);
                session.removeAttribute(Const.SESSION.USER_INFO);
                response.sendRedirect(Const.PATH.ROOT_PATH);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (menu.equals("login")) {
            String   id       = request.getParameter(Const.ATTRIBUTE.ID);
            String   pw       = request.getParameter(Const.ATTRIBUTE.PW);
            UserItem userInfo = new DatabaseManager.AUTH().checkLoginInfo(id, pw);

            if (userInfo.getId().equals("NO_USER")) {
                response.sendRedirect(Const.PATH.ROOT_PATH);
            } else {
                boolean isAdmin = userInfo.isAdmin();

                session.setAttribute(Const.SESSION.IS_ADMIN, isAdmin);
                session.setAttribute(Const.SESSION.IS_LOGIN, true);
                session.setAttribute(Const.SESSION.USER_INFO, userInfo);
                response.sendRedirect(Const.PATH.ROOT_PATH + (isAdmin
                                                              ? "/admin"
                                                              : "/index"));
            }
        } else {
            response.sendRedirect(Const.PATH.ROOT_PATH);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
