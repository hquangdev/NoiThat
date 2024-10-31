package com.example.noithatshop.Service;

import com.example.noithatshop.Model.Category;
import com.example.noithatshop.Model.Product;
import com.example.noithatshop.Repository.CategoryRepository;
import com.example.noithatshop.Repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class ProductService {
    @Autowired private CategoryRepository categoryRepo;
    @Autowired private ProductRepository productRepo;

    private final String uploadDir = "F:/File_web/NoiThatShop/src/main/resources/static/assets/images/productImg/" ;

    public String saveFile(MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        String filePath = Paths.get(uploadDir, originalFileName).toString();
        Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

        // Trả về tên file gốc
        return originalFileName;
    }

    // Thêm sản phẩm mới
    public void addProduct(String name, String title, String description, Double price, Double gianhap, Integer quantity, MultipartFile image, Long categoryId ) throws Exception {
        Category category = categoryRepo.findById(categoryId).orElseThrow(()-> new Exception("ko có danh mục"));

        // Kiểm tra tên sản phẩm
        if (productRepo.findByName(name).isPresent()) {
            throw new Exception("Tên sản phẩm đã tồn tại");
        }

        Product product = new Product();
        product.setName(name);
        product.setTitle(title);
        product.setDescription(description);
        product.setPrice(price);
        product.setGianhap(gianhap);
        product.setQuantity(quantity);
        product.setCategory(category);

        String imageFileName = saveFile(image);
        product.setImage(imageFileName);

        // Lưu sản phẩm vào cơ sở dữ liệu
        productRepo.save(product);
    }

    public void deleteImageFile(String fileName) {
        File file = new File(uploadDir + fileName);
        System.out.println("Đường dẫn ảnh: " + file.getAbsolutePath()); // In ra đường dẫn
        if (file.exists()) {
            boolean deleted = file.delete();
            if (deleted) {
                System.out.println("Xóa ảnh thành công.");
            } else {
                System.out.println("Không thể xóa ảnh.");
            }
        } else {
            System.out.println("Ảnh không tồn tại.");
        }
    }

    public void deleteProduct(Long id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));

        // Xóa ảnh từ thư mục
        if (product.getImage() != null) {
            deleteImageFile(product.getImage());
        }

        productRepo.deleteById(id);
    }

    public void updateProduct(Long id, String name, String title, String description, Double price, Double gianhap, Integer quantity, MultipartFile image, Long categoryId) throws Exception {
        // Tìm sản phẩm theo ID
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));

        // Kiểm tra tên sản phẩm
        if (!product.getName().equals(name) && productRepo.findByName(name).isPresent()) {
            throw new Exception("Tên sản phẩm đã tồn tại");
        }

        // Cập nhật thông tin sản phẩm
        product.setName(name);
        product.setTitle(title);
        product.setDescription(description);
        product.setPrice(price);
        product.setGianhap(gianhap);
        product.setQuantity(quantity);
        product.setCategory(categoryRepo.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Không có danh mục")));

        if (image != null && !image.isEmpty()) {
            // Xóa ảnh cũ
            deleteImageFile(product.getImage());

            // Lưu hình ảnh mới
            String imageFileName = saveFile(image);
            product.setImage(imageFileName);
        }

        // Lưu sản phẩm đã cập nhật vào cơ sở dữ liệu
        productRepo.save(product);
    }

    public List<Product> getProductsByIds(Set<Long> ids) {
        return productRepo.findByIdIn(ids);
    }

    public Optional<Product> getProductById(Long productId) {
        return productRepo.findById(productId);
    }


    //tim kiem theo ten
    public List<Product> searchProductsByName(String query) {
        return productRepo.findByNameContainingIgnoreCase(query);
    }


    public List<Product> getProductsByCategoryId(Long id) {
        return productRepo.findByCategoryId(id);
    }
}
