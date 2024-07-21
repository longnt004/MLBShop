package com.fpoly.mlbshop.Controller;

import com.fpoly.mlbshop.Service.CookieService;
import com.fpoly.mlbshop.Service.ParamService;
import com.fpoly.mlbshop.Service.SessionService;
import com.fpoly.mlbshop.Service.UsersService;
import com.fpoly.mlbshop.entity.User;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Random;

@Controller
public class SingUpController {
    @Autowired
    UsersService usersService;
    @Autowired
    ParamService paramService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    CookieService cookieService;
    @Autowired
    JavaMailSender emailSender;



    @RequestMapping("/singUp")
    public String register(){
        return "page/register";
    }

    @RequestMapping("/verify")
    public String verify(){
        return "page/verify";
    }

    @RequestMapping("/singUp/register/resendCode/{idUser}")
    public String resendCode(Model model, @PathVariable String idUser) throws MessagingException {
        User user = usersService.findById(idUser);
        if (user != null){
            String newOtp = randomOTP(6);
            System.out.println(newOtp);
            sendMail(user.getEmail(), newOtp);
            model.addAttribute("messageVerify", "The verification code has been sent to your email !");
            return "page/verify";
        }else {
            model.addAttribute("message", "Account not found !");
            usersService.deleteById(idUser);
            return "page/register";
        }
    }

    @RequestMapping("/singUp/register/{idUser}")
    public String submit(Model model, @PathVariable String idUser) throws InterruptedException {
        String otp = String.valueOf(cookieService.getValue("otp"));
        String code = paramService.getParam("verifyCode", "");
        if (otp.equals(code)){
            User newUser = usersService.findById(idUser);
            newUser.setStatus(true);
            usersService.update(newUser);
            System.out.println(newUser.getStatus());
            Thread.sleep(3000);
            model.addAttribute("emailNewUser", newUser.getEmail());
            model.addAttribute("message", "Account has been activated !");
            return "page/login";
        }else {
            model.addAttribute("messageVerify", "Verification code is incorrect !");
            return "page/verify";
        }
    }



    @RequestMapping("/singUp/register")
    public String singUp(Model model) {
        String idClient = usersService.autoIncreaseIdClient();
        String email = paramService.getParam("email", "");
        String fullName = paramService.getParam("fullName", "");
        String password = paramService.getParam("password", "");

        if (usersService.findByEmail(email) == null){
            User user = new User();
            user.setIdUser(idClient);
            user.setEmail(email);
            user.setFullname(fullName);
            user.setPassword(passwordEncoder.encode(password));
            user.setRole("USER");
            user.setStatus(false);
            usersService.save(user);
            model.addAttribute("idUser", user.getIdUser());
            System.out.println(user.getIdUser());
            model.addAttribute("messageVerify", "The verification code has been sent to your email !");
            String otp = randomOTP(6);
            System.out.println(otp);
            cookieService.addCookie("otp", otp, 60);
            sendMail(email, otp);
            return "page/verify";
        }else {
            model.addAttribute("message", "Account already exists !");
            return "page/register";
        }
    }

    private final String alpha = "abcdefghijklmnopqrstuvwxyz"; // a-z
    private final String alphaUpperCase = alpha.toUpperCase(); // A-Z
    private final String digits = "0123456789"; // 0-9
    private final String ALPHA_NUMERIC = alpha + alphaUpperCase + digits;
    private static final Random generator = new Random();

    public void sendMail(String to, String text){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("nguyenlong01042004@gmail.com");
        message.setTo(to);
        message.setSubject("<<MLB Shop>> OTP Verification Code");
        message.setText("Your OTP code is: " + text);

        emailSender.send(message);
    }

    public static int randomNumber(int min, int max) {
        return generator.nextInt((max - min) + 1) + min;
    }

    public String randomOTP(int numberOfCharacter) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numberOfCharacter; i++) {
            int number = randomNumber(0, ALPHA_NUMERIC.length() - 1);
            char ch = ALPHA_NUMERIC.charAt(number);
            sb.append(ch);
        }
        return sb.toString();
    }
}
