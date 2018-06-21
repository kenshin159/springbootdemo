package com.family.tech.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.family.tech.model.jpa.Product;
import com.family.tech.service.ProductService;

@Controller
public class DefaultController {

	@Autowired
	private ProductService productService;

	@GetMapping("/")
	public String home1() {
		return "/home";
	}

	@GetMapping("/home")
	public String home() {
		return "/home";
	}

	@GetMapping("/about")
	public String about() {
		// System.out.println("ABOUT");
		// Product product = new Product("ABC9", "ABC");
		// productService.save(product);
		return "/about";
	}

	@GetMapping("/about1")
	public String about1() {
		System.out.println("ABOUT1");
		try {
			List<Product> list = productService.findAll();
			System.out.println("Tri: " + list.size());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "/about";
	}

	// @GetMapping("/login")
	// public String login() {
	//
	// return "/login";
	// }

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("login");

		return model;
	}

	// customize the error message
	private String getErrorMessage(HttpServletRequest request, String key) {

		Exception exception = (Exception) request.getSession().getAttribute(key);

		String error = "";
		if (exception instanceof BadCredentialsException) {
			error = "Invalid username and password!";
		} else if (exception instanceof LockedException) {
			error = exception.getMessage();
		} else {
			error = "Invalid username and password!";
		}

		return error;
	}

	@GetMapping("/403")
	public String error403() {
		return "/error/403";
	}

}
