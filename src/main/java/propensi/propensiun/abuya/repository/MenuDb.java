package propensi.propensiun.abuya.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import propensi.propensiun.abuya.model.MenuModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MenuDb extends JpaRepository<MenuModel, UUID> {

    // Mencari menu berdasarkan kategori
    List<MenuModel> findByKategori(MenuModel.Kategori kategori);

    // Memeriksa apakah nama menu sudah ada, tanpa memperhatikan kapitalisasi
    boolean existsByNamaIgnoreCase(String nama);

    // Mencari menu berdasarkan nama, akan mengembalikan Optional untuk menghindari null
    Optional<MenuModel> findByNama(String nama);
}
