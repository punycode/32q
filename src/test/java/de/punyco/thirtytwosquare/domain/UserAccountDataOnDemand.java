package de.punyco.thirtytwosquare.domain;

import de.punyco.thirtytwosquare.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import org.springframework.roo.addon.dod.RooDataOnDemand;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;


@Component
@Configurable
@RooDataOnDemand(entity = UserAccount.class, quantity = 100)
public class UserAccountDataOnDemand {

    @Autowired
    UserRepository userRepository;

    private List<UserAccount> data;
    private Random rnd = new SecureRandom();

    public void init() {

        int from = 0;
        int to = 10;
        data = userRepository.findAll(new org.springframework.data.domain.PageRequest(from / to, to)).getContent();

        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'UserAccount' illegally returned null");
        }

        if (!data.isEmpty()) {
            return;
        }

        data = new ArrayList<UserAccount>();

        for (int i = 0; i < 100; i++) {
            UserAccount obj = getNewTransientUserAccount(i);

            try {
                userRepository.save(obj);
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();

                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=")
                        .append(cv.getInvalidValue()).append("]");
                }

                throw new RuntimeException(msg.toString(), e);
            }

            userRepository.flush();
            data.add(obj);
        }
    }


    public boolean modifyUserAccount(UserAccount obj) {

        return false;
    }


    public UserAccount getRandomUserAccount() {

        init();

        UserAccount obj = data.get(rnd.nextInt(data.size()));
        String id = obj.getUserId();

        return userRepository.findOne(id);
    }


    public UserAccount getSpecificUserAccount(int index) {

        init();

        if (index < 0) {
            index = 0;
        }

        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }

        UserAccount obj = data.get(index);
        String id = obj.getUserId();

        return userRepository.findOne(id);
    }


    public void setNickname(UserAccount obj, int index) {

        String nickname = "nickname_" + index;
        obj.setNickname(nickname);
    }


    public UserAccount getNewTransientUserAccount(int index) {

        UserAccount obj = new UserAccount();
        setNickname(obj, index);

        return obj;
    }
}
