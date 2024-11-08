package propensi.propensiun.abuya.model;

import java.time.LocalDate;
import java.util.Set;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "promo")
public class PromoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uuid", updatable = false, nullable = false)
    private Integer uuid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "startDate", nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate startDate;

    @Column(name = "endDate", nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate endDate;

    @Column(name = "is_valid", nullable = false)
    private boolean isValid;

    // @ManyToOne(fetch = FetchType.EAGER)
    // @JoinColumn(name = "store_id", nullable = false)
    // private StoreModel store;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "promo_store",
        joinColumns = @JoinColumn(name = "promo_id"),
        inverseJoinColumns = @JoinColumn(name = "store_id")
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<StoreModel> storeList;

}
