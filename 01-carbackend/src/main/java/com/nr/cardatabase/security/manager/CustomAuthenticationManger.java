package com.nr.cardatabase.security.manager;

import com.nr.cardatabase.entities.User;
import com.nr.cardatabase.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CustomAuthenticationManger implements AuthenticationManager {

  private UserService userService;
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  public Authentication authenticate(Authentication authentication)
    throws AuthenticationException {
    User user = userService.getUser(authentication.getName());
    if (
      !bCryptPasswordEncoder.matches(
        authentication.getCredentials().toString(),
        user.getPassword()
      )
    ) {
      throw new BadCredentialsException("Wrong Password");
    }
    return new UsernamePasswordAuthenticationToken(
      authentication.getName(),
      user
    );
  }
}
