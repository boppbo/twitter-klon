package de.hska.twitterklon.api.transferobjects;

import de.hska.twitterklon.api.transferobjects.validators.ValidUserName;

public class UserTO {
    
    @ValidUserName
    private String name;

    public UserTO() {
    }

    public UserTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserTO{" +
                "name='" + name + '\'' +
                '}';
    }
}
