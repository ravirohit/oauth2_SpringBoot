package com.example.springsecuritydemo;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.springsecuritydemo.sscustomimpl.CustomUser;
 
@Controller
public class RestResource 
{
    @GetMapping("/api/users/me")
    public ResponseEntity<UserProfile> profile() 
    {
        //Build some dummy data to return for testing
    	CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = user.getUsername() + "@howtodoinjava.com";
 
        UserProfile profile = new UserProfile();
        profile.setName(user.getUsername());
        profile.setEmail(email);
 
        return ResponseEntity.ok(profile);
    }
    @GetMapping("/api/users/me1")
    public ResponseEntity<Principal> get(final Principal principal) {
        return ResponseEntity.ok(principal);
    }
}
