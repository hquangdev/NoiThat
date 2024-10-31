package com.example.noithatshop.Service;

import com.example.noithatshop.Model.Product;
import com.example.noithatshop.Model.Slide;
import com.example.noithatshop.Repository.SlideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class SlideService {

    private final String uploadDir = "F:/File_web/NoiThatShop/src/main/resources/static/assets/images/slider" ;

    @Autowired
    SlideRepository slideRepo;

    public List<Slide> getAllSLide(){
        return slideRepo.findAll();
    }

    //thêm slide
    public void saveSlide(String title, String content, MultipartFile file)  throws IOException {

        Slide slide = new Slide();
        if(file != null && !file.isEmpty()){

            String fileName = file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);

            Files.copy(file.getInputStream(), filePath);
            slide.setImage(fileName);
        }

        slide.setTitle(title);
        slide.setContent(content);
        slideRepo.save(slide);
    }

    //sửa slide
    public void updateSlide(Long id, MultipartFile file, Slide slide){
       Slide exSlide = slideRepo.findById(id).
                orElseThrow(() -> new RuntimeException("ko thấy slide"));

       exSlide.setTitle(slide.getTitle());
       exSlide.setContent(slide.getContent());

       slideRepo.save(exSlide);

    }

    //xóa slide
    public void deleteImageFile(String fileName) {
        File file = new File(uploadDir + fileName);
        System.out.println("Đường dẫn ảnh: " + file.getAbsolutePath());
        if (file.exists()) {
            boolean deleted = file.delete();
        } else {
            System.out.println("Ảnh không tồn tại.");
        }
    }

    public void deleteslide(Long id) {
        Slide slide = slideRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));

        // Xóa ảnh từ thư mục
        if (slide.getImage() != null) {
            deleteImageFile(slide.getImage());
        }

        slideRepo.deleteById(id);
    }

    //sửa slide
    public void updateSlide(Long id, String title, String content, MultipartFile image) throws Exception{

        Slide slideID = slideRepo.findById(id).orElseThrow(()-> new Exception("ko tìm thấy id"));

        slideID.setTitle(title);
        slideID.setContent(content);

        if(image != null && image.isEmpty()){
            deleteImageFile(slideID.getImage());

            String fileName = image.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);

            Files.copy(image.getInputStream(), filePath);
            slideID.setImage(fileName);

        }
        slideRepo.save(slideID);
    }

}
