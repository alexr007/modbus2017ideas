package app.decision.test;

/**
 * Created by mac on 26.07.2017.
 */
public class Person {
    private Integer id;
    private String name;
    public Person(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
}