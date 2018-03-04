package ko.alex.enigmachat3;


public class Decrypt {

    public String Decrypt(String key, int seedNum) {

        String result = "";
        int len = key.length();
        char ch;

        try {

            for(int i = 0; i < len; i++){
                ch = key.charAt(i);
                ch -= seedNum;
                result += ch;
                seedNum += 1;
            }

        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
        return result;
    }

}

