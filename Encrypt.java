package ko.alex.enigmachat3;



public class Encrypt {

    String Newstr="";

    public String Encrypt(String string) {

        // http://www.c-sharpcorner.com/UploadFile/9a9e6f/how-to-encrypt-and-decrypt-the-string-in-java-without-using/

        try {

            for (int i = 0; i < string.length(); i++) {
                char ch = Character.toLowerCase(string.charAt(i));
                switch (ch) {
                    case 'a':
                        Newstr = Newstr + "{";
                        break;
                    case 'b':
                        Newstr = Newstr + "}";
                        break;
                    case 'c':
                        Newstr = Newstr + "#";
                        break;
                    case 'd':
                        Newstr = Newstr + "~";
                        break;
                    case 'e':
                        Newstr = Newstr + "+";
                        break;
                    case 'f':
                        Newstr = Newstr + "-";
                        break;
                    case 'g':
                        Newstr = Newstr + "*";
                        break;
                    case 'h':
                        Newstr = Newstr + "@";
                        break;
                    case 'i':
                        Newstr = Newstr + "/";
                        break;
                    case 'j':
                        Newstr = Newstr + "\\";
                        break;
                    case 'k':
                        Newstr = Newstr + "?";
                        break;
                    case 'l':
                        Newstr = Newstr + "$";
                        break;
                    case 'm':
                        Newstr = Newstr + "!";
                        break;
                    case 'n':
                        Newstr = Newstr + "^";
                        break;
                    case 'o':
                        Newstr = Newstr + "(";
                        break;
                    case 'p':
                        Newstr = Newstr + ")";
                        break;
                    case 'q':
                        Newstr = Newstr + "<";
                        break;
                    case 'r':
                        Newstr = Newstr + ">";
                        break;
                    case 's':
                        Newstr = Newstr + "=";
                        break;
                    case 't':
                        Newstr = Newstr + ";";
                        break;
                    case 'u':
                        Newstr = Newstr + ",";
                        break;
                    case 'v':
                        Newstr = Newstr + "_";
                        break;
                    case 'w':
                        Newstr = Newstr + "[";
                        break;
                    case 'x':
                        Newstr = Newstr + "]";
                        break;
                    case 'y':
                        Newstr = Newstr + ":";
                        break;
                    case 'z':
                        Newstr = Newstr + "\"";
                        break;



                    case ' ':
                        Newstr = Newstr + " ";
                        break;
                    case '.':
                        Newstr = Newstr + '3';
                        break;





                    case '1':
                        Newstr = Newstr + "r";
                        break;
                    case '2':
                        Newstr = Newstr + "k";
                        break;
                    case '3':
                        Newstr = Newstr + "b";
                        break;
                    case '4':
                        Newstr = Newstr + "e";
                        break;
                    case '5':
                        Newstr = Newstr + "q";
                        break;
                    case '6':
                        Newstr = Newstr + "h";
                        break;
                    case '7':
                        Newstr = Newstr + "u";
                        break;
                    case '8':
                        Newstr = Newstr + "y";
                        break;
                    case '9':
                        Newstr = Newstr + "w";
                        break;
                    case '0':
                        Newstr = Newstr + "z";
                        break;





                    default:
                        Newstr = Newstr + "0";
                        break;
                }
            }
        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
        return Newstr;

    }
}
