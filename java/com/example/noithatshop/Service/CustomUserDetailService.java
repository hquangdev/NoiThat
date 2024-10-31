package com.example.noithatshop.Service;

import com.example.noithatshop.Model.CustomUserDetails;
import com.example.noithatshop.Model.Role;
import com.example.noithatshop.Model.User;
import com.example.noithatshop.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;


@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userSv;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userSv.findByEmail(email);
        if (user == null){
            throw new UsernameNotFoundException("Tài khoản k đúng");
        }

        Collection<GrantedAuthority> grantedAuthorities = new HashSet<>();

        Role role = roleRepository.findById(user.getRole().getId())
                .orElse(null); // Lấy vai trò bằng ID

        if (role != null && role.getName() != null && !role.getName().isEmpty()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        } else {
            System.out.println("Role is null or empty for user ID: " + user.getId());
        }

        // Trả về đối tượng UserDetails tùy chỉnh
        return new CustomUserDetails(user, grantedAuthorities);
    }
}
