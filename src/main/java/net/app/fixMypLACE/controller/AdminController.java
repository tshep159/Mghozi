package net.app.fixMypLACE.controller;

import java.io.IOException;
import net.app.fixMypLACE.dto.*;
import net.app.fixMypLACE.service.*;
//import net.app.fixMypLACE.util.MsgPager;
import net.app.fixMypLACE.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "admin")
public class AdminController {

	@Autowired
	AdminService userService;

	@Autowired
	UserService us;
	@Autowired
	EventService eventService;

	@Autowired
	PostService postService;
        @Autowired
        PlaceService placeService;
        
        @Autowired
        SuggestionService service;
        
        @Autowired
        RequestService rs;
	
        @RequestMapping(value = "/all/users", method = RequestMethod.GET)
	public ModelAndView allUsers(@RequestParam(defaultValue = "0") int page) {
		ModelAndView modelAndView = new ModelAndView();
		Page<Post> posts = postService.findAllOrderedByDatePageable(page);
		Pager pager = new Pager(posts);
		modelAndView.addObject("pager", pager);// getting posts on homepage
		modelAndView.addObject("users", userService.listUsers());

		List<Event> documents = eventService.findAll();
		modelAndView.addObject("documents", documents);
		modelAndView.addObject("total", userService.TotalUsers());
                
		modelAndView.addObject("title", "All users");
                 modelAndView.addObject("place", new Place());
                 List<Request> requests = rs.listRequests();
                 modelAndView.addObject("requests",requests);
                 
                 
		modelAndView.setViewName("/admin/users");
		return modelAndView;
	}

	@RequestMapping("/user/{id}")
	public ModelAndView showUser(@PathVariable Long id) {
		ModelAndView model = new ModelAndView();
		model.addObject("user", userService.getUserById(id));
		model.addObject("title", "Show User");
		model.addObject("post", postService.findForId(id));
                 model.addObject("place", new Place());
		model.setViewName("/admin/userShow");
		return model;
	}

