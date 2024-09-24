package com.sboard.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;
    private String cate;
    private String title;
    private String content;
    private int comment;
    private int file;
    private int hit;
    private String writer;
    private String regip;

    @CreationTimestamp
    private LocalDateTime rdate;

    // 추가 필드
    @Transient // 엔티티의 속성에서 제외시키는 어노테이션, 테이블의 컬럼 생성을 방지하는 기능
    private String nick;

    /*
        DTO 변환 메서드 대신 ModelMapper 사용
     */

}


