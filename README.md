Programmation Java @ Et3
<br>
Polytech Paris-Saclay | 2019-20

___

# TP6

On souhaite mettre en place un système de commentaires très simple sur un texte statique. Concrètement, un lecteur doit pouvoir ajouter un commentaire libre portant sur un fragment continu d'un document. Les commentaires collectés pourront ensuite être affichés selon différentes stratégies de tri.

Un commentaire pour un texte donné sera donc composé d'un utilisateur (*user*), d'un indice de caractère de début (*indexBegin*) et de fin (*indexEnd*), ainsi que d'une date de création (*creationDate*).

Un certain nombre de stratégies de tri devront être anticipées, notamment :
- afficher les commentaires par ancienneté 
- afficher les commentaires par position de début dans le texte (en privilégiant les commentaires se terminant d'abord)
- afficher les commentaires par nom d'utilisateur (tri alphanumérique) puis par ancienneté
- afficher les commentaires par longueur de texte décroissante

6#1. Proposez une classe d'énumération `CommentSortingStrategy` pour représenter les stratégies de tri des commentaires. Chaque valeur d'énumération sera associée à une brève description textuelle qui sera affichée lorsqu'un tri sera réalisé.

6#1.1 code

> Ici, on veut créer une [`enum`](https://howtodoinjava.com/java/enum/enum-tutorial/#constructors) en faisant en sorte que chaque élément ait une description associée. Il faut donc implémenter un constructeur.
>
> ```Java
> package et3.java.sorting;
> 
> public enum CommentSortingStrategy
> {
> 	BySeniority("Elements are sorted from the most recent to the oldest."),
> 	ByPosition("Elements are sorted from the closest to the begining to the closest to the end."),
> 	ByNameAndSeniority("Elements are sorted by users' alphabetical order and senority, from the most recent to the oldest."),
> 	ByLength ("Elements are sorted by decreasing length.");
> 	
> 	private String description;
> 	
> 	/**
> 	 * Constructor of the enumeration {@link CommentSortingStrategy}
> 	 * @param description The description of the element
> 	 */
>     CommentSortingStrategy(String description)
>     {
>         this.description=description;
>     }
> 
>     /**
>      * This method returns the description of an element
>      * @return The description of the element
>      */
>     public String getDescription()
>     {
>         return description;
>     }
> }
> ```

6#2 Proposez un début de classe `CommentableImmutableText` pour représenter un texte non modifiable qui pourra être associé à une collection de commentaires qui sera de type `List<TextComment>` et d'implémentation associée d'un type concret approprié.

6#2.1 code

> Pour cette question, on se contente de définir les attributs de la classe et leurs méthodes d'accès et le(s) constructeur(s).
> 
> ```Java
> package et3.java.commentable;
> 
> import java.util.ArrayList;
> import java.util.List;
> 
> public class CommentableImmutableText
> {	
> 	private final String text;
> 	private List<TextComment> comments;
> 	
> 	/**
> 	 * Constructor of the class {@link CommentableImmutableText}
> 	 * @param text The commentable text
> 	 */
> 	public CommentableImmutableText(String text)
> 	{
> 		this.text = text;
> 		this.comments = new ArrayList<TextComment>();
> 	}
> 	
> 	/**
> 	 * This method returns the commentable text
> 	 * @return The commentable text
> 	 */
> 	public String getText()
> 	{
> 		return text;
> 	}
> 	
> 	/**
> 	 * This method returns the list of the comments associated with the commentable text
> 	 * @return The list of comments
> 	 */
> 	public List<TextComment> getComments()
> 	{
> 		return comments;
> 	}
> }
> ```

6#3 Proposez une définition pour le type `TextComment` sous forme de classe interne à la classe `CommentableImmutableText`. Anticipez de possibles problèmes de création de commentaires au moyen d'exceptions d'un type approprié.

6#3.1 Pourquoi le recours à une classe interne peut-il être approprié ici ?

> Si on définit une classe interne `TextComment` dans la classe englobante `CommentableImmutableText`, alors chaque instance de `TextComment` se réfère à une instance de `CommentableImmutableText`. Ces instances ont donc accès aux méthodes et aux attributs de `CommentableImmutableText`. Cela est approprié dans notre cas car les commentaires dépendent directement du texte.

6#3.2 code

> Lors de la création de commentaires, nous pourrions rencontrer plusieurs erreurs. Ici, on s'intéresse à deux exemples en particulier : le cas où l'auteur du commentaire n'a pas été explicité et le cas ou le commentaire ne réfère à aucun morceau du texte.
>
> Pour représenter ces erreurs, nous implémentons, en premier lieu, une classe mère général `TextCommentException` :
>
> ```Java
> package et3.java.exceptions;
> 
> public class TextCommentException extends Exception
> {
> 	private static final long serialVersionUID = 1L;
> 
> 	public TextCommentException()
> 	{
> 		super();
> 	}
> 	
> 	public TextCommentException(String message)
> 	{
> 		super(message);
> 	}
> }
> ```
>
> Puis, on défini nos deux classes d'erreur particulières : `AnonymousCommentException` qui interviendra lorsque l'auteur du commentaire n'est pas explicité, et `EmptyTextCommentException` qui interviendra si le commentaire créé ne se réfère à aucun morceau du text :
>
> ```Java
> package et3.java.exceptions;
> 
> public class EmptyTextCommentException extends TextCommentException
> {
> 	private static final long serialVersionUID = 1L;
> 
> 	public EmptyTextCommentException() 
> 	{
> 		super();
> 	}
> 	
> 	public EmptyTextCommentException(String message)
> 	{
> 		super(message);
> 	}
> }
> ```
> ```Java
> package et3.java.exceptions;
> 
> public class AnonymousCommentException extends TextCommentException
> {
> 	private static final long serialVersionUID = 1L;
> 
> 	public AnonymousCommentException() 
> 	{
> 		super();
> 	}
> 	
> 	public AnonymousCommentException(String message)
> 	{
> 		super(message);
> 	}
> }
> ```
>
> Une fois que ces erreurs sont définies, ont peut créer la classe `TextComment` à l'interieur de la classe `CommentableImmutableText` :
>
> ```Java
> 	public class TextComment
> 	{
> 		private String author;
> 		private Date creationDate;
> 		private int indexBegin;
> 		private int indexEnd;
> 		
> 		/**
> 		 * Constructor of the class {@link TextComment}
> 		 * @param author The author of the comment
> 		 * @param indexBegin The beginning index of the commented text
> 		 * @param indexEnd The ending index of the commented text
> 		 * @throws TextCommentException
> 		 */
> 		private TextComment(String author, int indexBegin, int indexEnd) throws TextCommentException
> 		{
> 			//The issues we can encounter are the following:
> 			
> 				//The text linked to the comment is empty
> 				if(indexBegin >= indexEnd)
> 				{
> 					throw new EmptyTextCommentException("The text linked to the comment is empty. The comment cannot be created.");
> 				}
> 				
> 				//The comment is anonymous
> 				if(author == null || author.equals(""))
> 				{
> 					throw new AnonymousCommentException("No author has been registered for this comment. The comment cannot be created.");
> 				}
> 				
> 			//If no exception raised, we implement 'TextComment'
> 			this.author = author;
> 			this.indexBegin = indexBegin;
> 			this.indexEnd = indexEnd;
> 			this.creationDate = new Date(System.currentTimeMillis());
> 		}
> 		
> 		@Override
> 		public String toString() 
> 		{
> 			StringBuilder comment = new StringBuilder();
> 			
> 			//We add the piece of text commented
> 			comment.append("\"" + text.substring(indexBegin, indexEnd) + "\"");
> 			
> 			//We add the coordinates of the commented text
> 			comment.append("\n[" + indexBegin + "->" + indexEnd + "]");
> 			
> 			//We add the author's name
> 			comment.append(" Author: " + this.author);
> 			
> 			//We add the creation date
> 			comment.append(" (" + this.creationDate.toString() + ")");
> 			
> 			return comment.toString();
> 		}
> 	}
> ```

6#4 Ajoutez le code nécessaire à votre classe `CommentableImmutableText` pour réaliser l'ajout de nouveaux commentaires associés au texte, lesquels pourront être simplement ajoutés par un programme principal de test, ou par un code plus complexe permettant des ajouts interactifs par l'utilisateur.

6#4.1 code

> Pour ajouter un commentaire, on se contente de créer une nouvelle instance de `TextComment`, que l'on vient ajouter à la liste de commentaires :
> 
> ```Java
> /**
>  * This methods add a comment to the list
>  * @param author The author of the comment
>  * @param indexBegin The beginning index of the comment in the text
>  * @param indexEnd The ending index of the comment in the text
>  * @throws TextCommentException
>  */
> public void addComment(String author, int indexBegin, int indexEnd) throws TextCommentException
> {
> 	this.comments.add(new TextComment(author, indexBegin, indexEnd));
> }
> ```
> 
> Pour tester cette nouvelle méthode, on créer une classe `Main` qui fera office de porte d'entrée sur l'application, et on ajoute nos test dans la méthode `main(String[] args)` :
> 
> ```Java
> package et3.java.application;
> 
> import et3.java.commentable.CommentableImmutableText;
> import et3.java.exceptions.TextCommentException;
> 
> public class Main 
> {
> 	public static void main(String[] args) 
> 	{
> 		CommentableImmutableText commentableText = new CommentableImmutableText(
> 				"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut tincidunt "
> 				+ "ligula ut libero egestas lobortis. Fusce rutrum nisi eget risus "
> 				+ "gravida, ut posuere eros tempus. Cras ut nibh sit amet neque rhoncus "
> 				+ "dapibus. Praesent in semper justo, non ornare quam. Donec euismod "
> 				+ "justo ac mi pretium facilisis. Vivamus diam justo, sagittis sit amet "
> 				+ "velit in, dictum bibendum nunc. In quis mollis justo. Fusce et nunc "
> 				+ "vestibulum, consequat massa eget, porttitor massa.");
> 		
> 		//6#4.1
> 		System.out.println("\n Question 6#4.1 \n");
> 		try 
> 		{
> 			commentableText.addComment("Anna", 100, 15);
> 			System.out.println("The Anna's comment has been added to the text.");
> 		} 
> 		catch (TextCommentException exception) 
> 		{
> 			exception.printStackTrace();
> 		}
> 		System.out.println();
> 		try 
> 		{
> 			commentableText.addComment("Bob", 210, 210);
> 			System.out.println("The Bob's comment has been added to the text.");
> 		} 
> 		catch (TextCommentException exception) 
> 		{
> 			exception.printStackTrace();
> 		}
> 		System.out.println();
> 		try 
> 		{
> 			commentableText.addComment("Charles", 6, 10);
> 			System.out.println("The Charles' comment has been added to the text.");
> 		} 
> 		catch (TextCommentException exception) 
> 		{
> 			exception.printStackTrace();
> 		}
> 		System.out.println();
> 		try 
> 		{
> 			commentableText.addComment("", 156, 214);
> 			System.out.println("The anonymous comment has been added to the text.");
> 		} 
> 		catch (TextCommentException exception) 
> 		{
> 			exception.printStackTrace();
> 		}
> 	}
> }
> ```
>
> En essayant les quatre portions de code ci-dessus, on obtient la sortie suivante :
> 
> ```
> 
>  Question 6#4.1 
> 
> et3.java.exceptions.EmptyTextCommentException: The text linked to the comment is empty. The comment cannot be created.
> 	at et3.java.commentable.CommentableImmutableText$TextComment.<init>(CommentableImmutableText.java:36)
> 	at et3.java.commentable.CommentableImmutableText.addComment(CommentableImmutableText.java:124)
> 	at et3.java.application.Main.main(Main.java:23)
> 
> et3.java.exceptions.EmptyTextCommentException: The text linked to the comment is empty. The comment cannot be created.
> 	at et3.java.commentable.CommentableImmutableText$TextComment.<init>(CommentableImmutableText.java:36)
> 	at et3.java.commentable.CommentableImmutableText.addComment(CommentableImmutableText.java:124)
> 	at et3.java.application.Main.main(Main.java:33)
> 
> The Charles' comment has been added to the text.
> 
> et3.java.exceptions.AnonymousCommentException: No author has been registered for this comment. The comment cannot be created.
> 	at et3.java.commentable.CommentableImmutableText$TextComment.<init>(CommentableImmutableText.java:42)
> 	at et3.java.commentable.CommentableImmutableText.addComment(CommentableImmutableText.java:124)
> 	at et3.java.application.Main.main(Main.java:53)
> ```

6#5 Implémentez une stratégie de tri par défaut pertinente en typant votre type TextComment avec l'interface `java.lang.Comparable<T>`  et en implémentant cette interface.

6#5.1 L'interface `java.lang.Comparable<T>` est un type paramétré. Qu'est-ce que cela apporte dans ce cas précis relativement à l'ancienne définition non paramétrée de cette interface java.lang.Comparable ?

>
>

6#5.2 Quels sont les avantages et les limitations d'un recours à une définition de la comparaison entre instances interne à une classe telle que permise par l'interface `java.lang.Comparable<T>` ?

>
>

6#5.3 code

> On ajoute l'interface `java.lang.Comparable<T>` à la définition de notre classe :
> 
> ```Java
> public class TextComment implements Comparable<TextComment>
> ```
> 
> Puis, on implémente la méthode `compareTo(TextComment anotherTextComment)` en choisissant, ici, un tri par ancienneté :
> 
> ```Java
> @Override
> /**
>  * Compare this 'TextComment' with another one.
>  * This method will return:
>  * - 0 if the two 'TextComment' were created at the same time
>  * - a positive value if this 'TextComment' is older than the other one
>  * - a negative value if this 'TextComment' is more recent than the other one
>  */
> public int compareTo(TextComment anotherTextComment) 
> {
> 	//By default, we compare 'TextComment' instances by seniority
> 	return - this.creationDate.compareTo(anotherTextComment.creationDate);
> }
> ```

6#6 Ajoutez dans votre classe CommentableImmutableText une méthode permettant de réaliser l'affichage détaillé des commentaires (dont l'extrait de texte commenté) et prenant comme paramètre une stratégie de tri de commentaires : `displayComments(CommentSortStrategy strategy)`. En testant sur les valeurs possibles de la stratégie de tri, implémentez initialement le tri avec la stratégie par défaut (reposant sur le tri (déjà) implémenté dans la classe `TextComment`). Assurez-vous que le choix d'une stratégie définie dans l'énumération `CommentSortStrategy` mais non implémentée concrètement mènera à la levée d'une exception d'un type approprié. Le tri lui-même pourra être effectué à l'aide de la méthode statique `java.util.Collections.sort(List<T> list)` .

6#6.1 Quel va être l'effet du recours à la méthode `sort(List<T> list)` sur la liste transmise en paramètre ? On suppose néanmoins que cela peut être souhaitable ici : pourquoi ? Si l'on ne souhaitait pas cet effet, que faudrait-il faire ?

> 
> 

6#6.2 code

> ```Java
> /**
>  * This methods displays the comments linked to the text
>  */
> public void displayComments()
> {
> 	for(TextComment comment : comments)
> 	{
> 		System.out.println(comment + "\n");
> 	}
> }
> 
> /**
>  * This methods displays the comments linked to the text after having sorted them
>  * @param sortingStrategy The sorting strategy
>  * @throws SortingStrategyNotImplementedException 
>  */
> public void displaySortedComments(CommentSortingStrategy commentSortStrategy) throws SortingStrategyNotImplementedException
> {
> 	//If we want to sort comments by seniority
> 	if(commentSortStrategy.equals(CommentSortingStrategy.BySeniority))
> 	{
> 		System.out.println(CommentSortingStrategy.BySeniority.getDescription() + "\n");
> 		comments.sort(null);
> 		displayComments();
> 	}
> 	//If we want to sort comments by position in the linked text
> 	if(commentSortStrategy.equals(CommentSortingStrategy.ByPosition))
> 	{
> 		throw new SortingStrategyNotImplementedException("The position sorting strategy hax not been implemented yet. Comments cannot be displayed.");
> 	}
> 	//If we want to sort comments by name and seniority
> 	if(commentSortStrategy.equals(CommentSortingStrategy.ByNameAndSeniority))
> 	{
> 		throw new SortingStrategyNotImplementedException("The name and seniority sorting strategy hax not been implemented yet. Comments cannot be displayed.");
> 	}
> 	//If we want to sort comments by decreasing length
> 	if(commentSortStrategy.equals(CommentSortingStrategy.ByLength))
> 	{
> 		throw new SortingStrategyNotImplementedException("The length sorting strategy hax not been implemented yet. Comments cannot be displayed.");
> 	}
> }
> ```
> 
> ```Java
> //6#6.2
> System.out.println("\n Question 6#6.2 \n");
> try 
> {
> 	commentableText.displaySortedComments(CommentSortingStrategy.BySeniority);
> } 
> catch (SortingStrategyNotImplementedException exception) 
> {
> 	exception.printStackTrace();
> }
> try 
> {
> 	commentableText.displaySortedComments(CommentSortingStrategy.ByPosition);
> } 
> catch (SortingStrategyNotImplementedException exception) 
> {
> 	exception.printStackTrace();
> }
> System.out.println();
> try 
> {
> 	commentableText.displaySortedComments(CommentSortingStrategy.ByNameAndSeniority);
> } 
> catch (SortingStrategyNotImplementedException exception) 
> {
> 	exception.printStackTrace();
> }
> System.out.println();
> try 
> {
> 	commentableText.displaySortedComments(CommentSortingStrategy.ByLength);
> } 
> catch (SortingStrategyNotImplementedException exception) 
> {
> 	exception.printStackTrace();
> }
> ```
> 
> ```
>  Question 6#6.2 
> 
> Elements are sorted from the most recent to the oldest.
> 
> " elit. Ut tincidunt "
> [50->70] Damien (Fri Apr 24 10:14:28 CEST 2020)
>
> "ipsu"
> [6->10] Charles (Fri Apr 24 10:14:28 CEST 2020)
> 
> et3.java.exceptions.SortingStrategyNotImplementedException: The position sorting strategy hax not been implemented yet. Comments cannot be displayed.
> 	at et3.java.commentable.CommentableImmutableText.displaySortedComments(CommentableImmutableText.java:158)
> 	at et3.java.application.Main.main(Main.java:87)
> 
> et3.java.exceptions.SortingStrategyNotImplementedException: The name and seniority sorting strategy hax not been implemented yet. Comments cannot be displayed.
> 	at et3.java.commentable.CommentableImmutableText.displaySortedComments(CommentableImmutableText.java:163)
> 	at et3.java.application.Main.main(Main.java:96)
> 
> et3.java.exceptions.SortingStrategyNotImplementedException: The length sorting strategy hax not been implemented yet. Comments cannot be displayed.
> 	at et3.java.commentable.CommentableImmutableText.displaySortedComments(CommentableImmutableText.java:168)
> 	at et3.java.application.Main.main(Main.java:105)
> ```

6#7 Implémentez à présent l'ensemble des autres stratégies de tri de commentaires prévues. Pour cela, enrichissez la méthode displayComments en associant chaque valeur de CommentSortStrategy à la définition d'une classe anonyme implémentant java.util.Comparator<E> , et en ayant désormais recours pour le tri lui-même à la méthode statique java.util.Collections.sort(List<T> list, Comparator<? super T> comparator) .

6#7.1 Pourquoi le recours à la définition d'une classe anonyme implémentant java.util.Comparator<E> peut-il être approprié ici ? Quelle pourrait être une alternative à l'utilisation d'une classe anonyme, pour quels avantages et inconvénients ?

>
>

6#7.2 code

> ```Java
> /**
>  * This methods displays the comments linked to the text after having sorted them
>  * @param sortingStrategy The sorting strategy
>  * @throws SortingStrategyNotImplementedException 
>  */
> public void displaySortedComments(CommentSortingStrategy commentSortStrategy) throws SortingStrategyNotImplementedException
> {
> 	//If we want to sort comments by seniority
> 	if(commentSortStrategy.equals(CommentSortingStrategy.BySeniority))
> 	{
> 		System.out.println(CommentSortingStrategy.BySeniority.getDescription() + "\n");
> 		
> 		comments.sort(new Comparator<TextComment>() 
> 		{
> 			@Override
> 			public int compare(TextComment textComment1, TextComment textComment2) 
> 			{
> 				//- 0 if textComment1 and textComment2 were created at the same time
> 				//	-> textComment1 and textComment2 will be placed indifferently
> 				//- a negative value if textComment1 is more recent than textComment2
> 				//	-> textComment1 will be placed before textComment2
> 				//- a positive value if textComment1 is older than textComment2 
> 				//	-> textComment1 will be placed after textComment2
> 				return - textComment1.creationDate.compareTo(textComment2.creationDate);
> 			}
> 		});
> 		
> 		displayComments();
> 	}
> 	//If we want to sort comments by position in the linked text
> 	if(commentSortStrategy.equals(CommentSortingStrategy.ByPosition))
> 	{
> 		System.out.println(CommentSortingStrategy.ByPosition.getDescription() + "\n");
> 		
> 		comments.sort(new Comparator<TextComment>() 
> 		{
> 			@Override
> 			public int compare(TextComment textComment1, TextComment textComment2) 
> 			{
> 				//If textComment1 end before textComment2
> 				if(textComment1.indexEnd < textComment2.indexEnd)
> 				{
> 					//textComment1 will be placed before textComment2
> 					return -1;
> 				}
> 				//If textComment2 end before textComment1
> 				else if(textComment2.indexEnd < textComment1.indexEnd)
> 				{
> 					//textComment1 will be placed after textComment2
> 					return 1;
> 				}
> 				//If textComment1 and textComment2 end at the same index
> 				else
> 				{
> 					//textComment1 and textComment2 will be placed indifferently
> 					return 0;
> 				}
> 			}
> 		});
> 		
> 		displayComments();
> 	}
> 	//If we want to sort comments by name and seniority
> 	if(commentSortStrategy.equals(CommentSortingStrategy.ByNameAndSeniority))
> 	{
> 		System.out.println(CommentSortingStrategy.ByNameAndSeniority.getDescription() + "\n");
> 		
> 		comments.sort(new Comparator<TextComment>() 
> 		{
> 			@Override
> 			public int compare(TextComment textComment1, TextComment textComment2) 
> 			{
> 				//- 0 if textComment1 and textComment2 were created by the same author
> 				//- a negative value if the author of textComment1 is lexicographically greater than the author of textComment2
> 				//- a positive value if the author of textComment1 is lexicographically lower than the author of textComment2
> 				int authorComparison = textComment1.author.compareTo(textComment2.author);
> 				
> 				if(authorComparison == 0)
> 				{
> 					//- 0 if textComment1 and textComment2 were created at the same time
> 					//	-> textComment1 and textComment2 will be placed indifferently
> 					//- a negative value if textComment1 is more recent than textComment2
> 					//	-> textComment1 will be placed before textComment2
> 					//- a positive value if textComment1 is older than textComment2 
> 					//	-> textComment1 will be placed after textComment2
> 					return - textComment1.creationDate.compareTo(textComment2.creationDate);
> 				}
> 				else
> 				{
> 					//- 0
> 					//	-> textComment1 and textComment2 will be placed indifferently
> 					//- a negative value
> 					// 	-> textComment1 will be placed before textComment2
> 					//- a positive value
> 					// 	-> textComment1 will be placed after textComment2
> 					return authorComparison;
> 				}
> 			}
> 		});
> 		
> 		displayComments();
> 	}
> 	//If we want to sort comments by decreasing length
> 	if(commentSortStrategy.equals(CommentSortingStrategy.ByLength))
> 	{
> 		System.out.println(CommentSortingStrategy.ByLength.getDescription() + "\n");
> 		
> 		comments.sort(new Comparator<TextComment>() 
> 		{
> 			@Override
> 			public int compare(TextComment textComment1, TextComment textComment2) 
> 			{
> 				int length1 = textComment1.indexEnd - textComment1.indexBegin;
> 				int length2 = textComment2.indexEnd - textComment2.indexBegin;
> 				
> 				//If textComment1 comments a larger text than textComment2
> 				if(length1 > length2)
> 				{
> 					//textComment1 will be placed after textComment2
> 					return 1;
> 				}
> 				//If textComment1 comments a shorter text than textComment2
> 				else if(length2 > length1)
> 				{
> 					//textComment1 will be placed before textComment2
> 					return -1;
> 				}
> 				//If textComment1 and textComment2 comment the same length of text
> 				else
> 				{
> 					//textComment1 and textComment2 will be placed indifferently
> 					return 0;
> 				}
> 			}
> 		});
> 		
> 		displayComments();
> 	}
> }
> ```

6#8. Testez votre programme sur un jeu de données et pour différentes stratégies de tri des commentaires.

6#8.1 trace d'exécution

> Afin de tester les différentes stratégies de tri, nous commençons par ajouter quelques nouveaux commentaires pertinents pour le test et nous essayons chaque tri séparemment dans le `main(String[] args)` :
> 
> ```Java
> //6#7.2
> System.out.println("\n Question 6#7.2 \n");
> try 
> {
> 	commentableText.addComment("Felicie", 108, 251);
> 	System.out.println("The Felicie's comment has been added to the text.");
> } 
> catch (TextCommentException exception) 
> {
> 	exception.printStackTrace();
> }
> System.out.println();
> try 
> {
> 	commentableText.addComment("Emma", 0, 4);
> 	System.out.println("The Emma's comment has been added to the text.");
> } 
> catch (TextCommentException exception) 
> {
> 	exception.printStackTrace();
> }
> System.out.println();
> try 
> {
> 	commentableText.addComment("Charles", 108, 251);
> 	System.out.println("The Charles' comment has been added to the text.");
> } 
> catch (TextCommentException exception) 
> {
> 	exception.printStackTrace();
> }
> System.out.println();
> try 
> {
> 	commentableText.displaySortedComments(CommentSortingStrategy.BySeniority);
> } 
> catch (SortingStrategyNotImplementedException exception) 
> {
> 	exception.printStackTrace();
> }
> try 
> {
> 	commentableText.displaySortedComments(CommentSortingStrategy.ByPosition);
> } 
> catch (SortingStrategyNotImplementedException exception) 
> {
> 	exception.printStackTrace();
> }
> try 
> {
> 	commentableText.displaySortedComments(CommentSortingStrategy.ByNameAndSeniority);
> } 
> catch (SortingStrategyNotImplementedException exception) 
> {
> 	exception.printStackTrace();
> }
> try 
> {
> 	commentableText.displaySortedComments(CommentSortingStrategy.ByLength);
> } 
> catch (SortingStrategyNotImplementedException exception) 
> {
> 	exception.printStackTrace();
> }
> ```
> 
> A l'éxécution, on obtient les traces suivantes :
> 
> ```
> 
>  Question 6#7.2 
> 
> The Felicie's comment has been added to the text.
> 
> The Emma's comment has been added to the text.
> 
> The Charles' comment has been added to the text.
> 
> Elements are sorted from the most recent to the oldest.
> 
> "ce rutrum nisi eget risus gravida, ut posuere eros tempus. Cras ut nibh sit amet neque rhoncus dapibus. Praesent in semper justo, non ornare qu"
> [108->251] Felicie (Fri Apr 24 11:59:02 CEST 2020)
> 
> "Lore"
> [0->4] Emma (Fri Apr 24 11:59:02 CEST 2020)
> 
> "ce rutrum nisi eget risus gravida, ut posuere eros tempus. Cras ut nibh sit amet neque rhoncus dapibus. Praesent in semper justo, non ornare qu"
> [108->251] Charles (Fri Apr 24 11:59:02 CEST 2020)
> 
> "ipsu"
> [6->10] Charles (Fri Apr 24 11:59:01 CEST 2020)
> 
> " elit. Ut tincidunt "
> [50->70] Damien (Fri Apr 24 11:59:01 CEST 2020)
> 
> Elements are sorted from the closest to the begining to the closest to the end.
> 
> "Lore"
> [0->4] Emma (Fri Apr 24 11:59:02 CEST 2020)
> 
> "ipsu"
> [6->10] Charles (Fri Apr 24 11:59:01 CEST 2020)
> 
> " elit. Ut tincidunt "
> [50->70] Damien (Fri Apr 24 11:59:01 CEST 2020)
> 
> "ce rutrum nisi eget risus gravida, ut posuere eros tempus. Cras ut nibh sit amet neque rhoncus dapibus. Praesent in semper justo, non ornare qu"
> [108->251] Felicie (Fri Apr 24 11:59:02 CEST 2020)
> 
> "ce rutrum nisi eget risus gravida, ut posuere eros tempus. Cras ut nibh sit amet neque rhoncus dapibus. Praesent in semper justo, non ornare qu"
> [108->251] Charles (Fri Apr 24 11:59:02 CEST 2020)
> 
> Elements are sorted by users' alphabetical order and senority, from the most recent to the oldest.
> 
> "ce rutrum nisi eget risus gravida, ut posuere eros tempus. Cras ut nibh sit amet neque rhoncus dapibus. Praesent in semper justo, non ornare qu"
> [108->251] Charles (Fri Apr 24 11:59:02 CEST 2020)
> 
> "ipsu"
> [6->10] Charles (Fri Apr 24 11:59:01 CEST 2020)
> 
> " elit. Ut tincidunt "
> [50->70] Damien (Fri Apr 24 11:59:01 CEST 2020)
> 
> "Lore"
> [0->4] Emma (Fri Apr 24 11:59:02 CEST 2020)
> 
> "ce rutrum nisi eget risus gravida, ut posuere eros tempus. Cras ut nibh sit amet neque rhoncus dapibus. Praesent in semper justo, non ornare qu"
> [108->251] Felicie (Fri Apr 24 11:59:02 CEST 2020)
> 
> Elements are sorted by decreasing length.
> 
> "ipsu"
> [6->10] Charles (Fri Apr 24 11:59:01 CEST 2020)
> 
> "Lore"
> [0->4] Emma (Fri Apr 24 11:59:02 CEST 2020)
> 
> " elit. Ut tincidunt "
> [50->70] Damien (Fri Apr 24 11:59:01 CEST 2020)
> 
> "ce rutrum nisi eget risus gravida, ut posuere eros tempus. Cras ut nibh sit amet neque rhoncus dapibus. Praesent in semper justo, non ornare qu"
> [108->251] Charles (Fri Apr 24 11:59:02 CEST 2020)
> 
> "ce rutrum nisi eget risus gravida, ut posuere eros tempus. Cras ut nibh sit amet neque rhoncus dapibus. Praesent in semper justo, non ornare qu"
> [108->251] Felicie (Fri Apr 24 11:59:02 CEST 2020)
> ```

6#9 On s'intéresse à présent à déterminer la popularité des fragments de texte commentés par les lecteurs. Pour cela, on considérera un fragment de texte comme d'autant plus populaire qu'il a été sélectionné exactement  de nombreuses fois dans des commentaires. Proposez une solution simple  via une méthode de votre classe CommentableImmutableText permettant d'afficher les fragments commentés du texte par popularité décroissante.

6#9.1 code

> Nous allons classifier les portions de texte commentées dans une [Map]{https://docs.oracle.com/javase/8/docs/api/java/util/Map.html} afin de pouvoir itérer sur leur nombre de commentaire :
> 
> ```Java
> /**
>  * This method displays the pieces of text that are commented from the most commented to the less commented
>  */
> public void displayCommentedTextByPopularity()
> {
> 	//We create a map containing the commented texts and their number of comments
> 	Map<String, Integer> commentedTexts = new HashMap<>();
> 	
> 	//For each comment
> 	for(TextComment comment : comments)
> 	{
> 		String commentedText = this.text.substring(comment.indexBegin, comment.indexEnd);
> 		
> 		//If the commented text has already been added to the map
> 		if(commentedTexts.containsKey(commentedText))
> 		{
> 			//We increment its value
> 			int previousCount = commentedTexts.get(commentedText);
> 			commentedTexts.replace(commentedText, previousCount + 1);
> 		}
> 		//If the commented text has not already been added to the map
> 		else
> 		{
> 			//We add it to the map with a value of 1
> 			commentedTexts.put(commentedText, 1);
> 		}
> 	}
> 	
> 	//We sort the map entries in a list
> 	List<Entry<String, Integer>> commentedTextsList = new ArrayList<>(commentedTexts.entrySet());
> 	commentedTextsList.sort(new Comparator<Entry<String, Integer>>() {
> > 		@Override
> 		public int compare(Entry<String, Integer> entry1, Entry<String, Integer> entry2)
> 		{
> 			//if the value of entry1 is greater than the value of entry2
> 			if(entry1.getValue() > entry2.getValue())
> 			{
> 				//entry1 will be placed before entry2
> 				return -1;
> 			}
> 			//if the value of entry1 is lower than the value of entry2
> 			if(entry1.getValue() < entry2.getValue())
> 			{
> 				//entry1 will be placed after entry2
> 				return 1;
> 			}
> 			//if entry1 and entry2 have the same value
> 			else
> 			{
> 				//entry1 and entry2 will be placed indifferently
> 				return 0;
> 			}
> 		}
> 	});
> 	
> 	//We display the commented texts
> 	for(Entry<String, Integer> entry : commentedTextsList)
> 	{
> 		System.out.println("\"" + entry.getKey() + "\" was commented " + entry.getValue() + " time(s).");
> 	}
> }
> ```

6#9.2 trace d'exécution

> On ajoute les lignes suivantes à notre `main(String[] args)` :
>
> ```Java
> //6#9.2
> System.out.println("\n Question 6#9.2 \n");
> commentableText.displayCommentedTextByPopularity();
> ```
> 
> On obtient les traces suivantes lors de l'éxécution :
> 
> ```
> "ce rutrum nisi eget risus gravida, ut posuere eros tempus. Cras ut nibh sit amet neque rhoncus dapibus. Praesent in semper justo, non ornare qu" was commented 2 time(s).
> " elit. Ut tincidunt " was commented 1 time(s).
> "Lore" was commented 1 time(s).
> "ipsu" was commented 1 time(s).
> ```
