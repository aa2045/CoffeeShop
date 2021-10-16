package f21as.coursework.coffeshop.exceptions;
//exception used when not matching ID for customer or item is passed 
public class DiscountFileNotFoundException extends Exception{
private static final long serialVersionUID = 1L;
    
    public DiscountFileNotFoundException(String errormessage) {
            super(errormessage);
    }

}
