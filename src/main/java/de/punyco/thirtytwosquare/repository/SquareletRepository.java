package de.punyco.thirtytwosquare.repository;

import de.punyco.thirtytwosquare.domain.Squarelet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;

import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@RooJpaRepository(domainType = Squarelet.class)
@Repository
@Transactional(propagation = Propagation.SUPPORTS)
public interface SquareletRepository extends JpaSpecificationExecutor<Squarelet>, JpaRepository<Squarelet, String> {
}
