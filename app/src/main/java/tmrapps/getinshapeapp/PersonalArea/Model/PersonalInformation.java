package tmrapps.getinshapeapp.PersonalArea.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by nathan on 01/02/2018.
 */

@Entity
public class PersonalInformation {

    @PrimaryKey
    @NonNull
    public String userId;

    public double currentWeight;
    public double weightToAchieve;

    long lastUpdate;

    public Date dateEndOfTrain;
    public int hourTrain;
    public int minuteTrain;

    public List<String> dayOfWeek;

    public String getUserId() { return this.userId; }
    public void setUserId(String id) { this.userId = id; }

    public double getCurrentWeight() { return this.currentWeight; }
    public void setCurrentWeight(double weight) { this.currentWeight = weight; }

    public double getWeightToAchieve() { return this.weightToAchieve; }
    public void setWeightToAchieve(double weight) { this.weightToAchieve = weight; }

    public String getDateEndOfTrain()
    {
        if(dateEndOfTrain != null){
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

            return df.format(this.dateEndOfTrain);
        }

        return "";
    }
    public void setDateEndOfTrain(Date date) { this.dateEndOfTrain = date; }

    public void setDateEndOfTrain(String dateEndOfTrain) {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        try {
            this.dateEndOfTrain = format.parse(dateEndOfTrain);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public int getHourTrain() { return this.hourTrain; }
    public void setHourTrain(int hour) { this.hourTrain = hour; }

    public int getMinuteTrain() { return this.minuteTrain; }
    public void setMinuteTrain(int minute) { this.minuteTrain = minute; }


    public String getTime(){
        if (this.minuteTrain != 0 || this.hourTrain != 0) {
            return this.hourTrain + ":" + this.minuteTrain;
        }

        return "";
    }

    public List<String> getDayOfWeek() { return this.dayOfWeek; }
    public void setDayOfWeek(List<String> days) { this.dayOfWeek = days; }

    public void setDayOfWeek(String days)
    {
        if (days != null) {
            String[] parts = days.split(",");

            for (String item :
                    parts) {
                this.dayOfWeek.add(item);
            }
        }
    }

    public String getDayOfWeekAppend()
    {
        StringBuilder build = new StringBuilder();
        for (String item:
             dayOfWeek) {
            build.append(item + ",");
        }
        return build.toString();
    }


    public PersonalInformation(){
        this.dayOfWeek = new ArrayList<>();
    }



}
