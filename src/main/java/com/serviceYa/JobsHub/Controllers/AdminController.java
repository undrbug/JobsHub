
package com.serviceYa.JobsHub.Controllers;
import com.serviceYa.JobsHub.Entities.User;
import com.serviceYa.JobsHub.Exceptions.MiException;
import com.serviceYa.JobsHub.Repositories.UserRepository;
import com.serviceYa.JobsHub.Services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



     
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @GetMapping("/dashboard")
    public String dashboard (Model model)throws MiException {
        model.addAtribute("users",userRepository.findAll(Sort.by(Sort.Direction.ASC,"profession")));
        return "dashboard";
    }
    @PostMapping("/search")
    public String searchEngine(@RequestParam(name="search",require=false)String search,Model model){
        List<User>se=userRepository.searchEngine(search);
        model.addAttribute("se",se);
        model.addAttribute("users",userRepository.findAll(Sort.by(Sort.Direction.ASC,"profession")));
        return "dashboard";
    }
}
