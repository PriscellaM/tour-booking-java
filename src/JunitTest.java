import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class JunitTest {

	@Test
	void test1() throws illegalArgException {
		//Valid
		
		Account salesExec1=new Account("Ben", "Ben", "B123");
		Ticket t1=new Ticket(1, 2, 0, salesExec1);
		Ticket t2=new Ticket(2, 1, 2, salesExec1);

		//getStatus() method
		assertEquals("Valid", t1.getStatus());	//status="valid" is automatically created when new ticket is created
		assertNotNull(t2.getStatus());
		
		//setTotal() method
		t1.setTotal(220.0);
		t2.setTotal(300.0);
		assertEquals(220, t1.getTotal());
		assertEquals(300.0, t2.getTotal());
		
		//refund() method
		t1.refund();
		t2.refund();
		assertEquals(55.0, t1.getTotal());		//refund 75%, keep 25%
		assertNotNull(t2.getTotal());
		
	}

	@Test
	void test2() throws illegalArgException {
		//Invalid
		
		Account salesExec1=new Account("Benjamin", "Ben", "B123");
		Ticket t1=new Ticket(1, 2, 0, salesExec1);
		//getStatus() method
		assertEquals("null", t1.getStatus());	
	}
	
	@Test
	void test3() throws illegalArgException {
		//Invalid
		
		Account salesExec2=new Account("Pipa", "Pipa", "P123");
		Ticket t1=new Ticket(1, 2, 0, salesExec2);
		//setTotal() method
		t1.setTotal(250.0);
		assertNull(t1.getTotal());
	}
	
	@Test
	void test4() throws illegalArgException {
		//Invalid
		
		Account salesExec2=new Account("Pipa", "Pipa", "P123");
		Ticket t1=new Ticket(1, 2, 0, salesExec2);
		
		//refund() method
		t1.setTotal(100.0);
		t1.refund();
		assertEquals(0, t1.getTotal());		//not supposed to be 0
											//should be 25% of 100.0 = 25.0
	}
}
