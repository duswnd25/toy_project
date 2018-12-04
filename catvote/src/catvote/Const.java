package catvote;

public class Const {
    public static class ATTRIBUTE {
        public static final String NEXT_VOTE         = "NEXT_VOTE";
        public static final String AVAILABLE_VOTE    = "AVAILABLE_VOTE";
        public static final String PRIMARY_NOTICE    = "PRIMARY_NOTICE";
        public static final String TODAY             = "TODAY";
        public static final String VOTE_RESULT_ALERT = "VOTE_RESULT_ALERT";
        public static final String NOTICE            = "NOTICE";
        public static final String MANAGER_POST      = "MANAGER_POST";
        public static final String VOTE_LIST         = "VOTE_LIST";
        public static final String INDEX             = "INDEX";
        public static final String ID                = "ID";
        public static final String PW                = "PW";
        public static final String REWARD_LIST       = "REWARD_LIST";
        public static final String USER_INFO         = "USER_INFO";
        public static final String CANDIDATE_LIST    = "CANDIDATE_LIST";
        public static final String CANDIDATE_INDEX   = "CANDIDATE_INDEX";
        public static final String POST_INDEX        = "POST_INDEX";
        public static final String POST_LIST         = "POST_LIST";
        public static final String NOTICE_INDEX      = "NOTICE_INDEX";
        public static final String NOTICE_LIST       = "NOTICE_LIST";
        public static final String VOTE_ID           = "VOTE_ID";
        public static final String VOTE_SIZE         = "VOTE_SIZE";
        public static final String RECENT_SELECT     = "RECENT_SELECT";
        public static final String TIMELINE_LIST     = "TIMELINE_LIST";
        public static final String VOTE_RATE         = "VOTE_RATE";
    }


    public static class DB_CONTENT {
        public static class ADMIN_POST {
            public static final String TABLE_NAME = "admin_post";
            public static final String POST_ID    = "post_id";
            public static final String ISPRIMARY  = "primary";
            public static final String CONTENT    = "content";
            public static final String TIME       = "time";
            public static final String TITLE      = "title";
        }


        public static class CANDIDATE {
            public static final String TABLE_NAME     = "candidate_info";
            public static final String NAME           = "name";
            public static final String MAJOR          = "major";
            public static final String INDEX          = "index";
            public static final String DESCRIPTION    = "description";
            public static final String MEMO           = "memo";
            public static final String STUDENT_NUMBER = "student_number";
        }


        public static class NOTICE {
            public static final String TABLE_NAME = "notice";
            public static final String NOTICE_ID  = "notice_id";
            public static final String CONTENT    = "content";
            public static final String TITLE      = "title";
            public static final String START      = "start";
            public static final String END        = "end";
            public static final String IS_PRIMARY = "is_primary";
        }


        public static class QUESTION {
            public static final String TABLE_NAME       = "question_log";
            public static final String USER_ID          = "user_id";
            public static final String QUESTION         = "question";
            public static final String CANDIDATE_NUMBER = "candidate_number";
            public static final String ID               = "id";
            public static final String TIMESTAMP        = "timestamp";
        }


        public static class REWARD {
            public static final String TABLE_NAME  = "reward";
            public static final String REWARD_ID   = "reward_id";
            public static final String TITLE       = "title";
            public static final String DESCRIPTION = "description";
            public static final String POINT       = "point";
        }


        public static class USER_INFO {
            public static final String TABLE_NAME = "user";
            public static final String USER_ID    = "user_id";
            public static final String PASSWORD   = "user_pw";
            public static final String POINT      = "point";
            public static final String IS_ADMIN   = "is_admin";
            public static final String GROUP      = "group";
        }


        public static class VOTE {
            public static final String TABLE_NAME = "vote";
            public static final String VOTE_ID    = "vote_id";
            public static final String TITLE      = "title";
            public static final String START_DATE = "start_date";
            public static final String END_DATE   = "end_date";
            public static final String TARGET     = "target";
            public static final String CANDIDATE  = "candidate";
        }


        public static class VOTE_LOG {
            public static final String TABLE_NAME = "vote_log";
            public static final String ID         = "id";
            public static final String VOTE_ID    = "vote_id";
            public static final String USER_ID    = "user_id";
            public static final String USERSELECT = "user_select";
            public static final String TIMESTAMP  = "timestamp";
        }
    }


    public static class DB_INFO {
        public static final String DB_ID             = "root";
        public static final String DB_NAME           = "catvote";
        public static final String DB_PW             = "";
        public static final String DB_URL            =
            "jdbc:mysql://localhost:3306/catvote?autoReconnect=true&useSSL=false";
        public static final String DB_CONNECTOR_NAME = "com.mysql.jdbc.Driver";
    }


    public static class PARAMETER {
        public static final String MENU              = "menu";
        public static final String INDEX             = "index";
        public static final String VOTE_API_TYPE     = "type";
        public static final String NAME              = "name";
        public static final String MAJOR             = "major";
        public static final String DESCRIPTION       = "description";
        public static final String MEMO              = "memo";
        public static final String STUDENT_NUMBER    = "student_number";
        public static final String TITLE             = "title";
        public static final String CONTENT           = "content";
        public static final String POST_ID           = "post_id";
        public static final String NOTICE_ID         = "notice_id";
        public static final String NOTICE_DATE_START = "date_start";
        public static final String NOTICE_DATE_END   = "date_end";
        public static final String NOTICE_PRIMARY    = "points";
        public static final String VOTE_CANDIDATE    = "vote_candidate";
        public static final String VOTE_TARGET       = "vote_target";
        public static final String AUTH_TYPE         = "auth_type";
        public static final String VOTE_ID           = "vote_id";
        public static final String USER_ID           = "user_id";
        public static final String REWARD_POINT      = "reward_point";
        public static final String REWARD_RESULT     = "reward_resut";
        public static final String CANDIDATE_LIST    = "candidate_list";
        public static final String VOTE_SELECT       = "vote_select";
    }


    public static class PATH {
        public static final String TEMPLATE_PATH_USER  = "/WEB-INF/view/user/basic_layout.jsp";
        public static final String ROOT_PATH           = "/catvote";
        public static final String TEMPLATE_PATH_ADMIN = "/WEB-INF/view/admin/basic_layout.jsp";
        public static final String PAGE                = "PAGE";
    }


    public static class SESSION {
        public static final String IS_LOGIN  = "IS_LOGIN";
        public static final String IS_ADMIN  = "IS_ADMIN";
        public static final String USER_INFO = "USER_INFO";
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
