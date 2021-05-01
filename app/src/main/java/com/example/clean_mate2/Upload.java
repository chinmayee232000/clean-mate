package com.example.clean_mate2;

public class Upload {
    private  String mName,mImageUrl;

    public Upload(){

    }

    public Upload(String ImageName,String imageUrl)
    {
        if(ImageName.trim().equals(""))
            ImageName="No Name";
        mName=ImageName;
        mImageUrl=imageUrl;
    }

    public String getName() {
        return mName;
    }


    public void setName(String Name)
    {
        mName=Name;
    }
    public void setmImageUrl(String imageUrl)
    {
        mImageUrl=imageUrl;
    }
    public String getImageUrl()
    {
        return mImageUrl;
    }
}
