package com.example.demo.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

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
    void getAllDataWhenCredentialsValidReturnsRedirect() {
        AdminLogin login = new AdminLogin();
        login.setEmail("admin@example.com");
        login.setPassword("secret");

        doReturn(true).when(adminServices).validateAdminCredentials("admin@example.com", "secret");

        String view = controller.getAllData(login, model);

        assertEquals("redirect:/admin/services", view);
        verify(adminServices).validateAdminCredentials("admin@example.com", "secret");
    }

    @Test
    void getAllDataWhenCredentialsInvalidAddsErrorAndReturnsLogin() {
        AdminLogin login = new AdminLogin();
        login.setEmail("admin@example.com");
        login.setPassword("wrong");

        doReturn(false).when(adminServices).validateAdminCredentials("admin@example.com", "wrong");

        String view = controller.getAllData(login, model);

        assertEquals("Login", view);
        verify(model).addAttribute("error", "Invalid email or password");
        verify(adminServices).validateAdminCredentials("admin@example.com", "wrong");
    }

    @Test
    void userLoginWhenValidAddsOrdersAndName() {
        UserLogin login = new UserLogin();
        login.setUserEmail("user@example.com");
        login.setUserPassword("pwd");

        doReturn(true).when(services).validateLoginCredentials("user@example.com", "pwd");
        doReturn(user).when(services).getUserByEmail("user@example.com");
        doReturn(Collections.emptyList()).when(orderServices).getOrdersForUser(user);

        String view = controller.userLogin(login, model);

        assertEquals("BuyProduct", view);
        verify(model).addAttribute("orders", Collections.emptyList());
        verify(model).addAttribute("name", "John");
        verify(services).validateLoginCredentials("user@example.com", "pwd");
        verify(services).getUserByEmail("user@example.com");
        verify(orderServices).getOrdersForUser(user);
    }

    @Test
    void userLoginWhenInvalidAddsErrorAndReturnsLogin() {
        UserLogin login = new UserLogin();
        login.setUserEmail("user@example.com");
        login.setUserPassword("bad");

        doReturn(false).when(services).validateLoginCredentials("user@example.com", "bad");

        String view = controller.userLogin(login, model);

        assertEquals("Login", view);
        verify(model).addAttribute("error2", "Invalid email or password");
        verify(services).validateLoginCredentials("user@example.com", "bad");
    }

    @Test
    void seachHandlerWhenProductMissingAddsMessageAndOrders() {
        doReturn(user).when(services).getUserByEmail(nullable(String.class));
        doReturn(Collections.emptyList()).when(orderServices).getOrdersForUser(nullable(User.class));
        doReturn(null).when(productServices).getProductByName("Laptop");

        String view = controller.seachHandler("Laptop", model);

        assertEquals("BuyProduct", view);
        verify(model).addAttribute("message", "SORRY...!  Product Unavailable");
        verify(model).addAttribute("product", null);
        verify(model).addAttribute("orders", Collections.emptyList());
        verify(orderServices).getOrdersForUser(nullable(User.class));
    }

    @Test
    void seachHandlerWhenProductExistsAddsProductAndOrders() {
        Product product = new Product();
        doReturn(user).when(services).getUserByEmail(nullable(String.class));
        doReturn(Collections.emptyList()).when(orderServices).getOrdersForUser(nullable(User.class));
        doReturn(product).when(productServices).getProductByName("Phone");

        String view = controller.seachHandler("Phone", model);

        assertEquals("BuyProduct", view);
        verify(model).addAttribute("orders", Collections.emptyList());
        verify(model).addAttribute("product", product);
        verify(orderServices).getOrdersForUser(nullable(User.class));
    }

    @Test
    void returnBackAddsAllCollectionsAndReturnsAdminPage() {
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
        verify(model).addAttribute("users", users);
        verify(model).addAttribute("admins", admins);
        verify(model).addAttribute("products", products);
        verify(model).addAttribute("orders", orders);
    }

    @Test
    void addAdminPageReturnsView() {
        assertEquals("Add_Admin", controller.addAdminPage());
    }

    @Test
    void addAdminDelegatesAndRedirects() {
        Admin admin = new Admin();

        String view = controller.addAdmin(admin);

        assertEquals("redirect:/admin/services", view);
        verify(adminServices).addAdmin(admin);
    }

    @Test
    void updateAddsAdminAndReturnsUpdateView() {
        Admin admin = new Admin();
        doReturn(admin).when(adminServices).getAdmin(5);

        String view = controller.update(5, model);

        assertEquals("Update_Admin", view);
        verify(model).addAttribute("admin", admin);
    }

    @Test
    void updateAdminDelegatesAndRedirects() {
        Admin admin = new Admin();

        String view = controller.updateAdmin(admin, 7);

        assertEquals("redirect:/admin/services", view);
        verify(adminServices).update(admin, 7);
    }

    @Test
    void deleteAdminDelegatesAndRedirects() {
        String view = controller.deleteAdmin(9);

        assertEquals("redirect:/admin/services", view);
        verify(adminServices).delete(9);
    }

    @Test
    void addProductReturnsView() {
        assertEquals("Add_Product", controller.addProduct());
    }

    @Test
    void updateProductAddsProductAndReturnsView() {
        Product product = new Product();
        doReturn(product).when(productServices).getProduct(11);

        String view = controller.updateProduct(11, model);

        assertEquals("Update_Product", view);
        verify(model).addAttribute("product", product);
    }

    @Test
    void addUserReturnsView() {
        assertEquals("Add_User", controller.addUser());
    }

    @Test
    void updateUserPageAddsUserAndReturnsView() {
        User existing = new User();
        doReturn(existing).when(services).getUser(3);

        String view = controller.updateUserPage(3, model);

        assertEquals("Update_User", view);
        verify(model).addAttribute("user", existing);
    }

    @Test
    void orderHandlerCalculatesTotalSavesOrderAndReturnsSuccess() {
        Orders order = new Orders();
        order.setoPrice(10.0);
        order.setoQuantity(3);

        String view = controller.orderHandler(order, model);

        assertEquals("Order_success", view);
        verify(orderServices).saveOrder(order);
        verify(model).addAttribute(eq("amount"), eq(30.0));
    }

    @Test
    void backAddsOrdersAndReturnsBuyProduct() {
        doReturn(user).when(services).getUserByEmail(nullable(String.class));
        doReturn(Collections.emptyList()).when(orderServices).getOrdersForUser(nullable(User.class));
        doReturn(true).when(services).validateLoginCredentials("user@example.com", "pwd");
        doReturn(user).when(services).getUserByEmail("user@example.com");

        UserLogin login = new UserLogin();
        login.setUserEmail("user@example.com");
        login.setUserPassword("pwd");
        controller.userLogin(login, model);

        String view = controller.back(model);

        assertEquals("BuyProduct", view);
        verify(model, times(2)).addAttribute("orders", Collections.emptyList());
    }
}