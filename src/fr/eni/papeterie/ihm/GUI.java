package fr.eni.papeterie.ihm;

import fr.eni.papeterie.bll.BLLException;
import fr.eni.papeterie.bll.CatalogueManager;
import fr.eni.papeterie.bo.Article;
import fr.eni.papeterie.bo.Couleur;
import fr.eni.papeterie.bo.Ramette;
import fr.eni.papeterie.bo.Stylo;
import fr.eni.papeterie.dal.jdbc.DALException;
import org.w3c.dom.css.RGBColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GUI extends JFrame {

    private JPanel panneauPrincipal;
    private JPanel panneauType;
    private JPanel panneauGrammage;
    private JPanel panneauCouleur;
    // nom des elements en colonnes
    private JLabel lblRef;
    private JLabel lblDesi;
    private JLabel lblMarque;
    private JLabel lblStock;
    private JLabel lblPrix;
    private JLabel lblType;
    private JLabel lblGrammage;
    private JLabel lblCouleur;
    //JTexteField
    private JTextField champTxtRef;
    private JTextField champTxtDesi;
    private JTextField champTxtMarque;
    private JTextField champTxtStock;
    private JTextField champTxtPrix;
    //JRadioButon
    private JRadioButton radioRamette;
    private JRadioButton radioStylo;
    //JCheckBox
    private JCheckBox ckbQuatreVingtGramme;
    private JCheckBox ckbCentGramme;
    //JComboBox
    private JComboBox comboBoxCouleur;
    private JComboBox<String> cboCouleur;
    //Boutons
    private JPanel panelBoutons;
    private JButton btnPrecedent;
    private JButton btnNouveau;
    private JButton btnEnregistrer;
    private JButton btnSupprimer;
    private JButton btnSuivant;
    // j'instancie une liste d'article
    private List<Article> articleListe = new ArrayList<>();
    private int index = 0;
    private Article articleAafficher;

    public GUI() {
        this.setTitle("Application Papeterie");
        this.setSize(500, 400);
        this.setResizable(false);// empeche de redimensionner la fenetre
        this.setAlwaysOnTop(true); // quand on change de fenetre ma fenetre reste "on top""
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//fenetre se ferme quand on stop le programme
        // Le tableau principal
        this.setContentPane(getPanneauPrincipal()); // Je colle le panneau principal sur le "support en bois".
        panneauPrincipal.setBackground(new Color(0x085684)); // couleur de fond du panneau principal
        // 1 et un seul "panneau en bois", et on colle les "afffiches" sur le "panneau en bois"
        this.setVisible(true);

        try {
            CatalogueManager cm = CatalogueManager.getInstance();//J'appel CatalogueManager (BLL) dans cm
            // Je rempli ma liste d'article
            this.articleListe = cm.getCatalogue();
        } catch (BLLException e) {
            System.out.println(e.getMessage());
        }

        if (!this.articleListe.isEmpty()) {
            this.articleAafficher = articleListe.get(0);// on récupère le 1er article (index initialisé à 0) de la liste (on veut modifier cet index pour aller au précédent ou suivant)
            getChampTxtRef().setText(articleAafficher.getReference());
            getChampTxtDesi().setText(articleAafficher.getDesignation());
            getChampTxtMarque().setText(articleAafficher.getMarque());
            getChampTxtStock().setText(String.valueOf(articleAafficher.getQteStock()));
            getChampTxtPrix().setText(String.valueOf(articleAafficher.getPrixUnitaire()));
            if (articleAafficher instanceof Ramette) {
                getComboBoxCouleur().setEnabled(false);
                getRadioRamette().setSelected(true);
                if (((Ramette) articleAafficher).getGrammage() == 80) {
                    getCheckbQuatreVingtGramme().setSelected(true);
                } else {
                    getCheckbCentGramme().setSelected(true);
                }
            }
                if (articleAafficher instanceof Stylo) {
                    getRadioStylo().setSelected(true);
                    getComboBoxCouleur().setSelectedItem(Couleur.valueOf(((Stylo) articleAafficher).getCouleur())); // Clean code
                }

        }
    }

    public JPanel getPanneauPrincipal(){
        if (panneauPrincipal==null){
            panneauPrincipal = new JPanel(); // Je crée le panneau principal
             // J'ajoute le label au menu principal
            panneauPrincipal.add(getlblRef());
            panneauPrincipal.add(getlblDesi());
            panneauPrincipal.add(getLblMarque());
            panneauPrincipal.add(getLblStock());
            panneauPrincipal.add(getLblPrix());
            panneauPrincipal.add(getLblType());
            panneauPrincipal.add(getLblGrammage());

            // J'ajoute les champs de texte au menu principal
            panneauPrincipal.add(getChampTxtRef());
            panneauPrincipal.add(getChampTxtDesi());
            panneauPrincipal.add(getChampTxtMarque());
            panneauPrincipal.add(getChampTxtStock());
            panneauPrincipal.add(getChampTxtPrix());

            panneauPrincipal.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();

            // les noms des colonnes en Colonne
            gbc.gridy = 0;
            gbc.gridx = 0;
            panneauPrincipal.add(getlblRef(),gbc);
            gbc.gridy = 1;
            gbc.gridx = 0;
            panneauPrincipal.add(getlblDesi(),gbc);
            gbc.gridy = 2;
            gbc.gridx = 0;
            panneauPrincipal.add(getLblMarque(),gbc);
            gbc.gridy = 3;
            gbc.gridx = 0;
            panneauPrincipal.add(getLblStock(),gbc);
            gbc.gridy = 4;
            gbc.gridx = 0;
            panneauPrincipal.add(getLblPrix(),gbc);
            gbc.gridy = 5;
            gbc.gridx = 0;
            panneauPrincipal.add(getLblType(),gbc);
            // appel au sous panneau du Type
            gbc.gridy = 5;
            gbc.gridx = 1;
            panneauPrincipal.add(getPanneauType(),gbc);
            gbc.gridy = 6;
            gbc.gridx = 0;
            panneauPrincipal.add(getLblGrammage(),gbc);
            // appel au sous panneau du Grammage avec les radiobox
            gbc.gridy = 6;
            gbc.gridx = 1;
            panneauPrincipal.add(getPanneauGrammage(),gbc);
            gbc.gridy = 7;
            gbc.gridx = 0;
            panneauPrincipal.add(getlblCouleur(),gbc);
            gbc.gridy = 7;
            gbc.gridx = 1;
            panneauPrincipal.add(getPanneauCouleur(),gbc);
            // appel au sous panneau Couleur avec les checkbox
            gbc.gridy = 8;
            gbc.gridx = 0;
            gbc.gridwidth = 2;
            panneauPrincipal.add(getPanelBoutons(), gbc);
            gbc.gridwidth = 1;
            //Colonne champs saisie
            gbc.gridy = 0;
            gbc.gridx = 1;
            panneauPrincipal.add(getChampTxtRef(), gbc);
            gbc.gridy = 1;
            gbc.gridx = 1;
            panneauPrincipal.add(getChampTxtDesi(),gbc);
            gbc.gridy = 2;
            gbc.gridx = 1;
            panneauPrincipal.add(getChampTxtMarque(),gbc);
            gbc.gridy = 3;
            gbc.gridx = 1;
            panneauPrincipal.add(getChampTxtStock(),gbc);
            gbc.gridy = 4;
            gbc.gridx = 1;
            panneauPrincipal.add(getChampTxtPrix(),gbc);
        }
        return panneauPrincipal;
    }

    public JPanel getPanneauType() {
        if (panneauType == null) {
            panneauType = new JPanel(); //Je crée le panneau panneauType
            panneauType.setBackground(new Color(0xFF048FD0, true));
            panneauType.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            //ajout des boutons radio au panneau principal
            panneauType.add(getRadioRamette());
            panneauType.add(getRadioStylo());
            //placement des boutons radios
            gbc.gridy = 0;
            gbc.gridx = 0;
            panneauType.add(getRadioRamette(), gbc);
            gbc.gridy = 1;
            gbc.gridx = 0;
            panneauType.add(getRadioStylo(),gbc);
            ButtonGroup bg = new ButtonGroup(); // forme un groupe avec les deux boutons radios
            bg.add(getRadioStylo());
            bg.add(getRadioRamette());
        }
        return panneauType;
    }

    public JPanel getPanneauGrammage() {
        if (panneauGrammage == null) {
            panneauGrammage = new JPanel(); //Je crée le panneau panneauGrammage
            panneauType.setBackground(new Color(0xFF048FD0, true));
            panneauGrammage.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            // ajout des boutons radios au panneau principal
            panneauGrammage.add(getCheckbQuatreVingtGramme());
            panneauGrammage.add(getCheckbCentGramme());
            // placement des checkbox
            gbc.gridy = 0;
            gbc.gridx = 0;
            panneauGrammage.add(getCheckbQuatreVingtGramme(),gbc);
            gbc.gridy = 1;
            gbc.gridx = 0;
            panneauGrammage.add(getCheckbCentGramme(),gbc);
            ButtonGroup bg = new ButtonGroup(); // forme un groupe avec les deux checkbox
            bg.add(getCheckbCentGramme());
            bg.add(getCheckbQuatreVingtGramme());
        }
        return panneauGrammage;
    }

    public JPanel getPanneauCouleur() {
        if (panneauCouleur == null) {
            panneauCouleur = new JPanel(); //Je crée le panneau panneauCouleur
            panneauType.setBackground(new Color(0xFF048FD0, true));
            panneauCouleur.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            panneauCouleur.add(getComboBoxCouleur());
            gbc.gridy = 0;
            gbc.gridx = 0;
            panneauCouleur.add(getComboBoxCouleur(),gbc);
        }
        return panneauCouleur;
    }

    public JLabel getlblRef(){
        if(lblRef == null){
            lblRef = new JLabel("Référence");
            lblRef.setForeground(new Color(240, 242, 242));
        }
        return lblRef;
    }

    public JLabel getlblDesi(){
        if(lblDesi == null){
            lblDesi = new JLabel("Désignation");
            lblDesi.setForeground(new Color(240, 242, 242));
        }
        return lblDesi;
    }

    public JLabel getLblMarque(){
        if(lblMarque == null){
            lblMarque = new JLabel("Marque");
            lblMarque.setForeground(new Color(240, 242, 242));
        }
        return lblMarque;
    }

    public JLabel getLblStock(){
        if(lblStock == null){
            lblStock = new JLabel("Stock");
            lblStock.setForeground(new Color(240, 242, 242));
        }
        return lblStock;
    }

    public JLabel getLblPrix(){
        if(lblPrix == null){
            lblPrix = new JLabel("Prix");
            lblPrix.setForeground(new Color(240, 242, 242));
        }
        return lblPrix;
    }

    public JLabel getLblType(){
        if(lblType == null){
            lblType = new JLabel("Type");
            lblType.setForeground(new Color(240, 242, 242));
        }
        return lblType;
    }

    public JLabel getLblGrammage(){
        if(lblGrammage == null){
            lblGrammage = new JLabel("Grammage");
            lblGrammage.setForeground(new Color(240, 242, 242));
        }
        return lblGrammage;
    }

   public JLabel getlblCouleur(){
        if(lblCouleur == null){
            lblCouleur = new JLabel("Couleur");
            lblCouleur.setForeground(new Color(240, 242, 242));
        }
        return lblCouleur;
    }

    public JTextField getChampTxtRef() {
        if (champTxtRef == null) {
            champTxtRef = new JTextField("", 25);
            champTxtRef.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JTextField textField = ((JTextField) e.getSource());
                    textField.setText("");
                }
            });
        }
        return champTxtRef;
    }

    public JTextField getChampTxtDesi() {
        if (champTxtDesi == null) {
            champTxtDesi = new JTextField("", 25);
            champTxtDesi.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JTextField textField =((JTextField)e.getSource() );
                    textField.setText("");
                }
            });
        }
        return champTxtDesi;
    }

    public JTextField getChampTxtMarque() {
        if (champTxtMarque == null) {
            champTxtMarque = new JTextField("", 25);
            champTxtMarque.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JTextField textField =((JTextField)e.getSource() );
                    textField.setText("");
                }
            });
        }
        return champTxtMarque;
    }

    public JTextField getChampTxtStock() {
        if (champTxtStock == null) {
            champTxtStock = new JTextField("", 25);
            champTxtStock.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JTextField textField =((JTextField)e.getSource() );
                    textField.setText("");
                }
            });
        }
        return champTxtStock;
    }

    public JTextField getChampTxtPrix() {
        if (champTxtPrix == null) {
            champTxtPrix = new JTextField("", 25);
            champTxtPrix.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JTextField textField =((JTextField)e.getSource() );
                    textField.setText("");
                }
            });
        }
        return champTxtPrix;
    }

    public JRadioButton getRadioRamette() {
        if (radioRamette == null) {
            radioRamette = new JRadioButton("Ramette");
            // Quand je clique sur le radio ramette
            radioRamette.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    getCheckbQuatreVingtGramme().doClick();// Je selectionne 80gr
                    getCheckbQuatreVingtGramme().setEnabled(true); // 80gr activé
                    getCheckbCentGramme().setEnabled(true); // 100gr activé
                    getComboBoxCouleur().setEnabled(false); // combobox désactivée
                }
            });
        }
        return radioRamette;
    }

    public JRadioButton getRadioStylo() {
        if (radioStylo == null) {
            radioStylo = new JRadioButton("Stylo");
            // Quand je clique sur radio Stylo
            radioStylo.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // desactive les 2 CheckBox
                    getCheckbQuatreVingtGramme().setEnabled(false);
                    getCheckbCentGramme().setEnabled(false);
                    // active la combobox des couleurs
                    getComboBoxCouleur().setEnabled(true);
                }
            });
        }
        return radioStylo;
    }

    public JCheckBox getCheckbQuatreVingtGramme() {
        if (ckbQuatreVingtGramme == null) {
            ckbQuatreVingtGramme = new JCheckBox("80 grammes");
        }
        return ckbQuatreVingtGramme;
    }

    public JCheckBox getCheckbCentGramme() {
        if (ckbCentGramme == null) {
            ckbCentGramme = new JCheckBox("100 grammes");
        }
        return ckbCentGramme;
    }

    public JComboBox getComboBoxCouleur() {
        if (comboBoxCouleur == null) {
            comboBoxCouleur = new JComboBox(Couleur.values());//appel de l'enumeration ds le package bo
        }
        return comboBoxCouleur;
    }

    public JPanel getPanelBoutons(){
        if(panelBoutons == null){
            panelBoutons = new JPanel();
            panelBoutons.setBackground(new Color(0xFF84327F, true));
            panelBoutons.add(getBtnPrecedent());
            panelBoutons.add(getBtnNouveau());
            panelBoutons.add(getBtnEnregistrer());
            panelBoutons.add(getBtnSupprimer());
            panelBoutons.add(getBtnSuivant());
        }
        return panelBoutons;
    }

    public JButton getBtnPrecedent(){
        if(btnPrecedent == null){
            btnPrecedent = new JButton(new ImageIcon("image/back24.gif"));
            btnPrecedent.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //getRadioRamette().setEnabled(false);
                    //getRadioStylo().setEnabled(false);
                    if (index <= 0) { // si index est egale à 0
                        index = articleListe.size() - 1; // index est egale à la taille maxi de la liste (donc cyclique)
                    } else {
                        index--;
                    }
                    articleAafficher = articleListe.get(index);
                    getChampTxtRef().setText(articleAafficher.getReference());
                    getChampTxtDesi().setText(articleAafficher.getDesignation());
                    getChampTxtMarque().setText(articleAafficher.getMarque());
                    getChampTxtStock().setText(String.valueOf(articleAafficher.getQteStock()));
                    getChampTxtPrix().setText(String.valueOf(articleAafficher.getPrixUnitaire()));
                }
            });
        }
        return btnPrecedent;
    }

    public JButton getBtnNouveau() {
        if (btnNouveau == null) {
            btnNouveau = new JButton(new ImageIcon("image/New24.gif"));
            btnNouveau.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    getChampTxtRef().setText("");
                    getChampTxtDesi().setText("");
                    getChampTxtMarque().setText("");
                    getChampTxtStock().setText("");
                    getChampTxtPrix().setText("");
                    articleAafficher = null;
                }
            });
        }
        return btnNouveau;
    }

    public JButton getBtnEnregistrer() {
        if (btnEnregistrer == null) {
            btnEnregistrer = new JButton(new ImageIcon("image/Save24.gif"));
            btnEnregistrer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                   CatalogueManager cm = CatalogueManager.getInstance(); // J'appel la BLL
                    if (articleAafficher != null){
                        try {
                            cm.updateArticle(articleAafficher);
                        } catch (BLLException bllException) {
                            bllException.printStackTrace();
                        }
                    }else {
                        Article article = null;
                        if (getRadioStylo().isSelected()) { // C'est un stylo
                            article = new Stylo(
                                    getChampTxtMarque().getText(),
                                    getChampTxtRef().getText(),
                                    getChampTxtDesi().getText(),
                                    Float.parseFloat(getChampTxtPrix().getText()),
                                    Integer.parseInt(getChampTxtStock().getText()),
                                    getComboBoxCouleur().getSelectedItem().toString()
                            );
                        }
                        if (getRadioRamette().isSelected()) { // C'est une Ramette
                            article = new Ramette(
                                    getChampTxtMarque().getText(),
                                    getChampTxtRef().getText(),
                                    getChampTxtDesi().getText(),
                                    Float.parseFloat(getChampTxtPrix().getText()),
                                    Integer.parseInt(getChampTxtStock().getText()),
                                    (getCheckbQuatreVingtGramme().isSelected() ? 80 : 100)
                            );
                        }
                        try {
                            cm.addArticle(article);
                        } catch (BLLException bllException) {
                            bllException.printStackTrace();
                        }
                    }
                }
            });
        }
        return btnEnregistrer;
    }

    public JButton getBtnSupprimer() {
        if (btnSupprimer == null) {
            btnSupprimer = new JButton(new ImageIcon("image/Delete24.gif"));
            btnSupprimer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    try{
                        CatalogueManager cm = CatalogueManager.getInstance();
                        cm.removeArticle(articleAafficher.getIdArticle());// Je le supprime en BDD
                    }catch (BLLException | DALException bllException){
                        bllException.printStackTrace();
                    }
                    articleListe.remove(articleAafficher);// Je le supprime de la liste d'articles
                    getBtnSuivant().doClick(); // Je clique sur le bouton suivant
                }
            });
        }
        return btnSupprimer;
    }

    public JButton getBtnSuivant() {

        if (btnSuivant == null) {
            btnSuivant = new JButton(new ImageIcon("image/Forward24.gif"));
            btnSuivant.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //getRadioRamette().setEnabled(false);
                    //getRadioStylo().setEnabled(false);
                    if (index >= articleListe.size() - 1 ) { // si l'index est égale à la taille (le max) de la liste
                        index = 0;                           // index reviens à 0  (donc cyclique)
                    } else {
                        index++;
                    }
                    Article articleaAfficher = articleListe.get(index);
                    getChampTxtRef().setText(articleaAfficher.getReference());
                    getChampTxtDesi().setText(articleaAfficher.getDesignation());
                    getChampTxtMarque().setText(articleaAfficher.getMarque());
                    getChampTxtStock().setText(String.valueOf(articleaAfficher.getQteStock()));
                    getChampTxtPrix().setText(String.valueOf(articleaAfficher.getPrixUnitaire()));
                }
            });
        }
        return btnSuivant;
    }


}
