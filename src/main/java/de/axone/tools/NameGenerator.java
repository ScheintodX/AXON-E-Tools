package de.axone.tools;

public interface NameGenerator {
	
	/**
	 * @return one generated name
	 * 
	 * @param male true: male, false: female, null: both
	 */
	public Name generate( Boolean male );

	public static class Name {
		
		private boolean male;
		
		private String title, firstname, surname;
		
		public Name( boolean male, String title, String firstname, String surname ){
			
			this.male = male;
			this.title = title;
			this.firstname = firstname;
			this.surname = surname;
		}
		
		public boolean isMale() { return male; }

		public String getTitle() { return title; }

		public String getFirstname() { return firstname; }

		public String getSurname() { return surname; }

		@Override public String toString(){
			
			return (title != null ? title : "") 
					+ " " + firstname + " " + surname + (male ? " (M)" : " (F)" );
		}
	}
}
