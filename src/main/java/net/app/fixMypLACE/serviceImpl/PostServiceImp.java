package net.app.fixMypLACE.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import net.app.fixMypLACE.dto.Comment;
import net.app.fixMypLACE.dto.Place;
import net.app.fixMypLACE.dto.Post;
import net.app.fixMypLACE.dto.User;
import net.app.fixMypLACE.repository.CommentRepository;
import net.app.fixMypLACE.repository.PostRepository;
import net.app.fixMypLACE.service.PostService;

@Service
public class PostServiceImp implements PostService {

	private final PostRepository postRepository;
	private final CommentRepository cRepo;

	@Autowired
	public PostServiceImp(PostRepository postRepository, CommentRepository cRepo) {
		this.postRepository = postRepository;
		this.cRepo = cRepo;
	}

	@Override
	public Optional<Post> findForId(Long id) {
		return postRepository.findById(id);
	}

	@Override
	public Post save(Post post) {
		return postRepository.saveAndFlush(post);
	}

	@Override
	public Page<Post> findByUserOrderedByDatePageable(User user, int page) {
		return postRepository.findByUserOrderByCreateDateDesc(user,
				new PageRequest(subtractPageByOne(page), 5));
	}

	@Override
	public Page<Post> findByPlaceOrderedByDatePagable(Place place, int page) {
		return postRepository.findByPlaceOrderByCreateDateDesc(place,
				new PageRequest(subtractPageByOne(page), 5));
	}

	@Override
	public Page<Post> findAllOrderedByDatePageable(int page) {
		return postRepository.findAllByOrderByCreateDateDesc(new PageRequest(
				subtractPageByOne(page), 5));
	}

	@Override
	public void delete(Post post) {
		postRepository.delete(post);
	}

	private int subtractPageByOne(int page) {
		return (page < 1) ? 0 : page - 1;
	}

	@Override
	public long listComments() {
		return cRepo.count();
	}

}
