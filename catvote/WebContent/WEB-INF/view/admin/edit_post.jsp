<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.LinkedList"%>
<%@ page import="catvote.beans.PostItem"%>
<%@ page import="catvote.Const"%>
<%!@SuppressWarnings("unchecked")%>

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
					<h5 class="title">게시글 수정/등록</h5>
				</div>
				<div class="card-body">
					<select id="select-post" class="form-control"
						style="margin-bottom: 16px;" onchange="changePost()">
						<option value="0">게시글 추가</option>
						<%
							int userSelectIndex = Integer.parseInt((String) request.getAttribute(Const.ATTRIBUTE.POST_INDEX));
							LinkedList<PostItem> list = new LinkedList<>();
							PostItem selectPostItem = new PostItem();
							selectPostItem.setTitle("");
							selectPostItem.setContent("");

							try {
								list.addAll((LinkedList<PostItem>) request.getAttribute(Const.ATTRIBUTE.POST_LIST));
							} catch (Exception e) {
								e.getLocalizedMessage();
							}

							for (PostItem item : list) {
								if (userSelectIndex == item.getIndex()) {
									selectPostItem.setIndex(item.getIndex());
									selectPostItem.setTitle(item.getTitle());
									selectPostItem.setContent(item.getContent());
									selectPostItem.setDate(item.getDate());
								}
							}

							for (PostItem item : list) {
						%>
						<option value="<%=item.getIndex()%>"
							<%if (userSelectIndex == item.getIndex()) {%> selected
							<%} else {
				}%>><%=item.getTitle()%></option>
						<%
							}
						%>
					</select>
					<form action="/catvote/api/admin?type=update_post"
						id="candidate_profile" method="post">
						<div class="row" style="padding: 12px;">
							<div class="col-md-3 px-1">
								<div class="form-group">
									<label>ID</label> <input type="text" class="form-control"
										name="<%=Const.PARAMETER.POST_ID%>" placeholder="ID" readonly
										value="<%=(userSelectIndex == 0) ? list.size() + 1 : selectPostItem.getIndex()%>">
								</div>
							</div>
							<div class="col-md-9 px-1">
								<div class="form-group">
									<label>제목</label> <input type="text" class="form-control"
										name="<%=Const.PARAMETER.TITLE%>" placeholder="제목"
										value="<%=selectPostItem.getTitle()%>">
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
		let langSelect = document.getElementById("select-post");

		// select element에서 선택된 option의 value가 저장된다.
		let selectValue = langSelect.options[langSelect.selectedIndex].value;
		window.location.href = '/catvote/admin?menu=edit_post&post_id='
				+ selectValue;
	}
</script>