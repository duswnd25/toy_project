<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.LinkedList"%>
<%@ page import="catvote.beans.RewardItem"%>
<%@ page import="catvote.Const"%>
<%!@SuppressWarnings("unchecked")%>
<%
	LinkedList<RewardItem> list = new LinkedList<>();
	try {
		list.addAll((LinkedList<RewardItem>) request.getAttribute(Const.ATTRIBUTE.REWARD_LIST));
	} catch (Exception ignored) {

	}
%>
<link rel="stylesheet" href="/catvote/public/css/snackbar.css">
<div style="margin-left: 8px; margin-right: 8px;">
	<%
		for (RewardItem item : list) {
	%>
	<div class="w3-col m4">
		<div class="w3-container w3-card w3-white w3-round"
			style="margin: 0 8px 8px;">
			<br>
			<h4>
				<%=item.getTitle()%>
			</h4>
			<br>
			<hr class="w3-clear">

			<div class="w3-row-padding" style="margin: 0 -16px">
				<img src="/catvote/public/image/reward/<%=item.getId()%>.png"
					style="width: 100%" alt="Nature" class="w3-margin-bottom">
			</div>

			<p><%=item.getDescription()%>
			</p>

			<button type="button" class="w3-button w3-block w3-amber w3-section"
				onclick="window.location.href='/catvote/api/reward?reward_point=<%=item.getPoint()%>'">
				<i class="fa fa-credit-card"></i>
				<%=item.getPoint()%>
				Point
			</button>
		</div>
		<!-- End Middle Column -->
	</div>
	<%
		}
	%>
</div>
<!-- The actual snackbar -->
<div id="snackbar">결제되었습니다.</div>

<script src="/catvote/public/js/core/jquery.min.js"></script>
<%
	if (request.getParameter(Const.PARAMETER.REWARD_RESULT) != null) {
%><script>
	let x = document.getElementById("snackbar");
	x.innerText =
<%=request.getParameter(Const.PARAMETER.REWARD_RESULT).contains("true") ? "'결제 성공'" : "'결제 실패'"%>
	;
	x.className = "show";
	setTimeout(function() {
		x.className = x.className.replace("show", "");
	}, 3000);
</script>
<%
	} else {
	}
%>