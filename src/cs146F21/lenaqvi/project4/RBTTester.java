package cs146F21.lenaqvi.project4;

import static org.junit.jupiter.api.Assertions.*;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;


import org.junit.jupiter.api.Test;

class RBTTester {

	@Test
	//Test the Red Black Tree
	// Dr. Potika Test
	public void test() {
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

		//final tree product
		String str=     "Color: 1, Key:D Parent: \n"+
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
	void addTest() {
		RedBlackTree rbt = new RedBlackTree();
        rbt.insert("D");
        rbt.insert("B");
        rbt.insert("A");
        rbt.insert("C");
        rbt.insert("F");
        rbt.insert("E");
        System.out.println(makeString(rbt));
        assertEquals("BADCFE", makeString(rbt));
            
	}

	@Test
	void lookUpTest() {
		RedBlackTree rbt = new RedBlackTree();
		rbt.insert("D");
		rbt.insert("B");
		rbt.insert("A");
		rbt.insert("C");
		rbt.insert("F");
		rbt.insert("E");

		RedBlackTree.Node result = rbt.lookup("A");
		assertNotNull(result);
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

	public RedBlackTree makeRBTdictionary() throws IOException{

		RedBlackTree rbt = new RedBlackTree();
		FileReader fr = new FileReader("stellar-speller/dictionary.txt");
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
		FileReader read = new FileReader("stellar-speller/message.txt");
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

			double startTime = System.nanoTime();
			RedBlackTree dictionary = makeRBTdictionary();
			double dictEndTime = System.nanoTime();
			double dictLength  = dictEndTime - startTime;

			double spellCheckStart = System.nanoTime();


			ArrayList<String> words = readPoem();
			ArrayList<String> missingWords = new ArrayList<String>();

			for (String word : words) {
				if (dictionary.lookup(word) == null) {
					missingWords.add(word);
				}
			}
			double spellCheckEnd = System.nanoTime();

			double lookUpLength = spellCheckEnd - spellCheckStart;

			System.out.println("Here is a list of all words in the poem not present in the dictionary." + "\n");
			System.out.println(missingWords + "\n");

			System.out.println("It took "+dictLength/1000000000+ " seconds to create the dictionary.");
			System.out.println("It took "+lookUpLength/1000000000+ " seconds to execute the spell check.");

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
					result = result + "Color: "+n.color+", Key:"+n.key+" Parent: \n";
    			else if (!(n.key).equals(""))
    				result = result +"Color: "+n.color+", Key:"+n.key+" Parent: "+n.parent.key+"\n";
             
    		}
    	};
    	
    	MyVisitor v = new MyVisitor();
        t.preOrderVisit(v);
        return v.result;
    }
}
