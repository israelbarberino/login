package br.com.library.userprofile.infrastructure.repositories;

import br.com.library.userprofile.core.domain.entity.UserProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfileEntity, Long> {

}
