<div>
	<img src="https://github.com/nkhai/nabcom/blob/master/Icom-xtra-setup/flowchart0.png"></img>
<div>
	The above image is workflow for addtocard for icomercial system. The system build base on Axon framework,this framework has support for below pattern:
	<ul>
		<li>CQRS(Command Query Responsibility Segregation)</li>
		<li>domain driven design</li>
		<li>Event Driven</li>
	</ul>	
</div>
<div>
		Below image is CQRS worflow. It is intro how to send command via distributedCommandBus
</div>
<div>
	<img src="https://github.com/nkhai/nabcom/blob/master/Icom-xtra-setup/base_arhcitect.PNG"></img>
</div>
<div>
	we notice, it has 2 progress of query and write data into the DB(mongo and MySQL). The ordercontroller get a request from the user, it will send 1 command to distribute commandbus and waiting to handle.</br></br>
 	<p>The handle command will get 1 command from distributing command bus and progress(must same command event type).</p>
 	<p>The command handle will be sent 2 event updateProductStock and notifyCreation(store­ in history) via rabbitMQ.</p>
 	It will be handle in eventhandle and store to database, updateProductStock will store to MySQL and notifyCreation(Update history) will store in MongoDB.</br></br>
 		<li>the database in MongoDb is focused on query, which means any visualization on the web needs data.</li>
 		<li>The database in MySQL store information about stock,sercurity, it means anything relate to product and user</li></br>
 	The database from Mongo and Mysql will be synchronized via third-party software(similar oracle database guard) .</br></br>
 	Any rollback in progress has handle via saga pattern in Axon. For example, you want to cancel or 1 delivery fail. It will be sent 2 events, the first event will be updated history, and send event will update product stock. It can be many sagas at the same time.</br></br>
</div>
	Below is work-flow to call from user<br/>
	<img src="https://github.com/nkhai/nabcom/blob/master/Icom-xtra-setup/flow_chart1.png"></img>
<br/>
<br/> 
------------------------------------------------------------Enviroment-------------------------------------------- <br/>

<br/>
<ul>
	<li> Unbutu</li>
	<li> Docker</li>
	<li> Docker-compose</li>
	<li> maven</li>
	<li> open jdk 11</li>
	<li> Postman</li>
</ul>

---------------------------------------------------------------Prepare------------------------------------------------------------ <br/>
before you start. You need modify eureka serviceUrl on below file(It is your IP adress)<br/>
.\Icom-cart\src\main\resources\application.yml<br/>
.\Icom-core\src\main\resources\application.yml<br/>
.\Icom-gateway\src\main\resources\application.yml<br/>
.\Icom-history\src\main\resources\application.yml<br/>
.\Icom-product\src\main\resources\application.yml<br/>
.\Icom-user\src\main\resources\application.yml<br/>
.\Icom-registry\src\main\resources\application.yml<br/>
<br/>
Change <br/>
   &emsp;eureka:<br/>
   &emsp;&emsp;client:<br/>
   &emsp;&emsp;&emsp;registerWithEureka: true<br/>
   &emsp;&emsp;&emsp;fetchRegistry: true<br/>
   &emsp;&emsp;&emsp;serviceUrl:<br/>
   &emsp;&emsp;&emsp;defaultZone : http://104.215.148.134:8761/eureka/<br/>
To<br/>
    &emsp;eureka:<br/>
        &emsp;&emsp;client:<br/>
            &emsp;&emsp;&emsp;registerWithEureka: true<br/>
            &emsp;&emsp;&emsp;fetchRegistry: true<br/>
            &emsp;&emsp;&emsp;serviceUrl:<br/>
            &emsp;&emsp;&emsp;defaultZone : http://Your_IP:8761/eureka/<br/>
<br/>
---------------------------------------------------------------------Running----------------------------------------------------------<br/>
--call below command<br/>
<br/>
mvn clean install

