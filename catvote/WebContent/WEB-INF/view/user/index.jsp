<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="catvote.beans.PostItem"%>
<%@ page import="java.util.LinkedList"%>
<%!@SuppressWarnings("unchecked")%>

<link rel="stylesheet"
	href="/catvote/public/css/snackbar.css">
<%
	LinkedList<PostItem> list = new LinkedList<>();
	try {
		list.addAll((LinkedList<PostItem>) request.getAttribute("MANAGER_POST"));
	} catch (Exception e) {
		e.printStackTrace();
	}

	for (PostItem item : list) {
%>
<div class="w3-container w3-card w3-white w3-round"
	style="margin: 0 16px 16px 16px;">
	<br> <img
		src="/catvote/public/image/admin.png"
		alt="Avatar" class="w3-left w3-circle w3-margin-right"
		style="width: 60px; border-radius: 50%; border-style: solid; border-width: 1px; border-color: grey;">
	<span class="w3-right w3-opacity"><%=item.getDate()%></span>
	<h4>
		<%=item.getTitle()%></h4>
	<hr class="w3-clear">
	<p><%=item.getContent()%></p>
	<button type="button" class="w3-button w3-block w3-green w3-section"
		onclick="copyToClip('<%=item.getContent()%>')" style="float: right;">
		<i class="fa fa-share"></i>  Share
	</button>
</div>
<%
	}
%>
<!-- The actual snackbar -->
<div id="snackbar">복사되었습니다.</div>

<script>
	function copyToClip(copyText) {
		var t = document.createElement("textarea");
		document.body.appendChild(t);
		t.value = copyText;
		t.select();
		document.execCommand('copy');
		document.body.removeChild(t);
		// Get the snackbar DIV
		var x = document.getElementById("snackbar");

		// Add the "show" class to DIV
		x.className = "show";

		// After 3 seconds, remove the show class from DIV
		setTimeout(function() {
			x.className = x.className.replace("show", "");
		}, 3000);
	}
</script>