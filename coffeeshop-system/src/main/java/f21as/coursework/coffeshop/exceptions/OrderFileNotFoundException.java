package f21as.coursework.coffeshop.exceptions;
//Exception is thrown or caught when the orderFile to be read is not found
public class OrderFileNotFoundException extends Exception{
private static final long serialVersionUID = 1L;
    
    public OrderFileNotFoundException(String message) {
            super(message);
    }

}


