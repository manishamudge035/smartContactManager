package com.sample5.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sample5.project.entities.User;
import com.sample5.project.helper.message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class homeController {
	@Autowired
	com.sample5.project.repository.userRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	
	
	
	
	@GetMapping("/home")
	public String start(Model model)
	{
		
		model.addAttribute("title","Home-smart contact manager");
		
		
		
		return"home";
		
	}
	
	
	
	@GetMapping("/viewabout")
	public String aboutHandler(Model model)
	{
		
		
		model.addAttribute("title","About-smart contact manager");
		return "about";
		
	}
	
	@GetMapping("/signup")
	public String signup(Model model)
	{
		
		
		model.addAttribute("title","Register-smart contact manager");
		
		
		model.addAttribute("user",new User());//sent blank object of user 
		
		return "signup";
		
	}
	
	//handler for register user
	@PostMapping("/do_register")
	public String registerUser(@Valid @ModelAttribute("user") User user,BindingResult result1,
			@RequestParam(value="agreement",defaultValue="false") 
	          boolean agreement ,Model model,HttpSession session)

	{
		try {
			if(!agreement)
			{
				
				
				throw new Exception("you have not agreed the term and conditions");
				
			}
			
			if(result1.hasErrors())
			{
				
				System.out.println("ERROR:"+result1.toString());
				
				model.addAttribute("user",user);
				
				
				return "signup";
				
			}
		
		
		
		user.setEnable(true);
		user.setImagURL("default.png");
		user.setRole("USER");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		
		
		
		System.out.println("Agreement:"+agreement);
		System.out.println(user);
		
		
		User result= userRepository.save(user);
		
		
		model.addAttribute("user",new User());
		
		session.setAttribute("message",new message("successfully registered","alert-success"));
		
		return "signup";
		
		
		
		
			
		} 
		
		catch (Exception e) {
			// TODO: handle exception
			
			
			e.printStackTrace();
			
			model.addAttribute("user",user);
			
			session.setAttribute("message", new message("something went wrong !!" +e.getMessage(),"alert-danger"));
			
		}
		
		
		
		
		return "signup";
		
	}
	
	
	
	//handler for custome loginC
	@GetMapping("/newlogin")
	public String Customelogin(Model model)
	{
		
		model.addAttribute("title","Login here");
		
		
		return "login";
	}

}
