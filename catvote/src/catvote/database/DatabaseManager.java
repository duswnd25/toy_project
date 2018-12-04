package catvote.database;

import java.util.LinkedList;

import catvote.beans.CandidateItem;
import catvote.beans.NoticeItem;
import catvote.beans.PostItem;
import catvote.beans.QuestionItem;
import catvote.beans.RewardItem;
import catvote.beans.UserItem;
import catvote.beans.VoteItem;
import catvote.beans.VoteLogItem;

public class DatabaseManager {
    public DatabaseManager() {}

    public static class AUTH {
        public UserItem checkLoginInfo(String id, String pw) {
            return new Auth().checkLoginInfo(id, pw);
        }

        public boolean purchaseItem(String userId, int point) {
            return new Reward().purchaseItem(userId, point);
        }

        public int getUserPoint(String id) {
            return new Auth().getUserPoint(id);
        }
    }


    public static class NOTICE {
        public NOTICE() {}

        public boolean uploadNotice(NoticeItem post) {
            return new Notice().uploadNotice(post);
        }

        public LinkedList<NoticeItem> getNoticeList() {
            return new Notice().getNoticeList();
        }
    }


    public static class POST {
        public boolean uploadPost(PostItem post) {
            return new Post().uploadPost(post);
        }

        public LinkedList<PostItem> getPostList() {
            return new Post().getPostList();
        }
    }


    public static class REWARD {
        public REWARD() {}

        public boolean purchaseItem(String userId, int point) {
            return new Reward().purchaseItem(userId, point);
        }

        public LinkedList<RewardItem> getRewardList() {
            return new Reward().getRewardList();
        }
    }


    public static class VOTE {
        public boolean createVote(VoteItem item) {
            return new Vote().createVote(item);
        }

        public boolean updateCandidateInfo(CandidateItem item) {
            return new Vote().updateCandidateInfo(item);
        }

        public boolean updateUserVoteStatus(String voteId, String userId, String select) {
            return new Vote().updateUserVoteStatus(voteId, userId, select);
        }

        public LinkedList<CandidateItem> getAllCandidate() {
            return new Vote().getAllCandidate();
        }

        public LinkedList<VoteLogItem> getAllVoteLog(int voteId) {
            return new Vote().getAllVoteLog(voteId);
        }

        public LinkedList<VoteItem> getAvailableVoteList(String userGroup) {
            return new Vote().getAvailableVoteList(userGroup);
        }

        public CandidateItem getCandidate(String studentNumber) {
            return new Vote().getCandidate(studentNumber);
        }

        public String getCandidateName(String id) {
            return new Vote().getCandidateName(id);
        }

        public VoteItem getNextVote() {
            return new Vote().getNextVote();
        }

        public String getRecentSelect(String voteId, String userId) {
            return new Vote().getRecentSelect(voteId, userId);
        }

        public boolean isUserAlreadyVote(int i, String userId) {
            return new Vote().isUserAlreadyVote(i, userId);
        }

        public VoteItem getVote(String voteId) {
            return new Vote().getVote(voteId);
        }

        public LinkedList<VoteItem> getVoteList() {
            return new Vote().getVoteList();
        }

        public int getVoteTargetNum(int voteId) {
            return new Vote().getVoteTargetNum(voteId);
        }

        public int getVoteUserNum(int voteid) {
            return new Vote().getVoteUserNum(voteid);
        }
        
        public boolean updateQuestion(QuestionItem item) {
        	return new Vote().updateQuestion(item);
        }
        
        public LinkedList<QuestionItem> getQuestionList (String candidate){
        	return new Vote().getQuestionList(candidate);
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