	@RequestMapping("/user/edit/{id}")
	public ModelAndView edit(@PathVariable Long id) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("user", userService.getUserById(id));
		modelAndView.addObject("post", postService.findForId(id));
		  modelAndView.addObject("place", new Place());
		modelAndView.setViewName("/admin/userform");
		return modelAndView;
	}

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public ModelAndView saveUser(@Valid User user, BindingResult bindingResult) {
		ModelAndView model = new ModelAndView();
		model.addObject("user");
		if (bindingResult.hasErrors()) {
			System.out.print(bindingResult.getAllErrors());
			model.setViewName("/admin/userform");
		} else {
			userService.updateUser(user);
			List<Event> documents = eventService.findAll();
			model.addObject("documents", documents);
			model.addObject("users", userService.listUsers());
			 model.addObject("place", new Place());
                        model.setViewName("/admin/users");
		}
		return model;
	}
        /**
         * Activate and Deactivate User
         * @param id
         * @param page
         * @return 
         */
	
	@RequestMapping(value = "/activate/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView check(@PathVariable Long id,
			@RequestParam(defaultValue = "0") int page) {

		ModelAndView mv = new ModelAndView();
		Page<Post> posts = postService.findAllOrderedByDatePageable(page);
		Pager pager = new Pager(posts);
		mv.addObject("pager", pager);// getting posts on homepage
		User webUser = userService.get(id);
		boolean isActive = webUser.getActive();
		webUser.setActive(!isActive);
		userService.updateUser(webUser);
		List<Event> documents = eventService.findAll();
		mv.addObject("documents", documents);
		mv.addObject("title", "User Activation & Deactivation");
		mv.addObject("users", userService.listUsers());
		mv.addObject("total", userService.TotalUsers());
		mv.addObject(
				"adminMessage",
				(isActive) ? webUser.getFirst_name()
						+ " Has Been Dectivated Successfully!" : webUser
						.getFirst_name() + " Has Been Activated Successfully");
                 mv.addObject("place", new Place());
		mv.setViewName("/admin/users");
		return mv;
	}
        /**
         * Adding event /document
         * @param model
         * @param principal
         * @return 
         */
	@RequestMapping(value = { "/add-document" }, method = RequestMethod.GET)
	public String addDocuments(ModelMap model, Principal principal) {
		Optional<User> user = us.findByUsername(principal.getName());
		model.addAttribute("user", user);

		FileBucket fileModel = new FileBucket();
		model.addAttribute("fileBucket", fileModel);

		List<Event> documents = eventService.findAll();
		model.addAttribute("documents", documents);
                 model.addAttribute("place", new Place());
		return "/admin/users";
	}

	@RequestMapping(value = { "/add-document" }, method = RequestMethod.POST)
	public String uploadDocument(@Valid FileBucket fileBucket, ModelMap model,
			Principal principal) throws IOException {

		System.out.println("Fetching file");

		Optional<User> user = us.findByUsername(principal.getName());
		model.addAttribute("user", user);
                model.addAttribute("place", new Place());
		saveDocument(fileBucket, user);

		return "/admin/users";

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
        /**
         * Searching Event
         * @return 
         */
	@RequestMapping(value = { "event/search" }, method = RequestMethod.GET)
	public ModelAndView gotoMunicipality() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("title", "Search for a town");
		modelAndView.setViewName("/event.1");
		return modelAndView;
	}
        /**
         * Deleting document
         * @param docId
         * @return 
         */
	@RequestMapping(value = { "/delete-document/{docId}" }, method = RequestMethod.GET)
	public String deleteDocument(@PathVariable Long docId) {
		eventService.deleteById(docId);
		return "redirect:/admin/all/users";
	}
        
        
          
    @RequestMapping(value="/add/place", method=RequestMethod.GET)
    public ModelAndView showPlacePage(){
        ModelAndView mv = new ModelAndView();
        List<Place> documents = placeService.listPlaces();
	mv.addObject("documents", documents);
        mv.addObject("title", "Add a place");
        mv.addObject("place", new Place());
        mv.setViewName("/request");
        return mv;
    }
    
    @RequestMapping(value="/add/place", method=RequestMethod.POST)
    public String addPlace(Place place){
        ModelAndView mv = new ModelAndView();
        mv.addObject("title", "Add a place");
        placeService.save(place);
           List<Place> documents = placeService.listPlaces();
        mv.addObject("documents", documents);
        List<Request> requests = rs.listRequests();
          mv.addObject("requests",requests);
        mv.setViewName("/admin/users");
        return "redirect:/add/place?success";
    }
//"redirect:/new/story";
    @RequestMapping(value="/add/place?success")
    public ModelAndView addPlaceSuccess(){
        ModelAndView mv = new ModelAndView();
        List<Place> documents = placeService.listPlaces();
        mv.addObject("documents", documents);
        mv.addObject("adminMessage",  "Your place has been successfully added.");
        mv.addObject("title", "Add a place");
        List<Request> requests = rs.listRequests();
          mv.addObject("requests",requests);
        mv.addObject("place", new Place());
        mv.setViewName("/request");
        return mv;
    }
        
      @RequestMapping(value="/all/suggestions")
      public ModelAndView sugg(){
          
          ModelAndView mv = new ModelAndView();
          List<Request> requests = rs.listRequests();
          mv.addObject("requests",requests);
          List<Suggestion> suggestions = service.listSuggestions();
          mv.addObject("suggestions",suggestions );
          mv.setViewName("");
          return mv;
      }
     
      
       @RequestMapping(value="/all/place/requests")
      public ModelAndView placeRequests(){
          
          ModelAndView mv = new ModelAndView();
          List<Request> requests = rs.listRequests();
          mv.addObject("requests",requests);
          mv.setViewName("/admin/users");
          return mv;
      }
        
      
      
        

}
