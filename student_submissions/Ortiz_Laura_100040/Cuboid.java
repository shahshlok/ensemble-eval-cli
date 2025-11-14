public class Cuboid {
    private double l;
    private double w;
    private double h;
    private String color;

    public Cuboid(double len, double wid, double hei, String col) {
        this.l = len;
        this.w = wid;
        this.h = hei;
        this.color = col;
    }

    public Cuboid() {
        this(1, 1, 1, "white");
    }

    public double getL() {
        return l;
    }

    public double getW() {
        return w;
    }

    public double getH() {
        return h;
    }

    public String getColor() {
        return color;
    }

    public double getSurfaceArea() {
        return 2 * (l * w + l * h + w * h);
    }

    public double getVolume() {
        return l * w;
    }

    public void displayInfo() {
        System.out.println("Color: " + color);
        System.out.println("l=" + l + " w=" + w + " h=" + h);
        System.out.println("surface=" + getSurfaceArea());
        System.out.println("volume=" + getVolume());
    }

    public static void main(String[] args) {
        Cuboid defaultCuboid = new Cuboid();
        Cuboid secondCuboid = new Cuboid(8, 3.5, 5.9, "green");

        defaultCuboid.displayInfo();
        secondCuboid.displayInfo();
    }
}

