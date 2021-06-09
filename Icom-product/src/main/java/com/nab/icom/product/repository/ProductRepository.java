package com.nab.icom.product.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.nab.icom.product.model.Product;


@RepositoryRestResource(collectionResourceRel = "productdata", path = "productdata")
public interface ProductRepository extends MongoRepository<Product, String>  {

	public List<Product> findByProductCategoryName(@Param("productCategory") String  productCatagoryName);
	public List<Product> findByCode(@Param("code") String  code);

	public List<Product> findByColor(@Param("color") String  color);
	
	@Query("{ 'name':{$regex:?0,$options:'i'}}")
	public List<Product> findByNameRegex(@Param("name") String name);

	@Query("{'price': {$gte: ?0, $lte:?1 }}")
    List<Product> findProductBetweenPrice(Float min, Float max);	

}
