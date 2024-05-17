package org.fullstack4.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Log4j2
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageRequestDTO {
    @Builder.Default
    @Min(1)
    private int total_count=0;
    @Builder.Default
    private int page=1;
    @Builder.Default
    private int page_size=10;
    //여기까지 3개는 꼭 있어야함! 나머지는 연산해서 만들 수 있음
    @Builder.Default
    @Min(1)
    private int total_page=1;
    @Builder.Default
    private int page_skip_count=0;
    @Builder.Default
    private int page_block_size=10;
    @Builder.Default
    private int page_block_start=1;
    @Builder.Default
    private int page_block_end=10;
    private boolean prev_page_flag;
    private boolean next_page_flag;

    private String search_type; //검색타입 (title:제목, content:내용, user_id:사용자ID...)
    private String[] search_types;
    private String search_word;
    private String linkParams;  //리턴 url

    public int getTotal_page() {
        this.total_page = (int)Math.ceil(this.total_count/(double)this.page_size);
        return (this.total_page < 1 ? 1 : this.total_page);
    }

    public int getPage_skip_count() {
        this.page = (this.page < 1 ? 1 : this.page);
        return (this.page-1)*this.page_size;
    }

    public int getPage_block_start() {
//        this.page_block_start = (this.page_block_end <= this.page_block_size ? 1 : this.page_block_end - this.page_block_size + 1);
        this.page_block_start = (int)(Math.ceil(page / (double)page_block_size) -1 ) * page_block_size + 1;
        return this.page_block_start;
    }

    public int getPage_block_end() {
        if (this.total_page < 1) {
            this.page_block_end = 1;
        } else {
            this.page_block_end = (int)Math.ceil(this.page/(double)this.page_block_size)*this.page_block_size;
            this.page_block_end = (this.page_block_end < 1 ? 1 : this.page_block_end);
            this.page_block_end = (this.total_page > this.page_block_end ? this.page_block_end : this.total_page);
        }
        return this.page_block_end;
    }

    public boolean getPrev_page_flag() {
        return (this.page_block_start > 1);
    }

    public boolean getNext_page_flag() {
        return (this.total_page > this.page_block_end);
    }

//    public int getPage_skip_count() { return (this.page -1) * this.page_size;}
    public String[] getSearch_types() {
        if (search_types == null || search_type.isEmpty()) {
            return null;
        }
        return search_type.split("");   //아무것도 안해도 array로 넘어가짐! split이 array 반환하기 때문
    }

    public PageRequest getPageable(String...props) {
        return PageRequest.of(this.page-1, this.page_size, Sort.by(props).descending());     //page-1 왜? 0부터 시작하기 때문에. 파라미터로 들어오는거로 sorting 할게~
    }
    public String getLinkParams() {
        if (this.linkParams == null || this.linkParams.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append("page=" + this.page);
            sb.append("&page_size=" + this.page_size);

            if (search_type != null && !search_type.isEmpty()) {
                sb.append("&search_type=" + this.search_type);
            }
            if (search_word != null && !search_word.isEmpty()) {
                try {
                    sb.append("&search_word=" + URLEncoder.encode(search_word, "UTF-8"));
                } catch(UnsupportedEncodingException e) {

                }
            }
            linkParams = sb.toString();
        }
        return linkParams;
    }


}
