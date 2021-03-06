/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.app.fixMypLACE.controller;

import net.app.fixMypLACE.dto.Place;
import net.app.fixMypLACE.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Tshepo
 */

@Controller
public class PlaceController {
    
    private final PlaceService placeService;

    @Autowired
    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }
    
    @RequestMapping(value="add/place", method=RequestMethod.GET)
    public ModelAndView showPlacePage(){
        ModelAndView mv = new ModelAndView();
        mv.addObject("title", "Add a place");
        mv.addObject("place", new Place());
        mv.setViewName("/request");
        return mv;
    }
    
    @RequestMapping(value="add/place", method=RequestMethod.POST)
    public String addPlace(Place place){
        ModelAndView mv = new ModelAndView();
        mv.addObject("title", "Add a place");
        placeService.save(place);
        mv.setViewName("/request");
        return "redirect:/add/place?success";
    }
//"redirect:/new/story";
    @RequestMapping(value="/add/place?success")
    public ModelAndView addPlaceSuccess(){
        ModelAndView mv = new ModelAndView();
        mv.addObject("message", "Your request has been successfully submitted.");
        mv.addObject("title", "Add a place");
        mv.addObject("place", new Place());
        mv.setViewName("/request");
        return mv;
    }
}
