package nl.androidappfactory.recipe.models;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
// @EqualsAndHashCode(exclude = { "recipe" })
@Entity
public class Ingredient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String description;
	private BigDecimal amount;

	@OneToOne(fetch = FetchType.EAGER) // EAGER is actually default, only here for documentation
	private UnitOfMeasure unitOfMeasure;

	@ManyToOne
	private Recipe recipe;

	public Ingredient() {}

	public Ingredient(String description, BigDecimal amount, UnitOfMeasure unitOfMeasure) {
		this.description = description;
		this.amount = amount;
		this.unitOfMeasure = unitOfMeasure;
	}

	@Override
	public String toString() {
		return "Ingredient [id=" + id + ", description=" + description + ", amount=" + amount + ", recipe=" + recipe
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

}
