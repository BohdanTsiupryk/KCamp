package bts.KCamps.repository;

import bts.KCamps.model.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentsRepo extends CrudRepository<Comment, Long> {
}
