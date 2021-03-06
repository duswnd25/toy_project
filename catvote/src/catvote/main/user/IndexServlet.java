package catvote.main.user;

import java.io.IOException;

import java.util.Collections;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import catvote.Const;

import catvote.beans.PostItem;

import catvote.database.DatabaseManager;

import catvote.utils.ServletInit;

public class IndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public IndexServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletInit servletInit = new ServletInit();

        request  = servletInit.initDefaultRequest(request);
        response = servletInit.initDefaultResponse(response, 60);

        // =================== CONTENT INFO ===================
        // ADMIN POST LIST
        LinkedList<PostItem> postList = new DatabaseManager.POST().getPostList();

        Collections.sort(postList);
        request.setAttribute(Const.ATTRIBUTE.MANAGER_POST, postList);

        HttpSession session = request.getSession();

        if (session.getAttribute(Const.SESSION.IS_LOGIN) == null) {
            response.sendRedirect(Const.PATH.ROOT_PATH);
        } else {
            request.setAttribute(Const.PATH.PAGE, "index");
            getServletContext().getRequestDispatcher(Const.PATH.TEMPLATE_PATH_USER).forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
