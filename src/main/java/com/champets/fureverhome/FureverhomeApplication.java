package com.champets.fureverhome;

import com.champets.fureverhome.security.SecurityUtil;
import com.champets.fureverhome.user.model.UserEntity;
import com.champets.fureverhome.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class FureverhomeApplication {
	@Autowired
	private UserService userService;
	@GetMapping("/")
	public String displayHome(Model model) {
		UserEntity user = userService.getCurrentUser();
		String username = SecurityUtil.getSessionUser();
		user = userService.findByUsername(username);
		model.addAttribute("user", user);
		if (user == null) {
			return "layout";
		}
		//model.addAttribute("user", user);
		return "user/user-home";
	}

	@GetMapping("/about")
	public String displayAbout() {
		return "user/user-about";
	}

	public static void main(String[] args) {
		SpringApplication.run(FureverhomeApplication.class, args);
	}

}
