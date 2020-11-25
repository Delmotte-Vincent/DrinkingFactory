package fr.univcotedazur.polytech.si4.fsm.project;

import fr.univcotedazur.polytech.si4.fsm.project.defaultsm.IDefaultSMStatemachine.SCInterfaceListener;

public class DrinkingMachineInterfaceImplementation implements SCInterfaceListener {
    DrinkFactoryMachine theDF;

    public DrinkingMachineInterfaceImplementation(DrinkFactoryMachine df) {
        theDF = df;
    }

    @Override
    public void onDoRefundRaised() {
        theDF.doRefund();
    }

    @Override
    public void onDoResetRaised() {
        theDF.doReset();
    }

    @Override
    public void onDoWaterHeatRaised() {
        theDF.doWaterHeat();
    }

    @Override
    public void onDoCoffeeRaised() {
        theDF.doCoffee();
    }

    @Override
    public void onDoExpressoRaised() {
        theDF.doExpresso();
    }

    @Override
    public void onDoTeaRaised() {
        theDF.doTea();
    }

	@Override
	public void onDoWaterFlowRaised() {
		theDF.doWaterFlow();
	}

	@Override
	public void onDoPutCupRaised() {
		theDF.doPutCup();
		
	}

	@Override
	public void onDoCheckNFCRaised() {
		theDF.doCheckNFC();
		
	}

	@Override
	public void onDoAddSugarRaised() {
		theDF.doAddSugar();
		
	}

	@Override
	public void onDoDecrementRaised() {
		theDF.doDecrement();
		
	}

	@Override
	public void onDoNotifyRaised() {
		theDF.doNotify();
		
	}

    /*
    @Override
    public void onReadyRaised() {
        theDF.doReady();
    }
    */
}
