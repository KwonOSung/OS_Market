package ShoppingMall.hello.Service;

import ShoppingMall.hello.Repository.BoardRepository;
import ShoppingMall.hello.entity.Board;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public Board save(Board board) {
        board.setType("Free");
        return boardRepository.save(board);
    }

    public List<Board> getFreeBoardList() {
        return boardRepository.findByTypeOrderByIdDesc("Free");
    }

    @Transactional
    public Board getPostAndIncreaseView(Long id) {
        Board post = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        post.setViewCount(post.getViewCount() + 1);
        return post;
    }
}
