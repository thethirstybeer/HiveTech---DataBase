show databases;
use classicmodels;
# No 2
select * from offices o where country ='USA';
# No 3
select *, concat(firstName, ' ', lastName)  as "full name" from employees e;
# No 4
select * , o.city as "offices.city", o.state as "offices.state"  from employees e 
left join offices o ON 
	o.officeCode = e.officeCode 
where o.state is not null ;
# No 5 liệt kê offices ko chứa employee nào
select o.officeCode , count(e.officeCode)  from offices o 
left join employees e on e.officeCode = o.officeCode 
where e.officeCode is not null 
group by e.officeCode
having count(e.officeCode ) = 0;

# No 6 đến số lượng employees
select count(e.employeeNumber) numbersEmployye from employees e ;


# No 7 liệt kê customers có nhiều nhất 3 payments 
select c.customerNumber, count(p.customerNumber) countPayment from customers c 
inner join payments p ON p.customerNumber = c.customerNumber 
group by p.customerNumber 
having count(p.customerNumber) <= 3;

# No 8 liệt kê 10 customer có payments sớm nhất
select c.customerNumber , p.paymentDate  from customers c 
inner join payments p on p.customerNumber = c.customerNumber
where p.paymentDate is not null 
order by p.paymentDate 
limit 10;

# No 9 liệt kê các customers có salesRepEmployeeNumber tồn tại
select * from customers c
inner join employees e on e.employeeNumber = c.salesRepEmployeeNumber;

# No 10 liệt kê 10 customer có nhiều payment nhất, sắ xếp giảm dần số lượng payment
select c.customerNumber, count(p.customerNumber) countPayment from customers c
inner join payments p  using (customerNumber)
group by p.customerNumber
order by count(p.customerNumber) desc
limit 10;

# No 11 tìm tất cả orders trong tháng 11/2003
select * from orders
where orderDate >= "2003-11-01" and orderDate <= "2003-11-30";

# No 12 thêm 2 employee

insert into employees (employeeNumber, lastName, firstName, extension, email, officeCode, reportsTo, jobTitle)
values
(1009, "Ninh", "Nguyen", "x1009", "hninh123@gmail.com", "1", 1009, "VP Sales"),
(1010, "Ninh1", "Nguyen", "x1010", "hninh321@gmail.com", "1", 1010, "VP Marketing");

# No 13 thêm 2 office
insert into offices (officeCode, city, phone, addressLine1, addressLine2, state, country, postalCode, territory)
values
(8, "Bac Ninh", "+84 0373695999", "Dong Nguyen", null, "BN", "VietNam", "12345", "VietNam"),
(9, "Ha Noi", "+84 0373695888", "Tran Dai Nghia", null, "HN", "VietNam", "54321", "VietNam");

# No 14 sửa addressLine2 = '31 street Red'  co officeCOde =1
update offices 
set addressLine2 = "31 Street Red"
where officeCode = 1;

# No 15 update tất cả customer có addressLine là Null bằng addressLine1
update customers
set addressLine2 = addressLine1
where addressLine2 is null;


# No 16 update tất cả customer có state là Null bằng 3 kí tự dầu của city, viết hoa chữ cái đầu
update customers
set state = concat(upper(substr(city, 1, 1)), substr(city, 2, 2))
where state is null;

# No 17 liệt kê 10 customer có nhiều orders nhất
select c.customerNumber, count(o.customerNumber) countOrders from customers c 
inner join orders o ON o.customerNumber = c.customerNumber 
group by o.customerNumber
order by count(o.customerNumber) desc 
limit 10;

# No 18 liệt kê tất cả orderdetails của từng orders
select o.* from orderdetails o
left join orders o2 on o2.orderNumber = o.orderNumber;

# No 19 liệt kê tất cả orderdetails, order có productCode bắt đầu là S10
select * from orderdetails o
left join orders o2 on o2.orderNumber = o.orderNumber
where substring(productCode, 1, 3) = "S10";

# No 20 liệt kê 11 product có ít orderdetails nhâṭ
select p.productCode, count(o.productCode) countOrderDetails  from products p
inner join orderdetails o on o.productCode = p.productCode
group by o.productCode
order by count(o.productCode)
limit 11;

# No 21 liệt kê thông tin productlines, products, orderdetails của từng order
select p.productLine , p2.productCode , o.orderNumber from productlines p 
left join products p2 on p2.productLine = p.productLine 
left join orderdetails o on o.productCode = p2.productCode 
left join orders o2 on o2.orderNumber = o.orderNumber;

# No 22 thêm 3 products
insert into products (productCode, productName, productLine, productScale, productVendor, productDescription, quantityInStock, buyPrice, MSRP)
values
	("S10-1679", "1900 Harley Davidson Ultimate Chopper", "Motorcycles", "1:10", "Min Lin Diecast", "This replica features working kickstand, front suspension, gear-shift lever, footbrake lever, drive chain, wheels and steering. All parts are particularly delicate due to their precise scale and require special care and attention.", 7933, 48.81, 95.7),
	("S10-1989", "1900 Harley Davidson Ultimate Chopper", "Motorcycles", "1:10", "Min Lin Diecast", "This replica features working kickstand, front suspension, gear-shift lever, footbrake lever, drive chain, wheels and steering. All parts are particularly delicate due to their precise scale and require special care and attention.", 7933, 48.81, 95.7),
	("S10-1000", "1900 Harley Davidson Ultimate Chopper", "Motorcycles", "1:10", "Min Lin Diecast", "This replica features working kickstand, front suspension, gear-shift lever, footbrake lever, drive chain, wheels and steering. All parts are particularly delicate due to their precise scale and require special care and attention.", 7933, 48.81, 95.7);

