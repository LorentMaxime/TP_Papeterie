package fr.eni.papeterie.dal.jdbc;

import fr.eni.papeterie.bo.Article;

import java.util.List;

public interface ArticleDAO {
    List<Article> selectAll();
    Article selectById(int id)throws DALException;
    void delete(int id) throws DALException;
    void insert(Article article);
    void update(Article article);

}
