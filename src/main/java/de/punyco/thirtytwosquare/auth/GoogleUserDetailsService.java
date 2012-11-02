/*
 * Copyright (c) 2012 zenhase <zh@punyco.de>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.punyco.thirtytwosquare.auth;

import com.google.appengine.api.users.User;

import de.punyco.thirtytwosquare.domain.UserAccount;
import de.punyco.thirtytwosquare.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;


public class GoogleUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    private static Logger LOG = LoggerFactory.getLogger(GoogleUserDetailsService.class);
    private UserRepository userRepository;

    @Autowired
    public GoogleUserDetailsService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {

        LOG.info("Loading user details for token {}", token);

        User user = (User) token.getPrincipal();

        UserAccount userAccount = userRepository.findByUserId(user.getUserId());

        if (userAccount == null) {
            userAccount = UserAccount.withGoogleId(user.getUserId());
            userAccount.setEmail(user.getEmail());
            userAccount.setNickname(user.getNickname());
        }

        return userAccount;
    }
}
