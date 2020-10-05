/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.app.fixMypLACE.service;

import java.util.List;
import net.app.fixMypLACE.dto.Request;

/**
 *
 * @author Tshepo
 */
public interface RequestService {
    
    Request save(Request request);
    List<Request> listRequests();
}
