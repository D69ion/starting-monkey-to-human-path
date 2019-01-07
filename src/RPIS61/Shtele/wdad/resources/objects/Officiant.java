package RPIS61.Shtele.wdad.resources.objects;

import java.io.Serializable;

public class Officiant implements Serializable{
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

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof Officiant)) return false;
        Officiant officiant = (Officiant) obj;
        return this.firstName.equals(officiant.firstName) && this.secondName.equals(officiant.secondName);
    }
}
