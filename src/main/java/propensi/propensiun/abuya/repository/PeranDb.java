package propensi.propensiun.abuya.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import propensi.propensiun.abuya.model.PeranModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeranDb extends JpaRepository<PeranModel, Long> {
        List<PeranModel> findAll();

        Optional<PeranModel> findById(Long id);

}
