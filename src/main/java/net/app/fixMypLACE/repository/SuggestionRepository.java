/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.app.fixMypLACE.repository;

import net.app.fixMypLACE.dto.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Tshepo
 */
public interface SuggestionRepository extends JpaRepository<Suggestion, Long>{
    
}
