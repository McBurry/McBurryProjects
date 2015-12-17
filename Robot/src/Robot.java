/* Groupe Gamma
 * GAND Adrien
 * VALLOT Julien
 * GOURDAIN Loic
 */
public class Robot {

	static final double DIST_PETITE=30;
	static final double DIST_GRANDE=70;
	static final double VIT_GRANDE=10.0;
	static final double VIT_PETITE=2.5;
	
	private double vitesseAvant;
	
	private char[][] tab;
	private int tabLigne;
	private int tabColonne;
	
	private double orientation;
	
	public Robot(){
		this.tab = new char[400][400];
		
		this.tabLigne = this.tab.length/2;
		this.tabColonne = this.tab.length/2;
		
		this.orientation = 90;
		
		for( int i = 0; i < this.tab.length; i++ ){
			for( int j = 0; j < this.tab.length; j++ ){
				if( i == 0 || i == this.tab.length-1 || j == 0 || j == this.tab.length-1 )
					this.tab[i][j] = 'M';
				else
					this.tab[i][j] = '.';
			}
		}
		
	}
	
	/**
	 * La methode getDistance renvoie à quelle distance se trouve le bord du tableau
	 * en ligne droite en fonction de l'angle donné en paramètre
	 * @param angle
	 * @return
	 */
	public int getDistance( double angle ){
		
		double cos = Math.cos(angle*(Math.PI/180));
		double sin = Math.sin(angle*(Math.PI/180));
		
		int colTemp = this.tabColonne;
		int ligTemp = this.tabLigne;
		int distance = 0;
		
		while( !((this.tabColonne+(cos*10))<=10) && !((this.tabColonne+(cos*10))>=(this.tab.length-10)) && !((this.tabLigne+(sin*10))<=10) && !((this.tabLigne+(sin*10))>=(this.tab.length-10)) ){
			this.tabColonne = (int)(this.tabColonne-(cos*10));
			this.tabLigne = (int)(this.tabLigne-(sin*10));
		}
		
		distance = (int)( Math.sqrt( Math.pow(Math.abs(colTemp-this.tabColonne), 2 ) + Math.pow(Math.abs(ligTemp-this.tabLigne), 2 ) ) );
		
		this.tabColonne = colTemp;
		this.tabLigne = ligTemp;
		
		return distance;
	}
	
	/**
	 * La méthode deplacement fait appel à setVitesse() permettant de 
	 * parametrer l'orientation et la vitesse du robot
	 * puis ensuite fait avancer le robot dans le sens de son orientation
	 * et a la vitesse actuelle
	 */
	public void deplacement(){
		this.setVitesse();
		double cos = Math.cos(this.orientation*(Math.PI/180));
		double sin = Math.sin(this.orientation*(Math.PI/180));
		
		this.tabColonne = (int)(this.tabColonne-(cos*this.vitesseAvant));
		this.tabLigne = (int)(this.tabLigne-(sin*this.vitesseAvant));
		
		try { 
			Thread.sleep(200);
		}
		catch (InterruptedException exception) {
			exception.printStackTrace();
		}
	}
	
	/**
	 * La methode setVitesse augmente ou diminue la vitesse avant 
	 * en regardant si la distance devant le robot est grande ou non
	 * elle gère aussi par la même occasion l'orientation du robot
	 * si il doit changer de direction
	 */
	public void setVitesse(){

		int test = 0;
		
		if( this.grandeDistance(this.getDistance(this.orientation)) >= 1 ){
			this.vitesseAvant = VIT_GRANDE;
		}
		else{
			if( this.grandeDistance(this.getDistance(this.orientation)) >= 0.5 ){
				this.vitesseAvant = VIT_PETITE;
				if( this.getDistance(this.orientation-30) >= 0 ){
					test = 1;
				}
				else{
					if( this.getDistance(this.orientation+30) >= 0 ){
						test=-1;
					}
				}
			}
			else{
				this.vitesseAvant = 0;
				test = 1;
			}
		}
		
		if( test == 1 )this.orientation = this.orientation + 5;
		if( test == -1 )this.orientation = this.orientation - 5;
	}
	
	/**
	 * Renvoie une valeure entre 0 et 1 donnat l'importance de la grande distance
	 * @param distance
	 * @return
	 */
	public double grandeDistance(double distance){
		if(distance>=DIST_GRANDE)
			return 1;
		
		else if(distance<=DIST_PETITE)
			return 0;
		return (distance-DIST_PETITE)/(DIST_GRANDE-DIST_PETITE);	
	}
	
	/**
	 * Renvoie une valeure entre 0 et 1 donnant l'importance de la petite distance
	 * @param distance
	 * @return
	 */
	public double petiteDistance(double distance){
		return 1-this.grandeDistance(distance);	
	}

	/**
	 * La methode toString affiche le tableau en format 1:10 pour faciliter la lecture du tableau
	 */
	public String toString(){
		String s = "";
		
		for( int i = 0; i < this.tab.length; i++ ){
			for( int j = 0; j < this.tab.length; j++ ){
				if( ( this.tabLigne/10 == i/10 && this.tabColonne/10 == j/10 ) && ( i % 10 == 0 || i == this.tab.length-1 ) && ( j % 10 == 0 || j == this.tab.length-1 ) )
					s += 'R';
				else{
					if( ( i % 10 == 0 || i == this.tab.length-1 ) && ( j % 10 == 0 || j == this.tab.length-1 ) )
						s += this.tab[i][j];
				}
			}
			if( i % 10 == 0 )	s += "\n";
		}
		
		return s;
	}
	
	public static void main(String[] args) {
		
		Robot robot1=new Robot();
		
		System.out.println( robot1.getDistance(90) );
		
		while( true ){
			robot1.deplacement();
			System.out.println( robot1 );
		}
	}

}