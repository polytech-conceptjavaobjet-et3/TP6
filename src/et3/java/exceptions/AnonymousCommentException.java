package et3.java.exceptions;

public class AnonymousCommentException extends TextCommentException
{
	private static final long serialVersionUID = 1L;

	public AnonymousCommentException() 
	{
		super();
	}
	
	public AnonymousCommentException(String message)
	{
		super(message);
	}
}
