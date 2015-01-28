package com.lab49.mcs;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.apache.jackrabbit.oak.Oak;
import org.apache.jackrabbit.oak.jcr.Jcr;
import org.apache.jackrabbit.oak.plugins.document.DocumentMK;
import org.apache.jackrabbit.oak.plugins.document.DocumentNodeStore;

import javax.jcr.Node;
import javax.jcr.Repository;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

public class OakMain {
  public static void main(String[] args) {

//    MongoMK.Builder m = new MongoMK.Builder();
//    MongoMK kernel = m.setMongoDB(db).open();

    try {
      DB db = new MongoClient("127.0.0.1", 27017).getDB("test");
      DocumentNodeStore ns = new DocumentMK.Builder()
        .setMongoDB(db)
        .getNodeStore();
      Repository repo = new Jcr(new Oak(ns)).createRepository();

      Session session = repo.login(
        new SimpleCredentials("admin", "admin".toCharArray())
      );
      Node root = session.getRootNode();
      if (root.hasNode("hello")) {
        Node hello = root.getNode("hello");
        long count = hello.getProperty("count").getLong();
        hello.setProperty("count", count + 1);
        System.out.println("found the hello node, count=" + count);
      } else {
        System.out.println("creating the hello node");
        root.addNode("hello").setProperty("count", 1);
      }
      session.save();
      session.logout();
      ns.dispose();
    } catch (Exception exp) {
      System.out.print(exp);
    } finally {

    }
  }
}
