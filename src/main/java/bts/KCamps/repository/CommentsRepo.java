package bts.KCamps.repository;

import bts.KCamps.model.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface CommentsRepo extends CrudRepository<Comment, Long> {
    Set<Comment> findAllByCampId(Long campId);
}
