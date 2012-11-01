package de.punyco.thirtytwosquare.domain;

import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import de.punyco.thirtytwosquare.repository.UserRepository;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import org.springframework.roo.addon.test.RooIntegrationTest;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Configurable
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@RooIntegrationTest(entity = UserAccount.class, transactional = false)
public class UserAccountIntegrationTest {

    private static final LocalServiceTestHelper helper = new LocalServiceTestHelper(
            new com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig());
    @Autowired
    private UserAccountDataOnDemand dod;
    @Autowired
    UserRepository userRepository;

    @BeforeClass
    public static void setUp() {

        helper.setUp();
    }


    @AfterClass
    public static void tearDown() {

        helper.tearDown();
    }


    @Test
    @Transactional(propagation = Propagation.SUPPORTS)
    public void testDelete() {

        UserAccount obj = dod.getRandomUserAccount();
        Assert.assertNotNull("Data on demand for 'UserAccount' failed to initialize correctly", obj);

        String id = obj.getId();
        Assert.assertNotNull("Data on demand for 'UserAccount' failed to provide an identifier", id);
        obj = userRepository.findOne(id);
        userRepository.delete(obj);
        userRepository.flush();
        Assert.assertNull("Failed to remove 'UserAccount' with identifier '" + id + "'", userRepository.findOne(id));
    }


    @Test
    public void testSave() {

        Assert.assertNotNull("Data on demand for 'UserAccount' failed to initialize correctly",
            dod.getRandomUserAccount());

        UserAccount obj = dod.getNewTransientUserAccount(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'UserAccount' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'UserAccount' identifier to be null", obj.getId());
        userRepository.save(obj);
        userRepository.flush();
        Assert.assertNotNull("Expected 'UserAccount' identifier to no longer be null", obj.getId());
    }


    @Test
    public void testSaveUpdate() {

        UserAccount obj = dod.getRandomUserAccount();
        Assert.assertNotNull("Data on demand for 'UserAccount' failed to initialize correctly", obj);

        String id = obj.getId();
        Assert.assertNotNull("Data on demand for 'UserAccount' failed to provide an identifier", id);
        obj = userRepository.findOne(id);

        boolean modified = dod.modifyUserAccount(obj);
        Integer currentVersion = obj.getVersion();
        UserAccount merged = userRepository.save(obj);
        userRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(),
            id);
        Assert.assertTrue("Version for 'UserAccount' failed to increment on merge and flush directive",
            (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }


    @Test
    public void testFlush() {

        UserAccount obj = dod.getRandomUserAccount();
        Assert.assertNotNull("Data on demand for 'UserAccount' failed to initialize correctly", obj);

        String id = obj.getId();
        Assert.assertNotNull("Data on demand for 'UserAccount' failed to provide an identifier", id);
        obj = userRepository.findOne(id);
        Assert.assertNotNull("Find method for 'UserAccount' illegally returned null for id '" + id + "'", obj);

        boolean modified = dod.modifyUserAccount(obj);
        Integer currentVersion = obj.getVersion();
        userRepository.flush();
        Assert.assertTrue("Version for 'UserAccount' failed to increment on flush directive",
            (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }


    @Test
    public void testCount() {

        Assert.assertNotNull("Data on demand for 'UserAccount' failed to initialize correctly",
            dod.getRandomUserAccount());

        long count = userRepository.count();
        Assert.assertTrue("Counter for 'UserAccount' incorrectly reported there were no entries", count > 0);
    }


    @Test
    public void testFind() {

        UserAccount obj = dod.getRandomUserAccount();
        Assert.assertNotNull("Data on demand for 'UserAccount' failed to initialize correctly", obj);

        String id = obj.getId();
        Assert.assertNotNull("Data on demand for 'UserAccount' failed to provide an identifier", id);
        obj = userRepository.findOne(id);
        Assert.assertNotNull("Find method for 'UserAccount' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'UserAccount' returned the incorrect identifier", id, obj.getId());
    }


    @Test
    public void testFindAll() {

        Assert.assertNotNull("Data on demand for 'UserAccount' failed to initialize correctly",
            dod.getRandomUserAccount());

        long count = userRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'UserAccount', as there are " + count
            + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test",
            count < 250);

        List<UserAccount> result = userRepository.findAll();
        Assert.assertNotNull("Find all method for 'UserAccount' illegally returned null", result);
        Assert.assertTrue("Find all method for 'UserAccount' failed to return any data", result.size() > 0);
    }


    @Test
    public void testFindEntries() {

        Assert.assertNotNull("Data on demand for 'UserAccount' failed to initialize correctly",
            dod.getRandomUserAccount());

        long count = userRepository.count();

        if (count > 20)
            count = 20;

        int firstResult = 0;
        int maxResults = (int) count;
        List<UserAccount> result = userRepository.findAll(new org.springframework.data.domain.PageRequest(
                    firstResult / maxResults, maxResults)).getContent();
        Assert.assertNotNull("Find entries method for 'UserAccount' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'UserAccount' returned an incorrect number of entries", count,
            result.size());
    }
}
