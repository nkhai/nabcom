package com.nab.icom.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nab.icom.product.inventory.repository.InventoryRepository;
import com.nab.icom.product.model.Product;
import com.nab.icom.product.repository.ProductRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductRestController.class)
// @SpringBootTest(classes = ProductRestController.class)
// @WebAppConfiguration
public class ProductRestControllerTest {

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private InventoryRepository inventoryRepository;

//     @Autowired
//     WebApplicationContext wac;
    
    @Autowired
    private MockMvc mvc;

//     @Autowired
//     private WebApplicationContext webApplicationContext;

    // This object will be magically initialized by the initFields method below.
    private JacksonTester<Product> json;

    @Before
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        // Product testProduct = new Product("123", "123", "123", "123", "123", "123", "123", 123.0, "123");
        // productRepository.save(testProduct);
        // List<Product> data = productRepository.findAll();
        // data.stream().forEach(i->System.out.println(i));
        // mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        // MockitoAnnotations.initMocks(this);
        // this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).dispatchOptions(true).build();
    }

    @Test
    public void getProductTest() throws Exception {
        
        given(productRepository.findAll()).
        willReturn(List.of(new Product("123", "123", "123", "123", "123", "123", "123", 123.0, "123")));
        MockHttpServletResponse response = mvc.perform(
                get("/products")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    public void getAllProductsByCategory() throws Exception {

        String category = "Nike";
        given(productRepository.findByProductCategoryName(category)).
        willReturn(List.of(new Product("123", "123", "Nike", "123", "123", "123", "123", 123.0, "123")));
        // when
        MockHttpServletResponse response = mvc.perform(
                get("/productsByCategory?category=Nike")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());  
    }

    @Test
    public void getProduct() throws Exception {
        
        given(productRepository.findAll()).
        willReturn(List.of(new Product("123", "123", "123", "123", "123", "123", "123", 123.0, "123")));        
        // when
        MockHttpServletResponse response = mvc.perform(
                get("/products")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());  
    }
    @Test
    public void deleteProduct() throws Exception {

        String productID = "123";
        given(productRepository.findOne(productID)).
        willReturn(new Product("123", "123", "123", "123", "123", "123", "123", 123.0, "123"));
        
        // when
        MockHttpServletResponse response = mvc.perform(
                delete("/products/123")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());  
    }

    @Test
    public void deleteAllProducts() throws Exception {

        String productID = "123";
        given(productRepository.count()).
        willReturn(10L);
        
        // when
        MockHttpServletResponse response = mvc.perform(
                delete("/products")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());  
    }

    @Test
    public void getAllProductsByPrice() throws Exception {


        given(productRepository.findProductBetweenPrice(10.0f,20.0f)).
        willReturn(List.of(new Product("123", "123", "123", "123", "123", "123", "123", 12.0, "123")));
        
        // when
        MockHttpServletResponse response = mvc.perform(
                get("/productsByPrice?minprice=10&maxprice=20")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());  
    }

    @Test
    public void getAllProductsByBrand() throws Exception {

        String brand = "Nike";
        given(productRepository.findByCode(brand)).
        willReturn(List.of(new Product("123", "123", "Nike", "123", "123", "123", "123", 12.0, "123")));
        
        // when
        MockHttpServletResponse response = mvc.perform(
                get("/productsByBrand?brand=Nike")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());  
    }


    @Test
    public void getAllBrand() throws Exception {

        String brand = "Nike";
        given(productRepository.findAll()).
        willReturn(List.of(new Product("123", "123", "Nike", "123", "123", "123", "123", 12.0, "123")));
        
        // when
        MockHttpServletResponse response = mvc.perform(
                get("/listofbrand")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());  
    }

    @Test
    public void getAllProductsByColor() throws Exception {

        String color = "red";
        given(productRepository.findByColor(color)).
        willReturn(List.of(new Product("123", "123", "Nike", "123", "123", "123", "red", 12.0, "123")));
        
        // when
        MockHttpServletResponse response = mvc.perform(
                get("/productsByColor?color=red")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());  
    }

    @Test
    public void productsByName() throws Exception {

        String name = "shoes";
        given(productRepository.findByNameRegex(name)).
        willReturn(List.of(new Product("123", "shoes", "Nike", "123", "123", "123", "red", 12.0, "123")));
        
        // when
        MockHttpServletResponse response = mvc.perform(
                get("/productsByName?name=shoes")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());  
    }
}
