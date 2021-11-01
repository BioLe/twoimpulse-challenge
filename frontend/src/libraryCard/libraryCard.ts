import Person from '../person/personModel';
import Library from '../library/libraryModel';

export default interface LibraryCard{
    libraryCardId: number;
    library: Library;
    person: Person;
}