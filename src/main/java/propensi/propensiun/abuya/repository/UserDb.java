package propensi.propensiun.abuya.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import propensi.propensiun.abuya.model.UserModel;

import java.util.List;

@Repository
public interface UserDb extends JpaRepository<UserModel, Integer> {
    UserModel findByUsername(String username);

    List<UserModel> findAll();

    @Query("SELECT u FROM UserModel u WHERE u.peran.name IN :roles")
    List<UserModel> findByRoleNames(@Param("roles") List<String> roles);

    @Query("SELECT u FROM UserModel u WHERE u.peran.name = 'Store Manager'")
    List<UserModel> findStoreManagers();

    @Query("SELECT u FROM UserModel u WHERE u.peran.name = 'Marketing'")
    List<UserModel> findMarketing();

    @Query("SELECT u FROM UserModel u WHERE u.peran.name = 'Chief Operating Officer'")
    List<UserModel> findCOO();
}
