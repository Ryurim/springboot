package org.fullstack4.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.domain.BoardEntity;
import org.fullstack4.domain.QBoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Log4j2
public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {

    public BoardSearchImpl() {
        super(BoardEntity.class);
    }

    @Override
    public Page<BoardEntity> search(Pageable pageable) {
       log.info("=========================================");
        log.info("BoardSearchImpl >> search START");
        QBoardEntity qBoard = QBoardEntity.boardEntity; //QBoardEntity 객체 생성
        JPQLQuery<BoardEntity> query = from(qBoard);    //SELECT ... FROM QBoardEntity --> tbl_board 에서 SELECT 하는 것임

//        query.where(qBoard.title.contains("제목"));   //contains는 like와 같음. 실행될 때 like로 변환되서 실행됨
//        query.where(qBoard.title.like("제목"));  //like도 동일하게 동작
//        query.where(qBoard.content.contains("제목")); //여러개 나열해서 쓰면 and로 이어짐

//         묶어서 처리하는 방법은 없을까? --> BooleanBuilder 이용하면 됨!
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.or(qBoard.title.contains("제목"));
        booleanBuilder.or(qBoard.content.contains("제목"));
        query.where(booleanBuilder);


        //paging
        this.getQuerydsl().applyPagination(pageable, query);

        log.info("query : {}", query);

        List<BoardEntity> boards = query.fetch();
//        int total = (int)query.fetchCount(); //얘는 기본 타입이 long 임
        long total = query.fetchCount();

        log.info("boards : {}" , boards);
        log.info("total : {}", total);
        log.info("BoardSearchImpl >> search END");
        log.info("=========================================");

        //return null;
        return new PageImpl<>(boards, pageable, total);
    }

    @Override
    public Page<BoardEntity> search2(Pageable pageable, String[] types, String search_keyword) {
        log.info("=========================================");
        log.info("BoardSearchImpl >> search2 START");

        QBoardEntity qBoard = QBoardEntity.boardEntity;
        JPQLQuery<BoardEntity> query = from(qBoard);

        if ((types != null) && (types.length > 0) && (search_keyword != null && search_keyword.length() > 0)) {
            BooleanBuilder booleanBuilder = new BooleanBuilder();
//            type: t - 제목, c - 내용, u - 사용자ID
            for (String type : types) {
                switch (type) {
                    case "t" :
                        booleanBuilder.or(qBoard.title.like("%" + search_keyword + "%"));
                        break;
                    case "c" :
                        booleanBuilder.or(qBoard.content.like("%" + search_keyword + "%"));
                        break;
                    case "u" :
                        booleanBuilder.or(qBoard.user_id.like("%" + search_keyword + "%"));
                        break;
                }
            }
            query.where(booleanBuilder);
        }
        query.where(qBoard.idx.gt(0)); //idx가 0보다 큰놈 greater than

        //paging
        this.getQuerydsl().applyPagination(pageable, query);

        log.info("query : {}", query);

        List<BoardEntity> boards = query.fetch();
        int total = (int)query.fetchCount(); //얘는 기본 타입이 long 임

        log.info("boards : {}" , boards);
        log.info("total : {}", total);
        log.info("BoardSearchImpl >> search2 END");
        log.info("=========================================");

        return new PageImpl<>(boards, pageable, total);
    }
}
