<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>

<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>CAT VOET ADMIN DASHBOARD</title>
<meta
	content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0, shrink-to-fit=no'
	name='viewport' />
<link href="/catvote/public/css/fontawesome-all.min.css"
	rel="stylesheet">
<!-- CSS Files -->
<link href="/catvote/public/css/bootstrap.min.css" rel="stylesheet" />
<link href="/catvote/public/css/now-ui-dashboard.css?v=1.1.0"
	rel="stylesheet" />
<!-- CSS Just for demo purpose, don't include it in your project -->
<link href="/catvote/public/demo/demo.css" rel="stylesheet" />
</head>

<body class="">
	<div class="wrapper ">
		<div class="sidebar" data-color="blue">
			<!--
        Tip 1: You can change the color of the sidebar using: beans-color="blue | green | orange | red | yellow"
    -->
			<div class="logo">
				<a href="/catvote/admin" class="simple-text logo-mini"> CV </a> <a
					href="/catvote/admin" class="simple-text logo-normal"> CAT VOTE
				</a>
			</div>
			<div class="sidebar-wrapper">
				<%
					int index = (int) request.getAttribute("INDEX");
				%>
				<ul class="nav">
					<li <%if (index == 0) {%> class="active" <%}%>><a
						href="/catvote/admin?menu=dashboard"> <i
							class="now-ui-icons design_bullet-list-67"></i> DASHBOARD
					</a></li>
					<li <%if (index == 1) {%> class="active" <%}%>><a
						href="/catvote/admin?menu=notification"> <i
							class="now-ui-icons ui-1_bell-53"></i> Notifications
					</a></li>
					<li <%if (index == 2) {%> class="active" <%}%>><a
						href="/catvote/admin?menu=edit_profile"> <i
							class="now-ui-icons users_single-02"></i> EDIT PROFILE
					</a></li>
					<li <%if (index == 3) {%> class="active" <%}%>><a
						href="/catvote/admin?menu=edit_post"> <i
							class="now-ui-icons text_align-left"></i> EDIT POST
					</a></li>
				</ul>
			</div>
		</div>
		<div class="main-panel">
			<!-- Navbar -->
			<nav
				class="navbar navbar-expand-lg navbar-transparent  navbar-absolute bg-primary fixed-top">
				<div class="container-fluid">
					<div class="navbar-wrapper">
						<div class="navbar-toggle">
							<button type="button" class="navbar-toggler">
								<span class="navbar-toggler-bar bar1"></span> <span
									class="navbar-toggler-bar bar2"></span> <span
									class="navbar-toggler-bar bar3"></span>
							</button>
						</div>
						<a class="navbar-brand" href="/">CAT VOTE - 온라인 투표의 시작</a>
					</div>
					<a class="nav-link" href="/catvote/auth?auth_type=logout"> <i
						class="now-ui-icons ui-1_lock-circle-open"></i> <span
						class="d-lg-none d-md-block">LOGOUT</span>
					</a>
				</div>
			</nav>
			<!-- End Navbar -->
			<div class="panel-header panel-header-lg">
				<canvas id="bigDashboardChart"></canvas>
			</div>

			<!-- Conetnt -->
			<%
				String content = "/WEB-INF/view/admin/" + request.getAttribute("PAGE") + ".jsp";
			%>
			<jsp:include page="<%=content%>" />
			<!-- End Content -->

			<footer class="footer">
				<div class="container-fluid">
					<nav>
						<ul>
							<li>객체지향패러다임 2조</li>
						</ul>
					</nav>
					<div class="copyright">
						&copy;
					김연중 편도윤 최태규 양승준 윤홍준
					</div>
				</div>
			</footer>
		</div>
	</div>
	<!--   Core JS Files   -->
	<script src="/catvote/public/js/core/jquery.min.js"></script>
	<script src="/catvote/public/js/core/popper.min.js"></script>
	<script src="/catvote/public/js/core/bootstrap.min.js"></script>
	<script
		src="/catvote/public/js/plugins/perfect-scrollbar.jquery.min.js"></script>
	<!-- Chart JS -->
	<script src="/catvote/public/js/plugins/chartjs.min.js"></script>
	<!--  Notifications Plugin    -->
	<script src="/catvote/public/js/plugins/bootstrap-notify.js"></script>
	<!-- Control Center for Now Ui Dashboard: parallax effects, scripts for the example pages etc -->
	<script src="/catvote/public/js/now-ui-dashboard.min.js?v=1.1.0"
		type="text/javascript"></script>
	<!-- Now Ui Dashboard DEMO methods, don't include it in your project! -->
	<script src="/catvote/public/demo/demo.js"></script>
	<script>
		$(document).ready(function() {
			// Javascript method's body can be found in assets/js/demos.js
			demo.initDashboardPageCharts();
		});
	</script>
</body>

</html>
