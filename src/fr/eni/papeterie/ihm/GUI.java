package fr.eni.papeterie.ihm;

import fr.eni.papeterie.bll.CatalogueManager;
import fr.eni.papeterie.bo.Article;
import fr.eni.papeterie.bo.Couleur;
import fr.eni.papeterie.bo.Ramette;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
    private JRadioButton radioRammette;
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

    public GUI() {
        this.setTitle("Application Papeterie");
        this.setSize(500, 400);
        this.setResizable(false);// empeche de redimmensionner la fenetre
        this.setAlwaysOnTop(true); // quand on change de fenetre ma fenetre reste visible
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Le tableau principal
        this.setContentPane(getPanneauPrincipal()); // Je colle le panneau principal sur le "support en bois".
        // 1 et un seul "panneau en bois", et on colle les "afffiches" sur le "panneau en bois"
        this.setVisible(true);
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
            panneauType.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            //ajout des boutons radio au panneau principal
            panneauType.add(getRadioRammette());
            panneauType.add(getRadioStylo());
            //placement des boutons radios
            gbc.gridy = 0;
            gbc.gridx = 0;
            panneauType.add(getRadioRammette(),gbc);
            gbc.gridy = 1;
            gbc.gridx = 0;
            panneauType.add(getRadioStylo(),gbc);
            ButtonGroup bg = new ButtonGroup(); // forme un groupe avec les deux boutons radios
            bg.add(getRadioStylo());
            bg.add(getRadioRammette());
        }
        return panneauType;
    }

    public JPanel getPanneauGrammage() {
        if (panneauGrammage == null) {
            panneauGrammage = new JPanel(); //Je crée le panneau panneauGrammage
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
        }
        return lblRef;
    }

    public JLabel getlblDesi(){
        if(lblDesi == null){
            lblDesi = new JLabel("Désignation");
        }
        return lblDesi;
    }

    public JLabel getLblMarque(){
        if(lblMarque == null){
            lblMarque = new JLabel("Marque");
        }
        return lblMarque;
    }

    public JLabel getLblStock(){
        if(lblStock == null){
            lblStock = new JLabel("Stock");
        }
        return lblStock;
    }

    public JLabel getLblPrix(){
        if(lblPrix == null){
            lblPrix = new JLabel("Prix");
        }
        return lblPrix;
    }

    public JLabel getLblType(){
        if(lblType == null){
            lblType = new JLabel("Type");
        }
        return lblType;
    }

    public JLabel getLblGrammage(){
        if(lblGrammage == null){
            lblGrammage = new JLabel("Grammage");
        }
        return lblGrammage;
    }

   public JLabel getlblCouleur(){
        if(lblCouleur == null){
            lblCouleur = new JLabel("Couleur");
        }
        return lblCouleur;
    }

    public JTextField getChampTxtRef() {
            if (champTxtRef == null) {
                champTxtRef = new JTextField("Saisissez la ref", 25);
                champTxtRef.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        JTextField textField =((JTextField)e.getSource() );
                        textField.setText("");
                    }
                });
            }
            return champTxtRef;
    }

    public JTextField getChampTxtDesi() {
        if (champTxtDesi== null) {
            champTxtDesi = new JTextField("Saisissez la designation", 25);
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
        if (champTxtMarque== null) {
            champTxtMarque = new JTextField("saisissez la marque", 25);
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
        if (champTxtStock== null) {
            champTxtStock = new JTextField("saisissez le stock", 25);
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
        if (champTxtPrix== null) {
            champTxtPrix = new JTextField("saisissez le prix", 25);
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

    public JRadioButton getRadioRammette(){
        if (radioRammette== null) {
            radioRammette = new JRadioButton();
            radioRammette.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    getComboBoxCouleur().setEnabled(false);
                    getCheckbQuatreVingtGramme().doClick();
                }
            });
        }
        return radioRammette;
    }

    public JRadioButton getRadioStylo(){
        if (radioStylo== null) {
            radioStylo = new JRadioButton();
        }
        return radioStylo;
    }

    public JCheckBox getCheckbQuatreVingtGramme(){
        if (ckbQuatreVingtGramme== null) {
            ckbQuatreVingtGramme = new JCheckBox();
        }
        return ckbQuatreVingtGramme;
    }

    public JCheckBox getCheckbCentGramme(){
        if (ckbCentGramme== null) {
            ckbCentGramme = new JCheckBox();
        }
        return ckbCentGramme;
    }

    public JComboBox getComboBoxCouleur(){
        if (comboBoxCouleur== null) {
            comboBoxCouleur = new JComboBox(Couleur.values());
        }
        return comboBoxCouleur;
    }

    public JPanel getPanelBoutons(){
        if(panelBoutons == null){
            panelBoutons = new JPanel();
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
        }
        return btnPrecedent;
    }

    public JButton getBtnNouveau() {
        if (btnNouveau == null) {
            btnNouveau = new JButton(new ImageIcon("image/New24.gif"));
        }
        return btnNouveau;
    }

    public JButton getBtnEnregistrer() {
        if (btnEnregistrer == null) {
            btnEnregistrer = new JButton(new ImageIcon("image/Save24.gif"));
           /** btnEnregistrer.addActionListener(new ActionListener() {
                @Override
               public void actionPerformed(ActionEvent e) {
                    CatalogueManager cm = CatalogueManager.getInstance();
                    Article article = new Ramette();
                    article.setDesignation(getChampTxtDesi().getText());// mais mettre ça dans un ty catch au cas ou le champs est vide
                    cm.addArticle(article);
                }
            });*/
        }
        return btnEnregistrer;
    }

    public JButton getBtnSupprimer() {
        if (btnSupprimer == null) {
            btnSupprimer = new JButton(new ImageIcon("image/Delete24.gif"));
        }
        return btnSupprimer;
    }

    public JButton getBtnSuivant() {
        if (btnSuivant == null) {
            btnSuivant = new JButton(new ImageIcon("image/Forward24.gif"));
        }
        return btnSuivant;
    }


}
