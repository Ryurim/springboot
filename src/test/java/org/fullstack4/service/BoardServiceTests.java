package org.fullstack4.service;

import lombok.extern.log4j.Log4j2;
import org.fullstack4.dto.BoardDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Log4j2
@SpringBootTest
public class BoardServiceTests {
    @Autowired
    private BoardServiceIf boardService;

    @Test
    public void testRegist() {
        log.info("================================================");
        log.info("BoardServiceTests >> testRegist Start");

        log.info("boardService.getClass().getName() : {}", boardService.getClass().getName());

        BoardDTO boardDTO = BoardDTO.builder()
                .user_id("test")
                .title("제목 테스트")
                .content("내용 테스트")
                .display_date("2024-05-13")
                .build();
        int result = boardService.regist(boardDTO);

        log.info("boardDTO : {}", boardDTO);
        log.info("result : {}", result);
        log.info("BoardServiceTests >> testRegist End");
        log.info("================================================");
    }

    @Test
    public void testModify() {
        log.info("================================================");
        log.info("BoardServiceTests >> testModify Start");

        BoardDTO boardDTO = BoardDTO.builder()
                .idx(11)
                .user_id("test")
                .title("제목 테스트 11")
                .content("내용 테스트 11")
                .display_date("2024-05-13")
                .build();
        boardService.modify(boardDTO);

        log.info("boardDTO : {}", boardDTO);
        log.info("BoardServiceTests >> testModify End");
        log.info("================================================");
    }

    @Test
    public void testView() {
        log.info("================================================");
        log.info("BoardServiceTests >> testView Start");

        int idx = 11;
        BoardDTO boardDTO = boardService.view(idx);

        log.info("boardDTO : {}", boardDTO);
        log.info("BoardServiceTests >> testView End");
        log.info("================================================");
    }

    @Test
    public void testDelete() {
        log.info("================================================");
        log.info("BoardServiceTests >> testDelete Start");

        int idx = 12;
        boardService.delete(idx);

        log.info("BoardServiceTests >> testDelete End");
        log.info("================================================");
    }
}
