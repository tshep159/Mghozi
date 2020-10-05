package net.app.fixMypLACE.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import net.app.fixMypLACE.dto.Event;
import net.app.fixMypLACE.dto.FileBucket;
import net.app.fixMypLACE.dto.Post;
//import net.app.fixMypLACE.dto.Place;
import net.app.fixMypLACE.dto.User;
import net.app.fixMypLACE.exceptions.PlaceNotFound;
import net.app.fixMypLACE.service.AdminService;
import net.app.fixMypLACE.service.EventService;
import net.app.fixMypLACE.service.PostService;
import net.app.fixMypLACE.service.UserService;
import net.app.fixMypLACE.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Tshepo
 */

@Controller
public class EventController {

	@Autowired
	EventService eventService;
	@Autowired
	UserService userService;
	@Autowired
	PostService postService;

	@Autowired
	AdminService us;

	@RequestMapping(value = { "/download-document/{docId}" }, method = RequestMethod.GET)
	public String downloadDocument(@PathVariable Long docId,
			HttpServletResponse response) throws IOException {
		Event document = eventService.findById(docId);
		response.setContentType(document.getType());
		response.setContentLength(document.getContent().length);
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ document.getName() + "\"");

		FileCopyUtils.copy(document.getContent(), response.getOutputStream());

		return "redirect:/add-document";// +userId;
	}

	@RequestMapping(value = { "/add-document" }, method = RequestMethod.GET)
	public String addDocuments(ModelMap model, Principal principal) {
		Optional<User> user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);

		FileBucket fileModel = new FileBucket();
		model.addAttribute("fileBucket", fileModel);

		List<Event> documents = eventService.findAll();
		model.addAttribute("documents", documents);

		return "/managedocuments";
	}

	@RequestMapping(value = { "/add-document" }, method = RequestMethod.POST)
	public String uploadDocument(@Valid FileBucket fileBucket, ModelMap model,
			Principal principal) throws IOException {

		System.out.println("Fetching file");

		Optional<User> user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);

		saveDocument(fileBucket, user);

		return "redirect:/add-document";

	}

	private void saveDocument(FileBucket fileBucket, Optional<User> user)
			throws IOException {

		Event document = new Event();

		MultipartFile multipartFile = fileBucket.getFile();

		document.setName(multipartFile.getOriginalFilename());
		document.setDescription(fileBucket.getDescription());
		document.setDate(fileBucket.getDate());
		document.setNormalPrice(fileBucket.getNormalPrice());
		document.setPlace(fileBucket.getPlace());
		document.setTown(fileBucket.getTown());
		document.setVipPrice(fileBucket.getVipPrice());
		document.setCell(fileBucket.getCell());
		document.setClubName(fileBucket.getClubName());
		document.setEmail(fileBucket.getEmail());
		document.setType(multipartFile.getContentType());
		document.setContent(multipartFile.getBytes());
		document.setUser(user.get());
		eventService.save(document);
	}

	private void saveEvent(FileBucket fileBucket, Optional<User> user)
			throws IOException {

		Event document = new Event();

		MultipartFile multipartFile = fileBucket.getFile();

		document.setName(multipartFile.getOriginalFilename());
		document.setDescription(fileBucket.getDescription());
		document.setType(multipartFile.getContentType());
		document.setContent(multipartFile.getBytes());
		document.setUser(user.get());
		eventService.save(document);
	}

	@RequestMapping(value = { "event/search" }, method = RequestMethod.GET)
	public ModelAndView gotoMunicipality() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("title", "Search for an event");
		modelAndView.setViewName("/event.1");
		return modelAndView;
	}

	// Searching Places by town

	@RequestMapping(value = { "/find-event", "town" }, method = RequestMethod.GET)
	public ModelAndView findplaces(
			@RequestParam(value = "search", required = false) String town)
			throws PlaceNotFound {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("search",
				eventService.listStudentsBySurname(town));
		List<Event> products = (List<Event>) eventService
				.listStudentsBySurname(town);
		if (products == null || products.isEmpty()) {
			throw new PlaceNotFound();
		}
		modelAndView.addObject("title", "Find an event!");
		modelAndView.setViewName("/event.1");
		return modelAndView;
	}

	// Showing Single event
	@RequestMapping(value = "/show/event/{id}", method = RequestMethod.GET)
	public ModelAndView showSingleProduct(@PathVariable Long id,
			Principal principal, @RequestParam(defaultValue = "0") int page)
			throws PlaceNotFound {
		Post post = new Post();
		Optional<User> user = userService.findByUsername(principal.getName());
		ModelAndView mv = new ModelAndView();
		Event event = eventService.get(id);
		event.setViwes(event.getViwes() + 1);
		eventService.save(event);

		mv.addObject("event", event);
		mv.addObject("title", event.getClubName());

		if (user.isPresent()) {
			if (post == null) {
				mv.setViewName("/municipalityEvent");
			} else {

			}
		}

		mv.setViewName("/municipalityEvent");
		return mv;
	}

	@ExceptionHandler(PlaceNotFound.class)
	public ModelAndView handleError(HttpServletRequest req,
			PlaceNotFound exception) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("message", exception.getMessage());
		mav.addObject("exception", exception);
		mav.addObject("title", "No events available!");
		mav.addObject("url", req.getRequestURL() + "?" + req.getQueryString());

		mav.addObject("errorTitle", "Sorry please try again");

		mav.addObject("errorDescription",
				"There are no events in the town you have searched!");
		mav.setViewName("/eventNotFound");
		return mav;
	}

	// //Activate and Deactivate User
	@RequestMapping(value = "/activate/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView check(@PathVariable Long id,
			@RequestParam(defaultValue = "0") int page) {

		ModelAndView mv = new ModelAndView();
		Page<Post> posts = postService.findAllOrderedByDatePageable(page);
		Pager pager = new Pager(posts);
		mv.addObject("pager", pager);// getting posts on homepage
		Event event = eventService.get(id);
		boolean isActive = event.isActive();

		event.setActive(!isActive);
		eventService.updateUser(event);
		List<Event> documents = eventService.findAll();
		mv.addObject("documents", documents);
		mv.addObject("title", "User Activation & Deactivation");
		mv.addObject("users", us.listUsers());
		mv.addObject("total", us.TotalUsers());
		mv.addObject("adminMessage", (isActive) ? event.getClubName()
				+ " Has Been Dectivated Successfully!" : event.getClubName()
				+ " Has Been Activated Successfully");

		mv.setViewName("/admin/users");
		return mv;
	}

}
