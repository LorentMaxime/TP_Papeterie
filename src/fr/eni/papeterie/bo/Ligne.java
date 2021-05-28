package fr.eni.papeterie.bo;

public class Ligne extends Article{
    protected int qte;
    protected Article article;

    public Ligne(Article aticle, int qte){
       this.article = aticle;
       this.qte = qte;
    }

    public Article getArticle() {
        return article;
    }

    public int getQte() {
        return qte;
    }
// Retourne le prix unitaire d'un article
    public float getPrix() {
        return this.article.getPrixUnitaire();
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    @Override
    public String toString() {
        return "Ligne{" +
                "qte=" + qte +
                ", article=" + article +
                '}';
    }
}
