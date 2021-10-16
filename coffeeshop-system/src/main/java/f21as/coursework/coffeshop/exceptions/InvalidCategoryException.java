package f21as.coursework.coffeshop.exceptions;

	// this exception is thrown when an invalid category is selected from the GUI 
	public class InvalidCategoryException extends RuntimeException {
	    public InvalidCategoryException(String errorMessage) {
	        super(errorMessage);
	    }
	}

