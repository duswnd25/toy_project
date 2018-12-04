<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.LinkedList"%>
<%@ page import="catvote.beans.CandidateItem"%>
<%@ page import="catvote.Const"%>
<%!@SuppressWarnings("unchecked")%>
<%
	LinkedList<CandidateItem> list = new LinkedList<>();
	String recentSelect = "";
	try {
		recentSelect = (String) request.getAttribute(Const.ATTRIBUTE.RECENT_SELECT);
		list.addAll((LinkedList<CandidateItem>) request.getAttribute(Const.ATTRIBUTE.CANDIDATE_LIST));
	} catch (Exception ignored) {

	}
%>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/public/css/snackbar.css">
<div style="margin-left: 8px; margin-right: 8px;">

	<div class="w3-col m12">
		<div class="w3-container w3-card w3-white w3-round"
			style="margin: 0 8px 8px;">
			<br>
			<h4>
				최근 선택 : <%=recentSelect%><br>투표율 : <%=request.getAttribute(Const.ATTRIBUTE.VOTE_RATE)%>
			</h4>
			<br>
		</div>
		<!-- End Middle Column -->
	</div>

	<%
		for (CandidateItem item : list) {
	%>
	<div class="w3-col m12">
		<div class="w3-container w3-card w3-white w3-round"
			style="margin: 0 8px 8px;">
			<br>
			<h4>
				<%=item.getName()%>
				<button class="w3-button w3-padding-large" title="question"
					onclick="sendQuestion(<%=item.getStudentNumber()%>)">
					<i class="far fa-question-circle"></i>
				</button>
			</h4>
			<br>
			<hr class="w3-clear">

			<%=item.getMajor()%>
			<div class="w3-row-padding" style="margin: 0 -16px"></div>

			<p><%=item.getDescription()%>
			</p>
			<hr>
			<p><%=item.getMemo()%></p>
			<hr>
			<p><%=item.getQuestion()%></p>
			<button type="button" class="w3-button w3-block w3-amber w3-section"
				onclick="window.location.href='/catvote/api/vote?<%=Const.PARAMETER.VOTE_ID%>=<%=request.getAttribute(Const.ATTRIBUTE.VOTE_ID)%>&<%=Const.PARAMETER.VOTE_SELECT%>=<%=item.getStudentNumber()%>'">
				선택</button>
		</div>
		<!-- End Middle Column -->
	</div>
	<%
		}
	%>
	<script>
	function sendQuestion(candidate){
		 var input = prompt("질문 내용을 입력해 주세요. 한 후보에게 한개의 질문만 할 수 있습니다.", "");
		 window.location.href='/catvote/api/question?candidate='+candidate+'&input='+input+"&vote_id=<%=request.getParameter("vote_id")%>";
	}</script>
</div>






