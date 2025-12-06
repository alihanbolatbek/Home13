// ===== Интерфейс состояния =====
interface State {
    void selectTicket(TicketMachine machine);
    void insertMoney(TicketMachine machine, int amount);
    void dispenseTicket(TicketMachine machine);
    void cancelTransaction(TicketMachine machine);
}


// ===== Контекст =====
class TicketMachine {
    int ticketPrice = 10;
    int amountInserted = 0;

    State state;

    public TicketMachine() {
        state = new IdleState();  // начальное состояние
    }

    public void selectTicket() {
        state.selectTicket(this);
    }

    public void insertMoney(int amount) {
        state.insertMoney(this, amount);
    }

    public void dispenseTicket() {
        state.dispenseTicket(this);
    }

    public void cancelTransaction() {
        state.cancelTransaction(this);
    }
}


// ===== IdleState =====
class IdleState implements State {

    @Override
    public void selectTicket(TicketMachine machine) {
        System.out.println("Билет выбран. Ожидание внесения денег...");
        machine.state = new WaitingForMoneyState();
    }

    @Override
    public void insertMoney(TicketMachine machine, int amount) {
        System.out.println("Сначала выберите билет!");
    }

    @Override
    public void dispenseTicket(TicketMachine machine) {
        System.out.println("Сначала выберите билет!");
    }

    @Override
    public void cancelTransaction(TicketMachine machine) {
        System.out.println("Нет активной транзакции.");
    }
}


// ===== WaitingForMoneyState =====
class WaitingForMoneyState implements State {

    @Override
    public void selectTicket(TicketMachine machine) {
        System.out.println("Билет уже выбран. Внесите деньги.");
    }

    @Override
    public void insertMoney(TicketMachine machine, int amount) {
        machine.amountInserted += amount;
        System.out.println("Внесено: " + machine.amountInserted);

        if (machine.amountInserted >= machine.ticketPrice) {
            machine.state = new MoneyReceivedState();
        }
    }

    @Override
    public void dispenseTicket(TicketMachine machine) {
        System.out.println("Недостаточно денег.");
    }

    @Override
    public void cancelTransaction(TicketMachine machine) {
        System.out.println("Транзакция отменена.");
        machine.amountInserted = 0;
        machine.state = new TransactionCanceledState();
    }
}


// ===== MoneyReceivedState =====
class MoneyReceivedState implements State {

    @Override
    public void selectTicket(TicketMachine machine) {
        System.out.println("Билет уже выбран и деньги внесены.");
    }

    @Override
    public void insertMoney(TicketMachine machine, int amount) {
        machine.amountInserted += amount;
        System.out.println("Дополнительно внесено: " + amount);
    }

    @Override
    public void dispenseTicket(TicketMachine machine) {
        System.out.println("Билет выдан. Спасибо за покупку!");
        machine.amountInserted = 0;
        machine.state = new TicketDispensedState();
    }

    @Override
    public void cancelTransaction(TicketMachine machine) {
        System.out.println("Транзакция отменена. Возврат денег.");
        machine.amountInserted = 0;
        machine.state = new TransactionCanceledState();
    }
}


// ===== TicketDispensedState =====
class TicketDispensedState implements State {

    @Override
    public void selectTicket(TicketMachine machine) {
        System.out.println("Начните новую транзакцию.");
    }

    @Override
    public void insertMoney(TicketMachine machine, int amount) {
        System.out.println("Начните новую транзакцию.");
    }

    @Override
    public void dispenseTicket(TicketMachine machine) {
        System.out.println("Билет уже выдан.");
    }

    @Override
    public void cancelTransaction(TicketMachine machine) {
        System.out.println("Невозможно отменить. Билет уже выдан.");
    }
}


// ===== TransactionCanceledState =====
class TransactionCanceledState implements State {

    @Override
    public void selectTicket(TicketMachine machine) {
        System.out.println("Начинаем новую транзакцию...");
        machine.state = new IdleState();
    }

    @Override
    public void insertMoney(TicketMachine machine, int amount) {
        System.out.println("Транзакция отменена. Сначала выберите билет.");
    }

    @Override
    public void dispenseTicket(TicketMachine machine) {
        System.out.println("Транзакция отменена. Невозможно выдать билет.");
    }

    @Override
    public void cancelTransaction(TicketMachine machine) {
        System.out.println("Транзакция уже отменена.");
    }
}


// ===== Main =====
public class Task2 {
    public static void main(String[] args) {

        TicketMachine machine = new TicketMachine();

        machine.selectTicket();       // Выбираем билет
        machine.insertMoney(5);       // Вносим часть суммы
        machine.insertMoney(5);       // Вносим оставшуюся сумму
        machine.dispenseTicket();     // Получаем билет
        machine.selectTicket();       // Начинаем новую транзакцию
    }
}
