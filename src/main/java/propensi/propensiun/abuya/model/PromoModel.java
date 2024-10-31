package propensi.propensiun.abuya.model;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
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

    @Column(name = "description", nullable = true) // renamed from 'desc'
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

    @ManyToMany
    @JoinTable(name = "promo_store", joinColumns = @JoinColumn(name = "uuid"), inverseJoinColumns = @JoinColumn(name = "id"))
    private Set<StoreModel> storeList;
}
