package de.punyco.thirtytwosquare.repository;

import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;

import de.punyco.thirtytwosquare.domain.Squarelet;


@RooJpaRepository(domainType = Squarelet.class)
public interface SquareletRepository {
}
