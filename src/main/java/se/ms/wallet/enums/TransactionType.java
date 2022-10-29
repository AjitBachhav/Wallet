package se.ms.wallet.enums;

public enum TransactionType {
    DEBIT(-1), CREDIT(1);

    private final int signum;

    TransactionType(final int signum) {
        this.signum = signum;
    }

    public int getSignum() {
        return signum;
    }
}
