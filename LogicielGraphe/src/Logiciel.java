import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.*;
import java.io.File;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;


public class Logiciel extends JFrame implements ActionListener, KeyListener{
	
	private Graphique graphe;
	private Arbre arbre;
	private JPanel total, button;
	private JButton b1, b2;
	public static String nomProjet;
	private File currentFile = null;
	private JMenuBar menu;
	
	private JMenu fichier, edition, affichage, nouveau, modifier, supprimer;
	
	private JMenuItem enregistrer, enregistrerSous, ouvrir, retourArriere, exporterSousTexte, exporterSousImage, exporterSousPdf;
	private JMenuItem nouveauGraphe, nouveauSommet, nouveauArrete, nouveauArc, nouveauChemin;
	
	private JMenuItem modifierSelection, copierSelection, collerSelection, styleGraphe, changerApparence;
	private JMenuItem modifierSommet, modifierArrete, modifierArc, modifierChemin;
	
	private JMenuItem supprimerSommet, supprimerArrete, supprimerArc, supprimerChemin;
	
	private JMenuItem recharger, zoomSelection, dezoomSelection;
	
	public Logiciel(){
		setTitle("Logiciel ");
		setSize(500,300);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		addKeyListener(this);
		
		this.menu = new JMenuBar();
		setJMenuBar(menu);
		
		this.fichier = new JMenu("Fichier");
		
		this.enregistrer = new JMenuItem("Enregistrer");
		this.enregistrerSous = new JMenuItem("Enregistrer Sous");
		this.ouvrir = new JMenuItem("Ouvrir Projet");
		this.exporterSousTexte = new JMenuItem("Exporter sous texte");
		this.exporterSousImage = new JMenuItem("Exporter sous image");
		this.exporterSousPdf = new JMenuItem("Exporter sous pdf");
		
		this.fichier.add(this.enregistrer);
		this.fichier.add(this.enregistrerSous);
		this.fichier.add(this.ouvrir);
		this.fichier.add(this.exporterSousTexte);
		this.fichier.add(this.exporterSousImage);
		this.fichier.add(this.exporterSousPdf);
		
		this.enregistrer.addActionListener(this);
		this.enregistrerSous.addActionListener(this);
		this.ouvrir.addActionListener(this);
		this.exporterSousTexte.addActionListener(this);
		this.exporterSousImage.addActionListener(this);
		this.exporterSousPdf.addActionListener(this);
		
		this.menu.add( this.fichier );
		
		this.edition = new JMenu("Edition");
		
		this.nouveau = new JMenu("Nouveau");
		this.nouveauGraphe = new JMenuItem("Nouveau Graphe");
		this.nouveauSommet = new JMenuItem("Nouveau Sommet");
		this.nouveauArrete = new JMenuItem("Nouveau Arrete");
		this.nouveauArc = new JMenuItem("Nouveau Arc");
		this.nouveauChemin = new JMenuItem("Nouveau Chemin");
		
		this.nouveau.add( this.nouveauGraphe );
		this.nouveau.add( this.nouveauSommet );
		this.nouveau.add( this.nouveauArrete );
		this.nouveau.add( this.nouveauArc );
		this.nouveau.add( this.nouveauChemin );
		
		this.nouveauGraphe.addActionListener(this);
		this.nouveauSommet.addActionListener(this);
		this.nouveauArrete.addActionListener(this);
		this.nouveauArc.addActionListener(this);
		this.nouveauChemin.addActionListener(this);
		
		this.modifier = new JMenu("Modifier");
			
		this.modifierSommet = new JMenuItem("Modifier Sommet");
		this.modifierArrete = new JMenuItem("Modifier Arrete");
		this.modifierArc = new JMenuItem("Modifier Arc");
		this.modifierChemin = new JMenuItem("Modifier Chemin");
		
		this.modifierSommet.addActionListener(this);
		this.modifierArrete.addActionListener(this);
		this.modifierArc.addActionListener(this);
		this.modifierChemin.addActionListener(this);
		
		this.modifier.add( this.modifierSommet );
		this.modifier.add( this.modifierArrete );
		this.modifier.add( this.modifierArc );
		this.modifier.add( this.modifierChemin );
		
		this.supprimer = new JMenu("Supprimer");
		
		this.supprimerSommet = new JMenuItem("Supprimer Sommet");
		this.supprimerArrete = new JMenuItem("Supprimer Arrete");
		this.supprimerArc = new JMenuItem("Supprimer Arc");
		this.supprimerChemin = new JMenuItem("Supprimer Chemin");
		
		this.supprimerSommet.addActionListener(this);
		this.supprimerArrete.addActionListener(this);
		this.supprimerArc.addActionListener(this);
		this.supprimerChemin.addActionListener(this);
		
		this.supprimer.add( this.supprimerSommet );
		this.supprimer.add( this.supprimerArrete );
		this.supprimer.add( this.supprimerArc );
		this.supprimer.add( this.supprimerChemin );
		
		this.retourArriere = new JMenuItem("Retour Arriere");
		this.modifierSelection = new JMenuItem("Modifier Selection");
		this.copierSelection = new JMenuItem("Copier Selection");
		this.collerSelection = new JMenuItem("Coller Selection");
		this.styleGraphe = new JMenuItem("Style Graphe");
		this.changerApparence = new JMenuItem("Changer Apparence");
		this.changerApparence.addActionListener(this);

		this.edition.add(this.nouveau);
		this.edition.add( this.modifier );
		this.edition.add( this.supprimer );
		this.edition.add(this.retourArriere);
		this.edition.add( this.modifierSelection );
		this.edition.add( this.copierSelection );
		this.edition.add( this.collerSelection );
		this.edition.add( this.styleGraphe );
		this.edition.add( this.changerApparence );
		
		this.menu.add( this.edition );
		
		this.affichage = new JMenu("Affichage");
		
		this.recharger = new JMenuItem("Recharger");
		this.zoomSelection = new JMenuItem("Zoom");
		this.dezoomSelection = new JMenuItem("Dezoom");
		
		this.affichage.add( this.recharger );
		this.affichage.add( this.zoomSelection );
		this.affichage.add( this.dezoomSelection );
		
		this.menu.add( this.affichage );
		
		this.total = new JPanel();
		this.total.setLayout( new BorderLayout() );
		
		this.arbre = new Arbre();
		this.graphe = new Graphique(this.arbre);
		//this.graphe.initFichier( "init.txt" );
		
		this.total.add( this.graphe );
		
		add( this.total, "Center" );
		
		add(this.arbre,"West");
		
		//pack();
		setVisible(true);
		try { 
				Locale.setDefault(new Locale("fr", "FRANCE", "WIN"));
				UIManager.getDefaults().setDefaultLocale(new Locale("fr", "FRANCE", "WIN"));
				JComponent.setDefaultLocale(new Locale("fr", "FRANCE", "WIN"));
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
				SwingUtilities.updateComponentTreeUI(this); 
			} catch (InstantiationException e) { 
			} catch (ClassNotFoundException e) { 
			} catch (UnsupportedLookAndFeelException e) { 
			} catch (IllegalAccessException e) {}
	}
	
