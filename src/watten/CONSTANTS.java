package watten;

public class CONSTANTS {
	
	public final static boolean VERBOSE = true;
	
	public final static int AT = 0;
	public final static int EN = 1;
	
	public final static int LANG = AT;
	
	public final static String[][] SUIT_NAME = {{"Eichel", "Herz", "Laub", "Schell"},
												{"Acorn", "Heart", "Leave", "Bell" }};
	
	
	public final static String[][] RANK_NAME = {{"Sechser","Siebner","Achter","Neuner","Zehner","Unter","Ober","König","As"},
												{"Six","Seven","Eight","Nine","Ten","Jack","Knight","King","Ace"}};
	
	public final static String[][] POSSIBILITY_NAME = {{"unmöglich","möglich","sicher"},
			 										   {"impossible","possible","sure"}};
	
	public final static int CARDS_IN_STICH = 4;
	public final static int NUMBER_OF_STICHE = 5;
	
	public final static String QUOTE_STRING = ",";
	
	public final static Card VELI = new Card(Suit.BELL,Rank.SIX);
	
	// Error Messages
	public final static String ERROR_Could_Not_Create_Card1 ="Karte \"";
	public final static String ERROR_Could_Not_Create_Card2 ="\" konnte nicht erzeugt werden!\n";
	public final static String ERROR_Could_Not_Create_Stich ="Stich konnte nicht erzeugt werden!\n";
	public final static String ERROR_Wrong_WinnerNumber = "Die Nummer des Siegers muss 1,2,3 oder 4 sein!\n";
	public final static String ERROR_Game_Status_Unchangeable = "\n Spiel Status +/- Guater\nkann nur zu Beginn veraendert werden!\n";
	
	// Messages
	public final static String messageHeader = "Stich Nr: ";
	public final static String messageFooter = "\n";
	//public final static String messageIMPOSSIBLE = "unmoeglich!\n";
	//public final static String messagePOSSIBLE = "moeglich!\n";
	//public final static String messageSURE = "sicher!\n";
	
	public final static String messageFarbStich =  "Farbstich:\t\t";
	public final static String messageTrumpfStich = "Trumpfstich:\t\t";
	public final static String messageLinkerStich = "Stich mit Linkem:\t";
	public final static String messageRechterStich = "Stich mit Rechtem:\t";
	public final static String messageGuaterStich = "Stich mit Guatem:\t";
	
	public final static String messageColor ="Farbe";
	public final static String messageNumber ="Schlag";
	
	// Labels
	public final static String labelProgramHeader = ">> Schlag & Trumpf <<";
	public final static String labelGameWithGuatem = "Spiel mit Guatem:";
	public final static String labelNewGame ="Neues Spiel !";
	
	public final static String labelColor = "Farbe =>";
	public final static String labelNumber = "Schlag =>";
	
	public final static String[] labelColors = {"Eichel","Herz","Laub","Schell"};
	public final static String[] labelNumbers = {"6","7","8","9","10","U","O","K","A"};
}
