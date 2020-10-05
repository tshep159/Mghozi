package net.app.fixMypLACE.serviceImpl;

import net.app.fixMypLACE.dto.Comment;
import net.app.fixMypLACE.repository.CommentRepository;
import net.app.fixMypLACE.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImp implements CommentService {

	private final CommentRepository commentRepository;

	@Autowired
	public CommentServiceImp(CommentRepository commentRepository) {
		this.commentRepository = commentRepository;
	}

	@Override
	public Comment save(Comment comment) {
		return commentRepository.saveAndFlush(comment);
	}

	@Override
	public long findById(Long id) {
		return commentRepository.listPostComments(id);
	}

	// @Override
	// public Long listComment(Long id) {
	// return commentRepository.listPostComments();
	// }

	@Override
	public Long listComment(Long id) {
		return commentRepository.listPostComments(Long.MIN_VALUE);
	}

	@Override
	public Long listComments() {
		return commentRepository.listPostComments(Long.MIN_VALUE);
	}
}
