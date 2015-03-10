package org.booklibrary.app.manager.beans;

import org.booklibrary.app.persistence.entity.Author;
import org.booklibrary.app.persistence.id.EntityIdentifier;
import org.booklibrary.app.manager.AuthorManagerLocal;
import org.booklibrary.app.persistence.session.AuthorFacadeLocal;
import org.booklibrary.app.persistence.session.AuthorHomeLocal;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class AuthorManager implements AuthorManagerLocal{

    @EJB
    private AuthorFacadeLocal authorFacade;

    @EJB
    private AuthorHomeLocal authorHome;

    @Override
    public Author save(Author obj) {
        return authorHome.save(obj);
    }

    @Override
    public Author update(Author obj) {
        return null;
    }

    @Override
    public void remove(EntityIdentifier key) {

    }

    @Override
    public void remove(String uuid) {

    }

    @Override
    public void removeAll() {

    }

    @Override
    public Author findByPk(EntityIdentifier key) {
        return null;
    }

    @Override
    public Author findByUuid(String uuid) {
        return null;
    }

    @Override
    public List<Author> findAll() {
        return null;
    }
}
