package propensi.propensiun.abuya.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "store")
public class StoreModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uuid;

    @NotNull
    @Column(name = "storeName", nullable = false, unique = true)
    @Size(min = 5, message = "Nama Gerai harus minimal 5 karakter")
    private String storeName;

    @NotNull
    @Column(name = "storeAddr", nullable = false)
    @Size(min = 7, message = "Alamat harus minimal 7 karakter")
    private String storeAddr;

    @NotNull
    @Column(name = "storeAddrLink", nullable = false, length=10000)
    private String storeAddrLink;

    @NotNull
    @Column(name = "storePhone", nullable = false, unique = true)
    @Pattern(regexp = "\\d{10,13}", message = "Nomor telefon harus memiliki 10-13 digit")
    private String storePhone;

    // New fields for latitude and longitude
    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    // Optional: Method to extract latitude and longitude from storeAddrLink
    public void extractCoordinatesFromLink() {
        String[] parts = storeAddrLink.split("@");
        if (parts.length > 1) {
            String[] coords = parts[1].split(",");
            if (coords.length >= 2) {
                this.latitude = Double.parseDouble(coords[0]);
                this.longitude = Double.parseDouble(coords[1]);
            }
        }
    }
    
    @ManyToMany(mappedBy = "stores")
    private Set<PromoModel> promos = new HashSet<>();
}
