package com.example.noithatshop.admin.ControllerAdmin;
import com.example.noithatshop.Model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AdminController extends BaseController {

    @GetMapping("/login")
    public String showLoginForm() {
        return "admin/login";
    }

    //hiện thị danh sahcs
    @GetMapping("admin/nhansu")
    public String showList(Model model) {
        List<User> users = adminSV.findAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("content", "admin/nhanvien/list");
        return "admin/layout";
    }

    @GetMapping("admin/nhansu/add")
    public String register(Model model) {
        model.addAttribute("content", "admin/nhanvien/add");
        return "admin/layout";
    }

    // Xử lý đăng ký người dùng
    @PostMapping("/admin/register")
    public String registerUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        try {
            adminSV.registerUser(user);
            redirectAttributes.addFlashAttribute("mess", "bạn đã đăng kí thành công");
            return "redirect:/admin/nhansu"; // Trả về trang thành công
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mess", "Có lỗi xảy ra");
            return "redirect:/admin/nhansu";
        }
    }

    @GetMapping("/admin")
    public String adminHome(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Thêm tên người dùng vào mô hình
        model.addAttribute("username", username);
        model.addAttribute("content", "admin/content/Dashboard");
        return "admin/layout";
    }


}
