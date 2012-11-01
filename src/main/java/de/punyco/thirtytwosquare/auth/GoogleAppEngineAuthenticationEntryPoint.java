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

import com.google.appengine.api.users.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class GoogleAppEngineAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger LOG = LoggerFactory.getLogger(GoogleAppEngineAuthenticationEntryPoint.class);

    private UserService userService;

    @Autowired
    public GoogleAppEngineAuthenticationEntryPoint(UserService userService) {

        this.userService = userService;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws IOException, ServletException {

        String loginURL = userService.createLoginURL(request.getRequestURI());

        if (LOG.isDebugEnabled()) {
            LOG.debug("Redirecting to login URL '{}'", loginURL);
        }

        response.sendRedirect(loginURL);
    }
}
