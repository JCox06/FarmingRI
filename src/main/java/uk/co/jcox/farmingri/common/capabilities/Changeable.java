package uk.co.jcox.farmingri.common.capabilities;


/**
 * Standard interface to just call back to a function that takes in 0 parameters and 0 args
 * Usually used to mark an object dirty allowing NBT data to be saved to disc
 */
@FunctionalInterface
public interface Changeable {

    void invoke();
}
