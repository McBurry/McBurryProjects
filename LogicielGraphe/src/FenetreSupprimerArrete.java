/**
 * Classe permettant de supprimer arrete
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
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class FenetreSupprimerArrete extends JFrame implements ActionListener, ListSelectionListener{
	
	private JButton ok, annuler;
	private JScrollPane scArrete;
	private JList listArrete;
	private JLabel lText;
	
	private String[] tabArrete;
	private Arrete isSelected;
	
	private Graphique graphe;
	
	/**
	 * constructeur
	 * @param graphe
	 */
	public FenetreSupprimerArrete( Graphique graphe ){
		setTitle( "Fenetre Supprimer Arrete" );
		setLayout( new GridLayout(2,2) );
		
		this.graphe = graphe;
		this.lText = new JLabel( "Sommet à supprimer : " );
		
		this.tabArrete = new String[this.graphe.getAlLine().size()];
		for( int i = 0; i < this.graphe.getAlLine().size(); i++ ){
			this.tabArrete[i] = "" + this.graphe.getAlLine().get(i).getNom() + " ";
			this.tabArrete[i] += "( " + this.graphe.getAlLine().get(i).getSommet1().getNom() + " <-> ";
			this.tabArrete[i] += this.graphe.getAlLine().get(i).getSommet2().getNom() + ")";
		}
		this.listArrete = new JList( this.tabArrete );
		this.listArrete.addListSelectionListener(this);
		this.scArrete = new JScrollPane( this.listArrete );
		
		add( this.lText );
		add( this.scArrete );
		
		this.ok = new JButton( "Ok" );
		this.annuler = new JButton( "Annuler" );
		this.ok.addActionListener(this);
		this.annuler.addActionListener(this);
		
		add( this.ok );
		add( this.annuler );
		
		pack();
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		
		if( e.getSource() == this.ok ){
			this.graphe.deleteArrete( this.isSelected );
			this.dispose();
		}
		if( e.getSource() == this.annuler ){
			this.dispose();
		}
		
	}

	public void valueChanged(ListSelectionEvent e) {
		if( e.getSource() == this.listArrete ){
			this.isSelected = this.graphe.getAlLine().get(this.listArrete.getSelectedIndex());
		}
	}
}
