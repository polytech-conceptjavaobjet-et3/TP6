package et3.java.exceptions;

public class EmptyCommentException extends TextCommentException
{
	private static final long serialVersionUID = 1L;

	public EmptyCommentException() 
	{
		super();
	}
	
	public EmptyCommentException(String message)
	{
		super(message);
	}
}
