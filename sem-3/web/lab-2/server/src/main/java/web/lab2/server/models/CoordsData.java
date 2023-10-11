package web.lab2.server.models;

import java.text.SimpleDateFormat;
import java.util.Date;


public record CoordsData(double x, double y, double r, Date createdAt) {
    
    public static CoordsData validateAndCreate(String rawX, String rawY, String rawR) throws NumberFormatException {
        if (rawX == null || rawY == null || rawR == null) {
            throw new NumberFormatException("Coords values must be not null");
        }
        double x = Double.parseDouble(rawX);
        double y = Double.parseDouble(rawY);
        double r = Double.parseDouble(rawR);
        Date createdAt = new Date();
        
        return new CoordsData(x, y, r, createdAt);
    }

    public boolean checkHit() {
        double x = x(), y = y(), r = r();

        if (x >= 0 && y >= 0) {  // 1ая четверть + координатные оси
            return Math.pow(x, 2) + Math.pow(y, 2) <= Math.pow(r / 2, 2);
        } else if (x < 0 && y > 0) {  // 2ая четверть
            return false;
        } else if (x <= 0 && y < 0) {  // 3яя четверть + ось Ox
            return y >= -x - r/2;
        } else if (x > 0 && y <= 0) {  // 4ая четверть + ось Oy
            return y >= -r && x <= r/2;
        }

        return false;
    }

    public String formattedCreatedAt() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return dateFormat.format(this.createdAt);
    }

}
