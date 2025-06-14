package org.example;

import org.example.fsm.FiniteStateMachine;

import static org.example.Constants.CLOSED;
import static org.example.Constants.CLOSE_DOOR;
import static org.example.Constants.LOCKED;
import static org.example.Constants.LOCK_DOOR;
import static org.example.Constants.OPEN;
import static org.example.Constants.OPEN_DOOR;
import static org.example.Constants.UNLOCK_DOOR;

public class DoorController {
    public static void main( String[] args ) {
        FiniteStateMachine<String, String> fsm = new FiniteStateMachine<>(CLOSED);

        fsm.add(CLOSED, OPEN, OPEN_DOOR,
            e -> e.equals(OPEN_DOOR),
            (s, e) -> System.out.println("Opening the door"));

        fsm.add(OPEN, CLOSED, CLOSE_DOOR,
            e -> e.equals(CLOSE_DOOR),
            (s, e) -> System.out.println("Closing the door"));

        fsm.add(CLOSED, LOCKED, LOCK_DOOR,
            e -> e.equals(LOCK_DOOR),
            (s, e) -> System.out.println("Locking the door"));

        fsm.add(LOCKED, CLOSED, UNLOCK_DOOR,
            e -> e.equals(UNLOCK_DOOR),
            (s, e) -> System.out.println("Unlocking the door"));

        fsm.send(OPEN_DOOR);// CLOSED => OPEN
        fsm.send(CLOSE_DOOR);// OPEN => CLOSED
        fsm.send(LOCK_DOOR);// CLOSED => LOCKED
        fsm.send(UNLOCK_DOOR);// LOCKED => CLOSED
        fsm.send(OPEN_DOOR);// CLOSED => OPEN

        fsm.send(LOCK_DOOR);// invalid
    }
}
