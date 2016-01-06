import java.awt.Color;
import java.awt.Polygon;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Arrete extends JPanel {
	
	private int x1, y1, x2, y2;
	private Sommet s1, s2;
	
	private Line2D l;
	private ArrayList<Polygon> alPol;
	private ArrayList<Sommet> alSommet;
	private ArrayList<Sommet> direction;
	private Color color = Color.BLACK;
	
	private String nom = "";

	public Arrete( Sommet s1, Sommet s2 ) {
		this.s1 = s1;
		this.s2 = s2;
		this.x1 = s1.getCenterX();
		this.y1 = s1.getCenterY();
		this.x2 = s2.getCenterX();
		this.y2 = s2.getCenterY();
		this.alSommet = new ArrayList<Sommet>();
		
		this.l = new Line2D.Double( this.x1, this.y1, this.x2, this.y2 );
		this.alPol = new ArrayList<Polygon>();
		this.direction = new ArrayList<Sommet>();
	}
	
	public Arrete( Sommet s1, Sommet s2, ArrayList<Sommet> direction ){
		this(s1, s2);
		this.direction = direction;
		for( int i = 0; i < this.direction.size(); i++ )
			this.alPol.add(new Polygon( this.getTriangleX(this.direction.get(i)), this.getTriangleY(this.direction.get(i)), 3 ));
	}
	
	public int[] getTriangleX( Sommet s ){
		double angle = 0;
		int x = 0;
		if( s == this.s1 ){
			angle = this.getOrientationVersS1();
			x = this.x2;
			if( angle < 180 )
				angle = 180 + this.getOrientationVersS2();
			else
				angle =  this.getOrientationVersS2() - 180;
		}
		if( s == this.s2 ){
			angle = this.getOrientationVersS2();
			x = this.x1;
		}	
		
		double cos1 = Math.cos(Math.PI*(angle-20)/180);
		double cos2 = Math.cos(Math.PI*(angle+20)/180);
		
		int[] tab = { x, x+(int)(cos1*20), x+(int)(cos2*20) };
		
		return tab;
	}
	
	public int[] getTriangleY( Sommet s ){
		double angle = 0;
		int y = 0;
		if( s == this.s1 ){
			angle = this.getOrientationVersS1();
			y = this.y2;
			if( angle < 180 )
				angle = 180 + this.getOrientationVersS2();
			else
				angle =  this.getOrientationVersS2() - 180;
		}
		if( s == this.s2 ){
			angle = this.getOrientationVersS2();
			y = this.y1;
		}
		
		double sin1 = Math.sin(Math.PI*(angle-20)/180);
		double sin2 = Math.sin(Math.PI*(angle+20)/180);
		
		int[] tab = { y, y-(int)(sin1*20), y-(int)(sin2*20) };
		
		return tab;
	}
	
	//Utiliser Y = arccos[(a*a + b*b + c*c) / 2ab ]
	public Double getOrientationVersS2(){
		double c = this.s2.getCenterX() - this.s1.getCenterX();
		double a = this.s2.getCenterY() - this.s1.getCenterY();
		double b = Math.sqrt( (a*a) + (c*c) );
		
		double angleArrete = Math.acos((b*b + c*c - a*a) / (2*b*c));
		
		angleArrete = (180*angleArrete)/Math.PI;
		if( this.s2.getCenterY() > this.s1.getCenterY() )	angleArrete = (180-angleArrete)+180;
		
		return angleArrete;
	}
	
	public Double getOrientationVersS1(){
		double c = this.s2.getCenterX() - this.s1.getCenterX();
		double a = this.s2.getCenterY() - this.s1.getCenterY();
		double b = Math.sqrt( (a*a) + (c*c) );
		
		double angleArrete = Math.acos((b*b + c*c - a*a) / (2*b*c));
		
		angleArrete = (180*angleArrete)/Math.PI;
		if( this.s2.getCenterY() < this.s1.getCenterY() )	angleArrete = (180-angleArrete)+180;
		
		return angleArrete;
	}
	
	public Line2D getLine2D(){	return this.l;	}
	public ArrayList<Polygon> getAlPolygon(){	return this.alPol;	}
	public ArrayList<Sommet> getAlSommet(){	return this.alSommet;	}

	public String getNom(){	return this.nom;	}
	public int getX1() {	return x1;	}
	public int getY1() {	return y1;	}
	public int getX2() {	return x2;	}
	public int getY2() {	return y2;	}
	public Sommet getSommet1(){	return this.s1;	}
	public Sommet getSommet2(){	return this.s2;	}
	
	public void setX1(int x1){
		this.x1 = x1;
		this.l = new Line2D.Double( this.x1, this.y1, this.x2, this.y2 );
		this.alPol = new ArrayList<Polygon>();
		for( int i = 0; i < this.direction.size(); i++ )
			this.alPol.add(new Polygon( this.getTriangleX(this.direction.get(i)), this.getTriangleY(this.direction.get(i)), 3 ));
	}
	
	public void setY1(int y1){
		this.y1 = y1;
		this.l = new Line2D.Double( this.x1, this.y1, this.x2, this.y2 );
		this.alPol = new ArrayList<Polygon>();
		for( int i = 0; i < this.direction.size(); i++ )
			this.alPol.add(new Polygon( this.getTriangleX(this.direction.get(i)), this.getTriangleY(this.direction.get(i)), 3 ));
	}
	
	public void setX2(int x2){
		this.x2 = x2;
		this.l = new Line2D.Double( this.x1, this.y1, this.x2, this.y2 );
		this.alPol = new ArrayList<Polygon>();
		for( int i = 0; i < this.direction.size(); i++ )
			this.alPol.add(new Polygon( this.getTriangleX(this.direction.get(i)), this.getTriangleY(this.direction.get(i)), 3 ));
	}
	
	public void setY2(int y2){
		this.y2 = y2;
		this.l = new Line2D.Double( this.x1, this.y1, this.x2, this.y2 );
		this.alPol = new ArrayList<Polygon>();
		for( int i = 0; i < this.direction.size(); i++ )
			this.alPol.add(new Polygon( this.getTriangleX(this.direction.get(i)), this.getTriangleY(this.direction.get(i)), 3 ));
	}
	
	public void setColor( Color c )	{	this.color = c;		}
	public Color getColor()			{	return this.color;	}
}