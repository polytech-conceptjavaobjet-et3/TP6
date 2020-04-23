package et3.java.sort;

public enum CommentSortStrategy
{
	BySeniority("Le tri est realise par anciennete"),							//Par anciennete
	ByPosition("Le tri est realise par position dans le texte"),				//Par position
	ByNameAndSeniority("Le tri est realise par nom et anciennete"),				//Par nom et anciennete
	ByLength ("Le tri est realise par longueur de commentaire decroissante");	//Par longueur
	
	private String description;
	
	/**
	 * 
	 * @param description
	 */
    CommentSortStrategy(String description)
    {
        this.description=description;
    }

    /**
     * 
     * @return
     */
    public String getDescription()
    {
        return description;
    }
}
