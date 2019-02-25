<%@ page buffer="none" %><jsp:forward page="apelat.jsp">
	<jsp:param name="user" value="ion"/>
	<jsp:param name="password" value="sesiune"/>
</jsp:forward>

Nu se mai apeleaza ce e mai jos.

<jsp:forward page="apelat.jsp">
	<jsp:param name="user" value="maria"/>
	<jsp:param name="password" value="makeup"/>
</jsp:forward>
