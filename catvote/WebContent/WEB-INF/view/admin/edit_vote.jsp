<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="catvote.Const"%>
<%@ page import="catvote.beans.VoteItem"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%
	int voteId = Integer.parseInt((String) request.getAttribute(Const.ATTRIBUTE.VOTE_ID));
	int index = (int) request.getAttribute(Const.ATTRIBUTE.VOTE_SIZE);
	VoteItem voteItem = (VoteItem) request.getAttribute(Const.ATTRIBUTE.VOTE_LIST);
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
%>
<div class="content">
	<div class="row">
		<div class="col-md-12">
			<div class="card">
				<div class="card-header">
					<h5 class="title">투표 등록</h5>
				</div>
				<div class="card-body">

					<form action="/catvote/api/admin?type=create_vote"
						id="candidate_profile" method="post">
						<div class="row" style="padding: 12px;">
							<div class="col-md-3 px-1">
								<div class="form-group">
									<label>ID</label> <input type="text" class="form-control"
										readonly name="<%=Const.PARAMETER.VOTE_ID%>"
										value="<%=voteId == 0 ? index : voteItem.getId()%>">
								</div>
							</div>
							<div class="col-md-9 px-1">
								<div class="form-group">
									<label>제목</label> <input type="text" class="form-control"
										name="<%=Const.PARAMETER.TITLE%>" placeholder="제목"
										value="<%=voteId == 0 ? "" : voteItem.getTitle()%>">
								</div>
							</div>
						</div>
						<div class="row" style="padding: 12px;">
							<div class="col-md-4 px-1">
								<div class="form-group">
									<label>시작일</label> <input type="datetime-local"
										class="form-control"
										name="<%=Const.PARAMETER.NOTICE_DATE_START%>" placeholder="제목"
										value="<%=voteId == 0 ? "" : sdf.format(voteItem.getStart())%>">
								</div>
							</div>
							<div class="col-md-4 px-1">
								<div class="form-group">
									<label>만료일</label> <input type="datetime-local"
										class="form-control"
										name="<%=Const.PARAMETER.NOTICE_DATE_END%>" placeholder="제목"
										value="<%=voteId == 0 ? "" : sdf.format(voteItem.getEnd())%>">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label>후보</label>
									<textarea rows="4" cols="80" class="form-control"
										name="<%=Const.PARAMETER.VOTE_CANDIDATE%>"
										form="candidate_profile" placeholder="후보1, 후보2, etc..."><%=voteId == 0 ? "" : voteItem.getCandidate()%></textarea>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label>대상</label>
									<textarea rows="4" cols="80" class="form-control"
										name="<%=Const.PARAMETER.VOTE_TARGET%>"
										form="candidate_profile" placeholder="동아리, 총학, etc..."><%=voteId == 0 ? "" : voteItem.getTarget()%></textarea>
								</div>
							</div>
						</div>
					</form>
					<div class="row" style="text-align: center; margin: auto;">
						<button type="submit" form="candidate_profile"
							style="width: 48%; margin: auto;" class="btn btn-success"
							title="Accept">
							<i class="fa fa-check"></i>
						</button>
						<button type="reset" form="candidate_profile"
							style="width: 48%; margin: auto;" class="btn btn-danger"
							title="Decline">
							<i class="fa fa-times"></i>
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>