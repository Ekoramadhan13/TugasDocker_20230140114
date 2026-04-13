package com.tugas.deploy.controller;

import com.tugas.deploy.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private static final List<User> listUser = new ArrayList<>();
    private final String ADMIN_USER = "admin";
    private final String ADMIN_PASS = "20230140114";

    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        model.addAttribute("listMahasiswa", listUser);
        return "home";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String loginProcess(@RequestParam String username, @RequestParam String password, HttpSession session) {
        if (ADMIN_USER.equals(username) && ADMIN_PASS.equals(password)) {
            session.setAttribute("user", username);
            return "redirect:/";
        }
        return "redirect:/login";
    }

    @GetMapping("/form")
    public String formPage(HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        return "form";
    }

    @PostMapping("/submit")
    public String submitProcess(@ModelAttribute User user, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        listUser.add(user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}