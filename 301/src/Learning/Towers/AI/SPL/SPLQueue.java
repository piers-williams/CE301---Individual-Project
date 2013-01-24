package Learning.Towers.AI.SPL;

import Learning.Towers.AI.SPL.Orders.AttackOrder;
import Learning.Towers.AI.SPL.Orders.DefendOrder;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Stores queues for the different kinds of SPL instructions available
 * Should handle multiple thread access
 */
public class SPLQueue {

    private ArrayList<AttackOrder> attackOrders;
    private final Object _attackOrders = new Object();

    private ArrayList<DefendOrder> defendOrders;
    private final Object _defendOrders = new Object();

    public SPLQueue() {
        attackOrders = new ArrayList<>();
        defendOrders = new ArrayList<>();
    }

    public void addAttackOrder(AttackOrder order) {
        synchronized (_attackOrders) {
            attackOrders.add(order);
            Collections.sort(attackOrders);
        }
    }

    public void addDefendOrder(DefendOrder order) {
        synchronized (_defendOrders) {
            defendOrders.add(order);
            Collections.sort(defendOrders);
        }
    }

    public boolean hasAttackOrder() {
        Boolean has;
        synchronized (_attackOrders) {
            has = attackOrders.size() >= 1;
        }
        return has;
    }

    public boolean hasDefendOrder() {
        Boolean has;
        synchronized (_defendOrders) {
            has = defendOrders.size() >= 1;
        }
        return has;
    }

    public AttackOrder getNextAttackOrder() {
        AttackOrder returnedOrder;
        synchronized (_attackOrders) {
            if (attackOrders.size() < 1) throw new IllegalStateException("No Attack Orders");
            returnedOrder = attackOrders.get(0);
            attackOrders.remove(0);
        }
        return returnedOrder;
    }

    public DefendOrder getNextDefendOrder() {
        DefendOrder returnedOrder;
        synchronized (_defendOrders) {
            if (defendOrders.size() < 1) throw new IllegalStateException("No Defend Orders");
            returnedOrder = defendOrders.get(0);
            defendOrders.remove(0);
        }
        return returnedOrder;
    }


}
