<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.User" %>

<!DOCTYPE html>
<html>
<head>
    <title>Manage Users</title>
</head>
<body>
    <h1>Manage Users</h1>
    <div class="btn-container">
        <div style="margin: 20px;">
            <button onclick="location.href='/index.jsp'" style="padding: 8px 16px; font-size: 16px; background-color: #6c757d; color: white; border: none; border-radius: 5px; cursor: pointer;">
                &larr; Back to Home
            </button>
        </div>
        <div style="text-align: center; margin: 20px;">
            <input type="text" id="searchInput" onkeyup="filterTable()" placeholder="Search users..." style="width: 300px; padding: 8px; font-size: 16px;">
        </div>
        <div style="text-align: right;">
            <button id="main-create-btn" class="create-btn" onclick="openCreateModal()">Create New User</button>
        </div>
    </div>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Email</th>
                <th>Password</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Gender</th>
                <th>Favourite Color</th>
                <th>DOB</th>
                <th>Time Created</th>
                <th>Time Updated</th>
                <th>Role</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
        <%
            List<User> users = (List<User>) request.getAttribute("users");
            if (users != null && !users.isEmpty()) {
                for (User user : users) {
        %>
            <tr>
                <td><%= user.getId() %></td>
                <td><%= user.getEmail() %></td>
                <td><%= user.getPassword() %></td>
                <td><%= user.getFirstName() %></td>
                <td><%= user.getLastName() %></td>
                <td><%= user.getGender() %></td>
                <td><%= user.getFavoriteColor() %></td>
                <td><%= user.getDateOfBirth() %></td>
                <td><%= user.getCreatedAt() %></td>
                <td class="updatedAtTime"><%= user.getUpdatedAt() %></td>
                <td><%= user.getRole() %></td>
                <td class="actionbtns">
                    <button class="edit-btn" onclick="openEditModal(
                        '<%= user.getId() %>',
                        '<%= user.getEmail().replace("'", "\\'") %>',
                        '<%= user.getPassword().replace("'", "\\'") %>',
                        '<%= user.getFirstName().replace("'", "\\'") %>',
                        '<%= user.getLastName().replace("'", "\\'") %>',
                        '<%= user.getGender().replace("'", "\\'") %>',
                        '<%= user.getFavoriteColor().replace("'", "\\'") %>',
                        '<%= user.getDateOfBirth() %>',
                        '<%= user.getCreatedAt() %>',
                        '<%= user.getUpdatedAt() %>',
                        '<%= user.getRole().replace("'", "\\'") %>'
                    )">Edit</button>                    
                    <form action="<%=request.getContextPath()%>/manage/users/delete" method="post" style="display:inline;">
                        <input type="hidden" name="id" value="<%= user.getId() %>">
                        <button type="submit" class="delete-btn">Delete</button>
                    </form>                    
                </td>
            </tr>
        <%
                }
            } else {
        %>
            <tr>
                <td colspan="5" style="text-align:center;">No users available.</td>
            </tr>
        <%
            }
        %>
        </tbody>
    </table>
    <div id="createUserModal" class="modal" style="display:none;">
        <div class="modal-content">
            <span class="close" onclick="closeCreateModal()">&times;</span>
            <form action="/manage/users" method="post">
                <input type="hidden" name="user_id"><br>
            
                <label>Email:</label>
                <input type="email" name="email" required><br>
            
                <label>Password:</label>
                <input type="password" name="password" required><br>
            
                <label>First Name:</label>
                <input type="text" name="firstName" required><br>
            
                <label>Last Name:</label>
                <input type="text" name="lastName" required><br>
            
                <label>Gender:</label>
                <select name="gender" required>
                    <option value="Male">Male</option>
                    <option value="Female">Female</option>
                    <option value="Other">Other</option>
                </select><br>
            
                <label>Favourite Color:</label>
                <input type="text" name="favouriteColor"><br>
            
                <label>Date of Birth:</label>
                <input type="date" id="create_dateOfBirth" name="dateOfBirth" required><br>
            
                <label>Role:</label>
                <select name="role" required>
                    <option value="customer">Customer</option>
                    <option value="staff">Staff</option>
                </select><br>
            
                <button type="submit" id="submit-modal-btn" class="create-btn">Create</button>
            </form>            
        </div>
    </div>
        <div id="editUserModal" class="modal" style="display:none;">
            <div class="modal-content">
                <span class="close" onclick="closeEditModal()">&times;</span>
                <form action="/manage/users/update" method="post">
                    <input type="hidden" name="user_id" id="edit_id">
                
                    <label>Email:</label>
                    <input type="email" name="email" id="edit_email" required>
                
                    <label>Password:</label>
                    <input type="password" name="password" id="edit_password" required>
                
                    <label>First Name:</label>
                    <input type="text" name="firstName" id="edit_firstName" required>
                
                    <label>Last Name:</label>
                    <input type="text" name="lastName" id="edit_lastName" required>
                
                    <label>Gender:</label>
                    <select name="gender" id="edit_gender" required>
                        <option value="Male">Male</option>
                        <option value="Female">Female</option>
                        <option value="Other">Other</option>
                    </select>
                
                    <label>Favourite Color:</label>
                    <input type="text" name="favoriteColor" id="edit_favoriteColor">
                
                    <label>Date of Birth:</label>
                    <input type="date" name="dateOfBirth" id="edit_dateOfBirth" required>
                    <input type="hidden" name="createdAt" id="edit_createdAt">
                    <input type="hidden" name="updatedAt" id="edit_updatedAt">

                    <label>Role:</label>
                    <select name="role" id="edit_role" required>
                        <option value="staff">Staff</option>
                        <option value="customer">Customer</option>
                    </select>
                    <input type="hidden" name="isActive" id="edit_isActive" value="true"> <!-- default true -->
                    <br>
                    <button type="submit" class="update-btn">Update</button>
                </form>                
            </div>
        </div>
        <style>
        body {
            font-family: Arial, Helvetica, sans-serif;
        }
        table {
            border-collapse: collapse;
            width: 90%;
            margin: 20px auto;
        }
        th, td {
            border: 1px solid #888;
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #eee;
        }
        h1 {
            text-align: center;
            margin-top: 30px;
        }
        td.actionbtns {
            min-width: 150px
        }
        td.updatedAtTime {
            max-width: 150px;
            word-break: break-all;
            white-space: normal;
            overflow-wrap: break-word;
        }

        input[type="text"],
        input[type="number"],
        input[type="date"],
        input[type="email"],
        input[type="password"],
        textarea {
            width: 100%;
            padding: 8px 12px;
            margin: 6px 0 12px 0;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 14px;
            box-sizing: border-box;
        }

        label {
            font-weight: bold;
            margin-top: 8px;
            display: block;
        }

        /* Modal Styling */
        .modal {
            position: fixed;
            top: 0;
            left: 0;
            width: 100vw;
            height: 100vh;
            background-color: rgba(0,0,0,0.5);
            display: flex;
            justify-content: center;
            align-items: center;
            z-index: 999;
        }

        .modal-content {
            background-color: white;
            padding: 30px;
            border-radius: 10px;
            width: 400px;
            max-width: 90%;
            max-height: 92vh; /* prevents it from exceeding screen height */
            overflow-y: auto;
            box-shadow: 0 4px 10px rgba(0,0,0,0.25);
            position: relative;
        }


        /* Close Button */
        .close {
            position: absolute;
            top: 12px;
            right: 16px;
            font-size: 24px;
            cursor: pointer;
            color: #666;
        }

        .close:hover {
            color: #000;
        }

        /* Table Styling */
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            background-color: white;
            border-radius: 6px;
            overflow: hidden;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
        }

        th, td {
            padding: 12px 16px;
            text-align: left;
            border-bottom: 1px solid #ddd;
            word-wrap: break-word;
            max-width: 200px;
        }

        th {
            background-color: #f2f2f2;
            font-weight: bold;
        }

        /* Action Button Styling Inside Table */
        .table-actions button {
            margin-right: 6px;
            font-size: 12px;
            padding: 6px 10px;
            background-color: #28a745;
        }

        .table-actions button.delete-btn {
            background-color: #dc3545;
        }
        .create-btn {
            background-color: #007BFF; /* Blue */
            color: white;
            border: none;
            padding: 8px 20px;
            font-size: 16px;
            border-radius: 5px;
            text-align: right;
            cursor: pointer;
            font-weight: 600;
            box-shadow: 0 3px 6px rgba(0,123,255,0.4);
            transition: background-color 0.3s ease, box-shadow 0.3s ease;
        }
        .create-btn:hover {
            background-color: #0056b3;
            box-shadow: 0 5px 10px rgba(0,86,179,0.6);
        }
        .create-btn:active {
            background-color: #004494;
            box-shadow: 0 2px 5px rgba(0,68,148,0.8);
        }
        #main-create-btn {
            margin: 0 25px;
        }
        #submit-modal-btn {
            float: right;
        }
            .edit-btn {
              background-color: #007bff;
              color: white;
              border: none;
              padding: 6px 14px;
              font-size: 14px;
              border-radius: 4px;
              cursor: pointer;
              transition: background-color 0.3s ease;
            }
            .edit-btn:hover {
              background-color: #0062ff;
            }
          
            .delete-btn {
              background-color: #f44336; /* Red */
              color: white;
              border: none;
              padding: 6px 14px;
              font-size: 14px;
              border-radius: 4px;
              cursor: pointer;
              transition: background-color 0.3s ease;
            }
            .delete-btn:hover {
              background-color: #d32f2f;
            }
          
            /* Optional: add spacing between buttons */
            .edit-btn, .delete-btn {
              margin-right: 8px;
            }

            .update-btn {
            background-color: #007BFF; /* Blue */
            color: white;
            border: none;
            padding: 8px 20px;
            margin-top: 15px;
            font-size: 16px;
            float: right;
            border-radius: 5px;
            text-align: right;
            cursor: pointer;
            font-weight: 600;
            box-shadow: 0 3px 6px rgba(0,123,255,0.4);
            transition: background-color 0.3s ease, box-shadow 0.3s ease;
        }
        .update-btn:hover {
            background-color: #0056b3;
            box-shadow: 0 5px 10px rgba(0,86,179,0.6);
        }
        .update-btn:active {
            background-color: #004494;
            box-shadow: 0 2px 5px rgba(0,68,148,0.8);
        }
        .btn-container {
            display: flex; justify-content: space-between; align-items: center; margin: 20px;
        }
          </style>
    <script>
        function openCreateModal() {
            document.getElementById('createUserModal').style.display = 'block';
        }
    
        function closeCreateModal() {
            document.getElementById('createUserModal').style.display = 'none';
        }
        function openEditModal(id, email, password, firstName, lastName, gender, favoriteColor, dateOfBirth, createdAt, updatedAt, role) {
            document.getElementById('edit_id').value = id;
            document.getElementById('edit_email').value = email;
            document.getElementById('edit_password').value = password;
            document.getElementById('edit_firstName').value = firstName;
            document.getElementById('edit_lastName').value = lastName;
            document.getElementById('edit_gender').value = gender;
            document.getElementById('edit_favoriteColor').value = favoriteColor;
            document.getElementById('edit_dateOfBirth').value = dateOfBirth;
            document.getElementById('edit_createdAt').value = createdAt;
            document.getElementById('edit_updatedAt').value = updatedAt;
            document.getElementById('edit_role').value = role;
            document.getElementById('edit_isActive').value = "true";

            document.getElementById('editUserModal').style.display = 'block';
        }


        function closeEditModal() {
            document.getElementById('editUserModal').style.display = 'none';
        }
        document.addEventListener("DOMContentLoaded", function () {
            const today = new Date().toISOString().split('T')[0];

            const createDOB = document.getElementById("create_dateOfBirth");
            const editDOB = document.getElementById("edit_dateOfBirth");

            if (createDOB) createDOB.max = today;
            if (editDOB) editDOB.max = today;
        });
        function filterTable() {
        const input = document.getElementById("searchInput");
        const filter = input.value.toLowerCase();
        const table = document.querySelector("table");
        const rows = table.getElementsByTagName("tr");

        for (let i = 1; i < rows.length; i++) {
            const cells = rows[i].getElementsByTagName("td");
            let match = false;
            for (let j = 0; j < cells.length - 1; j++) { // Skip action buttons
                const cell = cells[j];
                if (cell.textContent.toLowerCase().includes(filter)) {
                    match = true;
                    break;
                }
            }
            rows[i].style.display = match ? "" : "none";
        }
    }
</script>
    
</body>
</html>
