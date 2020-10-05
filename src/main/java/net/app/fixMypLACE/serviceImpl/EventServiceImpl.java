/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.app.fixMypLACE.serviceImpl;

import java.util.List;
import net.app.fixMypLACE.dto.Event;
import net.app.fixMypLACE.repository.EventRepository;
import net.app.fixMypLACE.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Tshepo
 */

@Service
public class EventServiceImpl implements EventService {

	private final EventRepository eventRepository;

	@Autowired
	public EventServiceImpl(EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}

	@Override
	public Event save(Event event) {

		return eventRepository.save(event);
	}

	@Override
	public void list() {
		eventRepository.findAll();
	}

	@Override
	public List<Event> listEvents() {
		return eventRepository.findAllByActiveTrue();
	}

	@Override
	public List<Event> findAllByUserId(Long id) {
		return eventRepository.findAllByUserId(id);
	}

	@Override
	public void deleteById(Long docId) {

		eventRepository.delete(docId);
	}

	@Override
	public Event findById(Long id) {
		return eventRepository.getOne(id);
	}

	@Override
	public List<Event> findAll() {
		return eventRepository.findAll();

	}

	@Override
	public Iterable<Event> listStudentsBySurname(String town) {

		return eventRepository.findByTown(town);
	}

	@Override
	public Event get(Long id) {
		return eventRepository.getOne(id);
	}

	@Override
	public void updateUser(Event event) {
		eventRepository.save(event);
	}

}
