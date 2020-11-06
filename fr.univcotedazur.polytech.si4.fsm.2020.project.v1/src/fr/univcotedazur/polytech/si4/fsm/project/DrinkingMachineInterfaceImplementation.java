package fr.univcotedazur.polytech.si4.fsm.project;

import fr.univcotedazur.polytech.si4.fsm.project.defaultsm.IDefaultSMStatemachine;

public class DrinkingMachineInterfaceImplementation implements IDefaultSMStatemachine.SCInterfaceListener {
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
    public void onDoResetTimerRaised() {
        theDF.doResetTimer();
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
}
