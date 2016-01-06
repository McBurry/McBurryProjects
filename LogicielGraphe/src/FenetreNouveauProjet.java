import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FenetreNouveauProjet extends JFrame implements ActionListener{

	private JTextField tNom;
	private JLabel lNom, choixMatrice;
	private JButton ok, annuler;
	private Graphique graphe;
	private Checkbox oui, non;
	private CheckboxGroup group;
	private JPanel cPanel;
	
	public FenetreNouveauProjet( Graphique graphe ){
		setLayout( new GridLayout(3,2) );
		
		this.graphe = graphe;
		this.lNom = new JLabel("Nom nouveau du projet");
		this.tNom = new JTextField();
		
		this.choixMatrice = new JLabel("Graphe orienté ?");
		this.cPanel = new JPanel( new GridLayout(1,2) );
		this.group = new CheckboxGroup();
		this.oui = new Checkbox("oui", this.group, false);
		this.non = new Checkbox("non", this.group, false);
		
		this.ok = new JButton("Ok");
		this.annuler = new JButton("Annuler");
		
		this.ok.addActionListener(this);
		this.annuler.addActionListener(this);
		
		this.cPanel.add( this.oui );
		this.cPanel.add( this.non );
		
		add(this.lNom);
		add(this.tNom);
		add(this.choixMatrice);
		add(this.cPanel);
		add(this.ok);
		add(this.annuler);
		
		pack();
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if( e.getSource() == this.ok ){
			this.graphe.newGraphique();
			Logiciel.currentFile = new File ("unregistred");
			if( this.group.getSelectedCheckbox() == this.oui )	this.graphe.setOriente(true);
			else	this.graphe.setOriente(false);
			Logiciel.nomProjet = this.tNom.getText();
			this.dispose();
		}
		if( e.getSource() == this.annuler ){
			this.dispose();
		}
	}
}
