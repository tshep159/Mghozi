/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.app.fixMypLACE.serviceImpl;

import java.util.List;
import net.app.fixMypLACE.dto.Place;
import net.app.fixMypLACE.repository.PlaceRepository;
import net.app.fixMypLACE.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Tshepo
 */

@Service
public class PlaceServiceImpl implements PlaceService {

	@Autowired
	PlaceRepository placeRepository;

	@Override
	public Iterable<Place> listStudentsBySurname(String province) {
		return placeRepository.findByProvince(province);
	}

	@Override
	public Place get(Long id) {
		return placeRepository.findOne(id);
	}

    @Override
    public Place save(Place place) {
       return placeRepository.save(place);
    }

    @Override
    public List<Place> listPlaces() {
        return placeRepository.findAll();
    }

}
