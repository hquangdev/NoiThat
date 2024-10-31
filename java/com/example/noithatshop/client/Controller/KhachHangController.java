package com.example.noithatshop.client.Controller;

import com.example.noithatshop.Model.KhachHang;
import com.example.noithatshop.admin.ControllerAdmin.BaseController;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class KhachHangController extends BaseController {

    @GetMapping("/home/login")
    public String showFormLogin(Model model){
        model.addAttribute("contentShop", "client/content/login");

        return "client/layout";
    }

    @GetMapping("home/register")
    public String showFormRegister(Model model){
        model.addAttribute("contentShop", "client/content/register");

        return "client/layout";
    }

    @PostMapping("/home/register")
    public String register(@ModelAttribute KhachHang khachHang, RedirectAttributes redirectAttributes) {
        String message = khachHangService.registerUser(khachHang);
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/home/login";
    }

    @PostMapping("/home/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {

        KhachHang khachHang = khachHangService.login(email, password);

        if (khachHang != null) {
            // Đăng nhập thành công, lưu tên vào session
            session.setAttribute("username", khachHang.getName());
            session.setAttribute("email", khachHang.getEmail());
            return "redirect:/home";
        } else {
            // Đăng nhập thất bại, hiển thị thông báo
            redirectAttributes.addFlashAttribute("message", "Đăng nhập thất bại. Vui lòng kiểm tra lại.");
            return "redirect:/home/login"; // Quay lại trang đăng nhập
        }
    }
}
