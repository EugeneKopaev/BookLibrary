package org.booklibrary.app.web.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class FacesMessageUtils {

    public static void addSuccessMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public static void addErrorMessage(Exception ex, String summaryInfo) {
        String errorMessage = getRootErrorMessage(ex);
        FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, summaryInfo, errorMessage);
        FacesContext.getCurrentInstance().addMessage(null, m);
    }

    private static String getRootErrorMessage(Exception e) {
        String errorMessage = " ";
        // Start with the exception and recurse to find the root cause
        Throwable t = e;
        while (t != null) {
            // Get the message from the Throwable class instance
            errorMessage = t.getLocalizedMessage();
            t = t.getCause();
        }
        // This is the root cause message
        return errorMessage;
    }
}
