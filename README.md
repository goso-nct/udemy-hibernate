Раздел 3: Основы Hibernate  

Select (get) нужно тоже делать в транзакции.

При изменении объекта не нужно его save - он сохранится при завершении транзакции.

При 1:1 нужен каскад в @OneToOne(cascade = CascadeType.ALL)  
иначе *"org.hibernate.TransientObjectException: object references an unsaved transient instance"*
child не нужно save отдельно - он записывается в таблицу при сохранении parent.  

При удалении parent удаляение child зависит от каскада.  

Если нужно удалить child, то сначала нужно разорвать связь:
employee.setEmpDetail(null);  
session.delete(detail);  



