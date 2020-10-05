package net.app.fixMypLACE.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;
import net.app.fixMypLACE.dto.Comment;
import net.app.fixMypLACE.dto.Post;
import net.app.fixMypLACE.dto.User;
import net.app.fixMypLACE.service.CommentService;
import net.app.fixMypLACE.service.EventService;
import net.app.fixMypLACE.service.PostService;
import net.app.fixMypLACE.service.UserService;
import net.app.fixMypLACE.util.Pager;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PostController {

	private final PostService postService;
	private final UserService userService;
	private final CommentService commentService;
	@Autowired
	EventService eventService;

	@Autowired
	public PostController(PostService postService, UserService userService,
			CommentService commentService) {
		this.postService = postService;
		this.userService = userService;
		this.commentService = commentService;
	}
        /**
         * This method handles user post and also display posts 
         * Displays events
         * @param principal
         * @param page
         * @param model
         * @return 
         */
	@RequestMapping(value = "/new/story", method = RequestMethod.GET)
	public String newPost(Principal principal,
			@RequestParam(defaultValue = "0") int page, Model model) {
		Page<Post> posts = postService.findAllOrderedByDatePageable(page);
		Pager pager = new Pager(posts);
		Comment comment = new Comment();
		Optional<User> user = userService.findByUsername(principal.getName());
		model.addAttribute("pager", pager);
		model.addAttribute("count", postService.listComments());
		if (user.isPresent()) {
			Post post = new Post();
			post.setUser(user.get());
			post.setViews(post.getViews() - 1);
			model.addAttribute("events", eventService.listEvents());
			model.addAttribute("post", post);
			model.addAttribute("title", "New Story");
			model.addAttribute("count", postService.listComments());

			model.addAttribute("comment", comment);
			// model.addAttribute("successMessage", "Story Added Successfully");
			return "/postForm";

		} else {
			return "/error";
		}
	}
        
        /**
         * Validates the post
         * @param post
         * @param bindingResult
         * @param m
         * @return 
         */
	@RequestMapping(value = "/new/story", method = RequestMethod.POST)
	public String createNewstory(@Valid Post post, BindingResult bindingResult,
			Model m) {
		m.addAttribute("successMessage", "Story Added Successfully");
		if (bindingResult.hasErrors()) {
			return "/postForm";
		} else {

			m.addAttribute("count", postService.listComments());
			m.addAttribute("successMessage", "Story Added Successfully");
			m.addAttribute("title", "New Story");
			postService.save(post);

			return "redirect:/new/story"; // + post.getUser().getUsername();
		}
	}
        
        /**
         * Allows user to edit their stories
         * @param id
         * @param principal
         * @param model
         * @return 
         */

	@RequestMapping(value = "/editPost/{id}", method = RequestMethod.GET)
	public String editPostWithId(@PathVariable Long id, Principal principal,
			Model model) {

		Optional<Post> optionalPost = postService.findForId(id);

		if (optionalPost.isPresent()) {
			Post post = optionalPost.get();

			if (isPrincipalOwnerOfPost(principal, post)) {
				model.addAttribute("post", post);
				model.addAttribute("count", postService.listComments());
				return "/postForm";
			} else {
				return "/403";
			}

		} else {
			return "/error";
		}
	}
/**
 * Gets a single post by its id
 * @param id
 * @param principal
 * @param model
 * @return 
 */
	@RequestMapping(value = "/post/{id}", method = RequestMethod.GET)
	public String getPostWithId(@PathVariable Long id, Principal principal,
			Model model) {

		Optional<Post> optionalPost = postService.findForId(id);

		if (optionalPost.isPresent()) {

			Post post = optionalPost.get();
			post.setViews(post.getViews() + 1);
			postService.save(post);

			model.addAttribute("post", post);
			model.addAttribute("title", post.getBody());
			model.addAttribute("count", postService.listComments());
			if (isPrincipalOwnerOfPost(principal, post)) {
				model.addAttribute("username", principal.getName());
			}

			return "/post";

		} else {
			return "/error";
		}
	}
/**
 * Allows user to support the post 
 * @param id
 * @param p
 * @param model
 * @return 
 */
	@RequestMapping("/support/post/{id}")
	public String support(@PathVariable Long id, Principal p, Model model) {
            
		Optional<Post> optionalPost = postService.findForId(id);

		if (optionalPost.isPresent()) {

			Post post = optionalPost.get();
			post.setLikes(post.getLikes() + 1);
			postService.save(post);

			model.addAttribute("post", post);
			model.addAttribute("title", post.getBody());
			model.addAttribute("count", postService.listComments());
			if (isPrincipalOwnerOfPost(p, post)) {
				model.addAttribute("username", p.getName());
			}
		}

		return "redirect:/home";
	}
/**
 * Allows user to delete their post
 * @param id
 * @param principal
 * @return 
 */
	@RequestMapping(value = "/post/{id}", method = RequestMethod.DELETE)
	public String deletePostWithId(@PathVariable Long id, Principal principal) {

		Optional<Post> optionalPost = postService.findForId(id);

		if (optionalPost.isPresent()) {
			Post post = optionalPost.get();

			if (isPrincipalOwnerOfPost(principal, post)) {
				postService.delete(post);
				return "redirect:/home";
			} else {
				return "/403";
			}

		} else {
			return "/error";
		}
	}
/**
 * Ensures that the user is the owner of the post
 * @param principal
 * @param post
 * @return 
 */
	private boolean isPrincipalOwnerOfPost(Principal principal, Post post) {
		return principal != null
				&& principal.getName().equals(post.getUser().getUsername());
	}
}
