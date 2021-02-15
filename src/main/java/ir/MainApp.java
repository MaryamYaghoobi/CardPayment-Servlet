package ir;


import ir.dotin.PersonDao;


public class MainApp {
    public static void main(String[] args) {

        PersonDao p=new PersonDao();
        System.out.println(p.find());

        }
    }