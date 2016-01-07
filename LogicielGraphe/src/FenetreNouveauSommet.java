/**
 * Classe permettant la creation d'un nouveau sommet 
 * 
 * @author Vallot Julien, Etancelin Pierre, Gourdain Loic, Florin kilian, Guelle Dylan
 * @version 1.0
 * 2016/01/07
 * 
 */
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FenetreNouveauSommet extends JFrame implements ActionListener{

	private Graphique graphe;
	//private String nom;
	private JPanel pCentre, pHaut, pBas;
	private JTextField tNom, tX, tY, tWidth, tHeight;
	private JLabel lNom, lX, lY, lWidth, lHeight;
	private JButton ok, annuler;
	
	/**
	 * constructeur
	 * @param graphe
	 */
	public FenetreNouveauSommet( Graphique graphe ){
		setTitle("Nouveau Sommet");
		this.graphe = graphe;
		
		this.pHaut = new JPanel( new GridLayout(1,2) );
		this.lNom = new JLabel("Nom");
		this.tNom = new JTextField();
		this.pHaut.add( this.lNom );
		this.pHaut.add( this.tNom );
		add( this.pHaut, "North" );
		
		this.pCentre = new JPanel( new GridLayout(1,8) );
		this.lX = new JLabel( "Pos X" );
		this.tX = new JTextField();
		this.tY = new JTextField();
		this.lY = new JLabel( "Pos Y" );
		this.tWidth = new JTextField();
		this.lWidth = new JLabel( "Largeur" );
		this.tHeight = new JTextField();
		this.lHeight = new JLabel( "Hauteur" );
		this.pCentre.add( this.lX );
		this.pCentre.add( this.tX );
		this.pCentre.add( this.lY );
		this.pCentre.add( this.tY );
		this.pCentre.add( this.lWidth );
		this.pCentre.add( this.tWidth );
		this.pCentre.add( this.lHeight );
		this.pCentre.add( this.tHeight );
		add( this.pCentre, "Center" );
		
		this.pBas = new JPanel( new GridLayout(1,2) );
		this.ok = new JButton("Ok");
		this.annuler = new JButton("Annuler");
		this.pBas.add( this.ok );
		this.pBas.add( this.annuler );
		this.ok.addActionListener(this);
		this.annuler.addActionListener(this);
		add( this.pBas, "South" );
		
		pack();
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if( e.getSource() == this.ok ){
			this.graphe.addSommet( new Sommet( this.tNom.getText(), Integer.parseInt(this.tX.getText()), Integer.parseInt(this.tY.getText()), Integer.parseInt(this.tWidth.getText()), Integer.parseInt(this.tHeight.getText()) ) );
			//this.graphe.addSommet();
			System.out.println("Lol");
			this.dispose();
		}
		if( e.getSource() == this.annuler ){
			this.dispose();
		}
	}
	
}
