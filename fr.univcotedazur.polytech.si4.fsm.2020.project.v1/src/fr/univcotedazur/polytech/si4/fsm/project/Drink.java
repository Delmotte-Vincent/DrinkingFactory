package fr.univcotedazur.polytech.si4.fsm.project;

public class Drink {
	enum DrinkType {
		COFFEE,
		EXPRESSO,
		TEA,
		SOUP,
		ICEDTEA
	}
	
	DrinkType type;
	int CondimentDose;
	int size;
	int temperature;
	boolean erableOption;
	boolean croutonOption;
	boolean glaceOption;
	boolean milkOption;
	
	
	public Drink() {
		
	}
	
	public float price() {
		float price = 0;
		if	(type == null) {
			return 0;
		}
		switch (type) {
			case COFFEE:
				price += 0.35;
				break;
			case EXPRESSO:
				price += 0.50;
				break;
			case TEA:
				price += 0.40;
				break;
			case SOUP:
				price += 0.75;
				break;
			case ICEDTEA:
				if (this.size == 0)
				{
					price += 0.50;
				}else {
					price += 0.75;
				}
				break;
			default:
				return 0;
		}
		if (erableOption) { price += 0.10; }
		if (croutonOption) { price += 0.30; }
		if (glaceOption) { price += 0.60; }
		if (milkOption) { price += 0.10; }
		return price;
		
	}
	

}
