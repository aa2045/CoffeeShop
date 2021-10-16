package f21as.coursework.coffeshop.exceptions;

//This exception is thrown when either of the orders files, menu file or the discounts file could not be initialised
public class FailedInitializationException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public FailedInitializationException(String errorMessage) {
        super(errorMessage);
    }

}
