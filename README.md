# IoTBay Web Application

This is a web-based IoT device management and purchasing application developed using **JSP**, **JavaBeans (MVC Model)**, **Maven**, and **Jetty server**.

---

## 🚀 How to Deploy and Run the Project

### 1. Prerequisites

- Java JDK 8 or higher installed
- Maven installed
- Internet connection to download Maven dependencies
- (Optional) IntelliJ IDEA, Eclipse, or VS Code for editing

---

### 2. Project Setup

1. **Build the Project**  
   In the project root directory (where `pom.xml` is located), run:

   ```bash
   mvn clean install
   ```

2. **Run Using Maven Jetty Plugin**  
   Start the Jetty server directly with:

   ```bash
   mvn jetty:run
   ```

3. **Access the Application**  
   Open your browser and visit:
   ```
   http://localhost:8080/
   ```

---

### 3. Project Structure

```
/src/main/java/model/         → JavaBeans (data models like User.java, Product.java, Order.java, etc.)
/src/main/resources/          → (reserved for config files if needed)
/src/main/webapp/             → Frontend files (JSP + assets)
/src/main/webapp/components/  → Reusable JSP components (header.jsp, footer.jsp)
/src/main/webapp/css/         → CSS files (e.g., styles.css)
/src/main/webapp/images/      → Image assets
/src/main/webapp/WEB-INF/     → Protected resources (web.xml, etc.)
/src/main/webapp/index.jsp    → Landing page (Home)
/src/main/webapp/login.jsp    → Login page
/src/main/webapp/logout.jsp   → Logout page
/src/main/webapp/register.jsp → Registration page
/src/main/webapp/welcome.jsp  → Welcome page
/pom.xml                      → Maven configuration file
/target/                      → Compiled build output (generated automatically)
```

- **Controllers**: Handle HTTP requests (Servlets)
- **Models**: JavaBeans representing entities like User, Product, Order
- **Views**: JSP pages

---

### 4. Common Commands

| Task             | Command         |
| ---------------- | --------------- |
| Clean project    | `mvn clean`     |
| Compile project  | `mvn compile`   |
| Package WAR file | `mvn package`   |
| Run project      | `mvn jetty:run` |

---

### 5. Stopping the Server

To stop Jetty server, simply press:  
`Ctrl + C` in your terminal.

---

### 6. Notes

- Default server port: **8080**
- If 8080 is already in use, you can change it by adding this to your `pom.xml` under `<configuration>`:
  ```xml
  <httpConnector>
    <port>8090</port>
  </httpConnector>
  ```

---

# 🛠 Technologies Used

- Java (JSP + Servlets)
- Maven
- Jetty Maven Plugin
- HTML/CSS (inside JSPs)
- MVC architecture

---

# 📄 License

This project is for academic purposes.
