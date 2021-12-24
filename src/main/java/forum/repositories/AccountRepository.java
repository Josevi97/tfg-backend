package forum.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import forum.entities.AccountEntity;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    boolean existsByLoginAndPassword(String login, String password);

    AccountEntity findByLoginAndPassword(String login, String password);

    AccountEntity findByLogin(String login);

    AccountEntity findByEmail(String email);
}
