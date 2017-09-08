package com.fz.cdh.pcdd.network.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hang on 2017/2/21.
 */

public class LatestChoiceInfo implements Serializable {

        /**
         * area_id : 1
         * bili : 2.0
         * bili_id : 3
         * choice_name : 单
         * choice_no : 837263
         * choice_result : 1,3,5,7,9,11,13,15,17,19,21,23,25,27
         * create_time : 1501515954000
         * game_type : 1
         * get_point : 0.0
         * id : 2
         * is_zhong : -1
         * point : 500.0
         * room_id : 1
         * user_id : 32
         */

        private int area_id;
        private double bili;
        private int bili_id;
        private String choice_name;
        private String choice_no;
        private String choice_result;
        private long create_time;
        private int game_type;
        private double get_point;
        private int id;
        private int is_zhong;
        private double point;
        private int room_id;
        private int user_id;

        public int getArea_id() {
            return area_id;
        }

        public void setArea_id(int area_id) {
            this.area_id = area_id;
        }

        public double getBili() {
            return bili;
        }

        public void setBili(double bili) {
            this.bili = bili;
        }

        public int getBili_id() {
            return bili_id;
        }

        public void setBili_id(int bili_id) {
            this.bili_id = bili_id;
        }

        public String getChoice_name() {
            return choice_name;
        }

        public void setChoice_name(String choice_name) {
            this.choice_name = choice_name;
        }

        public String getChoice_no() {
            return choice_no;
        }

        public void setChoice_no(String choice_no) {
            this.choice_no = choice_no;
        }

        public String getChoice_result() {
            return choice_result;
        }

        public void setChoice_result(String choice_result) {
            this.choice_result = choice_result;
        }

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }

        public int getGame_type() {
            return game_type;
        }

        public void setGame_type(int game_type) {
            this.game_type = game_type;
        }

        public double getGet_point() {
            return get_point;
        }

        public void setGet_point(double get_point) {
            this.get_point = get_point;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIs_zhong() {
            return is_zhong;
        }

        public void setIs_zhong(int is_zhong) {
            this.is_zhong = is_zhong;
        }

        public double getPoint() {
            return point;
        }

        public void setPoint(double point) {
            this.point = point;
        }

        public int getRoom_id() {
            return room_id;
        }

        public void setRoom_id(int room_id) {
            this.room_id = room_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }
}
