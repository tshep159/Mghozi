/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.app.fixMypLACE.service;

import java.util.List;
import net.app.fixMypLACE.dto.Event;

/**
 *
 * @author Tshepo
 */
public interface EventService {

	Event save(Event event);

	public void list();

	public List<Event> listEvents();

	public List<Event> findAllByUserId(Long id);

	public void deleteById(Long docId);

	public Event findById(Long id);

	public List<Event> findAll();

	public Object listStudentsBySurname(String province);

	public Event get(Long id);

	public void updateUser(Event event);

}
