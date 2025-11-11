package ShoppingMall.hello.Repository;

import ShoppingMall.hello.entity.Board;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @EntityGraph(attributePaths = {"member"})
    List<Board> findByTypeOrderByIdDesc(String type);

}
