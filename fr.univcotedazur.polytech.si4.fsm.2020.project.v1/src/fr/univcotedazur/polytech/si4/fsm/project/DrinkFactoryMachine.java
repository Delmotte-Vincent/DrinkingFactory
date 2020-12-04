package fr.univcotedazur.polytech.si4.fsm.project;

import fr.univcotedazur.polytech.si4.fsm.project.Drink.DrinkType;
import fr.univcotedazur.polytech.si4.fsm.project.defaultsm.DefaultSMStatemachine;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class DrinkFactoryMachine extends JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = 2030629304432075314L;
	private Hashtable<String, Integer> stock;
	private ArrayList<CarteBancaire> carteBancaires;
	private JPanel contentPane;
	private DefaultSMStatemachine theFSM;
	private TimerService timer;
	private JLabel messagesToUser;
	private CarteBancaire userCard;
	private boolean optionSugar;
	private boolean optionCrouton;
	private boolean optionMilk;
	private boolean optionIceCream;
	private boolean persoCup;
	public JSlider sugarSlider;
	public JSlider sizeSlider;
	public JSlider temperatureSlider;
	public double payment;
	public int temperature;
	public PayType paymentType;
	public double reduction;
	public int progress;
	JLabel lblSugar;
	JLabel lblSize;
	JLabel lblTemperature;
	Hashtable<Integer, JLabel> temperatureTable;
	Hashtable<Integer, JLabel> coldTemperatureTable;
	JProgressBar progressBar;
	Drink drink = new Drink();
	JLabel labelForPictures;

	/**
	 * @wbp.nonvisual location=311,475
	 */
	//private final ImageIcon imageIcon = new ImageIcon();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DrinkFactoryMachine frame = new DrinkFactoryMachine();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}





	/**
	 * Create the frame.
	 */
	public DrinkFactoryMachine() {
		carteBancaires = new ArrayList<CarteBancaire>();

		initialisationStock();

		theFSM = new DefaultSMStatemachine();
		timer = new TimerService();
		theFSM.setTimer(timer);
		DrinkingFactoryCallBackInterfaceImplementation callback = new DrinkingFactoryCallBackInterfaceImplementation(this);
		theFSM.getSCInterface().setSCInterfaceOperationCallback(callback);
		theFSM.init();
		theFSM.enter();
		theFSM.getSCInterface().getListeners().add(new DrinkingMachineInterfaceImplementation(this));

		theFSM.setSelection(" ");

		setForeground(Color.WHITE);
		setFont(new Font("Cantarell", Font.BOLD, 22));
		setBackground(Color.DARK_GRAY);
		setTitle("Drinking Factory Machine");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 650);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		messagesToUser = new JLabel("<html>Bienvenue");
		messagesToUser.setForeground(Color.WHITE);
		messagesToUser.setHorizontalAlignment(SwingConstants.LEFT);
		messagesToUser.setVerticalAlignment(SwingConstants.TOP);
		messagesToUser.setToolTipText("message to the user");
		messagesToUser.setBackground(Color.WHITE);
		messagesToUser.setBounds(126, 34, 165, 175);
		contentPane.add(messagesToUser);

		JLabel lblCoins = new JLabel("Coins");
		lblCoins.setForeground(Color.WHITE);
		lblCoins.setHorizontalAlignment(SwingConstants.CENTER);
		lblCoins.setBounds(538, 12, 44, 15);
		contentPane.add(lblCoins);

		JButton coffeeButton = new JButton("Coffee");
		coffeeButton.setForeground(Color.WHITE);
		coffeeButton.setBackground(Color.DARK_GRAY);
		coffeeButton.setBounds(12, 34, 96, 25);
		contentPane.add(coffeeButton);
		coffeeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isInStock("Coffee")) {
					theFSM.setSelection("Coffee");
					theFSM.raiseClassicDrinkTrigger();
					drink.type = DrinkType.COFFEE;

					updateUI();
				}
				else {
					ruptureDeStockMessage();
				}
			}
		});

		JButton expressoButton = new JButton("Expresso");
		expressoButton.setForeground(Color.WHITE);
		expressoButton.setBackground(Color.DARK_GRAY);
		expressoButton.setBounds(12, 71, 96, 25);
		contentPane.add(expressoButton);
		expressoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isInStock("Expresso")) {
					theFSM.setSelection("Expresso");
					theFSM.raiseClassicDrinkTrigger();
					drink.type = DrinkType.EXPRESSO;
					updateUI();
				}
				else {
					ruptureDeStockMessage();
				}
			}
		});

		JButton teaButton = new JButton("Tea");
		teaButton.setForeground(Color.WHITE);
		teaButton.setBackground(Color.DARK_GRAY);
		teaButton.setBounds(12, 108, 96, 25);
		contentPane.add(teaButton);
		teaButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isInStock("Tea")) {
					theFSM.setSelection("Tea");
					theFSM.raiseClassicDrinkTrigger();
					drink.type = DrinkType.TEA;
					updateUI();
				}
				else {
					ruptureDeStockMessage();
				}

			}
		});

		JButton icedTeaButton = new JButton("Iced Tea");
		icedTeaButton.setForeground(Color.WHITE);
		icedTeaButton.setBackground(Color.DARK_GRAY);
		icedTeaButton.setBounds(12, 182, 96, 25);
		contentPane.add(icedTeaButton);
		icedTeaButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isInStock("IcedTea")) {
					theFSM.setSelection("IcedTea");
					theFSM.raiseIceTeaTrigger();
					drink.type = DrinkType.ICEDTEA;
					updateUI();
				}
				else {
					ruptureDeStockMessage();
				}

			}
		});

		JButton soupButton = new JButton("Soup");
		soupButton.setForeground(Color.WHITE);
		soupButton.setBackground(Color.DARK_GRAY);
		soupButton.setBounds(12, 145, 96, 25);
		contentPane.add(soupButton);
		soupButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				theFSM.setSelection("Soup");
				theFSM.raiseSoupTrigger();
				drink.type = DrinkType.SOUP;
				updateUI();
			}
		});

		JButton optionSugarButton = new JButton("Erable");
		optionSugarButton.setForeground(Color.WHITE);
		optionSugarButton.setBackground(Color.DARK_GRAY);
		optionSugarButton.setBounds(12, 215, 75, 25);
		contentPane.add(optionSugarButton);
		optionSugarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (optionSugar) {
					System.out.println(" (-) Sucre d'Erable");
					optionSugar = false;
					drink.erableOption = false;
				}
				else {
					System.out.println(" (+) Sucre d'Erable");
					optionSugar = true;
					drink.erableOption = true;
				}
				updateUI();
			}
		});

		JButton optionCroutonButton = new JButton("Crouton");
		optionCroutonButton.setForeground(Color.WHITE);
		optionCroutonButton.setBackground(Color.DARK_GRAY);
		optionCroutonButton.setBounds(89, 215, 80, 25);
		contentPane.add(optionCroutonButton);
		optionCroutonButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (optionCrouton) {
					System.out.println(" (-) Crouton");
					optionCrouton = false;
					drink.croutonOption = false;
				}
				else {
					System.out.println(" (+) Crouton");
					optionCrouton = true;
					drink.croutonOption = true;
				}
				updateUI();
			}
		});

		JButton optionIceCreamButton = new JButton("Glace");
		optionIceCreamButton.setForeground(Color.WHITE);
		optionIceCreamButton.setBackground(Color.DARK_GRAY);
		optionIceCreamButton.setBounds(169, 215, 75, 25);
		contentPane.add(optionIceCreamButton);
		optionIceCreamButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (optionIceCream) {
					System.out.println(" (-) Creme glacée");
					optionIceCream = false;
					drink.glaceOption = false;
				}
				else {
					System.out.println(" (+) Creme glacée");
					optionIceCream = true;
					drink.glaceOption = true;
				}
				updateUI();
			}
		});

		JButton optionMilkButton = new JButton("Milk");
		optionMilkButton.setForeground(Color.WHITE);
		optionMilkButton.setBackground(Color.DARK_GRAY);
		optionMilkButton.setBounds(245, 215, 75, 25);
		contentPane.add(optionMilkButton);
		optionMilkButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (optionMilk) {
					System.out.println(" (-) Lait");
					optionMilk = false;
					drink.milkOption = false;
				}
				else {
					System.out.println(" (+) Lait");
					optionMilk = true;
					drink.milkOption = true;
				}
				updateUI();
			}
		});

		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progress = 0;
		progressBar.setValue(progress);
		progressBar.setForeground(Color.LIGHT_GRAY);
		progressBar.setBackground(Color.DARK_GRAY);
		progressBar.setBounds(12, 254, 622, 26);
		contentPane.add(progressBar);

		sugarSlider = new JSlider();
		sugarSlider.setValue(0);
		sugarSlider.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		sugarSlider.setBackground(Color.DARK_GRAY);
		sugarSlider.setForeground(Color.WHITE);
		sugarSlider.setPaintTicks(true);
		sugarSlider.setMinorTickSpacing(1);
		sugarSlider.setMajorTickSpacing(1);
		sugarSlider.setMaximum(4);
		sugarSlider.setBounds(330, 51, 200, 36);
		contentPane.add(sugarSlider);
		sugarSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				theFSM.raiseSugarTrigger();
				drink.CondimentDose = sugarSlider.getValue();
			}
		});


		sizeSlider = new JSlider();
		sizeSlider.setPaintTicks(true);
		sizeSlider.setValue(0);
		sizeSlider.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		sizeSlider.setBackground(Color.DARK_GRAY);
		sizeSlider.setForeground(Color.WHITE);
		sizeSlider.setMinorTickSpacing(1);
		sizeSlider.setMaximum(2);
		sizeSlider.setMajorTickSpacing(1);
		sizeSlider.setBounds(330, 125, 200, 36);
		contentPane.add(sizeSlider);
		sizeSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				theFSM.raiseSizeTrigger();
				drink.size = sizeSlider.getValue();
				if (drink.type != null) {
					updateUI();
				}
			}
		});


		temperatureSlider = new JSlider();
		temperatureSlider.setPaintLabels(true);
		temperatureSlider.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		temperatureSlider.setValue(0);
		temperatureSlider.setBackground(Color.DARK_GRAY);
		temperatureSlider.setForeground(Color.WHITE);
		temperatureSlider.setPaintTicks(true);
		temperatureSlider.setMajorTickSpacing(1);
		temperatureSlider.setMaximum(3);
		temperatureSlider.setBounds(330, 188, 200, 54);
		temperatureSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				theFSM.raiseTemperatureTrigger();
				drink.temperature = temperatureSlider.getValue();
			}
		});


		temperatureTable = new Hashtable<Integer, JLabel>();
		temperatureTable.put(0, new JLabel("20°C"));
		temperatureTable.put(1, new JLabel("35°C"));
		temperatureTable.put(2, new JLabel("60°C"));
		temperatureTable.put(3, new JLabel("85°C"));
		for (JLabel l : temperatureTable.values()) {
			l.setForeground(Color.WHITE);
		}

		coldTemperatureTable = new Hashtable<Integer, JLabel>();
		coldTemperatureTable.put(0, new JLabel("0°C"));
		coldTemperatureTable.put(1, new JLabel("5°C"));
		coldTemperatureTable.put(2, new JLabel("10°C"));
		coldTemperatureTable.put(3, new JLabel("15°C"));
		for (JLabel l : coldTemperatureTable.values()) {
			l.setForeground(Color.WHITE);
		}
		temperatureSlider.setLabelTable(temperatureTable);
		contentPane.add(temperatureSlider);

		lblSugar = new JLabel("Sugar");
		lblSugar.setForeground(Color.WHITE);
		lblSugar.setBackground(Color.DARK_GRAY);
		lblSugar.setHorizontalAlignment(SwingConstants.CENTER);
		lblSugar.setBounds(380, 34, 44, 15);
		contentPane.add(lblSugar);

		lblSize = new JLabel("Size");
		lblSize.setForeground(Color.WHITE);
		lblSize.setBackground(Color.DARK_GRAY);
		lblSize.setHorizontalAlignment(SwingConstants.CENTER);
		lblSize.setBounds(380, 113, 44, 15);
		contentPane.add(lblSize);

		lblTemperature = new JLabel("Temperature");
		lblTemperature.setForeground(Color.WHITE);
		lblTemperature.setBackground(Color.DARK_GRAY);
		lblTemperature.setHorizontalAlignment(SwingConstants.CENTER);
		lblTemperature.setBounds(363, 173, 96, 15);
		contentPane.add(lblTemperature);

		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		lblCoins.setLabelFor(panel);
		panel.setBounds(538, 25, 96, 97);
		contentPane.add(panel);

		JButton money50centsButton = new JButton("0.50 €");
		money50centsButton.setForeground(Color.WHITE);
		money50centsButton.setBackground(Color.DARK_GRAY);
		panel.add(money50centsButton);
		money50centsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (paymentType != PayType.NFC) {
					paymentType = PayType.COIN;
					theFSM.raiseCoinTrigger();
					payment += 0.50;
					updateUI();
				}

			}
		});

		JButton money25centsButton = new JButton("0.25 €");
		money25centsButton.setForeground(Color.WHITE);
		money25centsButton.setBackground(Color.DARK_GRAY);
		panel.add(money25centsButton);
		money25centsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (paymentType != PayType.NFC) {
					paymentType = PayType.COIN;
					theFSM.raiseCoinTrigger();
					payment += 0.25;
					updateUI();
				}
			}
		});

		JButton money10centsButton = new JButton("0.10 €");
		money10centsButton.setForeground(Color.WHITE);
		money10centsButton.setBackground(Color.DARK_GRAY);
		panel.add(money10centsButton);
		money10centsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (paymentType != PayType.NFC) {
					paymentType = PayType.COIN;
					theFSM.raiseCoinTrigger();
					payment += 0.10;
					updateUI();
				}

			}
		});

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.DARK_GRAY);
		panel_1.setBounds(538, 154, 96, 40);
		contentPane.add(panel_1);

		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.DARK_GRAY);
		panel_4.setBounds(538, 187, 96, 40);
		contentPane.add(panel_4);

		JTextField nfcTextField = new JTextField();
		nfcTextField.setColumns(7);
		panel_4.add(nfcTextField);

		JButton nfcBiiiipButton = new JButton("biiip");
		nfcBiiiipButton.setForeground(Color.WHITE);
		nfcBiiiipButton.setBackground(Color.DARK_GRAY);
		panel_1.add(nfcBiiiipButton);
		nfcBiiiipButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String input = nfcTextField.getText();
				System.out.println("Bip:" + input);

				if (paymentType != PayType.COIN) {
					paymentType = PayType.NFC;

					if (!alreadyRegister(input)) {
						carteBancaires.add(new CarteBancaire(input));
					}

					userCard = getCardByInput(input);
					theFSM.raiseNfcTrigger();
				}
			}
		});




		JLabel lblNfc = new JLabel("NFC");
		lblNfc.setForeground(Color.WHITE);
		lblNfc.setHorizontalAlignment(SwingConstants.CENTER);
		lblNfc.setBounds(541, 139, 41, 15);
		contentPane.add(lblNfc);

		JSeparator separator = new JSeparator();
		separator.setBounds(12, 292, 622, 15);
		contentPane.add(separator);
		
		BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(new File("./picts/vide2.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		labelForPictures = new JLabel(new ImageIcon(myPicture));
		labelForPictures.setBounds(175, 319, 286, 260);
		contentPane.add(labelForPictures);

		JButton addOwnCupButton = new JButton("Take back cup");
		addOwnCupButton.setForeground(Color.WHITE);
		addOwnCupButton.setBackground(Color.DARK_GRAY);
		addOwnCupButton.setBounds(45, 336, 128, 25);
		contentPane.add(addOwnCupButton);
		addOwnCupButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				persoCup = false;
				reduction = 0;
				updateUI();
				System.out.println("You take off your personnal cup");
				theFSM.raiseAddCupB();
				//------------------------------------
				BufferedImage myPicture = null;
				try {
					myPicture = ImageIO.read(new File("./picts/vide2.jpg"));
				} catch (IOException ee) {
					ee.printStackTrace();
				}
				labelForPictures.setIcon(new ImageIcon(myPicture));
				//------------------------------------
			}
		});

		JButton addCupButton = new JButton("Add your cup");
		addCupButton.setForeground(Color.WHITE);
		addCupButton.setBackground(Color.DARK_GRAY);
		addCupButton.setBounds(45, 376, 96, 25);
		contentPane.add(addCupButton);
		addCupButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				persoCup = true;
				reduction = 0.10;
				updateUI();
				System.out.println("You add your personnal cup");
				theFSM.raiseAddCupB();
				//------------------------------------
				BufferedImage myPicture = null;
				try {
					myPicture = ImageIO.read(new File("./picts/ownCup.jpg"));
				} catch (IOException ee) {
					ee.printStackTrace();
				}
				labelForPictures.setIcon(new ImageIcon(myPicture));
				//------------------------------------
			}
		});

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.DARK_GRAY);
		panel_2.setBounds(538, 185, 96, 33);
		contentPane.add(panel_2);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.DARK_GRAY);
		panel_3.setBounds(538, 217, 96, 33);
		contentPane.add(panel_3);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setForeground(Color.WHITE);
		cancelButton.setBackground(Color.DARK_GRAY);
		panel_3.add(cancelButton);
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				theFSM.raiseCancelB();
			}
		});

	}

	public void doRefund() {
		System.out.println("you're refund of " + this.payment +"€");
	}

	public void doReset() {
		System.out.println("RESET");
		this.paymentType = PayType.DEFAULT;
		this.optionSugar = false;
		this.optionCrouton = false;
		this.optionMilk = false;
		this.optionIceCream = false;
		this.temperature = 0;
		this.reduction = 0;
		this.payment = 0;
		this.persoCup = false;
		theFSM.setSelection(" ");
		messagesToUser.setText("<html>Welcome");
		sugarSlider.setValue(0);
		sizeSlider.setValue(0);
		temperatureSlider.setValue(0);
		progress = 0;
		progressBar.setValue(progress);
		drink = new Drink();
		drink.CondimentDose = sugarSlider.getValue();
		drink.size = sizeSlider.getValue();
		drink.temperature = temperatureSlider.getValue();
		drink.croutonOption = false;
		drink.erableOption = false;
		drink.glaceOption = false;
		drink.milkOption = false;
		System.out.println("INIT DRINK CREATED");
		
		//------------------------------------
		BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(new File("./picts/vide2.jpg"));
		} catch (IOException ee) {
			ee.printStackTrace();
		}
		labelForPictures.setIcon(new ImageIcon(myPicture));
		//------------------------------------
	}

	public void doWaterHeat() {
		if (!isHot()) {
			temperature += 2;
			System.out.println("+2°,  temperature : " + temperature +"°");
		}
	}


	public void doCoffee() {
		System.out.println("Coffe Preparation");
		progressBarIncrement();
	}

	public void doExpresso() {
		System.out.println("Expresso Preparation");
		progressBarIncrement();
	}

	public void doTea() {
		System.out.println("Tea Preparation");
		progressBarIncrement();
	}

	public void doWaterFlow() {
		System.out.println("water flow");
		theFSM.raiseWaterFilled();
		progressBarIncrement();
	}

	//-----Changement des sliders selon le type de boisson-------
	public void setClassicSliders() {
		System.out.println("Classic Sliders");
		lblSugar.setText("Sugar");
		sizeSlider.setMaximum(2);
		temperatureSlider.setLabelTable(temperatureTable);
	}

	public void setSoupSliders() {
		System.out.println("Soup Sliders");
		lblSugar.setText("Spices");
		sizeSlider.setMaximum(2);
		temperatureSlider.setLabelTable(temperatureTable);
	}

	public void setIceTeaSliders() {
		System.out.println("Ice Tea Sliders");
		lblSugar.setText("Sugar");
		sizeSlider.setMaximum(1);
		temperatureSlider.setLabelTable(coldTemperatureTable);
	}
	//------------------------------------------------------------

	public void doPutCup() {
		if (!persoCup) {
			System.out.println("Put a cup");
			//------------------------------------
			BufferedImage myPicture = null;
			try {
				myPicture = ImageIO.read(new File("./picts/gobeletPolluant.jpg"));
			} catch (IOException ee) {
				ee.printStackTrace();
			}
			labelForPictures.setIcon(new ImageIcon(myPicture));
			//------------------------------------
		}
		progressBarIncrement();
	}

	public String getSelection() {
		return theFSM.getSelection();
	}

	public void doCheckNFC() {

	}

	public void doAddSugar() {
		if (!optionSugar) {
			System.out.println("Adding " + sugarSlider.getValue() + " doses of sugar");
			progressBarIncrement();
		}
		else {
			System.out.println("Adding " + sugarSlider.getValue() + " doses of erable sugar");
			progressBarIncrement();
		}
	}

	public void doAddSpices() {
		System.out.println("Adding " + sugarSlider.getValue() + " doses of spices");
		progressBarIncrement();
	}

	public boolean isHot() {
		int goal = 0;
		switch(temperatureSlider.getValue()){
			case 0:
				goal = 20;
				break;
			case 1:
				goal = 35;
				break;
			case 2:
				goal = 60;
				break;
			case 3:
				goal = 85;
				break;
			default :
				goal = 50;
				break;
		}

		if (temperature >= goal) {
			progressBarIncrement();
			return true;
		}
		return false;
	}

	/**
	 * Permet de verifier s'il y a rupture de stock
	 * @param produit
	 * @return true si présent dans le stock
	 * @return false sinon
	 */
	public boolean isInStock(String produit) {
		return (stock.get(produit) > 0);
	}

	/**
	 * Méthode qui s'occupe de l'affichage utilisateur
	 */
	private void updateUI() {
		String messageOptionCrouton;
		String messageOptionLait;
		String messageOptionIceCream;
		String messageOptionErable;

		if (optionCrouton) {
			messageOptionCrouton = "(+)";
		}
		else {
			messageOptionCrouton = "(-)";
		}

		if (optionSugar) {
			messageOptionErable = "(+)";
		}
		else {
			messageOptionErable = "(-)";
		}

		if (optionIceCream) {
			messageOptionIceCream = "(+)";
		}
		else {
			messageOptionIceCream = "(-)";
		}

		if (optionMilk) {
			messageOptionLait = "(+)";
		}
		else {
			messageOptionLait = "(-)";
		}


		String message = "<html>Bienvenue<br>Selection : " + theFSM.getSelection()
				+ "<br>Coût : " + String.format("%.2f", drink.price()-this.reduction) + "€"
				+ "<br>Paiement : " + this.payment + "€"
				+ "<br>"
				+ "<br>OPTIONS :"
				+ "<br>Sucre d'Erable : " + messageOptionErable
				+ "<br>Crouton : " + messageOptionCrouton
				+ "<br>Lait : " + messageOptionLait
				+ "<br>Crème glacée : " + messageOptionIceCream;

		messagesToUser.setText(message);
	}

	/**
	 * Envoie un message de rupture de stock à l'utilisateur
	 */
	private void ruptureDeStockMessage() {
		messagesToUser.setText(
				"<html>Désolé, la boisson que vous avez selectionné est momentanément indisponible " +
						"<br>Veuillez choisir une autre boisson"
		);
	}

	/**
	 * Une fois la boisson réalisé on décrémente les dosettes
	 */
	private void decrementStock() {
		String selection = getSelection();

		if (stock.get(selection) != null) {
			int quantité = stock.get(selection);
			stock.put(selection, quantité - 1);
			System.out.println("Stock decrementé");
		}


	}

	public void doDecrement() {
		decrementStock();
	}

	public boolean isPaid() {
			if (paymentType.equals(PayType.NFC)) {
				return true;
			}
		return (payment >= drink.price());
	}

	public boolean isComplete() {
		return true;
	}

	public boolean isInfused() {
		return true;
	}

	public boolean isDispo() {
		if (!theFSM.getSelection().equals(" ")) {
			return isInStock(theFSM.getSelection());
		}
		return false;
	}

	public void doNotify() {
		messagesToUser.setText("<html>Votre boisson est prête" +
				"<br>Bonne dégustation !" +
				"<br>A bientôt"
		);
	}

	public boolean alreadyRegister(String idCB) {
		for (CarteBancaire cb : carteBancaires) {
			if (cb.getId().equals(idCB)) {
				return true;
			}
		}
		return false;
	}

	public void doSoup() {
		System.out.println("Soup preparation");
		progressBarIncrement();
	}

	public void doIceTea() {
		System.out.println("Ice Tea preparation");
		progressBarIncrement();
	}

	public CarteBancaire getCardByInput(String id) {
		for (CarteBancaire cb : carteBancaires) {
			if (cb.getId().equals(id))
				return cb;
		}
		return null;
	}

	public void doPay() {
		if (paymentType.equals(PayType.NFC)) {
			userCard.addCommande(drink.price());
			reduction += userCard.getReduction();
			System.out.println("You paid " + (drink.price()-reduction) +"€");
		}

		if (paymentType.equals(PayType.COIN) && (drink.price()-reduction) < payment) {
			System.out.println("You're refund of :" + (payment - (drink.price()-reduction)) + "€");
		}
	}

	public void doInfusion() {
		System.out.println("Infusion...");
		progressBarIncrement();
	}

	public void progressBarIncrement(){
		int steps = 0;
		switch(theFSM.getSelection()) {
			case "Coffee":
				steps = 5;
				break;
			case "Soup":
				steps = 5;
				break;
			case "IcedTea":
				steps = 9;
				break;
			default:
				steps = 6;
				break;
		}
		float increment = 100/steps;
		progress += increment;
		System.out.println("Progress raised by "+increment+" !");
		progressBar.setValue(progress);
	}

	public void validateStep() {
		progress += 50;
		progressBar.setValue(progress);
	}

	public void doCrouton() {
		if (optionCrouton) {
			System.out.println("Add some crouton to your soup");
		}
	}

	public void addMilk() {
		if (optionMilk) {
			System.out.println("Add some Milk");
		}
	}

	public void addIceCream() {
		if (optionIceCream)  {
			System.out.println("Add ice cream");
		}
	}


	
	
	public void doCooling() {
		temperature -= 2;
		System.out.println("-2°,  temperature : " + temperature +"°");
	}

	public boolean isCooled() {
		int goal = 0;
		switch(temperatureSlider.getValue()){
			case 0:
				goal = 0;
				break;
			case 1:
				goal = 5;
				break;
			case 2:
				goal = 10;
				break;
			case 3:
				goal = 15;
				break;
			default :
				goal = 0;
				break;
		}
		
		if (temperature <= goal) {
			progressBarIncrement();
			return true;
		}
		return false;
	}

	/**
	 * Initialisation des stocks
	 */
	public void initialisationStock() {
		stock = new Hashtable<>();
		stock.put("Coffee", 10);
		stock.put("Expresso", 20);
		stock.put("Tea", 8);
		stock.put("Soup", 1);
		stock.put("IcedTea", 5);
		stock.put("Nuage de lait", 5);
		stock.put("Croutons", 5);
		stock.put("Glace vanille", 5);
		stock.put("Sirop d'erable", 5);
	}





	public void doGrainCompacting() {
		System.out.println("Compacting grains...");
		progressBarIncrement();
	}
}
