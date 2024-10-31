package com.example.noithatshop.client.Controller;

import com.example.noithatshop.Model.Order;
import com.example.noithatshop.Model.OrderItem;
import com.example.noithatshop.Model.Product;
import com.example.noithatshop.Service.OrderService;
import com.example.noithatshop.Service.ProductService; // Dịch vụ sản phẩm
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@RequestMapping("/home")
@Controller
public class CheckoutController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @PostMapping("/checkout")
    public String checkout(@RequestParam String name, @RequestParam String email,
                           @RequestParam String phone, @RequestParam String address,
                           @RequestParam String note, HttpSession session, Model model) {
        // Lấy danh sách sản phẩm từ giỏ hàng
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
        BigDecimal totalAmount = BigDecimal.ZERO;

        Order order = new Order();
        order.setCustomerName(name);
        order.setEmail(email);
        order.setPhone(phone);
        order.setAddress(address);
        order.setNote(note);

        List<OrderItem> orderItems = new ArrayList<>();

        order.setStatus("1");

        for (Map.Entry<Long, Integer> entry : cart.entrySet()) {
            Long productId = entry.getKey(); //trả về id snar phẩm
            int quantity = entry.getValue();// trả về số lượng

            // Lấy sản phẩm từ cơ sở dữ liệu
            Optional<Product> optionalProduct = productService.getProductById(productId);

            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();  // Lấy đối tượng Product từ Optional

                // Tạo OrderItem và thiết lập thông tin
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);  // Liên kết OrderItem với Order
                orderItem.setProduct(product); // Truyền Product đã lấy được vào OrderItem
                orderItem.setQuantity(quantity);
                orderItem.setPrice(BigDecimal.valueOf(product.getPrice()));

                // Tính tổng số tiền
                totalAmount = totalAmount.add(BigDecimal.valueOf(product.getPrice()).multiply(new BigDecimal(quantity)));

                orderItems.add(orderItem);
            } else {
                System.out.println("ko tìm thấy sản phẩm");
                return "redirect:/error";
            }
        }

        // Thiết lập tổng tiền cho đơn hàng
        order.setTotalAmount(totalAmount);
        order.setItems(orderItems);

        // Lưu đơn hàng vào DB
        orderService.saveOrder(order);

        // Xóa giỏ hàng sau khi hoàn tất đơn hàng
        session.removeAttribute("cart");
        model.addAttribute("customerName", name);
        model.addAttribute("customerEmail", email);
        model.addAttribute("customerPhone", phone);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("contentShop", "client/content/thankyou");

        return "client/layout";
    }





}
