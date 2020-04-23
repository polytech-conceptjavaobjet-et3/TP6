package et3.java.comments;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import et3.java.exceptions.AnonymousCommentException;
import et3.java.exceptions.EmptyCommentException;
import et3.java.exceptions.HatefulCommentException;
import et3.java.exceptions.TextCommentException;
import et3.java.sort.CommentSortStrategy;

public class CommentableImmutableText
{
	//On créer une classe 'TextComment' interne à 'CommentableImmutableText'
	public class TextComment implements Comparable<TextComment>
	{
		private String user;
		private Date creationDate;
		private int indiceDebut;
		private int indiceFin;
		
		/**/
		private TextComment(String user, int indiceDebut, int indiceFin) throws TextCommentException
		{
			//Les problemes que l'on peut rencontrer sont les suivants :
			
				//Le commentaire n'est lie a aucun texte
				if(indiceDebut == indiceFin)
				{
					throw new EmptyCommentException("Le commentaire n'est lie a aucun texte et ne peut donc pas être ajouté.");
				}
				
				//Le commentaire est anonyme
				if(user == null || user.equals(""))
				{
					throw new AnonymousCommentException("Le commentaire est anonyme et ne peut donc pas être ajouté.");
				}
				
			//Si aucun des problemes ci-dessus n'a ete rencontre, on implemente le 'TextComment'
			this.user = user;
			this.indiceDebut = indiceDebut;
			this.indiceFin = indiceFin;
			this.creationDate = new Date(System.currentTimeMillis());
		}

		@Override
		/**
		 * Compare this 'TextComment' with another one.
		 * This method will return:
		 * - 0 if the two 'TextComment' were created at the same time
		 * - a negative value if this 'TextComment' is older than the other one
		 * - a positive value if this 'TextComment' is more recent than the other one
		 */
		public int compareTo(TextComment anotherTextComment) 
		{
			//On choisi d'utiliser le tri par anciennete par defaut
			return this.creationDate.compareTo(anotherTextComment.creationDate);
		}
		
		@Override
		public String toString() 
		{
			StringBuilder comment = new StringBuilder();
			//On 
			comment.append("");
			return super.toString();
		}
	}
	
	private final String text;
	private List<TextComment> comments;
	
	/**
	 * 
	 * @param text
	 */
	public CommentableImmutableText(String text)
	{
		this.text = text;
		this.comments = new ArrayList<TextComment>();
	}
	
	/**
	 * 
	 * @return
	 */
	public String getText()
	{
		return text;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<TextComment> getComments()
	{
		return comments;
	}
	
	/**
	 * 
	 * @param sortingStrategy
	 * @return
	 */
	public List<TextComment> getComments(CommentSortStrategy sortingStrategy)
	{
		return comments;
	}
	
	/**
	 * 
	 * @param comment
	 * @param name
	 * @throws TextCommentException 
	 */
	public void addComment(String comment, String name) throws TextCommentException
	{
		this.comments.add(new TextComment(comment, name));
	}
	
	/**
	 * 
	 */
	public void displayComments()
	{
		for(TextComment comment : comments)
		{
			System.out.println(comment + "\n");
		}
	}
	
	/**
	 * 
	 * @param sortingStrategy
	 */
	public void displaySortedComments(CommentSortStrategy commentSortStrategy)
	{
		//Si on choisi de trier les commentaires par anciennete
		if(commentSortStrategy.equals(CommentSortStrategy.BySeniority))
		{
			
		}
		//Si on choisi de trier les commentaires par position
		if(commentSortStrategy.equals(CommentSortStrategy.ByPosition))
		{
			
		}
		//Si on choisi de trier les commentaires par utilisateur et anciennete
		if(commentSortStrategy.equals(CommentSortStrategy.ByNameAndSeniority))
		{
			
		}
		//Si on choisi de trier les commentaires par longueur decroissante
		if(commentSortStrategy.equals(CommentSortStrategy.ByLength))
		{
			for(TextComment comment : comments)
			{
				System.out.println(comment + "\n");
			}
		}
		
	}
}
