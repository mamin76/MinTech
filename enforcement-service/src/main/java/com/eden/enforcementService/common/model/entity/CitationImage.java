package com.eden.enforcementService.common.model.entity;


import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
@Table(name = "citation_image")
@Where(clause = "deleted <> true")
@SQLDelete(sql = "UPDATE {h-schema} CitationImage SET deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
public class CitationImage extends BaseEntity {

    @Id
    @SequenceGenerator(name = "citation_image_seq",
            sequenceName = "citation_image_seq",
            allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "citation_image_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "citation_id")
    private Citation citation;

    @Column(name = "content")
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String content;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "file_name")
    private String fileName;
}
