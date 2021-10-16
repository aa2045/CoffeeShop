package f21as.coursework.coffeshop.exceptions;

//this exception is thrown when the report cannot be generated
public class FailedReportException extends Exception{


	private static final long serialVersionUID = 1L;

	public FailedReportException(String string) {
        super(string);
    }

}
