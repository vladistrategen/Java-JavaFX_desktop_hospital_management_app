package domain;

import java.io.Serializable;

public interface Entity extends Serializable {
    int getID();
    void setID(int ID);

    String toString();
}
