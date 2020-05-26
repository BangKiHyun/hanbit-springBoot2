package me.bang.springbootweb.repository;

import me.bang.springbootweb.domain.Board;
import me.bang.springbootweb.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Board findByUser(User user);
}
