package gomedic.model.person.patient;

import java.util.Objects;

import gomedic.commons.util.CollectionUtil;
import gomedic.model.commonfield.Name;
import gomedic.model.commonfield.Phone;
import gomedic.model.person.AbstractPerson;

/**
 * Represents a Patient in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Patient extends AbstractPerson {

	//data fields
	private final Age age;
	private final BloodType bloodtype;
	private final Gender gender;
	private final Height height;
	private final Weight weight;

	/**
	 * Every field must be present and not null.
	 *
	 * @param name Name of the patient.
	 * @param phone Phone number of the patient.
	 * @param id Id of the patient.
	 * @param age Age of the patient.
	 * @param bloodtype BloodType of the patient.
	 * @param gender Gender of the patient.
	 * @param height Height of the patient in cm.
	 * @param weight Weight of the patient in kg.
	 */
	public Patient(Name name, Phone phone, PatientId id, Age age, BloodType bloodtype, Gender gender,
	               Height height, Weight weight) {
		super(name, phone, id);
		CollectionUtil.requireAllNonNull(name, phone, id, age, bloodtype, gender, height, weight);
		this.age = age;
		this.bloodtype = bloodtype;
		this.gender = gender;
		this.height = height;
		this.weight = weight;
	}

	/**
	 * Returns Age of the patient.
	 *
	 * @return Age of the patient.
	 */
	public Age getAge() {
		return age;
	}

	/**
	 * Returns BloodType of the patient.
	 *
	 * @return BloodType of the patient.
	 */
	public BloodType getBloodType() {
		return bloodtype;
	}

	/**
	 * Returns Gender of the patient.
	 *
	 * @return Gender of the patient.
	 */
	public Gender getGender() {
		return gender;
	}

	/**
	 * Returns Height of the patient.
	 *
	 * @return Height of the patient.
	 */
	public Height getHeight() {
		return height;
	}

	/**
	 * Returns Weight of the patient.
	 *
	 * @return Weight of the patient.
	 */
	public Weight getWeight() {
		return weight;
	}

	/**
	 * Returns true if both patients have the same id.
	 */
	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}

		if (!(other instanceof Patient)) {
			return false;
		}

		Patient otherPatient = (Patient) other;
		return super.equals(otherPatient);
	}

	@Override
	public int hashCode() {
		// use this method for custom fields hashing instead of implementing your own
		return Objects.hash(getName(), getPhone(), getId(), age, bloodtype, gender, height, weight);
	}

	/**
	 * Returns a String representation of a {@code Patient}, using identity fields of its super class, as well as
	 * the patient's Age, BloodType, Gender, Height, and Weight field.
	 *
	 * @return a String representation of a {@code Patient}.
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append(super.toString())
				.append("; Age: ")
				.append(getAge())
				.append("; Blood type: ")
				.append(getBloodType())
				.append("; Gender: ")
				.append(getGender())
				.append("; Height: ")
				.append(getHeight())
				.append("; Weight: ")
				.append(getWeight());

		return builder.toString();
	}
}
