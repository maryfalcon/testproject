package com.example.testproject;

import com.example.testproject.model.User;
import com.example.testproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class InitDatabase implements ApplicationListener<ContextRefreshedEvent> {

    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public InitDatabase(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (this.userRepository.count() == 0) {
            User user1 = new User();
            user1.setLogin("1111");
            user1.setPassword(passwordEncoder.encode("1111"));
            userRepository.save(user1);
            User user2 = new User();
            user2.setLogin("2222");
            user2.setPassword(passwordEncoder.encode("2222"));
            userRepository.save(user2);
        }
    }

}
