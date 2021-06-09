![alt text](https://github.com/vkn1hc/nabcom/blob/master/Icom-xtra-setup/image.jpg?raw=true)


______________________________enviroment requirement________________________________________

* Unbutu
* Docker
* Docker-compose
* maven
* open jdk 11
* Postman
____________________________________________________________________Prepare_________________________________________________________
before you start. You need modify eureka serviceUrl on below file(It is your IP adress)
.\Icom-cart\src\main\resources\application.yml
.\Icom-core\src\main\resources\application.yml
.\Icom-gateway\src\main\resources\application.yml
.\Icom-history\src\main\resources\application.yml
.\Icom-product\src\main\resources\application.yml
.\Icom-user\src\main\resources\application.yml

Change 
    eureka:
        client:
            registerWithEureka: true
            fetchRegistry: true
            serviceUrl:
            defaultZone : http://104.215.148.134:8761/eureka/
To
    eureka:
        client:
            registerWithEureka: true
            fetchRegistry: true
            serviceUrl:
            defaultZone : http://Your_IP:8761/eureka/

_______________Running___________________________________________________
--call below command

mvn clean install

----add permisison for sh file to build
chmod +x /path/to/build.sh

./build.sh

docker-compose up

Note : you need check eureka server on http://Your_IP:8761 and rabbit MQ http://Your_IP:15672 . When all service has register, you can init data as below and test it
______________________________________________________________________Init Database________________________________________________
-- import data from mongo to mongodb
-- go to Icom-xtra-setup folder and call below command
--change below IP to your server IP

mongoimport --host "104.215.148.134" --port "27017" --db "icom" --collection "inventory" --file "inventory.json" --jsonArray
mongoimport --host "104.215.148.134" --port "27017" --db "icom" --collection "product" --file "products.json" --jsonArray
mongoimport --host "104.215.148.134" --port "27017" --db "icom" --collection "productCategory" --file "productCategory.json" --jsonArray

-- import mySQL:
- access my sql via command

mysql -u root -p123456 -h (your Ip) -P 3306 (example : mysql -u root -p123456 -h 104.215.148.134 -P 3306)

>>>>>>>>>>>>>>call below command to create database(It's may fail because code has create some table but it doesn't have full data)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

use icom;
-- (database has create when you run code)-------------------

-------Create table---------

CREATE TABLE `address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `apartment` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `pin` varchar(255) DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE `customer_order` (
  `id` bigint(20) NOT NULL,
  `last_event_sequence_number` bigint(20) DEFAULT NULL,
  `version` bigint(20) DEFAULT NULL,
  `order_date` datetime DEFAULT NULL,
  `order_status` varchar(255) DEFAULT NULL,
  `total` double DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `inventory` (
  `id` bigint(20) NOT NULL,
  `last_event_sequence_number` bigint(20) DEFAULT NULL,
  `version` bigint(20) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `sku` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `user_credential` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `active` tinyint(1) NOT NULL,
  `password` varchar(255) NOT NULL,
  `user_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_14ncv1m0gqncrbiagrs4uaqo8` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE `user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_apcc8lxk2xnug8377fatvbn04` (`user_id`),
  CONSTRAINT `FK_apcc8lxk2xnug8377fatvbn04` FOREIGN KEY (`user_id`) REFERENCES `user_credential` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

---------Create basic data for inventory and user_role---------------------

insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(1,0,0,10,'LEE'); 
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(2,0,0,10,'ACTION'); 
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(3,0,0,10,'ADDIDAS');      
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(4,0,0,10,'NIKE');  
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(5,0,0,10,'LEE_1');
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(6,0,0,10,'ACTION_1');  
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(7,0,0,10,'LAXME'); 
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(8,0,0,10,'LAXME13'); 
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(9,0,0,10,'ADDIDAS_W');      
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(10,0,0,10,'WEDGES');  
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(11,0,0,10,'LEE_2');
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(12,0,0,10,'LEE_3');  
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(13,0,0,10,'RAYBAN1'); 
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(14,0,0,10,'RAYBAN2'); 
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(15,0,0,10,'RAYBAN3');      
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(16,0,0,10,'FASTTRACK1');  
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(17,0,0,10,'FASTTRACK2');
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(18,0,0,10,'FASTTRACK3');  
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(19,0,0,10,'ARROW1'); 
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(20,0,0,10,'ARROW2'); 
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(21,0,0,10,'ARROW3');      
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(22,0,0,10,'POLO1');  
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(23,0,0,10,'POLO2');
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(24,0,0,10,'POLO3');  
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(25,0,0,10,'DON1'); 
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(26,0,0,10,'DON2'); 
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(27,0,0,10,'DON3');      
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(28,0,0,10,'DON4'); 
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(29,0,0,10,'X-COTTEN1');
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(30,0,0,10,'X-COTTEN2');  
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(31,0,0,10,'X-COTTEN3');  
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(32,0,0,10,'X-COTTEN4');  
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(33,0,0,10,'X-COTTEN5');  
insert into inventory(id,last_event_sequence_number, version,quantity, sku) values(34,0,0,10,'X-COTTEN6');  
commit;
insert into user_credential (id,active,password,user_id) values (1,1,'admin','admin');
insert into user_role (id,role,user_id) values (1,'ROLE_ADMIN',1);
insert into user_role (id,role,user_id) values (2,'CUSTOMER_READ',1);
insert into user_role (id,role,user_id) values (3,'PRODUCT_WRITE',1);
insert into user_role (id,role,user_id) values (4,'ORDER_READ',1);
insert into user_role (id,role,user_id) values (5,'PRODUCT_WRITE',1);
insert into user_role (id,role,user_id) values (6,'ORDER_WRITE',1);
insert into user_info (id,email,first_name,last_name,phone,user_id) values(1,'admin@admin','admin','admin',903766787,'admin');
commit;

____________________________________________________________Testing____________________________________________________________________________
Basic query of product(Below URL is azure cloud IP, you can use it but it can't remove any time )

http://104.215.148.134:9000/product/categories/

Search categories by name
http://104.215.148.134:9000/product/productsByCategory?category=women_footwear

Search categories by price
http://104.215.148.134:9000/product/productsByPrice?minprice=10&maxprice=20

Search categories by brand
http://104.215.148.134:9000/product/listofbrand
http://104.215.148.134:9000/product/productsByBrand?brand=NIKE

Search categories by Color
http://104.215.148.134:9000/product/productsByColor?color=red

Search categories by Name
http://104.215.148.134:9000/product/productsByName?name=Laxme 12


Post to add to cart:
http://104.215.148.134:9000/cart/customerCart/
data
{"userId":"5aa02b23-6c76-6fc9-2309-8991874c01a6","activeSince":null,"coupen":null,"lineItems":[{"id":"60bf5482eeb8cc2b9a7d24aa","name":"Action classic","price":167,"quantity":2,"inventoryId":2},{"id":"60bf5482eeb8cc2b9a7d24aa","name":"Action classic","price":167,"quantity":2,"inventoryId":2},{"id":"60bf5482eeb8cc2b9a7d24aa","name":"Action classic","price":167,"quantity":2,"inventoryId":2},{"id":"60bf5482eeb8cc2b9a7d24ab","name":"Addidas DX","price":180,"quantity":2,"inventoryId":3}]}


Post to order(order confirm)
http://104.215.148.134:9000/core/order/
data
case 1 : Out of stock
{"userId":"admin","lineItems":[{"inventoryId":3,"price":180,"quantity":20,"productId":"60bf5482eeb8cc2b9a7d24ab","productName":"Addidas DX"},{"inventoryId":3,"price":180,"quantity":2,"productId":"60bf5482eeb8cc2b9a7d24ab","productName":"Addidas DX"},{"inventoryId":3,"price":180,"quantity":2,"productId":"60bf5482eeb8cc2b9a7d24ab","productName":"Addidas DX"}]}

case 2: success
{"userId":"admin","lineItems":[{"inventoryId":25,"price":120,"quantity":2,"productId":"60bf5482eeb8cc2b9a7d24c2","productName":"Don"}]}
_______________________________________________________________________Management____________________________________________________________

rabbit MQ management(manage messenger) login username : guest and pass : guest
http://104.215.148.134:15761

eureka

http://104.215.148.134:8761/

gateway:
    http://104.215.148.134:9000/


http://104.215.148.134:9000/product/categories/

Search categories by name
http://104.215.148.134:9000/product/productsByCategory?category=women_footwear

Search categories by price
http://104.215.148.134:9000/product/productsByPrice?minprice=10&maxprice=20

Search categories by brand
http://104.215.148.134:9000/product/listofbrand
http://104.215.148.134:9000/product/productsByBrand?brand=NIKE

Search categories by Color
http://104.215.148.134:9000/product/productsByColor?color=red

Search categories by Name
http://104.215.148.134:9000/product/productsByName?name=Laxme 12

see product detail
http://104.215.148.134:9000/product/products/60bf5482eeb8cc2b9a7d24aa
