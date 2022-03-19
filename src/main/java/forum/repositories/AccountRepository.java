package forum.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import forum.entities.AccountEntity;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    @Query(value = "SELECT * FROM account WHERE id not in ?1 ORDER BY RAND(), RAND(), RAND()", nativeQuery = true)
    Page<AccountEntity> random(List<Long> blackList, Pageable pageable);

    @Query(value = "SELECT * FROM account WHERE login like %?1% or email like %?1% or username like %?1%", nativeQuery = true)
    public Page<AccountEntity> filtered(String filter, Pageable pageable);

    public Page<AccountEntity> findByLoginContaining(String login, Pageable pageable);

    AccountEntity findByLoginAndPassword(String login, String password);

    AccountEntity findByLogin(String login);

    AccountEntity findByEmail(String email);

    boolean existsByLogin(String login);

    boolean existsByEmail(String email);

    boolean existsByLoginAndPassword(String login, String password);
}