----add permisison for sh file to build<br/>
chmod +x /path/to/build.sh<br/>
<br/>
./build.sh<br/>
<br/>
docker-compose up<br/>
<br/>
Note : you need check eureka server on http://Your_IP:8761 and rabbit MQ http://Your_IP:15672 . When all service has register, you can init data as below and test it<br/>
-------------------------------------------------------------------Init Database----------------------------------------------------<br/>
-- import data from mongo to mongodb<br/>
-- go to Icom-xtra-setup folder and call below command<br/>
--change below IP to your server IP<br/>
<br/>
mongoimport --host "104.215.148.134" --port "27017" --db "icom" --collection "inventory" --file "inventory.json" --jsonArray<br/>
mongoimport --host "104.215.148.134" --port "27017" --db "icom" --collection "product" --file "products.json" --jsonArray<br/>
mongoimport --host "104.215.148.134" --port "27017" --db "icom" --collection "productCategory" --file "productCategory.json" --jsonArray<br/>
<br/>
-- import mySQL:<br/>
- access my sql via command<br/>
<br/>
mysql -u root -p123456 -h (your Ip) -P 3306 (example : mysql -u root -p123456 -h 104.215.148.134 -P 3306)<br/>
<br/>
>>>>>>>>>>>>>>call below command to create database(It's may fail because code has create some table but it doesn't have full data)>>>>>>>>>>>>>>>>>>>>>>>>>>>>><br/>
<br/>
use icom;<br/>
-- (database has create when you run code)<br/>
--------------------------Create table-------------------------------<br/>
<br/>
CREATE TABLE `address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `apartment` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `pin` varchar(255) DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;<br/>
<br/>
CREATE TABLE `customer_order` (
  `id` bigint(20) NOT NULL,
  `last_event_sequence_number` bigint(20) DEFAULT NULL,
  `version` bigint(20) DEFAULT NULL,
  `order_date` datetime DEFAULT NULL,
  `order_status` varchar(255) DEFAULT NULL,
  `total` double DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;<br/>
<br/>
CREATE TABLE `inventory` (
  `id` bigint(20) NOT NULL,
  `last_event_sequence_number` bigint(20) DEFAULT NULL,
  `version` bigint(20) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `sku` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;<br/>
<br/>
CREATE TABLE `line_item` (
  `id` bigint(20) NOT NULL,
  `last_event_sequence_number` bigint(20) DEFAULT NULL,
  `version` bigint(20) DEFAULT NULL,
  `inventory_id` bigint(20) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `product` varchar(255) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `order_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_1d3yqp9d56i39oajwjf0f8hkl` (`order_id`),
  CONSTRAINT `FK_1d3yqp9d56i39oajwjf0f8hkl` FOREIGN KEY (`order_id`) REFERENCES `customer_order` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;<br/>
<br/>
CREATE TABLE `user_credential` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `active` tinyint(1) NOT NULL,
  `password` varchar(255) NOT NULL,
  `user_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_14ncv1m0gqncrbiagrs4uaqo8` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;<br/>
<br/>
CREATE TABLE `user_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) NOT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `billing_address_id` bigint(20) DEFAULT NULL,
  `shipping_address_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_hixwjgx0ynne0cq4tqvoawoda` (`user_id`),
  KEY `FK_qbwlk9dbmm8uuja3agc5jo1no` (`billing_address_id`),
  KEY `FK_1wm8vskwwsippi0h0p5ub8vp4` (`shipping_address_id`),
  CONSTRAINT `FK_1wm8vskwwsippi0h0p5ub8vp4` FOREIGN KEY (`shipping_address_id`) REFERENCES `address` (`id`),
  CONSTRAINT `FK_qbwlk9dbmm8uuja3agc5jo1no` FOREIGN KEY (`billing_address_id`) REFERENCES `address` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;<br/>
<br/>
CREATE TABLE `user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_apcc8lxk2xnug8377fatvbn04` (`user_id`),
  CONSTRAINT `FK_apcc8lxk2xnug8377fatvbn04` FOREIGN KEY (`user_id`) REFERENCES `user_credential` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;<br/>
