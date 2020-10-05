package net.app.fixMypLACE.service;

import net.app.fixMypLACE.dto.Comment;

public interface CommentService {

	Comment save(Comment comment);

	long findById(Long id);

	public Long listComment(Long id);

	public Long listComments();

}
