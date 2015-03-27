package org.booklibrary.app.persistence.session;


import org.booklibrary.app.persistence.entity.Author;
import org.booklibrary.app.persistence.id.EntityIdentifier;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.persistence.ShouldMatchDataSet;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
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
import javax.persistence.PersistenceException;
import java.io.File;

/**
 * Integration test using arquillian persistence dbunit extension
 */
//@Transactional(TransactionMode.ROLLBACK)
//@PersistenceTest // every test will execute in transaction
public class AuthorHomeTest extends Arquillian {

    @EJB
    private AuthorHomeLocal authorHomeLocal;

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

        war.addPackages(true, Author.class.getPackage());
        war.addPackages(true, AuthorHomeLocal.class.getPackage());
        war.addPackages(true, EntityIdentifier.class.getPackage());
        war.addAsResource("test-persistence.xml", "META-INF/persistence.xml");
        war.addAsWebInfResource("test-ds.xml");
        //Enable CDI
        war.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

        return war;
    }

    @Test(expectedExceptions = EJBTransactionRolledbackException.class)
    public void saveDuplicateAuthorThrowExceptionTest() throws Exception {
        Author author1 = new Author();
        author1.setFirstName("Test first name");
        author1.setLastName("Test last name");
        authorHomeLocal.save(author1);
        Assert.assertNotNull(author1.getId());
        Author author2 = new Author();
        author2.setFirstName("Test first name");
        author2.setLastName("Test last name");
        authorHomeLocal.save(author2);
    }

    @Test
    @UsingDataSet("dataset/author/initial-dataset.xml")
    @ShouldMatchDataSet(value = "dataset/author/save-dataset.xml",
            excludeColumns = {"ID", "CREATED", "CHANGED"},
            // Entity have a UUID as primary key so the rows
            // ordering is not predictable and sometimes assertEquals will fail.
            // So we need to specify orderBy property.
            orderBy = {"BOOKS.CREATED", "AUTHORS.FIRST_NAME"})
    public void saveAuthorTest() throws Exception {
        Author testAuthor1 = new Author();
        testAuthor1.setFirstName("Test first name");
        testAuthor1.setLastName("Test last name");
        //check auto generated id
        Assert.assertNotNull(testAuthor1.getId());
        authorHomeLocal.save(testAuthor1);

        testAuthor1.setFirstName("Change first name");
        testAuthor1.setLastName("Change last name");

        //fire changes to db
        entityManager.flush();

        Author testAuthor2 = new Author();
        testAuthor2.setFirstName("Test new name");
        testAuthor2.setLastName("Test new name");

        Author testAuthor3 = new Author();
        testAuthor3.setFirstName("Test another name");
        testAuthor3.setLastName("Test another name");

        Assert.assertNotNull(testAuthor2.getId());
        Assert.assertNotNull(testAuthor3.getId());

        authorHomeLocal.save(testAuthor2);
        entityManager.flush();
        authorHomeLocal.save(testAuthor3);
    }

    @Test
    @UsingDataSet("dataset/author/initial-dataset.xml")
    @ShouldMatchDataSet(value = "dataset/author/update_dataset.xml",
            excludeColumns = {"ID", "CREATED", "CHANGED"},
            orderBy = {"BOOKS.CREATED", "AUTHORS.CREATED"})
    public void updateAuthorTest() throws Exception {
        Author author = entityManager.createQuery(
                "select a from Author a where a.firstName like :name", Author.class)
                .setParameter("name", "Mike")
                .getSingleResult();
        Assert.assertNotNull(author);
        entityManager.clear();
        author.setFirstName("Update first name");
        author.setLastName("Update last name");
        authorHomeLocal.update(author);
    }

    @Test(expectedExceptions = RuntimeException.class)
    @UsingDataSet("dataset/author/initial-dataset.xml")
    public void removeAuthorWithReferencesThrowExceptionTest() throws Exception {
        Author author = entityManager.createQuery(
                "select a from Author a where a.firstName like :name", Author.class)
                .setParameter("name", "Adam")
                .getSingleResult();
        Assert.assertNotNull(author);
        authorHomeLocal.removeByPk(author.getId());
    }

    @Test
    @UsingDataSet("dataset/author/initial-dataset.xml")
    @ShouldMatchDataSet(value = "dataset/author/remove-dataset.xml",
            excludeColumns = {"ID", "CREATED", "CHANGED"},
            orderBy = {"BOOKS.CREATED", "AUTHORS.FIRST_NAME"})
    public void removeAuthorTest() throws Exception {
        Author author = entityManager.createQuery(
                "select a from Author a where a.firstName like :name", Author.class)
                .setParameter("name", "Mike")
                .getSingleResult();
        Assert.assertNotNull(author);
        authorHomeLocal.removeByPk(author.getId());
    }

    @Test
    @UsingDataSet("dataset/author/initial-dataset.xml")
    @ShouldMatchDataSet(value = "dataset/author/remove-all-dataset.xml")
    public void removeAllAuthorTest() throws Exception {
        authorHomeLocal.removeAll();
    }
}
