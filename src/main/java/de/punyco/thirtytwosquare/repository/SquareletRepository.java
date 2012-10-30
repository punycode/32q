package de.punyco.thirtytwosquare.repository;

import de.punyco.thirtytwosquare.domain.Squarelet;
import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;

@RooJpaRepository(domainType = Squarelet.class)
public interface SquareletRepository {
}
