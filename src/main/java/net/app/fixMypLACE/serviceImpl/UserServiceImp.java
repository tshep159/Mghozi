package net.app.fixMypLACE.serviceImpl;

import net.app.fixMypLACE.dto.User;
import net.app.fixMypLACE.repository.RoleRepository;
import net.app.fixMypLACE.repository.UserRepository;

import net.app.fixMypLACE.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import net.app.fixMypLACE.App;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserServiceImp implements UserService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	@Value("${app.user.verification}")
	private Boolean requireActivation;
	@Value("${app.secret}")
	private String applicationSecret;

	private static final String USER_ROLE = "ROLE_USER";

	@Autowired
	private HttpSession httpSession;

	@Autowired
	public UserServiceImp(UserRepository userRepository,
			RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User save(User user) {
		// Encode plaintext password
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setActive(true);
		// Set Role to ROLE_USER
		user.setRoles(Collections.singletonList(roleRepository
				.findByRole(USER_ROLE)));
		return userRepository.saveAndFlush(user);
	}

	@Override
	public User findPasswordByEmail(String email) {
		User p = userRepository.findPasswordByEmail(email);
		User u = new User();
		u.getPassword();
		System.out.print(p);

		return p;
	}

	@Override
	public Iterable<User> listStudentsBySurname(String addressLineOne) {
		return userRepository.findByAddressLineOne(addressLineOne);
	}

	@Override
	public long userNo() {
		return userRepository.count();
	}

	@Override
	public long totalNormalUsers() {
		return userRepository.count();
	}

	@Override
	public User get(Long id) {
		return userRepository.getOne(id);
	}

	@Override
	public void updateLastLogin(String username) {
		this.userRepository.updateLastLogin(username);
	}

	@Override
	public User getUserById(Long id) {
		return userRepository.getOne(id);
	}

	@Override
	public void updateUser(User user) {
		userRepository.save(user);
	}

	@Override
	public User findById(Long userId) {
		//
		return userRepository.getOne(userId);
	}

	public String createResetPasswordToken(User user, Boolean save) {
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String resetToken = encoder.encodePassword(user.getEmail(),
				applicationSecret);
		if (save) {
			user.setToken(resetToken);
			this.userRepository.save(user);
		}
		return resetToken;
	}

	public void autoLogin(User user) {
		autoLogin(user.getUsername());
	}

	public final String CURRENT_USER_KEY = "CURRENT_USER";

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		User user = userRepository.findOneByUsernameOrEmail(username, username);

		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		if (requireActivation && !user.getToken().equals("1")) {
			App.log.error("User [" + username
					+ "] tried to login but is not activated");
			throw new UsernameNotFoundException(username
					+ " has not been activated yet");
		}
		httpSession.setAttribute(CURRENT_USER_KEY, user);
		List<GrantedAuthority> auth = AuthorityUtils
				.commaSeparatedStringToAuthorityList(user.getRoles().getClass()
						.getName());

		return new org.springframework.security.core.userdetails.User(
				user.getUsername(), user.getPassword(), auth);
	}

	public void autoLogin(String username) {
		UserDetails userDetails = this.loadUserByUsername(username);
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
				userDetails, null, userDetails.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(auth);
		if (auth.isAuthenticated()) {
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
	}

	public Boolean resetPassword(User user) {
		User u = this.userRepository.findOneByUsername(user.getUsername());
		if (u != null) {
			u.setPassword(encodeUserPassword(user.getPassword()));
			u.setToken("1");
			this.userRepository.save(u);
			return true;
		}
		return false;
	}

	public String encodeUserPassword(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		return passwordEncoder.encode(password);

	}

	public User resetActivation(String email) {
		User u = this.userRepository.findOneByEmail(email);
		if (u != null) {
			createActivationToken(u, true);
			return u;
		}
		return null;
	}

	public String createActivationToken(User user, Boolean save) {
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String activationToken = encoder.encodePassword(user.getUsername(),
				applicationSecret);
		if (save) {
			user.setToken(activationToken);
			this.userRepository.save(user);
		}
		return activationToken;
	}

	public User getLoggedInUser() {
		return getLoggedInUser(false);
	}

	public User getLoggedInUser(Boolean forceFresh) {
		String userName = SecurityContextHolder.getContext()
				.getAuthentication().getName();
		User user = (User) httpSession.getAttribute(CURRENT_USER_KEY);
		if (forceFresh || httpSession.getAttribute(CURRENT_USER_KEY) == null) {
			user = this.userRepository.findOneByUsername(userName);
			httpSession.setAttribute(CURRENT_USER_KEY, user);
		}
		return user;
	}

	public void updateProfilePicture(User user, String profilePicture) {
		this.userRepository.updateProfilePicture(user.getUsername(),
				profilePicture);
	}

	@Override
	public void updateUser(String username, User newData) {
		this.userRepository.updateUser(username, newData.getEmail(),

		newData.getContactNumber());

	}

}
