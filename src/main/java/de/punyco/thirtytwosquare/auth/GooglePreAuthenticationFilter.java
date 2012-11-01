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
import com.google.appengine.api.users.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class GooglePreAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {

    private static final Logger LOG = LoggerFactory.getLogger(GooglePreAuthenticationFilter.class);

    private static final String DUMMY_CREDENTIALS = "N/A";
    private static final Object ANONYMOUS = null;

    private UserService userService;

    @Autowired
    public GooglePreAuthenticationFilter(UserService userService) {

        this.userService = userService;
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {

        Principal principal = request.getUserPrincipal();

        if (principal != null) {
            LOG.info("Request has principal {}", principal);

            User currentUser = userService.getCurrentUser();

            return currentUser;
        } else {
            LOG.info("No pre-authenticated principal available. Returning anonymous (null) principal.");

            return ANONYMOUS;
        }
    }


    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {

        Principal principal = request.getUserPrincipal();

        if (principal != null) {
            return DUMMY_CREDENTIALS;
        } else {
            LOG.info("No pre-authenticated principal available. Returning anonymous (null) credentials.");

            return ANONYMOUS;
        }
    }
}
