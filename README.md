Раздел 3: Основы Hibernate  

Select (get) нужно тоже делать в транзакции.

Важно закрывать сессию в finaly чтобы не было утечек при exeptions  

При изменении объекта не нужно его save - он сохранится при завершении транзакции.

При 1:1 нужен каскад в @OneToOne(cascade = CascadeType.ALL)  
иначе *"org.hibernate.TransientObjectException: object references an unsaved transient instance"*
child не нужно save отдельно - он записывается в таблицу при сохранении parent.  

При удалении parent удаляение child зависит от каскада.  

Если нужно удалить child, то сначала нужно разорвать связь:
employee.setEmpDetail(null);  
session.delete(detail);  

При bi-directional добавляем в child поле (не колонку) - ссылку на parent'a:  
@OneToOne(mappedBy = "empDetail", cascade = CascadeType.ALL)    
private Employee employee;  

Когда этому полю присвоена ссылка на parent, то при save(child), если прописан каскад, то сохранится и parent. Удаление child приведёт и к удалению parent'a  





