package fr.univcotedazur.polytech.si4.fsm.project;

import fr.univcotedazur.polytech.si4.fsm.project.defaultsm.IDefaultSMStatemachine.SCInterfaceOperationCallback;


public class DrinkingFactoryCallBackInterfaceImplementation implements SCInterfaceOperationCallback {
    
    DrinkFactoryMachine theGUI;
    
    public DrinkingFactoryCallBackInterfaceImplementation(DrinkFactoryMachine df) {
        theGUI = df;
    }

    @Override
    public String getSelection() {
        return theGUI.getSelection();
    }

    @Override
    public boolean isHot() {
        return theGUI.isHot();
    }

    @Override
    public long getSugar() {
        switch(theGUI.sugarSlider.getValue()){
            case 0:
                System.out.println("0 sugar dose");
                return 0;
            case 1:
                System.out.println("1 sugar dose");
                return 1;
            case 2:
                System.out.println("2 sugar dose");
                return 2;
            case 3:
                System.out.println("3 sugar dose");
                return 3;
            case 4:
                System.out.println("4 sugar dose");
                return 4;

            default : return 0;
        }
    }

    @Override
    public long getSize() {
        switch(theGUI.sizeSlider.getValue()){
            case 0:
                System.out.println("short");
                return 0;
            case 1:
                System.out.println("medium");
                return 1;
            case 2:
                System.out.println("long");
                return 2;

            default : return 0;
        }

    }

    @Override
    public long getTemperature() {
        switch(theGUI.temperatureSlider.getValue()){
            case 0:
                System.out.println("20°");
                return 20;
            case 1:
                System.out.println("35°");
                return 35;
            case 2:
                System.out.println("60°");
                return 60;
            case 3:
                System.out.println("85°");
                return 85;
            default : return 0;
        }
    }

	@Override
	public boolean isPaid() {
        return theGUI.isPaid();
	}

	@Override
	public boolean isReady() {
		return true;
	}

    @Override
    public boolean isComplete() {
        return theGUI.isComplete();
    }

    @Override
    public boolean isDispo() {
        return theGUI.isDispo();
    }

	@Override
	public boolean isFilled() {
		return true;
	}

	@Override
	public boolean isCooled() {
		return theGUI.isCooled();
	}
}
