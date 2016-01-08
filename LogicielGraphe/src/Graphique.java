import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.itextpdf.awt.PdfGraphics2D;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

public class Graphique extends JPanel implements ActionListener, MouseListener, MouseMotionListener,MouseWheelListener{
	
	private Sommet s1;
	private JButton b1;
	private JPanel total;
	
	private JPanel jPanel;
	
	private int nbSommet;
	private int graphiqueWidth, graphiqueHeight;
	private int sommetWidth, sommetHeight;
	
	private boolean[][] tabLiaisons;
	
	private ArrayList<Sommet> alSommet;
	private ArrayList<Arrete> alLine;
	
	private ArrayList<Sommet> selectedSommet;
	private ArrayList<Arrete> selectedArrete;
	
	private ArrayList<Integer> keyPressed;
	
	private Graphics2D g2;
	private Dimension dim;
	private Arbre arbre;
	
	private boolean instance = false;
	private boolean oriente = false;
	
	private JPopupMenu popup;
	private JMenuItem addSommet, deleteSommet, modifySommet, addArrete, deleteArrete, modifyArrete;
	
	public Graphique( Arbre arbre ){
		addMouseListener(this);
		addMouseMotionListener(this);
		this.nbSommet = 0;
		this.sommetHeight = 30;
		this.sommetWidth = 0;
		setLayout( null );
		this.instance = true;
		this.arbre = arbre;

		this.alSommet = new ArrayList<Sommet>();
		this.alLine = new ArrayList<Arrete>();
		
		this.selectedSommet = new ArrayList<Sommet>();
		this.selectedArrete = new ArrayList<Arrete>();
		
		this.keyPressed = new ArrayList<Integer>();
		
		repaint();
	}
	
	public int getNbSommet(){	return this.nbSommet;	}
	public ArrayList<Sommet> getAlSommet(){	return this.alSommet;	}
	public ArrayList<Arrete> getAlLine(){	return this.alLine;	}
	public boolean getOriente(){	return this.oriente;	}
	
	public void setOriente( boolean oriente ){	this.oriente = oriente;	}
	
	//Ajoute un sommet au graphe avec des parametre par défault, puis réactualise
	public void addSommet(){
		this.nbSommet++;
		Sommet e = new Sommet( Integer.toString(this.alSommet.size()+1), 50, 50, this.alSommet.get(0).getWidth(), this.alSommet.get(0).getHeight() );
		this.alSommet.add(e);
		repaint();
	}
	
	//Ajoute au graphe le sommet passé en paramètre, puis réactualise
	public void addSommet( Sommet s ){
		this.nbSommet++;
		this.alSommet.add( s );
		this.arbre.maj();
		repaint();
	}
	
	//Ajoute au graphe l'arrete passé en paramètre, puis réactualise
	public void addArrete( Arrete a ){
		this.alLine.add(a);
		repaint();
	}
	
	//Supprime du graphe le sommet passé en paramètre ainsi que toutes les arretes
	//lui étant reliées
	public void deleteSommet( Sommet s ){
		for( int i = 0; i < this.alSommet.size(); i++ ){
			if( this.alSommet.get(i) == s ){
				//Boucle permettant d'aller chercher et de supprimer chacune des arretes
				//étant reliées au sommet allant être supprimé
				for( int j = 0; j < this.alLine.size(); j++ ){
					if( this.alLine.get(j).getSommet1() == this.alSommet.get(i) || this.alLine.get(j).getSommet2() == this.alSommet.get(i) ){
						this.alLine.remove( this.alLine.get(j) );
						j--;
					}
				}
				this.alSommet.remove(i);
			}
		}
		this.nbSommet--;
		this.arbre.maj();
		repaint();
	}
	
	//Supprime du graphe l'arrete passé en paramètre et va aller supprimer
	//cette arrete et la supprimer dans chacun des sommets qui y étaient reliés
	public void deleteArrete( Arrete a ){
		for( int i = 0; i < this.alLine.size(); i++ ){
			if( this.alLine.get(i) == a ){
				for( int j = 0; j < this.alSommet.size(); j++ ){
					for( int k = 0; k < this.alSommet.get(j).getAlArrete().size(); k++ ){
						if( this.alSommet.get(j).getAlArrete().get(k) == a ){
							this.alSommet.get(j).getAlArrete().remove(k);
							k--;
						}
					}
				}
				this.alLine.remove(i);
			}
		}
		this.arbre.maj();
		repaint();
	}
	
