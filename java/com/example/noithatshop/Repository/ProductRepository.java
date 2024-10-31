package com.example.noithatshop.Repository;

import com.example.noithatshop.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByIdIn(Set<Long> ids);
    Optional<Product> findByName(String name);

    //tim kiem theo ten
    List<Product> findByNameContainingIgnoreCase(String query);

    //san pham trong danh muc
    List<Product> findByCategoryId(Long categoryId);


}
