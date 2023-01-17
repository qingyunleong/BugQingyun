package org.example;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseManager {

    private Connection connect() {
        String url = "jdbc:sqlite:C:/Users/U/IdeaProjects/Bugbug Qingyun/database/bot.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public String login(String loginStaffID, String loginPassword) {
        String respond = " ";
        String sql = "SELECT * FROM System_Admin WHERE Staff_ID = ? AND Password = ?";

        try{
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, loginStaffID);
            pstmt.setString(2, loginPassword);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                respond += "Login successful! Welcome, " + loginStaffID + "." +
                        "\n\n" + "Here are some additional commands that are now available to you:\n" +
                        "/approvedSchoolAdmin - approved the request of register School Admin\n" +
                        "/displaySchoolAdmin - display the list of School Admin\n\n" +
                        "Reply 0: Back to main page";
            } else {
                respond += "Sorry, the Staff ID or password is incorrect. Please try again.\n\n" +
                        "Reply 0: Back to main page";
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return respond;
    }

    public String displayApprovedAdmin() {
        String respond = " ";

        try (Connection conn = this.connect();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT Staff_ID, Staff_Name FROM School_Admin WHERE status = 'pending'")) {

            while (resultSet.next()) {
                respond += resultSet.getString("Staff_ID") + " " + resultSet.getString("Staff_Name") + "\n";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return respond;
    }

    public boolean checkPinAdmin(String pin) {
        String sql = "SELECT pin FROM System_Admin WHERE pin = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, pin);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println(pin);
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public String checkAdminPin(String loginStaffID, String pin) {
        String respond = " ";

        if(checkPinAdmin(pin)) {
            String sql = "SELECT pin FROM System_Admin WHERE Staff_ID = ?";
            try (Connection conn = this.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, loginStaffID);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    respond += "Correct Pin! " + "\n\n" +
                            "The School Admin that need to approved: \n\n" +
                            displayApprovedAdmin() + "\n" +
                            "Please Enter the Staff Id that wanted to approved. \n" + "Format: Approve - xxxxxxx";
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        else {
            respond += "Sorry, this pin is incorrect. So you cannot continue to process approve the school admin. Please try again.\n\n" +
                    "Reply 0: Back to main page";
        }
        return respond;
    }

    public void updateStatusSchoolAdmin(String schoolStaffID) {
        String sql = "UPDATE School_Admin SET status = 'approved' WHERE Staff_ID = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, schoolStaffID);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public String displayAdminApproved() {
        String respond = " ";

        try (Connection conn = this.connect();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT Staff_ID, Staff_Name FROM School_Admin WHERE status = 'approved'")) {

            if (resultSet.next()) {
                respond += resultSet.getString("Staff_ID") + " " + resultSet.getString("Staff_Name") + "\n";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return respond;
    }

}


