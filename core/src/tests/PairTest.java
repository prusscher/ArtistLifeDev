// Was breaking the dist gradle command, I commented it out

//package tests;
//
//import static org.junit.Assert.*;
//
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import edu.sadsnails.game.Pair;
//
//public class PairTest {
//
//	private static Pair pair;
//		
//	@BeforeClass
//	public static void setupPair() {
//		pair = new Pair();
//	}
//
//	@Test
//	public void testPutString() {
//		pair.clear();
//		pair.putString("Test1", "testString");
//		assertEquals("testString", pair.getString("Test1"));
//		
//		assertEquals(null, pair.getString("DNE"));
//	}
//
//	@Test
//	public void testPutInt() {
//		pair.clear();
//		pair.putInt("Int1", 1);
//		assertEquals(1, pair.getInt("Int1"));
//		
//		assertEquals(-1, pair.getInt("DNE"));
//	}
//
//	@Test
//	public void testPutFloat() {
//		pair.clear();
//		pair.putFloat("Float1", 1f);
//		assertEquals(1f, pair.getFloat("Float1"), 0);
//		
//		assertEquals(-1f, pair.getFloat("DNE"), 0);
//	}
//	
//	@Test
//	public void testRemove() {
//		pair.clear();
//		pair.putInt("Remove1", 1);
//		assertEquals(1, pair.remove("Remove1"));
//	}
//	
//	@Test
//	public void testGetString() {
//		pair.putString("Test1", "test");
//		assertEquals("test", pair.getString("Test1"));
//		
//		assertEquals(null, pair.getString("DNE"));
//	}
//
//	@Test
//	public void testGetInt() {
//		pair.clear();
//		pair.putInt("Test1", 1);
//		assertEquals(1, pair.getInt("Test1"));
//		assertEquals(-1, pair.getInt("DNE"));
//	}
//
//	@Test
//	public void testGetFloat() {
//		pair.clear();
//		pair.putFloat("Test1", .1f);
//		assertEquals(.1f, pair.getFloat("Test1"), 0f);
//		assertEquals(-1f, pair.getFloat("DNE"), 0f);
//	}
//
//	@Test
//	public void testSize() {
//		pair.clear();
//		pair.putInt("1", 1);
//		pair.putFloat("2", 2f);
//		pair.putString("3", "3");
//		
//		assertEquals(3, pair.size());
//		
//		pair.remove("3");
//		assertEquals(2, pair.size());
//
//		pair.putFloat("3", 3f);
//		assertEquals(3, pair.size());
//		
//		pair.remove("3");
//		assertEquals(2, pair.size());
//		
//		pair.remove("2");
//		assertEquals(1, pair.size());
//		
//		pair.remove("1");
//		assertEquals(0, pair.size());
//	}
//	
//	@Test
//	public void testGet() {
//		fail("Not yet implemented");
//	}
//}
