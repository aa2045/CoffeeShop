package f21as.coursework.coffeshop.exceptions;
//Exception thrown when an invalid orderID is passed

public class OrderNotFoundException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public OrderNotFoundException(String errorMessage) {
        super(errorMessage);
    }

}
