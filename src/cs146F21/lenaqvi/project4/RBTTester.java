package cs146F21.lenaqvi.project4;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


/**
 * Unit tests for RedBlackTree class
 * @author Andy Qui Le and Mohammad Naqvi
 *
 */
class RBTTester {

	@Test
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
	public void addTest() {
      RedBlackTree rbt = new RedBlackTree();
      rbt.insert("D"); //root
      rbt.insert("B"); //goes left of D
      
      rbt.insert("A"); //goes left of B
      
      rbt.insert("C"); //goes right of 

      rbt.insert("F"); // right of D

      rbt.insert("E");
      
      assertEquals("BADCFE", makeString(rbt));
	}
	
	@Test
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
		assertEquals(node_F.parent.key, "H");
		assertEquals(node_F.rightChild.key, "G");  
    }
	
	
	@Test
	void rotateRightTest() {
		RedBlackTree rbt = new RedBlackTree();
		rbt.insert("D");
		rbt.insert("B");
		rbt.insert("F");
		rbt.insert("E");
		rbt.insert("I");
		rbt.insert("H");
		rbt.insert("G");

		rbt.rotateRight(rbt.lookup("F"));
		RedBlackTree.Node node_F = rbt.lookup("F");
		assertEquals(node_F.parent.key, "E");
		assertEquals(node_F.rightChild.key, "H"); 
    }
	
	
	private RedBlackTree makeRBTdictionary() throws IOException{

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
	
	private ArrayList<String> readPoem() throws IOException {
		FileReader read = new FileReader("message.txt");
		
		BufferedReader bRead = new BufferedReader(read);
		
		String line = bRead.readLine();
		String[] tokens;
		ArrayList<String> words = new ArrayList<String>();
		
		while (line != null) {
			tokens = line.split(" ");
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
			double createDictStart = System.nanoTime();
			RedBlackTree dictionary = makeRBTdictionary();
			double createDictTotalTime = System.nanoTime() - createDictStart;
			
			ArrayList<String> words = readPoem();
			ArrayList<String> missingWords = new ArrayList<String>();
			
			double lookUpTotalTime = 0;
			
			for (String word : words) {
				
				double lookUpStart = System.nanoTime();
				RedBlackTree.Node wNode = dictionary.lookup(word);
				double lookUpTime = System.nanoTime() - lookUpStart;
				lookUpTotalTime += lookUpTime;
				
				if (wNode == null) {
					missingWords.add(word);
				}
			}
			
			
			
			System.out.println("Here is a list of all words in the poem not present in the dictionary:" + "\n");
			System.out.println(missingWords + "\n");
			// Results
			System.out.println("It took " + createDictTotalTime/1000000000 + " seconds to create the dictionary.");
			System.out.println("It took " + lookUpTotalTime/1000000000 + " seconds to execute the spell check.");
		}
		catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		
		
		
	}
	
    
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
