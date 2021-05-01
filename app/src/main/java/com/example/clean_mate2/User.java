package com.example.clean_mate2;

public class User {
    private String Name,Phone_no,hostel,room_no;
    public User(String fullName, String phonenumber, String hostel, Object roomNo)
    {
        //empty constructor required
    }



    public User(String FullName,String phonenumber,String Hostel,String RoomNo)
    {
        Name=FullName;
        Phone_no=phonenumber;
        hostel=Hostel;
        room_no=RoomNo;

    }

    public String getFullName() {
        return Name;
    }
    public String getHostel(){
        return hostel;
    }
    public String getRoomNo()
    {
        return room_no;
    }
    public void setRoomNo(String RoomNo)
    {
        room_no=RoomNo;
    }
    public void setHostel(String Hostel){
        hostel=Hostel;
    }
    public void setFullName(String FullName)
    {
        Name=FullName;
    }
    public void setPhoneNo(String PhoneNo)
    {
        Phone_no=PhoneNo;
    }
    public String getPhoneNo()
    {
        return Phone_no;
    }
}