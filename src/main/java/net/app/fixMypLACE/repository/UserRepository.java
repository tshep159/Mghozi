package net.app.fixMypLACE.repository;

import net.app.fixMypLACE.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(@Param("email") String email);

	Optional<User> findByUsername(@Param("username") String username);

	Optional<User> getByUsername(@Param("username") String username);

	User findPasswordByEmail(@Param("email") String email);

	Iterable<User> findByAddressLineOne(
			@Param("addressLineOne") String addressLineOne);

	@Modifying
	@Transactional
	@Query("update User u set u.lastLogin = CURRENT_TIMESTAMP where u.username = ?1")
	int updateLastLogin(String username);

	User findOneByEmail(String email);

	User findOneByUsernameOrEmail(String username, String email);

	User findOneByToken(String token);

	User findOneByUsername(String username);

	@Modifying
	@Transactional
	@Query("update User u set u.profilePicture = ?2 where u.username = ?1")
	int updateProfilePicture(String userName, String profilePicture);

	@Modifying
	@Transactional
	@Query("update User u set u.email = :email, u.contactNumber = :contactNumber where u.username = :username")
	int updateUser(@Param("username") String username,
			@Param("email") String email,
			@Param("contactNumber") String contactNumber);
	// @Param("lastName") String lastName,
	// @Param("phone_number") String phone_number,
	// @Param("confirmNumber") String confirm_number);

	// public void updateUser(String userName, String email, String
	// contactNumber, String contactNumber0);

}
