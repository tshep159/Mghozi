package net.app.fixMypLACE.service;

import net.app.fixMypLACE.dto.User;

import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

	Optional<User> findByUsername(String username);

	Optional<User> findByEmail(String email);

	User save(User user);

	User findPasswordByEmail(String email);

	long userNo();

	Iterable<User> listStudentsBySurname(String addressLineOne);

	long totalNormalUsers();

	public User get(Long id);

	public void updateLastLogin(String username);

	public User getUserById(Long id);

	public void updateUser(User user);

	public User findById(Long userId);

	public UserDetails loadUserByUsername(String username);

	public String createResetPasswordToken(User user, Boolean save);

	public Boolean resetPassword(User user);

	public void autoLogin(String username);

	User getLoggedInUser();

	public User resetActivation(String email);

	public void updateProfilePicture(User user, String profilePicture);

	public void updateUser(String username, User newData);

}
