package catvote.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.LinkedList;

import catvote.Const;

import catvote.beans.RewardItem;

class Reward {
    boolean purchaseItem(String userId, int point) {
        int rowNum = 0;

        try {
            Class.forName(Const.DB_INFO.DB_CONNECTOR_NAME);
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection(Const.DB_INFO.DB_URL,
                                                           Const.DB_INFO.DB_ID,
                                                           Const.DB_INFO.DB_PW);
            Statement statement = conn.createStatement()) {
            String query = String.format("update %s set %s := %s where %s = '%s'",
                                         Const.DB_CONTENT.USER_INFO.TABLE_NAME,
                                         Const.DB_CONTENT.USER_INFO.POINT,
                                         point,
                                         Const.DB_CONTENT.USER_INFO.USER_ID,
                                         userId);

            rowNum = statement.executeUpdate(query);
        } catch (Exception ignored) {}

        return (rowNum > 0);
    }

    // return reward list
    LinkedList<RewardItem> getRewardList() {
        LinkedList<RewardItem> rewardList = new LinkedList<>();

        try {
            Class.forName(Const.DB_INFO.DB_CONNECTOR_NAME);
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection(Const.DB_INFO.DB_URL,
                                                           Const.DB_INFO.DB_ID,
                                                           Const.DB_INFO.DB_PW);
            Statement statement = conn.createStatement()) {
            ResultSet rs = statement.executeQuery("select * from " + Const.DB_CONTENT.REWARD.TABLE_NAME + ";");

            // select * from reward;
            while (rs.next()) {
                rewardList.add(new RewardItem(rs.getInt(Const.DB_CONTENT.REWARD.REWARD_ID),
                                              rs.getString(Const.DB_CONTENT.REWARD.TITLE),
                                              rs.getString(Const.DB_CONTENT.REWARD.DESCRIPTION),
                                              rs.getInt(Const.DB_CONTENT.REWARD.POINT)));
            }
        } catch (Exception ignored) {}

        return rewardList;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
