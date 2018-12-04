package catvote.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import catvote.Const;

import catvote.beans.UserItem;

class Auth {
    UserItem checkLoginInfo(String id, String pw) {
        UserItem userInfo = new UserItem();

        try {
            Class.forName(Const.DB_INFO.DB_CONNECTOR_NAME);
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection(Const.DB_INFO.DB_URL,
                                                           Const.DB_INFO.DB_ID,
                                                           Const.DB_INFO.DB_PW);
            Statement statement = conn.createStatement()) {
            String query = "select * from " + Const.DB_CONTENT.USER_INFO.TABLE_NAME + " where "
                           + Const.DB_CONTENT.USER_INFO.USER_ID + " = '" + id + "' and "
                           + Const.DB_CONTENT.USER_INFO.PASSWORD + " = '" + pw + "';";
            ResultSet rs = statement.executeQuery(query);

            rs.last();

            if (rs.getRow() == 0) {
                userInfo.setId("NO_USER");
            } else {
                rs.beforeFirst();

                if (rs.next()) {
                    userInfo.setId(rs.getString(Const.DB_CONTENT.USER_INFO.USER_ID));
                    userInfo.setPw(rs.getString(Const.DB_CONTENT.USER_INFO.PASSWORD));
                    userInfo.setPoint(rs.getInt(Const.DB_CONTENT.USER_INFO.POINT));
                    userInfo.setGroup(rs.getString(Const.DB_CONTENT.USER_INFO.GROUP));
                    userInfo.setAdmin(rs.getInt(Const.DB_CONTENT.USER_INFO.IS_ADMIN) == 1);
                }
            }
        } catch (Exception ignored) {}

        return userInfo;
    }

    int getUserPoint(String id) {
        int point = 0;

        try {
            Class.forName(Const.DB_INFO.DB_CONNECTOR_NAME);
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection(Const.DB_INFO.DB_URL,
                                                           Const.DB_INFO.DB_ID,
                                                           Const.DB_INFO.DB_PW);
            Statement statement = conn.createStatement()) {
            ResultSet rs = statement.executeQuery("select " + Const.DB_CONTENT.USER_INFO.POINT + " from "
                                                  + Const.DB_CONTENT.USER_INFO.TABLE_NAME + " where "
                                                  + Const.DB_CONTENT.USER_INFO.USER_ID + " = '" + id + "';");

            // select point from user where user_id = 'id';
            if (rs.next()) {
                point = rs.getInt("point");
            }
        } catch (Exception ignored) {}

        return point;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
