package models;

import javax.persistence.*;

import play.data.validation.Constraints;
import play.db.ebean.Model;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Alexx on 28.05.2015.
 */

@Entity
public class Help extends Model{

    @Id
    @GeneratedValue
    public int id;

    @Constraints.Required
    public String name;

    @Column
    public String description;

    @Column
    public double latitude;

    @Column
    public double longitude;
    @Column
    public Date creatingDate;
    @Column
    public Date closingDate;
    @Column
    public ArrayList<Date> dates;
    @Column
    public int price;

    @ManyToOne
    public Category category;

    @ManyToOne
    public User owner;

    @Column
    public boolean isDeleted;

    @ManyToOne
    public User helpie;

    public boolean done = false;

    public Help (){
        creatingDate = new Date();
    }

    public static Finder<String, Help> find = new Finder<String, Help>(String.class, Help.class);

    public static List<Help> getJobsNotDone(){
        List<Help> allHelps = find.all();
        List<Help> notDoneHelps = new ArrayList<>();
        for (int i=0; i<allHelps.size(); i++){
            if(allHelps.get(i).done==false && !allHelps.get(i).isDeleted) notDoneHelps.add(allHelps.get(i));
        }

        List<Help> reverseNotDOneHelps = new ArrayList<>();
        for(int i=1;i<=notDoneHelps.size();i++){
            reverseNotDOneHelps.add(notDoneHelps.get(notDoneHelps.size()-i));
        }
        return reverseNotDOneHelps;
        //return notDoneHelps;
    }

    public static List<Help> getLastJobs(){
        List<Help> allJobs = getJobsNotDone();
        List<Help> lastJobs = new ArrayList<>();
        if (allJobs.size()>0) lastJobs.add(allJobs.remove(allJobs.size()-1));
        if (allJobs.size()>0) lastJobs.add(allJobs.remove(allJobs.size()-1));
        return  lastJobs;
    }

    public static List<Help> getHelpsForOwner(User user){

        List<Help> helps = find.where().eq("owner_login", user.login).findList();
        List<Help> helps2 = new ArrayList<>();
        for (Help help : helps){
            if(!help.isDeleted) helps2.add(help);
        }
        return  helps2;
    }
    public static List<Help> getHelpsForHelpie(User user){
        List<Help> helps = find.where().eq("helpie_login", user.login).findList();
        List<Help> helps2 = new ArrayList<>();
        for (Help help : helps){
            if(!help.isDeleted) helps2.add(help);
        }
        return  helps2;
    }
    public static List<Help> getHelpsWithUserFilter(User user){



        return new ArrayList<>();
    }
}
