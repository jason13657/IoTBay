<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>IoT Bay - My Account</title>
    <link rel="stylesheet" href="css/styles.css" />
    <style>
        body { background: #f6f8fa; }
        .account__container {
            display: flex;
            max-width: 1080px;
            margin: 48px auto;
            background: #fff;
            border-radius: 22px;
            box-shadow: 0 8px 32px rgba(0,119,182,0.09), 0 2px 8px rgba(0,0,0,0.03);
            overflow: hidden;
        }
        .account__sidebar {
            width: 240px;
            background: #0077b6;
            color: #fff;
            padding: 36px 0;
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        .account__avatar {
            width: 92px;
            height: 92px;
            border-radius: 50%;
            background: #bde0fe;
            margin-bottom: 18px;
            object-fit: cover;
        }
        .account__name { font-size: 22px; font-weight: 700; margin-bottom: 6px; }
        .account__email { font-size: 14px; opacity: 0.9; margin-bottom: 18px; }
        .account__nav {
            width: 100%;
            margin-top: 16px;
        }
        .account__nav a {
            display: block;
            color: #fff;
            text-decoration: none;
            padding: 12px 32px;
            font-size: 16px;
            font-weight: 500;
            transition: background 0.15s;
        }
        .account__nav a:hover, .account__nav .active {
            background: #00b4d8;
        }
        .account__main {
            flex: 1;
            padding: 36px 48px;
        }
        .account__section {
            margin-bottom: 32px;
        }
        .section__header {
            font-size: 20px;
            font-weight: 700;
            color: #0077b6;
            margin-bottom: 16px;
        }
        .orders__table, .addresses__table, .activity__table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 8px;
        }
        .orders__table th, .orders__table td,
        .addresses__table th, .addresses__table td,
        .activity__table th, .activity__table td {
            padding: 10px 8px;
            border-bottom: 1px solid #e0e0e0;
            font-size: 15px;
        }
        .orders__table th { color: #0077b6; }
        .orders__status--pending { color: #ff9900; font-weight: 600; }
        .orders__status--completed { color: #38b000; font-weight: 600; }
        .orders__status--cancelled { color: #e63946; font-weight: 600; }
        .quick__actions {
            display: flex;
            gap: 12px;
            margin-top: 12px;
        }
        .quick__actions a, .quick__actions button {
            background: #00b4d8;
            color: #fff;
            border: none;
            border-radius: 8px;
            padding: 8px 18px;
            font-size: 15px;
            font-weight: 600;
            cursor: pointer;
            text-decoration: none;
            transition: background 0.15s;
        }
        .quick__actions a:hover, .quick__actions button:hover {
            background: #023e8a;
        }
        @media (max-width: 900px) {
            .account__container { flex-direction: column; }
            .account__sidebar { width: 100%; flex-direction: row; padding: 24px 0; }
            .account__main { padding: 24px 8px; }
        }
    </style>
</head>
<body>
<jsp:include page="components/header.jsp" />

<div class="account__container">
    <!-- Sidebar -->
    <div class="account__sidebar">
        <!-- 세션 또는 request 속성에서 사용자 정보를 가져옵니다 -->
        <c:set var="userInfo" value="${requestScope.user != null ? requestScope.user : sessionScope.user}" />
        
        <div class="account__name">${fn:escapeXml(userInfo.firstName)} ${fn:escapeXml(userInfo.lastName)}</div>
        <div class="account__email">${fn:escapeXml(userInfo.email)}</div>

        <nav class="account__nav">
            <a href="#profile" class="active">Profile</a>
            <a href="#orders">Orders</a>
            <a href="#addresses">Addresses</a>
            <a href="#activity">Activity</a>
            <a href="#wishlist">Wishlist</a>
            <a href="#settings">Settings</a>
            <a href="#support">Support</a>
            <a href="index.jsp">Logout</a>
            
        </nav>
    </div>

    <!-- Main Content -->
    <div class="account__main">
        <!-- Profile Section -->
        <div class="account__section" id="profile">
            <div class="section__header">My Profile</div>
            
            <!-- 성공 또는 오류 메시지 표시 -->
            <c:if test="${not empty requestScope.success}">
                <div class="alert alert-success">${requestScope.success}</div>
            </c:if>
            <c:if test="${not empty requestScope.error}">
                <div class="alert alert-error">${requestScope.error}</div>
            </c:if>
            
            <!-- 프로필 정보 표시 -->
            <table class="profile-details">
                <tr>
                    <td><strong>Name:</strong></td>
                    <td>${fn:escapeXml(userInfo.firstName)} ${fn:escapeXml(userInfo.lastName)}</td>
                </tr>
                <tr>
                    <td><strong>Email:</strong></td>
                    <td>${fn:escapeXml(userInfo.email)}</td>
                </tr>
                <tr>
                    <td><strong>Phone:</strong></td>
                    <td>${fn:escapeXml(userInfo.phone)}</td>
                </tr>
                <tr>
                    <td><strong>Address:</strong></td>
                    <td>
                        ${fn:escapeXml(userInfo.addressLine1)}
                        <c:if test="${not empty userInfo.addressLine2}">
                            <br/>${fn:escapeXml(userInfo.addressLine2)}
                        </c:if>
                        <br/>${fn:escapeXml(userInfo.postalCode)}
                    </td>
                </tr>
            </table>
            
            <div class="quick__actions">
                <a href="updateProfile.jsp">Edit Profile</a>
            </div>
        </div>

        <!-- Order History Section -->
        <div class="account__section" id="orders">
            <div class="section__header">Order History</div>
            <table class="orders__table">
                <thead>
                    <tr>
                        <th>Order #</th>
                        <th>Date</th>
                        <th>Status</th>
                        <th>Total</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="order" items="${orderList}">
                        <tr>
                            <td>${order.id}</td>
                            <td>${order.date}</td>
                            <td>
                                <span class="orders__status--${order.status}">
                                    ${fn:toUpperCase(order.status)}
                                </span>
                            </td>
                            <td>$${order.totalPrice}</td>
                            <td>
                                <a href="orderDetail.jsp?orderId=${order.id}">View</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div class="quick__actions">
                <a href="orderList.jsp">View All Orders</a>
            </div>
        </div>

        <!-- Address Book Section -->
        <div class="account__section" id="addresses">
            <div class="section__header">Address Book</div>
            <table class="addresses__table">
                <thead>
                    <tr>
                        <th>Type</th>
                        <th>Address</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="address" items="${addressList}">
                        <tr>
                            <td>${address.type}</td>
                            <td>${fn:escapeXml(address.fullAddress)}</td>
                            <td>
                                <a href="editAddress.jsp?addressId=${address.id}">Edit</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div class="quick__actions">
                <a href="updateProfile.jsp">Update Profiles</a>
            </div>
        </div>

        <!-- Activity Log Section -->
        <div class="account__section" id="activity">
            <div class="section__header">Recent Activity</div>
            <table class="activity__table">
                <thead>
                    <tr>
                        <th>Date</th>
                        <th>Activity</th>
                    </tr>
                </thead>
                
                <tbody>
                    <c:forEach var="log" items="${accessLogList}">
                        <tr>
                            <td>${log.loginDate}</td>
                            <td>${log.activity}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div class="quick__actions">
                <a href="accessLog.jsp">View All Activity</a>
            </div>
        </div>
    </div>
</div>

<jsp:include page="components/footer.jsp" />
</body>
</html>