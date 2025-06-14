package org.example;

import org.example.fsm.FiniteStateMachine;
import org.junit.jupiter.api.Test;

import static org.example.Constants.CLOSED;
import static org.example.Constants.CLOSE_DOOR;
import static org.example.Constants.LOCKED;
import static org.example.Constants.LOCK_DOOR;
import static org.example.Constants.OPEN;
import static org.example.Constants.OPEN_DOOR;
import static org.example.Constants.UNLOCK_DOOR;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DoorControllerTest {

    @Test
    void initial_state() {
        FiniteStateMachine<String, Integer> fsm = new FiniteStateMachine<>(CLOSED);
        assertEquals(CLOSED, fsm.currentState());
    }

    @Test
    void transition_from_open_to_closed() {
        FiniteStateMachine<String, String> fsm = new FiniteStateMachine<>(OPEN);
        fsm.add(OPEN, CLOSED, CLOSE_DOOR,
            e -> e.equals(CLOSE_DOOR),
            (s, e) -> System.out.println("Closing the door"));
        fsm.send(CLOSE_DOOR);
        assertEquals(CLOSED, fsm.currentState());
    }

    @Test
    void transition_from_closed_to_locked() {
        FiniteStateMachine<String, String> fsm = new FiniteStateMachine<>(CLOSED);
        fsm.add(CLOSED, LOCKED, LOCK_DOOR,
            e -> e.equals(LOCK_DOOR),
            (s, e) -> System.out.println("Locking the door"));
        fsm.send(LOCK_DOOR);
        assertEquals(LOCKED, fsm.currentState());
    }

    @Test
    void transition_from_locked_to_unlocked() {
        FiniteStateMachine<String, String> fsm = new FiniteStateMachine<>(LOCKED);
        fsm.add(LOCKED, CLOSED, UNLOCK_DOOR,
            e -> e.equals(UNLOCK_DOOR),
            (s, e) -> System.out.println("Unlocking the door"));
        fsm.send(UNLOCK_DOOR);
        assertEquals(CLOSED, fsm.currentState());
    }

    @Test
    void invalid_locked_to_open() {
        FiniteStateMachine<String, String> fsm = new FiniteStateMachine<>(LOCKED);
        fsm.add(LOCKED, CLOSED, UNLOCK_DOOR,
            e -> e.equals(UNLOCK_DOOR),
            (s, e) -> System.out.println("Unlocking the door"));
        fsm.send(OPEN_DOOR); //should not change state
        assertEquals(LOCKED, fsm.currentState());
    }

    @Test
    void invalid_open_to_lock() {
        FiniteStateMachine<String, String> fsm = new FiniteStateMachine<>(OPEN);
        fsm.add(CLOSED, LOCKED, LOCK_DOOR,
            e -> e.equals(LOCK_DOOR),
            (s, e) -> System.out.println("Locking the door"));
        fsm.send(LOCK_DOOR);// should not change state
        assertEquals(OPEN, fsm.currentState());
    }

    @Test
    void invalid_closed_to_unlock() {
        FiniteStateMachine<String, String> fsm = new FiniteStateMachine<>(CLOSED);
        fsm.add(LOCKED, CLOSED, UNLOCK_DOOR,
            e -> e.equals(UNLOCK_DOOR),
            (s, e) -> System.out.println("Unlocking the door"));
        fsm.send(UNLOCK_DOOR); // should not change state
        assertEquals(CLOSED, fsm.currentState());
    }
}
