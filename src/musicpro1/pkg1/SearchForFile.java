/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package musicpro1.pkg1;

import java.io.File;

/**
 *
 * @author Developer
 */
public class SearchForFile 
{
    public static File searchLocation;
    public static String files;
    public static void searchTrack(String trackName)
    {
        searchLocation = new File("C:");
        
        if(searchLocation.isDirectory())
        {
            files = searchLocation.getAbsolutePath();
        }
    }
    public void findFiles(File f) 
     {     
         if (f.isDirectory()) 
         {   
             /*stk = getFiles(f);    
             System.out.println(stk + f.getName());  
             File files[] = f.listFiles();    
             for (File ff :files)
             {    
                 findFiles(ff);          
             } */
         }
         else if (f.isFile()) 
         {   
            /* System.out.println(stk + "  " + f.getName());  

             track = f.toString().endsWith("mp3")+"";
             if(track.equals("true"))
             {
                 MusicProComponents.listModel.addElement(f.getName());
                 MusicProComponents.listModel2.addElement(f.getAbsoluteFile());
             }*/
         }
     } 
}
