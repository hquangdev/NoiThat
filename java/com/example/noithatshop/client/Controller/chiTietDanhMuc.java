package com.example.noithatshop.client.Controller;

import com.example.noithatshop.Model.Product;
import com.example.noithatshop.admin.ControllerAdmin.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/home")
@Controller
public class chiTietDanhMuc extends BaseController {

    @GetMapping("/category/{id}")
    public String viewProductsByCategory(@PathVariable Long id, Model model) {
        // Lấy category theo ID
        var category = categorySV.getCategoryID(id);

        if (category.isPresent()) {
            // Lấy danh sách sản phẩm thuộc category này
            List<Product> products = productSV.getProductsByCategoryId(id); // Lấy danh sách sản phẩm

            // Thêm dữ liệu vào model
            model.addAttribute("category", category.get());
            model.addAttribute("products", products);
            model.addAttribute("contentShop", "client/content/category");
            return "client/layout";
        }

        return "client/error/404";
    }


}
