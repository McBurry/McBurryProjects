import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Graphique extends JPanel implements MouseListener, MouseMotionListener{
	
	private Sommet s1;
	private JButton b1;
	private JPanel total;
	
	private JPanel jPanel;
	
	private int nbSommet;
	private int graphiqueWidth, graphiqueHeight;
	private int sommetWidth, sommetHeight;
	
	private boolean[][] tabLiaisons;
	
	private ArrayList<Sommet> alEl;
	private ArrayList<Arrete> alLine;
	
	private Graphics2D g2;
	private Dimension dim;
	private Arbre arbre;
	
	private boolean instance = false;
	
	public Graphique( Arbre arbre ){
		//setBackground( new Color(0,0,0) );
		addMouseListener(this);
		addMouseMotionListener(this);
		this.nbSommet = 0;
		this.sommetHeight = 30;
		this.sommetWidth = 0;
		setLayout( null );
		this.instance = true;
		this.arbre = arbre;

		this.alEl = new ArrayList<Sommet>();
		this.alLine = new ArrayList<Arrete>();
		
		repaint();
	}
	
	public int getNbSommet(){	return this.nbSommet;	}
	public ArrayList<Sommet> getAlSommet(){	return this.alEl;	}
	
	public ArrayList<Arrete> getAlLine(){	return this.alLine;	}
	
	//Ajoute un sommet au graphe avec des parametre par d�fault, puis r�actualise
	public void addSommet(){
		this.nbSommet++;
		Sommet e = new Sommet( Integer.toString(this.alEl.size()+1), 50, 50, this.alEl.get(0).getWidth(), this.alEl.get(0).getHeight() );
		this.alEl.add(e);
		repaint();
	}
	
	//Ajoute au graphe le sommet pass� en param�tre, puis r�actualise
	public void addSommet( Sommet s ){
		this.nbSommet++;
		this.alEl.add( s );
		System.out.println("yolo");
		this.arbre.maj();
		repaint();
	}
	
	//Ajoute au graphe l'arrete pass� en param�tre, puis r�actualise
	public void addArrete( Arrete a ){
		this.alLine.add(a);
		System.out.println("yolo");
		repaint();
	}
	
	//Supprime du graphe le sommet pass� en param�tre ainsi que toutes les arretes
	//lui �tant reli�es
	public void deleteSommet( Sommet s ){
		for( int i = 0; i < this.alEl.size(); i++ ){
			if( this.alEl.get(i) == s ){
				//Boucle permettant d'aller chercher et de supprimer chacune des arretes
				//�tant reli�es au sommet allant �tre supprim�
				for( int j = 0; j < this.alLine.size(); j++ ){
					if( this.alLine.get(j).getSommet1() == this.alEl.get(i) || this.alLine.get(j).getSommet2() == this.alEl.get(i) ){
						this.alLine.remove( this.alLine.get(j) );
						j--;
					}
				}
				this.alEl.remove(i);
			}
		}
		this.nbSommet--;
		this.arbre.maj();
		repaint();
	}
	
	//Supprime du graphe l'arrete pass� en param�tre et va aller supprimer
	//cette arrete et la supprimer dans chacun des sommets qui y �taient reli�s
	public void deleteArrete( Arrete a ){
		for( int i = 0; i < this.alLine.size(); i++ ){
			if( this.alLine.get(i) == a ){
				for( int j = 0; j < this.alEl.size(); j++ ){
					for( int k = 0; k < this.alEl.get(j).getAlArrete().size(); k++ ){
						if( this.alEl.get(j).getAlArrete().get(k) == a ){
							this.alEl.get(j).getAlArrete().remove(k);
							k--;
						}
					}
				}
				this.alLine.remove(i);
				System.out.println( "yata" );
			}
		}
		this.arbre.maj();
		repaint();
	}
	
	//Permet d'enregistrer le projet en transcrivant les donn�es du graphe dans le fichier f
	public void enregistrerSous( File f ){
		
		this.tabLiaisons = new boolean[this.nbSommet][this.nbSommet];
		
		//Instancie toutes les cases du tableau des liaisons � false pour pouvoir venir
		//Mettre � true les laisons entre les sommets
		for( int i = 0; i < this.tabLiaisons.length; i++ ){
			for( int j = 0; j < this.tabLiaisons.length; j++ ){
				this.tabLiaisons[i][j] = false;
			}
		}
		
		//Va chercher dans chacune des arretes � quelles sommets elle est reli�, puis la retranscrit dans le 
		//Tableau des liaisons par des trues
		for( int i = 0; i < this.alLine.size(); i++ ){
			for( int j = 0; j < this.alEl.size(); j++ ){
				for( int k = 0; k < this.alEl.size(); k++ ){
					if( this.alEl.get(j) == this.alLine.get(i).getSommet1() && this.alEl.get(k) == this.alLine.get(i).getSommet2() ){
						this.tabLiaisons[k][j] = true;
						this.tabLiaisons[j][k] = true;
						System.out.println("yeah");
					}
				}
			}
		}
		
		//Va �crire les donn�es du projet dans le fichier f � partir du tableau des relation
		//Sous forme de matrice
		try{
			FileWriter fw = new FileWriter( f );
			BufferedWriter bw = new BufferedWriter(fw);
			String s = "";
			for( int i = 0; i < this.tabLiaisons.length; i++ ){
				for( int j = 0; j < this.tabLiaisons.length; j++ ){
					if( j == this.tabLiaisons.length-1 ){
						if( !this.tabLiaisons[i][j] )	s+= "0\n";
						else	s += "1\n";
					}
					else{
						if( !this.tabLiaisons[i][j] )	s+= "0;";
						else	s += "1;";
					}
				}
			}
			bw.write(s);
			bw.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//Cette m�thode permet de r�cup�rer les donn�es du fichier ayant pour chemin la variable chemin
	//Puis elle ajoute ces donn�es au graphe actuel
	public void initFichier( String chemin ){
		this.graphiqueHeight = dim.width; // R�cup�re la hauteur de la zone graphique
		this.graphiqueWidth = dim.height; // R�cup�re la largeur de la zone graphique
		
		try{
			FileReader fr = new FileReader( chemin );
			Scanner sc = new Scanner ( fr );
			String s = sc.next();
			String[] tab = s.split(";");
			int cpt = 0;
			this.nbSommet = tab.length;
			
			//Initialise la taille en fonction du nombre de Sommet dans la matrice
			this.tabLiaisons = new boolean[this.nbSommet][this.nbSommet];
			
			fr = new FileReader( chemin );
			sc = new Scanner ( fr );
			
			//Initialise le tableau de relations entre les sommets en parcourant le scanner
			//Tous les 1 sont retranscrit en true et 0 en false
			while ( sc.hasNext() ){
				s = sc.nextLine();
				tab = s.split(";");
				
				for( int i = 0; i < tab.length; i++ ){
					if( tab[i].equals("1") )
						this.tabLiaisons[cpt][i] = true;
					if( tab[i].equals("0") )
						this.tabLiaisons[cpt][i] = false;
				}
				
				cpt++;
			}
			
			fr.close();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
		int sqrt = (int)(Math.ceil(Math.sqrt(this.nbSommet))); // R�cup�re le nombre de ligne de sommets
		
		//Va parcourrir l'ArrayList de Sommets indirectement sous forme de tableau a deux dimensions
		//Va instancier chacun de ces Sommets sous forme de tableau a deux dimensions
		//Puis va initialiser une arrete entre les Sommets devant �tre reli�
		for( int i = 0; i < sqrt; i++ ){
			for( int j = 0; j < sqrt; j++ ){
				//Cree un sommet pour chaque NbSommet
				if( this.alEl.size() < this.nbSommet ){
					Sommet e = new Sommet( Integer.toString(i*sqrt+j+1), (this.graphiqueHeight/sqrt)*i, (this.graphiqueWidth/sqrt)*j, (int)(Math.min(this.graphiqueHeight/sqrt, this.graphiqueWidth/sqrt) *0.8), (int)(Math.min(this.graphiqueHeight/sqrt, this.graphiqueWidth/sqrt) *0.8) );
					this.alEl.add((int)(i*sqrt+j),e);
				}
				//Cree une Arrete entre deux Sommets qui ont chacun true l'un vers l'autre
				//dans this.tabLiaisons
				for( int k = 0; k < this.alEl.size()-1; k++ ){
					if( this.tabLiaisons[this.alEl.size()-1][k] && this.tabLiaisons[k][this.alEl.size()-1] )
						this.createArrete(this.alEl.get(k), this.alEl.get(this.alEl.size()-1));
				}
			}
		}
		
		this.arbre.setAlSommet( this.alEl );
		repaint();
		this.arbre.maj();
	}
	
	//Cette m�thode permet de d�placer un Sommet vers une position
	//Elle d�place aussi par la m�me occasion les Arretes qui y sont reli�s
	public void translate( int nb, int x, int y ){
		Sommet e = this.alEl.get(nb);
		int centerX = e.getCenterX();
		int centerY = e.getCenterY();
		
		e.setPosition( x, y );
		
		for( int i = 0; i < e.getAlArrete().size(); i++ ){
			if( centerX == e.getAlArrete().get(i).getX1() && centerY == e.getAlArrete().get(i).getY1() ){
				e.getAlArrete().get(i).setX1(e.getCenterX());
				e.getAlArrete().get(i).setY1(e.getCenterY());
			}
			if( centerX == e.getAlArrete().get(i).getX2() && centerY == e.getAlArrete().get(i).getY2() ){
				e.getAlArrete().get(i).setX2(e.getCenterX());
				e.getAlArrete().get(i).setY2(e.getCenterY());
			}
		}
		
		repaint();
	}
	
	public void changeSize( int nb, int largeur, int hauteur ){
		Sommet e = this.alEl.get(nb);
		int centerX = e.getCenterX();
		int centerY = e.getCenterY();
		
		e.setSize( largeur, hauteur );
		
		for( int i = 0; i < e.getAlArrete().size(); i++ ){
			if( centerX == e.getAlArrete().get(i).getX1() && centerY == e.getAlArrete().get(i).getY1() ){
				e.getAlArrete().get(i).setX1(e.getCenterX());
				e.getAlArrete().get(i).setY1(e.getCenterY());
			}
			if( centerX == e.getAlArrete().get(i).getX2() && centerY == e.getAlArrete().get(i).getY2() ){
				e.getAlArrete().get(i).setX2(e.getCenterX());
				e.getAlArrete().get(i).setY2(e.getCenterY());
			}
		}
		
		repaint();
	}
	
	//Permet de cr�er un objet Arrete en le reliant avec deux Sommets
	public void createArrete( Sommet e1, Sommet e2 ){
		Arrete l = new Arrete( e1, e2 );
		e1.getAlArrete().add(l);
		e2.getAlArrete().add(l);
		this.alLine.add(l);
	}
	
	//Cr�e un nouveau graphe
	//Cette m�thode est appel� par la classe FenetreNouveauProjet
	//Car elle permet par la m�me occasion de r�initialiser la fenetre
	public void newGraphique(){
		this.nbSommet = 0;
		this.alEl = new ArrayList<Sommet>();
		this.alLine = new ArrayList<Arrete>();
		this.arbre.maj();
		repaint();
	}
	
	
	public void paintComponent( Graphics g ){
		this.g2 = (Graphics2D) g;
		super.paintComponent(g);
		this.dim = getSize();
		//this.arbre.maj();
		
		this.instance = false;
		
		if( this.nbSommet > 0 ){
			//Affichage de chacun des Sommets
			for( int i = 0; i < this.alEl.size(); i++ ){
				this.g2.draw( this.alEl.get(i).getEllipse2D() );
				this.g2.drawString( this.alEl.get(i).getNom(), this.alEl.get(i).getCenterX(), this.alEl.get(i).getCenterY() );
			}
			
			//Affichage de chacune des Arretes
			for( int i = 0; i < this.alLine.size(); i++ ){
				this.g2.draw( alLine.get(i).getLine2D() );
			}
		}
	}

	public void mouseClicked	(MouseEvent e) {}
	public void mouseEntered	(MouseEvent e) {}
	public void mouseExited		(MouseEvent e) {}
	public void mousePressed	(MouseEvent e) {}
	public void mouseMoved		(MouseEvent e) {}
	public void mouseReleased	(MouseEvent e) {}
	
	//Est appel� � chaque glissement de souris avec clic enfonc�
	//Elle permet de d�placer un sommet en regardant si la souris est dans le sommet
	public void mouseDragged	(MouseEvent e){
		for( int i = 0; i < this.alEl.size(); i++ ){
			if( alEl.get(i).contains( new Point(e.getX(),e.getY()) ) ){
				this.translate( i, (int)(e.getX()-(alEl.get(i).getWidth()/2)), (int)(e.getY()-(alEl.get(i).getHeight()/2)) );
				//System.out.println("lol");
				break;
			}
		}
	}
}
