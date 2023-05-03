package com.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comment_like")
public class CommentLikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @Column(name = "profile_id")
    private ProfileEntity profile;
    @ManyToOne
    @Column(name = "comment_id")
    private CommentEntity comment;
    @Column(name = "created_date")
    private LocalDateTime createdDate=LocalDateTime.now();
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private
    //    id,profile_id,comment_id,created_date,status,
}
