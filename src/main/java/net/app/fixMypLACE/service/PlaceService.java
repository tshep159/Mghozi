/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.app.fixMypLACE.service;

import java.util.List;
import net.app.fixMypLACE.dto.Place;

/**
 *
 * @author Tshepo
 */
public interface PlaceService {

	Iterable<Place> listStudentsBySurname(String province);
        Place save(Place place);
        List<Place> listPlaces();
	public Place get(Long id);

}
