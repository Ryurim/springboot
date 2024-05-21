package org.fullstack4.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "board")
@Table(
        name="tbl_board_reply",
        indexes = {
                @Index(name="idx_tbl_board_reply_bbs_idx", columnList = "board_idx")
        }
)
public class BoardReplyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int idx;

//    @Column(nullable = false)
//    private int board_idx;

    //조인하기 위해 보드 엔티티 객체로 받음
    @ManyToOne(fetch = FetchType.LAZY)
    private BoardEntity board;

    @Column(length = 20, nullable = false)
    private String user_id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 2000, nullable = false)
    private String content;

    @Column(length = 10, nullable = true)
    private String display_date;

    @CreatedDate
    @Column(name="reg_date", updatable = false, columnDefinition = "DATETIME NULL DEFAULT NOW()")
    private LocalDateTime reg_date;

    @LastModifiedDate
    @Column(name="modify_date", nullable = true, insertable = false, updatable = true)
    private LocalDateTime modify_date;

    public void modify(String user_id, String title, String content, String display_date) {
        this.user_id = user_id;
        this.title = title;
        this.content = content;
        this.display_date = display_date;
    }
}
