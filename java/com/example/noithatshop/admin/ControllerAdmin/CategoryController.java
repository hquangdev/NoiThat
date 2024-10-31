package com.example.noithatshop.admin.ControllerAdmin;

import com.example.noithatshop.Model.Category;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@RequestMapping("/admin/category")
@Controller
public class CategoryController  extends BaseController {

//    lấy tất cả danh mục
    @GetMapping()
    public String listCategory(Model model){
        List<Category> categories = categorySV.getAllCategory();
        model.addAttribute("content", "admin/category/list" );
        model.addAttribute("categories", categories);
        return "admin/layout";
    }

    @GetMapping("/add")
    public String showAddCategoryForm(Model model){
        model.addAttribute("content", "admin/category/add" );
        return "admin/layout";
    }

    @PostMapping("/save-category")
    public String saveProduct(@ModelAttribute Category category, RedirectAttributes redirectAttributes){
        categorySV.saveCategory(category);
        redirectAttributes.addFlashAttribute("mess", "Bạn đã thêm danh mục thành công");
        return "redirect:/admin/category";
    }

    //chuyen toi trang sua danh muc
    @GetMapping("/edit/{id}")
    public String showEditCategoryForm(@PathVariable Long id, Model model){
        Category categoryID = categorySV.getCategoryID(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục với ID: " + id));
        model.addAttribute("category", categoryID);
        model.addAttribute("content", "admin/category/edit");
        return "admin/layout";
    }

    //dửa danh mục
    @PostMapping("/edit-category/{id}")
    public String updateCategory(@PathVariable Long id,  @ModelAttribute Category category, RedirectAttributes redirectAttributes){

        try{
            String mess = categorySV.updateCategory(id, category);
            redirectAttributes.addFlashAttribute("mess", mess);
        }catch (RuntimeException e){
            redirectAttributes.addFlashAttribute("error",e.getMessage());
        }
        return "redirect:/admin/category";

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    // Xóa danh mục
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Category category = categorySV.getCategoryID(id).orElse(null);
        if (category != null) {
            categorySV.delete(category);
            redirectAttributes.addFlashAttribute("mess", "Danh mục đã được xóa thành công!");
            return "redirect:/admin/category";
        } else {
            // Nếu không tìm thấy danh mục
            redirectAttributes.addFlashAttribute("error", "Danh mục không tồn tại!");
        }
        return "redirect:/admin/category";
    }

}
