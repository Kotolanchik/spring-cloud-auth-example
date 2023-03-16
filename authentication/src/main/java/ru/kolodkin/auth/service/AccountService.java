package ru.kolodkin.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kolodkin.auth.bean.AccountCreateRequest;
import ru.kolodkin.auth.domain.Account;
import ru.kolodkin.auth.repository.AccountRepository;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {
    private final AccountRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        val account = repository.findByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new User(account.getUsername(), account.getPassword(), new ArrayList<>());
    }

    public void save(AccountCreateRequest account){
        repository.save(new Account(account.getUsername(), account.getPassword()));
    }
}
