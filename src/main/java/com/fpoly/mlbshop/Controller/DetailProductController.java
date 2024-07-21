package com.fpoly.mlbshop.Controller;

import com.fpoly.mlbshop.Service.*;
import com.fpoly.mlbshop.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@Controller
public class DetailProductController {
    @Autowired
    ProductService productService;
    @Autowired
    imgSlideService imgSlideService;
    @Autowired
    DetailProductService detailProductService;
    @Autowired
    private ParamService paramService;
    @Autowired
    UsersService usersService;
    @Autowired
    OrderDetailService orderDetailService;

    @RequestMapping("/DetailProduct")
    public String viewForm() {
        return "page/detailProduct";
    }

    @RequestMapping("/DetailProduct/{id}")
    public String DetailProduct(Model model, @PathVariable("id") String id){
        Product product = productService.findById(id);
        setForm(model, id, product);
        String total = detailProductService.countByProduct(id);
        if (total == null){
            model.addAttribute("total", "Sản phẩm đã hết hàng");
        }else {
            model.addAttribute("total", total);
        }
        return "page/detailProduct";
    }

    @RequestMapping("/products/addtocart/{id}")
    public String addToCart(@PathVariable("id") String productId, @RequestParam("color") String color, @RequestParam("size") String size) {
        CustomUserDetails auth = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = usersService.findById(auth.getIdUser());
        DetailProduct detailProduct = detailProductService.findByProductAndColorAndSize(productId, color, size);
        OrderDetail findOrder = orderDetailService.findByProductsAndUser(detailProduct, user);
        int quantity = paramService.getIntParam("quantity", 1);
        if (findOrder != null) {
            findOrder.setQuantity(quantity+findOrder.getQuantity());
            findOrder.setAmount(findOrder.getPrice() * findOrder.getQuantity());
            orderDetailService.update(findOrder);
        }else {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setQuantity(quantity);
            orderDetail.setPrice(detailProduct.getProduct().getPrice());
            orderDetail.setDetailProduct(detailProduct);
            orderDetail.setUser(user);
            orderDetail.setAmount(orderDetail.getPrice() * orderDetail.getQuantity());
            orderDetailService.save(orderDetail);
        }
        return "redirect:/cart";
    }


    public void setForm(Model model, String id, Product product){
        model.addAttribute("product", product);
        model.addAttribute("ListProduct", productService.findAll());
        model.addAttribute("ListImg", imgSlideService.findByIdProduct(id));
        model.addAttribute("ListColor", detailProductService.findColorByIdProduct(id));
        model.addAttribute("ListSize", detailProductService.findSizeByIdProduct(id));
    }

    @ResponseBody
    @GetMapping("/getDetailProduct/{id}")
    public ResponseEntity<DetailProduct> getDetailProduct(@PathVariable("id") String id){
        DetailProduct detailProduct = detailProductService.findByIdProduct(id);
        return ResponseEntity.ok(detailProduct);
    }

    @ResponseBody
    @GetMapping("/detail/countByColor/{idProduct}")
    public ResponseEntity<String> countProductsByColor(@PathVariable("idProduct") String idProduct, @RequestParam("color") String color) {
        // Lấy số lượng sản phẩm có màu được chọn từ database
        String count = detailProductService.countByProductAndColor(idProduct,color);
        return ResponseEntity.ok(count);
    }

    @ResponseBody
    @GetMapping("/detail/countBySize/{idProduct}")
    public ResponseEntity<String> countProductsBySize(@PathVariable("idProduct") String idProduct, @RequestParam("size") String size) {
        // Lấy số lượng sản phẩm có size được chọn từ database
        String count = detailProductService.countByProductAndSize(idProduct,size);
        return ResponseEntity.ok(count);
    }

    @ResponseBody
    @GetMapping("/detail/countByColorAndSize/{idProduct}")
    public ResponseEntity<String> countProductsByColorAndSize(@PathVariable("idProduct") String idProduct, @RequestParam("color") String color, @RequestParam("size") String size) {
        // Lấy số lượng sản phẩm có màu và size được chọn từ database
        String count = detailProductService.countByProductAndColorAndSize(idProduct,color,size);
        return ResponseEntity.ok(count);
    }

}
