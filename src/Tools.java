import java.io.File;
import java.util.Scanner;

class Tools
{
    // define your variables, e.g., myDictioary
    private static RBTDictionary myDictionary;
    private static RedBlackBST tablelist;
    public static int wordCount(String text)
    {
        int count = 0;
        Scanner sc = new Scanner(text);
        while (sc.hasNext() && sc.next() != null)
            count++;
        return count;
    }
    
        
    public static String spellCheck(String text) throws Exception
    {
        String wrongWords = ""; 
        tablelist = new RedBlackBST();
        myDictionary = new RBTDictionary();
        File file = new File("dictionary.txt");
        Scanner in = new Scanner(file);
        while(in.hasNextLine())
            myDictionary.insert(in.nextLine());
        
   	// create your dictionary (here you will insert words into dictionary)     
        System.out.println("dictionary created");
        System.out.println("spell checking ...");
           
         
        Scanner sc = new Scanner(text);
        while (sc.hasNext())
        {
            String s = sc.next().toLowerCase();
            if(s.length()>=2)
                s = removePunct(s);
            // handle punctuation marks 
            if(!tablelist.contains(s)){
                if(myDictionary.find(s)){
                    tablelist.insert(s,1);
                }
                else{
                    tablelist.insert(s,0);
                    wrongWords += s + "; "; 
                }
            }                     
        }        
        return wrongWords; 
       
    }

    private static String removePunct(String text){
        if((text.charAt(0)<97 || text.charAt(0)>122) && text.length()>=2)
            text = text.substring(1);
        else if((text.charAt(text.length()-2)<97 || text.charAt(text.length()-2)>122) && text.length()>=3 )
            text = text.substring(0,text.length()-2);
        else if((text.charAt(text.length()-1) < 97 || text.charAt(text.length()-1)>122) && text.length()>=2)
            text = text.substring(0,text.length()-1);
        return text;
    }
}
