package com.example.appfinance.model;

public class ReportCategoryLink {

    private int ID;
    private int reportID;
    private int categoryID;
    public ReportCategoryLink()
    {
    }
    public ReportCategoryLink(int ID, int reportID, int categoryID)
    {
        this.ID = ID;
        this.reportID = reportID;
        this.categoryID = categoryID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getReportID() {
        return reportID;
    }

    public void setReportID(int reportID) {
        this.reportID = reportID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }


}