	//C'est ici que l'on va gérer les actions de chacun des boutons dans la barre de menu
	public void actionPerformed(ActionEvent e) {
		
		//Crée un nouveau projet
		if( e.getSource() == this.nouveauGraphe ){
			FenetreNouveauProjet n = new FenetreNouveauProjet( this.graphe );
		}
		
		//Crée un nouveau sommet dans le projet courant
		if( e.getSource() == this.nouveauSommet ){
			FenetreNouveauSommet n = new FenetreNouveauSommet( this.graphe );
		}
		
		//Crée une nouvelle arrete en les reliants entre deux sommets
		if( e.getSource() == this.nouveauArrete ){
			FenetreNouveauArrete n = new FenetreNouveauArrete( this.graphe );
		}
		
		if( e.getSource() == this.modifierSommet ){
			FenetreModifierSommet n = new FenetreModifierSommet( this.graphe, null );
		}
		
		if( e.getSource() == this.supprimerSommet ){
			FenetreSupprimerSommet n = new FenetreSupprimerSommet( this.graphe );
		}
		
		if( e.getSource() == this.supprimerArrete ){
			FenetreSupprimerArrete n = new FenetreSupprimerArrete( this.graphe );
		}
		if( e.getSource() == this.exporterSousImage ){
			graphe.exportImage();
		}
		if( e.getSource() == this.exporterSousPdf ){
			//graphe.exportPdf();
		}
		
		if( e.getSource() == this.enregistrer ){
				if (currentFile == null)
					JOptionPane.showMessageDialog(null,"Aucun projet n'est encore ouvert");
				else
					this.graphe.enregistrerSous( currentFile );
				System.out.println("Enregistrement de "+currentFile.getPath());
		}
		
		//Ouvre une fenetre de parcours de dossiers pour décider où enregistrer le projet courant
		if( e.getSource() == this.enregistrerSous ){
			if (currentFile == null)
				JOptionPane.showMessageDialog(null,"Aucun projet n'est encore ouvert");
			else {
				JFileChooser enregistrerSous = new JFileChooser();
				enregistrerSous.setCurrentDirectory( new File(".") );
				enregistrerSous.setDialogTitle( "Enregistrer Sous" );
	    		FileFilter textFilter = new FileNameExtensionFilter("Fichier Texte", "txt");
	    		enregistrerSous.addChoosableFileFilter(textFilter);
	    		enregistrerSous.setFileFilter(textFilter);
				int resultatEnregistrer = enregistrerSous.showDialog(enregistrerSous, "Enregistrer");
				if (resultatEnregistrer == JFileChooser.APPROVE_OPTION){
					File chemin = enregistrerSous.getSelectedFile();
					if (!chemin.getPath().contains(".png"))
	    				chemin = new File(chemin.getPath() + ".txt");
					
					this.graphe.enregistrerSous( chemin );
					currentFile = chemin;
				}
			}
		}
		
		//Ouvre une fenetre de parcous de fichiers/dossiers pour décider quel fichier ouvrir 
		if( e.getSource() == this.ouvrir ){
			JFileChooser enregistrerSous = new JFileChooser();
			enregistrerSous.setCurrentDirectory( new File(".") );
			enregistrerSous.setDialogTitle( "Ouvrir" );
    		FileFilter textFilter = new FileNameExtensionFilter("Fichier Texte", "txt");
    		enregistrerSous.addChoosableFileFilter(textFilter);
    		enregistrerSous.setFileFilter(textFilter);
			int resultatEnregistrer = enregistrerSous.showDialog(ouvrir, "Ouvrir");
			if (resultatEnregistrer == JFileChooser.APPROVE_OPTION){
				String chemin = enregistrerSous.getSelectedFile().getAbsolutePath()+"\\";
				
				this.graphe.initFichier( chemin );
				currentFile = new File (chemin);
			}
		}
		
	}
	
	public void keyPressed(KeyEvent e) 	{	this.graphe.setKeyPressed( e.getKeyCode() );	}
	public void keyReleased(KeyEvent e) {	this.graphe.resetKeyPressed();	}
	public void keyTyped(KeyEvent e) {}
	
	public static void main( String[] args){
		new Logiciel();
	}
}