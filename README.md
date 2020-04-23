Programmation Java @ Et3
<br>
Polytech Paris-Saclay | 2019-20

___

# TP6

On souhaite mettre en place un système de commentaires très simple sur un texte statique. Concrètement, un lecteur doit pouvoir ajouter un commentaire libre portant sur un fragment continu d'un document. Les commentaires collectés pourront ensuite être affichés selon différentes stratégies de tri.

Un commentaire pour un texte donné sera donc composé d'un utilisateur, d'un indice de caractère de début et de fin, ainsi que d'une date de création .

Un certain nombre de stratégies de tri devront être anticipées, notamment :

	- afficher les commentaires par ancienneté
	- afficher les commentaires par position de début dans le texte (en privilégiant les commentaires se terminant d'abord)
	- afficher les commentaires par nom d'utilisateur (tri alphanumérique) puis par ancienneté
	- afficher les commentaires par longueur de texte décroissante


6#1. Proposez une classe d'énumération pour représenter les stratégies de tri des commentaires . Chaque valeur d'énumération sera associée à une brève description textuelle qui sera affichée lorsqu'un tri sera réalisé.

6#1.1 code

> ```Java
> ```

6#2 Proposez un début de classe CommentableImmutableText pour représenter un texte non modifiable qui pourra être associé à une collection de commentaires qui sera de type List<TextComment> et d'implémentation associée d'un type concret approprié.

6#2.1 code

> ```Java
> ```

6#3 Proposez une définition pour le type TextComment sous forme de classe interne à la classe  CommentableImmutableText. Anticipez de possibles problèmes de création de commentaires  au moyen d'exceptions d'un type approprié.

6#3.1 Pourquoi le recours à une classe interne peut-il être approprié ici ?

>
>

6#3.2 code

> ```Java
> ```

6#4 Ajoutez le code nécessaire à votre classe CommentableImmutableText pour réaliser l'ajout de nouveaux commentaires associés au texte, lesquels pourront être simplement ajoutés par un programme principal de test, ou par un code plus complexe permettant des ajouts interactifs par l'utilisateur.

6#4.1 code

> ```Java
> ```

6#5 Implémentez une stratégie de tri par défaut pertinente en typant votre type TextComment avec l'interface java.lang.Comparable<T>  et en implémentant cette interface.

6#5.1 L'interface java.lang.Comparable<T> est un type paramétré. Qu'est-ce que cela apporte dans ce cas précis relativement à l'ancienne définition non paramétrée de cette interface java.lang.Comparable ?

>
>

6#5.2 Quels sont les avantages et les limitations d'un recours à une définition de la comparaison entre instances interne à une classe telle que permise par l'interface java.lang.Comparable<T> ?

>
>

6#5.3 code

> ```Java
> ```

6#6 Ajoutez dans votre classe CommentableImmutableText une méthode permettant de réaliser l'affichage détaillé des commentaires (dont l'extrait de texte commenté) et prenant comme paramètre une stratégie de tri de commentaires : displayComments(CommentSortStrategy strategy). En testant sur les valeurs possibles de la stratégie de tri, implémentez initialement le tri avec la stratégie par défaut (reposant sur le tri (déjà) implémenté dans la classe TextComment). Assurez-vous que le choix d'une stratégie définie dans l'énumération CommentSortStrategy mais non implémentée concrètement mènera à la levée d'une exception d'un type approprié. Le tri lui-même pourra être effectué à l'aide de la méthode statique java.util.Collections.sort(List<T> list) .

6#6.1 Quel va être l'effet du recours à la méthode sort(List<T> list)sur la liste transmise en paramètre ? On suppose néanmoins que cela peut être souhaitable ici : pourquoi ?Si l'on ne souhaitait pas cet effet, que faudrait-il faire ?

6#6.2 code

> ```Java
> ```

6#7 Implémentez à présent l'ensemble des autres stratégies de tri de commentaires prévues. Pour cela, enrichissez la méthode displayComments en associant chaque valeur de CommentSortStrategy à la définition d'une classe anonyme implémentant java.util.Comparator<E> , et en ayant désormais recours pour le tri lui-même à la méthode statique java.util.Collections.sort(List<T> list, Comparator<? super T> comparator) .

6#7.1 Pourquoi le recours à la définition d'une classe anonyme implémentant java.util.Comparator<E> peut-il être approprié ici ? Quelle pourrait être une alternative à l'utilisation d'une classe anonyme, pour quels avantages et inconvénients ?

>
>

6#7.2 code

> ```Java
> ```

6#8. Testez votre programme sur un jeu de données et pour différentes stratégies de tri des commentaires.

6#8.1 trace d'exécution

> ```
> ```

6#9 On s'intéresse à présent à déterminer la popularité des fragments de texte commentés par les lecteurs. Pour cela, on considérera un fragment de texte comme d'autant plus populaire qu'il a été sélectionné exactement  de nombreuses fois dans des commentaires. Proposez une solution simple  via une méthode de votre classe CommentableImmutableText permettant d'afficher les fragments commentés du texte par popularité décroissante.

6#9.1 code

> ```Java
> ```

6#9.2 trace d'exécution

> ```
> ```
