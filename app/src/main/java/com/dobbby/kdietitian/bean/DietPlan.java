package com.dobbby.kdietitian.bean;

import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 * Created by Dobbby on 2017/6/26.
 */
public class DietPlan {
    private String id;
    private String username;
    private float startWeight;
    private float aimWeight;
    private float dietPerWeek;
    private float currentWeight;

    private List<Progress> progressList;

    public static class Progress {
        private Date date;
        private float weight;

        public Progress() {
        }

        public Progress(Date date, float weight) {
            this.date = date;
            this.weight = weight;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public float getWeight() {
            return weight;
        }

        public void setWeight(float weight) {
            this.weight = weight;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public float getStartWeight() {
        return startWeight;
    }

    public void setStartWeight(float startWeight) {
        this.startWeight = startWeight;
    }

    public float getAimWeight() {
        return aimWeight;
    }

    public void setAimWeight(float aimWeight) {
        this.aimWeight = aimWeight;
    }

    public float getDietPerWeek() {
        return dietPerWeek;
    }

    public void setDietPerWeek(float dietPerWeek) {
        this.dietPerWeek = dietPerWeek;
    }

    public float getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(float currentWeight) {
        this.currentWeight = currentWeight;
    }

    public List<Progress> getProgressList() {
        return progressList;
    }

    public void setProgressList(List<Progress> progressList) {
        this.progressList = progressList;
    }

    public void addProgressList(Progress progress) {
        if (this.progressList == null) {
            this.progressList = new Vector<>();
        }
        this.progressList.add(progress);
    }
}
