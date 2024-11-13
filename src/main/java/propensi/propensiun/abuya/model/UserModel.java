package propensi.propensiun.abuya.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uuid;

    @NotNull(message = "Company ID cannot be empty.")
    @Column(name = "companyid", nullable = false)
    private Integer companyid;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne(fetch= FetchType.EAGER, optional = false)
    @JoinColumn(name="id_role", referencedColumnName = "UUID", nullable = false)
    @OnDelete(action= OnDeleteAction.CASCADE)
    @JsonIgnore
    private PeranModel peran;

    @NotNull(message = "Phone number question cannot be empty.")
    @Column(name = "phoneNumber", nullable = true)
    private String phoneNumber;

    @NotNull(message = "Security question cannot be empty.")
    @Column(name = "securityQuestion", nullable = true)
    private String securityQuestion;

    @NotNull(message = "Security answer cannot be empty.")
    @Column(name = "securityAnswer", nullable = true)
    private String securityAnswer;

    @OneToOne(mappedBy = "storeManager", fetch=FetchType.LAZY)
    private StoreModel store;
}
