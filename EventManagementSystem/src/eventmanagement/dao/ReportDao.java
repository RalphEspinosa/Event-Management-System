
package eventmanagement.dao;
import eventmanagement.db.DBConnection;
import java.util.ArrayList;
import java.util.List;
import eventmanagement.model.EventReport;
import java.sql.*;

public class ReportDao {
    public List<EventReport> getEventReports(){
        List<EventReport> reports = new ArrayList<>();
        String sql = "SELECT e.event_id, e.name, e.event_date, e.category, e.capacity, "
            + "(SELECT COUNT(*) FROM attendees a WHERE a.event_id = e.event_id) AS registered, "
            + "(SELECT COUNT(*) FROM attendees a WHERE a.event_id = e.event_id AND a.checked_in = TRUE) AS checked_in_count, "
            + "(SELECT COUNT(*) FROM waitlist w WHERE w.event_id = e.event_id) AS waitlisted, "
            + "(SELECT COUNT(*) FROM feedback f WHERE f.event_id = e.event_id) AS feedback_count, "
            + "(SELECT AVG(f.rating) FROM feedback f WHERE f.event_id = e.event_id) AS average_rating "
            + "FROM events e ORDER BY e.event_date, e.event_id";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()){
            while(rs.next()){
                reports.add(new EventReport(
                    rs.getInt("event_id"),
                    rs.getString("name"),
                    rs.getString("event_date"),
                    rs.getString("category"),
                    rs.getInt("capacity"),
                    rs.getInt("registered"),
                    rs.getInt("checked_in_count"),
                    rs.getInt("waitlisted"),
                    rs.getInt("feedback_count"),
                    rs.getDouble("average_rating")
                ));
            }
        }catch(SQLException e){
            System.out.println("Error loading report: " + e.getMessage());
        }
        return reports;
    }
}