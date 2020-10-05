/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.app.fixMypLACE.controller;

import net.app.fixMypLACE.dto.Suggestion;
import net.app.fixMypLACE.service.SuggestionService;
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
public class SuggestionController {
    
    private final SuggestionService service;
    
    @Autowired
    public SuggestionController(SuggestionService service) {
        this.service = service;
    }
    
    /**
     *
     * @param suggestion
     * @return
     */
    @RequestMapping(value="/add/suggestion", method=RequestMethod.GET)
    public ModelAndView showSuggestion(){
        ModelAndView mv = new ModelAndView();
        mv.addObject("title", "Add Suggestion!");
        mv.addObject("suggestion" ,new Suggestion());
        mv.setViewName("/suggestion");
        return mv;
    }

    @RequestMapping(value="/add/suggestion", method=RequestMethod.POST)
    public String addSuggestion(Suggestion suggestion ){
        ModelAndView mv = new ModelAndView();
        mv.addObject("title", "Add Suggestion!");
        service.save(suggestion);
   
        return "redirect:/add/suggestion";
    }

}
