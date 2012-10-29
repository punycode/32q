package de.punyco.thirtytwosquare.domain;

import java.util.List;

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

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import de.punyco.thirtytwosquare.repository.SquareletRepository;
import de.punyco.thirtytwosquare.service.SquareletService;


@RooIntegrationTest(entity = Squarelet.class, transactional = false)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
@Configurable
public class SquareletIntegrationTest {

    private static final LocalServiceTestHelper helper = new LocalServiceTestHelper(
            new LocalDatastoreServiceTestConfig());

    @Autowired
    private SquareletDataOnDemand dod;

    @Autowired
    SquareletService squareletService;

    @Autowired
    SquareletRepository squareletRepository;


    @BeforeClass
    public static void setUp() {

        helper.setUp();
    }


    @AfterClass
    public static void tearDown() {

        helper.tearDown();
    }


    @Test
    public void testCountAllSquarelets() {

        Assert.assertNotNull("Data on demand for 'Squarelet' failed to initialize correctly", dod.getRandomSquarelet());
        long count = squareletService.countAllSquarelets();
        Assert.assertTrue("Counter for 'Squarelet' incorrectly reported there were no entries", count > 0);
    }


    @Test
    public void testFindSquarelet() {

        Squarelet obj = dod.getRandomSquarelet();
        Assert.assertNotNull("Data on demand for 'Squarelet' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Squarelet' failed to provide an identifier", id);
        obj = squareletService.findSquarelet(id);
        Assert.assertNotNull("Find method for 'Squarelet' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Squarelet' returned the incorrect identifier", id, obj.getId());
    }


    @Test
    public void testFindAllSquarelets() {

        Assert.assertNotNull("Data on demand for 'Squarelet' failed to initialize correctly", dod.getRandomSquarelet());
        long count = squareletService.countAllSquarelets();
        Assert.assertTrue(
                "Too expensive to perform a find all test for 'Squarelet', as there are "
                        + count
                        + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test",
                count < 250);
        List<Squarelet> result = squareletService.findAllSquarelets();
        Assert.assertNotNull("Find all method for 'Squarelet' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Squarelet' failed to return any data", result.size() > 0);
    }


    @Test
    public void testFindSquareletEntries() {

        Assert.assertNotNull("Data on demand for 'Squarelet' failed to initialize correctly", dod.getRandomSquarelet());
        long count = squareletService.countAllSquarelets();
        if (count > 20)
            count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Squarelet> result = squareletService.findSquareletEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Squarelet' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Squarelet' returned an incorrect number of entries", count,
                result.size());
    }


    @Test
    public void testFlush() {

        Squarelet obj = dod.getRandomSquarelet();
        Assert.assertNotNull("Data on demand for 'Squarelet' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Squarelet' failed to provide an identifier", id);
        obj = squareletService.findSquarelet(id);
        Assert.assertNotNull("Find method for 'Squarelet' illegally returned null for id '" + id + "'", obj);
        boolean modified = dod.modifySquarelet(obj);
        Integer currentVersion = obj.getVersion();
        squareletRepository.flush();
        Assert.assertTrue("Version for 'Squarelet' failed to increment on flush directive",
                (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }


    @Test
    public void testUpdateSquareletUpdate() {

        Squarelet obj = dod.getRandomSquarelet();
        Assert.assertNotNull("Data on demand for 'Squarelet' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Squarelet' failed to provide an identifier", id);
        obj = squareletService.findSquarelet(id);
        boolean modified = dod.modifySquarelet(obj);
        Integer currentVersion = obj.getVersion();
        Squarelet merged = squareletService.updateSquarelet(obj);
        squareletRepository.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object",
                merged.getId(), id);
        Assert.assertTrue("Version for 'Squarelet' failed to increment on merge and flush directive",
                (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }


    @Test
    public void testSaveSquarelet() {

        Assert.assertNotNull("Data on demand for 'Squarelet' failed to initialize correctly", dod.getRandomSquarelet());
        Squarelet obj = dod.getNewTransientSquarelet(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Squarelet' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Squarelet' identifier to be null", obj.getId());
        squareletService.saveSquarelet(obj);
        squareletRepository.flush();
        Assert.assertNotNull("Expected 'Squarelet' identifier to no longer be null", obj.getId());
    }


    @Test
    @Transactional(propagation = Propagation.SUPPORTS)
    public void testDeleteSquarelet() {

        Squarelet obj = dod.getRandomSquarelet();
        Assert.assertNotNull("Data on demand for 'Squarelet' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Squarelet' failed to provide an identifier", id);
        obj = squareletService.findSquarelet(id);
        squareletService.deleteSquarelet(obj);
        squareletRepository.flush();
        Assert.assertNull("Failed to remove 'Squarelet' with identifier '" + id + "'",
                squareletService.findSquarelet(id));
    }
}
