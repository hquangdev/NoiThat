package com.example.noithatshop.Repository;

import com.example.noithatshop.Model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    // Bạn có thể thêm các phương thức tùy chỉnh ở đây nếu cần
}
