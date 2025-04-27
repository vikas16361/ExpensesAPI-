package com.vikas.TrackerAPI.controller;

import com.vikas.TrackerAPI.entity.User;
import com.vikas.TrackerAPI.model.JwtResponse;
import com.vikas.TrackerAPI.model.LoginModel;
import com.vikas.TrackerAPI.model.UserModel;
import com.vikas.TrackerAPI.repository.UserRepository;
import com.vikas.TrackerAPI.security.CustomUserDetailsService;
import com.vikas.TrackerAPI.service.UserService;
import com.vikas.TrackerAPI.util.JwtTokenUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthController {
    @Autowired
    private UserService userService;

    //private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    //private AuthenticationManager authenticationManager;
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/home")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginModel login) throws Exception {
        //User user=	userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
		/*authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword()) );
		SecurityContextHolder.getContext().setAuthentication( authenticationManager.authenticate());*/

        final UserDetails user = customUserDetailsService.loadUserByUsername(login.getEmail());
        authenticate(login.getEmail(),login.getPassword());
        //SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities()));
        //we need to generate jwt token
        final String token =jwtTokenUtil.generateToken(user);

        return new ResponseEntity< JwtResponse>(new JwtResponse(token),HttpStatus.OK);
    }

    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        }
        catch (DisabledException e) {
            throw new Exception("user not found");
        }catch (BadCredentialsException e){
            throw new Exception("Bad Credential");
        }
    }


    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody UserModel user) {
        return new ResponseEntity<User>(userService.createUser(user), HttpStatus.CREATED);
    }
}
