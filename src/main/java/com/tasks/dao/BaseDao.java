package com.tasks.dao;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.MongoURI;

import java.net.UnknownHostException;

 /**
 * BaseDao
 * Abstract Class with he basic connection methods for the Database Access
 * @author Alejandro
 * @version 1.0
 */
 public abstract class BaseDao {
     public static Mongo mongo;
     public static DB database;
     public static boolean local=false;

     public static DB getDB() throws UnknownHostException, MongoException {
         try {
             MongoURI mongoURI = new MongoURI(System.getenv("MONGOHQ_URL"));
             //MongoURI mongoURI = new MongoURI("mongodb://todoUser:todoPassword@kahana.mongohq.com:10021/app27825767");
             database = mongoURI.connectDB();
             if (mongoURI.getUsername()!=null) {
                 database.authenticate(mongoURI.getUsername(), mongoURI.getPassword());
             }
         }
         catch (Exception e) {
             e.printStackTrace();
             return null;
         }
         return database;
     }

     public static void closeMongo(){
        if (mongo!=null) mongo.close();
     }
 }

