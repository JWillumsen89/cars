package dat3.experiments;
//Never use anything in here for real
public class SimpleSanitizer {
  public static String simpleSanitize(String s){
    return s.replaceAll("<[^>]*>","");
  }
}

