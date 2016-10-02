package com.goit.homeworks;

/**
 * Created by SSuchock on 27.09.16.
 */
public class Type {
    //boolean isCorrectArgument(String argument);
    private String typePattern;
    private String name;

    public Type(String name, String typePattern) {
        this.typePattern = typePattern;
        this.name = name;
    }

    public String getTypePattern() {
        return typePattern;
    }

    public void setTypePattern(String typePattern) {
        this.typePattern = typePattern;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Type type = (Type) o;

        if (typePattern != null ? !typePattern.equals(type.typePattern) : type.typePattern != null) return false;
        return name != null ? name.equals(type.name) : type.name == null;

    }

    @Override
    public int hashCode() {
        int result = typePattern != null ? typePattern.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
