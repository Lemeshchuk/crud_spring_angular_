package com.nix.lemeshuk.dao;

import com.nix.lemeshuk.dao.RoleService;
import com.nix.lemeshuk.exception.FilterProcessingException;
import com.nix.lemeshuk.model.DefaultRolePage;
import com.nix.lemeshuk.model.Role;
import com.nix.lemeshuk.dao.RoleRepository;
import com.nix.lemeshuk.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.nix.lemeshuk.util.Constant.ENTITY_IS_INVALID;
import static com.nix.lemeshuk.util.Constant.ENTITY_REMOVED;
import static com.nix.lemeshuk.util.Constant.ENTITY_SAVED;
import static com.nix.lemeshuk.util.Constant.ENTITY_UPDATED;

@Slf4j
@Service
@ComponentScan("com.nix.lemeshuk")
public class RoleServiceImpl implements RoleService {

    public final String FIND_ROLE_NAME_IN_CONFIG_FILE_ERROR = "A role with this name(%s) was not found in the configuration file!";

    private final RoleRepository roleRepository;
    private List<DefaultRolePage> rolePagesList;

    public RoleServiceImpl(RoleRepository roleRepository, JsonUtil jsonUtil) {
        this.roleRepository = roleRepository;

        rolePagesList = jsonUtil.readListFromJsonFile(ROLE_PAGES_CONFIG_FILENAME);

    }

    private static final String ROLE_PAGES_CONFIG_FILENAME = "rolePages.json";

    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    @Transactional
    public boolean create(Role role) {
        if (isAddedRoleValid(role)) {
            roleRepository.save(role);
            log.info(String.format(ENTITY_SAVED, role));

            return true;
        }
        log.error(String.format(ENTITY_IS_INVALID, role));

        return false;
    }

    @Override
    @Transactional
    public boolean update(Role role) {
        if (isAddedRoleValid(role)) {
            roleRepository.save(role);
            log.info(String.format(ENTITY_UPDATED, role));

            return true;
        }
        log.error(String.format(ENTITY_IS_INVALID, role));

        return false;
    }

    @Override
    @Transactional
    public void remove(Role role) {
        roleRepository.delete(role);
        log.info(String.format(ENTITY_REMOVED, role));
    }

    @Override
    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }

    public String getDefaultRolePage(String userRoleName) {

        DefaultRolePage defaultRolePage = rolePagesList.stream()
                .filter(rp -> rp.getRoleName().equals(userRoleName))
                .findFirst().orElseThrow(() -> new FilterProcessingException(
                        String.format(FIND_ROLE_NAME_IN_CONFIG_FILE_ERROR, userRoleName)));

        return defaultRolePage.getDefaultPage();
    }

    private boolean isAddedRoleValid(Role role) {
        return roleRepository.findByName(role.getName()).isEmpty();
    }
}
