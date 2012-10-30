package de.punyco.thirtytwosquare.repository;

import de.punyco.thirtytwosquare.domain.UserAccount;
import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;

@RooJpaRepository(domainType = UserAccount.class)
public interface UserRepository {
}
