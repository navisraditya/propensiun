package propensi.propensiun.abuya.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "menu", uniqueConstraints = @UniqueConstraint(columnNames = "nama"))
public class MenuModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // UUID akan di-generate secara otomatis
    private UUID uuid;

    @NotNull
    @Size(min = 5, message = "Nama menu harus memiliki minimal 5 karakter")
    @Column(name = "nama", nullable = false, unique = true)
    private String nama;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "kategori", nullable = false)
    private Kategori kategori;

    @NotNull
    @Size(min = 1, message = "Deskripsi tidak boleh kosong")
    @Column(name = "deskripsi", nullable = false, length = 1000)
    private String deskripsi;

    @NotNull
    @Column(name = "gambar", nullable = false)
    private String gambar;

    public enum Kategori {
        MAKANAN,
        MINUMAN,
        SNACK,
        PAKET
    }
}
