<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.LinkedList"%>
<%@ page import="catvote.beans.TimelineItem"%>
<%@ page import="catvote.beans.CandidateItem"%>
<%@ page import="catvote.Const"%>
<%!@SuppressWarnings("unchecked")%>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/public/css/snackbar.css">

<div style="margin-left: 8px; margin-right: 8px;">
	<%
		LinkedList<TimelineItem> list = new LinkedList<>();
		try {
			list.addAll((LinkedList<TimelineItem>) request.getAttribute(Const.ATTRIBUTE.TIMELINE_LIST));
		} catch (Exception ignored) {

		}

		for (TimelineItem item : list) {
	%>
	<div class="w3-col m12">
		<div class="w3-container w3-card w3-white w3-round"
			style="margin: 0 8px 8px;">
			<br>
			<h2><%=item.getTitle()%></h2>
			<br>
			<h3><%=item.getResult()%>표</h3>
			<h3>투표율 <%=item.getRate()%>%</h3>
			<hr class="w3-clear">
			<div class="w3-row-padding" style="margin: 0 -16px">
				<h3 style="text-align: center;">후보 명단</h3>
				<%
					for (CandidateItem candidate : item.getCandidateList()) {
				%>
				<div class="w3-col m12">
					<div class="w3-container w3-white w3-round"
						style="margin: 0 8px 8px;">
						<br>
						<h4>
							<%=candidate.getName()%>
						</h4>
						<hr class="w3-clear">

						<%=candidate.getMajor()%>
						<div class="w3-row-padding" style="margin: 0 -16px"></div>

						<p><%=candidate.getDescription()%>
						</p>
					</div>
					<!-- End Middle Column -->
				</div>
				<%
					}
				%>
			</div>
		</div>
		<!-- End Middle Column -->
	</div>
	<%
		}
	%>
</div>