import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Arrete extends JPanel {
	
	private int x1, y1, x2, y2;
	private Sommet s1, s2;
	
	private Line2D l;
	private ArrayList<Sommet> alSommet;

	public Arrete( Sommet s1, Sommet s2 ) {
		this.s1 = s1;
		this.s2 = s2;
		this.x1 = s1.getCenterX();
		this.y1 = s1.getCenterY();
		this.x2 = s2.getCenterX();
		this.y2 = s2.getCenterY();
		this.alSommet = new ArrayList<Sommet>();
		
		this.l = new Line2D.Double( this.x1, this.y1, this.x2, this.y2 );
	}
	
	public Line2D getLine2D(){	return this.l;	}
	public ArrayList<Sommet> getAlSommet(){	return this.alSommet;	}

	public int getX1() {	return x1;	}
	public int getY1() {	return y1;	}
	public int getX2() {	return x2;	}
	public int getY2() {	return y2;	}
	public Sommet getSommet1(){	return this.s1;	}
	public Sommet getSommet2(){	return this.s2;	}
	
	public void setX1(int x1){
		this.x1 = x1;
		this.l = new Line2D.Double( this.x1, this.y1, this.x2, this.y2 );
	}
	
	public void setY1(int y1){
		this.y1 = y1;
		this.l = new Line2D.Double( this.x1, this.y1, this.x2, this.y2 );
	}
	
	public void setX2(int x2){
		this.x2 = x2;
		this.l = new Line2D.Double( this.x1, this.y1, this.x2, this.y2 );
	}
	
	public void setY2(int y2){
		this.y2 = y2;
		this.l = new Line2D.Double( this.x1, this.y1, this.x2, this.y2 );
	}
}