import Book from "../book/bookModel";
import Library from '../library/libraryModel';

export default interface Inventory{
    inventoryId: number;
    book: Book;
    library: Library;
}