package fr.eni.papeterie.bll;

import fr.eni.papeterie.bo.Article;
import fr.eni.papeterie.bo.Ramette;
import fr.eni.papeterie.bo.Stylo;
import fr.eni.papeterie.dal.DAOFactory;
import fr.eni.papeterie.dal.jdbc.ArticleDAO;
import fr.eni.papeterie.dal.jdbc.DALException;

import java.util.List;

public class CatalogueManager {
    //Je crée une nouvelle instance d'articleDAO
    ArticleDAO articleDAO = DAOFactory.getArticleDAO();

    // Je crée une instance du type de mon manager en privé
    private static CatalogueManager instance;

    private CatalogueManager() throws BLLException {
    }

    public static CatalogueManager getInstance() throws BLLException {
        if (instance == null) {
            instance = new CatalogueManager();
        }
        return instance;
    }

    public List<Article> getCatalogue() throws BLLException {
        return this.articleDAO.selectAll();
    }

    public void addArticle(Article a) throws BLLException {
        this.validerArticles(a);
        this.articleDAO.update(a);
    }

    public void updateArticle(Article a) throws BLLException {
        this.validerArticles(a);
        this.articleDAO.update(a);
    }

    public void removeArticle(int index) throws BLLException, DALException {
        try{
            this.articleDAO.delete(index);
        }catch (DALException e){
            throw new BLLException("Erreur dans la BLL.");
        }
    }

    public Article getArticles(int index) throws DALException {
        return this.articleDAO.selectById(index);
    }

    private void validerArticles(Article a) throws BLLException {
        if (a == null) {
            throw new BLLException("L'article est null");
        }

        if (a instanceof Ramette && ((Ramette) a).getGrammage() <= 0) {
            throw new BLLException("Son grammage n'est pa valide");
        }

        if (a instanceof Stylo && (((Stylo) a).getCouleur() == null || ((Stylo) a).getCouleur().trim().length() == 0)) {
            throw new BLLException("La couleur n'est pas valide");
        }

        if (a.getReference() == null || a.getReference().trim().length() == 0) {
            throw new BLLException("La reference est obligatoire");
        }
    }
}