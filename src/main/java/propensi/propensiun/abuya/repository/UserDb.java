package propensi.propensiun.abuya.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import propensi.propensiun.abuya.model.UserModel;

import java.util.List;

@Repository
public interface UserDb extends JpaRepository<UserModel, Integer> {
    UserModel findByUsername(String username);

    List<UserModel> findAll();
}
