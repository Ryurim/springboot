package org.fullstack4.service;

import org.fullstack4.dto.BoardReplyDTO;
import org.fullstack4.dto.PageRequestDTO;
import org.fullstack4.dto.PageResponseDTO;

public interface BoardReplyServiceIf {
    int regist(BoardReplyDTO replyDTO);

    PageResponseDTO<BoardReplyDTO> getListOfReply(int bbs_idx, PageRequestDTO pageRequestDTO);

    void delete(int idx);
}
