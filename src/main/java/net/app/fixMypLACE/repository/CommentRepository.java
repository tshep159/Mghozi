package net.app.fixMypLACE.repository;

import net.app.fixMypLACE.dto.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	@Query("FROM Comment WHERE post_id = :post_id")
	long listPostComments(Long id);
}
