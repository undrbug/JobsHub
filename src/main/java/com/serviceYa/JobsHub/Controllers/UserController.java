
package com.serviceYa.JobsHub.Controllers;
import com.serviceYa.JobsHub.Entities.Image;
import com.serviceYa.JobsHub.Entities.User;
import com.serviceYa.JobsHub.Entities.Work;
import com.serviceYa.JobsHub.Entities.Reviews;
import com.serviceYa.JobsHub.Enums.Roles;
import com.serviceYa.JobsHub.Enums.Professions;
import com.serviceYa.JobsHub.Exceptions.MiException;
import com.serviceYa.JobsHub.Repositories.WorkRepository;
import com.serviceYa.JobsHub.Services.ImageService;
import com.serviceYa.JobsHub.Services.UserService;
import com.serviceYa.JobsHub.converters.ImageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
 import java.util.List;
import java.io.IOException;


@Controller
@RequestMapping("/user")
public class UserController {
@Autowired
UserService userService;
@Autowired
ImageService imageService;
@Autowired
WorkRepository workRepository;
@GetMapping("/register")
public String user(Model model){
    model.addAttribute("user",new User());
    model.addAttribute("professions",Professions.values());
    return "registerUser";
}

//register
@PostMapping("/register")
public String userRegister(@ModelAttribute User user, Model model)throws MiException{
    if(!userService.validateEmail(user)){
        userService.createUser(user);
        return"redirect:/login";
    }else{
        model.addAttibute("mssg","EL email ingresado ya se encuentra registrado");
        model.addAttribute("professions",Professions.values());
        return "registerUser";
    }
}

@GetMapping("/perfil/{id}")
public String user(@PathVariable("id")String id,ModelMap model,HttpSession session)throws MiExceptions{
    User user=userService.getBy(id);
    model.addAttribute("user",user);
    String check="";
    List<Work>listReviews=workRepository.getWorkByUserProvider(user);
    if(listReviews.size()==0){
        check="false";
    }
    model.addAttribute("check",check);
    model.addAttribute("listReviews",listReviews);
    
    User sessionUser=(User) session.getAttribute("usserSession");
    if(user !=null && sessionUser!=null && (user.getId().equals(sessionUser.getId())|| sessionUser.getRole().equals(Roles.ADMIN))){
        model.addAttribute("professions",Professions.values());
        return "myProfile";
    }else if(sessionUser!=null && sessionUser.getRole().equals(Roles.CUSTOMER)){
        return "otherProfile";
    } else if(sessionUser!=null && sessionUser.getRole().equals(Roles.PROVIDER)){
       return "redirect:/home";
    }else {
        return "redirect:/user/register";
    }
    
}
// delete review 
@Transactional
@GetMapping(value="/perfil/{id}/review")
public String deleteReview(@PathVariable("id")String id,Model model)throws MiException{
    User user=userService.getById(id);
    model.addAttribute("user",user);
    String check="";
    List<Work>listReviews=workRepository.getWorkByUserProvider(user);
    if(listReviews.size()==0){
        check="false";
    }
    model.addAttribute("check",check);
    model.addAttribute("listReviews",listReviews);
    return "otherProfile";
}
//modify image user
@Transactional
@PostMapping(value="/perfil/{id}/mod",consumes="multipart/from-data")
public String edit(@PathVariable("id")String id,@ModelAttribute User user,
        @RequestParam("img") MultipartFile archivo,ModelMap model,HttpSession session)throws MiException{
    try{
        User sessionUser=(User)session.getAttribute("userSession");
        if(user!=null && sessionUser!=null & (user.getId().equals(sessionUser.getID()||sessionUser.getRole().equals(Roles.ADMIN)))){
            Image image=null;
            if(!archivo.isEmpty()){
                image=imageConverter.convert(archivo);
            }
            if(user.getId().equals(sessionUser.getId())){
                session.setAttribute("userSession",userService.nidifyUser(id,user,image,false));
            }else{
                userService.modifyUser(id,user,image,false);
            }
            if(sessionUser.getRole().equals(Roles.ADMIN)){
            return "redirect:/admin/dashboard";
        }
        }
    }catch(MiException e){
        e.printStackTrace();
    }
    return "redirect:/home";
}

//modify user
@Transactional
@PostMapping(value="/perfil/{id}/change",consumes="multipart/from-data")
public String editModify(@PathVariable("id") String id,@ModelAttribute User user,
        @RequestParam("img")MultipartFile archivo, ModelMap model,HttpSession session)throws MiException,IOException{
    try{
        Image defaultImage=imageService.GetByName("customer-avatar.png");
        User sessionUser=(User)session.getAttribute("userSession");
        if(user!=null && sessionUser!=null && (user.getId().equals(sessionUser.getId()))){
        Image image=null;
        if(!archivo.isEmpty()){
            image=imageConverter.convert(archivo);
        }
        if(imageService.GetById(sessionUser.getImage()).getName().equals(sessionUser.getId())){
            image=imageService.GetByName("provider-avatar.png");
        }
        sessionUser=userService.modifyUser(id,user,image,false);
        session.setAttibute("userSession",sessionUser);
        
    }
    }catch (MiException e){
        e.printStackTrace();
    }
    return "redirect:/logout";
}
//edit Role
@Transactional
@GetMapping("/perfil/{id}/role")
public String editRole(@PathVariable("id") String id,ModelMap model,HttpSession session)throws MiException{
    User user=userService.getById(id);
    User sessionUser=(User) session.getAttribute("userSession");
    if(sessionUser!=(User)session.getRole().equals(Roles.ADMIN)){
        userService.updateRole(user);
    }
    return "redirect:/admin/dashboard";
}
// update alta user
@Transactional
@GetMapping("/perfil/{id}/alta")
public String editAlta(@PathVariable("id")String id,ModelMap model,HttpSession session)throws MiException{
    User user=userService.getById(id);
    User sessionUser=(User) session.getAttribute("userSession");
    if(sessionUser!=null && sessionUser.getRole().equals(Roles.ADMIN)){
        userService.updateAlta(user);
    }
    return "redirect:/admin/dashboard";
}
//delet user
@Transactional
@PostMapping("/perfil/{id}/del")
public String delete(@PathVariable("id") String id, ModelMap model,HttpSession session)throws MiException{
    User user=userService.getById(id);
    User sessionUser=(User) session.getAttibute("userSession");
    if(user!=null && sessionUser!=null && 
            (user.getId().equals(sessionUser.getId()) || sessionUser.getRole().equals(Roles.ADMIN))){
        userService.deleteUser(id);
     if(user.getId().equals(sessionUser.getId())){
         return "redirect:/logout";
     }else if(sessionUser.getRole().equals(Roles.ADMIN)){
         return "redirect:/admin/dashboard";
     }
    }
    return "redirect:/";
}
@GetMapping("/list")
public String listUsers(ModelMap modelo)throws MiException{
    List<User> users=userService.userList();
    modelo.addAttribute("users",users);
    return "userList";
}
}
