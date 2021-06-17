package service.impl;

import entity.Employee;
import org.apache.log4j.Logger;
import repository.EmployeeRepository;
import repository.RepositoryException;
import repository.RepositoryProvider;
import service.EmployeeService;
import service.ServiceException;

import java.util.List;

public class DefaultEmployeeService implements EmployeeService {
    private static final Logger log = Logger.getLogger(DefaultEmployeeService.class);
    private final EmployeeRepository employeeRepository;

    public DefaultEmployeeService() {
        RepositoryProvider provider = RepositoryProvider.getInstance();
        employeeRepository = provider.getEmployeeRepository();
    }

    @Override
    public Employee createEmployee(String firstName, String surName, String patronymic, String position) {
        try {
            validateValues(firstName, surName, patronymic, position);

            Employee employee = new Employee();
            employee.setFirstName(firstName);
            employee.setSurName(surName);
            employee.setPosition(position);
            if (patronymic != null && !patronymic.isBlank()) {
                employee.setPatronymic(patronymic);
            }

            employee = employeeRepository.saveEmployee(employee);
            log.info("Successfully created employee with id " + employee.getId());

            return employee;
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        try {
            log.info("Getting all employees.");
            return employeeRepository.getAllEmployees();
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Employee getEmployee(long id) {
        try {
            log.info("Getting employee with id " + id);
            Employee employee = employeeRepository.getEmployeeById(id);
            if (employee == null) {
                log.error("Employee with id " + id + " doesn't exist.");
                throw new ServiceException("Пользователь с id " + id + " не сущесвует!");
            }

            return employee;
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Employee updateEmployee(long id, String firstName, String surName, String patronymic, String position) {
        try {
            Employee employee = employeeRepository.getEmployeeById(id);
            if (employee == null) {
                log.error("Employee with id " + id + " doesn't exist.");
                throw new ServiceException("Пользователь с id " + id + " не сущесвует!");
            }

            validateValues(firstName, surName, patronymic, position);

            Employee newEmployee = new Employee();
            newEmployee.setId(id);
            newEmployee.setFirstName(firstName);
            newEmployee.setSurName(surName);
            newEmployee.setPosition(position);
            if (patronymic != null && !patronymic.isBlank()) {
                newEmployee.setPatronymic(patronymic);
            }

            employee = employeeRepository.updateEmployee(newEmployee);
            log.info("Successfully updated employee with id " + employee.getId());

            return employee;
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public long deleteEmployee(long id) {
        try {
            Employee employee = employeeRepository.getEmployeeById(id);
            if (employee == null) {
                log.error("Employee with id " + id + " doesn't exist.");
                throw new ServiceException("Пользователь с id " + id + " не сущесвует!");
            }

            employeeRepository.deleteEmployeeById(id);
            log.info("Successfully deleted employee with id " + id);

            return id;
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    private void validateValues(String firstName, String surName, String patronymic, String position) {
        if (firstName == null || firstName.isBlank() || surName == null || surName.isBlank()
                || position == null || position.isBlank()) {
            throw new ServiceException("Введите обязательные поля.");
        }

        if (firstName.length() > 30) {
            throw new ServiceException("Длина имени не больше 30 символов.");
        }

        if (surName.length() > 30) {
            throw new ServiceException("Длина фамилии не больше 30 символов.");
        }

        if (position.length() > 30) {
            throw new ServiceException("Длина должности не больше 30 символов.");
        }

        if (patronymic != null && patronymic.length() > 30) {
            throw new ServiceException("Длина отчества не больше 30 символов.");
        }
    }
}
