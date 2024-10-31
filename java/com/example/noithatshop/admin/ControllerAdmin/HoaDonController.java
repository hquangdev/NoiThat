package com.example.noithatshop.admin.ControllerAdmin;

import com.example.noithatshop.Model.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@RequestMapping("/admin")
@Controller
public class HoaDonController extends  BaseController{


    @GetMapping("/hoadon")
    public String listOrder(Model model){
        List<Order> listOrder = orderService.getAllOrder();

        model.addAttribute("orders", listOrder);
        model.addAttribute("content", "admin/hoadon/list");
        return "admin/layout";
    }

    // Hiển thị chi tiết hóa đơn theo ID
    @GetMapping("/hoadon/{id}")
    public String viewOrder(@PathVariable Long id, Model model) {
        Order order = orderService.findOrderById(id);
        if (order != null) {
            model.addAttribute("order", order);
            model.addAttribute("content", "admin/hoadon/chitiethoadon");
            return "admin/layout"; // Template để hiển thị chi tiết hóa đơn
        }
        return "admin/error";
    }

    @PostMapping("/status/{id}")
    public String confirmOrder(@PathVariable Long id, @RequestParam String status, RedirectAttributes redirectAttributes) {
        Order order = orderService.findOrderById(id); // Lấy đơn hàng bằng ID

        if (order != null) {
            order.setStatus(status);
            orderService.saveOrder(order); // Lưu lại thay đổi
            redirectAttributes.addFlashAttribute("mess", "Đơn hàng đã được xác nhận là đã giao.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy đơn hàng.");
        }

        return "redirect:/admin/hoadon"; // Quay lại danh sách đơn hàng
    }

}
