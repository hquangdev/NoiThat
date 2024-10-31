package com.example.noithatshop.Service;

import com.example.noithatshop.Model.Category;
import com.example.noithatshop.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepo;

    public List<Category> getAllCategory(){
        return categoryRepo.findAll();
    }

    //thêm danh mục
    public Category saveCategory(Category category){
        return categoryRepo.save(category);
    }

    public Optional<Category> getCategoryID(Long id){
        return categoryRepo.findById(id);
    }

    //cap nhat danh muc
    public String updateCategory(Long id, Category category){
        Category existingCategory = categoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("không tim thấy id" + id));

        existingCategory.setName(category.getName());
        existingCategory.setDetails(category.getDetails());

        categoryRepo.save(existingCategory);
        return "Cập nhật danh mục thành công";
    }

    //xoa danh muc
    public void delete(Category category) {
        categoryRepo.delete(category);
    }

}
