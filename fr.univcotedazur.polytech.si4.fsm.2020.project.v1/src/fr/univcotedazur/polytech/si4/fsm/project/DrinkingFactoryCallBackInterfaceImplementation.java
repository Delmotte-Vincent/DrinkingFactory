package fr.univcotedazur.polytech.si4.fsm.project;

import fr.univcotedazur.polytech.si4.fsm.project.defaultsm.IDefaultSMStatemachine.SCInterfaceOperationCallback;


public class DrinkingFactoryCallBackInterfaceImplementation implements SCInterfaceOperationCallback {
    
    DrinkFactoryMachine theGUI;
    
    public DrinkingFactoryCallBackInterfaceImplementation(DrinkFactoryMachine df) {
        theGUI = df;
    }

    @Override
    public String getSelection() {
        return null;
    }

    @Override
    public boolean isHot() {
        return false;
    }

    @Override
    public long getSugar() {
        return 0;
    }

    @Override
    public long getSize() {
        return 0;
    }

    @Override
    public long getTemperature() {
        return 0;
    }

	@Override
	public boolean isPaid() {
		return false;
	}
}
