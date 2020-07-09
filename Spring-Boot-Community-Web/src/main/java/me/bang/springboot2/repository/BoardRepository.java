package me.bang.springboot2.repository;

import me.bang.springboot2.domain.Board;
import me.bang.springboot2.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Board findByUser(User user);
}
