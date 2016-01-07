/**
 * Classe gérant les sommets
 * 
 * @author Vallot Julien, Etancelin Pierre, Gourdain Loic, Florin kilian, Guelle Dylan
 * @version 1.0
 * 2016/01/07
 * 
 */
import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Sommet extends JPanel{
	
	private int hauteur, largeur, x, y;
	
	private Ellipse2D e;
	private ArrayList<Arrete> alArrete;
	private String nom;
	private Color color = Color.BLACK;
	
	/**
	 * constructeur de sommets
	 * @param nom
	 * @param x
	 * @param y
	 * @param largeur
	 * @param hauteur
	 */
	public Sommet( String nom, int x, int y, int largeur, int hauteur ){
		this.x = x;
		this.y = y;
		this.hauteur = hauteur;
		this.largeur = largeur;
		this.alArrete = new ArrayList<Arrete>();
		
		this.nom = nom;
		
		this.e = new Ellipse2D.Double( x, y, largeur, hauteur );
	}
	
	/**
	 * initialisation d'une elipse2d
	 */
	public Ellipse2D getEllipse2D(){	return this.e;	}
	
	/**
	 * 
	 * @param p
	 */
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
	
	/**
	 * permet de recuperer la position pour l'elipse2d
	 * @param x
	 * @param y
	 */
	public void setPosition( int x, int y ){
		this.x = x;
		this.y = y;
		
		this.e = new Ellipse2D.Double( this.x, this.y, this.largeur, this.hauteur );
	}
	
	/**
	 * permet de recuperer la taille pour l'elipse2D
	 * @param largeur
	 * @param hauteur
	 * (non-Javadoc)
	 * @see java.awt.Component#setSize(int, int)
	 */
	public void setSize( int largeur, int hauteur ){
		this.hauteur = hauteur;
		this.largeur = largeur;
		this.e = new Ellipse2D.Double( this.x, this.y, this.largeur, this.hauteur );
	}
	
	/**
	 * permet de definir un nom
	 * @param nom
	 */
	public void setNom( String nom ){	this.nom = nom;	}
	
	/**
	 * mutateur pour attribuer une couleur
	 * @param c
	 */
	public void setColor( Color c )	{	this.color = c;		}
	
	/**
	 * accesseur pour recuperer une couleur
	 */
	public Color getColor()			{	return this.color;	}
}
