<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Thông tin cá nhân (Profile)</title>
</head>
<body>

    <p>
        <a href="<c:url value='/admin/categories'/>"><button type="button">&larr; Quản lý Category</button></a> | 
        <a href="<c:url value='/logout'/>">[Đăng xuất]</a>
    </p>

    <h2>HỒ SƠ CÁ NHÂN (USER PROFILE)</h2>

    <c:if test="${not empty message}">
        <p style="color: green; font-weight: bold;">${message}</p>
    </c:if>

    <form action="<c:url value='/profile'/>" method="post" enctype="multipart/form-data">
        <table cellpadding="8" style="width: 100%; max-width: 600px;">
            <tr>
                <td style="width: 140px;"><b>Tên đăng nhập:</b></td>
                <td>
                    <input type="text" value="${user.username}" readonly="readonly" style="background-color: #eee; width: 100%; padding: 6px;" />
                </td>
            </tr>
            <tr>
                <td><b>Email:</b></td>
                <td>
                    <input type="text" value="${user.email}" readonly="readonly" style="background-color: #eee; width: 100%; padding: 6px;" />
                </td>
            </tr>
            <tr>
                <td><b>Họ và tên:</b></td>
                <td>
                    <input type="text" name="fullname" value="${user.fullname}" required="required" style="width: 100%; padding: 6px;" />
                </td>
            </tr>
            <tr>
                <td><b>Số điện thoại:</b></td>
                <td>
                    <input type="text" name="phone" value="${user.phone}" placeholder="Nhập số điện thoại" style="width: 100%; padding: 6px;" />
                </td>
            </tr>
            <tr>
                <td><b>Ảnh đại diện hiện tại:</b></td>
                <td>
                    <c:choose>
                        <c:when test="${not empty user.images and user.images.startsWith('http')}">
                            <img src="${user.images}" width="120" height="120" style="object-fit: cover; border-radius: 60px; border: 1px solid #ccc;" alt="Avatar" />
                        </c:when>
                        <c:when test="${not empty user.images}">
                            <img src="<c:url value='/image?fname=${user.images}'/>" width="120" height="120" style="object-fit: cover; border-radius: 60px; border: 1px solid #ccc;" alt="Avatar" />
                        </c:when>
                        <c:otherwise>
                            <span style="color: #888;">(Chưa có ảnh đại diện)</span>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <tr>
                <td><b>Chọn ảnh mới:</b></td>
                <td>
                    <input type="file" name="imageFile" accept="image/*" />
                    <br/>
                    <small style="color: #666;">(Upload file qua Multipart: jpg, png, gif)</small>
                </td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <br/>
                    <input type="submit" value="Cập nhật Profile" style="padding: 8px 20px; font-weight: bold; background: #007bff; color: white; border: none; border-radius: 4px; cursor: pointer;" />
                </td>
            </tr>
        </table>
    </form>

</body>
</html>
