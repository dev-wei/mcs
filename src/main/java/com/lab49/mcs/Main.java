package com.lab49.mcs;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.apache.jackrabbit.oak.Oak;
import org.apache.jackrabbit.oak.jcr.Jcr;
import org.apache.jackrabbit.oak.plugins.document.DocumentMK;
import org.apache.jackrabbit.oak.plugins.document.DocumentNodeStore;

import javax.jcr.Repository;

public class Main {
  public static void main(String[] args) {
    System.out.print("abc");

    DB db = new MongoClient("127.0.0.1", 27017).getDB("test2");
    DocumentNodeStore ns = new DocumentMK.Builder().
      setMongoDB(db).getNodeStore();
    Repository repo = new Jcr(new Oak(ns)).createRepository();
  }
}
