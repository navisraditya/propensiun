package propensi.propensiun.abuya.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mitra")
public class MitraModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uuid;

    @NotNull
    @Column(name = "name", nullable = true)
    private String name;

    @NotNull(message = "Phone number question cannot be empty.")
    @Column(name = "phoneNumber", nullable = true)
    private String phoneNumber;

    @Column(name = "alasan", nullable = true)
    private String alasan;
}
