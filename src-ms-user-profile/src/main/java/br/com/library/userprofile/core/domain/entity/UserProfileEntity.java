package br.com.library.userprofile.core.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_test")
public class UserProfileEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -2420346134960559062L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "USER_NAME")
    private String name;

    @Column(name = "USER_EMAIL", length = 40, nullable = false, unique = false)
    private String email;

    @Column(name = "USER_PASSWORD", length = 6, nullable = false, unique = false)
    private String password;

    @Column(name = "USER_PHONE")
    private String phone;

    @Column(name = "USER_CPF")
    private String cpf;

    @Column(name = "DATA_CADASTRO", unique = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private String subscribeDate;

}
