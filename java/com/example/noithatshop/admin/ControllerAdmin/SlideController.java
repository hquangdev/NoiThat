package com.example.noithatshop.admin.ControllerAdmin;


import com.example.noithatshop.Model.Slide;
import com.example.noithatshop.Repository.SlideRepository;
import com.example.noithatshop.Service.SlideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@RequestMapping("/admin/slide")
@Controller
public class SlideController extends BaseController{


    @GetMapping("/list")
    public String listSlide(Model model){
        List<Slide> slides = slideSV.getAllSLide();
        model.addAttribute("slide", slides);
        model.addAttribute("content", "/admin/slide/list");
        return "/admin/layout";
    }

    //chuyển tới trang sửa slide
    @GetMapping("/edit/{id}")
    public String getSlideId(@PathVariable Long id, Model model){
        Slide slideID = slideRepo.findById(id).orElseThrow(()-> new RuntimeException("ko có slide nào"));

        model.addAttribute("slide", slideID);
        model.addAttribute("content", "/admin/slide/edit");
        return "admin/layout";
    }

    @GetMapping("/add")
    public String showFormSlide(Model model){
        model.addAttribute("content", "admin/slide/add");
        return "admin/layout";
    }

    @PostMapping("/save-slide")
    public String addSlide(@RequestParam("title") String title, @RequestParam("content") String content
            ,@RequestParam("image") MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            slideSV.saveSlide(title,content, file);
            redirectAttributes.addFlashAttribute("mess", "Slide đã được thêm thành công!");
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("mess", "Có lỗi xảy ra khi thêm slide!");
        }
        return "redirect:/admin/slide/list";
    }

    //xóa slide
    @GetMapping("/delete/{id}")
    public String deleteSlide(@PathVariable Long id, RedirectAttributes redirectAttributes){

        slideSV.deleteslide(id);
        redirectAttributes.addFlashAttribute("mess", "Đã xóa thành công");
        return "redirect:/admin/slide/list";
    }

    //Sửa Slide
    @PostMapping("/edit/edit-slide/{id}")
    public String updateSlide(@PathVariable Long id, @RequestParam("title") String title, @RequestParam("content") String contnet,
                              @RequestParam MultipartFile image, RedirectAttributes redirectAttributes) throws Exception {
        try{
            slideSV.updateSlide(id,title, contnet, image);
            redirectAttributes.addFlashAttribute("mess", "Cập nhật sản phẩm thành công!");
        }catch (Exception ex){
            redirectAttributes.addFlashAttribute("error", "đã có lỗi xảy ra");
        }
        return "redirect:/admin/slide/list";
    }
}
