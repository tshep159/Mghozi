package net.app.fixMypLACE.repository;

import net.app.fixMypLACE.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<User, Long> {


}
