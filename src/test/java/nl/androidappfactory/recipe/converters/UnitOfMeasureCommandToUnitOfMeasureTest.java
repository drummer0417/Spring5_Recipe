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
public class UnitOfMeasureCommandToUnitOfMeasureTest {

	public final static String DESCRIPTION = "";
	public final static Long LONG_VALUE = new Long(1l);

	UnitOfMeasureCommandToUnitOfMeasure converter;

	@Before
	public void setup() {
		converter = new UnitOfMeasureCommandToUnitOfMeasure();
	}

	@Test
	public void testNullParameter() {

		assertNull(converter.convert(null));
	}

	@Test
	public void testEmptyObject() {

		assertNotNull(converter.convert(new UnitOfMeasureCommand()));
	}

	@Test
	public void testConvert() {

		// Given
		UnitOfMeasureCommand uomCommand = new UnitOfMeasureCommand();
		uomCommand.setId(LONG_VALUE);
		uomCommand.setDescription(DESCRIPTION);

		// when
		UnitOfMeasure uom = converter.convert(uomCommand);

		// then
		assertNotNull(uom);
		assertEquals(LONG_VALUE, uom.getId());
		assertEquals(DESCRIPTION, uom.getDescription());
	}
}
