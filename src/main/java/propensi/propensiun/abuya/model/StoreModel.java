package propensi.propensiun.abuya.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="store")
// @Table(name = "admin", uniqueConstraints = {
// @UniqueConstraint(columnNames = "username"),
// @UniqueConstraint(columnNames = "email")
// })
public class StoreModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name="storeName", nullable = false, unique = true)
    @Size(min = 5, message = "Nama Gerai harus minimal 5 karakter")
    private String storeName;
//    private Integer managerId;

    @NotNull
    @Column(name="storeAddr", nullable=false)
    @Size(min = 7, message = "Alamat harus minimal 7 karakter")
    private String storeAddr;

    @NotNull
    @Column(name="storeAddrLink", nullable=false)
    private String storeAddrLink;

    @NotNull
    @Column(name="storePhone", nullable=false, unique = true)
    @Pattern(regexp = "\\d{10,13}", message = "Nomor telefon harus memiliki 10-13 digit")
    private String storePhone;
//    private Integer rating;
//    private List<FeedbackModel> feedbackList;

}
