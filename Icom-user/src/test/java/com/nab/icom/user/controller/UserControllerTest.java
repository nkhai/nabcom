package com.nab.icom.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nab.icom.user.controller.UserController;
import com.nab.icom.user.dto.AddressDTO;
import com.nab.icom.user.dto.CustomerDTO;
import com.nab.icom.user.model.User;
import com.nab.icom.user.repository.OnlineUserRepository;
import com.nab.icom.user.repository.UserRepository;
import com.nab.icom.user.service.CustomerService;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
// @SpringBootTest(classes = ProductRestController.class)
// @WebAppConfiguration
public class UserControllerTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private OnlineUserRepository onlineUserRepository;

    @Autowired
	private CustomerService customerService;
//     @Autowired
//     WebApplicationContext wac;
    
    @Autowired
    private MockMvc mvc;

//     @Autowired
//     private WebApplicationContext webApplicationContext;

    // This object will be magically initialized by the initFields method below.
    private JacksonTester<CustomerDTO> json;

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

    // @Test
    // public void postCustomer() throws Exception {
    //     AddressDTO temp = new AddressDTO("123", "123", "123", "123", "123", "123");
    //     CustomerDTO customer = new CustomerDTO("khai", "vo", "Nguyen", "n.khai1989", "0395008100",temp ,temp, "123456");
    //     // given(customerService.saveCustomer(customer)).
    //     // willReturn(List.of(new Product("123", "123", "123", "123", "123", "123", "123", 123.0, "123")));        
        
    //     MockHttpServletResponse response = mvc.perform(
    //             post("/customer")
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content(json.write(customer).getJson()))
    //             .andReturn().getResponse();

    //     // then
    //     assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    // }


    
    @Test
    public void getCustomer() throws Exception {      
        // given(customerService.saveCustomer(customer)).
        // willReturn(List.of(new Product("123", "123", "123", "123", "123", "123", "123", 123.0, "123")));        
        
        MockHttpServletResponse response = mvc.perform(
                get("/customer")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    }
}
