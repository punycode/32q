package de.punyco.thirtytwosquare.service.google;

import com.google.appengine.api.images.*;

import de.punyco.thirtytwosquare.service.SquareletPreprocessorService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


@Service
public class SquareletPreprocessorServiceImpl implements SquareletPreprocessorService {

    private static final Set<Image.Format> ALLOWED_FORMATS;

    static {
        HashSet<Image.Format> formats = new HashSet<Image.Format>();
        formats.add(Image.Format.GIF);
        formats.add(Image.Format.JPEG);
        formats.add(Image.Format.PNG);
        ALLOWED_FORMATS = Collections.unmodifiableSet(formats);
    }

    private ImagesService imagesService;

    @Autowired
    public SquareletPreprocessorServiceImpl(ImagesService imagesService) {

        this.imagesService = imagesService;
    }

    @Override
    public boolean isImageAllowedForPosting(byte[] rawImage) {

        Image image = ImagesServiceFactory.makeImage(rawImage);

        if (!ALLOWED_FORMATS.contains(image.getFormat()))
            return false;

        return true;
    }


    @Override
    public byte[] prepareImageForPosting(byte[] rawImage) {

        Image image = ImagesServiceFactory.makeImage(rawImage);

        if (image.getWidth() != 32 && image.getHeight() != 32) {
            Transform downSizeTransform = ImagesServiceFactory.makeResize(32, 32, true);
            image = imagesService.applyTransform(downSizeTransform, image);

            Transform upsizeTransform = ImagesServiceFactory.makeResize(256, 256, true);
            image = imagesService.applyTransform(upsizeTransform, image);
        }

        return image.getImageData();
    }
}
