package com.example.noithatshop.Service;

import com.example.noithatshop.Model.KhachHang;
import com.example.noithatshop.Repository.KhachHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KhachHangService {

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    //lấy danh sách
    public List<KhachHang> getAllKhachhang(){

        return khachHangRepository.findAll();
    }

    //xóa khách
    public void deleteKhach(Long id) {

        khachHangRepository.deleteById(id);
    }


    public String registerUser(KhachHang khachHang) {
        if (khachHangRepository.findByEmail(khachHang.getEmail()).isPresent()) {
            return "Đã tồn tại Email này!";
        }
        // Hash the password
        String hashedPassword = passwordEncoder.encode(khachHang.getPassword());
        khachHang.setPassword(hashedPassword);

        // Save the user
        khachHangRepository.save(khachHang);
        return "Bạn đã đăng kí thành công!";
    }

    public KhachHang login(String email, String password) {
        Optional<KhachHang> optionalKhachHang = khachHangRepository.findByEmail(email);

        if (optionalKhachHang.isPresent()) {
            KhachHang khachHang = optionalKhachHang.get();
            if (passwordEncoder.matches(password, khachHang.getPassword())) {
                return khachHang; // Trả về thông tin người dùng nếu đăng nhập thành công
            }
        }
        return null; // Trả về null nếu đăng nhập thất bại
    }

}
