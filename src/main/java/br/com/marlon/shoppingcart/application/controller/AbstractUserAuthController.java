package br.com.marlon.shoppingcart.application.controller;

import br.com.marlon.shoppingcart.application.security.JwtTokenProvider;
import br.com.marlon.shoppingcart.domain.enums.RoleEnum;
import br.com.marlon.shoppingcart.domain.exception.UnauthorizedException;
import br.com.marlon.shoppingcart.domain.model.Role;
import br.com.marlon.shoppingcart.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

abstract public class AbstractUserAuthController extends AbstractController {

    @Autowired
    private JwtTokenProvider tokenProvider;

    protected User getPrincipal(Authentication authentication) {
        return (User) authentication.getPrincipal();
    }

    protected User validateAccess(String userId, Authentication authentication) {
        User principal = getPrincipal(authentication);
        if (isNotAdmin(authentication) && !userId.equals(principal.getId())) {
            throw new UnauthorizedException("Access Denied");
        }

        return principal;
    }

    protected boolean isNotAdmin(Authentication authentication) {
        Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();
        return !roles.stream().anyMatch( r -> RoleEnum.ADMIN.name().equalsIgnoreCase(r.getAuthority()));
    }

    protected String createToken(User user) {

        List<String> roles = user
                .getRoles()
                .stream()
                .map(Role::getDescription)
                .collect(Collectors.toList());

        return tokenProvider.createToken(user.getUsername(), roles);
    }

}
