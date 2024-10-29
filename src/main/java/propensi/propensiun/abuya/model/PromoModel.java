package propensi.propensiun.abuya.model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class PromoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uuid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "desc", nullable = true)
    private String desc;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "startDate", nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date startDate;

    @Column(name = "endDate", nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date endDate;

    @Column(name = "isValid", nullable = false)
    private boolean isValid;

    @Column(name = "storeList", nullable = false)
    private List<StoreModel> storeList;
}
