package org.fullstack4.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.domain.BoardReplyEntity;
import org.fullstack4.dto.BoardReplyDTO;
import org.fullstack4.dto.PageRequestDTO;
import org.fullstack4.dto.PageResponseDTO;
import org.fullstack4.repository.search.BoardReplyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class BoardReplyServiceImpl implements BoardReplyServiceIf{

    private final BoardReplyRepository boardReplyRepository;
    private final ModelMapper modelMapper;

    @Override
    public int regist(BoardReplyDTO replyDTO) {
        BoardReplyEntity reply = modelMapper.map(replyDTO, BoardReplyEntity.class);
        int result = boardReplyRepository.save(reply).getIdx();
        return result;
    }

    @Override
    public PageResponseDTO<BoardReplyDTO> getListOfReply(int bbs_idx, PageRequestDTO pageRequestDTO) {
        PageRequest pageable = PageRequest.of(
                (pageRequestDTO.getPage() < 0 ? 0 : pageRequestDTO.getPage()-1), //페이지 번호. 0부터 진행되는 놈이라 이렇게 해줄게
                pageRequestDTO.getPage_size(),  //페이지 사이즈
                Sort.by("idx").ascending()  //정렬 방법
        );
        Page<BoardReplyEntity> replyResult = boardReplyRepository.listOfBoardReply(bbs_idx, pageable);

        int total_count = (replyResult != null ? (int)replyResult.getTotalElements() : 0);

        List<BoardReplyDTO> dtoList = null;
        if (replyResult != null) {
            dtoList = replyResult.getContent().stream()
                    .map(reply->modelMapper.map(reply, BoardReplyDTO.class))
                    .collect(Collectors.toList());
        }

        return PageResponseDTO.<BoardReplyDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total_count((int)replyResult.getTotalElements())
                .build();
    }

    @Override
    public void delete(int idx) {
        boardReplyRepository.deleteById(idx);
    }
}
