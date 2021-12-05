package cs146F21.lenaqvi.project4;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.File;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

class RBTTester {

	@Test
	@Disabled
	void test() {
		RedBlackTree rbt = new RedBlackTree();
        rbt.insert("D");
        rbt.insert("B");
        rbt.insert("A");
        rbt.insert("C");
        rbt.insert("F");
        rbt.insert("E");
        rbt.insert("H");
        rbt.insert("G");
        rbt.insert("I");
        rbt.insert("J");
        assertEquals("DBACFEHGIJ", makeString(rbt));
        String str =    "Color: 1, Key:D Parent: \n"+
                        "Color: 1, Key:B Parent: D\n"+
                        "Color: 1, Key:A Parent: B\n"+
                        "Color: 1, Key:C Parent: B\n"+
                        "Color: 1, Key:F Parent: D\n"+
                        "Color: 1, Key:E Parent: F\n"+
                        "Color: 0, Key:H Parent: F\n"+
                        "Color: 1, Key:G Parent: H\n"+
                        "Color: 1, Key:I Parent: H\n"+
                        "Color: 0, Key:J Parent: I\n";
		assertEquals(str, makeStringDetails(rbt));
            
	}
	
	@Test
	@Disabled
	void testFixTree() {
		RedBlackTree rbt = new RedBlackTree();
		rbt.insert("D");
		rbt.insert("B");
		rbt.insert("F");
		rbt.insert("E");
		assertEquals("DBFE", makeString(rbt));
		
		String info = "Color: 1, Key:D Parent: \n"+
					  "Color: 1, Key:B Parent: D\n"+
					  "Color: 1, Key:F Parent: D\n"+
					  "Color: 0, Key:E Parent: F\n";
	    assertEquals(info, makeStringDetails(rbt));
	}
	
	
	@Test
	@Disabled
    //Test the Red Black Tree
	public void addTest() {
      RedBlackTree rbt = new RedBlackTree();
      rbt.insert("D"); //root
      rbt.insert("B"); //goes left of D
      
      rbt.insert("A"); //goes left of B
      
      rbt.insert("C"); //goes right of 

      rbt.insert("F"); // right of D

      assertEquals("DBACF", makeString(rbt));
      
      RedBlackTree.Node result = rbt.lookup("D");
     
      assertEquals(result.getKey(), "D");
      
      RedBlackTree.Node result1 = rbt.lookup("Z");
      
      assertNull(result1);
	}
	
	@Test
	@Disabled
	void getSiblingTest() {
		RedBlackTree rbt = new RedBlackTree();
		rbt.insert("D");
		rbt.insert("B");
		rbt.insert("A");
		rbt.insert("C");
		rbt.insert("F");
		rbt.insert("E");
		rbt.insert("H");
		rbt.insert("G");
		rbt.insert("I");
		rbt.insert("J");

		RedBlackTree.Node sibling_A = rbt.getSibling(rbt.lookup("A"));
		assertEquals(sibling_A.key, "C");

		RedBlackTree.Node sibling_F = rbt.getSibling(rbt.lookup("F"));
		assertEquals(sibling_F.key, "B");

		RedBlackTree.Node sibling_J = rbt.getSibling(rbt.lookup("J"));
		//can't call key on a null pointer
		assertNull(sibling_J);
	}
	
	@Test
	@Disabled
	void getAuntTest() {
		RedBlackTree rbt = new RedBlackTree();
		rbt.insert("D");
		rbt.insert("B");
		rbt.insert("A");
		rbt.insert("C");
		rbt.insert("F");
		rbt.insert("E");
		rbt.insert("H");
		rbt.insert("G");
		rbt.insert("I");
		rbt.insert("J");

		RedBlackTree.Node aunt_A = rbt.getAunt(rbt.lookup("A"));
		assertEquals(aunt_A.key, "F");

		RedBlackTree.Node aunt_H = rbt.getAunt(rbt.lookup("H"));
		assertEquals(aunt_H.key, "B");

		RedBlackTree.Node aunt_J = rbt.getAunt(rbt.lookup("J"));
		assertEquals(aunt_J.key, "G");

		RedBlackTree.Node aunt_F = rbt.getAunt(rbt.lookup("F"));
		//can't call key on a null pointer
		assertNull(aunt_F);
	}
	
