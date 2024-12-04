package propensi.propensiun.abuya.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
@Table(name = "feedback")
public class FeedbackModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uuid", updatable = false, nullable = false)
    private Integer uuid;

    // Foreign key untuk store
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "store_id", referencedColumnName = "uuid", nullable = false)
    private StoreModel store;

    // Foreign key untuk user
    @ManyToOne(fetch = FetchType.LAZY, optional = true, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", referencedColumnName = "uuid", nullable = true)
    private UserModel user;

    @Column(name = "pelayanan", nullable = false)
    private float pelayanan;

    @Column(name = "menu", nullable = false)
    private float menu;

    @Column(name = "fasilitas", nullable = false)
    private float fasilitas;

    @Column(name = "saran", nullable = true) 
    private String saran;

    // Automatically captures the time of submission
    @CreationTimestamp
    @Column(name = "waktu_pengisian", nullable = false, updatable = false)
    private LocalDateTime waktuPengisian;

    // User-selected visiting date with format dd-MMM-yyyy
    @DateTimeFormat(pattern = "dd-MMM-yyyy")
    @Column(name = "tanggal_kunjungan", nullable = false)
    private LocalDate tanggalKunjungan;
}
