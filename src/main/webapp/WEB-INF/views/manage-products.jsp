<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Product" %>

<!DOCTYPE html>
<html>
<head>
    <title>Manage Products</title>
    <style>
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
    </style>
</head>
<body>
    <h1>Manage Products</h1>
    <div style="text-align: right;">
        <button onclick="openCreateModal()">Create New Product</button>
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
                    <form action="<%=request.getContextPath()%>/manage/products/delete" method="post" style="display:inline;">
                        <input type="hidden" name="id" value="<%= product.getId() %>">
                        <button type="submit" class="delete-btn">Delete</button>
                    </form>                    
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
                <label>Product ID:</label>
                <input type="number" name="product_id" required><br>

                <label>Name:</label>
                <input type="text" name="name" required><br>
    
                <label>Description:</label>
                <textarea name="description" required></textarea><br>
    
                <label>Price:</label>
                <input type="number" name="price" step="0.01" required><br>
    
                <label>Stock Quantity:</label>
                <input type="number" name="stockQuantity" required><br>
    
                <label>Category ID:</label>
                <input type="number" name="categoryId" required><br>
    
                <label>Image URL:</label>
                <input type="text" name="imageUrl"><br>
    
                <label>Created At:</label>
                <input type="date" name="created_at" id="created_at" required><br>
    
                <button type="submit">Create</button>
            </form>
        </div>
    </div>
        <!-- EDIT PRODUCT MODAL -->
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
                    <input type="number" name="price" step="0.01" id="edit_price" required>
    
                    <label>Stock Quantity:</label>
                    <input type="number" name="stockQuantity" id="edit_stockQuantity" required>
    
                    <label>Category ID:</label>
                    <input type="number" name="categoryId" id="edit_categoryId" required>
    
                    <label>Image URL:</label>
                    <input type="text" name="imageUrl" id="edit_imageUrl">
    
                    <label>Created At:</label>
                    <input type="date" name="created_at" id="edit_created_at" required>
    
                    <button type="submit">Update</button>
                </form>
            </div>
        </div>
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
        createdAtInput.max = today; // Prevent future dates
    });
    </script>
    
</body>
</html>
