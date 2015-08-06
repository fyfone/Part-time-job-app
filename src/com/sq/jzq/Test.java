package com.sq.jzq;

import java.util.List;

import com.google.gson.Gson;

public class Test {

	public static void main(String[] args) {
		String gs = "{" + "country:{" +

		"city:{" + "persons:[" + "{name:gaofeng,age:22},"
				+ "{name:bing,age:23}" + "]" + "}" + "}" + "}";
		Gson gson = new Gson();
		T t = gson.fromJson(gs, T.class);
		List<Person> persons = t.getCountry().getCity().getPersons();
		System.out.println("c:" + persons);
		for (Person p : persons) {
			System.out.println("pname:" + p.getName());
			System.out.println("page:" + p.getAge());
		}

	}

	class T {

		private Country country;

		public T() {
		}

		public Country getCountry() {
			return country;
		}

		public void setCountry(Country country) {
			this.country = country;
		}

	}

	class Country {
		private City city;

		public Country() {
		}

		public City getCity() {
			return city;
		}

		public void setCity(City city) {
			this.city = city;
		}

	}

	class City {
		private List<Person> persons;

		public City() {
		}

		public List<Person> getPersons() {
			return persons;
		}

		public void setPersons(List<Person> persons) {
			this.persons = persons;
		}

	}

	class Person {
		private String name;
		private int age;

		public Person() {
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

	}

}
