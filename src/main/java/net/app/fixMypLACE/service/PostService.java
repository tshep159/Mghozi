package net.app.fixMypLACE.service;

import org.springframework.data.domain.Page;

import java.util.Optional;
import net.app.fixMypLACE.dto.Comment;
import net.app.fixMypLACE.dto.Place;
import net.app.fixMypLACE.dto.Post;
import net.app.fixMypLACE.dto.User;

public interface PostService {

	Optional<Post> findForId(Long id);

	Post save(Post post);

	/**
	 * Finds a {@link Page) of {@link Post} of provided user ordered by date
	 */
	Page<Post> findByUserOrderedByDatePageable(User user, int page);

	/**
	 *
	 * @param place
	 * @param page
	 * @return
	 */
	Page<Post> findByPlaceOrderedByDatePagable(Place place, int page);

	/**
	 * Finds a {@link Page) of all {@link Post} ordered by date
	 */
	Page<Post> findAllOrderedByDatePageable(int page);

	void delete(Post post);

	public long listComments();
}
