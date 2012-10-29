package de.punyco.thirtytwosquare.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.punyco.thirtytwosquare.domain.Squarelet;
import de.punyco.thirtytwosquare.repository.SquareletRepository;


@Service
@Transactional
public class SquareletServiceImpl implements SquareletService {

    @Autowired
    SquareletRepository squareletRepository;


    public long countAllSquarelets() {

        return squareletRepository.count();
    }


    public void deleteSquarelet(Squarelet squarelet) {

        squareletRepository.delete(squarelet);
    }


    public Squarelet findSquarelet(Long id) {

        return squareletRepository.findOne(id);
    }


    public List<Squarelet> findAllSquarelets() {

        return squareletRepository.findAll();
    }


    public List<Squarelet> findSquareletEntries(int firstResult, int maxResults) {

        return squareletRepository.findAll(
                new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }


    public void saveSquarelet(Squarelet squarelet) {

        squareletRepository.save(squarelet);
    }


    public Squarelet updateSquarelet(Squarelet squarelet) {

        return squareletRepository.save(squarelet);
    }
}
