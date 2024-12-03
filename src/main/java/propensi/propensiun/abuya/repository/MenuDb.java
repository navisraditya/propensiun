package propensi.propensiun.abuya.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import propensi.propensiun.abuya.model.MenuModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MenuDb extends JpaRepository<MenuModel, UUID> {


    List<MenuModel> findByKategori(MenuModel.Kategori kategori);

    @Query("SELECT COUNT(m) > 0 FROM MenuModel m WHERE LOWER(m.nama) = LOWER(:nama) ")
    boolean existsByNamaIgnoreCase(String nama);


    Optional<MenuModel> findByNama(String nama);
}
