package org.fullstack4.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardDTO {

    private int idx;
    @NotEmpty
    private String user_id;
    @NotEmpty
    @Size(min=1, max=100)
    private String title;
    @NotEmpty
    private String content;
    private String display_date;
    private LocalDateTime reg_date;
    private LocalDateTime modify_date;
}