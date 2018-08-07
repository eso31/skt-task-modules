<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

  <head>
    <title>Index</title>
  </head>
    List of products <a href="/create">Create Product</a>

    <table>
      <th>Name</th>
      <th>Description</th>
      <th>Price</th>
      <th>Stock</th>
      <c:forEach items="${products}" var="p">
        <tr>
          <td>${p.name}</td>
          <td>${p.description}</td>
          <td>${p.price}</td>
          <td>${p.stock}</td>
        </tr>
      </c:forEach>
    </table>
