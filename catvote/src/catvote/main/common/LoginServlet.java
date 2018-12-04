package catvote.main.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import catvote.Const;

import catvote.beans.UserItem;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        boolean     isAdmin = false,
                    isLogin = false;

        try {
            isAdmin = (boolean) session.getAttribute(Const.SESSION.IS_ADMIN);
            isLogin = (boolean) session.getAttribute(Const.SESSION.IS_LOGIN);
        } catch (Exception e) {
            isAdmin = false;
            isLogin = false;
        } finally {
            if (isAdmin && ((UserItem) session.getAttribute(Const.SESSION.USER_INFO) != null)) {
                response.sendRedirect(Const.PATH.ROOT_PATH + "/admin");
            } else if (isLogin && ((UserItem) session.getAttribute(Const.SESSION.USER_INFO) != null)) {
                response.sendRedirect("/catvote/index");
            } else {
                getServletContext().getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
