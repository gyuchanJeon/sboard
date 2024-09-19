package com.sboard.entity;

import com.sboard.dto.TermsDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private String terms;
    private String privacy;

    public TermsDTO toDTO() {
        return TermsDTO.builder()
                .terms(terms)
                .privacy(privacy)
                .build();
    }

}