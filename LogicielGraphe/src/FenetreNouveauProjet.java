import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class FenetreNouveauProjet extends JFrame implements ActionListener{

	private JTextField tNom;
	private JLabel lNom;
	private JButton ok, annuler;
	private Graphique graphe;
	
	public FenetreNouveauProjet( Graphique graphe ){
		setLayout( new GridLayout(2,2) );
		
		this.graphe = graphe;
		this.lNom = new JLabel("Nom nouveau du projet");
		this.tNom = new JTextField();
		this.ok = new JButton("Ok");
		this.annuler = new JButton("Annuler");
		
		this.ok.addActionListener(this);
		this.annuler.addActionListener(this);
		
		add(this.lNom);
		add(this.tNom);
		add(this.ok);
		add(this.annuler);
		
		pack();
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if( e.getSource() == this.ok ){
			this.graphe.newGraphique();
			Logiciel.nomProjet = this.tNom.getText();
			this.dispose();
		}
		if( e.getSource() == this.annuler ){
			this.dispose();
		}
	}
}
