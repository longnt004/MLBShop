package com.fpoly.mlbshop.Controller;

import com.fpoly.mlbshop.Service.*;
import com.fpoly.mlbshop.Utils.OrderExcelExporter;
import com.fpoly.mlbshop.entity.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class AdminController {
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    DetailProductService detailProductService;
    @Autowired
    ParamService paramService;
    @Autowired
    HttpServletRequest request;
    @Autowired
    imgSlideService imgSlideService;
    @Autowired
    OrderService orderService;

    @RequestMapping("/admin")
    public String admin(Model model){
        String countOrder = orderService.countOrder();
        String countOrderCancel = orderService.countOrderCancel("Đã hủy");
        String countOrderSuccess = orderService.countOrderCancel("Đã thanh toán");
        String countProduct = productService.countProduct();
        String countCategory = categoryService.countCategory();
        model.addAttribute("countProduct", countProduct);
        model.addAttribute("countCategory", countCategory);
        model.addAttribute("countOrderSuccess", countOrderSuccess);
        model.addAttribute("countOrderCancel", countOrderCancel);
        model.addAttribute("countOrder", countOrder);
        model.addAttribute("view", "view00");
        return "admin/admin";
    }

    @RequestMapping("/admin/product")
    public String product(Model model, @Param("keyword") String keyword, @RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo){
        List<Product> ListProduct = productService.findAll();
        List<Catalogue> ListCataloque = categoryService.findAll();
        if (!Objects.equals(keyword, "") && keyword != null) {
            ListProduct = productService.searchProduct(keyword);
            model.addAttribute("keyword", keyword);
        }
        model.addAttribute("ListCataloque", ListCataloque);
        model.addAttribute("ListProduct", ListProduct);
        model.addAttribute("view", "view01");
        model.addAttribute("editProduct", new Product());
        return "admin/admin";
    }

    @RequestMapping("/admin/category")
    public String category(Model model, @Param("keyword") String keyword){
        List<Catalogue> ListCataloque = categoryService.findAll();
        if (keyword != null) {
            ListCataloque = categoryService.searchProduct(keyword);
            model.addAttribute("keyword", keyword);
        }
        model.addAttribute("ListCataloque", ListCataloque);
        model.addAttribute("view", "view02");
        return "admin/admin";
    }

    @RequestMapping("/admin/detail-product")
    public String detailProduct(Model model, @Param("keyword") String keyword){
        List<DetailProduct> ListDetailProduct = detailProductService.findAll();
        List<Product> ListProduct = productService.findAll();
        if (!Objects.equals(keyword, "") && keyword != null) {
            ListDetailProduct = detailProductService.findDetailProductsByKeyWord(keyword);
            model.addAttribute("keyword", keyword);
        }
        model.addAttribute("ListProduct", ListProduct);
        model.addAttribute("ListDetailProduct", ListDetailProduct);
        model.addAttribute("view", "view03");
        return "admin/admin";
    }

    @RequestMapping("/admin/order")
    public String order(Model model, @Param("keyword") String keyword){
        List<Order> ListOrder = orderService.findAll();
        if (!Objects.equals(keyword, "") && keyword != null) {
            ListOrder = orderService.findOrderByKeyWord(keyword);
            model.addAttribute("keyword", keyword);
        }
        model.addAttribute("ListOrder", ListOrder);
        model.addAttribute("view", "view04");
        return "admin/admin";
    }

    @RequestMapping("/admin/product/add")
    public String addProduct(Model model, @RequestParam("newImgSlide") MultipartFile[] files){
        String idProduct = productService.autoIncreaseIdProduct();
        String nameProduct = paramService.getParam("newNameProduct","");
        String price = paramService.getParam("newPrice","");
        String describe = paramService.getParam("newDescribe","");
        String image = files[0].getOriginalFilename();
        String idCatalogue = paramService.getParam("newCatalogue","");
        Product product = new Product();
        product.setIdProduct(idProduct);
        product.setNameProduct(nameProduct);
        product.setPrice(Double.parseDouble(price));
        product.setDescribe(describe);
        product.setImage(image);
        product.setStatus(false);
        try {
            List<imgSlide> imgSlides = new ArrayList<imgSlide>();
            for (MultipartFile file : files) {
                String fileName = file.getOriginalFilename();
                imgSlide item = new imgSlide();
                item.setImgSlide(fileName);
                item.setProduct(product);
                imgSlides.add(item);
            }
            imgSlideService.saveAll(imgSlides);
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        };
        product.setCatalogues(categoryService.findById(idCatalogue));
        productService.saveProduct(product);
        categoryService.updateQuantity();
        return "redirect:/admin/product";
    }

    @RequestMapping("/admin/detail-product/add")
    public String addDetailProduct(Model model){
        int quantity = paramService.getIntParam("quantity", 1);
        String idProduct = paramService.getParam("product","");
        Product product = productService.findById(idProduct);
        product.setStatus(true);
        String Size = paramService.getParam("size","");
        String Color = paramService.getParam("color","");
        DetailProduct detailProduct = new DetailProduct();
        detailProduct.setProduct(product);
        detailProduct.setSize(Size);
        detailProduct.setColor(Color);
        detailProduct.setQuantity(quantity);
        detailProductService.save(detailProduct);
        return "redirect:/admin/detail-product";
    }

    @RequestMapping("/admin/product/update/{idProduct}")
    public String updateProduct(Model model, @PathVariable("idProduct") String idProduct){
        Product product = productService.findById(idProduct);
        product.setNameProduct(paramService.getParam("nameProduct", paramService.getParam("nameProduct", product.getNameProduct())));
        product.setPrice(paramService.getDoubleParam("price", paramService.getDoubleParam("price", product.getPrice())));
        product.setDescribe(paramService.getParam("describe", paramService.getParam("describe", product.getDescribe())));
        product.setCatalogues(categoryService.findById(paramService.getParam("catalogue", product.getCatalogues().getIdCatalogue())));
        product.setStatus(product.isStatus());
        product.setImage(product.getImage());
        product.setImgSlides(product.getImgSlides());
        productService.updateProduct(product);
        categoryService.updateQuantity();
        return "redirect:/admin/product";
    }

    @RequestMapping("/admin/product/delete/{idProduct}")
    public String deleteProduct(Model model, @PathVariable("idProduct") String idProduct){
        Product product = productService.findById(idProduct);
        product.setStatus(false);
        productService.updateProduct(product);
        return "redirect:/admin/product";
    }

    @RequestMapping("/admin/product/deleteMulti")
    public String deleteListProduct(Model model, @RequestParam("listId") List<String> listId){
        for (String id : listId) {
            Product product = productService.findById(id);
            product.setStatus(false);
            productService.updateProduct(product);
        }
        return "redirect:/admin/product";
    }

    @RequestMapping("/admin/order/delete/{idOrder}")
    public String deleteOrder(Model model, @PathVariable("idOrder") String idOrder){
        Order order = orderService.findById(idOrder);
        order.setPaymentStatus("Đã hủy");
        orderService.update(order);
        return "redirect:/admin/order";
    }


    @RequestMapping("/admin/order/deleteMulti")
    public String deleteListOrder(Model model, @RequestParam("listId") List<String> listId){
        for (String id : listId) {
            Order order = orderService.findById(id);
            order.setPaymentStatus("Đã hủy");
            orderService.update(order);
        }
        return "redirect:/admin/order";
    }

    @RequestMapping("/admin/product/activeMulti")
    public String activeListProduct(Model model, @RequestParam("listId") List<String> listId){
        for (String id : listId) {
            Product product = productService.findById(id);
            product.setStatus(true);
            productService.updateProduct(product);
        }
        return "redirect:/admin/product";
    }

    @RequestMapping("/admin/catalogue/add")
    public String addCatalogue(Model model){
        String idCatalogue = categoryService.autoIncreaseIdCatalogue();
        String nameCatalogue = paramService.getParam("nameCatalogue","");
        String imgCatalogue = paramService.getFileParam("imgCatalogue",new File("")).getName();
        Catalogue catalogue = new Catalogue();
        catalogue.setIdCatalogue(idCatalogue);
        catalogue.setNameCatalogue(nameCatalogue);
        catalogue.setQuantity(0);
        catalogue.setImgCatalogue(imgCatalogue);
        categoryService.save(catalogue);
        return "redirect:/admin/category";
    }

    @RequestMapping("/admin/catalogue/update/{idCatalogue}")
    public String updateCatalogue(Model model, @PathVariable("idCatalogue") String idCatalogue){
        Catalogue catalogue = categoryService.findById(idCatalogue);
        if (paramService.getParam("nameCatalogue", "").isEmpty() && paramService.getFileParam("imgCatalogue", new File("")).getName().isEmpty()) {
            catalogue.setNameCatalogue(catalogue.getNameCatalogue());
            catalogue.setImgCatalogue(catalogue.getImgCatalogue());
        } else {
            catalogue.setNameCatalogue(paramService.getParam("nameCatalogue", catalogue.getNameCatalogue()));
            catalogue.setImgCatalogue(paramService.getFileParam("imgCatalogue", new File("")).getName());
        }
        categoryService.update(catalogue);
        return "redirect:/admin/category";
    }

    @RequestMapping("/admin/catalogue/delete/{idCatalogue}")
    public String deleteCatalogue(Model model, @PathVariable("idCatalogue") String idCatalogue){
        categoryService.delete(idCatalogue);
        return "redirect:/admin/category";
    }

    @RequestMapping("/admin/detail-product/delete/{idDetailProduct}")
    public String deleteDetailProduct(Model model, @PathVariable("idDetailProduct") String idDetailProduct){
        detailProductService.delete(idDetailProduct);
        return "redirect:/admin/detail-product";
    }


    @RequestMapping("/admin/order/exportExcel")
    public String exportExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=orders_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Order> listOrdes = orderService.findAll();

        OrderExcelExporter excelExporter = new OrderExcelExporter(listOrdes);

        excelExporter.export(response);
        return "redirect:/admin/order";
    }

    @GetMapping("/crud-product/{productId}")
    @ResponseBody
    public ResponseEntity<Product> editProduct(@PathVariable("productId") String productId, Model model){
        return ResponseEntity.ok(productService.findById(productId));
    }

    @GetMapping("/crud-detail-product/{idDetailProduct}")
    @ResponseBody
    public ResponseEntity<DetailProduct> editDetailProduct(@PathVariable("idDetailProduct") String idDetailProduct, Model model){
        return ResponseEntity.ok(detailProductService.findById(idDetailProduct));
    }

}
