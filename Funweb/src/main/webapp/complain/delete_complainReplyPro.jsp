<%@page import="dao.ComplainReplyDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
int num = Integer.parseInt(request.getParameter("num"));
int originNO = Integer.parseInt(request.getParameter("originNO"));
int pageNum = Integer.parseInt(request.getParameter("page"));
int root_num = Integer.parseInt(request.getParameter("root_num"));
String id = request.getParameter("id");

if(session.getAttribute("sessionId").equals(id)){
	
	ComplainReplyDAO complainReplyDAO = new ComplainReplyDAO();
	int deleteReplyCount = complainReplyDAO.deleteReply(num);
	
	if(deleteReplyCount > 0){
		
		response.sendRedirect("./complain_content.jsp?num="+root_num+"&page="+pageNum+"&originNO="+originNO);
		
	} else {
		%>
			<script type="text/javascript">
				alert("댓글 삭제 실패");
				history.back();
			</script>
		<%
	}
	
} else {
	%>
	 <script type="text/javascript">
		alert("본인의 댓글이 아닙니다 삭제할 수 없습니다!");
		history.back();
	</script>
	<%
}
%>