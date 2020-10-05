package net.app.fixMypLACE.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
import net.app.fixMypLACE.dto.Post;
import net.app.fixMypLACE.dto.User;
import net.app.fixMypLACE.service.PostService;
import net.app.fixMypLACE.service.UserService;
import net.app.fixMypLACE.util.Pager;

@Controller
public class BlogController {

    private final UserService userService;

    private final PostService postService;

    @Autowired
    public BlogController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }
    
    /**
     * Handling user post history
     * @param username
     * @param page
     * @param model
     * @return 
     */

    @RequestMapping(value = "/storyline/{username}", method = RequestMethod.GET)
    public String blogForUsername(@PathVariable String username,
                                  @RequestParam(defaultValue = "0") int page,
                                  Model model) {

        Optional<User> optionalUser = userService.findByUsername(username);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Page<Post> posts = postService.findByUserOrderedByDatePageable(user, page);
            Pager pager = new Pager(posts);

            model.addAttribute("pager", pager);
            model.addAttribute("user", user);
            model.addAttribute("title" , optionalUser.get().getUsername()+"'s storylile");
            model.addAttribute("count", postService.listComments());
            return "/posts";

        } else {
            return "/error";
        }
    }
}
