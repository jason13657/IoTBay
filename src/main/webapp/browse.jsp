<%@ page import="java.util.List" %>
<%@ page import="model.Product" %>
<%
    List<model.Product> products = (List<model.Product>) request.getAttribute("results");
    String keyword = (String) request.getAttribute("keyword");
%>


<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <link rel="stylesheet" href="css/styles.css"/>
    </head>
    <body>
        <jsp:include page="components/header.jsp" />

        <section class="base__container">
            <h2>Results for "<%= keyword %>"</h2>
            <div class="product__container">
                <%
                    if (products != null && !products.isEmpty()) {
                        for (Product p : products) {
                %>
                <a href="product?productId=<%= p.getId() %>" class="product__link">
                    <div class="product__card">
<<<<<<< HEAD
                        <img class="product__image" src="<%= p.getImageUrl() %>" alt="<%= p.getName() %>" />
=======
                        <img class="product__image" src="<%= p.getImageUrl() %>" alt="<%= p.getName() %>" onerror="this.onerror=null;this.src='https://i.imgur.com/EJLFNOwg.jpg';" />
>>>>>>> origin/main
                        <h4 class="product__title"><%= p.getName() %></h4>
                        <p class="product__price">$<%= p.getPrice() %></p>
                    </div>
                </a>
                <%
                        }
                    } else {
                %>
                        <p>No results found.</p>
                <%
                    }
                %>
            </div>
        </section>
    </body>
    </div>
        </section>

    </body>
</html>
