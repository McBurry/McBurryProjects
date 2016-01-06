import java.awt.Dimension;
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

public class FenetreModifierSommet extends JFrame implements ActionListener, ListSelectionListener{

	private Graphique graphe;
	private JPanel pCentre, pHaut, pBas, pTotalHaut, pList;
	private JTextField tNom, tX, tY, tWidth, tHeight;
	private JLabel lNom, lX, lY, lWidth, lHeight, lList;
	private JButton ok, annuler;
	private JList list;
	private JScrollPane scList;
	private String[] tab;
	private ArrayList<Sommet> selection;
	private int selectedSommet;
	
	public FenetreModifierSommet( Graphique graphe, ArrayList<Sommet> selection ){
		setTitle("Modifier Sommet");
		this.graphe = graphe;
		if( selection == null )
			this.selection = this.graphe.getAlSommet();
		else
			this.selection = selection;
		
		this.pTotalHaut = new JPanel( new GridLayout(2,1) );
		
		this.pList = new JPanel( new GridLayout(1,2) );
		this.lList = new JLabel("Sommet : ");
		this.tab = new String[this.selection.size()];
		for( int i = 0; i < this.selection.size(); i++ ){
			this.tab[i] = this.selection.get(i).getNom();
		}
		this.list = new JList( this.tab );
		this.list.addListSelectionListener(this);
		this.scList = new JScrollPane( this.list );
		this.pList.add( this.lList );
		this.pList.add( this.scList );
		this.pTotalHaut.add( this.pList );
		
		this.pHaut = new JPanel( new GridLayout(1,2) );
		this.lNom = new JLabel("Nom");
		this.tNom = new JTextField(  );
		this.pHaut.add( this.lNom );
		this.pHaut.add( this.tNom );
		this.pTotalHaut.add( this.pHaut );
		this.pTotalHaut.setPreferredSize( new Dimension(50,50) );
		
		add( this.pTotalHaut, "North" );
		
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
			//this.graphe.addSommet( new Sommet( this.tNom.getText(), Integer.parseInt(this.tX.getText()), Integer.parseInt(this.tY.getText()), Integer.parseInt(this.tWidth.getText()), Integer.parseInt(this.tHeight.getText()) ) );
			Sommet s = this.graphe.getAlSommet().get( this.selectedSommet );
			s.setNom(this.tNom.getText());
			this.graphe.translate( this.selectedSommet, Integer.parseInt(this.tX.getText()), Integer.parseInt(this.tY.getText()));
			this.graphe.changeSize( this.selectedSommet,Integer.parseInt(this.tWidth.getText()), Integer.parseInt(this.tHeight.getText()) );
			this.graphe.repaint();
			System.out.println("Lol");
			this.dispose();
		}
		if( e.getSource() == this.annuler ){
			this.dispose();
		}
	}

	public void valueChanged(ListSelectionEvent e) {
		
		if( e.getSource() == this.list ){
			for( int i = 0; i < this.graphe.getAlSommet().size(); i++ ){
				if( this.graphe.getAlSommet().get(i) == this.selection.get( this.list.getSelectedIndex() ) ){
					Sommet s = this.graphe.getAlSommet().get(i);
					this.selectedSommet = i;
					this.tNom.setText( s.getNom() );
					this.tHeight.setText( "" + s.getHeight() );
					this.tWidth.setText( "" + s.getWidth() );
					this.tX.setText( "" + s.getX() );
					this.tY.setText( "" + s.getY() );
				}
			}
		}
		
	}
	
}
