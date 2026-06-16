package com.example.demo.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import com.example.demo.count.Logic;
import com.example.demo.entities.Admin;
import com.example.demo.entities.Orders;
import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import com.example.demo.loginCredentials.AdminLogin;
import com.example.demo.loginCredentials.UserLogin;
import com.example.demo.services.AdminServices;
import com.example.demo.services.OrderServices;
import com.example.demo.services.ProductServices;
import com.example.demo.services.UserServices;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Mock
    private UserServices services;

    @Mock
    private AdminServices adminServices;

    @Mock
    private ProductServices productServices;

    @Mock
    private OrderServices orderServices;

    @Mock
    private Model model;

    @InjectMocks
    private AdminController controller;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUname("John");
    }

    @Test
    void getAllData_whenCredentialsValid_redirectsToAdminServices() {
        AdminLogin login = new AdminLogin();
        login.setEmail("admin@example.com");
        login.setPassword("secret");

        when(adminServices.validateAdminCredentials("admin@example.com", "secret")).thenReturn(true);

        String view = controller.getAllData(login, model);

        assertEquals("redirect:/admin/services", view);
        verify(adminServices).validateAdminCredentials("admin@example.com", "secret");
    }

    @Test
    void getAllData_whenCredentialsInvalid_addsErrorAndReturnsLogin() {
        AdminLogin login = new AdminLogin();
        login.setEmail("admin@example.com");
        login.setPassword("wrong");

        when(adminServices.validateAdminCredentials("admin@example.com", "wrong")).thenReturn(false);

        String view = controller.getAllData(login, model);

        assertEquals("Login", view);
        verify(model).addAttribute(eq("error"), eq("Invalid email or password"));
    }

    @Test
    void userLogin_whenCredentialsValid_returnsBuyProductAndAddsOrdersAndName() {
        UserLogin login = new UserLogin();
        login.setUserEmail("user@example.com");
        login.setUserPassword("pwd");

        when(services.validateLoginCredentials("user@example.com", "pwd")).thenReturn(true);
        doReturn(user).when(services).getUserByEmail("user@example.com");
        List<Orders> orders = Collections.singletonList(new Orders());
        doReturn(orders).when(orderServices).getOrdersForUser(nullable(User.class));

        String view = controller.userLogin(login, model);

        assertEquals("BuyProduct", view);
        verify(model).addAttribute(eq("orders"), eq(orders));
        verify(model).addAttribute(eq("name"), eq("John"));
    }

    @Test
    void userLogin_whenCredentialsInvalid_returnsLoginAndAddsError() {
        UserLogin login = new UserLogin();
        login.setUserEmail("user@example.com");
        login.setUserPassword("bad");

        when(services.validateLoginCredentials("user@example.com", "bad")).thenReturn(false);

        String view = controller.userLogin(login, model);

        assertEquals("Login", view);
        verify(model).addAttribute(eq("error2"), eq("Invalid email or password"));
    }

    @Test
    void seachHandler_whenProductMissing_returnsBuyProductAndAddsMessage() {
        doReturn(null).when(productServices).getProductByName("missing");
        doReturn(Collections.emptyList()).when(orderServices).getOrdersForUser(nullable(User.class));

        String view = controller.seachHandler("missing", model);

        assertEquals("BuyProduct", view);
        verify(model).addAttribute(eq("message"), eq("SORRY...!  Product Unavailable"));
        verify(model).addAttribute(eq("product"), eq(null));
    }

    @Test
    void seachHandler_whenProductExists_returnsBuyProductAndAddsProductAndOrders() {
        Product product = new Product();
        doReturn(product).when(productServices).getProductByName("phone");
        List<Orders> orders = Collections.singletonList(new Orders());
        doReturn(orders).when(orderServices).getOrdersForUser(nullable(User.class));

        String view = controller.seachHandler("phone", model);

        assertEquals("BuyProduct", view);
        verify(model).addAttribute(eq("orders"), eq(orders));
        verify(model).addAttribute(eq("product"), eq(product));
    }

    @Test
    void returnBack_addsAllCollectionsAndReturnsAdminPage() {
        List<User> users = Collections.singletonList(new User());
        List<Admin> admins = Collections.singletonList(new Admin());
        List<Product> products = Collections.singletonList(new Product());
        List<Orders> orders = Collections.singletonList(new Orders());

        doReturn(users).when(services).getAllUser();
        doReturn(admins).when(adminServices).getAll();
        doReturn(products).when(productServices).getAllProducts();
        doReturn(orders).when(orderServices).getOrders();

        String view = controller.returnBack(model);

        assertEquals("Admin_Page", view);
        verify(model).addAttribute(eq("users"), eq(users));
        verify(model).addAttribute(eq("admins"), eq(admins));
        verify(model).addAttribute(eq("products"), eq(products));
        verify(model).addAttribute(eq("orders"), eq(orders));
    }

    @Test
    void addAdminPage_returnsAddAdminView() {
        assertEquals("Add_Admin", controller.addAdminPage());
    }

    @Test
    void addAdmin_delegatesAndRedirects() {
        Admin admin = new Admin();

        String view = controller.addAdmin(admin);

        assertEquals("redirect:/admin/services", view);
        verify(adminServices).addAdmin(admin);
    }

    @Test
    void update_addsAdminAndReturnsUpdateView() {
        Admin admin = new Admin();
        doReturn(admin).when(adminServices).getAdmin(5);

        String view = controller.update(5, model);

        assertEquals("Update_Admin", view);
        verify(model).addAttribute(eq("admin"), eq(admin));
    }

    @Test
    void updateAdmin_delegatesAndRedirects() {
        Admin admin = new Admin();

        String view = controller.updateAdmin(admin, 7);

        assertEquals("redirect:/admin/services", view);
        verify(adminServices).update(admin, 7);
    }

    @Test
    void deleteAdmin_delegatesAndRedirects() {
        String view = controller.deleteAdmin(9);

        assertEquals("redirect:/admin/services", view);
        verify(adminServices).delete(9);
    }

    @Test
    void addProduct_returnsAddProductView() {
        assertEquals("Add_Product", controller.addProduct());
    }

    @Test
    void updateProduct_addsProductAndReturnsUpdateView() {
        Product product = new Product();
        doReturn(product).when(productServices).getProduct(11);

        String view = controller.updateProduct(11, model);

        assertEquals("Update_Product", view);
        verify(model).addAttribute(eq("product"), eq(product));
    }

    @Test
    void addUser_returnsAddUserView() {
        assertEquals("Add_User", controller.addUser());
    }

    @Test
    void updateUserPage_addsUserAndReturnsUpdateView() {
        User foundUser = new User();
        doReturn(foundUser).when(services).getUser(13);

        String view = controller.updateUserPage(13, model);

        assertEquals("Update_User", view);
        verify(model).addAttribute(eq("user"), eq(foundUser));
    }

    @Test
    void orderHandler_calculatesTotalSavesOrderAndReturnsSuccess() {
        Orders order = new Orders();
        order.setoPrice(10.0);
        order.setoQuantity(3);

        double expectedTotal = Logic.countTotal(10.0, 3);
        String view = controller.orderHandler(order, model);

        assertEquals("Order_success", view);
        assertEquals(expectedTotal, order.getTotalAmmout());
        verify(orderServices).saveOrder(order);
        verify(model).addAttribute(eq("amount"), eq(expectedTotal));
        assertNotNull(order.getOrderDate());
    }

    @Test
    void back_addsOrdersAndReturnsBuyProduct() {
        List<Orders> orders = Collections.singletonList(new Orders());
        doReturn(orders).when(orderServices).getOrdersForUser(nullable(User.class));

        String view = controller.back(model);

        assertEquals("BuyProduct", view);
        verify(model).addAttribute(eq("orders"), eq(orders));
    }
}