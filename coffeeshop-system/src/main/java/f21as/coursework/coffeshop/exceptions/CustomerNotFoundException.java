package f21as.coursework.coffeshop.exceptions;

//this exception is thrown when the customer is not found
public class CustomerNotFoundException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public CustomerNotFoundException(String errorMessage) {
        super(errorMessage);
    }

}
