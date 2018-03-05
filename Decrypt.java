package ko.alex.enigmachat3;


public class Decrypt {

    public String Decrypt(String key, int seedNum) {

        String result = "";
        int len = key.length();
        char ch;
        int newSeed = seedNum % 101;

        try {

            for(int i = 0; i < len; i++){
                ch = key.charAt(i);
                ch -= newSeed;
                result += ch;
                newSeed += 1;
            }

        } catch (Exception io) {
            io.printStackTrace();
        }
        return result;
    }

}

