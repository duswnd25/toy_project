package catvote.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import java.text.SimpleDateFormat;

import java.util.LinkedList;

import catvote.Const;

import catvote.beans.NoticeItem;

class Notice {

    // update or create notice
    boolean uploadNotice(NoticeItem notice) {
        SimpleDateFormat sdf   = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
        String           query =
            String.format(
                "insert into %s (`%s`, `%s`, `%s`, `%s`, `%s`, `%s`) VALUES (%s, '%s', '%s', '%s', '%s', %s) ON DUPLICATE KEY UPDATE %s='%s', %s='%s', %s='%s', %s='%s', %s=%s;",
                Const.DB_CONTENT.NOTICE.TABLE_NAME,
                Const.DB_CONTENT.NOTICE.NOTICE_ID,
                Const.DB_CONTENT.NOTICE.TITLE,
                Const.DB_CONTENT.NOTICE.CONTENT,
                Const.DB_CONTENT.NOTICE.START,
                Const.DB_CONTENT.NOTICE.END,
                Const.DB_CONTENT.NOTICE.IS_PRIMARY,
                notice.getIndex(),
                notice.getTitle(),
                notice.getContent(),
                sdf.format(notice.getStart()),
                sdf.format(notice.getEnd()),
                notice.isPrimary()
                ? 1
                : 0,
                Const.DB_CONTENT.NOTICE.TITLE,
                notice.getTitle(),
                Const.DB_CONTENT.NOTICE.CONTENT,
                notice.getContent(),
                Const.DB_CONTENT.NOTICE.START,
                sdf.format(notice.getStart()),
                Const.DB_CONTENT.NOTICE.END,
                sdf.format(notice.getEnd()),
                Const.DB_CONTENT.NOTICE.IS_PRIMARY,
                notice.isPrimary()
                ? 1
                : 0);

        System.out.println(query);

        int resultCount = 0;

        try {
            Class.forName(Const.DB_INFO.DB_CONNECTOR_NAME);
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection(Const.DB_INFO.DB_URL,
                                                           Const.DB_INFO.DB_ID,
                                                           Const.DB_INFO.DB_PW);
            Statement statement = conn.createStatement()) {
            resultCount = statement.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultCount != 0;
    }

    // return notice list
    LinkedList<NoticeItem> getNoticeList() {
        LinkedList<NoticeItem> noticeList = new LinkedList<>();

        try {
            Class.forName(Const.DB_INFO.DB_CONNECTOR_NAME);
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection(Const.DB_INFO.DB_URL,
                                                           Const.DB_INFO.DB_ID,
                                                           Const.DB_INFO.DB_PW);
            Statement statement = conn.createStatement()) {
            String    query = String.format("select * from %s;", Const.DB_CONTENT.NOTICE.TABLE_NAME);
            ResultSet rs    = statement.executeQuery(query);

            while (rs.next()) {
                NoticeItem temp = new NoticeItem();

                temp.setIndex(Integer.parseInt(rs.getString(Const.DB_CONTENT.NOTICE.NOTICE_ID)));
                temp.setTitle(rs.getString(Const.DB_CONTENT.NOTICE.TITLE));
                temp.setContent(rs.getString(Const.DB_CONTENT.NOTICE.CONTENT));
                temp.setStart(rs.getTimestamp(Const.DB_CONTENT.NOTICE.START));
                temp.setEnd(rs.getTimestamp(Const.DB_CONTENT.NOTICE.END));
                temp.setPrimary(rs.getInt(Const.DB_CONTENT.NOTICE.IS_PRIMARY) == 1);
                noticeList.add(temp);
            }
        } catch (Exception ignored) {}

        return noticeList;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
