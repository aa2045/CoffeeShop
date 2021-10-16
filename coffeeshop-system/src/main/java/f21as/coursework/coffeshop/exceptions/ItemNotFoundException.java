package f21as.coursework.coffeshop.exceptions;

//this exception is thrown when an item is not found
public class ItemNotFoundException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public ItemNotFoundException(String errorMessage) {
        super(errorMessage);
    }

}
