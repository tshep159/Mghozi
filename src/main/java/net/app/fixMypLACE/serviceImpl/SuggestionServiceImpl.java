/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.app.fixMypLACE.serviceImpl;

import java.util.List;
import java.util.logging.Logger;
import net.app.fixMypLACE.dto.Suggestion;
import net.app.fixMypLACE.repository.SuggestionRepository;
import net.app.fixMypLACE.service.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Tshepo
 */

@Service
public class SuggestionServiceImpl implements SuggestionService{

    private  final SuggestionRepository suggestions;
    @Autowired
    public SuggestionServiceImpl(SuggestionRepository suggestions) {
        this.suggestions = suggestions;
    }
    

    @Override
    public Suggestion save(Suggestion suggestion) {
    return suggestions.save(suggestion);
    }

    @Override
    public List<Suggestion> listSuggestions() {
        return suggestions.findAll();
    }

    @Override
    public void deleteById(Long id) {
         suggestions.delete(id);
    }
    
}
