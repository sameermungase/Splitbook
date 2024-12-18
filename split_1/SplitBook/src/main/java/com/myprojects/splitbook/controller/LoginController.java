package com.myprojects.splitbook.controller;

import com.myprojects.splitbook.entity.UserLogin;
import com.myprojects.splitbook.service.BusinessUtils;
import com.myprojects.splitbook.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    LoginService loginService;
    @Autowired
    BusinessUtils utils;

    @GetMapping("/index")
    public String showWelcomePage()
    {
        return "index";
    }

    @GetMapping("/login")
    public String displayLoginPage(HttpServletRequest request)
    {
        if(request.getUserPrincipal()!=null)    //if user is still logged in
        {
            return "redirect:/logout";
        }
        return "loginpage";
    }

    @GetMapping("/signup")
    public String displaySignUpPage()
    {
        return "signuppage";
    }

    @PostMapping("/signupprocess")
    public String doSignUp(@RequestParam String name, @RequestParam String email, @RequestParam String password, ModelMap model)
    {
        UserLogin user = loginService.generateUser(email,password,name);
        String res = loginService.registerUser(user);
        model.addAttribute("msg",res);
        return "signuppage";
    }

    @GetMapping("/welcome")
    public String displayHomepage(Authentication authentication, Model model)
    {
        UserLogin user = loginService.getUserByUsername(authentication.getName());

        utils.initHomepageAttributes(user,model);

        return "homepage";
    }

}
