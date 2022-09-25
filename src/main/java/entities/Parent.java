package entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NamedQuery(name = "Parent.deleteAllRows", query = "DELETE from Parent")
public class Parent {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private Integer age;
    @OneToMany(mappedBy = "parent") //Do not use CascadeTypes here, because Children has other non-identifying relationships.
    private Set<Child> children;
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL) //ID cards are personal and exist only when a persen does (Identifying relationship)
    private Set<IdentificationCard> cards;

    public Parent() {}

    public Parent(String name, Integer age) {
        this.name = name;
        this.age = age;
        this.children = new HashSet<>();
        this.cards = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "age")
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Parent parent = (Parent) o;

        if (id != parent.id) return false;
        if (name != null ? !name.equals(parent.name) : parent.name != null) return false;
        if (age != null ? !age.equals(parent.age) : parent.age != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        return result;
    }


    public Set<Child> getChildren() {
        return children;
    }

    public void setChildren(Set<Child> children) {
        this.children = children;
    }

    public void addChild(Child child) {
        this.children.add(child);
        child.setParent(this); //Child gets a parent when parent gets the child
    }

    public Set<IdentificationCard> getCards() {
        return cards;
    }

    public void setCards(Set<IdentificationCard> cards) {
        this.cards = cards;
    }

    public void addCard(IdentificationCard card) {
        this.cards.add(card);
        card.setParent(this);
    }

}
