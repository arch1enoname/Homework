package adapter;

public class UsbAdapter {
    Card card;
    public UsbAdapter(Card card) {
        this.card = card;
    }

    public Usb getUsb() {
        return new Usb(card.info);
    }
}
