package net.app.fixMypLACE.service;

import java.util.Optional;
import net.app.fixMypLACE.dto.User;

public interface AdminService {
	public Iterable<User> listUsers();

	Optional<User> getByUsername(String username);

	User getUserById(Long id);

	public void saveUser(User user);

	public void updateUser(User user);

	long TotalUsers();

	public User get(Long id);
}
