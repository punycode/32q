package de.punyco.thirtytwosquare.service;

import de.punyco.thirtytwosquare.domain.Squarelet;
import de.punyco.thirtytwosquare.repository.SquareletRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class PostingServiceImpl implements PostingService {

    @Autowired
    SquareletRepository squareletRepository;

    public Squarelet updateSquarelet(Squarelet squarelet) {

        return squareletRepository.save(squarelet);
    }


    public void saveSquarelet(Squarelet squarelet) {

        squareletRepository.save(squarelet);
    }


    public List<Squarelet> findSquareletEntries(int firstResult, int maxResults) {

        return squareletRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults,
                    maxResults)).getContent();
    }


    public List<Squarelet> findAllSquarelets() {

        return squareletRepository.findAll();
    }


    public Squarelet findSquarelet(String id) {

        return squareletRepository.findOne(id);
    }


    public void deleteSquarelet(Squarelet squarelet) {

        squareletRepository.delete(squarelet);
    }


    public long countAllSquarelets() {

        return squareletRepository.count();
    }
}
