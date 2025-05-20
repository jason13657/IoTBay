// 기존 코드 유지, doGet 메서드 수정
@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    // 1. Session check: Only allow logged-in users
    HttpSession session = request.getSession(false);
    if (session == null || session.getAttribute("user") == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }

    User user = (User) session.getAttribute("user");
    logger.log(Level.INFO, "Current logged-in userId: " + user.getId());

    // 2. Date parameter check and validation
    String startDateStr = request.getParameter("startDate");
    String endDateStr = request.getParameter("endDate");
    LocalDate startDate = null, endDate = null;
    LocalDate today = LocalDate.now();

    try {
        if (startDateStr != null && !startDateStr.isEmpty()) {
            startDate = LocalDate.parse(startDateStr);
        }
        if (endDateStr != null && !endDateStr.isEmpty()) {
            endDate = LocalDate.parse(endDateStr);
        }
    } catch (DateTimeParseException e) {
        request.setAttribute("error", "Date format is invalid. Please use YYYY-MM-DD.");
        request.getRequestDispatcher("/WEB-INF/views/accessLog.jsp").forward(request, response);
        return;
    }

    // Validation checks...
    // (기존 유효성 검사 코드 유지)

    // 3. Retrieve access logs
    List<AccessLog> accessLogList = null;
    try {
        if (startDate != null && endDate != null) {
            logger.log(Level.INFO, "Searching by date range: userId=" + user.getId() + 
                    ", startDate=" + startDate + ", endDate=" + endDate);
            accessLogList = accessLogDAO.getAccessLogsByUserIdAndDateRange(user.getId(), startDate, endDate);
        } else {
            logger.log(Level.INFO, "Searching all logs for userId=" + user.getId());
            accessLogList = accessLogDAO.getAccessLogsByUserId(user.getId());
        }

        // 로그 추가: 조회된 데이터 확인
        if (accessLogList != null) {
            logger.log(Level.INFO, "Number of access logs retrieved: " + accessLogList.size());
            for (AccessLog log : accessLogList) {
                logger.log(Level.INFO, "Log entry: " + log.getAction() + " / " + log.getTimestamp());
            }
        } else {
            logger.log(Level.WARNING, "AccessLog list is null for userId: " + user.getId());
        }

        request.setAttribute("accessLogList", accessLogList);
    } catch (SQLException e) {
        logger.log(Level.SEVERE, "Failed to retrieve access logs for userId " + user.getId(), e);
        request.setAttribute("error", "An error occurred while retrieving access logs.");
    }

    // 4. Forward to JSP
    request.getRequestDispatcher("/WEB-INF/views/accessLog.jsp").forward(request, response);
}