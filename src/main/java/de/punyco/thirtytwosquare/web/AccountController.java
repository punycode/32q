package de.punyco.thirtytwosquare.web;

import com.google.appengine.api.users.UserService;

import de.punyco.thirtytwosquare.domain.UserAccount;
import de.punyco.thirtytwosquare.util.MD5Util;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.validation.Valid;


@RequestMapping("/account")
@Controller
public class AccountController {

    @Autowired
    private UserService userService;

    @ModelAttribute("userAccount")
    public UserAccount addCurrentUserToModel(Principal principal) {

        if (principal instanceof Authentication) {
            Authentication authentication = (Authentication) principal;

            return (UserAccount) authentication.getPrincipal();
        } else {
            return null;
        }
    }


    @RequestMapping(method = RequestMethod.GET, produces = "text/html")
    public String index(@ModelAttribute("userAccount") UserAccount userAccount, Model model)
        throws NoSuchAlgorithmException {

        String email = userAccount.getEmail();
        String emailHash = MD5Util.md5Hex(email);
        model.addAttribute("emailHash", emailHash);

        return "account/myprofile";
    }


    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid UserAccount myAccount, BindingResult bindingResult, Model model) {

        return "redirect:/account";
    }


    @RequestMapping(method = RequestMethod.GET, params = "logout")
    public void logout(HttpSession session, HttpServletResponse response) throws IOException {

        session.invalidate();

        String logoutURL = userService.createLogoutURL("/");
        response.sendRedirect(logoutURL);
    }
}
