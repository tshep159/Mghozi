/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.app.fixMypLACE.repository;

import java.util.List;
import net.app.fixMypLACE.dto.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Tshepo
 */
public interface EventRepository extends JpaRepository<Event, Long> {

	public List<Event> findAllByUserId(Long id);

	public Iterable<Event> findByTown(@Param("town") String town);

	public List<Event> findAllByActiveTrue();

}
