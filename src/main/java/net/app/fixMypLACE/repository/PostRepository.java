package net.app.fixMypLACE.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import net.app.fixMypLACE.dto.Place;
import net.app.fixMypLACE.dto.Post;
import net.app.fixMypLACE.dto.User;
import org.springframework.data.domain.PageRequest;

public interface PostRepository extends JpaRepository<Post, Long> {
	Page<Post> findByUserOrderByCreateDateDesc(User user, Pageable pageable);

	Page<Post> findAllByOrderByCreateDateDesc(Pageable pageable);

	Optional<Post> findById(Long id);

	public Page<Post> findByPlaceOrderByCreateDateDesc(Place place,
			Pageable pageable);
}
