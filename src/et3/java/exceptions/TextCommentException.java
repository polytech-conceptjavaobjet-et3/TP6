package et3.java.exceptions;

public class TextCommentException extends Exception
{
	private static final long serialVersionUID = 1L;

	public TextCommentException()
	{
		super();
	}
	
	public TextCommentException(String message)
	{
		super(message);
	}
}
