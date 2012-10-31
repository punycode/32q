package de.punyco.thirtytwosquare.repository;

import de.punyco.thirtytwosquare.domain.UserAccount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;

import org.springframework.stereotype.Repository;


@RooJpaRepository(domainType = UserAccount.class)
@Repository
public interface UserRepository extends JpaSpecificationExecutor<UserAccount>, JpaRepository<UserAccount, String> {
}
