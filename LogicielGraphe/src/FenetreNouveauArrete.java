/**
 * Classe permettant de créer une nouvelle arrete
 * 
 * @author Vallot Julien, Etancelin Pierre, Gourdain Loic, Florin kilian, Guelle Dylan
 * @version 1.0
 * 2016/01/07
 * 
 */
import java.awt.Checkbox;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class FenetreNouveauArrete extends JFrame implements ActionListener, ListSelectionListener{
	
	private JPanel pCentre, pHaut, pBas;
	private JButton ok, annuler;
	private Graphique graphe;
	private JLabel lNom, lSommetUn, lSommetDeux, orienteVers;
	private JTextField tNom;
	private JList jlSommetUn, jlSommetDeux;
	private JScrollPane scSommetUn, scSommetDeux;
	private String[] tabSommetUn, tabSommetDeux;
	private Checkbox s1, s2;
	
	private Sommet sUn, sDeux;
	
	/**
	 * constructeur
	 * @param graphe
	 */
	public FenetreNouveauArrete( Graphique graphe ){
		setSize(350,200);
		setTitle("Nouvelle Arrete");
		this.graphe = graphe;
		
		this.pHaut = new JPanel( new GridLayout(1,2) );
		this.lNom = new JLabel("Nom");
		this.tNom = new JTextField();
		this.pHaut.add( this.lNom );
		this.pHaut.add( this.tNom );
		add( this.pHaut, "North" );
		
		this.pCentre = new JPanel( new GridLayout(1,4) );
		this.lSommetUn = new JLabel( "Sommet Un" );
		this.lSommetDeux = new JLabel( "Sommet Deux" );
		this.tabSommetUn = new String[this.graphe.getAlSommet().size()];
		this.tabSommetDeux = new String[this.graphe.getAlSommet().size()];
		
		for( int i = 0; i < this.graphe.getAlSommet().size(); i++ ){
			this.tabSommetUn[i] = this.graphe.getAlSommet().get(i).getNom();
		}
		
		for( int i = 0; i < this.graphe.getAlSommet().size(); i++ ){
			this.tabSommetDeux[i] = this.graphe.getAlSommet().get(i).getNom();
		}
		
		this.jlSommetUn = new JList( this.tabSommetUn );
		this.jlSommetDeux = new JList( this.tabSommetDeux );
		this.jlSommetUn.addListSelectionListener(this);
		this.jlSommetDeux.addListSelectionListener(this);
		this.scSommetUn = new JScrollPane( this.jlSommetUn );
		this.scSommetDeux = new JScrollPane( this.jlSommetDeux );
		
		this.pCentre.add( this.lSommetUn );
		this.pCentre.add( this.scSommetUn );
		this.pCentre.add( this.lSommetDeux );
		this.pCentre.add( this.scSommetDeux );
		add( this.pCentre, "Center" );
		
		this.pBas = new JPanel();
		//permet le choix pour la direction de la fleche vers un sommet 
		if( this.graphe.getOriente() ){
			this.pBas.setLayout( new GridLayout(2,2) );
			this.orienteVers = new JLabel("Orientation vers : ");
			JPanel check = new JPanel( new GridLayout(1,2) );
			this.s1 = new Checkbox( "Sommet 1" );
			this.s2 = new Checkbox( "Sommet 2" );
			check.add(this.s1);
			check.add(this.s2);
			this.pBas.add( this.orienteVers );
			this.pBas.add( check );
		}else{
			this.pBas.setLayout( new GridLayout(1,2) );
		}
		this.ok = new JButton("Ok");
		this.annuler = new JButton("Annuler");
		this.pBas.add( this.ok );
		this.pBas.add( this.annuler );
		this.ok.addActionListener(this);
		this.annuler.addActionListener(this);
		add( this.pBas, "South" );
		
		//pack();
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if( e.getSource() == this.ok ){
			if( this.graphe.getOriente() ){
				ArrayList<Sommet> dir = new ArrayList<Sommet>();
				if( this.s1.getState() )	dir.add( this.sDeux );
				if( this.s2.getState() )	dir.add( this.sUn );
				this.graphe.createArrete( this.sUn, this.sDeux, dir );
			}
			else	this.graphe.createArrete( this.sUn, this.sDeux );
			
			this.dispose();
		}
		if( e.getSource() == this.annuler ){
			this.dispose();
		}
	}

	public void valueChanged(ListSelectionEvent e) {
		
		if( e.getSource() == this.jlSommetUn ){
			this.sUn = this.graphe.getAlSommet().get( this.jlSommetUn.getSelectedIndex() );
			System.out.println( this.sUn.getNom() );
		}
		
		if( e.getSource() == this.jlSommetDeux ){
			this.sDeux = this.graphe.getAlSommet().get( this.jlSommetDeux.getSelectedIndex() );
			System.out.println( this.sDeux.getNom() );
		}
		
	}
	
}
