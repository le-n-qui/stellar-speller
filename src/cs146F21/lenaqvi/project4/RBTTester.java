package cs146F21.lenaqvi.project4;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

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
        /*String str=     "Color: 1, Key:D Parent: \n"+
                        "Color: 1, Key:B Parent: D\n"+
                        "Color: 1, Key:A Parent: B\n"+
                        "Color: 1, Key:C Parent: B\n"+
                        "Color: 1, Key:F Parent: D\n"+
                        "Color: 1, Key:E Parent: F\n"+
                        "Color: 0, Key:H Parent: F\n"+
                        "Color: 1, Key:G Parent: H\n"+
                        "Color: 1, Key:I Parent: H\n"+
                        "Color: 0, Key:J Parent: I\n";
		assertEquals(str, makeStringDetails(rbt));*/
            
	}
	
	@Test
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
		rbt.insert("A");
		rbt.insert("C");
		rbt.insert("F");
		rbt.insert("E");
		rbt.insert("H");
		rbt.insert("G");
		rbt.insert("I");
		rbt.insert("J");

  
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
    			if(!(n.key).equals(""))
    				result = result +"Color: "+n.color+", Key:"+n.key+" Parent: "+n.parent.key+"\n";
             
    		}
    	};
    	
    	MyVisitor v = new MyVisitor();
        t.preOrderVisit(v);
        return v.result;
    }
}
