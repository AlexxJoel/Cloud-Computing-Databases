package Main;

import Model.Person;
import com.db4o.*;
import com.db4o.internal.EmbeddedClientObjectContainer;

import java.io.File;

public class DB4O {
    public static void main(String[] args) {

        //Create and save
        Person p1 = new Person("Joel", 19 , 43, 34);
        Person p2 = new Person("Lia", 659 , 43, 3474);
        Person p3 = new Person("Marco", 179 , 43767, 3574);
        Person p4 = new Person("Jose", 159 , 47653, 34557);

        ObjectContainer db = Db4o.openFile("person.db4o");

        db.store(p1);
        db.store(p2);
        db.store(p3);
        db.store(p4);

        Person p = new Person(); //Query filter the information
        ObjectSet result = db.queryByExample(p);
        while (result.hasNext()){
            System.out.println("result.next() = " + result.next());
        }

        System.out.println("---------------------------------------------------------");



        //update information
         p = new Person();
        p.setName("Joel");
        result = db.queryByExample(p);
        if(result.hasNext()){
            Person pAct = (Person) result.next();
            pAct.setAge(30);
            db.store(pAct);
        }

        //deleted
        p = new Person(); //query
        p.setName("Lia");
        result = db.queryByExample(p);
        if(result.hasNext()){
            Person pDel = (Person) result.next();
           db.delete(pDel);

        }


        //show information
        p = new Person(); //Query filter the information
        result = db.queryByExample(p);
        while (result.hasNext()){
            System.out.println("result.next() = " + result.next());
        }

        db.close();
    }
}
