package de.punyco.thirtytwosquare.service;

import de.punyco.thirtytwosquare.domain.Squarelet;

import org.springframework.roo.addon.layers.service.RooService;

import java.util.List;


@RooService(domainTypes = { de.punyco.thirtytwosquare.domain.Squarelet.class })
public interface PostingService {

    long countAllSquarelets();


    void deleteSquarelet(Squarelet squarelet);


    Squarelet findSquarelet(String id);


    List<Squarelet> findAllSquarelets();


    List<Squarelet> findSquareletEntries(int firstResult, int maxResults);


    void saveSquarelet(Squarelet squarelet);


    Squarelet updateSquarelet(Squarelet squarelet);
}
