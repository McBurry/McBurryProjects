import java.awt.Font;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Sommet extends JPanel{
	
	private int hauteur, largeur, x, y;
	
	private Ellipse2D e;
	private ArrayList<Arrete> alArrete;
	private String nom;
	
	public Sommet( String nom, int x, int y, int largeur, int hauteur ){
		this.x = x;
		this.y = y;
		this.hauteur = hauteur;
		this.largeur = largeur;
		this.alArrete = new ArrayList<Arrete>();
		
		this.nom = nom;
		
		this.e = new Ellipse2D.Double( x, y, largeur, hauteur );
	}
	
	public Ellipse2D getEllipse2D(){	return this.e;	}
	
	public boolean contains( Point p ){	return this.e.contains(p);	}
	
	public int getWidth(){	return this.largeur;	}
	public int getHeight(){	return this.hauteur;	}
	public String getNom(){	return this.nom;		}
	public ArrayList<Arrete> getAlArrete(){	return this.alArrete;	}
	
	public int getX(){	return (int)this.e.getX();	}
	public int getY(){	return (int)this.e.getY();	}
	public int getCenterX()	{	return (int)this.e.getCenterX();	}
	public int getCenterY()	{	return (int)this.e.getCenterY();	}
	public void setAlArrete( ArrayList<Arrete> alArrete ){	this.alArrete = alArrete;	}
	
	public void setPosition( int x, int y ){
		this.x = x;
		this.y = y;
		this.e = new Ellipse2D.Double( this.x, this.y, this.largeur, this.hauteur );
	}
	
	public void setSize( int largeur, int hauteur ){
		this.hauteur = hauteur;
		this.largeur = largeur;
		this.e = new Ellipse2D.Double( this.x, this.y, this.largeur, this.hauteur );
	}
	
	public void setNom( String nom ){	this.nom = nom;	}
}
