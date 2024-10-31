package com.example.noithatshop.Repository;

import com.example.noithatshop.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
