package JDBC;

import java.sql.*;
import java.util.ArrayList;

public class jdbc_demo {
    public static Connection connection;
    public static ArrayList<Orders> orders = new ArrayList<>();
    public static ArrayList<Employee> employees = new ArrayList<>();

    public static void main(String[] args) throws SQLException {
        try {
            initDatabaseConnection();
//            readDataOrders();
//            readDataEmployees();
//            ordersInMonth();
//            countEmployees();
//            addManyEmployee();
//            addManyOffice();
//            updateOffice(1, "31 street Red");
//            updateAllCustomer();
//            addManyProducts();
            deleteCustomers();
        } finally {
            closeDatabaseConnection();
        }
    }

    private static void initDatabaseConnection() throws SQLException {
        System.out.println("Connecting to the Database...");
        connection = DriverManager.getConnection(
                "jdbc:mariadb://localhost:3306/classicmodels",
                "root",
                "haininh100902");
        System.out.println("Connection valid: " + connection.isValid(5));
    }

    private static void closeDatabaseConnection() throws SQLException {
        System.out.println("Closing database connection...");
        connection.close();
        System.out.println("Connection valid: " + connection.isValid(5));
    }

    // 1 - No 2: tạo mảng order rồi in ra
    private static void readDataOrders() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("""
                SELECT * FROM orders;
                
                """)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                boolean empty = true;
                while (resultSet.next()) {
                    empty = false;
                    Orders order = new Orders(
                            resultSet.getInt(1),
                            resultSet.getDate(2),
                            resultSet.getDate(3),
                            resultSet.getDate(4),
                            resultSet.getString(5),
                            resultSet.getString(6),
                            resultSet.getInt(7));
                    orders.add(order);
                }
                if (empty) {
                    System.out.println("No Data");
                } else {
                    int count = 1;
                    for (Orders o : orders) {
                        System.out.println(count + ": " + o);
                        count++;
                    }
                }

            }

        }
    }

    // 2 - No 3: tạo mảng employees rồi in ra
    private static void readDataEmployees() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("""
                SELECT * FROM employees;
                        
                """)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                boolean empty = true;
                while (resultSet.next()) {
                    empty = false;
                    Employee employee = new Employee(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getString(6),
                            resultSet.getInt(7),
                            resultSet.getString(8)
                    );
                    employees.add(employee);
                }
                if (empty) {
                    System.out.println("No Data");
                } else {
                    int count = 1;
                    for (Employee e : employees) {
                        System.out.println(count + ": " + e);
                        count++;
                    }
                }

            }

        }
    }

    // 3 - No 6: đến số lượng employees
    private static void countEmployees() throws SQLException{
        try(PreparedStatement statement = connection.prepareStatement("""
        SELECT COUNT(employees.employeeNumber) FROM employees;
        """)){
            try(ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()){
                    System.out.println("Numbers Employee: " +  resultSet.getInt(1));
                }
            }
        }
    }
    // 4 - No 11: tìm tất cả orders trong tháng 11/2003
    private static void ordersInMonth() throws SQLException {
        orders.removeAll(orders);
        try (PreparedStatement statement = connection.prepareStatement("""
                SELECT * FROM orders
                WHERE orders.orderDate >= "2003-11-01" and orders.orderDate <= "2003-11-30";
                """)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                boolean empty = true;
                while (resultSet.next()) {
                    empty = false;
                    Orders order = new Orders(
                            resultSet.getInt(1),
                            resultSet.getDate(2),
                            resultSet.getDate(3),
                            resultSet.getDate(4),
                            resultSet.getString(5),
                            resultSet.getString(6),
                            resultSet.getInt(7));
                    orders.add(order);
                }
                if(empty){
                    System.out.println("No Data");
                }else{
                    int count = 1;
                    for (Orders o:orders) {
                        System.out.println(count + ": " + o);
                        count++;
                    }
                }
            }
        }
    }

    // 5 - No 12: thêm 2 employee
    private static void addEmployee(int employeeNumber, String lastName, String firstName, String extension, String email, String officeCode, int reportsTo, String jobTitle) throws SQLException{
        try(PreparedStatement statement = connection.prepareStatement("""
        INSERT INTO employees(employeeNumber, lastName, firstName, extension, email, officeCode, reportsTo, jobTitle)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """)){
            statement.setInt(1, employeeNumber);
            statement.setString(2, lastName);
            statement.setString(3, firstName);
            statement.setString(4, extension);
            statement.setString(5, email);
            statement.setString(6, officeCode);
            statement.setInt(7, reportsTo);
            statement.setString(8, jobTitle);
            int rowInserted = statement.executeUpdate();
            System.out.println("Row inserted: " + rowInserted);
        }
    }
    private static void addManyEmployee() throws SQLException{
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(new Employee(9991, "Ninh", "Nguyen", "x1009", "hninh123@gmail.com", "1", 1009, "VP Sales"));
        employees.add(new Employee(9990, "Ninh", "Nguyen", "x1009", "hninh123@gmail.com", "1", 1009, "VP Sales"));
        try(PreparedStatement statement = connection.prepareStatement("""
        INSERT INTO employees(employeeNumber, lastName, firstName, extension, email, officeCode, reportsTo, jobTitle)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """)){
            int rowInserted = 0;
            for(Employee e: employees){
                statement.setInt(1, e.getEmployeeNumber());
                statement.setString(2, e.getLastName());
                statement.setString(3, e.getFirstName());
                statement.setString(4, e.getExtension());
                statement.setString(5, e.getEmail());
                statement.setString(6, e.getOfficeCode());
                statement.setInt(7, e.getReportTo());
                statement.setString(8, e.getJobTitle());
                statement.addBatch();
                rowInserted += statement.executeUpdate();
            }
            System.out.println("Row inserted: " + rowInserted);


        }
    }

    // 6 - No 13: thêm 2 office (Tương tự như thêm Employees)
    private static void addManyOffice() throws SQLException{
        ArrayList<Offices> offices = new ArrayList<>();
        offices.add(new Offices("10", "Bac Ninh", "+84 0373695999", "Dong Nguyen", null, "BN", "VietNam", "12345", "VietNam"));
        offices.add(new Offices("11", "Quang Ninh", "+84 0373695999", "Dong Nguyen", null, "BN", "VietNam", "12345", "VietNam"));
        try(PreparedStatement statement = connection.prepareStatement("""
        INSERT INTO offices(officeCode, city, phone, addressLine1, addressLine2, state, country, postalCode, territory)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);
        """)){
            int rowInserted = 0;
            for (Offices o: offices){
                statement.setString(1, o.getOfficeCode());
                statement.setString(2, o.getCity());
                statement.setString(3, o.getPhone());
                statement.setString(4, o.getAddressLine1());
                statement.setString(5, o.getAddressLine2());
                statement.setString(6, o.getState());
                statement.setString(7, o.getCountry());
                statement.setString(8, o.getPostalCode());
                statement.setString(9, o.getTerritory());
                statement.addBatch();
                rowInserted += statement.executeUpdate();
            }
            System.out.println("Row inserted: " + rowInserted);
        }
    }

    // 7 - No 14: sửa addressLine2 = '31 street Red'  co officeCOde =1
    private static void updateOffice(int officeCode, String address) throws SQLException{
        try(PreparedStatement statement = connection.prepareStatement("""
            UPDATE offices
            SET addressLine2 = ?
            WHERE officeCode = ?;
        """)){
            statement.setString(1, address);
            statement.setInt(2, officeCode);
            int rowsUpdated = statement.executeUpdate();
            System.out.println("Rows updated: " + rowsUpdated);
        }
    }

    // 8 - No 15: update tất cả customer có addressLine là Null bằng addressLine1
    private static void updateAllCustomer() throws SQLException{
        try(PreparedStatement statement = connection.prepareStatement("""
        UPDATE customers
        SET addressLine2 = addressLine1
        WHERE addressLine2 IS NULL;
        """)){
            int rowsUpdated = statement.executeUpdate();
            System.out.println("Rows updated: " + rowsUpdated);
        }
    }

    // 9 - No 22: thêm 3 products
    private static void addManyProducts() throws SQLException{
        ArrayList<Products> products = new ArrayList<>();
        products.add(new Products("S10-9999", "1900 Harley Davidson Ultimate Chopper", "Motorcycles", "1:10", "Min Lin Diecast", "This replica features working kickstand, front suspension, gear-shift lever, footbrake lever, drive chain, wheels and steering. All parts are particularly delicate due to their precise scale and require special care and attention.", 7933, 48.81, 95.7));
        products.add(new Products("S10-9998", "1900 Harley Davidson Ultimate Chopper", "Motorcycles", "1:10", "Min Lin Diecast", "This replica features working kickstand, front suspension, gear-shift lever, footbrake lever, drive chain, wheels and steering. All parts are particularly delicate due to their precise scale and require special care and attention.", 7933, 48.81, 95.7));
        products.add(new Products("S10-9997", "1900 Harley Davidson Ultimate Chopper", "Motorcycles", "1:10", "Min Lin Diecast", "This replica features working kickstand, front suspension, gear-shift lever, footbrake lever, drive chain, wheels and steering. All parts are particularly delicate due to their precise scale and require special care and attention.", 7933, 48.81, 95.7));
        try(PreparedStatement statement = connection.prepareStatement("""
        INSERT INTO products(productCode, productName, productLine, productScale, productVendor, productDescription, quantityInStock, buyPrice, MSRP)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """)){
            int rowInserted = 0;
            for(Products p: products){
                statement.setString(1, p.getProductCode());
                statement.setString(2, p.getProductName());
                statement.setString(3, p.getProductLine());
                statement.setString(4, p.getProductScale());
                statement.setString(5, p.getProductVendor());
                statement.setString(6, p.getProductDescription());
                statement.setInt(7, p.getQuantityInStock());
                statement.setDouble(8, p.getBuyPrice());
                statement.setDouble(9, p.getMSRP());
                statement.addBatch();
                rowInserted += statement.executeUpdate();
            }
            System.out.println("Row inserted: " + rowInserted);

        }
    }

    // 10 - No 23: xóa customers không có bất kì orders nào
    private static void deleteCustomers() throws SQLException{
        try(PreparedStatement statement = connection.prepareStatement("""
        DELETE c FROM customers c
        LEFT JOIN orders o using(customerNumber)
        WHERE o.customerNumber IS NULL;        
        """)){
            int rowsDeleted = statement.executeUpdate();
            System.out.println("Rows deleted: " + rowsDeleted);
        }
    }

    // 11 - No 24: liệt kê orders, orderdetails theo từng customers

    // 12 - No 25: liệt kê customers tại USA có số lượng payment nhiều nhất

    // 13 - No 26: liệt kê 3 customers có lượng amout it́ nhất
}

