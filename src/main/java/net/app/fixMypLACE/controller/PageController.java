package net.app.fixMypLACE.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
//import org.imgscalr.Scalr;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import net.app.fixMypLACE.dto.Comment;
import net.app.fixMypLACE.dto.Event;
import net.app.fixMypLACE.dto.Place;
import net.app.fixMypLACE.dto.Post;
import net.app.fixMypLACE.dto.User;
import net.app.fixMypLACE.exceptions.PlaceNotFound;
import net.app.fixMypLACE.service.EventService;
import net.app.fixMypLACE.service.PlaceService;
import net.app.fixMypLACE.service.PostService;
import net.app.fixMypLACE.service.UserService;
import net.app.fixMypLACE.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Tshepo
 */

@Controller
public class PageController {

	private final UserService userService;
	private final PostService postService;
	private final PlaceService placeService;
	private final EventService eventService;

	@Autowired
	public PageController(UserService userService, PlaceService placeService,
			PostService postService, EventService eventService) {
		this.userService = userService;
		this.postService = postService;
		this.placeService = placeService;
		this.eventService = eventService;
	}

	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("title", "Home");
		modelAndView.setViewName("/index");
		return modelAndView;
	}

	@RequestMapping(value = { "/about" }, method = RequestMethod.GET)
	public ModelAndView about() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("title", "About us");
		modelAndView.setViewName("/about");

		return modelAndView;
	}

	@RequestMapping(value = { "/faq" }, method = RequestMethod.GET)
	public ModelAndView faq() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("title", "Frequently Asked Questions");
		modelAndView.setViewName("/faq");
		return modelAndView;
	}

	@RequestMapping(value = { "/events" }, method = RequestMethod.POST)
	public ModelAndView event(Event event) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("title", "Ziwaphi");
		eventService.save(event);
		modelAndView.setViewName("/event");
		return modelAndView;
	}

	@RequestMapping(value = "/request", method = RequestMethod.GET)
	public ModelAndView request() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("title", "Register your place");
		mv.setViewName("/request");

		return mv;
	}

	@RequestMapping(value = { "/privacy" }, method = RequestMethod.GET)
	public ModelAndView privacy() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("title", "Privacy");
		modelAndView.setViewName("/privacy");
		return modelAndView;
	}

	@RequestMapping(value = "/forgot-password", method = RequestMethod.GET)
	public ModelAndView password() {
		ModelAndView model = new ModelAndView();
		model.addObject("title", "Forgot Password");
		// User user = userService.findPasswordByEmail(email);
		// model.addObject("password",userService.findPasswordByEmail(email));
		model.setViewName("/password");
		return model;
	}

	@GetMapping("/home")
	public String home(Principal p, @RequestParam(defaultValue = "0") int page,
			Model model) {
		Comment comment = new Comment();
		Optional<User> user = userService.findByUsername(p.getName());
		model.addAttribute("comment", comment);
		Page<Post> posts = postService.findAllOrderedByDatePageable(page);
		model.addAttribute("count", postService.listComments());
		Pager pager = new Pager(posts);

		model.addAttribute("title", "Welcome " + p.getName().toUpperCase());
		model.addAttribute("pager", pager);

		return "/home";
	}

	@RequestMapping(value = { "/search" }, method = RequestMethod.GET)
	public ModelAndView gotoMunicipality() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("title", "Search A Province");
		modelAndView.setViewName("/places.1");
		return modelAndView;
	}

	// Searching Places by province

	@RequestMapping(value = { "/find-place", "province" }, method = RequestMethod.GET)
	public ModelAndView findplaces(
			@RequestParam(value = "search", required = false) String province)
			throws PlaceNotFound {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("search",
				placeService.listStudentsBySurname(province));
		List<Place> products = (List<Place>) placeService
				.listStudentsBySurname(province);
		if (products == null || products.isEmpty()) {
			throw new PlaceNotFound();
		}
		modelAndView.addObject("title", "Find a Place");
		modelAndView.setViewName("/places.1");
		return modelAndView;
	}

	// Showing Single municipality
	@RequestMapping(value = "/show/place/{id}", method = RequestMethod.GET)
	public ModelAndView showSingleProduct(@PathVariable Long id,
			Principal principal, @RequestParam(defaultValue = "0") int page)
			throws PlaceNotFound {
		Post post = new Post();
		Optional<User> user = userService.findByUsername(principal.getName());
		ModelAndView mv = new ModelAndView();
		Place place = placeService.get(id);
		mv.addObject("municipality", place);
		mv.addObject("title", place.getName());
		Page<Post> posts = postService.findByPlaceOrderedByDatePagable(place,
				page);
		Pager pager = new Pager(posts);
		mv.addObject("pager", pager);

		if (user.isPresent()) {
			if (post == null) {
				mv.setViewName("/municipality");
			} else {
				post.setPlace(place);
				post.setUser(user.get());
				mv.addObject("post", post);
				// postService.save(post);
			}
		}

		mv.setViewName("/municipality");
		return mv;
	}

	@RequestMapping(value = "/post/place/{id}", method = RequestMethod.POST)
	public String newPost(Principal principal, @PathVariable Long id,
			Post post, Model model, @RequestParam(defaultValue = "0") int page) {

		Optional<User> user = userService.findByUsername(principal.getName());
		Place place = placeService.get(id);// getting municipality

		if (user.isPresent()) {
			// = new Post();
			post.setUser(user.get());
			post.setPlace(place);
			model.addAttribute("post", post);

			model.addAttribute("municipality", place);

			postService.save(post);
			Page<Post> posts = postService.findByPlaceOrderedByDatePagable(
					place, page);

			Pager pager = new Pager(posts);
			model.addAttribute("pager", pager);

			return "redirect:/show/place/" + place.getId();

		} else {
			return "/municipality";
		}
	}

	// //post to municipality +id
	// @RequestMapping(value = "/post/place/{id}",method = RequestMethod.POST)
	// public ModelAndView saveSinglePostg(@Valid Post post,
	// @PathVariable Long id,
	// Principal principal,
	// BindingResult bindingResult,
	// @RequestParam(defaultValue = "0") int page)
	// {
	// ModelAndView mv = new ModelAndView();
	//
	// System.out.print("p created");
	// Optional<User> user = userService.findByUsername(principal.getName());
	// Place tshepo = placeService.get(id);//getting municipality
	//
	// System.out.println("municipality present"+tshepo.getName());
	//
	// mv.addObject("municipality", tshepo);//municipality object
	//
	// mv.addObject("title", "Issues For "+tshepo.getName());//page title
	// Page<Post> posts = postService.findByPlaceOrderedByDatePagable(tshepo,
	// page);//getting municipality posts
	// Pager pager = new Pager(posts);
	// mv.addObject("pager", pager);
	//
	// if (user.isPresent())
	// {
	// System.out.println("ATTatching post properties");
	//
	// post.setPlace(tshepo);
	// post.setUser(user.get());
	// postService.save(post);
	// System.out.println("Post saved"+post);
	// mv.addObject("post", post);
	// // postService.save(post);
	// mv.addObject("successMessage",
	// " Post added successfully : --> "+post.getBody()+" "+post.getId().longValue());
	//
	// }
	//
	// mv.setViewName("/municipality");
	// return mv;
	// }

	// PlaceNotFound ExceptionHandler
	@ExceptionHandler(PlaceNotFound.class)
	public ModelAndView handleError(HttpServletRequest req,
			PlaceNotFound exception) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("message", exception.getMessage());
		mav.addObject("exception", exception);
		mav.addObject("title", "Province not available!");
		mav.addObject("url", req.getRequestURL() + "?" + req.getQueryString());

		mav.addObject("errorTitle", "Sorry please try again");

		mav.addObject("errorDescription",
				"The province you are looking for is not registered with us right now!");
		mav.setViewName("/placeNotFound");
		return mav;
	}

	@RequestMapping(value = "/newPost/{id}", method = RequestMethod.GET)
	public String newPosty(Principal principal, @PathVariable Long id,
			Model model, @RequestParam(defaultValue = "0") int page) {

		Optional<User> user = userService.findByUsername(principal.getName());
		Place place = placeService.get(id);
		if (user.isPresent()) {
			Post post = new Post();
			post.setUser(user.get());
			post.setBody(post.getBody());
			model.addAttribute("municipality", place);
			model.addAttribute("title", place.getName());
			Page<Post> posts = postService.findByPlaceOrderedByDatePagable(
					place, page);
			Pager pager = new Pager(posts);
			model.addAttribute("pager", pager);

			return "/municipality";

		} else {
			return "/error";
		}
	}

	@RequestMapping(value = "/newPost/{id}", method = RequestMethod.POST)
	public String createNewPost(@Valid Post post, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "/municipality";
		} else {
			postService.save(post);
			return "redirect:/blog/" + post.getUser().getUsername();
		}
	}

	@RequestMapping(value = "/403")
	public ModelAndView accessDenied() {
		ModelAndView mv = new ModelAndView("/403");
		mv.addObject("errorTitle", "Aha! Caught You.");
		mv.addObject("errorDescription",
				"You are not authorized to view this page!");
		mv.addObject("title", "403 Access Denied");
		return mv;
	}

}
