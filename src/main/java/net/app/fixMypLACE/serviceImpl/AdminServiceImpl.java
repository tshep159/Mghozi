package net.app.fixMypLACE.serviceImpl;

import net.app.fixMypLACE.dto.User;
import net.app.fixMypLACE.repository.AdminRepository;
import net.app.fixMypLACE.repository.RoleRepository;
import net.app.fixMypLACE.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("adminService")
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Iterable<User> listUsers() {
		return userRepository.findAll();
	}

	@Override
	public Optional<User> getByUsername(String username) {
		return null;
	}

	@Override
	public User getUserById(Long id) {
		return userRepository.findOne(id);
	}

	@Override
	public void saveUser(User user) {
		userRepository.save(user);
	}

	@Override
	public void updateUser(User user) {
		userRepository.save(user);
	}

	@Override
	public long TotalUsers() {
		return userRepository.count();
	}

	@Override
	public User get(Long id) {
		return (User) userRepository.findOne(id);
	}

}
