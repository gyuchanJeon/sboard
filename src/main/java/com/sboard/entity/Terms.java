package com.sboard.entity;

import com.sboard.dto.TermsDTO;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "terms")
public class Terms {

    @Id
    private int tid;

    @Lob
    @Column(length = 20000)
    private String terms;

    @Lob
    @Column(length = 20000)
    private String privacy;

    public TermsDTO toDTO() {
        return TermsDTO.builder()
                .tid(tid)
                .terms(terms)
                .privacy(privacy)
                .build();
    }

}