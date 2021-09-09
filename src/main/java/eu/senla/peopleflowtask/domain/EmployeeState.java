package eu.senla.peopleflowtask.domain;

import lombok.Getter;

public enum EmployeeState {
    ADDED,
    IN_CHECK,
    APPROVED,
    ACTIVE;

    //public abstract EmployeeState nextState();
    //public abstract EmployeeState previousState();

    /*public boolean canChangeState(EmployeeState target) {
        return this.nextState().equals(target) || this.previousState().equals(target);
    }*/
}
