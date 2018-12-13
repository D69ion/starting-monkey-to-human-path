package RPIS61.Shtele.wdad.resources.objects;

public class Officiant {
    private String firstName;
    private String secondName;

    public Officiant(String firstName, String secondName){
        this.firstName=firstName;
        this.secondName=secondName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    @Override
    public String toString() {
        return firstName + ' ' + secondName;
    }
}
