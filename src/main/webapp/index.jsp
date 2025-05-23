<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link rel="stylesheet" href="css/styles.css" />
        <title>IoT Bay</title>
    </head>
    <body>
        <jsp:include page="components/header.jsp" />
        <nav class="nav__container">
            <ul class="nav__list">
                <li class="nav__item">
                    <a href="" class="nav__link">Industrial</a>
                </li>
                <li class="nav__item">
                    <a href="" class="nav__link">Warehouse</a>
                </li>
                <li class="nav__item">
                    <a href="" class="nav__link">Agriculture</a>
                </li>
                <li class="nav__item">
                    <a href="" class="nav__link">Smart Home</a>
                </li>
            </ul>
        </nav>

        <section class="hero">
            <img class="hero__image" src="images/hero.png" alt="IoT Hero main"/>
        </section>

        <section class="base__container">
            <h3 class="featured__title">Featured Products</h3>
            <div class="product__container">
                <div class="product__card">
                    <img class="product__image" src="images/sample1.png" alt="Sample 1" />
                    <h4 class="product__title">Sensor</h4>
                    <p class="product__price">$459</p>
                </div>
                    <div class="product__card">
                    <img class="product__image" src="images/sample2.png" alt="Sample 2" />
                    <h4 class="product__title">Cables</h4>
                    <p class="product__price">$35</p>
                </div>
                    <div class="product__card">
                    <img class="product__image" src="images/sample3.png" alt="Sample 3" />
                    <h4 class="product__title">Battery</h4>
                    <p class="product__price">$99</p>
                </div>
            </div>
        </section>
        <jsp:include page="components/footer.jsp" />
    </body>
</html>
