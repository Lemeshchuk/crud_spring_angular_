package com.nix.lemeshuk.dao;

import com.nix.lemeshuk.dto.UserDto;
import com.nix.lemeshuk.model.Role;
import com.nix.lemeshuk.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.nix.lemeshuk.util.Constant.ENTITY_IS_INVALID;
import static com.nix.lemeshuk.util.Constant.ENTITY_REMOVED;
import static com.nix.lemeshuk.util.Constant.ENTITY_SAVED;
import static com.nix.lemeshuk.util.Constant.ENTITY_UPDATED;
import static com.nix.lemeshuk.util.Constant.USER_AUTHORITY_NAME;

@Slf4j
@Service
@RequiredArgsConstructor
@ComponentScan("com.nix.lemeshuk")
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleServiceImpl roleServiceImpl;

    public final String REMOVED_USER_BY_USERNAME = "The user (%s) was deleted (%s).";

    @Transactional
    public boolean create(User user) {
        if (isAddedUserValid(user)) {

            userRepository.save(user);
            log.info(String.format(ENTITY_SAVED, user));

            return true;
        }

        log.error(String.format(ENTITY_IS_INVALID, user));
        return false;
    }

    @Transactional
    public boolean update(User user) {
        if (isEditedUserValid(user)) {
            userRepository.save(user);
            log.info(String.format(ENTITY_UPDATED, user));

            return true;
        }
        log.error(String.format(ENTITY_IS_INVALID, user));

        return false;
    }

    @Transactional
    public void delete(User user) {
        userRepository.delete(user);
        log.info(String.format(ENTITY_REMOVED, user));
    }

    @Transactional
    public void deleteById(Long id) {
        Optional<User> currentUser = findById(id);
        currentUser.ifPresent(this::delete);
        log.info(String.format(REMOVED_USER_BY_USERNAME, currentUser, id));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findByUsername(String login) {
        return userRepository.findByUsername(login);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).get();
    }

    public User toUser(UserDto userDto) {

        if (userDto.getRoleName() == null) {
            Role userRole = roleServiceImpl.findByName(USER_AUTHORITY_NAME).get();
            userDto.setRoleName(userRole.getName());
        }

        Role userRole = roleServiceImpl.findByName(userDto.getRoleName()).get();

        if (userDto.getPassword().isEmpty()) {
            User editedUser = userRepository.findByUsername(userDto.getUsername()).get();
            userDto.setPassword(editedUser.getPassword());
        }

        return User.builder()
                .id(userDto.getId())
                .role(userRole)
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .email(userDto.getEmail())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .birthday(userDto.getBirthday())
                .build();
    }


    private boolean isAddedUserValid(User user) {
        return userRepository.findByUsername(user.getUsername()).isEmpty()
                && userRepository.findByEmail(user.getEmail()).isEmpty();
    }

    private boolean isEditedUserValid(User user) {

        Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());

        User userToEdit = optionalUser.get();

        if (user.getEmail().equals(userToEdit.getEmail())) {
            return true;
        } else {
            return userRepository.findByEmail(user.getEmail()).isEmpty();
        }
    }
}
