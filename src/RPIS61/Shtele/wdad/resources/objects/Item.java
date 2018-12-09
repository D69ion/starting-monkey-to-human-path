package RPIS61.Shtele.wdad.resources.objects;

public class Item {
    private String name;
    private int cost;

    public Item(String name, int cost){
        this.cost = cost;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
