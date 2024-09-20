package com.sboard.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TermsDTO {

    private int tid;
    private String terms;
    private String privacy;

}
