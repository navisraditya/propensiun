package propensi.propensiun.abuya.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import propensi.propensiun.abuya.model.MitraModel;

@Repository
public interface MitraDb extends JpaRepository<MitraModel, String> {
}
