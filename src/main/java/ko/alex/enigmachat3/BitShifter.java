package ko.alex.enigmachat3;



public class BitShifter {

    public String Encrypt(String key, long seedNum) {

        String result = "";
        int len = key.length();
        char ch;
        long newSeed = seedNum % 101;

        try {

            for(int i = 0; i < len; i++){
                ch = key.charAt(i);
                ch += newSeed;
                result += ch;
                newSeed += i+1;
            }

        } catch (Exception io) {
            io.printStackTrace();
        }
        return result;
    }

    public String Decrypt(String key, long seedNum) {

        String result = "";
        int len = key.length();
        char ch;
        long newSeed = seedNum % 101;

        try {

            for(int i = 0; i < len; i++){
                ch = key.charAt(i);
                ch -= newSeed;
                result += ch;
                newSeed += i+1;
            }

        } catch (Exception io) {
            io.printStackTrace();
        }
        return result;
    }


}
