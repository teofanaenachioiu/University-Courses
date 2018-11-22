package common.domain.validator;


import common.domain.Book;


public class BookValidator implements Validator<Book> {
    @Override
    public void validate(Book entity) throws ValidationException {
        //TODO validate book
    }
}
