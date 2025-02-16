package com.mainproject.wrieating.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private LocalDate birth;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private Integer height;

    @Column(nullable = false)
    private Integer weight;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Activity activity;  //회원의 활동량

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Status status;      //탈퇴유무

    // JWT
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    public enum Activity{
        NONE_ACTIVE("운동을 거의 또는 전혀 안함"), // BMR * 1.2
        LIGHTLY_ACTIVE("가벼운 활동 수준(주1~3일)"), // BMR * 1.375
        MODERATELY_ACTIVE("적당한 활동 수준/적당한 운동(주3~5일)"), // BMR * 1.55
        VERY_ACTIVE("매우 활동적인 수준/스포츠, 격렬한 운동(주6일)"), // BMR * 1.725
        EXTREMELY_ACTIVE("매우 활동적인 수준/매우 힘든 운동 및 육체노동"); // BMR * 1.9

        @Getter
        private String activity;

        Activity(String activity){
            this.activity = activity;
        }
    }

    public enum Status{
        MEMBER_ACTIVE("활동중"),
        MEMBER_QUIT("탈퇴 상태");

        @Getter
        private String status;

        Status(String status){
            this.status = status;
        }
    }
}