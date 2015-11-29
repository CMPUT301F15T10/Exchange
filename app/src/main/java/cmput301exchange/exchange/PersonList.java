package cmput301exchange.exchange;

import java.util.ArrayList;
import java.util.Collections;
/**
 *
 * Stores a list of friends
 * @author Baihong Hang
 */


public class PersonList {

    private ArrayList<Person> personList;
    /**
     * Returns a List of Friends.
     */
    public ArrayList<Person> getPersonList() {

        return personList;
    }

    public PersonList() {
        personList= new ArrayList<>();
    }
    /**
     * Searches the personList for a string
     * @param query the string that you wish to search for.
     */
    public ArrayList<Person> searchPerson(String query){

        ArrayList<Person> results= new ArrayList<Person>();
        int n = personList.size();
        if(n==0){
            return results;
        }
        for(int i = 0;i<n;i++){
            if(personList.get(i).getName().toLowerCase().contains(query.toLowerCase())){
                results.add(personList.get(i));
            }
        }


        return results;
    }
    /**
     * Adds a friend
     * @param aperson the person you wish to add.
     */
    public void addPerson(Person aperson) {
        if (personList.contains(aperson)) {
            //Do nothing as we dont want to add duplicates.
        } else {
            personList.add(aperson);
        }
    }

    /**
     * Removes a person
     * remove by id, since id is unique
     * @param aperson the person you wish to remove from the personlist
     */
    public void removePerson(Person aperson){

        int n = personList.size();
        if(n==0){
            return;
        }
        for(int i = 0;i<n;i++){
            if(personList.get(i).getID()==aperson.getID()){
                personList.remove(i);
                break;
            }
        }
    }

    public Person getPersonByID(long ID){
        for (Person person:personList){
            if (person.getID()==ID){
                return person;
            }
        }
        return null;
    }

}
