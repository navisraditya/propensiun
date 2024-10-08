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
import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="peran")
public class PeranModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uuid;

    @NotNull
    @Column(name = "nama", nullable = false)
    private String name;

    @OneToMany(mappedBy="peran", fetch=FetchType.LAZY)
    @OnDelete(action= OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<UserModel> userRole;
}
