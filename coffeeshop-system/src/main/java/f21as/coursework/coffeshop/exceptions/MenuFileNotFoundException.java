package f21as.coursework.coffeshop.exceptions;

//this exception is thrown when the menu.csv file is not found in the file system.
public class MenuFileNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;
    
    public MenuFileNotFoundException(String errormessage) {
            super(errormessage);
    }

}
