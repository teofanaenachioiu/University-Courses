<html>
<head>
	<title>Exemplu de custom tag</title>
</head>
<body>

    <%@ taglib uri="demo" prefix="d"%>
    <d:toUpperCase>

        <%
            java.util.Collection c1 = new java.util.ArrayList();
            c1.add(1);
            c1.add(2);
            c1.add(3);
            c1.add(4);
        %>
            Colectia de numere intregi contine:
        <p>
            <d:iterate collection="<%= c1 %>" crtElem="elem">
                <%= elem %><br/>
            </d:iterate>
        </p>
        <%
            java.util.Collection c2 = new java.util.ArrayList();
            c2.add("a");
            c2.add("b");
            c2.add("c");
            c2.add("d");
        %>
            Colectia de stringuri contine:
        <p>
            <d:iterate collection="<%= c2 %>" crtElem="elem">
                <%= elem %><br/>
            </d:iterate>
        </p>

    </d:toUpperCase>

</body>
</html>
