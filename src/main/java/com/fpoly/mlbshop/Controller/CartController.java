package com.fpoly.mlbshop.Controller;

import com.fpoly.mlbshop.Service.*;
import com.fpoly.mlbshop.entity.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {
    @Autowired
    ProductService productService;
    @Autowired
    VNPayService vnpayService;
    @Autowired
    ParamService paramService;
    @Autowired
    OrderDetailService orderDetailService;
    @Autowired
    UsersService usersService;
    @Autowired
    OrderService orderService;
    @Autowired
    SessionService sessionService;

    @RequestMapping("/cart")
    public String Cart(Model model) {
        CustomUserDetails auth = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<OrderDetail> cart = orderDetailService.findByUser(auth.getIdUser());
        double totalPrice = cart.stream().mapToDouble(OrderDetail::getAmount).sum();
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("cart", cart);
        return "page/cart";
    }


    @RequestMapping("/cart/remove/{id}")
    public String removeCart(@PathVariable("id") String id) {
        orderDetailService.deleteById(id);
        System.out.println("id: " + id);
        return "redirect:/cart";
    }

    @RequestMapping("/cart/plus/{id}")
    public String plusCart(@PathVariable("id") String id) {
        OrderDetail orderDetail = orderDetailService.findById(id);
        orderDetail.setQuantity(orderDetail.getQuantity() + 1);
        orderDetail.setAmount(orderDetail.getPrice() * orderDetail.getQuantity());
        orderDetailService.update(orderDetail);
        return "redirect:/cart";
    }

    @RequestMapping("/cart/minus/{id}")
    public String minusCart(@PathVariable("id") String id) {
        OrderDetail orderDetail = orderDetailService.findById(id);
        if (orderDetail.getQuantity() > 1) {
            orderDetail.setQuantity(orderDetail.getQuantity() - 1);
            orderDetail.setAmount(orderDetail.getPrice() * orderDetail.getQuantity());
            orderDetailService.update(orderDetail);
        }
        return "redirect:/cart";
    }

    @RequestMapping("/cart/payment/vnPay/{id}")
    public String pay(HttpServletRequest request, @PathVariable("id") String id, @RequestParam("totalPrice") double totalPrice) {
        Order order = new Order();
        List<OrderDetail> orderDetail = orderDetailService.findByUser(id);
        User user = usersService.findById(id);
        if (!orderDetail.isEmpty()) {
            order.setOrderDetails(orderDetail);
            order.setTotalPrice((long) totalPrice);
            order.setOrderDate(java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
            order.setPaymentStatus("Chờ xác nhận");
            order.setIdOrder(orderService.autoIncreaseIdOrder());
            order.setPaymentTypes("VNPay");
            order.setUser(user);
            orderService.save(order);
            sessionService.set("idOrder", order.getIdOrder());
        }else {
            return "redirect:/cart";
        }
        return "redirect:" + vnpayService.createVnPayPayment(request, (long) totalPrice).paymentUrl;
    }

    @RequestMapping("/cart/payment/cod/{id}")
    public String cod(@PathVariable("id") String id, @RequestParam("totalPrice") double totalPrice, Model model) {
        Order order = new Order();
        User user = usersService.findById(id);
        List<OrderDetail> orderDetail = orderDetailService.findByUser(id);
        if (!orderDetail.isEmpty()) {
            order.setOrderDetails(orderDetail);
            order.setTotalPrice((long) totalPrice);
            order.setOrderDate(java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
            order.setPaymentStatus("Thanh toán khi nhận hàng");
            order.setIdOrder(orderService.autoIncreaseIdOrder());
            order.setPaymentTypes("COD");
            order.setUser(user);
            orderService.save(order);
            model.addAttribute("status", "Đặt hàng thành công!");
            orderDetailService.deleteByUser(id);
        }
        return "redirect:/cart";
    }

    @RequestMapping("/cart/payment/vnpay-return")
    public String paymentReturn(HttpServletRequest request, Model model) {
        String status = request.getParameter("vnp_ResponseCode");
        CustomUserDetails auth = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String idOrder = sessionService.get("idOrder");
        Order order = orderService.findById(idOrder);
        if (status.equals("00")) {
            model.addAttribute("status", "Thanh toán thành công!");
            order.setPaymentStatus("Đã thanh toán");
            orderService.update(order);
            orderDetailService.deleteByUser(auth.getIdUser());
            sessionService.remove("idOrder");
        }else {
            model.addAttribute("status", "Thanh toán thất bại!");
            order.setPaymentStatus("Thanh toán thất bại");
            orderService.update(order);
        }
        attribute(model, request);
        return "page/payment-return";
    }

    public void attribute(Model model, HttpServletRequest request) {
        String amount = request.getParameter("vnp_Amount");
        String bankCode = request.getParameter("vnp_BankCode");
        String orderInfo = request.getParameter("vnp_OrderInfo");
        String orderType = request.getParameter("vnp_OrderType");
        String responseTime = request.getParameter("vnp_ResponseTime");
        model.addAttribute("amount", amount);
        model.addAttribute("bankCode", bankCode);
        model.addAttribute("orderInfo", orderInfo);
        model.addAttribute("orderType", orderType);
        model.addAttribute("responseTime", responseTime);
    }
}
