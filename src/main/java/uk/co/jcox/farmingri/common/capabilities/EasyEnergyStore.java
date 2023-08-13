package uk.co.jcox.farmingri.common.capabilities;

import net.minecraftforge.energy.EnergyStorage;

public class EasyEnergyStore extends EnergyStorage {

    private final Changeable onChanged;

    public EasyEnergyStore(int capacity, Changeable onChanged) {
        super(capacity);
        this.onChanged = onChanged;
    }


    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        onChanged.invoke();
        return super.extractEnergy(maxExtract, simulate);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        onChanged.invoke();
        return super.receiveEnergy(maxReceive, simulate);
    }
}
