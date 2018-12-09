package RPIS61.Shtele.wdad.resources.objects;

import java.util.List;

public class Order {
    private Officiant officiant;
    private List<Item> items;

    public Order(Officiant officiant, List<Item> items){
        this.items = items;
        this.officiant = officiant;
    }

    public Officiant getOfficiant() {
        return officiant;
    }

    public void setOfficiant(Officiant officiant) {
        this.officiant = officiant;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
