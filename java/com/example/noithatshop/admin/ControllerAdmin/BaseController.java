package com.example.noithatshop.admin.ControllerAdmin;

import com.example.noithatshop.Repository.ProductRepository;
import com.example.noithatshop.Repository.SlideRepository;
import com.example.noithatshop.Service.*;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {

    @Autowired
    public   CategoryService categorySV;

    @Autowired
    public   AdminService adminSV;

    @Autowired
    public  ProductService productSV;

    @Autowired
    public ProductRepository productRepo;

    @Autowired
    public KhachHangService khachHangService;

    @Autowired
    public OrderService orderService;

    //slide
    @Autowired
    public SlideRepository slideRepo;
    @Autowired
    public SlideService slideSV;
}
