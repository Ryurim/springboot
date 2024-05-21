package org.fullstack4.service;

import org.fullstack4.dto.BoardDTO;
import org.fullstack4.dto.BoardListDTO;
import org.fullstack4.dto.PageRequestDTO;
import org.fullstack4.dto.PageResponseDTO;

public interface BoardServiceIf {
    int regist(BoardDTO boardDTO);
    BoardDTO view(int idx);
    void modify(BoardDTO boardDTO);
    void delete(int idx);
    //PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);
    PageResponseDTO<BoardListDTO> list(PageRequestDTO pageRequestDTO);
}
