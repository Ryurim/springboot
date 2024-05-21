package org.fullstack4.repository.search;

import org.fullstack4.domain.BoardEntity;
import org.fullstack4.dto.BoardListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface BoardSearch {
    Page<BoardEntity> search(Pageable pageable);

    Page<BoardEntity> search2(Pageable pageable, String[] types, String search_keyword);

    Page<BoardListDTO> searchWithReplyCnt(Pageable pageable, String[] types, String search_keryword);
}
