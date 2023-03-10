package POJO;

import java.util.ArrayList;

public class Card {
    public class Data {
        public boolean isCard;
        public String userPass;
        public String userLogin;
        public String locale;
    }

    public class Root {
        public ArrayList<Data> data;
    }

}
