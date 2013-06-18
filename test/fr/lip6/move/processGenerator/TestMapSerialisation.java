package fr.lip6.move.processGenerator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import org.junit.Assert;
import org.junit.Test;


public class TestMapSerialisation {

	@Test
	public void testHashMapSerialization() throws IOException, ClassNotFoundException {

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("a", "1");
		map.put("z", "2");

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream objOut = new ObjectOutputStream(out);
		objOut.writeObject(map);
		objOut.close();

		ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
		ObjectInputStream objIn = new ObjectInputStream(in);
		@SuppressWarnings("unchecked")
		HashMap<String, String> actual = (HashMap<String, String>) objIn.readObject();
		// now try to get a value
		Assert.assertEquals("2", actual.get("z"));
	}
}
