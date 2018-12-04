<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="catvote.beans.NoticeItem"%>
<%@ page import="catvote.beans.VoteItem"%>
<%@ page import="java.util.LinkedList"%>
<%@ page import="catvote.Const"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%!@SuppressWarnings("unchecked")%>

<%
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	VoteItem nextVote = (VoteItem) request.getAttribute(Const.ATTRIBUTE.NEXT_VOTE);
	LinkedList<VoteItem> voteList = new LinkedList<>();
	LinkedList<NoticeItem> noticeList = new LinkedList<>();
	try {
		voteList.addAll((LinkedList<VoteItem>) request.getAttribute(Const.ATTRIBUTE.AVAILABLE_VOTE));
		noticeList.addAll((LinkedList<NoticeItem>) request.getAttribute(Const.ATTRIBUTE.NOTICE));
	} catch (Exception ignored) {

	}
%>
<!DOCTYPE html>

<html>
<title>CAT VOTE</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="/catvote/public/css/w3.css">
<link rel="stylesheet"
	href="/catvote/public/css/w3-theme-blue-grey.css">
<link rel='stylesheet'
	href='https://fonts.googleapis.com/css?family=Open+Sans'>
<link rel="stylesheet"
	href="/catvote/public/css/fontawesome-all.min.css">
<style>
html, body, h1, h2, h3, h4, h5 {
	font-family: "Open Sans", sans-serif
}

