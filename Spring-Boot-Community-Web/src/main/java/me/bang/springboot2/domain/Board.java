package me.bang.springboot2.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.bang.springboot2.enums.BoardType;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table
public class Board implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String title;

    @Column
    private String subTitle;

    @Column
    private String content;

    @Column
    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    @Column
    LocalDateTime createDate;

    @Column
    LocalDateTime updatedDate;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder
    public Board(String title, String subTitle, String content, BoardType boardType,
                 LocalDateTime createDate, LocalDateTime updateeDate, User user) {
        this.title = title;
        this.subTitle = subTitle;
        this.content = content;
        this.boardType = boardType;
        this.createDate = createDate;
        this.updatedDate = updateeDate;
        this.user = user;
    }
}
