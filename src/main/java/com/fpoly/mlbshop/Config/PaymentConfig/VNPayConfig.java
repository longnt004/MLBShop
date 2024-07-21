package com.fpoly.mlbshop.Config.PaymentConfig;

import com.fpoly.mlbshop.Utils.VNPayUtil;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

@Configuration
public class VNPayConfig {
    @Getter
    @Value("https://sandbox.vnpayment.vn/paymentv2/vpcpay.html")
    private String vnp_PayUrl;
    @Value("0EK5VBL9")
    private String vnp_TmnCode;
    @Getter
    @Value("6YHSL41U8UF1BG2QO91YESKSA4CJ0IDD")
    private String vnp_SecretKey;
    @Value("http://localhost:8080/cart/payment/vnpay-return")
    private String vnp_ReturnUrl;
    @Value("2.1.0")
    private String vnp_Version;
    @Value("pay")
    private String vnp_Command;
    @Value("billpayment")
    private String vnp_OrderType;

    public Map<String, String> getVnPayConfig() {
        Map<String, String> vnpayParamsMap = new HashMap<>();
        vnpayParamsMap.put("vnp_Version", vnp_Version);
        vnpayParamsMap.put("vnp_Command", vnp_Command);
        vnpayParamsMap.put("vnp_TmnCode", vnp_TmnCode);
        vnpayParamsMap.put("vnp_CurrCode", "VND");
        vnpayParamsMap.put("vnp_TxnRef", VNPayUtil.getRandomNumber(8));
        vnpayParamsMap.put("vnp_OrderType", vnp_OrderType);
        vnpayParamsMap.put("vnp_ReturnUrl", vnp_ReturnUrl);
        vnpayParamsMap.put("vnp_OrderInfo", "Thanh toán đơn hàng: "+VNPayUtil.getRandomNumber(8));
        vnpayParamsMap.put("vnp_Locale", "vn");
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnpCreateDate = formatter.format(calendar.getTime());
        vnpayParamsMap.put("vnp_CreateDate", vnpCreateDate);
        calendar.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(calendar.getTime());
        vnpayParamsMap.put("vnp_ExpireDate", vnp_ExpireDate);
        return vnpayParamsMap;
    }
}
