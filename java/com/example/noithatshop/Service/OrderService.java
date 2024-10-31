package com.example.noithatshop.Service;

import com.example.noithatshop.Model.Order;
import com.example.noithatshop.Model.OrderItem;
import com.example.noithatshop.Repository.OrderItemRepository;
import com.example.noithatshop.Repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Transactional
    public Order saveOrder(Order order) {
        Order savedOrder = orderRepository.save(order);

        // Lưu chi tiết hóa đơn
        for (OrderItem item : order.getItems()) {
            item.setOrder(savedOrder); // Liên kết OrderItem với Order
            orderItemRepository.save(item);
        }
        return savedOrder;
    }

    public Order findOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public List<Order> getAllOrder(){
        return orderRepository.findAll();
    }
}
