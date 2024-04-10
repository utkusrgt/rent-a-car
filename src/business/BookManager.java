package business;

import dao.BookDao;
import entity.Book;

public class BookManager {
    private final BookDao bookDao;

    public BookManager() {
        bookDao = new BookDao();
    }

    public boolean save(Book book){
        return this.bookDao.save(book);
    }

}
