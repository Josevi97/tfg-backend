package forum.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import forum.entities.AccountEntity;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    @Query(value = "SELECT * FROM account ORDER BY RAND(), RAND(), RAND()", nativeQuery = true)
    Page<AccountEntity> random(Pageable pageable);

    boolean existsByLoginAndPassword(String login, String password);

    AccountEntity findByLoginAndPassword(String login, String password);

    AccountEntity findByLogin(String login);

    AccountEntity findByEmail(String email);

    boolean existsByLogin(String login);

    boolean existsByEmail(String email);
}