<br/>
---------Create basic data for inventory and user_role---------------------<br/>
<br/>
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(1,0,0,10,'LEE'); <br/>
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(2,0,0,10,'ACTION'); <br/>
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(3,0,0,10,'ADDIDAS');    <br/>  
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(4,0,0,10,'NIKE');  <br/>
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(5,0,0,10,'LEE_1');<br/>
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(6,0,0,10,'ACTION_1');  <br/>
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(7,0,0,10,'LAXME'); <br/>
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(8,0,0,10,'LAXME13'); <br/>
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(9,0,0,10,'ADDIDAS_W');     <br/> 
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(10,0,0,10,'WEDGES');  <br/>
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(11,0,0,10,'LEE_2');<br/>
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(12,0,0,10,'LEE_3');  <br/>
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(13,0,0,10,'RAYBAN1'); <br/>
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(14,0,0,10,'RAYBAN2'); <br/>
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(15,0,0,10,'RAYBAN3');      <br/>
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(16,0,0,10,'FASTTRACK1');  <br/>
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(17,0,0,10,'FASTTRACK2');<br/>
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(18,0,0,10,'FASTTRACK3');  <br/>
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(19,0,0,10,'ARROW1'); <br/>
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(20,0,0,10,'ARROW2'); <br/>
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(21,0,0,10,'ARROW3');      <br/>
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(22,0,0,10,'POLO1');  <br/>
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(23,0,0,10,'POLO2');<br/>
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(24,0,0,10,'POLO3');  <br/>
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(25,0,0,10,'DON1'); <br/>
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(26,0,0,10,'DON2'); <br/>
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(27,0,0,10,'DON3'); <br/>
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(28,0,0,10,'DON4'); <br/>
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(29,0,0,10,'X-COTTEN1');<br/>
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(30,0,0,10,'X-COTTEN2');  <br/>
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(31,0,0,10,'X-COTTEN3');  <br/>
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(32,0,0,10,'X-COTTEN4');  <br/>
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(33,0,0,10,'X-COTTEN5');  <br/>
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(34,0,0,10,'X-COTTEN6');  <br/>
commit;<br/>
insert into user_credential (id,active,password,user_id) values (1,1,'admin','admin');<br/>
insert into user_role (id,role,user_id) values (1,'ROLE_ADMIN',1);<br/>
insert into user_role (id,role,user_id) values (2,'CUSTOMER_READ',1);<br/>
insert into user_role (id,role,user_id) values (3,'PRODUCT_WRITE',1);<br/>
insert into user_role (id,role,user_id) values (4,'ORDER_READ',1);<br/>
insert into user_role (id,role,user_id) values (5,'PRODUCT_WRITE',1);<br/>
insert into user_role (id,role,user_id) values (6,'ORDER_WRITE',1);<br/>
insert into user_info (id,email,first_name,last_name,phone,user_id) values(1,'admin@admin','admin','admin',903766787,'admin');<br/>
commit;<br/>
<br/>
------------------------------------------------------------------------Testing-------------------------------------------------------------<br/>
Basic query of product(Below URL is azure cloud IP, you can use it but it can't remove any time )<br/>
<br/>
http://104.215.148.134:9000/product/categories/<br/>
<br/>
Search product by catalogue name<br/>
http://104.215.148.134:9000/product/productsByCategory?category=women_footwear<br/>
<br/>
Search product by price<br/>
http://104.215.148.134:9000/product/productsByPrice?minprice=10&maxprice=20<br/>
<br/>
Search product by brand<br/>
http://104.215.148.134:9000/product/listofbrand<br/>
http://104.215.148.134:9000/product/productsByBrand?brand=NIKE<br/>
<br/>
Search product by Color<br/>
http://104.215.148.134:9000/product/productsByColor?color=red<br/>
<br/>
Search Product by Name<br/>
http://104.215.148.134:9000/product/productsByName?name=Laxme 12<br/>
<br/>
Get all Product<br/>
http://104.215.148.134:9000/product/products<br/>
<br/>
see product detail<br/>
http://104.215.148.134:9000/product/products/60bf5482eeb8cc2b9a7d24aa<br/>
http://104.215.148.134:9000/product/products/{ProductID}<br/>
<br/>
Post to add to cart:<br/>
http://104.215.148.134:9000/cart/customerCart/<br/>
data<br/>
{"userId":"5aa02b23-6c76-6fc9-2309-8991874c01a6","activeSince":null,"coupen":null,"lineItems":[{"id":"60bf5482eeb8cc2b9a7d24aa","name":"Action classic","price":167,"quantity":2,"inventoryId":2},{"id":"60bf5482eeb8cc2b9a7d24aa","name":"Action classic","price":167,"quantity":2,"inventoryId":2},{"id":"60bf5482eeb8cc2b9a7d24aa","name":"Action classic","price":167,"quantity":2,"inventoryId":2},{"id":"60bf5482eeb8cc2b9a7d24ab","name":"Addidas DX","price":180,"quantity":2,"inventoryId":3}]}<br/>
<br/>
<br/>
Post to order(order confirm)<br/>
http://104.215.148.134:9000/core/order/<br/>
data<br/>
case 1 : Out of stock<br/>
{"userId":"admin","lineItems":[{"inventoryId":3,"price":180,"quantity":20,"productId":"60bf5482eeb8cc2b9a7d24ab","productName":"Addidas DX"},{"inventoryId":3,"price":180,"quantity":2,"productId":"60bf5482eeb8cc2b9a7d24ab","productName":"Addidas DX"},{"inventoryId":3,"price":180,"quantity":2,"productId":"60bf5482eeb8cc2b9a7d24ab","productName":"Addidas DX"}]}<br/>
<br/>
case 2: success<br/>
{"userId":"admin","lineItems":[{"inventoryId":25,"price":120,"quantity":2,"productId":"60bf5482eeb8cc2b9a7d24c2","productName":"Don"}]}<br/>
------------------------------------------------------------------------Management----------------------------------------------------------------<br/>
<br/>
rabbit MQ management(manage messenger) login username : guest and pass : guest<br/>
http://104.215.148.134:15672<br/>
<br/>
eureka<br/>
<br/>
http://104.215.148.134:8761/<br/>
<br/>
gateway:<br/>
    http://104.215.148.134:9000/<br/>
<br/>
</div>

