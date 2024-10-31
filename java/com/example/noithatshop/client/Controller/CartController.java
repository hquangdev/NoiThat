package com.example.noithatshop.client.Controller;

import com.example.noithatshop.Model.Product;
import com.example.noithatshop.admin.ControllerAdmin.BaseController;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import java.math.BigDecimal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@RequestMapping("/home")
@Controller
public class CartController extends BaseController {

    // Hiển thị giỏ hàng
    @GetMapping("/cart")
    public String showCart(HttpSession session, Model model) {
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap<>();
        }
        // Lấy danh sách sản phẩm từ cơ sở dữ liệu hoặc dịch vụ
        List<Product> productList = productSV.getProductsByIds(cart.keySet());

        // Tính tổng tiền
        double totalAmount = 0.0;
        for (Product product : productList) {
            totalAmount += product.getPrice() * cart.get(product.getId());
        }

        String username = (String) session.getAttribute("username");
        String email = (String) session.getAttribute("email");

        model.addAttribute("email", email);
        model.addAttribute("username", username);
        model.addAttribute("cart", productList);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("quantities", cart);
        model.addAttribute("contentShop", "client/content/cart");
        return "client/layout";
    }


    // Thêm sản phẩm vào giỏ hàng
    @PostMapping("/addToCart/{id}")
    public String addToCart(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        // Lấy giỏ hàng từ session
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap<>(); // Tạo mới nếu không có giỏ hàng
        }

        // Cập nhật số lượng sản phẩm trong giỏ hàng
        cart.put(id, cart.getOrDefault(id, 0) + 1);
        session.setAttribute("cart", cart);

        redirectAttributes.addFlashAttribute("mess", "Thêm vào giỏ hàng thành công");
        return "redirect:/home";
    }

//Cap nhat gio hang
    @PostMapping("/updateCart")
    public String updateCart(@RequestParam("productId") Long productId, @RequestParam("quantity") Integer quantity, HttpSession session) {
        // Lấy giỏ hàng từ session
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");

        if (cart != null) {
            // Cập nhật số lượng sản phẩm trong giỏ hàng
            if (cart.containsKey(productId)) {
                cart.put(productId, quantity);
            }
        }

        session.setAttribute("cart", cart);
        return "redirect:/home/cart";
    }


    //xóa giỏ hàng
    @PostMapping("/delete")
    public String deleteProduct(@RequestParam("productId") Long productId,
                                HttpSession session,RedirectAttributes redirectAttributes) {
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
        if (cart != null) {
            cart.remove(productId);
        }
        redirectAttributes.addFlashAttribute("mess","Xóa sản phẩm thành công");
        session.setAttribute("cart", cart);
        return "redirect:/home/cart";
    }




}
