package com.example.noithatshop.admin.ControllerAdmin;

import com.example.noithatshop.Model.KhachHang;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RequestMapping("/admin")
@Controller
public class ListKhachController extends BaseController{

    @GetMapping("/khachhang")
    public String listKhachhang(Model model){
        List<KhachHang> listKhachHang = khachHangService.getAllKhachhang();
        model.addAttribute("khachhang", listKhachHang);
        model.addAttribute("content", "admin/khachhang/list");

        return "admin/layout";
    }

    @GetMapping("/khachhang/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {

        khachHangService.deleteKhach(id);
        redirectAttributes.addFlashAttribute("Đã xóa thành công");
        return "redirect:/admin/khachhang";
    }

}
