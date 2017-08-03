package tests.lambda;

import java.util.List;
import java.util.ArrayList;
import java.time.chrono.IsoChronology;
import java.time.LocalDate;

public class PersonTest
{

    public enum Sex {
        MALE, FEMALE
    }

    String name;
    LocalDate birthday;
    Sex gender;
    String emailAddress;

    PersonTest(String nameArg, LocalDate birthdayArg,
               Sex genderArg, String emailArg) {
        name = nameArg;
        birthday = birthdayArg;
        gender = genderArg;
        emailAddress = emailArg;
    }

    public int getAge() {
        return birthday
                .until(IsoChronology.INSTANCE.dateNow())
                .getYears();
    }

    public void printPerson() {
        System.out.println(name + ", " + this.getAge());
    }

    public Sex getGender() {
        return gender;
    }

    public String getName() {
        return name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public static int compareByAge(PersonTest a, PersonTest b) {
        return a.birthday.compareTo(b.birthday);
    }

    public static List<PersonTest> createRoster() {

        List<PersonTest> roster = new ArrayList<>();
        roster.add(
                new PersonTest(
                        "Fred",
                        IsoChronology.INSTANCE.date(1980, 6, 20),
                        PersonTest.Sex.MALE,
                        "fred@example.com"));
        roster.add(
                new PersonTest(
                        "Jane",
                        IsoChronology.INSTANCE.date(1990, 7, 15),
                        PersonTest.Sex.FEMALE, "jane@example.com"));
        roster.add(
                new PersonTest(
                        "George",
                        IsoChronology.INSTANCE.date(1991, 8, 13),
                        PersonTest.Sex.MALE, "george@example.com"));
        roster.add(
                new PersonTest(
                        "Bob",
                        IsoChronology.INSTANCE.date(2000, 9, 12),
                        PersonTest.Sex.MALE, "bob@example.com"));

        return roster;
    }

}