package Learning.ShootingEachOtherAgents;
public final class Vector2D {

    // fields
    public double x, y;

    // construct a null vector
    public Vector2D() {
        x = 0;
        y = 0;
    }

    // construct a vector with given coordinates
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // construct a vector that is a copy of the argument
    public Vector2D(Vector2D v) {
        this.x = v.getX();
        this.y = v.getY();
    }

    // set coordinates
    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // set coordinates to argument vector coordinates
    public void set(Vector2D v) {
        set(v.getX(), v.getY());
    }

    // compare for equality (needs to allow for Object type argument...)

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector2D vector2D = (Vector2D) o;

        if (Double.compare(vector2D.x, x) != 0) return false;
        if (Double.compare(vector2D.y, y) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = x != +0.0d ? Double.doubleToLongBits(x) : 0L;
        result = (int) (temp ^ (temp >>> 32));
        temp = y != +0.0d ? Double.doubleToLongBits(y) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    //  magnitude (= "length") of this vector
    public double mag() {
        return(Math.sqrt(x*x + y*y));
    }

    // angle between vector and horizontal axis in radians
    public double theta() {
        return(Math.atan2(y,x));
    }

    // String for displaying vector as text

    @Override
    public String toString() {
        return "Vector2D{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    // add argument vector
    public void add(Vector2D v) {
        add(v.getX(), v.getY());
    }

    // add coordinate values
    public void add(double x, double y) {
        this.x+= x;
        this.y+= y;
    }

    // weighted add - frequently useful
    public void add(Vector2D v, double fac) {
        add(v.getX() * fac, v.getY() * fac);
    }

    // substract argument vector
    public void subtract(Vector2D v) {
        subtract(v.getX(), v.getY());
    }

    // subtract coordinate values
    public void subtract(double x, double y) {
        this.x-= x;
        this.y -=y;
    }

    // multiply with factor
    public void mult(double fac) {
        this.x *= fac;
        this.y *= fac;
    }

// "wrap" vector with respect to given positive values w and h

    // method assumes that x >= -w and y >= -h

    public void wrap(double w, double h) {
        if(x >= w){x = x%w;}
        if(y >= h){y = y%h;}
        if(x < 0){x = (x+w)%w;}
        if(y < 0){y = (y+h)%h;}

    }

    // rotate by angle given in radians
    public void rotate(double theta) {
        set(x * Math.cos(theta) - y * Math.sin(theta), x * Math.sin(theta) + y * Math.cos(theta));
    }

    // scalar product with argument vector
    public double scalarProduct(Vector2D v) {
        return((x * v.getX()) + (y*v.getY()));
    }

    // distance to argument vector
    public double dist(Vector2D v) {
        double tempx = (x - v.getX()) *  (x - v.getX());
        double tempy = (y - v.getY()) *  (y - v.getY());

        return(Math.sqrt(tempx + tempy));
    }

// normalise vector so that mag becomes 1

    // direction is unchanged

    public void normalise() {
        mult(1/mag());
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
