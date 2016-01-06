import java.awt.geom.Arc2D;

import javax.swing.JPanel;

public class Arc extends JPanel{
	
	private String nom;
	private Sommet s1, s2;
	private int x1, x2, y1, y2;
	private Arc2D arc;
	
	public Arc( Sommet s1, Sommet s2 ){
		this.s1 = s1;
		this.s2 = s2;
		this.x1 = s1.getCenterX();
		this.y1 = s1.getCenterY();
		this.x2 = s2.getCenterX();
		this.y2 = s2.getCenterY();
		
		this.arc = new Arc2D.Double( this.x1, this.y1, Math.abs( this.x2 - this.x1 ), Math.abs( this.y2 - this.y1 ), 50, 50, Arc2D.OPEN );
	}
	
	public Arc2D getArc2D(){	return this.arc;	}
}
