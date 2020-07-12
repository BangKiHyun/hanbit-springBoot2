package me.bang.springboot2.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.bang.springboot2.enums.SocialType;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private String principal;

    @Column
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Column
    private LocalDateTime createDate;

    @Column
    private LocalDateTime updatedDate;

    @Builder
    public User(String name, String password, String email, String principal,
                SocialType socialType, LocalDateTime createDate, LocalDateTime updatedDate) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.principal = principal;
        this.socialType = socialType;
        this.createDate = createDate;
        this.updatedDate = updatedDate;
    }
}
