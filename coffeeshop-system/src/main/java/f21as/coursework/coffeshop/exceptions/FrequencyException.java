package f21as.coursework.coffeshop.exceptions;

//this exception is thrown when objects of filemanager are called do not have the same serialization.
public class FrequencyException extends Exception{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FrequencyException(String string) {
        super(string);
    }

}
