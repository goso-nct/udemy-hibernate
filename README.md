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

При **bi-directional** добавляем в child не колоночное поле для ссылки на parent'a:  
@OneToOne(mappedBy = "empDetail", cascade = CascadeType.ALL)    
private Employee employee;  

Когда этому полю присвоена ссылка на parent, то при save(child), если прописан каскад, то сохранится и parent. Удаление child приведёт и к удалению parent'a.  

**В БД MySQL** Foreign Key Options: On Update, On Delete  
RESTRICT, CASCADE, SET NULL, NO ACTION

**1:M uni**  
При вставке по save инсертятся родительская и дочерняя записи, а при закрытии
транзакции каскад изменяет дочернии записи - проставляется foreign key - 
выполняется update на каждую дочь.

