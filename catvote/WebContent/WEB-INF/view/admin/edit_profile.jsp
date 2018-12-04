<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.LinkedList"%>
<%@ page import="catvote.beans.CandidateItem"%>
<%@ page import="catvote.Const"%>
<%!@SuppressWarnings("unchecked")%>
<%
	String userSelectIndex = (String) request.getAttribute(Const.ATTRIBUTE.CANDIDATE_INDEX);
	LinkedList<CandidateItem> list = new LinkedList<>();
	CandidateItem selectCandidate = new CandidateItem();
	selectCandidate.setName("");
	selectCandidate.setStudentNumber("");
	selectCandidate.setMajor("");
	selectCandidate.setDescription("");
	selectCandidate.setMemo("");
	try {
		list.addAll((LinkedList<CandidateItem>) request.getAttribute(Const.ATTRIBUTE.CANDIDATE_LIST));
	} catch (Exception e) {
		e.getLocalizedMessage();
	}

	for (CandidateItem item : list) {
		if (userSelectIndex.contains(item.getStudentNumber())) {
			selectCandidate.setName(item.getName());
			selectCandidate.setStudentNumber(item.getStudentNumber());
			selectCandidate.setMajor(item.getMajor());
			selectCandidate.setDescription(item.getDescription());
			selectCandidate.setMemo(item.getMemo());
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
					<h5 class="title">후보자 정보 수정/등록</h5>
				</div>
				<div class="card-body">
					<select id="select-candidate" class="form-control"
						style="margin-bottom: 16px;" onchange="changeCandidateInfo()">
						<option value="0">후보자 추가</option>
						<%
							for (CandidateItem item : list) {
						%>
						<option value="<%=item.getStudentNumber()%>"
							<%if (userSelectIndex.contains(item.getStudentNumber())) {%>
							selected <%} else {
				}%>><%=item.getName()%></option>
						<%
							}
						%>
					</select>
					<form action="/catvote/api/admin?type=update_candidate"
						id="candidate_profile" method="post">
						<div class="row">
							<div class="col-md-3 pr-1">
								<div class="form-group">
									<label>학번</label> <input type="text"
										<%if (selectCandidate.getStudentNumber().equals("")) {
			} else {%>
										readonly <%}%> class="form-control" placeholder="학번"
										name="<%=Const.PARAMETER.STUDENT_NUMBER%>"
										style="text-align: center;"
										value="<%=selectCandidate.getStudentNumber()%>">
								</div>
							</div>
							<div class="col-md-3 px-1">
								<div class="form-group">
									<label>후보 이름</label> <input type="text" class="form-control"
										name="<%=Const.PARAMETER.NAME%>" placeholder="후보 이름"
										value="<%=selectCandidate.getName()%>">
								</div>
							</div>
							<div class="col-md-6 pl-1">
								<div class="form-group">
									<label>전공</label> <input type="text" class="form-control"
										name="<%=Const.PARAMETER.MAJOR%>"
										value="<%=selectCandidate.getMajor()%>" placeholder="전공">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label>약력</label>
									<textarea rows="4" cols="80" class="form-control"
										name="<%=Const.PARAMETER.DESCRIPTION%>"
										form="candidate_profile" placeholder="후보자 약력"><%=selectCandidate.getDescription()%></textarea>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label>메모</label>
									<textarea rows="4" cols="80" class="form-control"
										name="<%=Const.PARAMETER.MEMO%>" form="candidate_profile"
										placeholder="메모"><%=selectCandidate.getMemo()%></textarea>
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
	function changeCandidateInfo() {
		let langSelect = document.getElementById("select-candidate");

		// select element에서 선택된 option의 value가 저장된다.
		let selectValue = langSelect.options[langSelect.selectedIndex].value;
		window.location.href = '/catvote/admin?menu=edit_profile&student_number='
				+ selectValue;
	}
</script>



