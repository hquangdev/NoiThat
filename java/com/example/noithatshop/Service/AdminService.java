package com.example.noithatshop.Service;

import com.example.noithatshop.Model.Role;
import com.example.noithatshop.Model.User;
import com.example.noithatshop.Repository.RoleRepository;
import com.example.noithatshop.Repository.UserRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminService {
    @Autowired
    private UserRepository uerRepo;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //đăng kí nhân sự
    @Transactional
    public User registerUser(User user) {
        // Mã hóa mật khẩu
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        // Lấy role từ roleId
        if (user.getRole() != null && user.getRole().getId() != null) {
            Role role = roleRepository.findById(user.getRole().getId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy vai trò"));
            user.setRole(role);
        }

        return uerRepo.save(user);
    }

    //lấy danh sách
    public List<User> findAllUsers() {
        return uerRepo.findAll();
    }
}
