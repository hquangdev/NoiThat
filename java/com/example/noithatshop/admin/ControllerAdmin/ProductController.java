package com.example.noithatshop.admin.ControllerAdmin;

import com.example.noithatshop.Model.Category;
import com.example.noithatshop.Model.Product;
import com.example.noithatshop.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@RequestMapping("/admin/product")
@Controller
public class ProductController extends BaseController{
    @Autowired
    ProductRepository productRepo;

    @GetMapping()
    public String listProduct(Model model){
        List<Product> listProduct = productRepo.findAll();
        List<Category> listCategories = categorySV.getAllCategory();
        model.addAttribute("category", listCategories);
        model.addAttribute("product", listProduct);

        model.addAttribute("content", "admin/product/list");
        return "admin/layout";
    }

    @GetMapping("/add")
    public String showFormAddProduct(Model model){
        List<Category> listCategories = categorySV.getAllCategory();
        model.addAttribute("category", listCategories);
        model.addAttribute("content", "admin/product/add");
        return "admin/layout";
    }

    @PostMapping("/save-product")
    public String addProduct(
            @RequestParam String name, @RequestParam String title,
            @RequestParam String description,@RequestParam Double price, @RequestParam Double gianhap,
            @RequestParam Integer quantity,@RequestParam MultipartFile image,
            @RequestParam Long categoryId,
            RedirectAttributes redirectAttributes) {
        try {
            productSV.addProduct(name, title,description,price,gianhap,quantity,image,categoryId);
            redirectAttributes.addFlashAttribute("mess", "Sản phẩm đã được thêm thành công!");
            return "redirect:/admin/product";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/product/add";
        }
    }

    //Xem chi tiet san pham
    @GetMapping("/edit/{id}")
    public String showProduct(@PathVariable Long id, Model model) throws Exception {

        Product product = productRepo.findById(id).orElseThrow(()->new Exception("ko tim thay san pham"));

        List<Category> listCategories = categorySV.getAllCategory();
        model.addAttribute("category", listCategories);
        model.addAttribute("product", product);
        model.addAttribute("content", "admin/product/edit");
        return "admin/layout";
    }

    //Sửa sản phẩm
    @PostMapping("/edit/edit-product/{id}")
    public String editProduct(@PathVariable Long id,@RequestParam String name, @RequestParam String title,
                              @RequestParam String description,@RequestParam Double price, @RequestParam Double gianhap,
                              @RequestParam Integer quantity,@RequestParam MultipartFile image,
                              @RequestParam Long categoryId,RedirectAttributes redirectAttributes) {
        try {
            productSV.updateProduct(id, name, title, description, price, gianhap, quantity, image, categoryId);
            redirectAttributes.addFlashAttribute("mess", "Cập nhật sản phẩm thành công!");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi cập nhật sản phẩm: " + e.getMessage());
        }
        return "redirect:/admin/product";
    }

    //Xóa sản phẩm
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            productSV.deleteProduct(id);
            redirectAttributes.addFlashAttribute("mess", "Sản phẩm đã được xóa thành công.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi xóa sản phẩm: " + e.getMessage());
        }
        return "redirect:/admin/product";
    }





}