a:link, a:visited, a:active {
	text-decoration: none;
}
</style>
<body class="w3-theme-l5">

	<!-- Navbar -->
	<div class="w3-top">
		<div class="w3-bar w3-theme-d2 w3-left-align w3-large">
			<a
				class="w3-bar-item w3-button w3-hide-medium w3-hide-large w3-right w3-padding-large w3-hover-white w3-large w3-theme-d2"
				href="javascript:void(0);" onclick="openNav()"><i
				class="fa fa-bars"></i></a> <a href="/catvote/index"
				class="w3-bar-item w3-button w3-padding-large w3-theme-d4"><i
				class="fa fa-home w3-margin-right"></i>HOME</a> <a
				href="/catvote/reward"
				class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white"
				title="News"><i class="fa fa-store" title="리워드"></i></a><a
				href="/catvote/timeline"
				class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white"
				title="News"><i class="fa fa-history" title="타임라인"></i></a>
			<div class="w3-dropdown-hover w3-hide-small">
				<button class="w3-button w3-padding-large" title="Notifications">
					<i class="fa fa-bell"></i> <span
						class="w3-badge w3-right w3-small w3-green"><%=noticeList.size()%></span>
				</button>
				<div class="w3-dropdown-content w3-card-4 w3-bar-block"
					style="width: 300px">
					<%
						for (NoticeItem item : noticeList) {
					%>
					<a href="" class="w3-bar-item w3-button"><%=item.getContent()%>
					</a>
					<%
						}
					%>
				</div>
			</div>
			<a href="/catvote/auth?auth_type=logout"
				class="w3-bar-item w3-button w3-hide-small w3-padding-large w3-hover-white"
				title="News"><i class="fa fa-sign-out-alt" title="로그아웃"></i></a>
		</div>
	</div>

	<!-- Navbar on small screens -->
	<div id="navDemo"
		class="w3-bar-block w3-theme-d2 w3-hide w3-hide-large w3-hide-medium w3-large">
		<a class="w3-bar-item w3-button w3-padding-large"></a> <br> <a
			href="/catvote/reward" class="w3-bar-item w3-button w3-padding-large">리워드</a>
		<a href="/catvote/timeline"
			class="w3-bar-item w3-button w3-padding-large">타임라인</a><a
			href="/catvote/auth?auth_type=logout"
			class="w3-bar-item w3-button w3-padding-large">로그아웃</a>

		<hr>
		<%
			for (NoticeItem item : noticeList) {
		%>
		<a class="w3-bar-item w3-button"><%=item.getContent()%> </a>
		<%
			}
		%>
	</div>


	<!-- Page Container -->
	<div class="w3-container w3-content"
		style="max-width: 1400px; margin-top: 80px">
		<!-- The Grid -->
		<div class="w3-row">
			<!-- Left Column -->
			<div class="w3-col m3">
				<!-- Profile -->
				<div class="w3-card w3-round w3-white">
					<div class="w3-container">
						<jsp:useBean class="catvote.beans.UserItem" id="USER_INFO"
							scope="request" />
						<h4 class="w3-center"><%=USER_INFO.getId()%></h4>
						<p class="w3-center">
							<img
								src="/catvote/public/image/catholic_logo.png"
								class="w3-circle"
								style="height: 106px; width: 106px; border-radius: 50%;"
								alt="Avatar">
						</p>
						<hr>
						<p>
							<i class="fa fa-coins fa-fw w3-margin-right w3-text-theme"></i>
							<%=USER_INFO.getPoint()%>
							Point
						</p>
						<p>
							<i class="fa fa-clock fa-fw w3-margin-right w3-text-theme"></i>
							<%=request.getAttribute("TODAY")%>
						</p>
						<p>
							<i class="fa fa-code fa-fw w3-margin-right w3-text-theme"></i> <span
								style="font-size: 0.8em;">김연중 편도윤 최태규 양승준 윤홍준</span>
						</p>
					</div>
				</div>
				<br>

				<%
					for (NoticeItem item : noticeList) {
				%>

				<%
					boolean isPrimaryNooticeVisible = item.isPrimary();
						if (isPrimaryNooticeVisible) {
				%>
				<div
					class="w3-container w3-display-container w3-round w3-theme-l4 w3-border w3-theme-border w3-margin-bottom w3-hide-small">
					<span onclick="this.parentElement.style.display='none'"
						class="w3-button w3-theme-l3 w3-display-topright"> <i
						class="fa fa-times"></i>
					</span>
					<p>
						<strong><%=item.getTitle()%> </strong>
					</p>
					<p>
						<%=item.getContent()%>
					</p>
				</div>
				<%
					} else {
						}
					}
				%>

			</div>
			<!-- End Left Column -->

			<!-- Middle Column -->
			<div class="w3-col m7">
				<!-- 내부 컨텐츠 로드 -->
				<%
					String content = "/WEB-INF/view/user/" + request.getAttribute("PAGE") + ".jsp";
				%>
				<jsp:include page="<%=content%>" />
			</div>
			<!-- End Middle Column -->

			<!-- Right Column -->
			<div class="w3-col m2">
				<div class="w3-card w3-round w3-white w3-center">
					<%
						for (VoteItem item : voteList) {
					%>
					<div class="w3-container"
						style="border: 1px solid #c5c8cc; padding-top: 16px;">
						<img
							src="/catvote/public/image/vote/<%=item.getId() %>.png"
							alt="Avatar" style="width: 50%; border-radius: 50%;"> <br>
						<p><%=item.getTitle()%></p>
						<div class="w3-row w3-opacity">
							<div>
								<button class="w3-button w3-block w3-green w3-section"
									onclick="window.location.href='/catvote/vote?vote_id=<%=item.getId()%>'"
									title="Accept">
									<%
										if (item.isUserAlreadyVote()) {
									%>
									<i class="fa fa-check"></i>
									<%
										} else {
									%>
									<i class="fa fa-boxes"></i>
									<%
										}
									%>
								</button>
							</div>
						</div>
					</div>
					<%
						}
					%>
				</div>
				<br>

				<div class="w3-card w3-round w3-white w3-center">
					<div class="w3-container">
						<p>시작 예정 투표</p>
						<img
							src="/catvote/public/image/vote/<%=nextVote.getId() %>.png"
							alt="Forest" style="width: 100%; border-radius: 50%;">
						<p>
							<strong><%=nextVote.getTitle()%></strong>
						</p>
						<p><%=sdf.format(nextVote.getStart())%></p>
					</div>
				</div>
				<br>

				<div class="w3-card w3-round w3-white w3-padding-16 w3-center">
					<p>ADS</p>
				</div>
				<br>
				<!-- End Right Column -->
			</div>

			<!-- End Grid -->
		</div>

		<!-- End Page Container -->
	</div>


	<br>
	<!-- Footer -->
	<div class="w3-container w3-theme-d3 w3-padding-16">
		<h5>온라인 투표 시스템 CAT VOTE</h5>
	</div>

	<div class="w3-container w3-theme-d5">
		<p>&copy; 객체지향 패러다임 2조 All rights Reserved.</p>
	</div>

	<script>
		// Accordion
		function myFunction(id) {
			var x = document.getElementById(id);
			if (x.className.indexOf("w3-show") === -1) {
				x.className += " w3-show";
				x.previousElementSibling.className += " w3-theme-d1";
			} else {
				x.className = x.className.replace("w3-show", "");
				x.previousElementSibling.className = x.previousElementSibling.className
						.replace(" w3-theme-d1", "");
			}
		}

		// Used to toggle the menu on smaller screens when clicking on the menu button
		function openNav() {
			var x = document.getElementById("navDemo");
			if (x.className.indexOf("w3-show") === -1) {
				x.className += " w3-show";
			} else {
				x.className = x.className.replace(" w3-show", "");
			}
		}
	</script>

</body>
</html>
