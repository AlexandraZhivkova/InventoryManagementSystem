public class Payment {
    private String paymentType;
    private boolean processed = false;

    public Payment(String paymentType) {
        this.paymentType = paymentType;
    }

    @Override
    public String toString() {
        return paymentType;
    }

    public void processPayment() {
        processed = true;
    }

    public boolean isProcessed() {
        return processed;
    }
}
