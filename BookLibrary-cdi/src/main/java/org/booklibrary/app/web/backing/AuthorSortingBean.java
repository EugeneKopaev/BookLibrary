package org.booklibrary.app.web.backing;

import org.richfaces.component.SortOrder;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

@Named
@ViewScoped
public class AuthorSortingBean implements Serializable{

    private static final String SORT_PROPERTY_PARAMETER = "sortProperty";

    private static final String CREATED_SORT_OPTION_KEY = "created";

    private SortOrder createdDefaultSortOrder = SortOrder.descending;

    private Map<String, SortOrder> sortsOrders;
    private Queue<String> sortPriorities;

    @Inject
    private FacesContext facesContext;

    public AuthorSortingBean() {

    }

    @PostConstruct
    public void initSortOrders() {
        this.sortsOrders = new HashMap<>();
        this.sortPriorities = new ArrayDeque<>();
        // set common sorting priorities
        this.sortPriorities.add(CREATED_SORT_OPTION_KEY);
        this.sortsOrders.put(CREATED_SORT_OPTION_KEY, createdDefaultSortOrder);
    }

    public void sort() {
        String property = this.facesContext.getExternalContext().getRequestParameterMap()
                .get(SORT_PROPERTY_PARAMETER);
        if (property != null) {
            SortOrder currentPropertySortOrder = this.sortsOrders.get(property);
            initSortOrders();
            if (!sortPriorities.contains(property)) {
                this.sortPriorities.add(property);
            }
            if (currentPropertySortOrder == null || currentPropertySortOrder.equals(SortOrder.descending)) {
                this.sortsOrders.put(property, SortOrder.ascending);
            } else {
                this.sortsOrders.put(property, SortOrder.descending);
            }
        }
    }

    public Map<String, SortOrder> getSortsOrders() {
        return sortsOrders;
    }

    public Queue<String> getSortPriorities() {
        return sortPriorities;
    }
}
