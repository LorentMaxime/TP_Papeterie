package fr.eni.papeterie.dal.jdbc;

import fr.eni.papeterie.bo.Article;
import fr.eni.papeterie.bo.Ramette;
import fr.eni.papeterie.bo.Stylo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe permettant d'agir sur la base de donnée pour
 * selectionner tous les articles
 * selectionner par l'id de l'article
 * supprimer une ligne dans la base de donnée
 * inserer une ligne dans la base de donnée
 * mettre à jour une ligne dans la base de donnée
 *
 */

public class ArticleDAOJdbcImpl implements ArticleDAO {
    private final String SELECT_BY_ID = "SELECT * FROM Articles WHERE idArticle=?";
    private final String SQL_DELETE = "DELETE FROM Articles WHERE idArticle=?";
    private final String SQL_UPDATE = "UPDATE Articles set " +
            "reference=?,marque=?,designation=?,prixUnitaire=?,qteStock=?,grammage=?,couleur=?" +
            " WHERE idArticle=?";
    private final String SQL_INSERT = "INSERT INTO Articles " +
            "(reference,marque, designation,prixUnitaire,qteStock,grammage,couleur,type) " +
            "VALUES(?,?,?,?,?,?,?,?);";

    /**
     * Mehode qui selectionne tous les article de la base article
     * @return une liste d'articles
     */
    @Override
    public List<Article> selectAll(){
        List<Article>articleList = new ArrayList<>();
        try(Connection connection = JdbcTools.recupConnection();) {

            Statement etat = connection.createStatement();
            String sql = "SELECT * FROM article";
            etat.executeQuery(sql);
        } catch (SQLException throwables) {
            throwables.getMessage();
        }
        return articleList;
    }

    /**
     * methode qui selectionne un article par son identifiant
     * @param id
     * Si l'article a une couleur c'est un crayon
     * Si l'article a un poid c'est une ramette
     * @return l'article soit crayon soit ramette
     */
    @Override
    public Article selectById(int id) { // Codé en requete non preparée
        Article article = null;
        try (
                Connection connection = JdbcTools.recupConnection();
                Statement etat = connection.createStatement()
        ) {
            String sql = SELECT_BY_ID;
            ResultSet rs = etat.executeQuery(sql);
            if (rs.next()) {
                if (rs.getString("type").trim().equalsIgnoreCase("RAMETTE")) {
                    article = new Ramette(
                            rs.getInt("idArticle"),
                            rs.getString("marque"),
                            rs.getString("reference"),
                            rs.getString("designation"),
                            rs.getFloat("prixUnitaire"),
                            rs.getInt("qteStock"),
                            rs.getInt("grammage")
                    );
                }
                if (rs.getString("type").trim().equalsIgnoreCase("STYLO")) {
                    article = new Stylo(
                            rs.getInt("idArticle"),
                            rs.getString("marque"),
                            rs.getString("reference"),
                            rs.getString("designation"),
                            rs.getFloat("prixUnitaire"),
                            rs.getInt("qteStock"),
                            rs.getString("couleur")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return article;
    }

    /**
     * methode qui supprime un article en base de donnée
     * @param id
     * @throws SQLException
     */
    @Override
    public void delete(int id) throws DALException { // requete préparée
        try (Connection connection = JdbcTools.recupConnection()) {
            PreparedStatement reqPreparee = connection.prepareStatement(this.SQL_DELETE);
            reqPreparee.setInt(1, id);
            reqPreparee.executeUpdate();
        } catch (SQLException e) {
            throw new DALException("Erreur dans la méthode delete().");
        }
    }

    /**
     * methode qui permet d'inserer un article en base de donnée,
     * Si c'est un crayon il faut ajouter la couleur aux autres attributs communs et mettre le grammage à null
     * Si c'est une ramette il faut ajouter le grammage aux autres attributs communs et la couleur à null
     * @param article
     * traite les exceptions
     */
    @Override
    public void insert(Article article) { // Requete préparée
        try (
                Connection connection = JdbcTools.recupConnection();
                PreparedStatement etat = connection.prepareStatement(this.SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
        ) {
            etat.setString(1, article.getReference());
            etat.setString(2, article.getMarque());
            etat.setString(3, article.getDesignation());
            etat.setFloat(4, article.getPrixUnitaire());
            etat.setInt(5, article.getQteStock());

            if (article instanceof Ramette) {
                etat.setInt(6, ((Ramette) article).getGrammage());
                etat.setString(8, "RAMETTE");
            }
            if (article instanceof Stylo) {
                etat.setString(7, ((Stylo) article).getCouleur());
                etat.setString(8, "STYLO");
            }

            etat.executeUpdate();
            ResultSet rs = etat.getGeneratedKeys(); // Je récupère l'id auto généré par la méthode insert
            if (rs.next()) {
                int id = rs.getInt(1);
                article.setIdArticle(id); // Et je le met dans l'article en utilisant le Setter
                // article.setIdArticle(rs.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * methode qui permet de mettre à jour un article en base de donnée
     * @param article
     * Si c'est un crayon prend en compte la couleur en plus des autres attributs
     * Si c'est une ramette prend en compte le grammage en plus des autres attibuts
     * traite les exceptions
     */
    @Override
    public void update(Article article) { // Requete préparée
        try(Connection connection = JdbcTools.recupConnection()) {
            PreparedStatement etat = connection.prepareStatement(this.SQL_UPDATE);
            etat.setString(1, article.getReference());
            etat.setString(2, article.getMarque());
            etat.setString(3, article.getDesignation());
            etat.setFloat(4, article.getPrixUnitaire());
            etat.setInt(5, article.getQteStock());
            etat.setInt(8, article.getIdArticle());
            if (article instanceof Stylo) {
                etat.setString(7,  ((Stylo) article).getCouleur());
                etat.setNull(6, Types.NULL);
            }
            if (article instanceof Ramette) {
                etat.setInt(6, ((Ramette) article).getGrammage());
                etat.setNull(7, Types.NULL);
            }
            etat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
