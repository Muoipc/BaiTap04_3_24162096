<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><sitemesh:write property="title">Hệ thống quản lý</sitemesh:write></title>
<sitemesh:write property="head" />
<style>
    body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f9f9f9; }
    header { background-color: #007bff; color: white; padding: 15px 30px; display: flex; justify-content: space-between; align-items: center; }
    header a { color: white; text-decoration: none; margin-left: 15px; font-weight: bold; }
    nav { background-color: #333; padding: 10px 30px; }
    nav a { color: #fff; text-decoration: none; margin-right: 20px; font-size: 14px; }
    nav a:hover { color: #ffc107; }
    .main-container { padding: 30px; min-height: 500px; background: white; margin: 20px auto; max-width: 900px; box-shadow: 0 0 10px rgba(0,0,0,0.05); border-radius: 5px; }
    footer { background-color: #343a40; color: #ccc; text-align: center; padding: 15px; font-size: 13px; margin-top: 30px; }
</style>
</head>
<body>

    <!-- Header dùng chung quản lý bởi SiteMesh -->
    <header>
        <div>
            <h2 style="margin: 0; display: inline-block;">HCMUTE - Java Web</h2>
        </div>
        <div>
            <c:choose>
                <c:when test="${not empty sessionScope.account}">
                    <span>Xin chào, <b><c:out value="${sessionScope.account.fullname != null ? sessionScope.account.fullname : sessionScope.account.username}" /></b></span>
                    <a href="<c:url value='/profile'/>">Hồ sơ cá nhân</a>
                    <a href="<c:url value='/admin/categories'/>">Quản lý Category</a>
                    <a href="<c:url value='/logout'/>">[Đăng xuất]</a>
                </c:when>
                <c:otherwise>
                    <a href="<c:url value='/login'/>">Đăng nhập</a>
                </c:otherwise>
            </c:choose>
        </div>
    </header>

    <!-- Menu dùng chung quản lý bởi SiteMesh -->
    <nav>
        <a href="<c:url value='/admin/categories'/>">Trang chủ Category</a>
        <a href="<c:url value='/admin/category/add'/>">Thêm Category</a>
        <a href="<c:url value='/profile'/>">Hồ sơ cá nhân (Profile)</a>
    </nav>

    <!-- Nơi nội dung trang con (body) được SiteMesh chèn vào -->
    <div class="main-container">
        <sitemesh:write property="body" />
    </div>

    <!-- Footer dùng chung quản lý bởi SiteMesh -->
    <footer>
        <p>&copy; 2026 - Lập trình Web JPA Category CRUD | Quản lý Layout bằng SiteMesh 3</p>
    </footer>

</body>
</html>
