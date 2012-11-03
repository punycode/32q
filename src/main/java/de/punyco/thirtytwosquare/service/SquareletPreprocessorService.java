package de.punyco.thirtytwosquare.service;

public interface SquareletPreprocessorService {

    boolean isImageAllowedForPosting(byte[] rawImage);


    byte[] prepareImageForPosting(byte[] rawImage);
}
