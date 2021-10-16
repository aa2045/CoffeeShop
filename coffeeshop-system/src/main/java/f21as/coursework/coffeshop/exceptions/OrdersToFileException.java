package f21as.coursework.coffeshop.exceptions;
//exception thrown when Orders cannot be written to the file
public class OrdersToFileException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public OrdersToFileException(FrequencyException e) {
        super(e);
    }

}
