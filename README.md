Раздел 3: Основы Hibernate  

Select (get) нужно тоже делать в транзакции.
Касакд "по-умолчанию" отсутствует.

Важно закрывать сессию в finally чтобы не было session leak при exception.  

При изменении объекта не нужно его save - он сохранится при завершении транзакции.

**1:1**  
нужен каскад в @OneToOne(cascade = CascadeType.ALL)  
иначе *"org.hibernate.TransientObjectException: object references an unsaved transient instance"*  

не нужно save(child) - он записывается в таблицу при save(parent) при наличии каскада.  

При удалении parent удаляение child зависит от каскада.  

Если нужно удалить только child, то сначала нужно разорвать связь:
parent.setChild(**null**);  
session.delete(child);  

При **bi-directional** добавляем в child не колоночное поле для ссылки на parent:
```
// (по умолчанию у 1:1 fetch = FetchType.EAGER --> left outer join)
@OneToOne(mappedBy = "empDetail", cascade = CascadeType.ALL)    
private Employee employee;  
```

Когда этому полю присвоена ссылка на parent, то при save(child), 
если прописан каскад, то сохранится и parent. 
Удаление child приведёт и к удалению parent.  

**В БД MySQL** Foreign Key Options: On Update, On Delete  
RESTRICT, CASCADE, SET NULL, NO ACTION

В **toString** в сущностях нужно выводить только колоночные поля
этой сущности, а не объекты-ссылки - иначе по каскаду будет
перегруз. Если нужно показывать значение такого поля, то его
нужно добавлять с параметрами:
```
@Column(name = "department_id", 
        insertable = false, updatable = false)
private Integer departmentId;
```
и можно не делать геттер/сеттер  

**1:M Uni**_Directional_   
```
// Department: 
// (по умолчанию у 1:M fetch = FetchType.LAZY)
@OneToMany(cascade = CascadeType.ALL)
@JoinColumn(name = "department_id")
private List<Employee> employees;
...
public void addEmployee(Employee emp) {
  if (employees == null) employees = new ArrayList<>();
  employees.add(emp);
}

// Employee:
@Column(name = "department_id")
private Integer departmentId;
```

Hibernate понимает что employee.department_id --> department.**id** из
foreign key в БД. 

По save(parent) инсертятся он сам и его childes, а при закрытии
транзакции каскад проставляет foreign key путём выполнения update на 
каждый child.

В delete parent'a чтаются его child'ы. Само удаление происходит 
при выполнении транзакции и вначале рвутся его связи:     
```
Hibernate: update employees_o2m set department_id=null where department_id=?
```  
затем удаляются childes, и в конце удаляется parent.  

**1:M Bi**_Directional_  
```
// Department: 
@OneToMany(cascade = CascadeType.ALL, mappedBy = "department")
private List<Employee> employees;

// Изменяем добавку
public void addEmployee(Employee emp) {
    if (employees == null) employees = new ArrayList<>();
    employees.add(emp);
    emp.setDepartment(this);  <---
}

// Изменяем Employee:
// (по умолчанию у M:1 fetch = FetchType.EAGER --> left outer join)
@ManyToOne  
@JoinColumn(name = "department_id")  
private Department department;
```

Нужно пользоваться 1:M_Bi:  
По save инсертятся родительская и дочерняя записи и никаких update для foreign key не происходит! 
При delete никаких лишних update-разрывов с childes не происходит.  


