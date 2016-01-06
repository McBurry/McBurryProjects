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

public class FenetreSupprimerSommet extends JFrame implements ListSelectionListener, ActionListener{

	private JLabel lSupp;
	private JList listSupp;
	private String[] tabSelection;
	private JScrollPane scSelection;
	private Graphique graphe;
	
	private JButton ok, annuler;
	private Sommet sSelected;
	
	public FenetreSupprimerSommet( Graphique graphe ){
		setTitle( "Supprimer Sommet" );
		setLayout( new GridLayout(2,2) );
		
		this.graphe = graphe;
		this.lSupp = new JLabel("Sommet à supprimer : ");
		this.tabSelection = new String[this.graphe.getAlSommet().size()];
		
		for( int i = 0; i < this.graphe.getAlSommet().size(); i++ )
			this.tabSelection[i] = this.graphe.getAlSommet().get(i).getNom();
		
		this.listSupp = new JList( this.tabSelection );
		this.listSupp.addListSelectionListener(this);
		this.scSelection = new JScrollPane( this.listSupp );
		
		add( this.lSupp );
		add( this.scSelection );
		
		this.ok = new JButton( "Ok" );
		this.annuler = new JButton( "Annuler" );
		
		this.ok.addActionListener(this);
		this.annuler.addActionListener(this);
		
		add( this.ok );
		add( this.annuler );
		
		pack();
		setVisible(true);
	}

	public void actionPerformed( ActionEvent e ) {
		
		if( e.getSource() == this.ok ){
			this.graphe.deleteSommet( this.sSelected );
			this.dispose();
		}
		if( e.getSource() == this.annuler ){
			this.dispose();
		}
		
	}

	public void valueChanged( ListSelectionEvent e ) {
		
		if( e.getSource() == this.listSupp ){
			this.sSelected = this.graphe.getAlSommet().get( this.listSupp.getSelectedIndex() );
			System.out.println(this.sSelected.getNom());
		}
	
	}
}
