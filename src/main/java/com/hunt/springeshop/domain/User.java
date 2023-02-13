package com.hunt.springeshop.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    private static final String SEQ_NAME = "user_seq";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long id;

    private String name;

    private String password;

    private String email;

    private boolean archive;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Bucket bucket;
    /*Как только мы добавляем юзера у нас сразу же в бд будет код активации. Он будет отправлен по мейлу
    * в случае если отправка успешная, то у юзера в графе активат код будет код. Как юзер подверждает на почте активацию
    * данный активационный код будет уходить у нас из БД, останется null*/
    @Column(name = "activate_code", length = 80)
    private String activateCode;
}
