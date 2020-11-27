package fr.univcotedazur.polytech.si4.fsm.project;

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
import java.util.HashMap;
import java.util.Hashtable;
import java.util.IdentityHashMap;

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
	public JSlider sugarSlider;
    public JSlider sizeSlider;
    public JSlider temperatureSlider;
    public double payment;
    public double price;
    public int temperature;
    public PayType paymentType;
    public double reduction;
    JLabel lblSugar;
    JLabel lblSize;
    JLabel lblTemperature;
    Hashtable<Integer, JLabel> temperatureTable;
    Hashtable<Integer, JLabel> coldTemperatureTable;

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

        stock = new Hashtable<>();
        stock.put("Coffee", 1);
        stock.put("Expresso", 0);
        stock.put("Tea", 8);
        stock.put("Soup", 1);
        stock.put("IcedTea", 5);
        stock.put("Nuage de lait", 5);
        stock.put("Croutons", 5);
        stock.put("Glace vanille", 5);
        stock.put("Sirop d'erable", 5);

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
					price = 0.35;
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
					price = 0.50;
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
					price = 0.40;
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
				price = 1.0;
				updateUI();
			}
		});

		JProgressBar progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setValue(0);
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
		sugarSlider.setBounds(301, 51, 200, 36);
		contentPane.add(sugarSlider);
		sugarSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                theFSM.raiseSugarTrigger();
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
		sizeSlider.setBounds(301, 125, 200, 36);
		contentPane.add(sizeSlider);
        sizeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                theFSM.raiseSizeTrigger();
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
		temperatureSlider.setBounds(301, 188, 200, 54);
        temperatureSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                theFSM.raiseTemperatureTrigger();
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
		coldTemperatureTable.put(0, new JLabel("15°C"));
		coldTemperatureTable.put(1, new JLabel("10°C"));
		coldTemperatureTable.put(2, new JLabel("5°C"));
		coldTemperatureTable.put(3, new JLabel("2°C"));
		for (JLabel l : coldTemperatureTable.values()) {
			l.setForeground(Color.WHITE);
		}
		
		temperatureSlider.setLabelTable(temperatureTable);

		contentPane.add(temperatureSlider);

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
					price = 1.0;
					updateUI();
				}
				else {
					ruptureDeStockMessage();
				}

			}
		});

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

					payment = price;
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

		JButton addOwnCupButton = new JButton("Add your cup");
		addOwnCupButton.setForeground(Color.WHITE);
		addOwnCupButton.setBackground(Color.DARK_GRAY);
		addOwnCupButton.setBounds(45, 336, 128, 25);
		contentPane.add(addOwnCupButton);
		addOwnCupButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reduction = 0.10;
				updateUI();
				double delta = (price-reduction) - payment;
			    if (payment >= (price-reduction)) {
                    theFSM.raiseAddCupB();
                    System.out.println("add a cup and refund " + Math.abs(delta));
                }
			    else {
                    System.out.println("You need to insert " + delta + "€");
                }

			}
		});

		JButton addCupButton = new JButton("Add cup");
		addCupButton.setForeground(Color.WHITE);
		addCupButton.setBackground(Color.DARK_GRAY);
		addCupButton.setBounds(45, 376, 96, 25);
		contentPane.add(addCupButton);
		addCupButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				double delta = (price-reduction) - payment;
			    if (payment >= (price-reduction)) {
                    theFSM.raiseAddCupB();
                    System.out.println("add a cup and refund " + Math.abs(delta));
                }
			    else {
                    System.out.println("You need to insert " + delta + "€");
                }

				theFSM.raiseAddCupB();
			}
		});

		BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(new File("./picts/vide2.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JLabel labelForPictures = new JLabel(new ImageIcon(myPicture));
		labelForPictures.setBounds(175, 319, 286, 260);
		contentPane.add(labelForPictures);

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


		// listeners
		addCupButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				BufferedImage myPicture = null;
				try {
					myPicture = ImageIO.read(new File("./picts/ownCup.jpg"));
				} catch (IOException ee) {
					ee.printStackTrace();
				}
				labelForPictures.setIcon(new ImageIcon(myPicture));
			}
		});

	}

	public void doRefund() {
        System.out.println("you're refund of " + this.payment +"€");
	}

	public void doReset() {

        System.out.println("RESET");
        this.paymentType = PayType.DEFAULT;
        this.temperature = 0;
		this.reduction = 0;
        this.payment = 0;
        this.price = 0;
        theFSM.setSelection(" ");
        messagesToUser.setText("<html>Welcome");
        sugarSlider.setValue(0);
        sizeSlider.setValue(0);
        temperatureSlider.setValue(0);

	}

	public void doWaterHeat() {
        temperature += 2;
		System.out.println("+2°,  temperature : " + temperature +"°");
	}


	public void doCoffee() {
        System.out.println("Coffe Preparation");
	}

	public void doExpresso() {
        System.out.println("Expresso Preparation");
	}

	public void doTea() {
        System.out.println("Tea Preparation");
	}

	public void doWaterFlow() {
        System.out.println("water flow");
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
        System.out.println("Put a cup");

	}

    public String getSelection() {
	    return theFSM.getSelection();
    }

	public void doCheckNFC() {

	}

	public void doAddSugar() {
		System.out.println("Adding " + sugarSlider.getValue() + " doses of sugar");

	}
	
	public void doAddSpices() {
		System.out.println("Adding " + sugarSlider.getValue() + " doses of spices");
	}

    public boolean isHot() {
		switch(temperatureSlider.getValue()){
			case 0:
				return temperature >= 20;
			case 1:
				return temperature >= 35;
			case 2:
				return temperature >= 60;
			case 3:
				return temperature >= 85;
			default : return false;
		}
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
		messagesToUser.setText(
				"<html>Bienvenue<br>Selection : " + theFSM.getSelection()
				+ "<br>Coût : " + String.format("%.2f", this.price-this.reduction) + "€"
				+ "<br>Paiement : " + this.payment + "€"
		);
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
	    return (payment >= price);
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

	private CarteBancaire getCB(String idCB) {
		for (CarteBancaire cb : carteBancaires) {
			if (cb.getId().equals(idCB)) {
				return cb;
			}
		}
		return null;
	}

	public void doSoup() {
		System.out.println("Soup preparation");
		
	}

	public void doIceTea() {
		System.out.println("Ice Tea preparation");
	}
	
	public void doCooling() {
		temperature -= 2;
		System.out.println("-2°,  temperature : " + temperature +"°");
		int tempGoal = 0;
		switch(temperatureSlider.getValue()){
			case 0:
				tempGoal = 15;
			case 1:
				tempGoal = 10;
			case 2:
				tempGoal = 5;
			case 3:
				tempGoal = 2;
		}
		if (temperature <= tempGoal) {
			theFSM.raiseCoolingDone();
		}
	}
}
