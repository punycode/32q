package de.punyco.thirtytwosquare.domain;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.dod.RooDataOnDemand;
import org.springframework.stereotype.Component;

import de.punyco.thirtytwosquare.repository.SquareletRepository;
import de.punyco.thirtytwosquare.service.SquareletService;


@RooDataOnDemand(entity = Squarelet.class, quantity = 100)
@Component
@Configurable
public class SquareletDataOnDemand {

    private Random rnd = new SecureRandom();

    private List<Squarelet> data;

    @Autowired
    SquareletService squareletService;

    @Autowired
    SquareletRepository squareletRepository;


    public Squarelet getNewTransientSquarelet(int index) {

        Squarelet obj = new Squarelet();
        setMetadata(obj, index);
        return obj;
    }


    public void setMetadata(Squarelet obj, int index) {

        String metadata = "metadata_" + index;
        obj.setMetadata(metadata);
    }


    public Squarelet getSpecificSquarelet(int index) {

        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Squarelet obj = data.get(index);
        Long id = obj.getId();
        return squareletService.findSquarelet(id);
    }


    public Squarelet getRandomSquarelet() {

        init();
        Squarelet obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return squareletService.findSquarelet(id);
    }


    public boolean modifySquarelet(Squarelet obj) {

        return false;
    }


    public void init() {

        int from = 0;
        int to = 10;
        data = squareletService.findSquareletEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Squarelet' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }

        data = new ArrayList<Squarelet>();
        for (int i = 0; i < 100; i++) {
            Squarelet obj = getNewTransientSquarelet(i);
            try {
                squareletService.saveSquarelet(obj);
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage())
                            .append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            squareletRepository.flush();
            data.add(obj);
        }
    }
}