	@Test
	@Disabled
	void getGrandparentTest() {
		RedBlackTree rbt = new RedBlackTree();
		rbt.insert("D");
		rbt.insert("B");
		rbt.insert("A");
		rbt.insert("C");
		rbt.insert("F");
		rbt.insert("E");
		rbt.insert("H");
		rbt.insert("G");
		rbt.insert("I");
		rbt.insert("J");

	    RedBlackTree.Node grandparent_E = rbt.getGrandparent(rbt.lookup("E"));
	    assertEquals(grandparent_E.key, "D");
	
	    RedBlackTree.Node grandparent_J = rbt.getGrandparent(rbt.lookup("J"));
	    assertEquals(grandparent_J.key, "H");
	
	    RedBlackTree.Node grandparent_B = rbt.getGrandparent(rbt.lookup("B"));
	    assertNull(grandparent_B);
	}
	
	@Test
	@Disabled
	void rotateLeftTest() {
		RedBlackTree rbt = new RedBlackTree();
		rbt.insert("D");
		rbt.insert("B");
		rbt.insert("F");
		rbt.insert("E");
		rbt.insert("I");
		rbt.insert("H");
		rbt.insert("G");
		rbt.rotateLeft(rbt.lookup("F"));
		RedBlackTree.Node node_F = rbt.lookup("F");
		assertEquals(node_F.parent.key, "I");
		assertEquals(node_F.rightChild.key, "H");  
    }
	
	
	@Test
	@Disabled
	void rotateRightTest() {
		RedBlackTree rbt = new RedBlackTree();
		rbt.insert("I");
		rbt.insert("K");
		rbt.insert("G");
		rbt.insert("H");
		rbt.insert("D");
		rbt.insert("E");
		rbt.insert("F");
		rbt.rotateRight(rbt.lookup("G"));
		RedBlackTree.Node node_G = rbt.lookup("G");
		assertEquals(node_G.parent.key, "D");
		assertEquals(node_G.leftChild.key, "E");  
    }
	
	
	public RedBlackTree makeRBTdictionary() throws IOException{

		RedBlackTree rbt = new RedBlackTree();
		FileReader fr = new FileReader("dictionary.txt");
		BufferedReader br = new BufferedReader(fr);

		String line = br.readLine();

		while (line != null) {
			rbt.insert(line);
			line = br.readLine();
		}
		br.close();
		fr.close();

		return rbt;
	}
	
	public ArrayList<String> readPoem() throws IOException {
		FileReader read = new FileReader("message.txt");
		//FileWriter out = new FileWriter(new File("poem_words.txt"));
		BufferedReader bRead = new BufferedReader(read);
		
		String line = bRead.readLine();
		String[] tokens;
		ArrayList<String> words = new ArrayList<String>();
		
		while (line != null) {
			tokens = line.split(" "); // ["a", "aa"]
			for (int i = 0; i < tokens.length; i++) {
				tokens[i] = tokens[i].toLowerCase().replaceAll("[^a-zA-Z]", "");
				words.add(tokens[i]);
			}
			
			line = bRead.readLine();
;		}
		
		bRead.close();
		read.close();
		
		return words;
	}
	
	@Test
	public void testSpeller() {
		try {
			RedBlackTree dictionary = makeRBTdictionary();
			ArrayList<String> words = readPoem();
			ArrayList<String> missingWords = new ArrayList<String>();
			
			for (String word : words) {
				if (dictionary.lookup(word) == null) {
					missingWords.add(word);
				}
			}
			
			for (String w : missingWords) {
				System.out.println(w + "\n");
			}
		}
		catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		
		
		
	}
	//add tester for spell checker
    
    public static String makeString(RedBlackTree t) {
       
    	class MyVisitor implements RedBlackTree.Visitor {
    		String result = "";
		    public void visit(RedBlackTree.Node n) {
		        result = result + n.key;
		    }
    	};
    	
    	MyVisitor v = new MyVisitor();
	    t.preOrderVisit(v);
	    return v.result;
    }

    public static String makeStringDetails(RedBlackTree t) {
	
    	class MyVisitor implements RedBlackTree.Visitor {
    		String result = "";
    		public void visit(RedBlackTree.Node n) {
    			if (n.parent == null)
    				result = result +"Color: "+n.color+", Key:"+n.key+" Parent: \n";
    			else if(!(n.key).equals(""))
    				result = result +"Color: "+n.color+", Key:"+n.key+" Parent: "+n.parent.key+"\n";
             
    		}
    	};
    	
    	MyVisitor v = new MyVisitor();
        t.preOrderVisit(v);
        return v.result;
    }
}
