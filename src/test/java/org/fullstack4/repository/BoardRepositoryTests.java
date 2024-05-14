package org.fullstack4.repository;

import lombok.extern.log4j.Log4j2;
import org.fullstack4.domain.BoardEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.stream.IntStream;

@Log4j2
@SpringBootTest
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void testGetNow() {
        String now = boardRepository.getNow();
        log.info("===========================================");
        log.info("now : " + now);
        log.info("===========================================");
    }

    @Test
    public void testRegist() {
        log.info("===========================================");
        log.info("BoardRepositoryTests >> testRegist START");
        IntStream.rangeClosed(0, 10)
                .forEach(i -> {
                    BoardEntity bbs = BoardEntity.builder()
                            .user_id("test")
                            .title("테스트 제목 " + i)
                            .content("테스트 내용 " + i)
                            .display_date(new SimpleDateFormat("yyyy-MM-dd").format(
                                    new Date(2024-1900, 4, (i%10 == 0 ? 1 : i%10))).toString()
                            )
                            .build();

                    BoardEntity bbsResult = boardRepository.save(bbs);
                    log.info("result : {}" + bbsResult);
                });
        log.info("BoardRepositoryTests >> testRegist END");
        log.info("===========================================");
    }

    @Test
    public void testView() {
        int idx = 1;
        Optional<BoardEntity> result = boardRepository.findById(idx);
        BoardEntity bbs = result.orElse(null); //없으면 null값 리턴해! 이게 젤 편한 방법임
//        result.get(); // 값이 없으면 NoSuchElementException 발생
//        if (result.isPresent()) { } else { throw new IllegalArgumentException(); }
//        result.orElseThrow(IllegalArgumentException::new);
//        result.orElseThrow(() -> new IllegalArgumentException("no find data"));
//        result.orElseGet(BoardEntity::new);
//        result.ifPresent(b->{log.info("result:{}", b)});
//        이렇게 다양한 방법으로 예외처리 해줘야 한다!
//        트랜잭션이 걸릴 때 여기서 예외 처리를 안해주면 rollback이 안됨.
//        트랜잭션이 제대로 실행되게 하기 위해서 처리해 주는 것임

        log.info("===========================================");
        log.info("BoardRepositoryTests >> testView START");
        log.info("bbs : {} " + bbs);
        log.info("BoardRepositoryTests >> testView END");
        log.info("===========================================");
    }

    @Test
    public void testModify() {
        int idx = 1;
        Optional<BoardEntity> result = boardRepository.findById(idx);
        BoardEntity bbs = result.orElse( // vo dto 매퍼 해준것과 같음!
                BoardEntity.builder()
                        .idx(idx)
                        .user_id("test")
                        .title("제목 수정 1")
                        .content("내용 수정 1")
                        .display_date(
                                 new SimpleDateFormat("yyyy-MM-dd")
                                         .format(new Date()).toString()
                        )
                        .build()
        );

        bbs = BoardEntity.builder()
                .idx(idx)
                .user_id("test")
                .title("제목 수정 1")
                .content("내용 수정 1")
                .display_date(
                        new SimpleDateFormat("yyyy-MM-dd")
                                .format(new Date()).toString()
                )
                .build();

        //옛날엔 이렇게 다 한땀한땀 했었다
        bbs.modify("test", "제목 수정 1", "내용 수정 1", "2024-05-09");
        boardRepository.save(bbs); // 등록, 수정은 다 save

        log.info("===========================================");
        log.info("BoardRepositoryTests >> testModify START");
        log.info("bbs : {} ", bbs);
        log.info("BoardRepositoryTests >> testModify END");
        log.info("===========================================");
    }

    @Test
    public void testDelete() {
        int idx = 1;
        boardRepository.deleteById(idx);
    }

    @Test
    public void testSearch() {
        log.info("===========================================");
        log.info("BoardRepositoryTests >> testSearch START");

        PageRequest pageable = PageRequest.of(0, 40, Sort.by("idx").descending());

        //boardRepository.search(pageable);
        Page<BoardEntity> result = boardRepository.search(pageable);


        log.info("result : {}", result.toList());
        log.info("BoardRepositoryTests >> testSearch END");
        log.info("===========================================");
    }

    @Test
    public void testSearch2() {
        log.info("===========================================");
        log.info("BoardRepositoryTests >> testSearch2 START");

        PageRequest pageable = PageRequest.of(0, 10, Sort.by("idx").descending());
        String[] types = {"t", "c", "u"};
        String search_keyword = "내용";

        boardRepository.search2(pageable, types, search_keyword);


        log.info("BoardRepositoryTests >> testSearch2 END");
        log.info("===========================================");
    }
}
