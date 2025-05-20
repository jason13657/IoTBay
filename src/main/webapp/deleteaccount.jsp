<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>
<!DOCTYPE html>
<html>
<head>
    <title>Delete Account</title>
    <style>
        body {
            font-family: 'Segoe UI', Arial, sans-serif;
            background: #f9f9f9;
            padding: 40px;
        }
        .container {
            max-width: 420px;
            margin: 60px auto;
            background: #fff;
            border-radius: 10px;
            box-shadow: 0 4px 24px rgba(0,0,0,0.10);
            padding: 36px 32px;
            text-align: center;
        }
        .warning {
            color: #d9534f;
            font-weight: bold;
            font-size: 1.2em;
            margin-bottom: 18px;
        }
        .delete-btn {
            background: linear-gradient(90deg, #ff5858 0%, #ff1e1e 100%);
            color: #fff;
            border: none;
            padding: 16px 38px;
            border-radius: 8px;
            font-size: 1.2em;
            font-weight: bold;
            letter-spacing: 1px;
            margin-top: 24px;
            cursor: pointer;
            box-shadow: 0 2px 8px rgba(255,30,30,0.20);
            transition: background 0.2s, transform 0.2s;
        }
        .delete-btn:hover {
            background: linear-gradient(90deg, #c9302c 0%, #a90000 100%);
            transform: scale(1.04);
        }
    </style>
</head>
<body>
<div class="container">
    <h2 style="color:#c9302c; margin-bottom: 10px;">⚠️ Delete Account</h2>
    <div class="warning">
        <span style="font-size:1.4em;">This action <u>cannot be undone!</u></span><br>
        <span>Your account will be <b>permanently deleted</b> and all information will be lost.</span>
    </div>
    <p style="margin-bottom: 30px;">
        Are you absolutely sure you want to <b style="color:#b71c1c;">delete</b> your account?<br>
        <span style="color:#b71c1c; font-weight:bold;">Please think carefully before proceeding.</span>
    </p>
    <button class="delete-btn" onclick="deleteAccount()">🚨 Permanently Delete Account</button>
</div>

<script>
function deleteAccount() {
    if (!confirm('Are you sure you want to delete your account? This action cannot be undone.')) {
        return;
    }
    fetch('<%=request.getContextPath()%>/api/Profiles', {
        method: 'DELETE',
        credentials: 'same-origin'
    })
    .then(response => response.text())
    .then(msg => {
        alert(msg);
        // Redirect to goodbye page after deletion
        window.location.href = '<%=request.getContextPath()%>/goodbye.jsp';
    })
    .catch(err => alert('An error occurred while deleting your account: ' + err));
}
</script>
</body>
</html>
