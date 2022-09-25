package entities;

import javax.persistence.*;
import java.util.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "Toy.deleteAllRows", query = "DELETE from Toy"),
        @NamedQuery(name = "Toy.findAllToys", query = "Select t from Toy t")
})
public class Toy {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique=true) // these unique constraint helps avoid that multiple entities of the same identity are created. Especially usefull when using cascades.
    private String name;
    private Integer age;
    private Double price;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable( // This is now the owner side of the relationsship
            name = "children_toys",
            joinColumns = @JoinColumn(name = "child_id"),
            inverseJoinColumns = @JoinColumn(name = "toy_id"))
    private Set<Child> children;

    @ManyToMany(cascade = CascadeType.ALL) //BAD idea to have cascade here between Toy and Tool (Since ones existence is not dependend on another). See ToolFacadeTest
    @JoinTable( // This is now the owner side of the relationsship
            name = "toys_tools",
            joinColumns = @JoinColumn(name = "tool_id"),
            inverseJoinColumns = @JoinColumn(name = "toys_id"))
    private Set<Tool> tools;

    public Toy() {}

    public Toy(String name, Integer age, Double price) {
        this.name = name;
        this.age = age;
        this.price = price;
        this.children = new HashSet<>();
        this.tools = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", unique = true)
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

    public Set<Child> getChildren() {
        return children;
    }

    public void setChildren(Set<Child> children) {
        this.children = children;
    }

    public void addChildren(Child c) {
        this.children.add(c);
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Set<Tool> getTools() {
        return tools;
    }

    public void setTools(Set<Tool> tools) {
        this.tools = tools;
    }

    public void addTool(Tool tool) {
        this.tools.add(tool);
        if(!tool.getToys().contains(this))
            tool.addToy(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Toy toy = (Toy) o;
        return id == toy.id && Objects.equals(name, toy.name) && Objects.equals(age, toy.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age);
    }
}
