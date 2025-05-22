<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Product" %>

<!DOCTYPE html>
<html>
<head>
    <title>Manage Products</title>
</head>
<body>
    <h1>Manage Products</h1>
    <div class="btn-container">
    <div style="margin: 20px;">
        <button onclick="location.href='/index.jsp'" style="padding: 8px 16px; font-size: 16px; background-color: #6c757d; color: white; border: none; border-radius: 5px; cursor: pointer;">
            &larr; Back to Home
        </button>
    </div>
    <div style="text-align: center; margin: 20px;">
        <input type="text" id="searchInput" onkeyup="filterTable()" placeholder="Search products..." style="width: 300px; padding: 8px; font-size: 16px;">
    </div>
    
    <div style="text-align: right;">
        <button id="main-create-btn" class="create-btn" onclick="openCreateModal()">Create New Product</button>
    </div>
    </div>
    </body>
</div>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Category ID</th>
                <th>Name</th>
                <th>Description</th>
                <th>Price</th>
                <th>Stock QTY</th>
                <th>Image URL</th>
                <th>Date Created</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
        <%
            List<Product> products = (List<Product>) request.getAttribute("products");
            if (products != null && !products.isEmpty()) {
                for (Product product : products) {
        %>
            <tr>
                <td><%= product.getId() %></td>
                <td><%= product.getCategoryId() %></td>
                <td><%= product.getName() %></td>
                <td><%= product.getDescription() %></td>
                <td><%= product.getPrice() %></td>
                <td><%= product.getStockQuantity() %></td>
                <td class="image-url"><%= product.getImageUrl() %></td>
                <td><%= product.getCreatedAt() %></td>
                <td>
                    <button class="edit-btn" onclick="openEditModal(
                        '<%= product.getId() %>',
                        '<%= product.getCategoryId() %>',
                        '<%= product.getName().replace("'", "\\'") %>',
                        '<%= product.getDescription().replace("'", "\\'") %>',
                        '<%= product.getPrice() %>',
                        '<%= product.getStockQuantity() %>',
                        '<%= product.getImageUrl() %>',
                        '<%= product.getCreatedAt() %>'
                    )">Edit</button>
                    <button class="delete-btn" onclick="openDeleteModal(<%= product.getId() %>)">Delete</button>
                </td>
            </tr>
        <%
                }
            } else {
        %>
            <tr>
                <td colspan="5" style="text-align:center;">No products available.</td>
            </tr>
        <%
            }
        %>
        </tbody>
    </table>
    <div id="createProductModal" class="modal" style="display:none;">
        <div class="modal-content">
            <span class="close" onclick="closeCreateModal()">&times;</span>
            <form action="/manage/products" method="post">
                <input type="hidden" name="product_id"><br>

                <label>Name:</label>
                <input type="text" name="name" required><br>
    
                <label>Description:</label>
                <textarea name="description" required></textarea><br>
    
                <label>Price:</label>
                <input type="number" name="price" step="0.01" required min="0.01"><br>
    
                <label>Stock Quantity:</label>
                <input type="number" name="stockQuantity" required min="1"><br>
    
                <label>Category ID:</label>
                <input type="number" name="categoryId" required min="1" max="5"><br>
    
                <label>Image URL:</label>
                <input type="text" name="imageUrl"><br>
    
                <label>Created At:</label>
                <input type="date" name="created_at" id="created_at" required><br>
    
                <button type="submit" id="submit-modal-btn" class="create-btn">Create</button>
            </form>
        </div>
    </div>
        <div id="editProductModal" class="modal" style="display:none;">
            <div class="modal-content">
                <span class="close" onclick="closeEditModal()">&times;</span>
                <form action="/manage/products/update" method="post">
                    <input type="hidden" name="product_id" id="edit_id">
                    <label>Name:</label>
                    <input type="text" name="name" id="edit_name" required>
    
                    <label>Description:</label>
                    <textarea name="description" id="edit_description" required></textarea>
    
                    <label>Price:</label>
                    <input type="number" name="price" step="0.01" id="edit_price" required min="0.01">
    
                    <label>Stock Quantity:</label>
                    <input type="number" name="stockQuantity" id="edit_stockQuantity" required min="1">
    
                    <label>Category ID:</label>
                    <input type="number" name="categoryId" id="edit_categoryId" required min="1" max="5">
    
                    <label>Image URL:</label>
                    <input type="text" name="imageUrl" id="edit_imageUrl">
    
                    <label>Created At:</label>
                    <input type="date" name="created_at" id="edit_created_at" required>
    
                    <button type="submit" class="update-btn">Update</button>
                </form>
            </div>
        </div>
        <div id="deleteConfirmModal" class="modal" style="display: none;">
            <div class="modal-content">
              <span class="close" onclick="closeDeleteModal()">&times;</span>
              <h2>Confirm Delete</h2>
              <p>Are you sure you want to delete this user?</p>
              <form id="deleteForm" method="post" action="<%=request.getContextPath()%>/manage/products/delete">
                <input type="hidden" name="id" id="deleteUserId">
                <div class="modal-actions">
                  <button type="button" id="delete-cancel-btn" onclick="closeDeleteModal()">Cancel</button>
                  <button type="submit" class="delete-btn">Delete</button>
                </div>
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
        td.image-url {
        max-width: 200px;
        word-break: break-all;
        white-space: normal;
        overflow-wrap: break-word;
    }

        input[type="text"],
        input[type="number"],
        input[type="date"],
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
            font-size: 16px;
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
        #delete-cancel-btn {
            background-color: #f0f0f0;
            border: 1px solid #ccc;
            color: #333;
            padding: 6px 12px;
            border-radius: 4px;
            font-weight: 500;
            cursor: pointer;
            transition: background-color 0.2s ease, border-color 0.2s ease;
        }
          </style>
    <script>
        function openCreateModal() {
            document.getElementById('createProductModal').style.display = 'block';
        }
    
        function closeCreateModal() {
            document.getElementById('createProductModal').style.display = 'none';
        }
        function openEditModal(id, categoryId, name, description, price, stockQty, imageUrl, createdAt) {
            document.getElementById('editProductModal').style.display = 'flex';
            document.getElementById('edit_id').value = id;
            document.getElementById('edit_name').value = name;
            document.getElementById('edit_description').value = description;
            document.getElementById('edit_price').value = price;
            document.getElementById('edit_stockQuantity').value = stockQty;
            document.getElementById('edit_categoryId').value = categoryId;
            document.getElementById('edit_imageUrl').value = imageUrl;
            document.getElementById('edit_created_at').value = createdAt;
        }

        function closeEditModal() {
            document.getElementById('editProductModal').style.display = 'none';
        }

        window.addEventListener('DOMContentLoaded', () => {
        const today = new Date().toISOString().split('T')[0];
        const createdAtInput = document.getElementById('created_at');
        createdAtInput.value = today;
        createdAtInput.max = today;
    });

    window.addEventListener('DOMContentLoaded', () => {
        const today = new Date().toISOString().split('T')[0];
        const createdAtInput = document.getElementById('edit_created_at');
        createdAtInput.max = today;
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
    function openDeleteModal(userId) {
    document.getElementById('deleteUserId').value = userId;
    document.getElementById('deleteConfirmModal').style.display = 'block';
  }

  function closeDeleteModal() {
    document.getElementById('deleteConfirmModal').style.display = 'none';
  }

  // Optional: Close modal if clicked outside
  window.onclick = function(event) {
    const modal = document.getElementById('deleteConfirmModal');
    if (event.target == modal) {
      closeDeleteModal();
    }
  }
    </script>
</body>
</html>
