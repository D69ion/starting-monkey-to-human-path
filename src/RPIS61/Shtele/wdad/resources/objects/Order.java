package RPIS61.Shtele.wdad.resources.objects;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable{
    private Officiant officiant;
    private List<Item> items;
    private int totalCost;

    public Order(Officiant officiant, List<Item> items){
        this.items = items;
        this.officiant = officiant;
        this.totalCost = 0;
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

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return officiant.toString();
    }
}
