package org.example.gfgblr9.enums;

public enum LibraryUserRoles {
    CUSTOMER("Customer"),
    LIBRARIAN("Librarian"),
    ADMIN("Admin");


    private String value;
    LibraryUserRoles(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }

}
