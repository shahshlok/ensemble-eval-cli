public class Cuboid {
    private int l;
    private int w;
    private int h;
    private String color;

    public Cuboid(int l, int w, int h, String color) {
        this.l = l;
        this.w = w;
        this.h = h;
        this.color = color;
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
        System.out.println("Cuboid color: " + color);
        System.out.println("Length: " + l + ", Width: " + w + ", Height: " + h);
        System.out.println("SurfaceArea: " + getSurfaceArea());
        System.out.println("Volume: " + getVolume());
    }

    public static void main(String[] args) {
        Cuboid c1 = new Cuboid();
        Cuboid c2 = new Cuboid(8, 3, 6, "green");

        c1.displayInfo();
        c2.displayInfo();
    }
}

