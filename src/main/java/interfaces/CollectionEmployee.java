package interfaces;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.Employee;

public class CollectionEmployee implements MainObjectInterface<Employee> {
    private ObservableList<Employee> employeeObservableList = FXCollections.observableArrayList();

    @Override
    public void add(Employee employee) {
//        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName().toString());
        employeeObservableList.add(employee);
    }

    @Override
    public void delete(Employee employee) {
        employeeObservableList.remove(employee);
    }

    public Employee lasted() { return employeeObservableList.get(employeeObservableList.size() - 1); }

    public ObservableList<Employee> getEmployeeObservableList() {
        return employeeObservableList;
    }

    public void printEmployee() {
        int number = 0;
        System.out.println();
        for (Employee employee : employeeObservableList) {
            number++;
            System.out.println(number + ") id - " + employee.getId() + "; First name - " + employee.getFirstName() +
                    "; Last Name - " + employee.getLastName() + "; Pathronimyc -" + employee.getPatronymic() +
                    "; Vacancy - " + employee.getVacancy());
        }
    }

    public void fillTestDataEmployee() {
        employeeObservableList.add(new Employee("101", "Лапшов", "Михаил", "Михайлович", "Менеджер"));
        employeeObservableList.add(new Employee("102", "Маркин", "Евгений", "Анатольевич", "Оператор"));
        employeeObservableList.add(new Employee("103", "Корякин", "Артём", "Михайлович", "Старший менеджер"));
        employeeObservableList.add(new Employee("104", "Носова", "Илона", "Игоревна", "Менеджер"));
        employeeObservableList.add(new Employee("105", "Романихин", "Алексей", "Вячеславович", "Оператор"));
    }
}
