package de.punyco.thirtytwosquare.repository;

import de.punyco.thirtytwosquare.domain.UserAccount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@RooJpaRepository(domainType = UserAccount.class)
@Repository
@Transactional(propagation = Propagation.SUPPORTS)
public interface UserRepository extends JpaSpecificationExecutor<UserAccount>, JpaRepository<UserAccount, String> {

    UserAccount findByUserId(String id);
}
