/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.app.fixMypLACE.serviceImpl;

import java.util.List;
import net.app.fixMypLACE.dto.Request;
import net.app.fixMypLACE.repository.RequestRepository;
import net.app.fixMypLACE.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Tshepo
 */
@Service
public class RequestServiceImpl implements RequestService{

    private final RequestRepository requestRepository;
    @Autowired
    public RequestServiceImpl(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }
    
            
    @Override
    public Request save(Request request) {
    return  requestRepository.save(request);
     }

    @Override
    public List<Request> listRequests() {
        return requestRepository.findAll();
    }
    
}
