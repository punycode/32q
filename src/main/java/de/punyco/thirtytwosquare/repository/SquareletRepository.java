package de.punyco.thirtytwosquare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;
import org.springframework.stereotype.Repository;

import de.punyco.thirtytwosquare.domain.Squarelet;


@RooJpaRepository(domainType = Squarelet.class)
@Repository
public interface SquareletRepository extends JpaSpecificationExecutor<Squarelet>, JpaRepository<Squarelet, Long> {
}
