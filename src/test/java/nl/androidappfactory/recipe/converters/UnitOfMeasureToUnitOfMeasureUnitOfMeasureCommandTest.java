/**
 * 
 */
package nl.androidappfactory.recipe.converters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import nl.androidappfactory.recipe.commands.UnitOfMeasureCommand;
import nl.androidappfactory.recipe.models.UnitOfMeasure;

/**
 * @author Hans van Meurs
 *
 */
public class UnitOfMeasureToUnitOfMeasureUnitOfMeasureCommandTest {

	public final static String DESCRIPTION = "";
	public final static Long LONG_VALUE = new Long(1l);

	private UnitOfMeasureToUnitOfMeasureCommand converter;

	@Before
	public void setUp() throws Exception {

		converter = new UnitOfMeasureToUnitOfMeasureCommand();
	}

	@Test
	public void testNullParameter() {

		assertNull(converter.convert(null));
	}

	@Test
	public void testNotNull() {
		assertNotNull(converter.convert(new UnitOfMeasure()));
	}

	@Test
	public void testConvert() {

		// Given
		UnitOfMeasure uom = new UnitOfMeasure();
		uom.setId(LONG_VALUE);
		uom.setDescription(DESCRIPTION);

		// when
		UnitOfMeasureCommand uomCommand = converter.convert(uom);

		// then
		assertNotNull(uomCommand);
		assertEquals(LONG_VALUE, uomCommand.getId());
		assertEquals(DESCRIPTION, uomCommand.getDescription());
	}

}
