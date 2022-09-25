package facades;

import entities.Child;
import entities.Parent;
import entities.Toy;

import java.util.List;

public interface IExtraFunc {
    Long countChildren(Parent p);
    Long countAllToys();
    List<Toy> getToysByParent(Parent p);
    List<Parent> getParentsByToyId(int id);
    List<Parent> getParentsOfChildrenBetweenAge(int minYear, int maxYear);
    Parent getParentWithMostToys();
    Child getChildWithMostExpensiveToysTotal();
}