# No 23 xóa customers không có bất kì orders nào
select c.customerNumber , c.customerName, o.orderNumber  from customers c
left join orders o on o.customerNumber = c.customerNumber 
where o.orderNumber is null;

delete c from customers c
left join orders o on o.customerNumber = c.customerNumber 
where o.orderNumber is null ;

# No 24 liệt kê orders, orderdetails theo từng customers
select o.*, o2.*  from customers c 
left join orders o on c.customerNumber = o.customerNumber 
left join orderdetails o2 on o.orderNumber = o2.orderNumber ;

# No 25 liệt kê customers tại USA có số lượng payment nhiều nhất
select c.customerNumber , c.customerName, count(p.customerNumber),c.country  from customers c 
inner join payments p on c.customerNumber = p.customerNumber 
where country = "USA"
group by p.customerNumber
order by count(p.customerNumber) desc
limit 1;

# No 26 liệt kê 3 customers có lượng amout it́ nhất
select c.customerNumber , c.customerName , Sum(p.amount)  from customers c
inner join payments p on c.customerNumber = p.customerNumber
group by c.customerNumber 
order by Sum(p.amount)
limit 3;

# No 27 liệt kê 5 products có số lượng payment nhiều nhất
select c.customerNumber , c.customerName , count(p.customerNumber) from customers c
inner join payments p ON c.customerNumber = p.customerNumber
group by p.customerNumber 
order by count(p.customerNumber) desc
limit 5;

# No 28 liệt kê orders được thanh toán trong tháng 4-2003 & tháng 12-2003
select * from orders o
where (o.shippedDate >= "2003-04-01" and o.shippedDate <= "2003-04-30") or
	  (o.shippedDate >="2003-12-01" and o.shippedDate <= "2003-12-30");

# No 29 liệt kê 1 office có số lượng employee chăm sóc nhiều customers nhất
select o.officeCode , count(c.salesRepEmployeeNumber) as countCustomer  from offices o 
inner join employees e on o.officeCode = e.officeCode
inner join customers c on e.employeeNumber = c.salesRepEmployeeNumber 
group by o.officeCode
order by count(c.salesRepEmployeeNumber  ) desc
limit 1;

# No 30 liệt kê 2 customer có giới hạn tín dụng < 20000
select c.customerNumber , c.customerName, c.creditLimit  from customers c
where c.creditLimit < 20000
limit 2;

# No 31 liệt kê 2 products tồn hàng nhiều nhất
select p.productCode , p.productName, p.quantityInStock  from products p
order by p.quantityInStock desc
limit 2;

# No 32 liêt kê 10 products có giá thấp nhất & được nhiều KH chọn nhất
select p.productCode , p.productName, p.buyPrice , count(o.productCode)  from products p
inner join orderdetails o on p.productCode = o.productCode
group by p.productCode
order by p.buyPrice  , count(o.productCode  ) desc
limit 10;

# No 33 liệt kê 5 sản phẩm bị Cancelled nhất
select p.productCode , p.productName , o.orderNumber, count(o2.status)  from products p 
inner join orderdetails o on p.productCode = o.productCode 
inner join orders o2 on o.orderNumber = o2.orderNumber 
where o2.status = "Cancelled"
group by p.productCode 
order by count(o2.status ) desc
limit 5;

# No 34 liệt kê 5 sản phâm được giao hàng sớm nhất trong năm 2004
select p.productCode, o.orderNumber, o2.shippedDate  from products p 
inner join orderdetails o on p.productCode = o.productCode
inner join orders o2 on o.orderNumber = o2.orderNumber 
where o2.shippedDate >= "2004-01-01" and o2.shippedDate <= "2004-12-30"
limit 5;

# No 35 tính tổng số tiền đã được thanh toán theo từng KH có payment trong năm 2004
select c.customerNumber , c.customerName, sum(p.amount) from customers c
inner join payments p on c.customerNumber = p.customerNumber
where p.paymentDate between "2004-01-01" and "2004-12-31"
group by c.customerNumber  ;

# No 36 tìm 2 employees bị báo cáo nhiều nhất
select e.employeeNumber , count(e.employeeNumber) from employees e 
inner join employees e2 on e.employeeNumber = e2.reportsTo 
group by e.employeeNumber 
order by count(e.employeeNumber) desc
limit 2;

# No 37 tìm 2 product ko được đặt hàng trong năm 2005 (Chưa xong)
select p.* from products p 
left join orderdetails o using (productCode)
left join orders o2 using (orderNumber)
where o2.orderDate not between "2005-01-1" and "2005-12-31"
limit 2;

# No 38 top 10 oder được ship tính từ thời gian orderDate ko quá 3 ngày
select o.orderNumber , o.orderDate , o.shippedDate from orders o
where date_sub(o.shippedDate, interval 3 day) = o.orderDate
limit 10;

# No 39 tìm 10 oder dã được hoàn thành ship trong tháng 12-2004
select *  from orders o
where o.shippedDate  >= "2004-12-01" and o.shippedDate <= "2004-12-30" and o.status = "Shipped"
limit 10;
