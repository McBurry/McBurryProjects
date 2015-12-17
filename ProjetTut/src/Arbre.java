import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Arbre extends JPanel implements ActionListener{
	
	private ArrayList<Sommet> alSommet;
	private ArrayList<Arrete> alArrete;
	private ArrayList<Sommet> isSelected;
	private ArrayList<JButton> alJButton;
	
	private int nombre;
	
	public Arbre(){
		setLayout( new GridLayout(1,2) );
		this.alJButton = new ArrayList<JButton>();
		this.isSelected = new ArrayList<Sommet>();
		this.nombre = this.alJButton.size();
	}
	
	public void setAlSommet( ArrayList<Sommet> alSommet ){
		this.alSommet = alSommet;
	}
	
	public void setAlArrete( ArrayList<Arrete> alArrete ){
		this.alArrete = alArrete;
	}
	
	public void maj(){
		removeAll();
		setLayout( new GridLayout(this.nombre,2,10,10) );
		
		System.out.println(this.nombre);
		
		this.alJButton = new ArrayList<JButton>();
		this.isSelected = new ArrayList<Sommet>();
		
		for( int i = 0; i < this.alSommet.size(); i++ ){
			JButton b = new JButton("+");
			b.setPreferredSize( new Dimension(45,20) );
			this.alJButton.add(b);
			b.addActionListener(this);
			add( b );
			add( new Label(this.alSommet.get(i).getNom()) );
		}
		revalidate();
	}
	
	public void actionPerformed(ActionEvent e) {
		for( int i = 0; i < this.alJButton.size(); i++ ){
			if( e.getSource() == this.alJButton.get(i) ){
				if( this.isSelected.contains( this.alSommet.get(i) ) ){
					this.alJButton.get(i).setText("+");
					this.isSelected.remove( this.alSommet.get(i) );
				}
				else{
					this.alJButton.get(i).setText("-");
					this.isSelected.add( this.alSommet.get(i) );
					this.nombre = this.nombre + this.alSommet.get(i).getAlArrete().size();
				}
			}
			revalidate();
		}
	}
}