package eu.accesa.learningplatform.service.exception;

public class ServiceException extends RuntimeException {

    
    public ServiceException(final String failureDescription) {
    	super(failureDescription);
    }
    
    public ServiceException(final String failureDescription, final Exception failureException) {
    	super(failureDescription, failureException);
    }
    
}
