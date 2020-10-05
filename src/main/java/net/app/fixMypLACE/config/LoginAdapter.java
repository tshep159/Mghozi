package net.app.fixMypLACE.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;

import net.app.fixMypLACE.service.UserService;

@Configuration
public class LoginAdapter implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {
    
    
    private final UserService userService;
    @Autowired
    public LoginAdapter(UserService userService) {
        this.userService = userService;
    }
    
    
    @Override
    public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event) {
        userService.updateLastLogin(event.getAuthentication().getName());
    }

}
