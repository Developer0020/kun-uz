package com.example.entity;

import com.example.enums.CommentLikeStatus;
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
    @JoinColumn (name = "profile_id",insertable = false, updatable = false)
    private ProfileEntity profile;
    @ManyToOne
    @JoinColumn(name = "comment_id",insertable = false, updatable = false)
    private CommentEntity comment;
    @Column(name = "created_date")
    private LocalDateTime createdDate=LocalDateTime.now();
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CommentLikeStatus status;
}
