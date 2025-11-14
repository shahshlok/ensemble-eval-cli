public class Cuboid {
    private double l;
    private double w;
    private double h;
    private String color;

    public Cuboid(double len, double wid, double hei, String c) {
        this.l = len;
        this.w = wid;
        this.h = hei;
        this.color = c;
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
        return l * w * h;
    }

    public void displayInfo() {
        System.out.println("Cuboid information:");
        System.out.println("Color: " + color);
        System.out.println("l=" + l + ", w=" + w + ", h=" + h);
        System.out.println("Surface area: " + getSurfaceArea());
        System.out.println("Volume: " + getVolume());
    }

    public static void main(String[] args) {
        Cuboid a = new Cuboid();
        Cuboid b = new Cuboid(8, 3.5, 5.9, "green");

        System.out.println("A:");
        a.displayInfo();

        System.out.println("B:");
        b.displayInfo();
    }
}

