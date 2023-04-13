import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler1 implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> strings = new ArrayList<String>();
    String forReturn = "";

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("Current List: %d", strings);
        } else if (url.getPath().contains("/search")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")){
                forReturn = "";
                for (String x: strings){
                    if (x.contains(parameters[1])){
                        forReturn += x + ", ";
                    }
                }
                forReturn = forReturn.substring(0,forReturn.length()-2);
            }
            return forReturn;
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    strings.add(parameters[1]);
                    return String.format("%s added to string array! It's now %d", parameters[1], strings);
                }
            }
            return "404 Not Found!";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler1());
    }
}

