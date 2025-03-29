package ph.com.asacoco_tickets;

/*
 * IMPORTS, A
 */
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;

import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class Main extends JFrame {
	/**========================================
	 * PRIVATE VARIABLE DECLARATION/INITIALIZATION FOR COMPONENTS USED
	 * ========================================
	 */
	private static final long serialVersionUID = 1L;
	
	//JPANELS USED
	private JPanel contentPane;					//main frame
	private JPanel eventPanel;					//contains all events and other details
	private JPanel ticketBookingPanel;			//contains the necessary labels for booking
	private JPanel jPanelTransactionHistory;	//essentially a log which contains all booking transactions
	private JPanel navbarPanel;					//contains buttons that navigates to categories of events such as Concerts, etc.
	private JPanel bannerPanel;					//purely for designing the interface with an appropriate title name
	private JPanel transactionHistoryBG;		//an added panel that gives emphasis to the text "Transaction History"
	
	/*========================================
	 * VARIABLE USED
	 * ========================================
	 */
	private final int NUM_EVENTS = 6;			//number of events in the system
	private int category;						//determines which among of the three categories are to be displayed
	private int categoryOfEventChosen;			//contains the section of the event chosen by the user
	private int eventChosen=-1;					//contains the event chosen by the user, initialized as -1 to throw an appropriate error message
	private int numOfTransactions=0;			//essentially a counter variable, holding the number of transactions made through the system
	

	//JTEXTFIELD USED
	private JTextField txtFTicketsNumInput;		//a text field dedicated for user input of the number of tickets to book
	
	//JLABELS USED FOR EVENT PANEL
	
	/*
	 * IMPORTANT !!
	 * In order to greatly diminish the lines of code used for this program,
	 * a 2D array containing all JLabels used for event details is created
	 * 
	 * All event details are indexed as follows:
	 * eventDetailLabels[0][n] = Banner Image of the Event
	 * eventDetailLabels[1][n] = Text in PNG form containing the title of the Event
	 * eventDetailLabels[2][n] = Ticket price of the Event
	 * eventDetailLabels[3][n] = Date of the Event
	 * eventDetailLabels[4][n] = Number of Available Tickets of the event
	 */
	private JLabel [][] jLabelsEventPanelDetails = new JLabel[5][NUM_EVENTS];
	
	//JLABELS USED FOR BOOKING PANEL
	private JLabel userChosenEventImageLabel;				//displays image of the user's chosen event, initialized with no image placeholder
	private JLabel userChosenEventTitle;					//displays title of the user's chosen event, initialized as no image
	private JLabel jLabelTicketsPurchase;					//displays ticket prices, initialized with a bunch of dashes
	private JLabel jLabelEventDescription;					//displays event description to give user's more context about the event, initialized with a "no input" message
	private JLabel ticketPriceOfUserChoiceLabel;			//shows ticket price of the user's chosen event, initialized with no text
	private JLabel dateOfUserChoiceEventLabel; 				//shows date of event
	private JLabel ticketsAvailableOfUserChosenEventLabel; 	//shows available tickets for the chosen event
	
	//OTHER JLABELS USED
	private JLabel jLabelTransactionHistory;	//transaction log label which names the section it is in
	private JLabel jLabelSystemName;			//banner panel label which contains the 
	//JBUTTON USED
	private JButton btnConfirmBooking;			//button exclusive in the booking panel. It shows user prompts based on user input
	/*
	 * IMPORTANT !!
	 * In order to greatly diminish the lines of code used for this program,
	 * a 2D array containing all JLabels used for event details is created
	 * 
	 * All event details are indexed as follows:
	 * btnCategory[0] = Concerts
	 * btnCategory[1] = Sports
	 * btnCategory[2] = Movies
	 */
	private JButton [] btnCategory = new JButton[3]; //buttons used for navigating through categories
	
	//JTEXTAREA USED
	private JTextArea txtAEventDescription;				//contains event descriptions
	private JTextArea transactionHistoryDisplayArea;	//contains all transaction logs

	//JCHECKBOXES USED
	private JCheckBox [] eventCheckBoxes  = new JCheckBox[NUM_EVENTS];//check boxes for event selection

	//JSCROLLPANE USED
	private JScrollPane transactionHistoryScrollPane;	//shows scroll bars as needed, it shows the latest transactions due to a line of possibly? advanced code in the update structure
	/*=========================================
	 * ARRAYS USED
	 * ========================================
	 */
	private final String[] EVENT_CATEGORIES = {"CONCERTS", "SPORTS", "MOVIES"};	//stores category names of events in preparation of looping structure
	private final int[] Y_ABSOLUTE_POS_DETAIL ={//contains starting y values to determine location of java components in event panel, same order as the JLabel array
			0, 165, 186, 200, 214
	};
	/*
	 * IMPORTANT !!
	 * In order to greatly diminish the lines of code used for this program,
	 * a 3D array containing all event details is created
	 * 
	 * All event details are indexed as follows:
	 * EVENT_DETAILS[0][n][n] = Banner Image Directories
	 * EVENT_DETAILS[1][n][n] = Title Image Directories
	 * EVENT_DETAILS[2][n][n] = Ticket Price of Event per book
	 * EVENT_DETAILS[3][n][n] = Event Dates
	 * EVENT_DETAILS[4][n][n] = Number of Tickets Available in Event
	 * EVENT_DETAILS[5][n][n] = Event Names
	 * EVENT_DETAILS[6][n][n] = Event Descriptions
	 * 
	 * [n][3][n] accommodates the three categories of tickets to be booked
	 * [n][n][NUM_EVENTS] accommodates the number of events per category
	 */
	private final String[][][] EVENT_DETAILS = 
		{	
			{{//BANNER IMAGE DIRECTORIES ======================-----||[0][n][n]
			//CONCERTS EVENT IMAGES - index[0][0][n]
			"/assets/images/ConcertBanner_BTSWorldTour.png",
			"/assets/images/ConcertBanner_HatsuneMiku.png",
			"/assets/images/ConcertBanner_SB19.png",
			"/assets/images/ConcertBanner_OPM.png",
			"/assets/images/ConcertBanner_Genshin.png",
			"/assets/images/ConcertBanner_Yorushika.png"
			},{
			//SPORTS EVENT IMAGES - index[0][1][n]
			"/assets/images/SportsBanner_Badminton.png",
			"/assets/images/SportsBanner_Olympics.png",
			"/assets/images/SportsBanner_NBA.png",
			"/assets/images/SportsBanner_Pac.png",
			"/assets/images/SportsBanner_TT.png",
			"/assets/images/SportsBanner_Volleyball.png",
			},{
			//MOVIE EVENT IMAGES - index[0][2][n]
			"/assets/images/MovieBanner_LookBack.png",
			"/assets/images/MovieBanner_TheNotebook.png",
			"/assets/images/MovieBanner_HelloLoveGoodbye.png",
			"/assets/images/MovieBanner_Spiderman.png",
			"/assets/images/MovieBanner_Howl.png",
			"/assets/images/MovieBanner_Mickey17.png"
			}},
			{{//TITLE IMAGE DIRECTORIES ========================-----||[1][n][n]
			//CONCERTS TITLE IMAGES - index[1][0][n]
			"/assets/images/txtImage_BTSWorldTourPhilippines.png",
			"/assets/images/txtImage_HatsuneMiku.png",
			"/assets/images/txtImage_SB19.png",
			"/assets/images/txtImage_OPM.png",
			"/assets/images/txtImage_Genshin.png",
			"/assets/images/txtImage_Yorushika.png"
			},{
			//SPORTS TITLE IMAGES - index[1][1][n]
			"/assets/images/txtImage_Badminton.png",
			"/assets/images/txtImage_Olympics.png",
			"/assets/images/txtImage_NBA.png",
			"/assets/images/txtImage_Pac.png",
			"/assets/images/txtImage_TT.png",
			"/assets/images/txtImage_Volleyball.png"
			},{
			//MOVIE TITLE IMAGES - index[1][2][n]
			"/assets/images/txtImage_LookBack.png",
			"/assets/images/txtImage_TheNotebook.png",
			"/assets/images/txtImage_HelloLoveGoodbye.png",
			"/assets/images/txtImage_Spiderman.png",
			"/assets/images/txtImage_Howl.png",
			"/assets/images/txtImage_Mickey17.png"
			}},
			{//TICKET PRICES OF EVENTS ========================-----||[2][n][n]
			{"6500","5000","6000","9000","10000","5000"},		//concerts - index[2][0][n]
			{"750","1000","1250","4000","6500","6900"},			//sports   - index[2][1][n]
			{"999","1290","1000","390","540","340"}				//events   - index[2][2][n]
			},
			{{//DATES OF EVENTS =================================-----||[3][n][n]
			//CONCERTS DATES - index[3][0][n]
			"March 19, 2024",
			"September 11, 2001",
			"January 1, 2059",
			"March 20, 2022",
			"May 4, 2021",
			"February 4, 2024"
			},{
			//SPORTS DATES - index[3][1][n]
			"March 24, 2069",
			"April 1, 2001",
			"August 13, 2004",
			"March 19, 2024",
			"September 11, 2001",
			"January 1, 2059"
			},{
			//MOVIE DATES - index[3][2][n]
			"August 8, 2025",
			"April 2, 2025",
			"January, 3023",
			"August 13, 2004",
			"March 19, 2024",
			"September 11, 2001"
			}},
			{//AVAILABLE TICKETS OF EVENTS ====================-----||[4][n][n]
			{"10000","10000","10000","10000","10000","10000"},	//concerts - index[4][0][n]
			{"10000","10000","10000","10000","10000","10000"},	//sports   - index[4][1][n]
			{"10000","10000","10000","10000","10000","10000"}	//events   - index[4][2][n]
			},
			//EVENT NAMES =====================================-----||[5][n][n]
			//CONCERT EVENT NAMES - index[5][0][n]
			{{
			"BTS Love Yourself World Tour",
			"Hatsune Miku's Blooming Miracle Concert",
			"Back in the Zone SB19 Concert",
			"Pinoy Playlist Music Festival",
			"Genshin Live Orchestra",
			"Yorushika 2021 Live"
			},{
			//SPORTS EVENT NAMES - index[5][1][n]
			"Smithson Badminton Tournament",
			"Olympics Game Paris",
			"NBA Champions Tournament",
			"Pacquiao vs Marquez Fight",
			"Ma Long Table Tennis Tourney",
			"All Women's Volleyball PH"
			},{
			//MOVIE EVENT NAMES - index[5][2][n]
			"Look Back by Tatsuki Fujimoto",
			"The Notebook",
			"Hello, Love, Goodbye",
			"Spider-man: Across The Spiderverse",
			"Hayao Miyazaki's Howl's Moving Castle",
			"Mickey 17"
			}},
			//EVENT DESCRIPTIONS =========================== ===-----||[6][n][n]
			//CONCERT EVENT DESCRIPTIONS - index[6][0][n]
			{{
			"BTS's Love Yourself world tour, including its Speak Yourself extension, broke numerous records and captivated millions of fans globally.",
			"Hatsune Miku's Blooming Miracle concert captivates audiences with its spectacle of light, sound, and holographic technology",
			"SB19's concert is a high-energy spectacle of music, dance, and captivating stage presence, leaving a lasting impression on their devoted A'TIN fanbase.",
			"The Pinoy Music Festival was a vibrant celebration of Filipino talent, showcasing diverse musical styles and energizing the crowd with its infectious energy.",
			"The Genshin Orchestra concert was a magical journey through the game's iconic soundtrack, transporting the audience to Teyvat with its stunning visuals and masterful musicianship.",
			"Yorushika's concert was a breathtaking spectacle of music and emotion, leaving the audience spellbound by their captivating performance and the enchanting atmosphere."
			},{
			//SPORTS EVENT DESCRIPTIONS - index[6][1][n]
			"The badminton tournament showcases intense rallies, strategic gameplay, and the athleticism of top competitors vying for victory.",
			"The Olympic Games unite athletes from around the world in a celebration of sportsmanship, competition, and human potential.",
			"The NBA season is a thrilling spectacle of athleticism, skill, and competition, featuring the world's best basketball players battling for the title.",
			"The Pacquiao vs. Marquez fight was a legendary boxing match, filled with dramatic twists and turns that kept the audience on the edge of their seats until the final bell.",
			"The Chinese table tennis match was a display of exceptional skill and precision, with lightning-fast reflexes and strategic gameplay captivating the audience.",
			"The volleyball game was a thrilling back-and-forth match, showcasing incredible athleticism and strategic plays that kept the spectators engaged until the very end."
			},{
			//MOVIES EVENT DESCRIPTIONS - index[6][2][n]
			"Look Back is a captivating film exploring themes of memory, identity, and the complexities of human relationships.",
			"The Notebook, a 2004 romantic drama film adapted from Nicholas Sparks' novel, portrays a decades-long love story overcoming numerous obstacles.",
			"Hello, Love, Goodbye is a poignant and heartwarming Filipino romantic-comedy-drama that explores themes of love, sacrifice, and the immigrant experience.",
			"The Spider-Man movie was an action-packed adventure, filled with thrilling stunts, witty humor, and a heartwarming story that resonated with audiences of all ages.",
			"Hayao Miyazaki's Howl's Moving Castle was a whimsical and enchanting animated film, filled with breathtaking visuals, memorable characters, and a captivating story about love, courage, and self-discovery.",
			"The movie Mickey 17 was a captivating sci-fi thriller, exploring themes of identity, sacrifice, and the complexities of human cloning in a harsh, alien environment."
			}}
		};
	/**========================================
	 * Launches the event ticket booking system.
	 * ========================================
	 */
	public static void main(String[] args) {	//main method, entry point
		EventQueue.invokeLater(new Runnable() {	//makes program run after values in constructor are set
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);//shows the JFrame together with all the components initialized in constructor
				} catch (Exception e) {
					e.printStackTrace();//diagnoses exceptions in runtime
				}
			}
		});
	}
	/**========================================
	 * Constructor - Initializes values and creates the JFrame Layout
	 * ========================================
	 */
	public Main() {
		/*==================
		 * JFRAME PROPERTIES
		 * ==================
		 */
		setTitle("ASACOCO - Event Ticket Booking System");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/assets/images/jframe_custom_icon.png")));//sets an icon image for the JFrame
		setResizable(false);								//based on set bounds, the frame will remain as is in size
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		//program terminates upon closing
		setBounds(100, 100, 1201, 815);						//sets the bounds of the JFrame
		contentPane = new JPanel();							//creates a JPanel as the main content pane of the JFrame
		contentPane.setBackground(new Color(255, 255, 255));//sets the background to white in RGB values
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));	//sets empty borders, no drawings
		setContentPane(contentPane);						//the created JPanel is then set as the content pane of the JFrame
		contentPane.setLayout(null);						//sets the content pane's layout to absolute
		
		/*==================
		 * EVENT PANEL w/ OTHER COMPONENTS PROPERTIES
		 * ==================
		 */
		//EVENT PANEL INITIALIZATION
		eventPanel = new JPanel();													//creates a JPanel used for events
		eventPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));	//draws an etched border around the event panel
		eventPanel.setBackground(new Color(255, 255, 255)); 						//sets background to white in RGB values
		eventPanel.setBounds(476, 64, 699, 701);									//sets the bounds of the eventPanel
		contentPane.add(eventPanel);												//adds the eventPanel to the contentPane
		eventPanel.setLayout(null);													//sets layout to absolute
		
		/*J LABEL EVENT DETAILS INITIALIZATION
		 */
		for(int counter=0, xPos=10, yPosIncrement=0;counter<jLabelsEventPanelDetails[0].length;counter++,yPosIncrement+=230) {
			if(counter==(jLabelsEventPanelDetails[0].length/2)) {
				xPos+=340;
				yPosIncrement=0;
			}
			for(int innerCounter=0;innerCounter<jLabelsEventPanelDetails.length;innerCounter++) {
				jLabelsEventPanelDetails[innerCounter][counter] = new JLabel("");
			}
			
			jLabelsEventPanelDetails[0][counter].setBounds(xPos,Y_ABSOLUTE_POS_DETAIL[0]+yPosIncrement,300,164);
			jLabelsEventPanelDetails[1][counter].setBounds(xPos,Y_ABSOLUTE_POS_DETAIL[1]+yPosIncrement,279,23);
			for(int innerCounter=2;innerCounter<jLabelsEventPanelDetails.length;innerCounter++) {
				jLabelsEventPanelDetails[innerCounter][counter].setBounds(xPos,Y_ABSOLUTE_POS_DETAIL[innerCounter]+yPosIncrement,204,14);
			}
			
			jLabelsEventPanelDetails[2][counter].setText("Price of Booking: ");
			jLabelsEventPanelDetails[4][counter].setText("Tickets Available: ");
			
			for(int innerCounter=0;innerCounter<jLabelsEventPanelDetails.length;innerCounter++) {
				
				if(innerCounter<2) {
					jLabelsEventPanelDetails[innerCounter][counter].setIcon(new ImageIcon(Main.class.getResource(EVENT_DETAILS[innerCounter][category][counter])));
				}else {
					jLabelsEventPanelDetails[innerCounter][counter].setFont(new Font("Arial", Font.PLAIN, 11));
					jLabelsEventPanelDetails[innerCounter][counter].setText(jLabelsEventPanelDetails[innerCounter][counter].getText() + EVENT_DETAILS[innerCounter][category][counter]);
				}
				eventPanel.add(jLabelsEventPanelDetails[innerCounter][counter]);
			}
		}
		/*CHECK BOXES OF EVENTS INITIALIZATION
		 */
		for(int counter=0,x=213,y=191;counter<eventCheckBoxes.length;counter++,y+=230) {
			eventCheckBoxes[counter] = new JCheckBox("BOOK");
			eventCheckBoxes[counter].setForeground(new Color(255, 255, 255));
			eventCheckBoxes[counter].setBackground(new Color(32, 32, 32));	//sets color of the background to yellow
			eventCheckBoxes[counter].setFont(new Font("Arial", Font.BOLD, 11)); //sets font style of check box text
			if(counter==(eventCheckBoxes.length/2)) {
				x+=340;
				y=191;
			}
			eventCheckBoxes[counter].setBounds(x,y,97,23);
			
			final int innerCounter = counter;//workaround for cannot use local variable enclosed ...
			
			eventCheckBoxes[counter].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for(int deselect=0;deselect<eventCheckBoxes.length;deselect++) {
						if(deselect!=innerCounter) {
							eventCheckBoxes[deselect].setSelected(false);
						}else {
							eventCheckBoxes[innerCounter].setSelected(true);
						}
					}	
					categoryOfEventChosen = category;
					eventChosen=innerCounter;
					updateUserChosenEvent();
					
				}
			});
			eventPanel.add(eventCheckBoxes[counter]);
		}
		/*==================
		 * TICKET BOOKING PANEL w/ OTHER COMPONENTS PROPERTIES
		 * ==================
		 */	
		/*
		 * TICKET BOOKING PANEL INITIALIZATION
		 */
		ticketBookingPanel = new JPanel();						//creates a JPanel used for ticket booking
		ticketBookingPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		ticketBookingPanel.setBackground(Color.WHITE);			//sets background to white
		ticketBookingPanel.setBounds(10, 64, 456, 300);			//sets bounds of ticket booking panel
		ticketBookingPanel.setLayout(null);						//sets layout to absolute
		contentPane.add(ticketBookingPanel);					//adds the ticket booking panel to content pane
		/*
		 * IMAGE LABEL FOR USER CHOSEN EVENT
		 */
		userChosenEventImageLabel = new JLabel("");					//creates new JLabel as placeholder for chosen event by user 		
		userChosenEventImageLabel.setBackground(new Color(192, 192, 192));	//sets background color to yellow using RGB values
		userChosenEventImageLabel.setIcon(new ImageIcon(Main.class.getResource("/assets/images/placeholderNoEvent.png")));//sets image of the JLabel, initialized with no image placeholder
		userChosenEventImageLabel.setBounds(10, 6, 300, 164);		//sets bounds of user chosen event image label
		ticketBookingPanel.add(userChosenEventImageLabel);			//adds the label to the ticket booking panel
		
		/*
		 * TEXT LABEL FOR USER CHOSEN EVENT
		 */
		userChosenEventTitle = new JLabel("");					//creates a JLabel for user chosen event text label
		userChosenEventTitle.setIcon(null);						//sets icon to none
		userChosenEventTitle.setBounds(10, 182, 300, 23);		//sets bounds of the label
		ticketBookingPanel.add(userChosenEventTitle);			//adds the label to the ticket booking panel
		
		/*
		 * NUM OF TICKETS TEXT FIELD BOOKING PANEL INITIALIZATION
		 */
		txtFTicketsNumInput = new JTextField();						//creates JTextField for getting integer inputs from user
		txtFTicketsNumInput.setBounds(113, 236, 101, 20);			//sets bounds of the text field
		txtFTicketsNumInput.setColumns(10);							//sets the size of the text field to 10 columns
		ticketBookingPanel.add(txtFTicketsNumInput);				//adds the text field to the ticket booking panel
		
		/*
		 * BUTTON TO CONFIRM BOOKING
		 */
		btnConfirmBooking = new JButton("CONFIRM BOOKING");			//creates JButton for confirming booking
		btnConfirmBooking.setBackground(new Color(32, 32, 32));
		btnConfirmBooking.setForeground(new Color(255, 255, 255));
		btnConfirmBooking.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnConfirmBooking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int numTickets = validateUserInput(txtFTicketsNumInput.getText());
				if(numTickets!=0) {
					if(numTickets>1) {
						JOptionPane.showMessageDialog(null,"Thank you for buying a total of "+ numTickets + " tickets! \nTOTAL = " + calculateBooking(numTickets),"Booked Successfully",JOptionPane.INFORMATION_MESSAGE);
					}else {
						JOptionPane.showMessageDialog(null,"Thank you for buying a ticket! \nTOTAL = " + calculateBooking(numTickets),"Booked Successfully",JOptionPane.INFORMATION_MESSAGE);
					}
					txtFTicketsNumInput.setText("");//clears out text field in preparation of another transaction
					updateAvailableTickets(numTickets);
					updateTransactionLog(numTickets);
				}
			}
		});
		btnConfirmBooking.setBounds(256, 233, 186, 50);				//sets the bounds of the button
		ticketBookingPanel.add(btnConfirmBooking);					//adds the button to the ticket booking panel
		
		/*
		 * TEXT LABEL FOR TICKETS PURCHASE
		 */
		jLabelTicketsPurchase = new JLabel("Tickets to Purchase: ");		//creates a JLabel to label the text field for desired inputs
		jLabelTicketsPurchase.setFont(new Font("Arial", Font.PLAIN, 11));	//sets the font style of the label
		jLabelTicketsPurchase.setBounds(10, 239, 125, 14);					//sets bounds of the label
		ticketBookingPanel.add(jLabelTicketsPurchase);						//adds the text label to the ticket booking panel
		
		/*
		 * TEXT AREA FOR EVENT DESCRIPTION
		 */
		txtAEventDescription = new JTextArea();										//creates JTextArea for event details
		txtAEventDescription.setFont(new Font("Tahoma", Font.BOLD, 11));			//sets the font style of the text area
		txtAEventDescription.setLineWrap(true);										//proceeds with next line if line area is not sufficient to accommodate text
		txtAEventDescription.setWrapStyleWord(true);								//for readability, words are wrapped to prevent unreadable broken text
		txtAEventDescription.setText("Please choose an event to book first...");	//sets the text for event details, initialized as shown
		txtAEventDescription.setBackground(new Color(255, 255, 255));				//sets background to a grey color
		txtAEventDescription.setEditable(false);									//prevents user to manipulate the text inside the text area
		txtAEventDescription.setBounds(320, 23, 126, 202);							//sets bounds of the text area
		txtAEventDescription.setBorder(BorderFactory.createEtchedBorder());			//draws etched border around the text area
		txtAEventDescription.setMargin(getInsets());								//sets margins to prevent text overlap
		ticketBookingPanel.add(txtAEventDescription);								//adds the text area to the ticket booking panel
		
		/*
		 * TEXT LABEL FOR EVENT DETAILS 
		 */
		jLabelEventDescription = new JLabel("Event Description:");				//creates a JLabel to signify a dedicated area for event description
		jLabelEventDescription.setFont(new Font("Arial", Font.PLAIN, 11));		//sets font style
		jLabelEventDescription.setBounds(319, 6, 69, 14);						//sets bounds of the label
		ticketBookingPanel.add(jLabelEventDescription);							//adds the label to the ticket booking panel
		
		ticketPriceOfUserChoiceLabel = new JLabel("Price of Booking: ---");		//creates a JLabel for ticket price, initialized with dashes
		ticketPriceOfUserChoiceLabel.setFont(new Font("Arial", Font.PLAIN, 11));//sets the font style
		ticketPriceOfUserChoiceLabel.setBounds(10, 168, 204, 14);				//sets the bounds
		ticketBookingPanel.add(ticketPriceOfUserChoiceLabel);					//adds the JLabel to the ticket booking panel
		
		dateOfUserChoiceEventLabel = new JLabel("---- -- ----");				//creates a JLabel for event date, initialized with dashes
		dateOfUserChoiceEventLabel.setFont(new Font("Arial", Font.PLAIN, 11));	//sets the font style
		dateOfUserChoiceEventLabel.setBounds(213, 168, 97, 14);					//sets bounds
		ticketBookingPanel.add(dateOfUserChoiceEventLabel);						//adds the JLabel to the ticket booking panel
		
		ticketsAvailableOfUserChosenEventLabel = new JLabel("Tickets Available: ---");
		ticketsAvailableOfUserChosenEventLabel.setFont(new Font("Arial", Font.PLAIN, 11));
		ticketsAvailableOfUserChosenEventLabel.setBounds(112, 221, 187, 14);
		ticketBookingPanel.add(ticketsAvailableOfUserChosenEventLabel);
		
		/*
		 * ==================
		 * TRANSACTION HISTORY PANEL w/ OTHER COMPONENTS PROPERTIES
		 * ==================
		 */	
		/*
		 * TRANSACTION HISTORY PANEL PROPERTIES
		 */
		jPanelTransactionHistory = new JPanel();
		jPanelTransactionHistory.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		jPanelTransactionHistory.setLayout(null);
		jPanelTransactionHistory.setBackground(Color.WHITE);
		jPanelTransactionHistory.setBounds(10, 375, 456, 390);
		contentPane.add(jPanelTransactionHistory);
		
		transactionHistoryScrollPane = new JScrollPane();
		transactionHistoryScrollPane.setBounds(10, 37, 436, 327);
		jPanelTransactionHistory.add(transactionHistoryScrollPane);
		/*
		 * EVENT DISPLAY DETAILS DISPLAY AREA PROPERTIES
		 */
		transactionHistoryDisplayArea = new JTextArea(); 								//creates a JTextArea for event details
		transactionHistoryDisplayArea.setForeground(new Color(0, 0, 0));			//sets text color to white
		transactionHistoryDisplayArea.setFont(new Font("Monospaced", Font.PLAIN, 11));	//sets font style of the text area
		//initialized text, shows that system is up and running
		transactionHistoryDisplayArea.setText("   ==================================\r\nTRANSACTION HISTORY - EVENT TICKET BOOKING SYSTEM MM/DD/YY\r\n   ==================================");											
		transactionHistoryDisplayArea.setWrapStyleWord(true);							//for readability, words are wrapped to prevent unreadable broken text
		transactionHistoryDisplayArea.setLineWrap(true);								//proceeds with next line if line area is not sufficient to accommodate text
		transactionHistoryDisplayArea.setEditable(false);								//prevents user to manipulate the text inside the text area
		transactionHistoryDisplayArea.setBounds(10, 43, 416, 321);						//sets bounds of the text area
		transactionHistoryDisplayArea.setBackground(new Color(255, 255, 255));			//sets color of the background to black using RGB values
		transactionHistoryScrollPane.setViewportView(transactionHistoryDisplayArea);					//adds text area to transaction history panel
		
		transactionHistoryBG = new JPanel();
		transactionHistoryBG.setLayout(null);
		transactionHistoryBG.setBackground(new Color(23, 23, 23));
		transactionHistoryBG.setBounds(0, 0, 456, 33);
		jPanelTransactionHistory.add(transactionHistoryBG);
		
		jLabelTransactionHistory = new JLabel("");
		jLabelTransactionHistory.setBounds(5, -21, 406, 76);
		transactionHistoryBG.add(jLabelTransactionHistory);
		jLabelTransactionHistory.setBackground(new Color(29, 29, 29));
		jLabelTransactionHistory.setIcon(new ImageIcon(Main.class.getResource("/assets/images/txtImage_TRANSACTION HISTORY.png")));
		
		/*==================
		 * BANNER PANEL w/ OTHER COMPONENTS PROPERTIES
		 * ==================
		 */	
		//BANNER PANEL PROPERTIES
		bannerPanel = new JPanel();		//creates a JPanel for the banner
		bannerPanel.setBounds(-14, 0, 491, 62);	//sets bounds of the panel
		bannerPanel.setLayout(null);			//sets layout to absolute
		bannerPanel.setBackground(new Color(23, 23, 23)); //sets background to color white
		contentPane.add(bannerPanel);
		
		//BANNER PANEL LOGO PROPERTIES
		jLabelSystemName = new JLabel("");		//creates JLabel for system name/Logo
		jLabelSystemName.setIcon(new ImageIcon(Main.class.getResource("/assets/images/eventTicketSystemLogo.png")));	//sets image according to file directory
		jLabelSystemName.setBounds(92, 5, 241, 76);		//sets bounds of image
		bannerPanel.add(jLabelSystemName);				//adds name/Logo to banner panel
		
		JLabel asacoco_logo = new JLabel("");
		asacoco_logo.setIcon(new ImageIcon(Main.class.getResource("/assets/images/asacoco_logo.png")));
		asacoco_logo.setBounds(17, 0, 72, 68);
		bannerPanel.add(asacoco_logo);
		
		/*==================
		 * NAVIGATION BAR PANEL w/ OTHER COMPONENTS PROPERTIES
		 * ==================
		 */	
		//NAVIGATION BAR PROPERTIES
		navbarPanel = new JPanel();								//creates a jPanel for section navigation
		navbarPanel.setBackground(new Color(23, 23, 23));	//sets color of the background to white using RGB values
		navbarPanel.setBounds(475, 0, 700, 62);				//sets bounds of the panel
		navbarPanel.setLayout(null);							//sets layout to absolute
		contentPane.add(navbarPanel);							//adds the navigation panel to content panel
		
		/*
		 * NAVIGATION BAR BUTTONS PROPERTIES
		 */
		for(int counter=0,x=310,y=11;counter<btnCategory.length;counter++,x+=130) {
			btnCategory[counter] = new JButton(EVENT_CATEGORIES[counter]);
			btnCategory[counter].setFont(new Font("Arial", Font.BOLD, 11));
			btnCategory[counter].setForeground(new Color(255, 255, 255));
			btnCategory[counter].setBackground(new Color(128, 128, 128));
			btnCategory[counter].setBounds(x, y, 120, 40);				//sets bounds of button
			final int innerCounter = counter;//workaround for local variable error ...
			btnCategory[counter].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(category!=innerCounter) {
						category=innerCounter;
						deselectCheckBoxes();
						updateEventSelectionPanel();
					}
				}
			});
			navbarPanel.add(btnCategory[counter]);						//adds button to navigation bar panel
		}
	}
	private void deselectCheckBoxes() {
		for(int counter=0;counter<eventCheckBoxes.length;counter++) {//deselects all event check boxes
			eventCheckBoxes[counter].setSelected(false);
		}
	}
	private void updateEventSelectionPanel() {
		for(int counter=0;counter<jLabelsEventPanelDetails[0].length;counter++) {//loops through all events based on NUM_EVENTS
			jLabelsEventPanelDetails[0][counter].setIcon(new ImageIcon(Main.class.getResource(EVENT_DETAILS[0][category][counter])));//sets images of selected category of events
			jLabelsEventPanelDetails[1][counter].setIcon(new ImageIcon(Main.class.getResource(EVENT_DETAILS[1][category][counter])));//sets title of selected category of events
			jLabelsEventPanelDetails[2][counter].setText("Price of Booking: " + EVENT_DETAILS[2][category][counter]);//shows prices of tickets of selected category of events
			jLabelsEventPanelDetails[3][counter].setText(EVENT_DETAILS[3][category][counter]);//shows dates of selected category of events
			jLabelsEventPanelDetails[4][counter].setText("Tickets Available: "+ EVENT_DETAILS[4][category][counter]);//shows tickets available for category selected
		}
	}
	private void updateUserChosenEvent() {	
		userChosenEventImageLabel.setIcon(new ImageIcon(Main.class.getResource(EVENT_DETAILS[0][categoryOfEventChosen][eventChosen])));//shows banner image of the event
		userChosenEventTitle.setIcon(new ImageIcon(Main.class.getResource(EVENT_DETAILS[1][categoryOfEventChosen][eventChosen])));//shows title of the event
		ticketPriceOfUserChoiceLabel.setText("Price of Booking: " + EVENT_DETAILS[2][category][eventChosen]);//shows the ticket price
		dateOfUserChoiceEventLabel.setText(EVENT_DETAILS[3][categoryOfEventChosen][eventChosen]);//shows date of event chosen
		ticketsAvailableOfUserChosenEventLabel.setText("Tickets Available: " + EVENT_DETAILS[4][categoryOfEventChosen][eventChosen]);//shows tickets available of event chosen
		txtAEventDescription.setText(EVENT_DETAILS[6][categoryOfEventChosen][eventChosen]);//shows event description of event chosen
	}
	/* ==================
	 * INPUT VALIDATION
	 * ==================
	 */	
	private int validateUserInput(String str) {
		if(eventChosen==-1) {//checks if user has chosen
			JOptionPane.showMessageDialog(null,"Please choose an event","ERROR",JOptionPane.ERROR_MESSAGE);
			return 0;//return 0 breaks the current method
		}
		int availableTickets =Integer.parseInt(EVENT_DETAILS[4][categoryOfEventChosen][eventChosen]);//converts the string value of available tickets to integer for later arithmetic operations
		
		if(!(str!=""&&str!=null)) {//checks if text field is empty
			JOptionPane.showMessageDialog(null,"Please input a number on the box provided","ERROR",JOptionPane.ERROR_MESSAGE);
			return 0;
		}
		if(availableTickets==0) {//checks if tickets available is 0
			JOptionPane.showMessageDialog(null,"Tickets are already sold out. :(","ERROR",JOptionPane.ERROR_MESSAGE);
			return 0;
		}
		if(!isNumeric(str)) {//checks if user input is not numeric
			JOptionPane.showMessageDialog(null,"Please input a valid number \n E.G. 1, 2, 3","ERROR",JOptionPane.ERROR_MESSAGE);
			return 0;
		}
		int numTickets = Integer.parseInt(str);//converts the string to integer and will be used as a comparator
		
		if(numTickets<1) {//checks if user input is negative or 0
			JOptionPane.showMessageDialog(null,"Please input a number from 1 to " + availableTickets,"ERROR",JOptionPane.ERROR_MESSAGE);
			return 0;
		}
		if(availableTickets<numTickets) {//checks if available tickets is less than the tickets the user wants to buy
			JOptionPane.showMessageDialog(null,"Unfortunately, there are only "+availableTickets+ " tickets available. Please try again","ERROR",JOptionPane.ERROR_MESSAGE);
			return 0;
		}
		int input = JOptionPane.showConfirmDialog(null, "Do you want to proceed with booking the event?", "Select an Option...",JOptionPane.YES_NO_CANCEL_OPTION);//asks user if they want to proceed with booking
		if(input!=0) {//checks if no or cancel is pressed by the user
			return 0;
		}
		return numTickets;//when all if conditions are bypassed, returns the string value
	}
	private boolean isNumeric(String str) {
		try {  
		  Integer.parseInt(str);  //converts the user input to integer
		  return true;
		}catch(NumberFormatException e){//catches a non-integer input
			return false; 
		}   
	}
	/*=====================
	 * CALCULATING TOTAL PRICE AND PERFORMING NECESSARY UPDATES IN ARRAY DATA STRUCTURE
	 * ====================
	 */	
	private double calculateBooking(int numTickets) {
		return Integer.parseInt(EVENT_DETAILS[2][category][eventChosen])*numTickets;//multiplies price of ticket to number of tickets bought
	}
	private void updateAvailableTickets(int boughtTickets) {
		//UPDATING DATA STRUCTURE
		EVENT_DETAILS[4][categoryOfEventChosen][eventChosen]= String.valueOf(Integer.parseInt(EVENT_DETAILS[4][categoryOfEventChosen][eventChosen])-boughtTickets);
		//UPDATING AVAILABLE TICKETS
		ticketsAvailableOfUserChosenEventLabel.setText("Tickets Available: " + EVENT_DETAILS[4][categoryOfEventChosen][eventChosen]);//updates displayed available tickets on event booking panel		
		for(int counter=0;counter<NUM_EVENTS;counter++) {//updates displayed available tickets on event panel
			if(eventChosen==counter) {
				jLabelsEventPanelDetails[4][counter].setText("Tickets Available: " + EVENT_DETAILS[4][categoryOfEventChosen][eventChosen]);
				break;//once display is changed, breaks loop for no more further iterations
			}
		}
	}
	private void updateTransactionLog(int numTickets) {
		numOfTransactions++;//increments number of transactions made
		transactionHistoryDisplayArea.append("\n\nTRANSACTION NO. " + numOfTransactions + " Successfully booked \n" + numTickets + " tickets of "+ EVENT_DETAILS[5][categoryOfEventChosen][eventChosen]+ "\nTotal Price of " + calculateBooking(numTickets));
		transactionHistoryScrollPane.getVerticalScrollBar().setValue(transactionHistoryScrollPane.getVerticalScrollBar().getMaximum());//makes latest transactions visible in the screen by setting the value of scroll bar to max
	}
}