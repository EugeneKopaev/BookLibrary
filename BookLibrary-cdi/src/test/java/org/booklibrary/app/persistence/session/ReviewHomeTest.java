package org.booklibrary.app.persistence.session;

import org.booklibrary.app.common.Resources;
import org.booklibrary.app.persistence.entity.Author;
import org.booklibrary.app.persistence.entity.Review;
import org.booklibrary.app.persistence.id.IdGenerator;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.File;

/**
 * Integration test using arquillian persistence dbunit extension
 */
public class ReviewHomeTest extends Arquillian {

    @EJB
    private ReviewHomeLocal reviewHome;

    @Inject
    private EntityManager entityManager;

    /**
     * Create testable archive
     *
     * @return Archive
     */
    @Deployment
    public static Archive<?> createTestableDeployment() {

        // resolve all dependencies from pom.xml
        // TODO:// create basic class for deployments
        File[] libs = Maven.resolver().loadPomFromFile("pom.xml")
                .importRuntimeAndTestDependencies().asFile();

        WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war");

        for (File file : libs) {
            war.addAsLibrary(file);
        }

        war.addPackages(true, Review.class.getPackage());
        war.addPackages(true, ReviewHomeLocal.class.getPackage());
        war.addPackages(true, Resources.class.getPackage());
        war.addPackages(true, IdGenerator.class.getPackage());
        war.addAsResource("test-persistence.xml", "META-INF/persistence.xml");
        war.addAsWebInfResource("test-ds.xml");
        //Enable CDI
        war.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

        return war;
    }

}
