<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="catvote.beans.VoteItem"%>
<%@ page import="java.util.LinkedList"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.Calendar"%>
<%!@SuppressWarnings("unchecked")%>

<div class="content">
	<div class="row">
		<div class="col-md-12">
			<div class="card">
				<div class="card-header">
					<h4 class="card-title">
						투표 현황
						<button type="button"
							onclick="window.location.href='/catvote/admin?menu=edit_vote&vote_id=0'"
							class="btn btn-round btn-default dropdown-toggle btn-simple btn-icon no-caret"
							data-toggle="dropdown">
							<i class="now-ui-icons ui-1_simple-add"></i>
						</button>
					</h4>
				</div>
				<div class="card-body">
					<div class="table-responsive" style="overflow: hidden;">
						<table class="table">
							<thead class="text-primary">
								<tr>
									<th class="text-center">투표명</th>
									<th class="text-center">종료까지</th>
									<th class="text-center">투표율</th>
									<th class="text-center">득표현황</th>
								</tr>
							</thead>
							<tbody>
								<%
									LinkedList<VoteItem> list = null;
									try {
										list = (LinkedList<VoteItem>) request.getAttribute("VOTE_LIST");
									} catch (Exception ignored) {

									}

									assert list != null;
									for (VoteItem item : list) {
								%>
								<tr>
									<td><a style="color: black;"
										href="/catvote/admin?menu=edit_vote&vote_id=<%=item.getId()%>"><%=item.getTitle()%></a></td>
									<td class="text-center">
										<%
											Date end = item.getEnd();
												Date now = Calendar.getInstance().getTime();
												long diff = end.getTime() - now.getTime();
												long sec = diff / (1000 * 60 * 60);
										%> <%=sec < 0 ? "종료" : sec + "시간"%>
									</td>
									<td class="text-center"><%=item.getVoteRate()%>%<br><%=item.getVoteSize()%>
										/ <%=item.getTargetSize()%></td>
									<td class="text-center"><%=item.getStatus()%></td>
								</tr>
								<%
									}
								%>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
