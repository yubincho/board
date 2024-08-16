package com.example.board1.entity;


import com.example.board1.entity.constant.UserRole;
import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@Table(name = "users", indexes = {
        @Index(columnList = "email", unique = true),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
@Builder
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private String userId;

    @Column(nullable = false)
    private String userPassword;

    @Column(length = 100)
    private String email;

    @Column(length = 100)
    private String nickname;

    private String memo;

    @Enumerated(EnumType.STRING)
    private UserRole role;    // 유저 role

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<UserRole> roleSet = new HashSet<>();

    private boolean fromSocial;   // 소셜 로그인 여부

    @ToString.Exclude
    @OneToMany(mappedBy = "user")
    private List<Great> greats;  // 좋아요 목록


    @ToString.Exclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Post> posts;

    @ToString.Exclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image profileImage;

    // Security
    public User(String username, String password, String auth) {
        this.email = username;
        this.userPassword = password;
    }


    // role 추가 메서드
    public void addUserRole(UserRole role) {
        roleSet.add(role);
    }


}
