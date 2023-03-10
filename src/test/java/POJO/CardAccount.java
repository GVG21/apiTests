package POJO;

public class CardAccount {
    public class Data{
        public String order_id;
        public String message_id;
    }

    public class Root{
        public boolean isCardAccount;
        public Data data;
        public boolean isRevaluation;
        public int userId;
        public String userLogin;
        public int[] docIds;
        public String locale;
    }
}
