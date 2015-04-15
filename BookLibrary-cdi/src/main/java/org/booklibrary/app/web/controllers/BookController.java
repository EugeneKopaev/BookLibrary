package org.booklibrary.app.web.controllers;

import org.booklibrary.app.exceptions.DuplicateResourceException;
import org.booklibrary.app.manager.AuthorManagerLocal;
import org.booklibrary.app.manager.BookManagerLocal;
import org.booklibrary.app.persistence.entity.Author;
import org.booklibrary.app.persistence.entity.Book;
import org.booklibrary.app.web.util.FacesMessageUtils;
import org.slf4j.Logger;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
public class BookController implements Serializable {

    @EJB
    private BookManagerLocal bookManager;
    @EJB
    private AuthorManagerLocal authorManager;

    @Inject
    private FacesContext facesContext;

    private String currentBookId;
    private Book managedBook = null;
    private Author reference = null;
    private Book createdBook = new Book();
    private List<Book> books = new ArrayList<>();
    private List<Book> filteredBooks = null;
    private List<SelectItem> authorOptions = new ArrayList<>();
    private Map<Object, Object> checkedItems = new HashMap<>();
    private int page = 1;

    public void save() {
        if (createdBook == null) {
            return;
        }
        try {
            addSelectedAuthors(createdBook);
            bookManager.saveUnique(createdBook);
            createdBook = new Book();
        } catch (DuplicateResourceException e) {
            FacesMessageUtils.addErrorMessage(e, e.getMessage());
        } catch (EJBException e) {
            FacesMessageUtils.addErrorMessage(e, "Save unsuccessful");
        }
    }

    public void remove() {
        try {
            bookManager.removeByPk(managedBook.getId());
            FacesMessageUtils.addInfoMessage("Removed book successful");
        } catch (EJBException e) {
            FacesMessageUtils.addErrorMessage(e, "Remove unsuccessful");
        }
    }

    public void removeReference() {
        if (reference == null || managedBook == null) {
            return;
        }
        if (managedBook.getAuthors().size() <= 1) {
            return;
        }
        managedBook.getAuthors().remove(reference);
        bookManager.update(managedBook);
        FacesMessageUtils.addInfoMessage("remove successful");
    }

    public void removeSelected() {
        if (!checkedItems.isEmpty()) {
            List<String> listToDelete = new ArrayList<>();
            for (Object key : checkedItems.keySet()) {
                if ((Boolean) checkedItems.get(key)) {
                    listToDelete.add((String) key);
                    checkedItems.put(key, false);
                }
            }
            for (String entry : listToDelete) {
                try {
                    bookManager.removeByPk(entry);
                    checkedItems.clear();
                    FacesMessageUtils.addInfoMessage("Removed book with id: " + entry);
                } catch (EJBException e) {
                    FacesMessageUtils.addErrorMessage(e, "Remove unsuccessful for book with id: " + entry);
                }
            }
        } else {
            FacesMessageUtils.addInfoMessage("There is nothing to delete");
        }

    }

    public void update() {
        if (managedBook == null) {
            return;
        }
        try {
            addSelectedAuthors(managedBook);
            bookManager.update(managedBook);
        } catch (EJBException e) {
            e.printStackTrace();
            FacesMessageUtils.addErrorMessage(e, "Update unsuccessful");
        }
    }

    public void loadData() {
        managedBook = bookManager.findByUuid(getCurrentBookId());
    }

    public void valueChanged(ValueChangeEvent event) {
        if (!authorOptions.isEmpty()) {
            authorOptions.clear();
        }
        if (event.getNewValue() != null) {
            String pk = (String) event.getNewValue();
            authorOptions.add(new SelectItem(pk));
        }
    }

    public void filterBooksByAuthor(String filterValue) {
        List<Author> authorList = authorManager.findAll();
        if (authorList != null) {
            this.filteredBooks = new ArrayList<>();
            for (Author author: authorList) {
                if (author.getLastName().toLowerCase().startsWith(filterValue.toLowerCase())) {
                    this.filteredBooks.addAll(author.getBooks());
                }
            }
        }

    }


    @Produces
    @Named
    public Book getManagedBook() {
        return managedBook;
    }

    public void setManagedBook(Book managedBook) {
        this.managedBook = managedBook;
    }

    @Produces
    @Named
    public List<Book> getBooks() {
        return this.books = bookManager.findAll();
    }

    @Produces
    @Named
    public Book getCreatedBook() {
        return createdBook;
    }

    public List<Book> getFilteredBooks() {
        if (this.filteredBooks == null) {
            return getBooks();
        }
        return this.filteredBooks;
    }

    public void setFilteredBooks(List<Book> filteredBooks) {
        this.filteredBooks = filteredBooks;
    }

    public Double getBookRating(String bookId) {
        return bookManager.countAvgBookRating(bookId);

    }

    public void setCreatedBook(Book createdBook) {
        this.createdBook = createdBook;
    }

    public List<SelectItem> getAuthorOptions() {
        return this.authorOptions;
    }

    public void setAuthorOptions(List<SelectItem> authorOptions) {
        this.authorOptions = authorOptions;
    }

    public String getCurrentBookId() {
        return currentBookId;
    }

    public void setCurrentBookId(String currentBookId) {
        this.currentBookId = currentBookId;
    }

    public Author getReference() {
        return reference;
    }

    public void setReference(Author reference) {
        this.reference = reference;
    }

    public Map<Object, Object> getCheckedItems() {
        return checkedItems;
    }

    public void setCheckedItems(Map<Object, Object> checkedItems) {
        this.checkedItems = checkedItems;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    private void addSelectedAuthors(Book book) {
        if (authorOptions.isEmpty()) {
            return;
        }
        for (SelectItem item : authorOptions) {
            Author author = authorManager.findByUuid((String) item.getValue());
            if (!book.getAuthors().contains(author)) {
                book.addAuthor(author);
            }
        }
    }
}
