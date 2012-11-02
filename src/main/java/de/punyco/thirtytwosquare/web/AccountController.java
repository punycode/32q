package de.punyco.thirtytwosquare.web;

import com.google.appengine.api.users.UserService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RequestMapping("/account")
@Controller
public class AccountController {

    @Autowired
    private UserService userService;

    @RequestMapping
    public String index() {

        return "account/index";
    }


    @RequestMapping("/login")
    public String login() {

        return "redirect:/";
    }


    @RequestMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.getSession().invalidate();

        String logoutURL = userService.createLogoutURL("/");
        response.sendRedirect(logoutURL);
    }
}
