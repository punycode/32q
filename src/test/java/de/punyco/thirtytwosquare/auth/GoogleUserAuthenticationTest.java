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

import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalUserServiceTestConfig;

import de.punyco.thirtytwosquare.domain.Roles;
import de.punyco.thirtytwosquare.domain.UserAccount;
import de.punyco.thirtytwosquare.repository.UserRepository;

import org.hamcrest.CustomTypeSafeMatcher;
import org.hamcrest.Matcher;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.mockito.Mockito.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.Collection;


public class GoogleUserAuthenticationTest {

    // STUBS & MOCKS
    private static final String DUMMY_CREDENTIALS = "N/A";
    private final UserRepository userRepository = mock(UserRepository.class);
    private final LocalServiceTestHelper testHelper = new LocalServiceTestHelper(new LocalUserServiceTestConfig());

    // Under test, should be stateless itself
    private final GoogleUserDetailsService userDetailsService = new GoogleUserDetailsService(userRepository);

    public static Matcher<GrantedAuthority> sameAsRole(GrantedAuthority role) {

        return new RoleMatcher(role);
    }


    @Before
    public void setUp() {

        testHelper.setUp();
        testHelper.setEnvAuthDomain("32quadrat.appspot.com");
    }


    @After
    public void tearDown() {

        reset(userRepository);
        testHelper.tearDown();
    }


    @Test
    public void newUserShouldHaveOnlyRolesUserAndUnregistered() {

        testHelper.setEnvIsLoggedIn(true);
        testHelper.setEnvEmail("neverseen@gmail.com");

        // Given an authentication token of a never seen before user
        PreAuthenticatedAuthenticationToken authenticationToken = new PreAuthenticatedAuthenticationToken(
                UserServiceFactory.getUserService().getCurrentUser(), DUMMY_CREDENTIALS);

        // ... retrieving the user details ...
        UserDetails userDetails = userDetailsService.loadUserDetails(authenticationToken);

        // .. should yield _only_ roles USER and UNREGISTERED
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) userDetails.getAuthorities();

        assertThat(authorities, hasItems(sameAsRole(Roles.USER), sameAsRole(Roles.UNREGISTERED)));
    }


    @Test
    public void returningUserShouldNotHaveRoleUnregistered() {

        setupReturningUser();
        testHelper.setEnvIsLoggedIn(true);
        testHelper.setEnvEmail("returning@gmail.com");

        // Given an authentication token of a returning user
        PreAuthenticatedAuthenticationToken authenticationToken = new PreAuthenticatedAuthenticationToken(
                UserServiceFactory.getUserService().getCurrentUser(), DUMMY_CREDENTIALS);

        // ... retrieving the user details ...
        UserDetails userDetails = userDetailsService.loadUserDetails(authenticationToken);

        // ... should yield _no_ UNREGISTERED role
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) userDetails.getAuthorities();
        assertThat(authorities, not(hasItem(sameAsRole(Roles.UNREGISTERED))));
    }


    @Test
    @Ignore
    public void adminUserFromGoogleShouldHaveRoleAdmin() {

        setupReturningUser();
        testHelper.setEnvIsLoggedIn(true);
        testHelper.setEnvEmail("admin@gmail.com");
        testHelper.setEnvIsAdmin(true);

        // Given an authentication token of a returning user
        PreAuthenticatedAuthenticationToken authenticationToken = new PreAuthenticatedAuthenticationToken(
                UserServiceFactory.getUserService().getCurrentUser(), DUMMY_CREDENTIALS);

        // ... retrieving the user details ...
        UserDetails userDetails = userDetailsService.loadUserDetails(authenticationToken);

        // ... should yield _no_ UNREGISTERED role
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) userDetails.getAuthorities();
        assertThat(authorities, hasItem(sameAsRole(Roles.ADMIN)));
    }


    private void setupReturningUser() {

        UserAccount userAccount = new UserAccount("anything");
        userAccount.setId("anything");
        when(userRepository.findByUserId(anyString())).thenReturn(userAccount);
    }

    public static class RoleMatcher extends CustomTypeSafeMatcher<GrantedAuthority> {

        private GrantedAuthority role;

        public RoleMatcher(GrantedAuthority role) {

            super("role " + role);
            this.role = role;
        }

        @Override
        protected boolean matchesSafely(GrantedAuthority item) {

            return role.getAuthority().equals(item.getAuthority());
        }
    }
}
