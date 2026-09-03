<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><sitemesh:write property="title">Admin Panel</sitemesh:write></title>
<sitemesh:write property="head" />
<style>
    body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f6f9; }
    header { background-color: #343a40; color: white; padding: 15px 30px; display: flex; justify-content: space-between; align-items: center; }
    header a { color: #f8f9fa; text-decoration: none; margin-left: 15px; font-weight: bold; }
    nav { background-color: #212529; padding: 10px 30px; }
    nav a { color: #adb5bd; text-decoration: none; margin-right: 20px; font-size: 14px; }
    nav a:hover { color: #ffffff; }
    .admin-container { padding: 30px; min-height: 500px; background: white; margin: 20px auto; max-width: 1000px; box-shadow: 0 0 10px rgba(0,0,0,0.05); border-radius: 5px; }
    footer { background-color: #212529; color: #adb5bd; text-align: center; padding: 15px; font-size: 13px; margin-top: 30px; }
</style>
</head>
<body>

    <!-- Header dùng chung quản lý bởi SiteMesh -->
    <header>
        <div>
            <h2 style="margin: 0; display: inline-block;">ADMIN CONTROL PANEL</h2>
        </div>
        <div>
            <c:if test="${not empty sessionScope.account}">
                <span>Xin chào, <b><c:out value="${sessionScope.account.fullname != null ? sessionScope.account.fullname : sessionScope.account.username}" /></b></span>
                <a href="<c:url value='/profile'/>">Hồ sơ (Profile)</a>
                <a href="<c:url value='/logout'/>">[Đăng xuất]</a>
            </c:if>
        </div>
    </header>

    <!-- Menu điều hướng quản trị -->
    <nav>
        <a href="<c:url value='/admin/categories'/>">Danh sách Category</a>
        <a href="<c:url value='/admin/category/add'/>">Thêm Category mới</a>
        <a href="<c:url value='/profile'/>">Thông tin cá nhân (Profile)</a>
    </nav>

    <!-- Nơi nội dung của view admin được chèn vào -->
    <div class="admin-container">
        <sitemesh:write property="body" />
    </div>

    <!-- Footer dùng chung -->
    <footer>
        <p>&copy; 2026 - Admin Panel Category CRUD & User Profile | SiteMesh Decorator</p>
    </footer>

</body>
</html>
