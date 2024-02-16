package cz.itnetwork;

public class Entry {
    private static int currentIndex = 0;
    public static final String[] attributeNames = { "CLIENT NAME", "ORDERED GOODS", "AMOUNT", "PRICE", "NOTE", "STATUS" };
    private final int uniqueIndex; // staring with 1, not 0 (because ++currentIndex)
    private String name;
    private String goods;
    private String amount;
    private String price;
    private String note;
    private String status; // e.g. ordered, paid, delivered, whatever - not making ENUM
    // bother with timestamps later...
    private boolean isDeleted;

    // les getteurs obligatoires
    public String[] getReadableAttributes() {
        return new String[]{ name, goods, amount, price, note, status };
    }

    public int getUniqueIndex() {
        return uniqueIndex;
    }

    // obsolete constructor
    public Entry(String name, String goods, String amount, String price, String note, String status) {
        uniqueIndex = ++currentIndex;
        this.name = name;
        this.goods = goods;
        this.amount = amount;
        this.price = price;
        this.note = note;
        this.status = status;
        isDeleted = false;
    }

    // current constructor
    public Entry(String[] attributeValues) {
        uniqueIndex = ++currentIndex;
        name = attributeValues[0];
        goods = attributeValues[1];
        amount = attributeValues[2];
        price = attributeValues[3];
        note = attributeValues[4];
        status = attributeValues[5];
        isDeleted = false;
    }

    public void modifyEntry(String[] attributeValues) {
        name = attributeValues[0];
        goods = attributeValues[1];
        amount = attributeValues[2];
        price = attributeValues[3];
        note = attributeValues[4];
        status = attributeValues[5];
    }

    public void makeDeleted() {
        isDeleted = true;
    }

    @Override
    public String toString() {
        return
            "   |Order ID|: " + uniqueIndex + " |Customer|: " + name + "\n" +
            "   |Goods|: " + goods + " |Amount|: " + amount + " |Price|: " + price + " |Note|: " + note + " |Status|: " + status + "\n";
    }
}
