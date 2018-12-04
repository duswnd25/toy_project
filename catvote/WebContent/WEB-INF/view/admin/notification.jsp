<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.LinkedList"%>
<%@ page import="catvote.beans.NoticeItem"%>
<%@ page import="catvote.Const"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%!@SuppressWarnings("unchecked")%>
<%
	int userSelectIndex = Integer.parseInt((String) request.getAttribute(Const.ATTRIBUTE.NOTICE_INDEX));
	LinkedList<NoticeItem> list = new LinkedList<>();
	NoticeItem selectPostItem = new NoticeItem();
	selectPostItem.setTitle("");
	selectPostItem.setContent("");
	selectPostItem.setStart(Calendar.getInstance().getTime());
	selectPostItem.setEnd(Calendar.getInstance().getTime());

	try {
		list.addAll((LinkedList<NoticeItem>) request.getAttribute(Const.ATTRIBUTE.NOTICE_LIST));
	} catch (Exception e) {
		e.getLocalizedMessage();
	}

	for (NoticeItem item : list) {
		if (userSelectIndex == item.getIndex()) {
			selectPostItem.setIndex(item.getIndex());
			selectPostItem.setTitle(item.getTitle());
			selectPostItem.setContent(item.getContent());
			selectPostItem.setStart(item.getStart());
			selectPostItem.setEnd(item.getEnd());
			selectPostItem.setPrimary(item.isPrimary());
		}
	}
%>
<style>
input {
	text-align: center;
}
</style>

<div class="content">
	<div class="row">
		<div class="col-md-12">
			<div class="card">
				<div class="card-header">
					<h5 class="title">공지사항 수정/등록</h5>
				</div>
				<div class="card-body">
					<select id="select-notice" class="form-control"
						style="margin-bottom: 16px;" onchange="changePost()">
						<option value="0">게시글 추가</option>
						<%
							for (NoticeItem item : list) {
						%>
						<option value="<%=item.getIndex()%>"
							<%if (userSelectIndex == item.getIndex()) {%> selected
							<%} else {
				}%>><%=item.getTitle()%></option>
						<%
							}
						%>
					</select>
					<form action="/catvote/api/admin?type=update_notice"
						id="candidate_profile" method="post">
						<div class="row" style="padding: 12px;">
							<div class="col-md-12 px-1">
								<div class="form-group">
									<label>제목</label> <input type="text" class="form-control"
										name="<%=Const.PARAMETER.TITLE%>" placeholder="제목"
										value="<%=selectPostItem.getTitle()%>">
								</div>
							</div>
						</div>
						<div class="row" style="padding: 12px;">
							<div class="col-md-2 px-1">
								<div class="form-group">
									<label>ID</label> <input type="text" class="form-control"
										name="<%=Const.PARAMETER.NOTICE_ID%>" placeholder="ID"
										readonly
										value="<%=(userSelectIndex == 0) ? list.size() + 1 : selectPostItem.getIndex()%>">
								</div>
							</div>
							<div class="col-md-2 px-1">
								<div class="form-group">
									<label>중요 OFF/ON</label> <input type="range" name="points"
										min="0" step="1" max="1" class="form-control"
										name="<%=Const.PARAMETER.NOTICE_PRIMARY%>"
										value="<%=(selectPostItem.isPrimary()) ? 1 : 0%>">
								</div>
							</div>
							<%
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
							%>
							<div class="col-md-4 px-1">
								<div class="form-group">
									<label>시작일</label> <input type="datetime-local"
										class="form-control"
										name="<%=Const.PARAMETER.NOTICE_DATE_START%>" placeholder="제목"
										value="<%=sdf.format(selectPostItem.getStart())%>">
								</div>
							</div>
							<div class="col-md-4 px-1">
								<div class="form-group">
									<label>만료일</label> <input type="datetime-local"
										class="form-control"
										name="<%=Const.PARAMETER.NOTICE_DATE_END%>" placeholder="제목"
										value="<%=sdf.format(selectPostItem.getEnd())%>">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label>내용</label>
									<textarea rows="4" cols="80" class="form-control"
										name="<%=Const.PARAMETER.CONTENT%>" form="candidate_profile"
										placeholder="메모"><%=selectPostItem.getContent()%></textarea>
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

<script>
	function changePost() {
		let langSelect = document.getElementById("select-notice");

		// select element에서 선택된 option의 value가 저장된다.
		let selectValue = langSelect.options[langSelect.selectedIndex].value;
		window.location.href = '/catvote/admin?menu=notification&notice_id='
				+ selectValue;
	}
</script>