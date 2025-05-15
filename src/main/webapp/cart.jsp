<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link rel="stylesheet" href="css/styles.css" />
        <title>Cart</title>
    </head>
    <body>
        <nav class="cart-nav">
            <a href="index.jsp"><img src="images/logo.png"></a>

            <button class="exit-cart-btn">X</button>
        </nav>
        <!-- display cart IF user is logged in 
        if not logged in, display login/register page -->
        <section class="cart-container">
            <div class="cart-header">
                <h2>Your Cart</h2>
            </div>
            <div class="cart-main">

            </div>
            <div class="cart-footer">
                <h3>Subtotal:</h3>
                <h2>Total:</h2>
            </div>
        </section>
        <!-- IF cart is empty, display empty cart message -->
    </body>
</html>
