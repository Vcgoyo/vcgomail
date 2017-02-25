package com.vcgo.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

	@RequestMapping("/page/login")
	public String login(String redirecturl,Model model) {
		model.addAttribute("redirect", redirecturl);
		return "login";
	}
	@RequestMapping("/page/showRegister")
	public String showRegister() {
		return "register";
	}
}
