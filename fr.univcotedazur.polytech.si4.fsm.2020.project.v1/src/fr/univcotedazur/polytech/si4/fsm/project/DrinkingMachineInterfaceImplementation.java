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

	@Override
	public void onSetClassicSlidersRaised() {
		theDF.setClassicSliders();
		
	}

	@Override
	public void onSetSoupSlidersRaised() {
		theDF.setSoupSliders();
		
	}

	@Override
	public void onSetIceTeaSlidersRaised() {
		theDF.setIceTeaSliders();
		
	}

	@Override
	public void onDoSoupRaised() {
		theDF.doSoup();
		
	}

	@Override
	public void onDoIceTeaRaised() {
		theDF.doIceTea();
		
	}

	@Override
	public void onDoAddSpicesRaised() {
		theDF.doAddSpices();
		
	}

	@Override
	public void onDoCoolingRaised() {
		theDF.doCooling();
		
	}

	@Override
	public void onDoPayRaised() {
		theDF.doPay();
		
	}

	@Override
	public void onValidateStepRaised() {
		theDF.validateStep();
		
	}

	@Override
	public void onDoInfusionRaised() {
		theDF.doInfusion();
		
	}

	@Override
	public void onDoCroutonRaised() {
		theDF.doCrouton();
		
	}

	@Override
	public void onAddMilkRaised() {
		theDF.addMilk();
		
	}

	@Override
	public void onAddIceCreamRaised() {
		theDF.addIceCream();
		
	}

    /*
    @Override
    public void onReadyRaised() {
        theDF.doReady();
    }
    */
}
