/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.app.fixMypLACE.controller;

import net.app.fixMypLACE.dto.Place;
import net.app.fixMypLACE.dto.Request;
import net.app.fixMypLACE.service.RequestService;
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
public class RequestController {
    
    private final RequestService service;
    @Autowired
    public RequestController(RequestService service) {
        this.service = service;
    }
    
        @RequestMapping(value="add/your/place", method=RequestMethod.GET)
    public ModelAndView showPlacePage(){
        ModelAndView mv = new ModelAndView();
        mv.addObject("title", "Add a place");
        mv.addObject("place", new Request());
        mv.setViewName("/requestPlace");
        return mv;
    }
    
    @RequestMapping(value="add/your/place", method=RequestMethod.POST)
    public String addPlace(Request r){
        ModelAndView mv = new ModelAndView();
        mv.addObject("title", "Add a place");
        service.save(r);
       // mv.setViewName("/request");
        return "redirect:/add/your/place?success";
    }
//"redirect:/new/story";
    @RequestMapping(value="/add/your/place?success")
    public ModelAndView addPlaceSuccess(){
        ModelAndView mv = new ModelAndView();
        mv.addObject("msg", "Your request has been successfully submitted.");
        mv.addObject("title", "Add a place");
        mv.addObject("place", new Request());
        mv.setViewName("/requestPlace");
        return mv;
    }
    
    
}
