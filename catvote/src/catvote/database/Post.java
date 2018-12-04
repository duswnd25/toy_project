package catvote.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import java.text.SimpleDateFormat;

import java.util.LinkedList;

import catvote.Const;

import catvote.beans.PostItem;

class Post {

    // create or update admin post
    boolean uploadPost(PostItem post) {
        SimpleDateFormat sdf   = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String           query =
            String.format(
                "insert into %s (`%s`, `%s`, `%s`, `%s`) VALUES ('%s', '%s', '%s', '%s') ON DUPLICATE KEY UPDATE %s='%s', %s='%s', %s='%s';",
                Const.DB_CONTENT.ADMIN_POST.TABLE_NAME,
                Const.DB_CONTENT.ADMIN_POST.POST_ID,
                Const.DB_CONTENT.ADMIN_POST.TITLE,
                Const.DB_CONTENT.ADMIN_POST.CONTENT,
                Const.DB_CONTENT.ADMIN_POST.TIME,
                post.getIndex(),
                post.getTitle(),
                post.getContent(),
                sdf.format(post.getDate()),
                Const.DB_CONTENT.ADMIN_POST.TITLE,
                post.getTitle(),
                Const.DB_CONTENT.ADMIN_POST.CONTENT,
                post.getContent(),
                Const.DB_CONTENT.ADMIN_POST.TIME,
                sdf.format(post.getDate()));
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

    // return admin post list
    LinkedList<PostItem> getPostList() {
        LinkedList<PostItem> postList = new LinkedList<>();

        try {
            Class.forName(Const.DB_INFO.DB_CONNECTOR_NAME);
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection(Const.DB_INFO.DB_URL,
                                                           Const.DB_INFO.DB_ID,
                                                           Const.DB_INFO.DB_PW);
            Statement statement = conn.createStatement()) {
            ResultSet rs = statement.executeQuery("select * " + "from " + Const.DB_CONTENT.ADMIN_POST.TABLE_NAME + ";");

            // select post_id from admin_post;
            while (rs.next()) {
                PostItem post = new PostItem();

                post.setIndex(rs.getInt(Const.DB_CONTENT.ADMIN_POST.POST_ID));
                post.setTitle(rs.getString(Const.DB_CONTENT.ADMIN_POST.TITLE));
                post.setContent(rs.getString(Const.DB_CONTENT.ADMIN_POST.CONTENT));
                post.setDate(rs.getTimestamp(Const.DB_CONTENT.ADMIN_POST.TIME));
                postList.add(post);
            }
        } catch (Exception ignored) {}

        return postList;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
