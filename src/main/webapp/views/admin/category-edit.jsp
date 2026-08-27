<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sửa Category</title>
</head>
<body>
	<form action="<c:url value="/admin/category/update"/>" method="post" enctype="multipart/form-data">
		<input type="text" name="categoryid" value="${cate.categoryId}" hidden="hidden">

		<label for="fname">Category name:</label><br>
		<input type="text" id="categoryname" name="categoryname" value="${cate.categoryname}"><br>

		<label for="lname">Link images:</label><br>
		<input type="text" id="images" name="images" value="${cate.images}"><br>

		<c:choose>
			<c:when test="${cate.images.startsWith('https')}">
				<c:url value="${cate.images }" var="imgUrl"></c:url>
			</c:when>
			<c:otherwise>
				<c:url value="/image?fname=${cate.images }" var="imgUrl"></c:url>
			</c:otherwise>
		</c:choose>
		<img height="150" width="200" src="${imgUrl}" /><br>

		<label for="lname">Upload images:</label><br>
		<input type="file" id="images1" name="images1"><br>

		<label for="html">Status</label><br>
		<input type="radio" id="ston" name="status" value="1" ${cate.status==1?'checked':'' }>
		<label for="css">Hoạt động</label><br>
		<input type="radio" id="stoff" name="status" value="0" ${cate.status!=1?'checked':'' }>
		<label for="javascript">Khóa</label>
		<br><br>
		<input type="submit" value="Update">
	</form>
</body>
</html>
