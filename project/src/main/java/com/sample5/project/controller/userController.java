package com.sample5.project.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sample5.project.entities.Contact;
import com.sample5.project.entities.User;
import com.sample5.project.helper.message;
import com.sample5.project.repository.ContactRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class userController {
	
	
	@Autowired
	com.sample5.project.repository.userRepository userRepository;
	
	@Autowired
	ContactRepository contactRepository;
	
	
	// handler for  fetch user dashboard
	
	@GetMapping("index")
	public String dashboard(Model model,Principal principal)
	{
		
		
		String userName=principal.getName();
		
		
		System.out.println(userName);
		
		
		
		
		
		//getting the user from userName(email) from database
		
		
		
		User user=userRepository.getUserByUserName(userName);
		
		
		System.out.println(user);
		
		//sending user from controller to dashboard
		
		model.addAttribute("userName", userName);
		
		
		
		
		return "normal_user/user_dashboard";
		
	}



	//method handler for add contact page

@GetMapping("/add-contact")
public String addContactForm(Model model,Principal principal)

{
	
	String userName=principal.getName();
	
	
	User user1=userRepository.getUserByUserName(userName); //take the the data of login user
	
	model.addAttribute("userName", userName);
	
	model.addAttribute("title","Add contact");
	model.addAttribute("contact",new Contact());
	
	
	
	
	
	return "normal_user/add-contact-form";
	

	
}


//handler for save contact

@PostMapping("/process_contact")
public String processContact(@Valid@ModelAttribute("contact") Contact contact ,
		BindingResult result,
		@RequestParam("profileImage") MultipartFile file,
		Principal principal,HttpSession session)

{
	
	if(result.hasErrors())
	{
		
		
		
		System.out.println("Error:"+result.toString());
		
		return "normal_user/add-contact-form";
		
		
	}
	try {
		
		
	String name=principal.getName();//getting logged in user name
	
	User user1=userRepository.getUserByUserName(name);
	
	
	//processing file and uploading file
	if(file.isEmpty())
	{
		
		
		System.out.println("file is empty");
		contact.setImage("contact.png");
		
		
	}
	else
	{
		
		
		//upload the file into folder and update the name to contact
		
		
		contact.setImage(file.getOriginalFilename());
		
		//upload image/provide path 
		File file1=new ClassPathResource("static/images").getFile();
		
		Path path=Paths.get(file1.getAbsolutePath()+File.separator+file.getOriginalFilename());
		
	    Files.copy(file.getInputStream(), path,StandardCopyOption.REPLACE_EXISTING);
	    
	    System.out.println("image is uploaded");
	    
		
		
		
		
	}
	
	

	contact.setUser(user1);//set user
	
	user1.getContact().add(contact); //adding contact 
	
	userRepository.save(user1);
	
	System.out.println(contact);
	
	System.out.println("data added successfully");
	//success massage
	
	session.setAttribute("message", new message("Your contact is added !!Add more..","success"));
	
	
	}catch(Exception e)
	{
		
		
		
		//error message
		
		session.setAttribute("message",new message("something went wrong","danger"));
		
		
	}
	
	
	
	
	
	return "normal_user/add-contact-form";
	
	
	
	
}


//handler for show contact



@GetMapping("/show-contacts")
public String Show_Contacts(Model m,Principal principal,HttpSession session)
{
	
	
	
	String userName=principal.getName();
	
	User user=userRepository.getUserByUserName(userName);
	
	
	
	m.addAttribute("title","show contacts");
	
	
	m.addAttribute("userName",userName);
	
	
	
	
	//send contacts list 
	
	


	List<Contact> contacts=user.getContact();
	
	
	
	
	
	m.addAttribute("contacts",contacts);
	
	
	
	
	
	return "normal_user/showContact";
	
	
	
	
}


//delete contact
@GetMapping("/deleteContact/{c_id}")
public String deleteContact(@PathVariable("c_id") Integer c_id,Model model,HttpSession session)
{
	
	
	
	
	 Optional<Contact> optional=contactRepository.findById(c_id);
	 
	 
	 Contact contact=optional.get();
	 
	 
	 contactRepository.delete(contact);
	 
	 
	 contact.setUser(null);
	 
	 
	 
	 
	
	 session.setAttribute("message1",new message("contact deleted successfully......","success"));
	 
	
	
	
	
	 return "redirect:/user/show-contacts";
		
	
}




//update contact handler

//to secure url use postmapping instead getmapping

@PostMapping("/updateContact/{c_id}")
public String UpdateContact(@PathVariable("c_id") Integer c_id,Model model,Principal principal)

{
	
	//get login user
	
	
	String userName=principal.getName();
	
	User user=userRepository.getUserByUserName(userName);
	
	
	model.addAttribute("userName",userName);
	
	
	//get the data of contact
	
	
	Optional<Contact> optional=contactRepository.findById(c_id);
	
	
	 Contact contact=optional.get();
	 
	 
	 model.addAttribute("contact", contact);
	 
	model.addAttribute("title", "update-contact");
	

	
	
	return "normal_user/update_contact";
	
	
}


@PostMapping("/update_contact")
public String ProcessUpdateContact(@ModelAttribute("contact") Contact contact,Model model ,HttpSession session,Principal principal)

{
	
	
	
	String userName=principal.getName();
	
	User user=userRepository.getUserByUserName(userName);
	
	contact.setUser(user);
	
     
	
	System.out.println("ContactId:"+contact.getC_id());
	System.out.println("Contact name:"+contact.getName());
	
	
	
	
	try {
		
		
		contactRepository.save(contact);
		
		
		
		session.setAttribute("message1",new message("contact updated successfully....!!", "success"));
		
		
		
		
	} catch (Exception e) {
		// TODO: handle exception
		
		
		session.setAttribute("message1",new message("something went wrong....!!","danger"));
	}
	
	
	
	
		
		
		
	
	
	
	
	
	return "redirect:/user/show-contacts";
	
}


//handler for show profile 
@GetMapping("/show_profile")
public String profile(Model model,Principal principal)
{
	
	

	String userName=principal.getName();
	
	
	User user=userRepository.getUserByUserName(userName); //take the the data of login user
	
	model.addAttribute("userName", userName);
	
	model.addAttribute("user", user);
	
	model.addAttribute("title","my profile");
	
	
	
	return "normal_user/profile";
}

}
