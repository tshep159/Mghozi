/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.app.fixMypLACE.repository;

import java.io.Serializable;
import net.app.fixMypLACE.dto.Place;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Tshepo
 */
public interface PlaceRepository extends JpaRepository<Place, Long> {

	Iterable<Place> findByProvince(@Param("province") String province);

}
