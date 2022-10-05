import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    int num = 0;

    ArrayList<String> items = new ArrayList<String>();
	 
    public String handleRequest(URI url) {
        if (url.getPath().equals("/")){
            return String.format("No Query!");
        } else if (url.getPath().contains("/add")){
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                items.add(parameters[1]);
                String itemList = String.join(", ", items);
                return String.format("All Words: %s", itemList);
            }
        } else if (url.getPath().contains("/search")){
            ArrayList<String> results = new ArrayList<String>();
            String[] parameters = url.getQuery().split("=");
            String search = parameters[1];
            for(int j = 0; j < items.size(); j += 1){
                if(items.get(j).contains(search)){
                    results.add(items.get(j));
                }
            }
            String resultList = String.join(", ", results);
            return String.format("Search Results: %s", resultList);
        }
        
        else {
            return String.format("error");
        }



        if (url.getPath().equals("/")) {
            return String.format("Number: %d", num);
        } else if (url.getPath().equals("/increment")) {
            num += 1;
            return String.format("Number incremented!");
         
        }
        
            else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("count")) {
                    num += Integer.parseInt(parameters[1]);
                    return String.format("Number increased by %s! It's now %d", parameters[1], num);
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

        Server.start(port, new Handler());
    }
}