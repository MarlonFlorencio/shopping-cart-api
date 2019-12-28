package br.com.marlon.shoppingcart.domain.services;

import br.com.marlon.shoppingcart.domain.enums.RoleEnum;
import br.com.marlon.shoppingcart.domain.exception.ResourceNotFoundException;
import br.com.marlon.shoppingcart.domain.model.Role;
import br.com.marlon.shoppingcart.domain.model.User;
import br.com.marlon.shoppingcart.domain.repository.RoleRepository;
import br.com.marlon.shoppingcart.domain.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;

import static br.com.marlon.shoppingcart.domain.util.ValidadeUtil.validateIsBlank;
import static br.com.marlon.shoppingcart.domain.util.ValidadeUtil.validateIsNull;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    UserRepository repository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = findByEmail(username);
        return user.orElseThrow( () -> new UsernameNotFoundException("Username " + username + " not found"));
    }

    public Optional<User> findByEmail(String email) throws UsernameNotFoundException {
        return repository.findOneByEmail(email);
    }

    @Transactional
    public synchronized User createGeneralUser(String email, String password, String name, RoleEnum role) {
        synchronized (email) {
            checkNewGeneralUser(email, password, name, role);
            User user = buildUser(email, password, name, role);
            repository.save(user);
            return user;
        }
    }

    private void checkNewGeneralUser(String email, String password, String name, RoleEnum role) {
        validateIsBlank(email, "Email is required");
        validateIsBlank(password, "Password is required");
        validateIsBlank(name, "Name is required");
        validateIsNull(role, "Role is required");
        checkIfEmailIsAvailable(email);
    }

    private void checkIfEmailIsAvailable(String email) {
        if (findByEmail(email).isPresent()) {
            throw new BadCredentialsException("User with email " + email + " already exists");
        }
    }

    private User buildUser(String email, String password, String name, RoleEnum role) {

        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(password));

        Role userRole = roleRepository.findByDescription(role.name()).get();
        user.setRoles(Arrays.asList(userRole));

        return user;
    }

    public User findById(String id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No user found for this ID"));
    }

    @Transactional
    public synchronized User updateGeneralUser(User user) {

        checkExisGeneralUser(user);

        User updatedUser = findById(user.getId());

        updatedUser.setName(user.getName());

        if (!user.getEmail().equalsIgnoreCase(updatedUser.getEmail())) {
            checkIfEmailIsAvailable(user.getEmail());
        }

        if (StringUtils.isNotBlank(user.getPassword())) {
            updatedUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        repository.save(updatedUser);
        return updatedUser;

    }

    private void checkExisGeneralUser(User user) {
        validateIsBlank(user.getId(), "Id is required");
        validateIsBlank(user.getEmail(), "Email is required");
        validateIsBlank(user.getName(), "Name is required");
    }

}