package de.punyco.thirtytwosquare.service;

import java.util.List;

import org.springframework.roo.addon.layers.service.RooService;

import de.punyco.thirtytwosquare.domain.Squarelet;


@RooService(domainTypes = { de.punyco.thirtytwosquare.domain.Squarelet.class })
public interface SquareletService {

    public abstract long countAllSquarelets();


    public abstract void deleteSquarelet(Squarelet squarelet);


    public abstract Squarelet findSquarelet(Long id);


    public abstract List<Squarelet> findAllSquarelets();


    public abstract List<Squarelet> findSquareletEntries(int firstResult, int maxResults);


    public abstract void saveSquarelet(Squarelet squarelet);


    public abstract Squarelet updateSquarelet(Squarelet squarelet);

}