	//Permet d'enregistrer le projet en transcrivant les données du graphe dans le fichier f
	public void enregistrerSous( File f ){
		
		this.tabLiaisons = new boolean[this.alSommet.size()][this.alSommet.size()];
		
		//Instancie toutes les cases du tableau des liaisons à false pour pouvoir venir
		//Mettre à true les laisons entre les sommets
		for( int i = 0; i < this.tabLiaisons.length; i++ ){
			for( int j = 0; j < this.tabLiaisons.length; j++ ){
				this.tabLiaisons[i][j] = false;
			}
		}
		
		//Va chercher dans chacune des arretes à quelles sommets elle est relié, puis la retranscrit dans le 
		//Tableau des liaisons par des trues
		for( int i = 0; i < this.alLine.size(); i++ ){
			for( int j = 0; j < this.alSommet.size(); j++ ){
				for( int k = 0; k < this.alSommet.size(); k++ ){
					if( this.alSommet.get(j) == this.alLine.get(i).getSommet1() && this.alSommet.get(k) == this.alLine.get(i).getSommet2() ){
						if( this.alLine.get(i).getAlDirection().contains( this.alSommet.get(j) ) )	this.tabLiaisons[j][k] = true;
						if( this.alLine.get(i).getAlDirection().contains( this.alSommet.get(k) ) )	this.tabLiaisons[k][j] = true;
						//this.tabLiaisons[j][k] = true;
					}
				}
			}
		}
		
		//Va écrire les données du projet dans le fichier f à partir du tableau des relation
		//Sous forme de matrice
		try{
			FileWriter fw = new FileWriter( f );
			BufferedWriter bw = new BufferedWriter(fw);
			String s = "";
			
			//Liste de paramètre du graphe
			s += "oriente:" + this.getOriente() + "\n";
			s += "size:" + this.alSommet.size() + "\n";
			s += "carac:\n";
			for( int i = 0; i < this.alSommet.size(); i++ )
				s+= "x:"+this.alSommet.get(i).getX()+";y:"+this.alSommet.get(i).getY()+";w:"+this.alSommet.get(i).getWidth()+";h:"+this.alSommet.get(i).getHeight()+"\n";
			
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
	
	//Cette méthode permet de récupérer les données du fichier ayant pour chemin la variable chemin
	//Puis elle ajoute ces données au graphe actuel
	public void initFichier( String chemin ){
		this.graphiqueHeight = dim.width; // Récupère la hauteur de la zone graphique
		this.graphiqueWidth = dim.height; // Récupère la largeur de la zone graphique
		int nbS = 0;
		int[][] tabCarac = new int[0][0];
		
		try{
			FileReader fr = new FileReader( chemin );
			Scanner sc = new Scanner ( fr );
			
			//identifie si le graphe est orienté ou non
			String oriente = sc.next();
			if( oriente.contains("true") )	this.setOriente(true);
			else	this.setOriente(false);
			
			//Initialise le nombre de sommets
			String size = sc.next();
			if( size.contains("size") ){
				size = size.replace("size:","");
				nbS = Integer.parseInt(size);
			}
			
			//Initialise un tableau les carac des sommets
			if( sc.next().contains("carac") ){
				tabCarac = new int[nbS][4];
				for( int i = 0; i < nbS; i++ ){
					String[] line = sc.next().split(";");
					for( int j = 0; j < line.length; j++ ){
						line[j] = line[j].replace("x:","");
						line[j] = line[j].replace("y:","");
						line[j] = line[j].replace("w:","");
						line[j] = line[j].replace("h:","");
						tabCarac[i][j] = Integer.parseInt(line[j]);
					}
				}
			}
			
			//Initialise la taille du tableau en fonction du nombre de Sommet dans la matrice
			this.tabLiaisons = new boolean[nbS][nbS];
			int cpt = 0;
			
			//Initialise le tableau de relations entre les sommets en parcourant le scanner
			//Tous les 1 sont retranscrit en true et 0 en false
			while ( sc.hasNext() ){
				String s = sc.next();
				String[] tab = s.split(";");
				
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
		
		int sqrt = (int)(Math.ceil(Math.sqrt(nbS))); // Récupère le nombre de ligne de sommets
		
		//Sommets
		ArrayList<Sommet> alTemp = new ArrayList<Sommet>();
		for( int i = 0; i < nbS; i++ ){
			alTemp.add( new Sommet( Integer.toString(i), tabCarac[i][0], tabCarac[i][1], tabCarac[i][2], tabCarac[i][3] ) );
		}
		//Arretes
		for( int k = 0; k < this.tabLiaisons.length; k++ ){
			for( int l = 0; l < this.tabLiaisons.length; l++ ){
				if( !this.getOriente() ){
					if( this.tabLiaisons[l][k] && this.tabLiaisons[k][l] )
						this.createArrete(alTemp.get(l), alTemp.get(k));
				}
				else{
					ArrayList<Sommet> dir = new ArrayList<Sommet>();
					
					if(this.tabLiaisons[l][k] || this.tabLiaisons[k][l] ){
						if( this.tabLiaisons[l][k] ){
							dir.add(alTemp.get(l));
						}
						if( this.tabLiaisons[k][l] ){
							dir.add(alTemp.get(k));
						}
						this.createArrete(alTemp.get(l), alTemp.get(k), dir );
					}
				}
			}
		}
		
		this.alSommet.addAll( alTemp );
		
		this.arbre.setAlSommet( this.alSommet );
		repaint();
		this.arbre.maj();
	}
	
	//Cette méthode permet de déplacer un Sommet vers une position
	//Elle déplace aussi par la même occasion les Arretes qui y sont reliés
	public void translate( int nb, int x, int y ){
		Sommet e = this.alSommet.get(nb);
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
		Sommet e = this.alSommet.get(nb);
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
	
	//Permet de créer un objet Arrete en le reliant avec deux Sommets
	public void createArrete( Sommet e1, Sommet e2 ){
		Arrete l = new Arrete( e1, e2 );
		e1.getAlArrete().add(l);
		e2.getAlArrete().add(l);
		this.alLine.add(l);
	}
	
	//Permet de créer un objet Arrete orienté en le reliant avec deux Sommets
	public void createArrete( Sommet e1, Sommet e2, ArrayList<Sommet> dir ){
		Arrete l = new Arrete( e1, e2, dir );
		e1.getAlArrete().add(l);
		e2.getAlArrete().add(l);
		this.alLine.add(l);
		repaint();
	}
	
	//Crée un nouveau graphe
	//Cette méthode est appelé par la classe FenetreNouveauProjet
	//Car elle permet par la même occasion de réinitialiser la fenetre
	public void newGraphique(){
		this.nbSommet = 0;
		this.alSommet = new ArrayList<Sommet>();
		this.alLine = new ArrayList<Arrete>();
		this.arbre.setAlSommet(this.alSommet);
		this.arbre.maj();
		repaint();
	}
	
	public void exportImage(){
		File fileToSave = null;
        try{
    		BufferedImage bufferedImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
    		JFileChooser fileChooser = new JFileChooser();
    		fileChooser.setDialogTitle("Enregistrer sous");
    		FileFilter imageFilter = new FileNameExtensionFilter("PNG", "png");
    		fileChooser.addChoosableFileFilter(imageFilter);
    		fileChooser.setFileFilter(imageFilter);
    		int userSelection = fileChooser.showDialog(fileChooser, "Enregistrer");
    		if (userSelection == JFileChooser.APPROVE_OPTION){
    			fileToSave = fileChooser.getSelectedFile();
    			if (!fileToSave.getPath().contains(".png"))
    				fileToSave = new File(fileToSave.getPath() + ".png");
    		}
			g2 = bufferedImage.createGraphics();
			paintAll(g2);
	        ImageIO.write(bufferedImage, "png", fileToSave);
    	}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void exportPdf() throws DocumentException{
		String fileToSave = null;
        try{
    		BufferedImage bufferedImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
    		JFileChooser fileChooser = new JFileChooser();
    		fileChooser.setDialogTitle("Selectionnez l'emplacement du fichier à sauvergarder");
    		int userSelection = fileChooser.showSaveDialog(this);
    		if (userSelection == JFileChooser.APPROVE_OPTION){
    			fileToSave = fileChooser.getSelectedFile().toString();
    			if (!fileToSave.contains(".pdf"))
    				fileToSave += ".pdf";
    		}
			g2 = bufferedImage.createGraphics();
	        Document document = new Document();
	        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileToSave));
			g2 = bufferedImage.createGraphics();
			paintAll(g2);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "png", baos);
			Image iTextImage = Image.getInstance(baos.toByteArray());
			
            document.addAuthor("Groupe 10 BTW");
            document.addTitle("Importation du graph "+ Logiciel.nomProjet);
            document.open();
            Paragraph PImage = new Paragraph();
            PImage.setAlignment(Element.ALIGN_CENTER);
            PImage.add(iTextImage);
            document.add(PImage);
            document.close();
			
    	}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void paintComponent( Graphics g ){
		this.g2 = (Graphics2D) g;
		super.paintComponent(g);
		this.dim = getSize();
		//this.arbre.maj();
		
		this.instance = false;
		
		if( this.alSommet.size() > 0 ){
			//Affichage de chacun des Sommets
			for( int i = 0; i < this.alSommet.size(); i++ ){
				this.g2.setPaint( this.alSommet.get(i).getColor() );
				this.g2.draw( this.alSommet.get(i).getEllipse2D() );
				this.g2.drawString( this.alSommet.get(i).getNom(), this.alSommet.get(i).getCenterX(), this.alSommet.get(i).getCenterY() );
			}
			
			//Affichage de chacune des Arretes
			for( int i = 0; i < this.alLine.size(); i++ ){
				this.g2.setPaint( this.alLine.get(i).getColor() );
				this.g2.draw( this.alLine.get(i).getLine2D() );
				for( int j = 0; j < this.alLine.get(i).getAlPolygon().size(); j++ )
					this.g2.draw( this.alLine.get(i).getAlPolygon().get(j) );
				//System.out.println( Math.round(alLine.get(i).getOrientationVersS2()) );
			}
		}
	}

	public void mouseClicked	(MouseEvent e) {
		if( e.getButton() == MouseEvent.BUTTON1 ){
			if( !this.keyPressed.contains( KeyEvent.VK_SHIFT) ){
				this.selectedSommet = new ArrayList<Sommet>();
				for( int i = 0; i < this.alSommet.size(); i++ ){
					alSommet.get(i).setColor( Color.BLACK );
				}
			}
			for( int i = 0; i < this.alSommet.size(); i++ ){
				if( alSommet.get(i).contains( new Point(e.getX(),e.getY()) ) ){
					alSommet.get(i).setColor( Color.BLUE );
					this.selectedSommet.add( alSommet.get(i) );
					break;
				}
			}
			repaint();
		}
		if( e.getButton() == MouseEvent.BUTTON3 ){
			this.popup = new JPopupMenu();
			this.popup.add( this.addSommet = new JMenuItem("Ajouter Sommet") );
			this.addSommet.addActionListener(this);
			for( int i = 0; i < this.alSommet.size(); i++ ){
				if( alSommet.get(i).contains( new Point(e.getX(),e.getY()) ) ){
					this.popup.add( this.modifySommet = new JMenuItem("Modifier Sommet") );
					this.popup.add( this.deleteSommet = new JMenuItem("Supprimer Sommet") );
					this.deleteSommet.addActionListener(this);
					break;
				}
			}
			this.popup.show( Graphique.this, e.getX(), e.getY() );
			repaint();
		}
	}
	
	public void mouseEntered	(MouseEvent e) {}
	public void mouseExited		(MouseEvent e) {}
	public void mousePressed	(MouseEvent e) {}
	public void mouseMoved		(MouseEvent e) {}
	public void mouseReleased	(MouseEvent e) {}
	
	public void resetKeyPressed() {	this.keyPressed = new ArrayList<Integer>();	}
	public void setKeyPressed( int  i ) {	this.keyPressed.add(i);	}
	
	//Est appelé à chaque glissement de souris avec clic enfoncé
	//Elle permet de déplacer un sommet en regardant si la souris est dans le sommet
	public void mouseDragged	(MouseEvent e){
		for( int i = 0; i < this.alSommet.size(); i++ ){
			if( alSommet.get(i).contains( new Point(e.getX(),e.getY()) ) ){
				this.translate( i, (int)(e.getX()-(alSommet.get(i).getWidth()/2)), (int)(e.getY()-(alSommet.get(i).getHeight()/2)) );
				break;
			}
		}
	}
	public void mouseWheelMoved(MouseWheelEvent e){
		System.out.println("salut");
		System.out.println("ez");
		System.out.println("ioehozerg");
	}

	public void actionPerformed(ActionEvent e) {
		if( e.getSource() == this.addSommet ){
			FenetreNouveauSommet n = new FenetreNouveauSommet( this );
		}
		if( e.getSource() == this.modifySommet ){
			FenetreModifierSommet n = new FenetreModifierSommet( this, this.selectedSommet );
		}
		if( e.getSource() == this.deleteSommet ){
			for( int i = 0; i < this.selectedSommet.size(); i++ )
				this.deleteSommet( this.selectedSommet.get(i) );
		}
		System.out.println("qzdqz");
	}
}
