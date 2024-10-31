package com.example.noithatshop.client.Controller;

import com.example.noithatshop.Model.Category;
import com.example.noithatshop.Model.Product;
import com.example.noithatshop.Model.Slide;
import com.example.noithatshop.admin.ControllerAdmin.BaseController;
import com.example.noithatshop.Repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController extends BaseController {

    @Autowired private ProductRepository productRepo;

    @GetMapping({"/home", "/home/search"})
    public String homeShop(@RequestParam(value = "search", required = false) String query,
                           Model model,
                           HttpSession session) {
        List<Category> listCategory = categorySV.getAllCategory();
        List<Product> listProduct;
        List<Slide> listSlide = slideSV.getAllSLide();

        // Nếu có từ khóa tìm kiếm, tìm kiếm sản phẩm theo tên
        if (query != null && !query.trim().isEmpty()) {
            listProduct = productSV.searchProductsByName(query);
            if (listProduct.isEmpty()) {
                model.addAttribute("mess", "Không có sản phẩm nào được tìm thấy."); // Message for no products found
            }
        } else {
            listProduct = productRepo.findAll();
        }

        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap<>();
            session.setAttribute("cart", cart);
        }

        // Lấy danh sách sản phẩm từ giỏ hàng
        List<Product> productList = productSV.getProductsByIds(cart.keySet());

        // Tính tổng tiền
        double totalAmount = 0.0;
        for (Product product : productList) {
            totalAmount += product.getPrice() * cart.get(product.getId());
        }

        String username = (String) session.getAttribute("username");
        model.addAttribute("username", username);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("category", listCategory);
        model.addAttribute("quantities", cart);
        model.addAttribute("product", listProduct);
        model.addAttribute("query", query);
        model.addAttribute("slide", listSlide);

        model.addAttribute("contentShop", "client/content/content");

        return "client/layout";
    }

    //xem chi tiết sản phẩm
    @GetMapping("/home/product/{id}")
    public String productDetails(@PathVariable Long id, Model model){
        List<Category> listCategory = categorySV.getAllCategory();
        var  productID = productSV.getProductById(id);

        if(productID.isPresent()){
            Product product = productID.get();
            model.addAttribute("productCategories", listCategory);
            model.addAttribute("product", product);
            model.addAttribute("contentShop", "client/content/productDetails");
            return "client/layout";
        }
        return null;
    }


}
